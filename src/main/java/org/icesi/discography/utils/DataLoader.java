package org.icesi.discography.utils;

import com.google.gson.Gson;
import jakarta.annotation.PostConstruct;
import org.icesi.discography.models.Artist;
import org.icesi.discography.models.Track;
import org.icesi.discography.repositories.ArtistRepository;
import org.icesi.discography.repositories.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;

@Component
public class DataLoader {
    private final ArtistRepository artistRepository;
    private final TrackRepository trackRepository;
    private final Gson gson = new Gson();

    @Autowired
    public DataLoader(ArtistRepository ar, TrackRepository tr){
        this.artistRepository = ar;
        this.trackRepository = tr;
    }

    @PostConstruct
    public void init(){
        boolean success = loadData();
        if(success){
            System.out.println("Operación de precarga exitosa.");
        }
    }

    public boolean loadData(){
        String jsonData = "data.json";
        boolean result;

        try(InputStreamReader reader = new InputStreamReader(
                Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(jsonData)),StandardCharsets.UTF_8)){

            DataWrapper data = gson.fromJson(reader,DataWrapper.class);

            for(Artist a: data.getArtistList()){
                if(a.getTracks() == null) a.setTracks(new ArrayList<>());

                for(Track t: a.getTracks()){
                    if(t.getSingers() == null) t.setSingers(new ArrayList<>());
                    if(!t.getSingers().contains(a)) t.getSingers().add(a);

                    trackRepository.saveTrack(t);
                }

                artistRepository.saveArtist(a);
            }
            result = true;

        }catch(IOException ioException){
            ioException.printStackTrace();
            result = false;
        }

        return result;
    }

    public ArtistRepository getArtistRepository() {
        return artistRepository;
    }

    public TrackRepository getTrackRepository() {
        return trackRepository;
    }
}
