package com.djpedersen.movies.services;

public class OMDBMovie {

	private String title;
	private String imdbId;

	private String year;

	private String rated;

	private String released;
	private String runtime;
	private String genre;
	private String director;

	private String writer;
	private String actors;

	private String plot;

	private String language;

	private String country;
	private String awards;

	private String poster;

	private String metascore;

	private String imdbRating;
	private String imdbVotes;

	private String type;

	private String response;
	private String error;

	public OMDBMovie() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImdbId() {
		return imdbId;
	}

	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getRated() {
		return rated;
	}

	public void setRated(String rated) {
		this.rated = rated;
	}

	public String getReleased() {
		return released;
	}

	public void setReleased(String released) {
		this.released = released;
	}

	public String getRuntime() {
		return runtime;
	}

	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getActors() {
		return actors;
	}

	public void setActors(String actors) {
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

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public String getMetascore() {
		return metascore;
	}

	public void setMetascore(String metascore) {
		this.metascore = metascore;
	}

	public String getImdbRating() {
		return imdbRating;
	}

	public void setImdbRating(String imdbRating) {
		this.imdbRating = imdbRating;
	}

	public String getImdbVotes() {
		return imdbVotes;
	}

	public void setImdbVotes(String imdbVotes) {
		this.imdbVotes = imdbVotes;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();
		sb.append("OMDBMovie(");
		sb.append("imdbId=").append(this.imdbId);
		sb.append(",title=").append(this.title);
		sb.append(",response=").append(this.response);
		sb.append(",error=").append(this.error);
		sb.append(",year=").append(this.year);
		sb.append(",rated=").append(this.rated);
		sb.append(",released=").append(this.released);
		sb.append(",runtime=").append(this.runtime);
		sb.append(",genre=").append(this.genre);
		sb.append(",director=").append(this.director);
		sb.append(",writer=").append(this.writer);
		sb.append(",actors=").append(this.actors);
		sb.append(",language=").append(this.language);
		sb.append(",country=").append(this.country);
		sb.append(",awards=").append(this.awards);
		sb.append(",poster=").append(this.poster);
		sb.append(",metascore=").append(this.metascore);
		sb.append(",imdbRating=").append(this.imdbRating);
		sb.append(",imdbVotes=").append(this.imdbVotes);
		sb.append(",type=").append(this.type);
		sb.append(",plot=").append(this.plot);
		sb.append(")");
		return sb.toString();
	}
}
