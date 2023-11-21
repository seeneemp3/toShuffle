package com.personal.myShuffler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MainController {
    private final SearchArtistService searchArtistService;
    private final MyPlaylistService playlistService;


    @GetMapping("/home")
    public String home(){
        log.info("Accessing home page");
        return "main";
    }

    @GetMapping("/search")
    public ResponseEntity<List<String>> searchArtists(@RequestParam String query) {
        log.info("Performing search with query: {}", query);
        try {
            ResponseEntity<List<String>> response = searchArtistService.searchResults(query);
            log.info("Search completed");
            return response;
        } catch (Exception e) {
            log.error("Error during search: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/showPlaylist")
    public ResponseEntity<List<Track>> showPlaylist(@RequestParam String searchInput1, @RequestParam String searchInput2,
                                                    @RequestParam String searchInput3) {
        log.info("Showing playlist for inputs: {}, {}, {}", searchInput1, searchInput2, searchInput3);
        try {
            ResponseEntity<List<Track>> response = ResponseEntity.ok(playlistService.showMyPlaylist(searchInput1, searchInput2, searchInput3));
            log.info("Playlist displayed");
            return response;
        } catch (IOException | ParseException | SpotifyWebApiException e) {
            log.error("Error displaying playlist: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/createPlaylist")
    public ResponseEntity<String> createPlaylist() {
        log.info("Creating playlist");
        try {
            String response = playlistService.createMyPlaylist();
            log.info("Playlist created");
            return ResponseEntity.ok(response);
        } catch (IOException | ParseException | SpotifyWebApiException e) {
            log.error("Error creating playlist: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }


}
