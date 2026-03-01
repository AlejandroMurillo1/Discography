package org.icesi.discography.services;

import org.icesi.discography.models.Artist;
import org.icesi.discography.models.Track;
import org.icesi.discography.repositories.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Component
public class TrackService {

    private final TrackRepository trackRepository;
    private final ArtistService artistService;

    @Autowired
    public TrackService(TrackRepository trackRepository, ArtistService artistService) {
        this.trackRepository = trackRepository;
        this.artistService = artistService;
    }

    public List<Track> getTracks() {
        return trackRepository.getAllTracks();
    }

    public Track createTrack(long id, String title, String genre, int duration, String albumTitle, List<Artist> singers) {
        Track track = new Track(id,title,genre,duration,albumTitle,singers);
        trackRepository.saveTrack(track);
        return track;
    }

    public void deleteTrack(long id){
        Track toDelete = getTrackById(id);
        trackRepository.deleteTrack(toDelete);
    }

    private Track getTrackById(long id){
        return trackRepository.getTrackById(id);
    }

    public void assignArtistsToTrack(List<Long> artistIds, long trackId){
        Track toUpdate = getTrackById(trackId);
        List<Artist> artists = new ArrayList<>();

        for(long id: artistIds){
            Artist artist = artistService.getArtistById(id);
            artists.add(artist);
        }

        trackRepository.assignArtistsToTrack(artists,toUpdate);
    }

}
