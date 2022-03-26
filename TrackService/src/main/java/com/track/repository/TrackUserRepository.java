package com.track.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.track.model.Track;
import com.track.model.TrackUser;

@Repository
public interface TrackUserRepository extends MongoRepository<TrackUser, String> {

	public TrackUser findByUserName(String userId);

}
