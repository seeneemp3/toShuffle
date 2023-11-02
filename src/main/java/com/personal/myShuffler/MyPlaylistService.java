package com.personal.myShuffler;

import com.neovisionaries.i18n.CountryCode;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;
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

@Service
@RequiredArgsConstructor
public class MyPlaylistService {
    private final SpotifySecurityContext securityContext;
    private final MyUserService userService;
    public MyPlaylist createMyPlaylist(String artist1, String artist2, String artist3) throws IOException, ParseException, SpotifyWebApiException {
        SpotifyApi api = securityContext.getSpotifyApi();
        String userId = userService.getUser().getUserId();
        String playlistName = String.format("Top 5 from %s, %s and %s", artist1, artist2, artist3);

        Playlist playlist = api.createPlaylist(userId, playlistName).build().execute();

        List <String> artistIds = List.of(search(artist1), search(artist3), search(artist3));

        List<String> trackUrls = new ArrayList<>();

        for(String artistId : artistIds){
            final Track[] tracks = api.getArtistsTopTracks(artistId, CountryCode.US).build().execute();
            Arrays.stream(tracks).map(Track::getUri).limit(5).forEach(trackUrls::add);
        }

        String [] trackUrlsArr = trackUrls.toArray(new String[15]);
        api.addItemsToPlaylist(playlist.getId(), trackUrlsArr).build().execute();

       return new MyPlaylist(playlist.getId(), playlist.getName(), playlist.getOwner().getId());
    }
    private String search(String artist) throws IOException, ParseException, SpotifyWebApiException {
        SearchArtistsRequest searchArtistsRequest = securityContext.getSpotifyApi().searchArtists(artist).limit(1).build();
        final Paging<Artist> artistPaging = searchArtistsRequest.execute();
        return Arrays.stream(artistPaging.getItems()).map(Artist::getId).findAny().orElse("");
    }
}
