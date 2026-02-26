package org.icesi.discography.services;

import org.icesi.discography.models.Artist;
import org.icesi.discography.models.Track;
import org.icesi.discography.repositories.TrackRepository;

import java.util.List;

public class TrackService {

    private final TrackRepository trackRepository;

    // Constructor para inyectar la dependencia del repositorio
    public TrackService(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    public List<Track> getTracks() {
        return trackRepository.getAllTracks();
    }

    public void createTrack(long id, String title, String genre, int duration, String albumTitle, List<Artist> singers) {
        Track track = new Track(id,title,genre,duration,albumTitle,singers);
        trackRepository.saveTrack(track);
    }

    public void deleteTrack(long id){
        Track toDelete = getTrackById(id);
        trackRepository.deleteTrack(toDelete);
    }

    private Track getTrackById(long id){
        return trackRepository.getTrackById(id);
    }


}
