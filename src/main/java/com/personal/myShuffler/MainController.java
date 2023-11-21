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
        return "main";
    }

    @GetMapping("/search")
    public ResponseEntity<List<String>> searchArtists(@RequestParam String query) {
        return searchArtistService.searchResults(query);
    }

    @GetMapping("/showPlaylist")
    public ResponseEntity<List<Track>> showPlaylist(@RequestParam String searchInput1, @RequestParam String searchInput2,
                                                    @RequestParam String searchInput3) throws IOException, ParseException, SpotifyWebApiException {
        return ResponseEntity.ok(playlistService.showMyPlaylist(searchInput1, searchInput2, searchInput3));
    }

    @GetMapping("/createPlaylist")
    public ResponseEntity<String> createPlaylist() throws IOException, ParseException, SpotifyWebApiException {
        log.error("playlist created");
        return ResponseEntity.ok(playlistService.createMyPlaylist());
    }


}
