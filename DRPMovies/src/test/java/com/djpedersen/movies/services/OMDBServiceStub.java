package com.djpedersen.movies.services;

import java.io.IOException;

import com.google.api.client.http.HttpResponse;

//import com.google.appengine.repackaged.com.google.api.client.http.HttpResponse;

public class OMDBServiceStub extends OMDBService {

	private HttpResponse httpSearchResponse;

	public void setSearchResponse(final HttpResponse httpResponse) {
		this.httpSearchResponse = httpResponse;
	}

	@Override
	protected HttpResponse goSearchOMDBAPIByTitle(String title) throws IOException {
		return httpSearchResponse;
	}

}
