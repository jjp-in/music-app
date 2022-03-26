package com.track.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.track.exception.TrackAlreadyExistsException;
import com.track.exception.TrackNotFoundException;
import com.track.model.Track;
import com.track.model.TrackUser;
import com.track.repository.TrackUserRepository;

@Service
public class TrackService {

	@Autowired
	TrackUserRepository trackUserRepository;
	
	
	public TrackService(TrackUserRepository trackUserRepository) {
		super();
		this.trackUserRepository = trackUserRepository;
	}

	public boolean saveToFavourites(String userName, Track track) throws TrackAlreadyExistsException {

		TrackUser trackUser = null;
		TrackUser trackUserObj = new TrackUser();


		TrackUser n = trackUserRepository.findByUserName(userName);//already existing user? get tracks
		System.out.println("find by user name"+n);


		if (n != null) {
			List<Track> trackList = n.getTrackListByUser();
			System.out.println("chedk alredy existse"+trackList);

			for (Track checkTrack : trackList) {
				if (checkTrack.getId().equals(track.getId())) {
					throw new TrackAlreadyExistsException("Track Already Exists");

				}

			}
			n.getTrackListByUser().add(track);
			trackUser = trackUserRepository.save(n);
		} else {
			List<Track> trackList = new ArrayList(); // create a new arraylist
			trackList.add(track);
			trackUserObj.setUserNamed(userName);
			trackUserObj.setTrackListByUser(trackList);
			trackUser = trackUserRepository.save(trackUserObj);
		}

		if (trackUser != null) {
			return true;
		} else {
			return false;
		}

	}

	public List<Track> generateListData(String subStr) throws JsonMappingException, JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		List<Track> listTracks = mapper.readValue(subStr, new TypeReference<List<Track>>() {
		});

		return listTracks;

	}

	public List<Track> getAllFav(String userId) throws TrackNotFoundException {
		TrackUser user = trackUserRepository.findByUserName(userId);

		if (user != null) {
			return user.getTrackListByUser();
		} else {
			throw new TrackNotFoundException("Favourite TrackList Not found");
		}
	}

}
