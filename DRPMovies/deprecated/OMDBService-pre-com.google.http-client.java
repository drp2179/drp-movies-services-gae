package com.djpedersen.movies.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.util.logging.Logger;

import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.IOUtils;

import com.google.appengine.repackaged.com.google.api.client.http.GenericUrl;
import com.google.appengine.repackaged.com.google.api.client.http.HttpRequest;
import com.google.appengine.repackaged.com.google.api.client.http.HttpRequestFactory;
import com.google.appengine.repackaged.com.google.api.client.http.HttpResponse;
import com.google.appengine.repackaged.com.google.api.client.http.HttpTransport;
import com.google.appengine.repackaged.com.google.api.client.http.javanet.NetHttpTransport;

public class OMDBService {

	private static final Logger logger = Logger.getLogger(OMDBService.class.getName());

	public final static String OMDBAPI_BASE_URL = "http://www.omdbapi.com/";

	private HttpTransport httpTransport;
	private HttpRequestFactory requestFactory;

	public OMDBService() {
		this(new NetHttpTransport());
	}

	public OMDBService(final HttpTransport httpTransport) {
		this.setHttpTransport(httpTransport);
	}

	// public HttpTransport getHttpTransport() {
	// return httpTransport;
	// }

	public void setHttpTransport(final HttpTransport httpTransport) {
		this.httpTransport = httpTransport;

		requestFactory = this.httpTransport.createRequestFactory();
	}

	public OMDBMovie findMovieByTitle(final String title) throws IOException, OMDBMovieNotFoundException {
		final HttpResponse response = goSearchOMDBAPIByTitle(title);
		return processResponse(response);
	}

	public OMDBMovie findMovieByIMDBId(final String imdbId) throws IOException, OMDBMovieNotFoundException {
		final HttpResponse response = goSearchOMDBAPIByIMDBId(imdbId);
		return processResponse(response);
	}

	protected HttpResponse goSearchOMDBAPIByTitle(final String title) throws IOException {
		final String theqs = "t=" + URLEncoder.encode(title, "UTF-8") + "&y=&plot=short&r=json";
		logger.fine("the qs:" + theqs);
		final GenericUrl url = new GenericUrl(OMDBAPI_BASE_URL + "?" + theqs);
		logger.fine("URL:" + url);
		final HttpRequest request = requestFactory.buildGetRequest(url);
		final HttpResponse response = request.execute();
		return response;
	}

	protected HttpResponse goSearchOMDBAPIByIMDBId(final String imdbId) throws IOException {
		final String theqs = "i=" + imdbId + "&y=&plot=short&r=json";
		logger.fine("the qs:" + theqs);
		final GenericUrl url = new GenericUrl(OMDBAPI_BASE_URL + "?" + theqs);
		logger.fine("URL:" + url);
		final HttpRequest request = requestFactory.buildGetRequest(url);
		final HttpResponse response = request.execute();
		return response;
	}

	protected OMDBMovie processResponse(HttpResponse response) throws IOException, OMDBMovieNotFoundException {
		if (response.getStatusCode() == Status.OK.getStatusCode()) {
			final StringWriter stringWriter = new StringWriter();
			final InputStream contentIS = response.getContent();
			IOUtils.copy(contentIS, stringWriter, "UTF-8");
			final String content = stringWriter.toString();

			logger.info("Content:" + content);

			final OMDBMovieConverter converter = new OMDBMovieConverter();
			final OMDBMovie movie = converter.jsonToOMDBMovie(content);

			if ("True".equals(movie.getResponse())) {
				return movie;
			} else {
				logger.warning("Error:" + movie.getError());
				throw new OMDBMovieNotFoundException(movie.getError());
			}
		} else {
			logger.severe("Unsuccessful response from omdbapi.com:" + response.getStatusCode() + " "
					+ response.getStatusMessage());
			return null;
		}
	}
}
