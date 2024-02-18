package cat.itacademy.barcelonactiva.SanchezMoreno.Marc.s05.t02.n01Mongo.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cat.itacademy.barcelonactiva.SanchezMoreno.Marc.s05.t02.n01Mongo.domain.Game;
import cat.itacademy.barcelonactiva.SanchezMoreno.Marc.s05.t02.n01Mongo.domain.Usuario;
import cat.itacademy.barcelonactiva.SanchezMoreno.Marc.s05.t02.n01Mongo.dto.GameDTO;
import cat.itacademy.barcelonactiva.SanchezMoreno.Marc.s05.t02.n01Mongo.dto.UsuarioDTO;
import cat.itacademy.barcelonactiva.SanchezMoreno.Marc.s05.t02.n01Mongo.exceptions.AlreadyHasNameException;
import cat.itacademy.barcelonactiva.SanchezMoreno.Marc.s05.t02.n01Mongo.exceptions.ExistingNameException;
import cat.itacademy.barcelonactiva.SanchezMoreno.Marc.s05.t02.n01Mongo.exceptions.GamesNotPlayedException;
import cat.itacademy.barcelonactiva.SanchezMoreno.Marc.s05.t02.n01Mongo.exceptions.NotExistingUsersException;
import cat.itacademy.barcelonactiva.SanchezMoreno.Marc.s05.t02.n01Mongo.exceptions.UserNotFoundException;
import cat.itacademy.barcelonactiva.SanchezMoreno.Marc.s05.t02.n01Mongo.repository.GameRepository;
import cat.itacademy.barcelonactiva.SanchezMoreno.Marc.s05.t02.n01Mongo.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService {


	@Autowired
	private UserRepository usersRepo;

	@Autowired
	private GameRepository gameRepo;



	@Override
	public Usuario findUser(String username) {
		
		Usuario user = null;
		try {
			user = usersRepo.findByName(username);
		} catch ( UserNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return user;
	}


	@Override
	public Usuario findRepeatedUser(String name) {
	    List<Usuario> userList = usersRepo.findAll();
	    
	    Optional<Usuario> repeatedUser = userList.stream()
	        .filter(u -> u.getName() != null && !u.getName().equalsIgnoreCase("ANONYMOUS"))
	        .filter(u -> u.getName() != null && u.getName().equalsIgnoreCase(name))
	        .findFirst();

	    return repeatedUser.orElse(null);
	}
	
	@Override
	public void saveUser(UsuarioDTO registerDTO) {
		
		if (registerDTO.getName() == null || registerDTO.getName().trim().isEmpty()) {
		    Usuario registerUserAnonymus = userDTOToUserAnonymus(registerDTO);
		    usersRepo.save(registerUserAnonymus);
		    return;
		}
		Usuario registerUser = userDTOToUser(registerDTO);		
		Usuario repeatedUser = findRepeatedUser(registerUser.getName());
		
		if (repeatedUser == null) {
			usersRepo.save(registerUser);
			return;
		}
	
		if (registerUser.getName().equalsIgnoreCase(repeatedUser.getName())) {
			throw new ExistingNameException();
		} else {
			usersRepo.save(registerUser);
		}
	}

	@Override
	public double calculateAllAverageRate() {
		
		List<Game> games = gameRepo.findAll();
		
		if(games.isEmpty()) {
			throw new GamesNotPlayedException();
		}

		long victories = games.stream().filter(game -> game.getResult().equalsIgnoreCase("Victory!")).count();

		long defeats = games.size() - victories;

		double averageRate = 0;
		if (victories + defeats > 0) {
			averageRate = (double) victories / (victories + defeats) * 100;
		}

		return averageRate;
	}

	@Override
	public double calculatePlayerAverageRate(String id) {

		List<Game> games = getUserGamesMongo(id);
		
		if(games.isEmpty()) {
			throw new GamesNotPlayedException();
		}
		double averageRate = 0;

		if (games == null || games.isEmpty()) {
			return 0.0;
		}

		long victories = games.stream().filter(game -> game.getResult().equalsIgnoreCase("Victory!")).count();

		long defeats = games.size() - victories;
		
		averageRate = (double) victories / (victories + defeats) * 100;

		return averageRate;
	}
	

	@Override
	public List<Game> listGames() {
		return gameRepo.findAll();
	}

	@Override
	public Game saveGame(Game game) {
		return gameRepo.save(game);
	}


	@Override
	public List<Usuario> getAllUsers() {
		return usersRepo.findAll();
	}

	@Override
	public Usuario findById(String id) {
		return usersRepo.findById(id).get();
	}


    @Override
    public Usuario userDTOToUser(UsuarioDTO userRegisterDTO){
        return new Usuario(userRegisterDTO.getName());
    }
    
	@Override
	public UsuarioDTO userToDTO(Usuario userRegister) {
		return new UsuarioDTO(userRegister.getName(), userRegister.getAverageRate());
	}
	
    @Override
    public Usuario userDTOToUserAnonymus(UsuarioDTO userRegisterDTOAnonymus){
        return new Usuario("ANONYMOUS");
    }
    
	@Override
	public GameDTO gameToGameDTO(Game game) {
		return new GameDTO(game.getDice1(), game.getDice2());
	}

	@Override
	public List<GameDTO> gameListToGameListDTO(List<Game> games) {
		List<GameDTO> gamesDTO = new ArrayList<GameDTO>();

		for (Game game : games) {
			gamesDTO.add(gameToGameDTO(game));
		}

		return gamesDTO;

	}
	

	@Override
	public void updateUser(String id, UsuarioDTO userDTO) {

		Usuario user = getUser(id);
		
	    if(userDTO.getName().equalsIgnoreCase(user.getName())) {
        	throw new AlreadyHasNameException();
        }

		Usuario existingUser = usersRepo.findByName(userDTO.getName());

		if (existingUser != null) {
			if (existingUser.getId() != user.getId()) {
				throw new ExistingNameException();
			}
		}
		user.setName(userDTO.getName());
		usersRepo.save(user);
	}
	
    @Override
    public Usuario getUser(String id) {
    	
    	
        Optional<Usuario> user = usersRepo.findById(id);
        if(user.isEmpty()){
            throw new UserNotFoundException();
        }
        return user.get();
    }

	@Override
	public GameDTO playGame(String id) {

        Optional<Usuario> user = usersRepo.findById(id);
        if(user.isEmpty()){
            throw new UserNotFoundException();
        }

		Game game = new Game(id);
		saveGame(game);
		addGame(game,id);
		recalculateAverage(id);	
		GameDTO gameDTO = gameToGameDTO(game);
		

		return gameDTO;
	}

	@Override
	public void addGame(Game game, String id) {
		
		Usuario user = getUser(id);
		user.addGame(game);
		usersRepo.save(user);
	}
	
public void recalculateAverage(String id) {
	
	 Double newAverage = calculatePlayerAverageRate(id);  
	
	 Usuario user = getUser(id);
	 
	 user.setAverageRate(newAverage);	
     
     usersRepo.save(user);
	
}
	
	@Override
	public void deleteAllUserGames(String id) { 
		
	    List<Game> userGames = getUserGamesMongo(id);
	    
	    if(userGames.isEmpty()) {
	    	throw new GamesNotPlayedException();
	    }
	    
	    for (Game game : userGames) {
	        gameRepo.delete(game);
	        
	    }
	   Usuario currentUser = getUser(id);
	   currentUser.setAverageRate(null);
	   currentUser.setGamesHistory(new ArrayList<Game>());
	   usersRepo.save(currentUser);
	}

	@Override
	public List<UsuarioDTO> getUsersAverageRate() {
		   
	    List<Usuario> userList = usersRepo.findAll();
	    
	    if(userList.isEmpty()) {
	    	throw new NotExistingUsersException();
	    }
	    List<UsuarioDTO> userDTOList = new ArrayList<>();
	   
	    
	    if (userList != null) {
	        userList.forEach(u -> {
	            if (u != null) {
	                userDTOList.add(new UsuarioDTO(u.getName(), u.getAverageRate()));
	            }
	        });
	    }

	    return userDTOList;
	}
	@Override
	public UsuarioDTO getLoser() {
		
	    List<UsuarioDTO> playerDTOList = getUsersAverageRate();
	    return playerDTOList
	            .stream()
	            .filter(u -> u.getAverageRate() != null)
	            .min(Comparator.comparing(UsuarioDTO::getAverageRate))
	            .orElseThrow(GamesNotPlayedException::new);
	}
	@Override
	public UsuarioDTO getWiner() {
		
	    List<UsuarioDTO> playerDTOList = getUsersAverageRate();
	    return playerDTOList
	            .stream()
	            .filter(u -> u.getAverageRate() != null)
	            .max(Comparator.comparing(UsuarioDTO::getAverageRate))
	            .orElseThrow(GamesNotPlayedException::new);
	}

	@Override
	public List<Game> getUserGamesMongo(String id) {

		List<Usuario> users = usersRepo.findAll();

		List<Game> games = new ArrayList<Game>();
        boolean userFinded =false;
        
		for (Usuario user : users) {
			if (user.getId().equals(id)) {
				userFinded = true;
				games.addAll(user.getGamesHistory());
			}
		}
		if(userFinded == false) {
			throw new UserNotFoundException();
		}
		
		if (games.isEmpty()) {
			throw new GamesNotPlayedException();
		}
		return games;
	}


}
