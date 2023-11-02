package com.personal.myShuffler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final SearchArtistService searchArtistService;
    private final MyPlaylistService playlistService;


    @GetMapping("/home")
    public String home(){

        return "artists";
    }

    @GetMapping("/search")
    public ResponseEntity<List<String>> searchArtists(@RequestParam String query) {
        return searchArtistService.searchResults(query);
    }

    @GetMapping("/processSearch")
    public String createPlaylist(@RequestParam String searchInput1, @RequestParam String searchInput2,
                                 @RequestParam String searchInput3) {
            playlistService.createMyPlaylist(searchInput1, searchInput2, searchInput3);
        return "createPlaylist";
    }


}
