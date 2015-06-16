package com.djpedersen.movies.services;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OMDBMovieDataHelper {

	private static final Logger logger = Logger.getLogger(OMDBMovieDataHelper.class.getName());

	public static final String HUNT_FOR_RED_OCTOBER_IMDBID = "tt0099810";
	public static final String A_TEAM_IMDBID = "tt0429493";
	public static final Object STAR_TREK_IMDBID = "tt0796366";

	public static String getHuntForRedOctoberJSON() {
		return readResourceFile("hunt-for-red-october.json");
	}

	public static String getATeamJSON() {
		return readResourceFile("a-team.json");
	}

	private static String readResourceFile(final String filename) {
		final Path path = FileSystems.getDefault().getPath("./src/test/resources/omdb", filename);

		try {
			final List<String> readAllLines = Files.readAllLines(path, StandardCharsets.UTF_8);
			final StringBuffer sb = new StringBuffer();
			for (String line : readAllLines) {
				sb.append(line).append("\n");
			}

			return sb.toString();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "unalbe to read: " + filename, e);

			return null;
		}
	}

	public static String getStarTrekJSON() {
		return readResourceFile("star-trek.json");
	}
}
