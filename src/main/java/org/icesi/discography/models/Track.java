package org.icesi.discography.models;

import java.time.Duration;
import java.util.List;

public class Track {
    private long id;
    private String title;
    private String genre;
    private Duration duration;
    private String albumTitle;
    private List<Artist> singers;

    public Track(long id, String title, String genre, Duration duration, String albumTitle, List<Artist> singers) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.albumTitle = albumTitle;
        this.singers = singers;
    }

    public Track() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public List<Artist> getSingers() {
        return singers;
    }

    public void setSingers(List<Artist> singers) {
        this.singers = singers;
    }
}
