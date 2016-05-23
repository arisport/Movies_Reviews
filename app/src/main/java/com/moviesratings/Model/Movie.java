package com.moviesratings.Model;

public class Movie {
	private String display_title, thumbnailUrl,rating,critics_pick,byline,headline,summary,publication_date,
	 opening_date,date_updated,type,url,suggested,type_multimedia,width,height;

	public Movie() {
	}

	public Movie(String title, String rating, String critics, String line, String headline, String summary, String publication_date
			, String opening_date, String date_updated, String type, String url, String suggested, String type_multimedia, String thumbnailUrl, String width, String height) {
		super();
		this.display_title = title;
		this.rating = rating;
		this.critics_pick = critics;
		this.byline = line;
		this.headline = headline;
		this.summary=summary;
		this.publication_date=publication_date;
		this.opening_date = opening_date;
		this.date_updated=date_updated;
		this.type=type;
		this.url=url;
		this.suggested=suggested;
		this.type_multimedia=type_multimedia;
		this.thumbnailUrl = thumbnailUrl;
		this.width = width;
		this.height = height;

	}

	public String getDisplay_title() {
		return display_title;
	}

	public void setDisplay_title(String display_title) {
		this.display_title = display_title;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getCritics_pick() {
		return critics_pick;
	}

	public void setCritics_pick(String critics_pick) {
		this.critics_pick = critics_pick;
	}

	public String getByline() {
		return byline;
	}

	public void setByline(String byline) {
		this.byline = byline;
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public String getPublication_date() {
		return publication_date;
	}

	public void setPublication_date(String publication_date) {
		this.publication_date = publication_date;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getOpening_date() {
		return opening_date;
	}

	public void setOpening_date(String opening_date) {
		this.opening_date = opening_date;
	}

	public String getDate_updated() {
		return date_updated;
	}

	public void setDate_updated(String date_updated) {
		this.date_updated = date_updated;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSuggested() {
		return suggested;
	}

	public void setSuggested(String suggested) {
		this.suggested = suggested;
	}

	public String getType_multimedia() {
		return type_multimedia;
	}

	public void setType_multimedia(String type_multimedia) {
		this.type_multimedia = type_multimedia;
	}


	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}
}
