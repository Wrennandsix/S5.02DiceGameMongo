package cat.itacademy.barcelonactiva.SanchezMoreno.Marc.s05.t02.n01Mongo.services;

import org.springframework.stereotype.Service;

import cat.itacademy.barcelonactiva.SanchezMoreno.Marc.s05.t02.n01Mongo.domain.Game;
import cat.itacademy.barcelonactiva.SanchezMoreno.Marc.s05.t02.n01Mongo.domain.Usuario;
import cat.itacademy.barcelonactiva.SanchezMoreno.Marc.s05.t02.n01Mongo.dto.GameDTO;
import cat.itacademy.barcelonactiva.SanchezMoreno.Marc.s05.t02.n01Mongo.dto.UsuarioDTO;

import java.util.List;



@Service
public interface UserService {
	
	public void updateUser(String id, UsuarioDTO userDTO);
	public void saveUser(UsuarioDTO registroDto);
	public Usuario findUser(String username);
	public List<Usuario> getAllUsers();
	public List<Game> getUserGamesMongo(String user_id);
	public Usuario findById(String user_id);
	public Usuario findRepeatedUser(String name);
	public double calculateAllAverageRate();
	public List<Game> listGames();
	public Game saveGame(Game game);
	public double calculatePlayerAverageRate(String id);
	public Usuario userDTOToUser(UsuarioDTO userRegisterDTO);
	public UsuarioDTO userToDTO(Usuario userRegister);
	public Usuario getUser(String id);
	public void deleteAllUserGames(String id);
	public List<UsuarioDTO> getUsersAverageRate();
	public UsuarioDTO getLoser();
	public UsuarioDTO getWiner();
	public void recalculateAverage(String id);
	public Usuario userDTOToUserAnonymus(UsuarioDTO userRegisterDTOAnonymus);
	public void addGame(Game game, String id);
	public List<GameDTO> gameListToGameListDTO(List<Game> games);
	public GameDTO gameToGameDTO(Game game);
	public GameDTO playGame(String id);
}	
