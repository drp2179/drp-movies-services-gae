package com.djpedersen.movies.services;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DRPMovieDataHelper {

	private static final Logger logger = Logger.getLogger(DRPMovieDataHelper.class.getName());

	public static String getTestJSON() {
		return readResourceFile("test.json");
	}

	public static String getTestJSON2() {
		return readResourceFile("test2.json");
	}

	private static String readResourceFile(final String filename) {
		final Path path = FileSystems.getDefault().getPath("./src/test/resources/drp/", filename);

		try {
			final List<String> readAllLines = Files.readAllLines(path, StandardCharsets.UTF_8);
			final StringBuffer sb = new StringBuffer();
			for (String line : readAllLines) {
				// sb.append(line.trim()).append("\n");
				sb.append(line);
			}

			return sb.toString();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "unalbe to read: " + filename, e);

			return null;
		}
	}

}
