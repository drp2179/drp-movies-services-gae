package com.djpedersen.movies.webservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import javax.ws.rs.WebApplicationException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.djpedersen.movies.services.OMDBMovie;
import com.djpedersen.movies.services.OMDBMovieNotFoundException;
import com.djpedersen.movies.services.OMDBService;

public class TestOMDBWebService {

	private OMDBWebService webservice;
	private OMDBService omdbService;

	@Before
	public void setup() {
		omdbService = Mockito.mock(OMDBService.class);
		webservice = new OMDBWebService(omdbService);
	}

	@Test
	public void testBasicSearch() throws IOException, OMDBMovieNotFoundException {
		final String titleSearch = "Hunt for Red October";
		final OMDBMovie movie = new OMDBMovie();
		movie.setTitle("The Hunt for Red October");
		Mockito.when(omdbService.findMovieByTitle(titleSearch)).thenReturn(movie);

		final OMDBMovie foundMovie = webservice.findMovie(titleSearch, null);
		assertEquals("The Hunt for Red October", foundMovie.getTitle());
	}

	@Test(expected = WebApplicationException.class)
	public void testBasicSearchThatFails() throws IOException, OMDBMovieNotFoundException {
		final String titleSearch = "Star Trek Into the Darkness";
		Mockito.when(omdbService.findMovieByTitle(titleSearch)).thenThrow(new OMDBMovieNotFoundException());

		webservice.findMovie(titleSearch, null);
	}

	@Test
	public void testOmdbAccessors() {
		assertEquals(omdbService, webservice.getOmdbService());
	}

	@Test
	public void testDefaultConstructor() {
		final OMDBWebService newservice = new OMDBWebService();
		assertNotNull(newservice.getOmdbService());
	}
}
