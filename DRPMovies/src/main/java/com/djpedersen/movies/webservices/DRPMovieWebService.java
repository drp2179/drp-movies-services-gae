package com.djpedersen.movies.webservices;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.djpedersen.movies.services.DRPMovie;
import com.djpedersen.movies.services.DRPMovieConverter;
import com.djpedersen.movies.services.DRPMovieService;
import com.djpedersen.movies.services.OMDBMovie;
import com.djpedersen.movies.services.OMDBMovieConverter;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EntityNotFoundException;

@Path("drpmovies")
public class DRPMovieWebService {
	private static final Logger logger = Logger.getLogger(DRPMovieWebService.class.getName());

	private DRPMovieService drpMovieService;

	private OMDBMovieConverter omdbMovieConverter;

	public DRPMovieWebService() {
		final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		final DRPMovieService drpMovieService2 = new DRPMovieService(datastore);
		this.setDrpMovieService(drpMovieService2);
		this.setOmdbMovieConverter(new OMDBMovieConverter());
	}

	public DRPMovieWebService(final DRPMovieService drpMovieService, final OMDBMovieConverter omdbMovieConverter) {
		this.setDrpMovieService(drpMovieService);
		this.setOmdbMovieConverter(omdbMovieConverter);
	}

	public OMDBMovieConverter getOmdbMovieConverter() {
		return omdbMovieConverter;
	}

	public void setOmdbMovieConverter(OMDBMovieConverter omdbMovieConverter) {
		this.omdbMovieConverter = omdbMovieConverter;
	}

	public DRPMovieService getDrpMovieService() {
		return drpMovieService;
	}

	public void setDrpMovieService(DRPMovieService drpMovieService) {
		this.drpMovieService = drpMovieService;
	}

	@GET
	// @Path("/")
	@Produces("application/json")
	public String getListOfMovies(@QueryParam("sort") final String sort) {

		// TODO: add security check on sort parameter

		logger.info("In getListOfMovies(" + sort + ")");
		// final String sort = null;
		final List<DRPMovie> listOfMovies = this.drpMovieService.getListOfMovies(sort);

		final DRPMovieConverter converter = new DRPMovieConverter();
		final String listOfMoviesJson = converter.drpMovieListToJson(listOfMovies);

		logger.info("returning " + listOfMovies.size() + " movies: " + listOfMoviesJson);
		return listOfMoviesJson;
	}

	@POST
	// @Path("/")
	public String bulkImport(final String drpMovieJSONArray) {

		if (drpMovieJSONArray == null) {
			throw new WebApplicationException("Empty JSON Payload", Response.Status.BAD_REQUEST);
		}

		logger.info("bulkImport:" + drpMovieJSONArray);

		final DRPMovieConverter converter = new DRPMovieConverter();
		final List<DRPMovie> drpMovies = converter.jsonToDRPMovieList(drpMovieJSONArray);
		logger.info("Num Movies to Import:" + drpMovies.size());

		this.drpMovieService.importDRPMovies(drpMovies);

		return getListOfMovies(null);
	}

	// @PUT
	// @Path("/xyz/{imdbid}")
	// @Consumes("application/json")
	// @Produces("application/json")
	// public Response importMovieFromOMDBObject(@PathParam("imdbid") final
	// String imdbId, final OMDBMovie omdbMovie)
	// throws URISyntaxException {
	// logger.info("In importMovieFromOMDB(" + imdbId + ")");
	//
	// final String[] formats = null;
	// if (imdbId == null) {
	// final String msg = "missing id";
	// logger.severe(msg);
	// throw new WebApplicationException(msg, Response.Status.BAD_REQUEST);
	// } else if (omdbMovie == null) {
	// final String msg = "no movie submitted";
	// logger.severe(msg);
	// throw new WebApplicationException(msg, Response.Status.BAD_REQUEST);
	// } else {
	// logger.info("rx:" + omdbMovie.toString());
	//
	// if (!imdbId.equals(omdbMovie.getImdbID())) {
	//
	// final String msg = "id(" + imdbId + ") != movie.imdbId(" +
	// omdbMovie.getImdbID() + ")";
	// logger.warning(msg);
	// throw new WebApplicationException(msg, Response.Status.BAD_REQUEST);
	// } else {
	// final DRPMovie drpMovie = drpMovieService.importMovieFromOMDB(omdbMovie,
	// formats);
	// logger.info("tx: " + drpMovie.toString());
	//
	// final URI createdUri = new URI("/" + imdbId);
	// return Response.created(createdUri).entity(drpMovie).build();
	// }
	// }
	// }

	@PUT
	@Path("/{imdbid}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response importMovieFromOMDB(@PathParam("imdbid") final String imdbId, final String omdbMovieJSON)
			throws URISyntaxException {

		// TODO: add security check on imdbid parameter
		// TODO: add security check on omdbMovieJSON parameter

		logger.info("In importMovieFromOMDB(" + imdbId + ")");

		final String[] formats = null;
		if (imdbId == null) {
			final String msg = "missing id";
			logger.severe(msg);
			throw new WebApplicationException(msg, Response.Status.BAD_REQUEST);
		} else if (omdbMovieJSON == null) {
			final String msg = "no movie JSON submitted";
			logger.severe(msg);
			throw new WebApplicationException(msg, Response.Status.BAD_REQUEST);
		} else {
			logger.info("rx-json:" + omdbMovieJSON);

			final OMDBMovie omdbMovie = omdbMovieConverter.jsonToOMDBMovie(omdbMovieJSON);

			logger.info("rx-obj :" + omdbMovie.toString());

			if (!imdbId.equals(omdbMovie.getImdbId())) {

				final String msg = "id(" + imdbId + ") != movie.imdbId(" + omdbMovie.getImdbId() + ")";
				logger.warning(msg);
				throw new WebApplicationException(msg, Response.Status.BAD_REQUEST);
			} else {
				final DRPMovie drpMovie = drpMovieService.importMovieFromOMDB(omdbMovie, formats);
				logger.info("tx: " + drpMovie.toString());

				final DRPMovieConverter converter = new DRPMovieConverter();
				final String drpMovieJson = converter.drpMovieToJson(drpMovie);

				final URI createdUri = new URI("/" + imdbId);
				return Response.created(createdUri).entity(drpMovieJson).build();
			}
		}
	}

	@DELETE
	@Path("/{imdbid}")
	public Response deleteMovie(@PathParam("imdbid") final String imdbId) {

		// TODO: add security check on imdbid parameter

		logger.info("In deleteMovie(" + imdbId + ")");
		try {
			this.drpMovieService.deleteMovie(imdbId);
			return Response.noContent().build();
		} catch (EntityNotFoundException e) {
			logger.log(Level.SEVERE, "Unable to delete " + imdbId, e);
			return Response.status(Status.NOT_FOUND).build();
		}
	}

}
