package com.djpedersen.movies.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.api.client.http.javanet.NetHttpTransport;

public class TestOMDBService {

	private OMDBService service;

	@Before
	public void setup() {
		service = new OMDBService();
		service.setHttpTransport(new NetHttpTransport());
	}

	@Test
	public void canIssueBasicTitleQueryLiveHttpRequest() throws IOException, OMDBMovieNotFoundException {
		final String title = "Hunt for Red October";

		final OMDBMovie omdbMovie = service.findMovieByTitle(title);
		assertNotNull(omdbMovie);
		assertEquals("The Hunt for Red October", omdbMovie.getTitle());
		assertEquals("tt0099810", omdbMovie.getImdbId());
	}

	@Test(expected = OMDBMovieNotFoundException.class)
	public void basicTitleQueryWithBadTitleLiveHttpRequest() throws IOException, OMDBMovieNotFoundException {
		final String title = "HTRO";

		service.findMovieByTitle(title);
	}

	@Test
	@Ignore("Google makes everything final so hard to test")
	public void basicTitleQueryReturns500() throws IOException {
		// final HttpTransport httpTransport =
		// Mockito.mock(HttpTransport.class);
		// final HttpRequestFactory requestFactory =
		// Mockito.mock(HttpRequestFactory.class);
		// final HttpRequest httpRequest = Mockito.mock(HttpRequest.class);
		// final HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
		// Mockito.when(httpTransport.createRequestFactory()).thenReturn(requestFactory);
		// Mockito.when(requestFactory.buildGetRequest(Mockito.any(GenericUrl.class))).thenReturn(httpRequest);
		// Mockito.when(httpRequest.execute()).thenReturn(httpResponse);
		// Mockito.when(httpResponse.getStatusCode()).thenReturn(500);
		//
		// service.setHttpTransport(httpTransport);
		//
		// service.findMovieByTitle("something");
	}

	@Test
	public void canIssueBasicIMDBIdQueryLiveHttpRequest() throws IOException, OMDBMovieNotFoundException {
		final OMDBMovie omdbMovie = service.findMovieByIMDBId(OMDBMovieDataHelper.HUNT_FOR_RED_OCTOBER_IMDBID);
		assertNotNull(omdbMovie);
		assertEquals("The Hunt for Red October", omdbMovie.getTitle());
		assertEquals("tt0099810", omdbMovie.getImdbId());
	}

	@Test(expected = OMDBMovieException.class)
	public void basicIMDBIdQueryWithBadTitleLiveHttpRequest() throws IOException, OMDBMovieNotFoundException {
		final String imdbId = "HTRO";

		service.findMovieByIMDBId(imdbId);
	}

	@Test
	@Ignore("Google makes everything final so hard to test")
	public void basicIMDBIDQueryReturns500() throws IOException {
		// final HttpTransport httpTransport =
		// Mockito.mock(HttpTransport.class);
		// final HttpRequestFactory requestFactory =
		// Mockito.mock(HttpRequestFactory.class);
		// final HttpRequest httpRequest = Mockito.mock(HttpRequest.class);
		// final HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
		// Mockito.when(httpTransport.createRequestFactory()).thenReturn(requestFactory);
		// Mockito.when(requestFactory.buildGetRequest(Mockito.any(GenericUrl.class))).thenReturn(httpRequest);
		// Mockito.when(httpRequest.execute()).thenReturn(httpResponse);
		// Mockito.when(httpResponse.getStatusCode()).thenReturn(500);
		//
		// service.setHttpTransport(httpTransport);
		//
		// service.findMovieByTitle("something");
	}
}
