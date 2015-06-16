package com.djpedersen.movies.services;

//import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

public class OMDBMovieConverter {

	public OMDBMovie jsonToOMDBMovie(final String json) {
		// final OMDBMovie omdbMovie = JSON.parseObject(jsonMovie,
		// OMDBMovie.class);
		final Gson gson = new Gson();
		final String fixedJson = fixUpFieldName(json);
		final OMDBMovie omdbMovie = gson.fromJson(fixedJson, OMDBMovie.class);
		return omdbMovie;
	}

	public String omdbMovieToJson(final OMDBMovie movie) {
		final Gson gson = new Gson();
		final String json = gson.toJson(movie);
		return json;
	}

	private String fixUpFieldName(final String json) {
		String fixed = json.replaceFirst("\"Title\":", "\"title\":");
		fixed = fixed.replaceFirst("\"Year\":", "\"year\":");
		fixed = fixed.replaceFirst("\"imdbID\":", "\"imdbId\":");
		fixed = fixed.replaceFirst("\"Rated\":", "\"rated\":");
		fixed = fixed.replaceFirst("\"Released\":", "\"released\":");
		fixed = fixed.replaceFirst("\"Runtime\":", "\"runtime\":");
		fixed = fixed.replaceFirst("\"Genre\":", "\"genre\":");
		fixed = fixed.replaceFirst("\"Director\":", "\"director\":");
		fixed = fixed.replaceFirst("\"Writer\":", "\"writer\":");
		fixed = fixed.replaceFirst("\"Actors\":", "\"actors\":");
		fixed = fixed.replaceFirst("\"Plot\":", "\"plot\":");
		fixed = fixed.replaceFirst("\"Language\":", "\"language\":");
		fixed = fixed.replaceFirst("\"Country\":", "\"country\":");
		fixed = fixed.replaceFirst("\"Awards\":", "\"awards\":");
		fixed = fixed.replaceFirst("\"Poster\":", "\"poster\":");
		fixed = fixed.replaceFirst("\"Metascore\":", "\"metascore\":");
		fixed = fixed.replaceFirst("\"Type\":", "\"type\":");
		fixed = fixed.replaceFirst("\"Response\":", "\"response\":");
		fixed = fixed.replaceFirst("\"Error\":", "\"error\":");

		return fixed;
	}

}
