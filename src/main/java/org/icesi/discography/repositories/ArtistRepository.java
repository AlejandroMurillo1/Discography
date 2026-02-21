package org.icesi.discography.repositories;

import org.icesi.discography.models.Artist;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@Component
public class ArtistRepository {

    private final List<Artist> artists = new ArrayList<>();
    private long currentArtistId;

    public List<Artist> getAllArtists() {
        return artists;
    }

    public void createArtist(Artist artist) {
        currentArtistId ++;
        artist.setId(currentArtistId);
        artists.add(artist);
    }

    public Artist getArtisAndTracksByName(String artistName) {
        return artists.stream()
                .filter(artist -> artist.getName().equalsIgnoreCase(artistName))
                .findFirst().orElseThrow();
    }

    public void deleteArtist(Artist artist) {
        currentArtistId --;
        artist.setId(currentArtistId);
        artists.remove(artist);
    }




}
