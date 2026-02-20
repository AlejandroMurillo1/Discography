package org.icesi.discography.repositories;

import org.icesi.discography.models.Artist;
import java.util.List;
import java.util.ArrayList;

public class ArtistRepository {

    List<Artist> artists = new ArrayList<>();
    private long currentArtistId;

    public List<Artist> getAllArtists() {

        return artists;
    }

    public void createArtist(Artist artist) {

        currentArtistId ++;
        artist.setId(currentArtistId);
        artists.add(artist);
    }

    public void getArtisAndTracksByName(String artistName) {


    }

    public void deleteArtist(Artist artist) {

        currentArtistId --;
        artist.setId(currentArtistId);
        artists.remove(artist);
    }




}
