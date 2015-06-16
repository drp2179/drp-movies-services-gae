package com.djpedersen.movies.webservices;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.core.Response;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import com.djpedersen.movies.services.DRPMovie;
import com.djpedersen.movies.services.DRPMovieConverter;
import com.djpedersen.movies.services.OMDBMovieDataHelper;
import com.google.gson.Gson;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;

public class ITDRPMovieWebService {

	private Gson gson;

	@Before
	public void setup() {
		gson = new Gson();
	}

	@Test
	public void testGetEmptyListOfMovies() {
		givenNoMoviesInDatabase();

		final String url = "http://localhost:8080/ws/drpmovies/";

		final String responseJSON = RestAssured.get(url).then().extract().asString();
		// final JSONArray theArray = JSON.parseArray(responseJSON);
		final DRPMovie[] moveArray = gson.fromJson(responseJSON, DRPMovie[].class);
		assertEquals(0, moveArray.length);
	}

	private void givenNoMoviesInDatabase() {
		System.out.println("In  givenNoMoviesInDatabase");
		final DRPMovieConverter converter = new DRPMovieConverter();
		final String listurl = "http://localhost:8080/ws/drpmovies/";

		final String movielistjson = RestAssured.get(listurl).then().extract().asString();

		final List<DRPMovie> movies = converter.jsonToDRPMovieList(movielistjson);
		System.out.println("Movies to delete:" + movies.size());

		for (DRPMovie movie : movies) {
			final String deleteurl = "http://localhost:8080/ws/drpmovies/" + movie.getImdbId();
			System.out.println("deleting:" + deleteurl);
			RestAssured.delete(deleteurl).then().statusCode(Matchers.is(Response.Status.NO_CONTENT.getStatusCode()));

		}

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Out givenNoMoviesInDatabase");
	}

	@Test
	public void testImportHuntForRedOctoberMismatchId() throws IOException {
		final String url = "http://localhost:8080/ws/drpmovies/1234";
		final String omdbMovieJSON = OMDBMovieDataHelper.getHuntForRedOctoberJSON();

		RestAssured.given().body(omdbMovieJSON).contentType(ContentType.JSON).when().put(url).then()
				.statusCode(Matchers.is(Response.Status.BAD_REQUEST.getStatusCode()));
	}

	@Test
	public void testImportHuntForRedOctoberCorrect() throws IOException {
		final String url = "http://localhost:8080/ws/drpmovies/" + OMDBMovieDataHelper.HUNT_FOR_RED_OCTOBER_IMDBID;
		final String omdbMovieJSON = OMDBMovieDataHelper.getHuntForRedOctoberJSON();

		RestAssured.given().body(omdbMovieJSON).contentType(ContentType.JSON).when().put(url).then()
				.statusCode(Matchers.is(Response.Status.CREATED.getStatusCode()))
				.body("title", Matchers.equalTo("The Hunt for Red October"));
	}

	@Test
	public void testGetListOfMoviesWithOne() throws IOException {
		givenNoMoviesInDatabase();

		testImportHuntForRedOctoberCorrect();

		final String getUrl = "http://localhost:8080/ws/drpmovies/";

		final String responseJSON = RestAssured.get(getUrl).then().extract().asString();
		// final JSONArray theArray = JSON.parseArray(responseJSON);
		final DRPMovie[] movieArray = gson.fromJson(responseJSON, DRPMovie[].class);
		assertEquals(1, movieArray.length);
	}

	@Test
	public void testGetListOfMoviesWithTwo() throws IOException {
		givenNoMoviesInDatabase();

		testImportHuntForRedOctoberCorrect();

		final String aTeamUrl = "http://localhost:8080/ws/drpmovies/" + OMDBMovieDataHelper.A_TEAM_IMDBID;
		final String omdbMovieATeamJSON = OMDBMovieDataHelper.getATeamJSON();

		RestAssured.given().body(omdbMovieATeamJSON).contentType(ContentType.JSON).when().put(aTeamUrl).then()
				.statusCode(Matchers.is(Response.Status.CREATED.getStatusCode()))
				.body("title", Matchers.equalTo("The A-Team"));

		final String getUrl = "http://localhost:8080/ws/drpmovies/";

		final String responseJSON = RestAssured.get(getUrl).then().extract().asString();
		// final JSONArray theArray = JSON.parseArray(responseJSON);
		final DRPMovie[] movieArray = gson.fromJson(responseJSON, DRPMovie[].class);
		assertEquals(2, movieArray.length);

	}
}
