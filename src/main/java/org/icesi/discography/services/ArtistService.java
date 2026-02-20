package org.icesi.discography.services;

import org.icesi.discography.models.Artist;
import org.icesi.discography.models.Track;
import org.icesi.discography.repositories.ArtistRepository;

import java.util.List;

public class ArtistService {

    ArtistRepository artistRepository;


    public List<Artist> getAllArtists() {
        return artistRepository.getAllArtists();
    }

    public void createArtist(Long id, String name, String nacionality) {

        Artist artists = new Artist();

        artists.setId(id);
        artists.setName(name);
        artists.setNationality(nacionality);

        artistRepository.createArtist(artists);
    }

    public void addTrackToArtist(Long idUser, Long idTrack) {



    }

}
