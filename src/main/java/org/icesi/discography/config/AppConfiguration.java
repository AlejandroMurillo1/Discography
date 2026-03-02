package org.icesi.discography.config;

import org.icesi.discography.repositories.ArtistRepository;
import org.icesi.discography.repositories.TrackRepository;
import org.icesi.discography.services.ArtistService;
import org.icesi.discography.services.TrackService;
import org.icesi.discography.utils.DataLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public ArtistService artistService(){
        return new ArtistService(artistRepository());
    }

    @Bean
    public TrackService trackService(){
        return new TrackService(trackRepository(),artistService());
    }

    @Bean
    public ArtistRepository artistRepository(){
        return new ArtistRepository();
    }

    @Bean
    public TrackRepository trackRepository(){
        return new TrackRepository();
    }

    @Bean
    public DataLoader dataLoader(){return new DataLoader(artistRepository(),trackRepository());}
}