package com.personal.myShuffler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchArtistsRequest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class SearchArtistService {
    private final SpotifySecurityContext securityContext;

    public ResponseEntity<List<String>> searchResults(String query){
        SearchArtistsRequest searchArtistsRequest = securityContext.getSpotifyApi().searchArtists(query).limit(3).build();
        List<String> searchResults = Collections.emptyList();
        try {
            final CompletableFuture<Paging<Artist>> pagingFuture = searchArtistsRequest.executeAsync();
            final Paging<Artist> artistPaging = pagingFuture.join();

            searchResults = Arrays.stream(artistPaging.getItems()).map(Artist::getName).toList();
        } catch (CancellationException e) {
            System.out.println("Async operation cancelled.");
        }
        return ResponseEntity.ok(searchResults);
    }
}
