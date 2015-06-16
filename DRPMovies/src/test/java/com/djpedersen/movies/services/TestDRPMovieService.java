package com.djpedersen.movies.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class TestDRPMovieService {

	private final LocalServiceTestHelper gaeLocalServiceHelper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	private DRPMovieService service;

	private DatastoreService datastoreService;

	@Before
	public void setup() {
		gaeLocalServiceHelper.setUp();
		datastoreService = DatastoreServiceFactory.getDatastoreService();

		service = new DRPMovieService();
		service.setDatastore(datastoreService);

	}

	@After
	public void tearDown() {
		gaeLocalServiceHelper.tearDown();
	}

	@Test
	public void testImportMovieFromOMDB() {
		final OMDBMovie omdbMovie = new OMDBMovie();
		final String title = "Star Wars";
		final String imdbID = "theStarWarsId";
		omdbMovie.setTitle(title);
		omdbMovie.setImdbId(imdbID);
		final String[] formats = { "DVD", "VHS" };

		final DRPMovie drpMovie = service.importMovieFromOMDB(omdbMovie, formats);
		assertNotNull(drpMovie);
		assertEquals(title, drpMovie.getTitle());
	}

	@Test
	public void testCanFetchAfterImported() {
		final OMDBMovie omdbMovie = new OMDBMovie();
		final String title = "Star Wars";
		final String imdbID = "theImdbId";
		omdbMovie.setTitle(title);
		omdbMovie.setImdbId(imdbID);
		final String[] formats = { "DVD", "VHS" };

		final DRPMovie drpMovie = service.importMovieFromOMDB(omdbMovie, formats);
		assertEquals(imdbID, drpMovie.getImdbId());

		final DRPMovie drpMovieFetch = service.getMovieByIMDBId(imdbID);
		assertNotNull(drpMovieFetch);
		assertEquals(title, drpMovieFetch.getTitle());
		assertEquals(imdbID, drpMovieFetch.getImdbId());
	}

	@Test
	public void testCanGetAllMoviesEmptyList() {
		final String sort = null;
		final List<DRPMovie> allMovies = service.getListOfMovies(sort);
		assertTrue(allMovies.isEmpty());
	}

	@Test
	public void testCanGetAllMovies1InList() {

		final OMDBMovie omdbMovie = new OMDBMovie();
		omdbMovie.setTitle("the title");
		omdbMovie.setImdbId("theid");
		final String[] formats = null;

		service.importMovieFromOMDB(omdbMovie, formats);

		final String sort = null;
		final List<DRPMovie> allMovies = service.getListOfMovies(sort);

		assertEquals(1, allMovies.size());
	}

	@Test
	public void testCanGetAllMovies2InList() {

		final OMDBMovie omdbMovie1 = new OMDBMovie();
		omdbMovie1.setTitle("the title 1");
		omdbMovie1.setImdbId("theid1");
		final OMDBMovie omdbMovie2 = new OMDBMovie();
		omdbMovie2.setTitle("the title 2");
		omdbMovie2.setImdbId("theid2");
		final String[] formats = null;

		service.importMovieFromOMDB(omdbMovie1, formats);
		service.importMovieFromOMDB(omdbMovie2, formats);

		final String sort = null;
		final List<DRPMovie> allMovies = service.getListOfMovies(sort);

		assertEquals(2, allMovies.size());
	}

	@Test
	public void testCanGetAllMovies3InList() throws InterruptedException {

		final OMDBMovie omdbMovie1 = new OMDBMovie();
		omdbMovie1.setTitle("the title 1");
		omdbMovie1.setImdbId("theid1");
		final OMDBMovie omdbMovie2 = new OMDBMovie();
		omdbMovie2.setTitle("the title 2");
		omdbMovie2.setImdbId("theid2");
		final OMDBMovie omdbMovie3 = new OMDBMovie();
		omdbMovie3.setTitle("the title 3");
		omdbMovie3.setImdbId("theid3");
		final String[] formats = null;

		service.importMovieFromOMDB(omdbMovie1, formats);
		service.importMovieFromOMDB(omdbMovie2, formats);
		service.importMovieFromOMDB(omdbMovie3, formats);

		final String sort = null;
		final List<DRPMovie> allMovies = service.getListOfMovies(sort);

		assertEquals(3, allMovies.size());
	}

	@Test
	public void testCanGetAllMovies3InTitleAscendingSort() throws InterruptedException {

		final OMDBMovie omdbMovie1 = new OMDBMovie();
		omdbMovie1.setTitle("AAAA");
		omdbMovie1.setImdbId("theid1");
		final OMDBMovie omdbMovie2 = new OMDBMovie();
		omdbMovie2.setTitle("CCCC");
		omdbMovie2.setImdbId("theid2");
		final OMDBMovie omdbMovie3 = new OMDBMovie();
		omdbMovie3.setTitle("BBBB");
		omdbMovie3.setImdbId("theid3");
		final String[] formats = null;

		service.importMovieFromOMDB(omdbMovie1, formats);
		service.importMovieFromOMDB(omdbMovie2, formats);
		service.importMovieFromOMDB(omdbMovie3, formats);

		final String sort = DRPMovieService.SORT_TYPE_TITLE;
		final List<DRPMovie> allMovies = service.getListOfMovies(sort);

		assertEquals(3, allMovies.size());
		assertEquals("AAAA", allMovies.get(0).getTitle());
		assertEquals("BBBB", allMovies.get(1).getTitle());
		assertEquals("CCCC", allMovies.get(2).getTitle());
	}

	@Test
	public void testCanGetAllMovies3InReleasedAscendingSort() throws InterruptedException {

		final OMDBMovie omdbMovie1 = new OMDBMovie();
		omdbMovie1.setTitle("AAAA");
		omdbMovie1.setImdbId("theid1");
		omdbMovie1.setReleased("11 Jun 2010");
		final OMDBMovie omdbMovie2 = new OMDBMovie();
		omdbMovie2.setTitle("BBBB");
		omdbMovie2.setImdbId("theid2");
		omdbMovie2.setReleased("12 Jun 2009");
		final OMDBMovie omdbMovie3 = new OMDBMovie();
		omdbMovie3.setTitle("CCCC");
		omdbMovie3.setImdbId("theid3");
		omdbMovie3.setReleased("13 Jun 2008");
		final String[] formats = null;

		service.importMovieFromOMDB(omdbMovie1, formats);
		service.importMovieFromOMDB(omdbMovie2, formats);
		service.importMovieFromOMDB(omdbMovie3, formats);

		final String sort = DRPMovieService.SORT_TYPE_RELEASE_DATE;
		final List<DRPMovie> allMovies = service.getListOfMovies(sort);

		assertEquals(3, allMovies.size());
		assertEquals("CCCC", allMovies.get(0).getTitle());
		assertEquals("BBBB", allMovies.get(1).getTitle());
		assertEquals("AAAA", allMovies.get(2).getTitle());
	}

	@Test(expected = IllegalStateException.class)
	public void testDeleteNullKey() throws EntityNotFoundException {
		final String imdbId = null;
		service.deleteMovie(imdbId);
	}

	@Test
	public void testDeleteKnownKey() throws EntityNotFoundException {
		final String imdbID = "theid";
		final OMDBMovie omdbMovie = new OMDBMovie();
		omdbMovie.setTitle("the title");
		omdbMovie.setImdbId(imdbID);
		final String[] formats = null;

		service.importMovieFromOMDB(omdbMovie, formats);
		service.deleteMovie(imdbID);
		final DRPMovie movieByIMDBId = service.getMovieByIMDBId(imdbID);
		assertNull(movieByIMDBId);
	}

	@Test(expected = EntityNotFoundException.class)
	public void testDeleteUnknownMovie() throws EntityNotFoundException {
		final String imdbId = "unknown id";
		service.deleteMovie(imdbId);
	}

	@Test
	public void testUnknownMovieLookup() {
		final String imdbID = "unknown id";
		final DRPMovie movieByIMDBId = service.getMovieByIMDBId(imdbID);
		assertNull(movieByIMDBId);
	}

	@Test
	public void testDatastoreConstructor() {
		final DRPMovieService theService = new DRPMovieService(datastoreService);
		assertEquals(datastoreService, theService.getDatastore());
	}

	@Test
	public void testImportDRPMoviesNull() {
		final DRPMovieService theService = new DRPMovieService(datastoreService);
		final List<DRPMovie> drpMovies = null;
		final List<DRPMovie> listOfMoviesBefore = theService.getListOfMovies(null);

		theService.importDRPMovies(drpMovies);

		final List<DRPMovie> listOfMoviesAfter = theService.getListOfMovies(null);

		assertEquals(listOfMoviesBefore.size(), listOfMoviesAfter.size());
	}

	@Test
	public void testImportDRPMoviesEmptyList() {
		final DRPMovieService theService = new DRPMovieService(datastoreService);
		final List<DRPMovie> drpMovies = new ArrayList<DRPMovie>();
		final List<DRPMovie> listOfMoviesBefore = theService.getListOfMovies(null);

		theService.importDRPMovies(drpMovies);

		final List<DRPMovie> listOfMoviesAfter = theService.getListOfMovies(null);

		assertEquals(listOfMoviesBefore.size(), listOfMoviesAfter.size());
	}

	@Test
	public void testImportDRPMoviesNewMovieInList() {
		final DRPMovieService theService = new DRPMovieService(datastoreService);
		final List<DRPMovie> drpMovies = new ArrayList<DRPMovie>();

		final String testJSON = DRPMovieDataHelper.getTestJSON();
		final DRPMovieConverter converter = new DRPMovieConverter();
		final DRPMovie drpMovie = converter.jsonToDRPMovie(testJSON);

		assertNull(theService.getMovieByIMDBId(drpMovie.getImdbId()));

		drpMovies.add(drpMovie);

		theService.importDRPMovies(drpMovies);

		final List<DRPMovie> listOfMovies = theService.getListOfMovies(null);
		listOfMovies.contains(drpMovie);
	}
}
