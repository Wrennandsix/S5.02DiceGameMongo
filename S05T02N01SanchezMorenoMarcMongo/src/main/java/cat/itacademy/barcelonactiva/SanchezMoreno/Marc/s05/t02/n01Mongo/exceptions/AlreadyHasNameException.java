package cat.itacademy.barcelonactiva.SanchezMoreno.Marc.s05.t02.n01Mongo.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class AlreadyHasNameException extends EntityNotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AlreadyHasNameException() {
		super("You already have this name");
	}
}
