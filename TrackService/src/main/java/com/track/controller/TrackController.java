package com.track.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.bson.json.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.track.exception.TrackAlreadyExistsException;
import com.track.exception.TrackNotFoundException;
import com.track.model.Track;
import com.track.service.TrackService;

@RestController
public class TrackController {

	@Autowired
	TrackService trackService;

	public TrackController(TrackService trackService) {
		super();
		this.trackService = trackService;
	}

	@GetMapping("/tracks/getAllTracks")
	public ResponseEntity<?> getAllTracks() throws JsonMappingException, JsonProcessingException {

//	     String lastFMURL = "https://ws.audioscrobbler.com/2.0/?method=geo.gettoptracks&format=json&country=india&api_key=c158a4a980266b5977fca2e3dcd9d5ec&format=json";
		String napsterURI = "https://api.napster.com/v2.1/tracks/top?apikey=MzFhN2EzYzYtM2FhMi00ZmYzLWJiZmItZGU0YzFjZjAzMTUw";
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(napsterURI, String.class);
		String subStr = result.substring(10, result.length() - 1);

		List<Track> tracksList = trackService.generateListData(subStr);

		return new ResponseEntity<List<Track>>(tracksList, HttpStatus.OK);

	}

	@PostMapping("/tracks/addToFav/{userid}")
	public ResponseEntity<?> saveallFav(@RequestBody Track track, @PathVariable String userid) {
		try {
			trackService.saveToFavourites(userid, track);
			return new ResponseEntity<String>("Track Added to Favourites", HttpStatus.OK);

		} catch (TrackAlreadyExistsException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);

		}

	}

	@GetMapping("/tracks/viewFav/{userid}")
	public ResponseEntity<?> viewallFav(@PathVariable String userid) {
		List<Track> favTrackList;
		try {
			favTrackList = trackService.getAllFav(userid);

			return new ResponseEntity<List<Track>>(favTrackList, HttpStatus.OK);

		} catch (TrackNotFoundException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);

		}

	}

}
