package cat.itacademy.barcelonactiva.SanchezMoreno.Marc.s05.t02.n01Mongo.exceptions;

import java.util.NoSuchElementException;

public class GamesNotPlayedException extends NoSuchElementException {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GamesNotPlayedException(){
        super("Games not played");
    }
}