package com.djpedersen.movies.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.repackaged.org.joda.time.DateTime;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class TestDRPMovieConverter {

	private final LocalServiceTestHelper gaeLocalServiceHelper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	private DRPMovieConverter converter;

	@Before
	public void setup() {
		gaeLocalServiceHelper.setUp();

		converter = new DRPMovieConverter();
	}

	@After
	public void tearDown() {
		gaeLocalServiceHelper.tearDown();
	}

	@Test
	public void testOmdbMovieToDrpMovie() {
		final String title = "the title";
		final long year = 1950;
		final String yearStr = Long.toString(year);
		final String rated = "the rating";
		final String released = "2 Mar 1990";
		final DateTime releasedDT = new DateTime(1990, 3, 2, 0, 0);
		final String runtimeMinutesStr = "123 min";
		final Long runtimeMinutes = 123L;
		final String director = "the director";
		final String plot = "the plot";
		final String language = "the language";
		final String country = "the country";
		final String awards = "the awards";
		final String poster = "http://www.rit.edu/";
		final String imdbId = "the imdb db";
		final String type = "the type";
		final Double metascore = 123.456;
		final String metascoreStr = Double.toString(metascore);
		final Double imdbRating = 987.654;
		final String imdbRatingStr = Double.toString(imdbRating);
		final List<String> writersList = Arrays.asList("writer1", "writer2", "the writer 3");
		final String writerStr = "writer1, writer2 , the writer 3";
		final List<String> actorsList = Arrays.asList("actor 1", "actor 2", "the actor 3");
		final String actorsStr = "actor 1, actor 2 , the actor 3";
		final List<String> genresList = Arrays.asList("genre 1", "genre 2", "the genre 3");
		final String genresStr = "genre 1, genre 2 , the genre 3";
		final List<String> formatsList = Arrays.asList("format 1", "format 2", "the format 3");
		// final String formatsStr = "format 1, format 2 , the format 3";

		final OMDBMovie omdbMovie = new OMDBMovie();
		omdbMovie.setTitle(title);
		omdbMovie.setYear(yearStr);
		omdbMovie.setRated(rated);
		omdbMovie.setReleased(released);
		omdbMovie.setRuntime(runtimeMinutesStr);
		omdbMovie.setDirector(director);
		omdbMovie.setPlot(plot);
		omdbMovie.setLanguage(language);
		omdbMovie.setCountry(country);
		omdbMovie.setAwards(awards);
		omdbMovie.setPoster(poster);
		omdbMovie.setImdbId(imdbId);
		omdbMovie.setType(type);
		omdbMovie.setMetascore(metascoreStr);
		omdbMovie.setImdbRating(imdbRatingStr);
		omdbMovie.setWriter(writerStr);
		omdbMovie.setActors(actorsStr);
		omdbMovie.setGenre(genresStr);

		final DRPMovie drpMovie = converter.omdbMovieToDRPMovie(omdbMovie, formatsList);

		assertEquals(omdbMovie.getTitle(), drpMovie.getTitle());
		assertEquals(year, drpMovie.getYear().intValue());
		assertEquals(omdbMovie.getRated(), drpMovie.getRating());
		assertEquals(releasedDT.toDate(), drpMovie.getReleased());
		assertEquals(runtimeMinutes, drpMovie.getRuntimeMinutes());
		assertEquals(omdbMovie.getDirector(), drpMovie.getDirector());
		assertEquals(omdbMovie.getPlot(), drpMovie.getPlot());
		assertEquals(omdbMovie.getLanguage(), drpMovie.getLanguage());
		assertEquals(omdbMovie.getCountry(), drpMovie.getCountry());
		assertEquals(omdbMovie.getAwards(), drpMovie.getAwards());
		assertEquals(omdbMovie.getPoster(), drpMovie.getPosterURL());
		assertEquals(omdbMovie.getImdbId(), drpMovie.getImdbId());
		assertEquals(omdbMovie.getType(), drpMovie.getType());
		assertEquals(metascore, drpMovie.getMetascore());
		assertEquals(imdbRating, drpMovie.getImdbRating());
		assertTrue(drpMovie.getWriters().containsAll(writersList));
		assertTrue(drpMovie.getActors().containsAll(actorsList));
		assertTrue(drpMovie.getGenres().containsAll(genresList));
		assertTrue(drpMovie.getFormats().containsAll(formatsList));
	}

	@Test
	public void testOmdbMovieToDrpMovieMetascoreImdbRatingNA() {
		final String title = "the title";
		final long year = 1950;
		final String yearStr = Long.toString(year);
		final String rated = "the rating";
		final String released = "2 Mar 1990";
		final DateTime releasedDT = new DateTime(1990, 3, 2, 0, 0);
		final String runtimeMinutesStr = "123 min";
		final Long runtimeMinutes = 123L;
		final String director = "the director";
		final String plot = "the plot";
		final String language = "the language";
		final String country = "the country";
		final String awards = "the awards";
		final String poster = "http://www.rit.edu/";
		final String imdbId = "the imdb db";
		final String type = "the type";
		final String metascoreStr = "N/A";
		final String imdbRatingStr = "N1/A1";
		final List<String> writersList = Arrays.asList("writer1", "writer2", "the writer 3");
		final String writerStr = "writer1, writer2 , the writer 3";
		final List<String> actorsList = Arrays.asList("actor 1", "actor 2", "the actor 3");
		final String actorsStr = "actor 1, actor 2 , the actor 3";
		final List<String> genresList = Arrays.asList("genre 1", "genre 2", "the genre 3");
		final String genresStr = "genre 1, genre 2 , the genre 3";
		final List<String> formatsList = Arrays.asList("format 1", "format 2", "the format 3");
		// final String formatsStr = "format 1, format 2 , the format 3";

		final OMDBMovie omdbMovie = new OMDBMovie();
		omdbMovie.setTitle(title);
		omdbMovie.setYear(yearStr);
		omdbMovie.setRated(rated);
		omdbMovie.setReleased(released);
		omdbMovie.setRuntime(runtimeMinutesStr);
		omdbMovie.setDirector(director);
		omdbMovie.setPlot(plot);
		omdbMovie.setLanguage(language);
		omdbMovie.setCountry(country);
		omdbMovie.setAwards(awards);
		omdbMovie.setPoster(poster);
		omdbMovie.setImdbId(imdbId);
		omdbMovie.setType(type);
		omdbMovie.setMetascore(metascoreStr);
		omdbMovie.setImdbRating(imdbRatingStr);
		omdbMovie.setWriter(writerStr);
		omdbMovie.setActors(actorsStr);
		omdbMovie.setGenre(genresStr);

		final DRPMovie drpMovie = converter.omdbMovieToDRPMovie(omdbMovie, formatsList);

		assertEquals(omdbMovie.getTitle(), drpMovie.getTitle());
		assertEquals(year, drpMovie.getYear().intValue());
		assertEquals(omdbMovie.getRated(), drpMovie.getRating());
		assertEquals(releasedDT.toDate(), drpMovie.getReleased());
		assertEquals(runtimeMinutes, drpMovie.getRuntimeMinutes());
		assertEquals(omdbMovie.getDirector(), drpMovie.getDirector());
		assertEquals(omdbMovie.getPlot(), drpMovie.getPlot());
		assertEquals(omdbMovie.getLanguage(), drpMovie.getLanguage());
		assertEquals(omdbMovie.getCountry(), drpMovie.getCountry());
		assertEquals(omdbMovie.getAwards(), drpMovie.getAwards());
		assertEquals(omdbMovie.getPoster(), drpMovie.getPosterURL());
		assertEquals(omdbMovie.getImdbId(), drpMovie.getImdbId());
		assertEquals(omdbMovie.getType(), drpMovie.getType());
		assertNull(drpMovie.getMetascore());
		assertNull(drpMovie.getImdbRating());
		assertTrue(drpMovie.getWriters().containsAll(writersList));
		assertTrue(drpMovie.getActors().containsAll(actorsList));
		assertTrue(drpMovie.getGenres().containsAll(genresList));
		assertTrue(drpMovie.getFormats().containsAll(formatsList));
	}

	@Test
	public void testOmdbMovieToDrpMovieNoFormats() {
		final String title = "the title";
		final long year = 1950;
		final String yearStr = Long.toString(year);
		final String rated = "the rating";
		final String released = "2 Mar 1990";
		final DateTime releasedDT = new DateTime(1990, 3, 2, 0, 0);
		final String runtimeMinutesStr = "123 min";
		final Long runtimeMinutes = 123L;
		final String director = "the director";
		final String plot = "the plot";
		final String language = "the language";
		final String country = "the country";
		final String awards = "the awards";
		final String poster = "http://www.rit.edu/";
		final String imdbId = "the imdb db";
		final String type = "the type";
		final Double metascore = 123.456;
		final String metascoreStr = Double.toString(metascore);
		final Double imdbRating = 987.654;
		final String imdbRatingStr = Double.toString(imdbRating);
		final List<String> writersList = Arrays.asList("writer1", "writer2", "the writer 3");
		final String writerStr = "writer1, writer2 , the writer 3";
		final List<String> actorsList = Arrays.asList("actor 1", "actor 2", "the actor 3");
		final String actorsStr = "actor 1, actor 2 , the actor 3";
		final List<String> genresList = Arrays.asList("genre 1", "genre 2", "the genre 3");
		final String genresStr = "genre 1, genre 2 , the genre 3";
		// final List<String> formatsList = Arrays.asList("format 1",
		// "format 2", "the format 3");
		// final String formatsStr = "format 1, format 2 , the format 3";

		final OMDBMovie omdbMovie = new OMDBMovie();
		omdbMovie.setTitle(title);
		omdbMovie.setYear(yearStr);
		omdbMovie.setRated(rated);
		omdbMovie.setReleased(released);
		omdbMovie.setRuntime(runtimeMinutesStr);
		omdbMovie.setDirector(director);
		omdbMovie.setPlot(plot);
		omdbMovie.setLanguage(language);
		omdbMovie.setCountry(country);
		omdbMovie.setAwards(awards);
		omdbMovie.setPoster(poster);
		omdbMovie.setImdbId(imdbId);
		omdbMovie.setType(type);
		omdbMovie.setMetascore(metascoreStr);
		omdbMovie.setImdbRating(imdbRatingStr);
		omdbMovie.setWriter(writerStr);
		omdbMovie.setActors(actorsStr);
		omdbMovie.setGenre(genresStr);

		final DRPMovie drpMovie = converter.omdbMovieToDRPMovie(omdbMovie);

		assertEquals(omdbMovie.getTitle(), drpMovie.getTitle());
		assertEquals(year, drpMovie.getYear().intValue());
		assertEquals(omdbMovie.getRated(), drpMovie.getRating());
		assertEquals(releasedDT.toDate(), drpMovie.getReleased());
		assertEquals(runtimeMinutes, drpMovie.getRuntimeMinutes());
		assertEquals(omdbMovie.getDirector(), drpMovie.getDirector());
		assertEquals(omdbMovie.getPlot(), drpMovie.getPlot());
		assertEquals(omdbMovie.getLanguage(), drpMovie.getLanguage());
		assertEquals(omdbMovie.getCountry(), drpMovie.getCountry());
		assertEquals(omdbMovie.getAwards(), drpMovie.getAwards());
		assertEquals(omdbMovie.getPoster(), drpMovie.getPosterURL());
		assertEquals(omdbMovie.getImdbId(), drpMovie.getImdbId());
		assertEquals(omdbMovie.getType(), drpMovie.getType());
		assertEquals(metascore, drpMovie.getMetascore());
		assertEquals(imdbRating, drpMovie.getImdbRating());
		assertTrue(drpMovie.getWriters().containsAll(writersList));
		assertTrue(drpMovie.getActors().containsAll(actorsList));
		assertTrue(drpMovie.getGenres().containsAll(genresList));

		assertNull(drpMovie.getFormats());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testDrpMovieToEntity() {
		final String title = "the title";
		final Long year = 1959L;
		final String rating = "the rating";
		final Date released = new Date();
		final Long runtimeMinutes = 145L;
		final String plot = "the plot";
		final String language = "the language";
		final String country = "the country";
		final String awards = "the awards";
		final String posterURL = "http://www.harris.com";
		final Double metascore = 123.987;
		final Double imdbRating = 234.443;
		final String imdbId = "the imdb id";
		final String type = "the type";
		final String director = "the director";

		final DRPMovie drpMovie = new DRPMovie();
		drpMovie.setTitle(title);
		drpMovie.setYear(year);
		drpMovie.setRating(rating);
		drpMovie.setReleased(released);
		drpMovie.setRuntimeMinutes(runtimeMinutes);
		drpMovie.setPlot(plot);
		drpMovie.setLanguage(language);
		drpMovie.setCountry(country);
		drpMovie.setAwards(awards);
		drpMovie.setPosterURL(posterURL);
		drpMovie.setMetascore(metascore);
		drpMovie.setImdbRating(imdbRating);
		drpMovie.setImdbId(imdbId);
		drpMovie.setType(type);
		drpMovie.setDirector(director);
		final List<String> genres = Arrays.asList("genre1", "genre2");
		final List<String> writers = Arrays.asList("writer1", "writer2");
		final List<String> actors = Arrays.asList("actors1", "actors2");
		final List<String> formats = Arrays.asList("format1", "format2");
		drpMovie.setGenres(genres);
		drpMovie.setWriters(writers);
		drpMovie.setActors(actors);
		drpMovie.setFormats(formats);

		final Entity drpMovieEntity = converter.drpMovieToEntity(drpMovie);

		assertNotNull(drpMovieEntity);
		assertEquals(title, drpMovieEntity.getProperty(DRPMovieConverter.FIELD_NAME_TITLE));
		assertEquals(year, drpMovieEntity.getProperty(DRPMovieConverter.FIELD_NAME_YEAR));
		assertEquals(rating, drpMovieEntity.getProperty(DRPMovieConverter.FIELD_NAME_RATING));
		assertEquals(released, drpMovieEntity.getProperty(DRPMovieConverter.FIELD_NAME_RELEASED));
		assertEquals(runtimeMinutes, drpMovieEntity.getProperty(DRPMovieConverter.FIELD_NAME_RUNTIME_MINUTES));
		assertEquals(plot, drpMovieEntity.getProperty(DRPMovieConverter.FIELD_NAME_PLOT));
		assertEquals(language, drpMovieEntity.getProperty(DRPMovieConverter.FIELD_NAME_LANGUAGE));
		assertEquals(country, drpMovieEntity.getProperty(DRPMovieConverter.FIELD_NAME_COUNTRY));
		assertEquals(awards, drpMovieEntity.getProperty(DRPMovieConverter.FIELD_NAME_AWARDS));
		assertEquals(posterURL, drpMovieEntity.getProperty(DRPMovieConverter.FIELD_NAME_POSTER_URL));
		assertEquals(metascore, drpMovieEntity.getProperty(DRPMovieConverter.FIELD_NAME_METASCORE));
		assertEquals(imdbRating, drpMovieEntity.getProperty(DRPMovieConverter.FIELD_NAME_IMDB_RATING));
		assertEquals(imdbId, drpMovieEntity.getProperty(DRPMovieConverter.FIELD_NAME_IMDBID));
		assertEquals(type, drpMovieEntity.getProperty(DRPMovieConverter.FIELD_NAME_TYPE));
		assertEquals(director, drpMovieEntity.getProperty(DRPMovieConverter.FIELD_NAME_DIRECTOR));
		assertTrue("genres",
				((List<String>) drpMovieEntity.getProperty(DRPMovieConverter.FIELD_NAME_GENRES)).containsAll(genres));
		assertTrue("writers",
				((List<String>) drpMovieEntity.getProperty(DRPMovieConverter.FIELD_NAME_WRITERS)).containsAll(writers));
		assertTrue("actors",
				((List<String>) drpMovieEntity.getProperty(DRPMovieConverter.FIELD_NAME_ACTORS)).containsAll(actors));
		assertTrue("formats",
				((List<String>) drpMovieEntity.getProperty(DRPMovieConverter.FIELD_NAME_FORMATS)).containsAll(formats));
	}

	@Test
	public void testMovieToJson() {
		final String title = "the title";
		final Long year = 1959L;
		final String rating = "the rating";
		final Date released = new Date();
		final Long runtimeMinutes = 145L;
		final String plot = "the plot";
		final String language = "the language";
		final String country = "the country";
		final String awards = "the awards";
		final String posterURL = "http://www.harris.com";
		final Double metascore = 123.987;
		final Double imdbRating = 234.443;
		final String imdbId = "the imdb id";
		final String type = "the type";
		final String director = "the director";

		final DRPMovie drpMovie = new DRPMovie();
		drpMovie.setTitle(title);
		drpMovie.setYear(year);
		drpMovie.setRating(rating);
		drpMovie.setReleased(released);
		drpMovie.setRuntimeMinutes(runtimeMinutes);
		drpMovie.setPlot(plot);
		drpMovie.setLanguage(language);
		drpMovie.setCountry(country);
		drpMovie.setAwards(awards);
		drpMovie.setPosterURL(posterURL);
		drpMovie.setMetascore(metascore);
		drpMovie.setImdbRating(imdbRating);
		drpMovie.setImdbId(imdbId);
		drpMovie.setType(type);
		drpMovie.setDirector(director);
		final List<String> genres = Arrays.asList("genre1", "genre2");
		final List<String> writers = Arrays.asList("writer1", "writer2");
		final List<String> actors = Arrays.asList("actors1", "actors2");
		final List<String> formats = Arrays.asList("format1", "format2");
		drpMovie.setGenres(genres);
		drpMovie.setWriters(writers);
		drpMovie.setActors(actors);
		drpMovie.setFormats(formats);

		final String json = converter.drpMovieToJson(drpMovie);

		System.out.println("thejson:" + json);
		assertNotNull(json);
		assertTrue(json.indexOf("\"title\":\"the title\"") > 0);
	}

	@Test
	public void testCanConvertToJSONAndBack() {
		final String testJson = DRPMovieDataHelper.getTestJSON();
		final DRPMovie movie = converter.jsonToDRPMovie(testJson);
		final String drpMovieToJson = converter.drpMovieToJson(movie);
		System.out.println("testJson:" + testJson);
		System.out.println("the Json:" + drpMovieToJson);
		assertEquals(testJson, drpMovieToJson);
	}

	@Test
	public void testJsonToMovieEmptyObject() {
		final String json = "{}";
		final DRPMovie movie = converter.jsonToDRPMovie(json);
		assertNotNull(movie);
		assertNull(movie.getTitle());
	}

	@Test
	public void testJsonStringToEmptyList() {
		final List<DRPMovie> movieList = converter.jsonToDRPMovieList("[]");
		assertEquals(0, movieList.size());
	}

	@Test
	public void testJsonStringToListofOne() {
		final String testJson = DRPMovieDataHelper.getTestJSON();
		System.out.println("1MJSON:" + testJson);
		final List<DRPMovie> movieList = converter.jsonToDRPMovieList("[" + testJson + "]");
		assertEquals(1, movieList.size());
		final DRPMovie drpMovie = movieList.get(0);
		System.out.println("1Movie:" + drpMovie);
		assertEquals("the title", drpMovie.getTitle());
		assertEquals(1959, drpMovie.getYear().longValue());
		final DateTime released = new DateTime(drpMovie.getReleased());
		assertEquals(2015, released.getYear());
	}

	@Test
	public void testJsonStringToListofTwo() {
		final String testJson = DRPMovieDataHelper.getTestJSON();
		final String testJson2 = DRPMovieDataHelper.getTestJSON2();
		final List<DRPMovie> movieList = converter.jsonToDRPMovieList("[" + testJson + "," + testJson2 + "]");
		assertEquals(2, movieList.size());

		final DRPMovie drpMovie1 = movieList.get(0);
		System.out.println("2Movie1:" + drpMovie1);
		assertEquals("the title", drpMovie1.getTitle());
		assertEquals(1959, drpMovie1.getYear().longValue());
		final DateTime released1 = new DateTime(drpMovie1.getReleased());
		assertEquals(2015, released1.getYear());

		final DRPMovie drpMovie2 = movieList.get(1);
		System.out.println("2Movie2:" + drpMovie2);
		assertEquals("the title 2", drpMovie2.getTitle());
		assertEquals(1981, drpMovie2.getYear().longValue());
		final DateTime released2 = new DateTime(drpMovie2.getReleased());
		assertEquals(1970, released2.getYear());
	}

	@Test
	public void testMovieListToJsonNullList() {
		final List<DRPMovie> listOfMovies = null;
		final String json = converter.drpMovieListToJson(listOfMovies);
		assertEquals("[]", json);
	}

	@Test
	public void testMovieListToJsonEmptyList() {
		final List<DRPMovie> listOfMovies = new ArrayList<DRPMovie>();
		final String json = converter.drpMovieListToJson(listOfMovies);
		assertEquals("[]", json);
	}

	@Test
	public void testMovieListToJsonListOfOne() {
		final List<DRPMovie> listOfMovies = new ArrayList<DRPMovie>();
		final DRPMovie movie1 = new DRPMovie();
		movie1.setTitle("the title");
		listOfMovies.add(movie1);

		final String json = converter.drpMovieListToJson(listOfMovies);

		assertEquals("[{\"title\":\"the title\"}]", json);
	}

	@Test
	public void testMovieListToJsonListOfTwo() {
		final List<DRPMovie> listOfMovies = new ArrayList<DRPMovie>();
		final DRPMovie movie1 = new DRPMovie();
		movie1.setTitle("the title 1");
		listOfMovies.add(movie1);
		final DRPMovie movie2 = new DRPMovie();
		movie2.setTitle("the title 2");
		listOfMovies.add(movie2);

		final String json = converter.drpMovieListToJson(listOfMovies);

		assertEquals("[{\"title\":\"the title 1\"},{\"title\":\"the title 2\"}]", json);
	}

}