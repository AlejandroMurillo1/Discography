package org.icesi.discography.utils;

import com.google.gson.Gson;
import org.icesi.discography.models.Artist;
import org.icesi.discography.models.Track;
import org.icesi.discography.repositories.ArtistRepository;
import org.icesi.discography.repositories.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

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

    public boolean loadData(){
        String jsonData = "data.json";

        List<Artist> artists;
        List<Track> tracks;

        try(InputStream is = getClass().getClassLoader().getResourceAsStream(jsonData)){
            if(is == null){
                System.err.println("No se encontró el archivo: "+jsonData);
                return false;
            }

            InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
            DataWrapper data = gson.fromJson(reader,DataWrapper.class);

            //TODO: Completar esta lógica.
            for(Artist a: data.getArtistList()){
                artistRepository.saveArtist(a);
            }

        }catch(IOException ioException){

        }




        return true;
    }

    public ArtistRepository getArtistRepository() {
        return artistRepository;
    }

    public TrackRepository getTrackRepository() {
        return trackRepository;
    }
}
