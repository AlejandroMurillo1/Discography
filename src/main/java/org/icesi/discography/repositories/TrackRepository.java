package org.icesi.discography.repositories;

import org.icesi.discography.models.Artist;
import org.icesi.discography.models.Track;

import java.util.ArrayList;
import java.util.List;

public class TrackRepository {

    List<Track> tracks = new ArrayList<>();
    private long currentArtistId;

    public List<Track> getAllTracks() {

        return tracks;
    }

    public void saveTrack(Track track) {

        currentArtistId ++;
        track.setId(currentArtistId);
        tracks.add(track);
    }


    public void deleteTrackById(Track track) {

        currentArtistId --;
        track.setId(currentArtistId);
        tracks.remove(track);
    }

}
