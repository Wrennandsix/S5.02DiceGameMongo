package cat.itacademy.barcelonactiva.SanchezMoreno.Marc.s05.t02.n01Mongo.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cat.itacademy.barcelonactiva.SanchezMoreno.Marc.s05.t02.n01Mongo.domain.Game;
import cat.itacademy.barcelonactiva.SanchezMoreno.Marc.s05.t02.n01Mongo.dto.GameDTO;
import cat.itacademy.barcelonactiva.SanchezMoreno.Marc.s05.t02.n01Mongo.dto.UsuarioDTO;
import cat.itacademy.barcelonactiva.SanchezMoreno.Marc.s05.t02.n01Mongo.services.UserService;



@RestController
@CrossOrigin(origins = "http://localhost:9001")
@RequestMapping("/players")
public class GameController {
	
    private final UserService userService;

    public GameController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<String> newPlayer(@RequestBody UsuarioDTO userRegister){
        userService.saveUser(userRegister);
        return new ResponseEntity<>("Player added successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable("id") String id, @RequestBody UsuarioDTO userDTORequest){
        userService.updateUser(id, userDTORequest);
        return new ResponseEntity<>("Player updated successfully", HttpStatus.OK);
    }
    
    @PostMapping("/{id}/game")
	public ResponseEntity<GameDTO> play(@PathVariable("id") String id) {
		GameDTO newGameDTO = userService.playGame(id);
		return new ResponseEntity<>(newGameDTO, HttpStatus.OK);
	}

    @DeleteMapping("{id}/games")
    public ResponseEntity<String> deleteAllUserGames(@PathVariable("id") String id){
        userService.deleteAllUserGames(id);
        return new ResponseEntity<>("All games deleted successfully", HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<UsuarioDTO>> getAllAverageRate() {

    	List<UsuarioDTO> userDTOList = userService.getUsersAverageRate();
    	return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }
    
    
    @GetMapping("/{id}/games")
    public ResponseEntity<List<GameDTO>> getAllUserGames(@PathVariable("id") String id){
    	
        List<Game> allGames = userService.getUserGamesMongo(id);
        List<GameDTO> allGamesDTO = userService.gameListToGameListDTO(allGames);
        return new ResponseEntity<>(allGamesDTO, HttpStatus.OK);
    }
    
    @GetMapping("/ranking")
    public ResponseEntity<Double> getAllRanking(){
        double ranking = userService.calculateAllAverageRate();
        return new ResponseEntity <>(ranking, HttpStatus.OK);
    }

    @GetMapping("/ranking/loser")
    public ResponseEntity<UsuarioDTO> getLoser(){
        UsuarioDTO loserUser = userService.getLoser();
        return new ResponseEntity<>(loserUser, HttpStatus.OK);
    }

    @GetMapping("/ranking/winner")
    public ResponseEntity<UsuarioDTO> getWinner(){
    	UsuarioDTO winnerUser = userService.getWiner();
        return new ResponseEntity<>(winnerUser, HttpStatus.OK);
    }
}
