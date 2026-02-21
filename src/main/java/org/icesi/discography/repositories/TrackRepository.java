package org.icesi.discography.repositories;

import org.icesi.discography.models.Artist;
import org.icesi.discography.models.Track;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TrackRepository {

    //TODO: Concretar la lögica de creación de ID's.
    private final List<Track> tracks = new ArrayList<>();
    private long currentArtistId = 0;

    public List<Track> getAllTracks() {
        return tracks;
    }

    public void saveTrack(Track track) {

        currentArtistId ++;
        track.setId(currentArtistId);
        tracks.add(track);
    }


    public void deleteTrackById(long id) {

        //Lambda pa obtener la canción por el id
        Track toDelete = tracks.stream()
                .filter(tr -> tr.getId() == id)
                .findFirst()
                .orElseThrow();

        // Por cada uno de los cantantes involucrados, se les quita esa canción
        toDelete.getSingers().forEach(a -> a.getTracks().remove(toDelete));

        //Liberar recursos de lista
        toDelete.getSingers().clear();

        //Eliminar definitivamente la canción
        tracks.remove(toDelete);
    }

}
