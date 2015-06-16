package com.djpedersen.movies.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

public class DRPMovieService {

	public static final String SORT_TYPE_TITLE = "title";
	public static final String SORT_TYPE_RELEASE_DATE = "released";

	private static final Logger logger = Logger.getLogger(DRPMovieService.class.getName());

	private DatastoreService datastore;

	public DRPMovieService() {
	}

	public DRPMovieService(final DatastoreService datastore) {
		this.setDatastore(datastore);
	}

	public DatastoreService getDatastore() {
		return datastore;
	}

	public void setDatastore(final DatastoreService datastore) {
		this.datastore = datastore;
	}

	public DRPMovie importMovieFromOMDB(final OMDBMovie omdbMovie, final String[] formats) {

		final DRPMovieConverter converter = new DRPMovieConverter();
		final List<String> formatsList = (formats == null ? null : Arrays.asList(formats));
		final DRPMovie drpMovie = converter.omdbMovieToDRPMovie(omdbMovie, formatsList);

		final Entity drpMovieEntity = converter.drpMovieToEntity(drpMovie);
		final Key key = datastore.put(drpMovieEntity);
		logger.info("Imported OMDB Movie '" + omdbMovie.getTitle() + "' with entity key:" + key.toString());

		return drpMovie;
	}

	public DRPMovie getMovieByIMDBId(final String imdbId) {

		final DRPMovieConverter converter = new DRPMovieConverter();

		final Key key = converter.getEntityKeyByIMDBId(imdbId);

		try {
			final Entity entity = datastore.get(key);
			final DRPMovie drpMovie = converter.entityToDRPMovie(entity);
			return drpMovie;
		} catch (EntityNotFoundException e) {
			logger.warning("Unable to located IMDBID: " + imdbId);
			return null;
		}

	}

	public List<DRPMovie> getListOfMovies(final String sortType) {
		final DRPMovieConverter converter = new DRPMovieConverter();

		final List<DRPMovie> movies = new ArrayList<DRPMovie>();

		final Query q = new Query(DRPMovieConverter.ENTITY_NAME_DRPMOVIE);

		if (SORT_TYPE_TITLE.equals(sortType)) {
			q.addSort(DRPMovieConverter.FIELD_NAME_TITLE, SortDirection.ASCENDING);
		} else if (SORT_TYPE_RELEASE_DATE.equals(sortType)) {
			q.addSort(DRPMovieConverter.FIELD_NAME_RELEASED, SortDirection.ASCENDING);
		}

		final PreparedQuery pq = datastore.prepare(q);

		for (Entity entity : pq.asIterable()) {
			final DRPMovie drpMovie = converter.entityToDRPMovie(entity);
			movies.add(drpMovie);
		}

		return movies;
	}

	public void deleteMovie(final String imdbId) throws EntityNotFoundException {
		logger.info("Deleting movie " + imdbId);

		if (imdbId == null) {
			throw new IllegalStateException("id is null");
		}

		final DRPMovieConverter converter = new DRPMovieConverter();
		final Key key = converter.getEntityKeyByIMDBId(imdbId);
		this.datastore.get(key);// throws EntityNotFound
		this.datastore.delete(key);
		logger.info("Deleted movie " + imdbId);
	}

	public void importDRPMovies(final List<DRPMovie> drpMovies) {
		if (drpMovies != null) {
			final DRPMovieConverter converter = new DRPMovieConverter();

			for (DRPMovie drpMovie : drpMovies) {
				final Entity drpMovieEntity = converter.drpMovieToEntity(drpMovie);
				final Key key = datastore.put(drpMovieEntity);
				logger.info("Bulk Imported '" + drpMovie.getTitle() + "' with key:" + key.toString());
			}
		}
	}
}
