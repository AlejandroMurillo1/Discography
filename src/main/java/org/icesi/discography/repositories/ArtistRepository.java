package org.icesi.discography.repositories;

import org.icesi.discography.models.Artist;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class ArtistRepository {

    private final List<Artist> artists = new ArrayList<>();
    private long currentArtistId = 0;

    public ArtistRepository() {}

    public List<Artist> getAllArtists() {
        return artists;
    }

    public void saveArtist(Artist artist) {
        currentArtistId++;
        artist.setId(currentArtistId);
        artists.add(artist);
    }

    public Artist getArtisAndTracksByName(String artistName) {
        return artists.stream()
                .filter(artist -> artist.getName().equalsIgnoreCase(artistName))
                .findFirst().orElseThrow();
    }

    public List<Artist> searchArtistsByName(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return new ArrayList<>(); // Devuelve lista vacía si no hay término
        }

        String searchTermLower = searchTerm.toLowerCase();

        return artists.stream()
                .filter(artist -> artist.getName().toLowerCase().contains(searchTermLower))
                .collect(Collectors.toList());
    }

    public Optional<Artist> getArtistById(long id) {
        return artists.stream()
                .filter(artist -> artist.getId() == id)
                .findFirst();
    }


    public void deleteArtist(Artist artist) {
        artists.remove(artist);

    }

}
