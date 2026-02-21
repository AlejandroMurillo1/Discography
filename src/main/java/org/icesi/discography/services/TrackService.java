package org.icesi.discography.services;

import org.icesi.discography.models.Artist;
import org.icesi.discography.models.Track;
import org.icesi.discography.repositories.TrackRepository;

import java.time.Duration;
import java.util.List;

public class TrackService {

    private TrackRepository trackRepository;

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
