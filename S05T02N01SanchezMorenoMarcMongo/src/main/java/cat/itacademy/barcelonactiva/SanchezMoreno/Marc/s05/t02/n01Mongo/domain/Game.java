package cat.itacademy.barcelonactiva.SanchezMoreno.Marc.s05.t02.n01Mongo.domain;



import java.util.Date;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
;

@Data
@Document(collection = "games")
@AllArgsConstructor
@NoArgsConstructor
public class Game {
	
	@Id
	private String id;
		
	private int dice1;

	
	private int dice2;

	
	private String result;
	

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private Date gameDate = new Date();
	

	public Game(String id) {
		ResultCalculator calculateResult = (d1, d2) -> (d1 + d2 == 7) ? "Victory!" : "you lose";

		this.dice1 = (int) (Math.random() * 6 + 1);
		this.dice2 = (int) (Math.random() * 6 + 1);
		this.result = calculateResult.calculate(dice1, dice2);

	}

}
