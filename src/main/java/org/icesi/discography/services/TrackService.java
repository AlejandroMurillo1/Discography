package org.icesi.discography.services;

import org.icesi.discography.models.Artist;
import org.icesi.discography.models.Track;
import org.icesi.discography.repositories.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
public class TrackService {

    private TrackRepository trackRepository;

    @Autowired
    public TrackService(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    public List<Track> getTracks() {
        return trackRepository.getAllTracks();
    }

    public void createTrack(long id, String title, String genre, int duration, String albumTitle, List<Artist> singers) {
        Track track = new Track(id,title,genre,Duration.ofSeconds(duration),albumTitle,singers);
        trackRepository.saveTrack(track);
    }

    public void deleteTrack(long id){

    }


}
