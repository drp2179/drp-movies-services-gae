package com.djpedersen.movies.services;

public class OMDBMovieException extends Exception {

	private static final long serialVersionUID = -173550630575523207L;

	public OMDBMovieException() {
		super();
	}

	public OMDBMovieException(final String message) {
		super(message);
	}

}
