package org.icesi.discography.models;

import java.util.List;

public class Artist {
    private long id;
    private String name;
    private String nationality;
    private List<Track> tracks;

    public Artist(long id, String name, String nationality, List<Track> tracks) {
        this.id = id;
        this.name = name;
        this.nationality = nationality;
        this.tracks = tracks;
    }

    public Artist() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    @Override
    public String toString() {
        return "Artist{id=" + id + ", name='" + name + "', nationality='" + nationality + "'}";
    }
}
