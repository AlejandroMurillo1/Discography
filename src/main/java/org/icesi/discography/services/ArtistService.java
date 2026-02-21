package org.icesi.discography.services;

import org.icesi.discography.models.Artist;
import org.icesi.discography.repositories.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArtistService {

    private ArtistRepository artistRepository;

    @Autowired
    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }


    public List<Artist> getAllArtists() {
        return artistRepository.getAllArtists();
    }

    public void createArtist( String name, String nationality) {

        Artist artists = new Artist();

        artists.setName(name);
        artists.setNationality(nationality);

        artistRepository.createArtist(artists);
    }

    public void addTrackToArtist(long idUser, long idTrack) {



    }

    public Artist getArtistWithTracks(String name){
        return artistRepository.getArtisAndTracksByName(name);
    }

}
