package com.djpedersen.movies.webservices;

import java.io.IOException;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.djpedersen.movies.services.OMDBMovie;
import com.djpedersen.movies.services.OMDBMovieNotFoundException;
import com.djpedersen.movies.services.OMDBService;

@Path("/omdb")
public class OMDBWebService {

	private static final Logger logger = Logger.getLogger(OMDBWebService.class.getName());

	private OMDBService omdbService;

	public OMDBWebService() {
		this(new OMDBService());
	}

	public OMDBWebService(OMDBService omdbService) {
		this.setOmdbService(omdbService);
	}

	public OMDBService getOmdbService() {
		return omdbService;
	}

	public void setOmdbService(OMDBService omdbService) {
		this.omdbService = omdbService;
	}

	@GET
	@Path("/search")
	@Produces("application/json")
	public OMDBMovie findMovie(@QueryParam("title") final String title, @QueryParam("imdbid") final String imdbId)
			throws IOException {
		logger.info("findMovie('" + title + "' or '" + imdbId + "'");

		if (title != null) {
			try {
				final OMDBMovie omdbMovie = omdbService.findMovieByTitle(title);
				return omdbMovie;
			} catch (OMDBMovieNotFoundException e) {
				throw new WebApplicationException(Response.Status.NOT_FOUND);
			}
		} else if (imdbId != null) {
			try {
				final OMDBMovie omdbMovie = omdbService.findMovieByIMDBId(imdbId);
				return omdbMovie;
			} catch (OMDBMovieNotFoundException e) {
				throw new WebApplicationException(Response.Status.NOT_FOUND);
			}
		} else {
			throw new WebApplicationException("neither title or imdbid was defined", Response.Status.BAD_REQUEST);
		}
	}
}
