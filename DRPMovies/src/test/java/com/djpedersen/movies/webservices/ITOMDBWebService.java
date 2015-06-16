package com.djpedersen.movies.webservices;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.jayway.restassured.RestAssured;

public class ITOMDBWebService {

	@Before
	public void setup() {

	}

	@Test
	public void testHuntRedOctoberByTitle() throws UnsupportedEncodingException {
		final String url = "http://localhost:8080/ws/omdb/search?title="
				+ URLEncoder.encode("Hunt for Red October", "UTF-8");
		RestAssured.get(url).then().body("title", Matchers.equalTo("The Hunt for Red October"));
	}

	@Test
	@Ignore
	public void testHuntRedOctoberByIMDBId() throws UnsupportedEncodingException {
		final String url = "http://localhost:8080/ws/omdb/search?title="
				+ URLEncoder.encode("Hunt for Red October", "UTF-8");
		RestAssured.get(url).then().body("title", Matchers.equalTo("The Hunt for Red October"));
	}
}
