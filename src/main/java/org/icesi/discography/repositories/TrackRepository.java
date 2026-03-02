package org.icesi.discography.repositories;

import org.icesi.discography.models.Artist;
import org.icesi.discography.models.Track;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TrackRepository {

    private final List<Track> tracks = new ArrayList<>();
    private long currentTrackId = 0;

    public List<Track> getAllTracks() {
        return tracks;
    }

    public void saveTrack(Track track) {
        currentTrackId ++;
        track.setId(currentTrackId);
        tracks.add(track);
    }

    public Track getTrackById(long id){
        return tracks.stream()
                .filter(tr -> tr.getId() == id)
                .findFirst()
                .orElseThrow();
    }

    public void deleteTrack(Track toDelete) {
        toDelete.getSingers().forEach(a -> a.getTracks().remove(toDelete));
        toDelete.getSingers().clear();
        tracks.remove(toDelete);
    }

    public void assignArtistsToTrack(List<Artist> artists, Track track){
        List<Artist> trackArtists = track.getSingers();
        List<Artist> updated = new ArrayList<>(trackArtists);

        updated.addAll(artists);
        track.setSingers(updated);
    }

}
