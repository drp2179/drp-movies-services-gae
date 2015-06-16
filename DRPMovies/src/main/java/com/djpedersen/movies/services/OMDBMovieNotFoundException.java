package com.djpedersen.movies.services;

public class OMDBMovieNotFoundException extends OMDBMovieException {

	private static final long serialVersionUID = -906224324460723462L;

	public OMDBMovieNotFoundException() {
		super();
	}

	public OMDBMovieNotFoundException(final String message) {
		super(message);
	}

}
