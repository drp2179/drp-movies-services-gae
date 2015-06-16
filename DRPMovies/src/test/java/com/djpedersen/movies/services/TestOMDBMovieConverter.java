package com.djpedersen.movies.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

public class TestOMDBMovieConverter {

	private static final Logger logger = Logger.getLogger(TestOMDBMovieConverter.class.getName());

	private OMDBMovieConverter converter;

	@Before
	public void setup() {
		converter = new OMDBMovieConverter();
	}

	@Test
	public void testJsonToOMDBMovie() {
		final String json = OMDBMovieDataHelper.getATeamJSON();
		logger.info("thejson:" + json);

		final OMDBMovie omdbMovie = converter.jsonToOMDBMovie(json);

		logger.info("theobj:" + omdbMovie.toString());

		assertNotNull(omdbMovie);
		assertEquals("The A-Team", omdbMovie.getTitle());
		assertEquals(OMDBMovieDataHelper.A_TEAM_IMDBID, omdbMovie.getImdbId());
		assertEquals("2010", omdbMovie.getYear());
		assertEquals("PG-13", omdbMovie.getRated());
		assertEquals("11 Jun 2010", omdbMovie.getReleased());
		assertEquals("117 min", omdbMovie.getRuntime());
		assertEquals("Action, Adventure, Comedy", omdbMovie.getGenre());
		assertEquals("Joe Carnahan", omdbMovie.getDirector());
		assertEquals(
				"Joe Carnahan, Brian Bloom, Skip Woods, Frank Lupo (television series \"The A-Team\"), Stephen J. Cannell (television series \"The A-Team\")",
				omdbMovie.getWriter());
		assertEquals("Liam Neeson, Bradley Cooper, Jessica Biel, Quinton 'Rampage' Jackson", omdbMovie.getActors());
		assertEquals("English, Spanish, French, Swahili, German", omdbMovie.getLanguage());
		assertEquals("USA", omdbMovie.getCountry());
		assertEquals("1 win & 3 nominations.", omdbMovie.getAwards());
		assertEquals(
				"http://ia.media-imdb.com/images/M/MV5BMTc4ODc4NTQ1N15BMl5BanBnXkFtZTcwNDUxODUyMw@@._V1_SX300.jpg",
				omdbMovie.getPoster());
		assertEquals("47", omdbMovie.getMetascore());
		assertEquals("6.8", omdbMovie.getImdbRating());
		assertEquals("192160", omdbMovie.getImdbVotes());
		assertEquals("movie", omdbMovie.getType());
		assertEquals("True", omdbMovie.getResponse());
		assertEquals(
				"A group of Iraq War veterans looks to clear their name with the U.S. military, who suspect the four men of committing a crime for which they were framed.",
				omdbMovie.getPlot());
		assertNull(omdbMovie.getError());
	}

	@Test
	public void testJsonWithLowercaseFieldNames() {
		final String json = OMDBMovieDataHelper.getStarTrekJSON();
		logger.info("thejson:" + json);

		final OMDBMovie omdbMovie = converter.jsonToOMDBMovie(json);

		logger.info("theobj:" + omdbMovie.toString());

		assertNotNull(omdbMovie);
		assertEquals("Star Trek", omdbMovie.getTitle());
		assertEquals(OMDBMovieDataHelper.STAR_TREK_IMDBID, omdbMovie.getImdbId());
		assertEquals("2009", omdbMovie.getYear());
		assertEquals("PG-13", omdbMovie.getRated());
		assertEquals("8 May 2009", omdbMovie.getReleased());
		assertEquals("127 min", omdbMovie.getRuntime());
		assertEquals("Action, Adventure, Sci-Fi", omdbMovie.getGenre());
		assertEquals("J.J. Abrams", omdbMovie.getDirector());
		assertEquals("Roberto Orci, Alex Kurtzman, Gene Roddenberry (television series \"Star Trek\")",
				omdbMovie.getWriter());
		assertEquals("Chris Pine, Zachary Quinto, Leonard Nimoy, Eric Bana", omdbMovie.getActors());
		assertEquals("English", omdbMovie.getLanguage());
		assertEquals("USA, Germany", omdbMovie.getCountry());
		assertEquals("Won 1 Oscar. Another 23 wins & 67 nominations.", omdbMovie.getAwards());
		assertEquals(
				"http://ia.media-imdb.com/images/M/MV5BMjE5NDQ5OTE4Ml5BMl5BanBnXkFtZTcwOTE3NDIzMw@@._V1_SX300.jpg",
				omdbMovie.getPoster());
		assertEquals("83", omdbMovie.getMetascore());
		assertEquals("8.0", omdbMovie.getImdbRating());
		assertEquals("458022", omdbMovie.getImdbVotes());
		assertEquals("movie", omdbMovie.getType());
		assertEquals("True", omdbMovie.getResponse());
		assertEquals(
				"The brash James T. Kirk tries to live up to his father's legacy with Mr. Spock keeping him in check as a vengeful, time-traveling Romulan creates black holes to destroy the Federation one planet at a time.",
				omdbMovie.getPlot());
		assertNull(omdbMovie.getError());
	}

	@Test
	public void testOMDBMovieSetters() {
		final OMDBMovie omdbMovie = new OMDBMovie();

		final String imdbVotes = "movie votes";
		omdbMovie.setImdbVotes(imdbVotes);
		assertEquals(imdbVotes, omdbMovie.getImdbVotes());

		final String response = "the response";
		omdbMovie.setResponse(response);
		assertEquals(response, omdbMovie.getResponse());

		final String error = "the error";
		omdbMovie.setError(error);
		assertEquals(error, omdbMovie.getError());
	}

	@Test
	public void testOMDBMovieToJSON() {
		final String director = "the director";
		final String imdbId = "the imdb id";
		final String title = "the title";
		final String year = "1776";

		final OMDBMovie omdbMovie = new OMDBMovie();
		omdbMovie.setDirector(director);
		omdbMovie.setImdbId(imdbId);
		omdbMovie.setTitle(title);
		omdbMovie.setYear(year);

		logger.info("theobj:" + omdbMovie.toString());

		final String json = converter.omdbMovieToJson(omdbMovie);

		logger.info("thejson:" + json);

		assertTrue("director", json.indexOf(director) > 0);
		assertTrue("imdbId", json.indexOf(imdbId) > 0);
		assertTrue("title", json.indexOf(title) > 0);
		assertTrue("year", json.indexOf(year) > 0);
	}

}
