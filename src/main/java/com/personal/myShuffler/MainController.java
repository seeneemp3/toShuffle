package com.personal.myShuffler;

import com.neovisionaries.i18n.CountryCode;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchArtistsRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final OAuth2AuthorizedClientService authorizedClientService;
    private static SpotifyApi spotifyApi;
    private static SearchArtistsRequest searchArtistsRequest;
    private static String userId;
    private final SpotifyApiService service;


    @GetMapping("/home")
    public String home(){
        service.get();
        System.out.println("ok");

        return "artists";
    }

    @GetMapping("/search")
    public ResponseEntity<List<String>> searchArtists(OAuth2AuthenticationToken token, @RequestParam String query) {
//        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
//                token.getAuthorizedClientRegistrationId(),
//                token.getName());
        List<String> searchResults = null;
        try{
            searchArtistsRequest = spotifyApi.searchArtists(query).build();
            final Paging<Artist> artistPaging = searchArtistsRequest.execute();
            searchResults = Arrays.stream(artistPaging.getItems()).limit(3).map(Artist::getName).toList();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return ResponseEntity.ok(searchResults);
    }

    @GetMapping("/processSearch")
    public String createPlaylist(@RequestParam String searchInput1, @RequestParam String searchInput2,
                                 @RequestParam String searchInput3) {
        try {
        String playlistName = String.format("Top 5 from %s, %s and %s", searchInput1, searchInput2, searchInput3);
        userId = spotifyApi.getCurrentUsersProfile().build().execute().getId();

        List<String> artistIds = new ArrayList<>();
        artistIds.add(getId(searchInput1));
        artistIds.add(getId(searchInput2));
        artistIds.add(getId(searchInput3));

        List<String> trackUrls = new ArrayList<>();

        for(String artistId : artistIds){
            final Track[] tracks = spotifyApi.getArtistsTopTracks(artistId, CountryCode.US).build().execute();
            Arrays.stream(tracks).map(Track::getUri).limit(5).forEach(trackUrls::add);
        }
            Playlist playlist = spotifyApi.createPlaylist(userId, playlistName).build().execute();
            String [] trackUrlsArr = trackUrls.toArray(new String[15]);

            spotifyApi.addItemsToPlaylist(playlist.getId(), trackUrlsArr).build().execute();



    } catch (IOException | SpotifyWebApiException | ParseException e) {
        System.out.println("Error: " + e.getMessage());
    }

        return "createPlaylist";
    }
    private String getId(String searchInput){
        String res = "";
        searchArtistsRequest = spotifyApi.searchArtists(searchInput).build();
        try {
            final Paging<Artist> artistPaging = searchArtistsRequest.execute();
           res = Arrays.stream(artistPaging.getItems()).limit(1).map(Artist::getId).findAny().orElse("");
        } catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return res;
    }

}
