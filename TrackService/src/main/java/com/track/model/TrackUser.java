package com.track.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class TrackUser {
	@Id
	private String userName;
	private List<Track> trackListByUser;

	public TrackUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TrackUser(String userName, List<Track> trackListByUser) {
		super();
		this.userName = userName;
		this.trackListByUser = trackListByUser;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserNamed(String userName) {
		this.userName = userName;
	}

	public List<Track> getTrackListByUser() {
		return trackListByUser;
	}

	public void setTrackListByUser(List<Track> trackListByUser) {
		this.trackListByUser = trackListByUser;
	}

	@Override
	public String toString() {
		return "TrackUser [userId=" + userName + ", trackListByUser=" + trackListByUser + "]";
	}

}
