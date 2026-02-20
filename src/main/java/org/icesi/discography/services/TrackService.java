package org.icesi.discography.services;

import org.icesi.discography.models.Artist;
import org.icesi.discography.models.Track;
import org.icesi.discography.repositories.TrackRepository;

import java.time.Duration;
import java.util.List;

public class TrackService {

    TrackRepository trackRepository;
    Track track;
    public List<Track> getTracks() {

        return trackRepository.getAllTracks();
    }


    public void createTrack(Long id, String title, String genre, Duration duration, String albumTitle, List<Artist> singers) {

        track.setId(id);
        track.setTitle(title);
        track.setGenre(genre);
        track.setDuration(duration);
        track.setAlbumTitle(albumTitle);
        track.setSingers(singers);
        trackRepository.saveTrack(track);

    }


}
