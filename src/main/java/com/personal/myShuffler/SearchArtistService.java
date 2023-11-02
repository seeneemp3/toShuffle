package com.personal.myShuffler;

import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchArtistsRequest;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchArtistService {
    private final SpotifySecurityContext securityContext;

    public ResponseEntity<List<String>> searchResults(String query){
        List<String> searchResults = Collections.emptyList();
        try{
            SearchArtistsRequest searchArtistsRequest = securityContext.getSpotifyApi().searchArtists(query).limit(3).build();
            final Paging<Artist> artistPaging = searchArtistsRequest.execute();
            searchResults = Arrays.stream(artistPaging.getItems()).map(Artist::getName).toList();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println(searchResults);
        return ResponseEntity.ok(searchResults);
    }


}
