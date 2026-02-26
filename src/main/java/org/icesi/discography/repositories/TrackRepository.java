package org.icesi.discography.repositories;

import org.icesi.discography.models.Track;

import java.util.ArrayList;
import java.util.List;


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

        // Por cada uno de los cantantes involucrados, se les quita esa canción
        toDelete.getSingers().forEach(a -> a.getTracks().remove(toDelete));

        //Liberar recursos de lista
        toDelete.getSingers().clear();

        //Eliminar definitivamente la canción
        tracks.remove(toDelete);
    }

}
