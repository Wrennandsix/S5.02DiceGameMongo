package cat.itacademy.barcelonactiva.SanchezMoreno.Marc.s05.t02.n01Mongo.domain;



import java.util.ArrayList;
import java.util.Date;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "users")

public class Usuario {
	
	@Id
	private String id;


	private String name;
	
	
	private Double averageRate;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private Date userDate = new Date();


	private ArrayList<Game> gamesHistory; 
	
	
    public Usuario(String name) {
        this.name = name;
        this.averageRate = null;
        this.gamesHistory = new ArrayList<Game>();
      
    }

	public void addGame(Game game) {
		this.gamesHistory.add(game);
	}

}