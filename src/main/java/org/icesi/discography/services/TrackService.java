package org.icesi.discography.services;

import org.icesi.discography.models.Artist;
import org.icesi.discography.models.Track;
import org.icesi.discography.repositories.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackService {

    private final TrackRepository trackRepository;

    @Autowired
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
