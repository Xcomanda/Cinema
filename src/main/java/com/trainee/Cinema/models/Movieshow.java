package com.trainee.Cinema.models;

import java.util.Date;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Movieshow {
	private long id;
	private String title;
	private Set<Date> shows;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<Date> getShows() {
		return shows;
	}

	public void setShows(Set<Date> shows) {
		this.shows = shows;
	}

}