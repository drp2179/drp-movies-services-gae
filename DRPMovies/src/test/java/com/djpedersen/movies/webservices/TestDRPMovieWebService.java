package com.djpedersen.movies.webservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.djpedersen.movies.services.DRPMovie;
import com.djpedersen.movies.services.DRPMovieDataHelper;
import com.djpedersen.movies.services.DRPMovieService;
import com.djpedersen.movies.services.OMDBMovie;
import com.djpedersen.movies.services.OMDBMovieConverter;
import com.djpedersen.movies.services.OMDBMovieDataHelper;
import com.google.appengine.api.datastore.EntityNotFoundException;

public class TestDRPMovieWebService {

	private DRPMovieWebService webservice;
	private DRPMovieService drpMovieService;
	private OMDBMovieConverter omdbMovieConverter;

	@Before
	public void setup() {
		drpMovieService = Mockito.mock(DRPMovieService.class);
		omdbMovieConverter = new OMDBMovieConverter();
		webservice = new DRPMovieWebService(drpMovieService, omdbMovieConverter);
	}

	@Test(expected = WebApplicationException.class)
	public void testImportWithoutIdFails() throws URISyntaxException, IOException {
		final String imdbId = null;
		final String huntForRedOctoberJSON = OMDBMovieDataHelper.getHuntForRedOctoberJSON();

		webservice.importMovieFromOMDB(imdbId, huntForRedOctoberJSON);
	}

	@Test(expected = WebApplicationException.class)
	public void testImportWithoutIdMismiatchFails() throws URISyntaxException {
		final String huntForRedOctoberJSON = OMDBMovieDataHelper.getHuntForRedOctoberJSON();
		final String imdbId = "123";
		webservice.importMovieFromOMDB(imdbId, huntForRedOctoberJSON);
	}

	@Test
	public void testBasicImportOfMovie() throws URISyntaxException {
		final String huntForRedOctoberJSON = OMDBMovieDataHelper.getHuntForRedOctoberJSON();
		final OMDBMovieConverter converter = new OMDBMovieConverter();
		final OMDBMovie omdbMovie = converter.jsonToOMDBMovie(huntForRedOctoberJSON);

		final DRPMovie drpMovie = new DRPMovie();
		Mockito.when(drpMovieService.importMovieFromOMDB(Mockito.any(OMDBMovie.class), Mockito.any(String[].class)))
				.thenReturn(drpMovie);

		final Response response = webservice.importMovieFromOMDB(omdbMovie.getImdbId(), huntForRedOctoberJSON);
		assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
		assertNotNull(response.getEntity());
	}

	@Test(expected = WebApplicationException.class)
	public void testImportWithoutDataFails() throws URISyntaxException, IOException {
		final String imdbId = "12345";
		webservice.importMovieFromOMDB(imdbId, null);
	}

	@Test
	public void testBasicGetListOfMovies() {
		final List<DRPMovie> listOfMovies = new ArrayList<DRPMovie>();
		final String sort = null;
		Mockito.when(drpMovieService.getListOfMovies(sort)).thenReturn(listOfMovies);

		final String jsonMovieArray = webservice.getListOfMovies(null);
		assertNotNull(jsonMovieArray);
		assertTrue(jsonMovieArray.startsWith("["));
		assertTrue(jsonMovieArray.endsWith("]"));
	}

	@Test
	public void testGetListOfMoviesTitleSort() {
		final List<DRPMovie> listOfMovies = new ArrayList<DRPMovie>();
		// final String sort = DRPMovieService.SORT_TYPE_TITLE;
		Mockito.when(drpMovieService.getListOfMovies(DRPMovieService.SORT_TYPE_TITLE)).thenReturn(listOfMovies);
		Mockito.when(drpMovieService.getListOfMovies(null)).thenReturn(null);

		final String jsonMovieArray = webservice.getListOfMovies(DRPMovieService.SORT_TYPE_TITLE);
		assertNotNull(jsonMovieArray);
		assertEquals("[]", jsonMovieArray);
	}

	@Test
	public void testGetListOfMoviesReleasedSort() {
		final List<DRPMovie> listOfMovies = new ArrayList<DRPMovie>();
		// final String sort = DRPMovieService.SORT_TYPE_RELEASE_DATE;
		Mockito.when(drpMovieService.getListOfMovies(null)).thenReturn(null);
		Mockito.when(drpMovieService.getListOfMovies(DRPMovieService.SORT_TYPE_RELEASE_DATE)).thenReturn(listOfMovies);

		final String jsonMovieArray = webservice.getListOfMovies(DRPMovieService.SORT_TYPE_RELEASE_DATE);
		assertNotNull(jsonMovieArray);
		assertEquals("[]", jsonMovieArray);
	}

	@Test
	public void testDefaultConstructor() {
		final DRPMovieWebService newservice = new DRPMovieWebService();
		assertNotNull(newservice.getDrpMovieService());
		assertNotNull(newservice.getOmdbMovieConverter());
	}

	@Test
	public void testDeleteNotExisting() throws EntityNotFoundException {
		final String imdbId = "unknown";

		Mockito.doThrow(new EntityNotFoundException(null)).when(drpMovieService).deleteMovie(imdbId);

		final Response deleteMovie = this.webservice.deleteMovie(imdbId);
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), deleteMovie.getStatus());
	}

	@Test
	public void testDeleteExisting() throws URISyntaxException {
		final String huntForRedOctoberJSON = OMDBMovieDataHelper.getHuntForRedOctoberJSON();
		final OMDBMovieConverter converter = new OMDBMovieConverter();
		final OMDBMovie omdbMovie = converter.jsonToOMDBMovie(huntForRedOctoberJSON);

		final DRPMovie drpMovie = new DRPMovie();
		Mockito.when(drpMovieService.importMovieFromOMDB(Mockito.any(OMDBMovie.class), Mockito.any(String[].class)))
				.thenReturn(drpMovie);

		final Response createResponse = webservice.importMovieFromOMDB(omdbMovie.getImdbId(), huntForRedOctoberJSON);

		assertEquals(Response.Status.CREATED.getStatusCode(), createResponse.getStatus());

		final Response deleteResponse = this.webservice.deleteMovie(OMDBMovieDataHelper.HUNT_FOR_RED_OCTOBER_IMDBID);
		assertEquals(Response.Status.NO_CONTENT.getStatusCode(), deleteResponse.getStatus());
	}

	@Test(expected = WebApplicationException.class)
	public void testBulkImportNull() {

		final String drpMovieJSONArray = null;
		webservice.bulkImport(drpMovieJSONArray);
	}

	@Test
	public void testBulkImportEmptyArray() {

		final String drpMovieJSONArray = "[]";
		final String jsonMovieArray = webservice.bulkImport(drpMovieJSONArray);
		assertNotNull(jsonMovieArray);
		assertTrue(jsonMovieArray.startsWith("["));
		assertTrue(jsonMovieArray.endsWith("]"));
	}

	@Test
	public void testBulkImportNewMovie() {
		final List<DRPMovie> listOfMovies = new ArrayList<DRPMovie>();
		final String sort = null;
		Mockito.when(drpMovieService.getListOfMovies(sort)).thenReturn(listOfMovies);
		final String drpMovieJSONArray = "[" + DRPMovieDataHelper.getTestJSON() + "]";

		final String jsonMovieArray = webservice.bulkImport(drpMovieJSONArray);
		assertNotNull(jsonMovieArray);
		assertTrue(jsonMovieArray.startsWith("["));
		assertTrue(jsonMovieArray.endsWith("]"));
	}

}
