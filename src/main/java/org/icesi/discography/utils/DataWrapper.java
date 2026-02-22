package org.icesi.discography.utils;

import org.icesi.discography.models.Artist;
import org.icesi.discography.models.Track;

import java.util.List;

public class DataWrapper {
    private List<Artist> artistList;

    public List<Artist> getArtistList() {
        return artistList;
    }

    public void setArtistList(List<Artist> artistList) {
        this.artistList = artistList;
    }
}
