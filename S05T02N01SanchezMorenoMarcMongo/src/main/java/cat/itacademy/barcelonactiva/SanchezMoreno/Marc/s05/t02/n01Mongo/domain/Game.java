package cat.itacademy.barcelonactiva.SanchezMoreno.Marc.s05.t02.n01Mongo.domain;



import java.util.Date;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


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
	

	public Game() {
		ResultCalculator calculateResult = (d1, d2) -> (d1 + d2 == 7) ? "Victory!" : "you lose";

		this.dice1 = (int) (Math.random() * 6 + 1);
		this.dice2 = (int) (Math.random() * 6 + 1);
		this.result = calculateResult.calculate(dice1, dice2);
		
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public int getDice1() {
		return dice1;
	}

	public void setDice1(int dice1) {
		this.dice1 = dice1;
	}

	public int getDice2() {
		return dice2;
	}

	public void setDice2(int dice2) {
		this.dice2 = dice2;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Date getGameDate() {
		return gameDate;
	}

	public void setGameDate(Date gameDate) {
		this.gameDate = gameDate;
	}

}
