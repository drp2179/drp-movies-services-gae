package com.djpedersen.movies.services;

import java.util.Date;
import java.util.List;

public class DRPMovie {

	private String title;
	private Long year;
	private String rating;
	private Date released;
	private Long runtimeMinutes;
	private List<String> genres;
	private String director;
	private List<String> writers;
	private List<String> actors;
	private String plot;
	private String language;
	private String country;
	private String awards;
	private String posterURL;
	private Double metascore;
	private Double imdbRating;
	private String imdbId;
	private String type;
	private List<String> formats;

	public DRPMovie() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getYear() {
		return year;
	}

	public void setYear(Long year) {
		this.year = year;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public Date getReleased() {
		return released;
	}

	public void setReleased(Date released) {
		this.released = released;
	}

	public Long getRuntimeMinutes() {
		return runtimeMinutes;
	}

	public void setRuntimeMinutes(Long runtimeMinutes) {
		this.runtimeMinutes = runtimeMinutes;
	}

	public List<String> getGenres() {
		return genres;
	}

	public void setGenres(List<String> genres) {
		this.genres = genres;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public List<String> getActors() {
		return actors;
	}

	public void setActors(List<String> actors) {
		this.actors = actors;
	}

	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAwards() {
		return awards;
	}

	public void setAwards(String awards) {
		this.awards = awards;
	}

	public String getPosterURL() {
		return posterURL;
	}

	public void setPosterURL(String posterURL) {
		this.posterURL = posterURL;
	}

	public Double getMetascore() {
		return metascore;
	}

	public void setMetascore(Double metascore) {
		this.metascore = metascore;
	}

	public Double getImdbRating() {
		return imdbRating;
	}

	public void setImdbRating(Double imdbRating) {
		this.imdbRating = imdbRating;
	}

	public String getImdbId() {
		return imdbId;
	}

	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getFormats() {
		return formats;
	}

	public void setFormats(List<String> formats) {
		this.formats = formats;
	}

	public List<String> getWriters() {
		return writers;
	}

	public void setWriters(List<String> writers) {
		this.writers = writers;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();
		sb.append("DRPMovie(");
		sb.append("imdbid=").append(this.imdbId);
		sb.append(",title=").append(this.title);
		sb.append(",year=").append(this.year);
		sb.append(",rating=").append(this.rating);
		sb.append(",released=").append(this.released);
		sb.append(",runtimeMinutes=").append(this.runtimeMinutes);
		sb.append(",genres=").append(this.genres);
		if (genres == null) {
			sb.append(genres);
		} else {
			for (String genre : this.genres) {
				sb.append(genre).append(" ");
			}
		}
		sb.append(",director=").append(this.director);
		sb.append(",writers=");
		if (writers == null) {
			sb.append(writers);
		} else {
			for (String writer : this.writers) {
				sb.append(writer).append(" ");
			}
		}
		sb.append(",actors=");
		if (actors == null) {
			sb.append(actors);
		} else {
			for (String actor : this.actors) {
				sb.append(actor).append(" ");
			}
		}
		sb.append(",language=").append(this.language);
		sb.append(",country=").append(this.country);
		sb.append(",awards=").append(this.awards);
		sb.append(",posterUrl=").append(this.posterURL);
		sb.append(",metascore=").append(this.metascore);
		sb.append(",imdbRating=").append(this.imdbRating);
		// sb.append(",imdbVotes=").append(this.imdbVotes);
		sb.append(",type=").append(this.type);
		sb.append(",plot=").append(this.plot);
		sb.append(")");
		return sb.toString();
	}
}
