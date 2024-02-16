package cat.itacademy.barcelonactiva.SanchezMoreno.Marc.s05.t02.n01Mongo.domain;


import java.sql.Timestamp;
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
        this.averageRate = 0.0;
        this.gamesHistory = new ArrayList<Game>();
      
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Double getAverageRate() {
		return averageRate;
	}

	public void setAverageRate(Double averageRate) {
		this.averageRate = averageRate;
	}

	public Date getUserDate() {
		return userDate;
	}

	public void setUserDate(Date userDate) {
		this.userDate = userDate;
	}

	public ArrayList<Game> getGamesHistory() {
		return gamesHistory;
	}

	public void setGamesHistory(ArrayList<Game> gamesHistory) {
		this.gamesHistory = gamesHistory;
	}

	public void addGame(Game game) {
		this.gamesHistory.add(game);
	}

}