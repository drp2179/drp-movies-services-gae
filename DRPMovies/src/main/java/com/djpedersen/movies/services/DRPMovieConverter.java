package com.djpedersen.movies.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DRPMovieConverter {
	private static final Logger logger = Logger.getLogger(DRPMovieConverter.class.getName());

	public static final String ENTITY_NAME_DRPMOVIE = "DRPMovieEntity";

	public static final String FIELD_NAME_TITLE = "title";
	public static final String FIELD_NAME_YEAR = "year";
	public static final String FIELD_NAME_RATING = "rating";
	public static final String FIELD_NAME_RELEASED = "released";
	public static final String FIELD_NAME_RUNTIME_MINUTES = "runtimeMinutes";
	public static final String FIELD_NAME_PLOT = "plot";
	public static final String FIELD_NAME_LANGUAGE = "language";
	public static final String FIELD_NAME_COUNTRY = "country";
	public static final String FIELD_NAME_AWARDS = "awards";
	public static final String FIELD_NAME_POSTER_URL = "posterurl";
	public static final String FIELD_NAME_METASCORE = "metascore";
	public static final String FIELD_NAME_IMDB_RATING = "imdbrating";
	public static final String FIELD_NAME_IMDBID = "imdbid";
	public static final String FIELD_NAME_TYPE = "type";
	public static final String FIELD_NAME_DIRECTOR = "director";
	public static final String FIELD_NAME_GENRES = "genres";
	public static final String FIELD_NAME_WRITERS = "writers";
	public static final String FIELD_NAME_ACTORS = "actors";
	public static final String FIELD_NAME_FORMATS = "formats";

	final private static DateTimeFormatter releasedDRFormatter = DateTimeFormat.forPattern("dd MMM yyyy");

	public DRPMovie omdbMovieToDRPMovie(final OMDBMovie omdbMovie, final List<String> formats) {
		final DRPMovie drpMovie = new DRPMovie();

		drpMovie.setTitle(omdbMovie.getTitle());
		drpMovie.setRating(omdbMovie.getRated());
		drpMovie.setDirector(omdbMovie.getDirector());
		drpMovie.setPlot(omdbMovie.getPlot());
		drpMovie.setLanguage(omdbMovie.getLanguage());
		drpMovie.setCountry(omdbMovie.getCountry());
		drpMovie.setAwards(omdbMovie.getAwards());
		drpMovie.setPosterURL(omdbMovie.getPoster());
		drpMovie.setImdbId(omdbMovie.getImdbId());
		drpMovie.setType(omdbMovie.getType());

		final String yearStr = omdbMovie.getYear();
		if (yearStr != null) {
			final Long year = Long.valueOf(yearStr);
			drpMovie.setYear(year);
		}

		final String released = omdbMovie.getReleased();
		if (released != null) {
			final DateTime parsedDateTime = releasedDRFormatter.parseDateTime(released);
			final Date releasedDate = parsedDateTime.toDate();
			drpMovie.setReleased(releasedDate);
		}

		final String runtime = omdbMovie.getRuntime();
		if (runtime != null) {
			final int indexOfSpace = runtime.indexOf(" ");
			final String minStr = runtime.substring(0, indexOfSpace);

			final Long runtimeMinutes = Long.valueOf(minStr);
			drpMovie.setRuntimeMinutes(runtimeMinutes);
		}

		final String metascoreStr = omdbMovie.getMetascore();
		if (metascoreStr != null) {
			try {
				final Double metascore = Double.valueOf(metascoreStr);
				drpMovie.setMetascore(metascore);
			} catch (NumberFormatException nfe) {
				logger.warning("unable to convert metascore:" + nfe.getMessage());
			}
		}

		final String imdbRatingStr = omdbMovie.getImdbRating();
		if (imdbRatingStr != null) {
			try {
				final Double imdbRating = Double.valueOf(imdbRatingStr);
				drpMovie.setImdbRating(imdbRating);
			} catch (NumberFormatException nfe) {
				logger.warning("unable to convert imdbRating:" + nfe.getMessage());
			}
		}

		final String writerStr = omdbMovie.getWriter();
		final List<String> writersList = parseStrings(writerStr);
		drpMovie.setWriters(writersList);

		final String actorsStr = omdbMovie.getActors();
		final List<String> actorsList = parseStrings(actorsStr);
		drpMovie.setActors(actorsList);

		final String genresStr = omdbMovie.getGenre();
		final List<String> genresList = parseStrings(genresStr);
		drpMovie.setGenres(genresList);

		if (formats != null) {
			drpMovie.setFormats(formats);
		}

		return drpMovie;
	}

	public DRPMovie omdbMovieToDRPMovie(final OMDBMovie omdbMovie) {
		return this.omdbMovieToDRPMovie(omdbMovie, null);
	}

	public Entity drpMovieToEntity(final DRPMovie drpMovie) {
		final Entity drpMovieEntity = new Entity(ENTITY_NAME_DRPMOVIE, drpMovie.getImdbId());

		drpMovieEntity.setProperty(FIELD_NAME_TITLE, drpMovie.getTitle());
		drpMovieEntity.setProperty(FIELD_NAME_YEAR, drpMovie.getYear());
		drpMovieEntity.setProperty(FIELD_NAME_RATING, drpMovie.getRating());
		drpMovieEntity.setProperty(FIELD_NAME_RELEASED, drpMovie.getReleased());
		drpMovieEntity.setProperty(FIELD_NAME_RUNTIME_MINUTES, drpMovie.getRuntimeMinutes());
		drpMovieEntity.setProperty(FIELD_NAME_PLOT, drpMovie.getPlot());
		drpMovieEntity.setProperty(FIELD_NAME_LANGUAGE, drpMovie.getLanguage());
		drpMovieEntity.setProperty(FIELD_NAME_COUNTRY, drpMovie.getCountry());
		drpMovieEntity.setProperty(FIELD_NAME_AWARDS, drpMovie.getAwards());
		drpMovieEntity.setProperty(FIELD_NAME_POSTER_URL, drpMovie.getPosterURL());
		drpMovieEntity.setProperty(FIELD_NAME_METASCORE, drpMovie.getMetascore());
		drpMovieEntity.setProperty(FIELD_NAME_IMDB_RATING, drpMovie.getImdbRating());
		drpMovieEntity.setProperty(FIELD_NAME_IMDBID, drpMovie.getImdbId());
		drpMovieEntity.setProperty(FIELD_NAME_TYPE, drpMovie.getType());
		drpMovieEntity.setProperty(FIELD_NAME_DIRECTOR, drpMovie.getDirector());
		drpMovieEntity.setProperty(FIELD_NAME_GENRES, drpMovie.getGenres());
		drpMovieEntity.setProperty(FIELD_NAME_WRITERS, drpMovie.getWriters());
		drpMovieEntity.setProperty(FIELD_NAME_ACTORS, drpMovie.getActors());
		drpMovieEntity.setProperty(FIELD_NAME_FORMATS, drpMovie.getFormats());

		return drpMovieEntity;
	}

	public Key getEntityKeyByIMDBId(final String imdbID) {
		final Entity drpMovieEntity = new Entity(ENTITY_NAME_DRPMOVIE, imdbID);
		return drpMovieEntity.getKey();
	}

	@SuppressWarnings("unchecked")
	public DRPMovie entityToDRPMovie(final Entity entity) {
		final DRPMovie drpMovie = new DRPMovie();
		drpMovie.setTitle((String) entity.getProperty(FIELD_NAME_TITLE));
		drpMovie.setYear((Long) entity.getProperty(FIELD_NAME_YEAR));
		drpMovie.setRating((String) entity.getProperty(FIELD_NAME_RATING));
		drpMovie.setReleased((Date) entity.getProperty(FIELD_NAME_RELEASED));
		drpMovie.setRuntimeMinutes((Long) entity.getProperty(FIELD_NAME_RUNTIME_MINUTES));
		drpMovie.setPlot((String) entity.getProperty(FIELD_NAME_PLOT));
		drpMovie.setLanguage((String) entity.getProperty(FIELD_NAME_LANGUAGE));
		drpMovie.setCountry((String) entity.getProperty(FIELD_NAME_COUNTRY));
		drpMovie.setAwards((String) entity.getProperty(FIELD_NAME_AWARDS));
		drpMovie.setPosterURL((String) entity.getProperty(FIELD_NAME_POSTER_URL));
		drpMovie.setMetascore((Double) entity.getProperty(FIELD_NAME_METASCORE));
		drpMovie.setImdbRating((Double) entity.getProperty(FIELD_NAME_IMDB_RATING));
		drpMovie.setImdbId((String) entity.getProperty(FIELD_NAME_IMDBID));
		drpMovie.setType((String) entity.getProperty(FIELD_NAME_TYPE));
		drpMovie.setDirector((String) entity.getProperty(FIELD_NAME_DIRECTOR));
		drpMovie.setGenres((List<String>) entity.getProperty(FIELD_NAME_GENRES));
		drpMovie.setWriters((List<String>) entity.getProperty(FIELD_NAME_WRITERS));
		drpMovie.setActors((List<String>) entity.getProperty(FIELD_NAME_ACTORS));
		drpMovie.setFormats((List<String>) entity.getProperty(FIELD_NAME_FORMATS));
		return drpMovie;
	}

	private static List<String> parseStrings(final String stringToParse) {
		if (stringToParse == null) {
			return null;
		} else {
			final String[] splitArray = stringToParse.split(",");
			final List<String> splitList = new ArrayList<String>();

			for (String elements : splitArray) {
				splitList.add(elements.trim());
			}

			return splitList;
		}
	}

	public List<DRPMovie> jsonToDRPMovieList(String json) {
		// final List<DRPMovie> movies = new ArrayList<DRPMovie>();

		// final JSONArray theArray = JSON.parseArray(responseJSON);

		// for (int i = 0; i < theArray.size(); i++) {
		// JSONObject jsonObject = (JSONObject) theArray.get(i);
		// jsonObject.
		//
		// if (object instanceof DRPMovie) {
		// final DRPMovie drpMovie = (DRPMovie) object;
		// System.out.println("deleting " + drpMovie.getImdbId());
		// RestAssured.delete("/" + drpMovie.getImdbId()).then()
		// .statusCode(Matchers.is((Response.Status.NO_CONTENT.getStatusCode())));
		// } else {
		// System.out.println("I dont know what this is:" + object.getClass() +
		// ":" + object);
		// }
		// }

		final Gson gson = getGsonWithDateHandling();
		final DRPMovie[] fromJson = gson.fromJson(json, DRPMovie[].class);

		return Arrays.asList(fromJson);
	}

	public DRPMovie jsonToDRPMovie(final String json) {
		final Gson gson = getGsonWithDateHandling();
		final DRPMovie drpMovie = gson.fromJson(json, DRPMovie.class);
		return drpMovie;
		// final DRPMovie drpMovie = JSON.parseObject(json, DRPMovie.class);

		// final JSONDeserializer<DRPMovie> deserializer = new
		// JSONDeserializer<DRPMovie>();
		// return deserializer.deserialize(json);
	}

	private Gson getGsonWithDateHandling() {
		// final Gson gson = new
		// GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create();
		final Gson gson = new GsonBuilder().setDateFormat("MMM dd, yyyy HH:mm:ss a").create();
		return gson;
	}

	public String drpMovieToJson(final DRPMovie movie) {
		final Gson gson = getGsonWithDateHandling();
		final String json = gson.toJson(movie);
		return json;

		// final String json = JSON.toJSONString(movie);
		// final JSONSerializer serializer = new JSONSerializer();
		// return serializer.deepSerialize(movie);
	}

	public String drpMovieListToJson(final List<DRPMovie> listOfMovies) {
		final StringBuffer sb = new StringBuffer();

		sb.append("[");
		if (listOfMovies != null) {
			final Gson gson = getGsonWithDateHandling();
			boolean firstTimeThrough = true;
			for (DRPMovie movie : listOfMovies) {
				if (!firstTimeThrough) {
					sb.append(",");
				}

				final String json = gson.toJson(movie);
				sb.append(json);
				firstTimeThrough = false;
			}
		}

		sb.append("]");
		return sb.toString();
	}
}
