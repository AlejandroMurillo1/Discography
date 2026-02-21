package org.icesi.discography.services;

import org.icesi.discography.models.Artist;
import org.icesi.discography.repositories.ArtistRepository;

import java.util.List;

public class ArtistService {

    private ArtistRepository artistRepository;

    public List<Artist> getAllArtists() {
        return artistRepository.getAllArtists();
    }

    public void createArtist(long id, String name, String nationality) {

        Artist artists = new Artist();

        artists.setId(id);
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
