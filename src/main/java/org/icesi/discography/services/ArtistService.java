package org.icesi.discography.services;

import org.icesi.discography.models.Artist;
import org.icesi.discography.repositories.ArtistRepository;

import java.util.List;

public class ArtistService {

    private final ArtistRepository artistRepository;

    // Constructor para inyectar la dependencia del repositorio
    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public List<Artist> getAllArtists() {
        return artistRepository.getAllArtists();
    }

    public Artist createArtist(String name, String nationality) {

        Artist artist = new Artist();

        artist.setName(name);
        artist.setNationality(nationality);

        artistRepository.saveArtist(artist);

        return artist;
    }

    public void addTrackToArtist(long idUser, long idTrack) {
        // no se toca
    }

    public List<Artist> searchArtists(String searchTerm) {
        return artistRepository.searchArtistsByName(searchTerm);
    }

    public void deleteArtistById(long id) {
        Artist artist = getArtistById(id);
        artistRepository.deleteArtist(artist);
    }

    private Artist getArtistById(long id) {
        return artistRepository.getArtistById(id).orElseThrow(() -> new IllegalArgumentException("Artista no encontrado con ID: " + id));
    }

    public Artist getArtistWithTracks(String name){
        return artistRepository.getArtisAndTracksByName(name);
    }

}
