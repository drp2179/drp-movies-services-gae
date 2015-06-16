package com.djpedersen.movies.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class TestOMDBMovieException {

	@Test
	public void testDefaultConstructor() {
		final OMDBMovieException omdbMovieException = new OMDBMovieException();
		assertNull(omdbMovieException.getMessage());
	}

	@Test
	public void testMessageConstructor() {
		final String msg = "this is a test of the emergency broadcast system";
		final OMDBMovieException omdbMovieException = new OMDBMovieException(msg);
		assertEquals(msg, omdbMovieException.getMessage());
	}

}
