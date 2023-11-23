import com.neovisionaries.i18n.CountryCode;
import com.personal.myShuffler.model.MyUser;
import com.personal.myShuffler.repository.PlaylistRepository;
import com.personal.myShuffler.security.SpotifySecurityContext;
import com.personal.myShuffler.service.MyPlaylistService;
import com.personal.myShuffler.service.MyUserService;
import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.mockito.stubbing.Answer;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.special.SnapshotResult;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.artists.GetArtistsTopTracksRequest;
import se.michaelthelin.spotify.requests.data.playlists.AddItemsToPlaylistRequest;
import se.michaelthelin.spotify.requests.data.playlists.CreatePlaylistRequest;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchArtistsRequest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
public class MyPlaylistServiceTest {
    @Mock
    private SpotifySecurityContext securityContext;
    @Mock
    private MyUserService userService;
    @Mock
    private PlaylistRepository repository;
    @Mock
    private SpotifyApi spotifyApi;
    @InjectMocks
    private MyPlaylistService service;

    @BeforeEach
    public void setup() {
        when(securityContext.getSpotifyApi()).thenReturn(spotifyApi);
    }

    @Test
    void testShowMyPlaylist() throws IOException, ParseException, SpotifyWebApiException {
        Track mockTrack = mock(Track.class);
        Track[] tracks = new Track[]{mockTrack, mockTrack, mockTrack, mockTrack, mockTrack};

        SearchArtistsRequest searchArtistsRequestMock = mock(SearchArtistsRequest.class);
        SearchArtistsRequest.Builder builderMock2 = mock(SearchArtistsRequest.Builder.class);
        Paging<Artist> artistPagingMock = mock(Paging.class);

        when(spotifyApi.searchArtists(anyString())).thenReturn(builderMock2);
        when(builderMock2.limit(anyInt())).thenReturn(builderMock2);
        when(builderMock2.build()).thenReturn(searchArtistsRequestMock);

        Artist artistMock = mock(Artist.class);
        when(artistMock.getId()).thenReturn("someArtistId");

        Artist[] artists = new Artist[]{artistMock};
        when(artistPagingMock.getItems()).thenReturn(artists);
        when(searchArtistsRequestMock.execute()).thenReturn(artistPagingMock);

        GetArtistsTopTracksRequest.Builder builderMock = mock(GetArtistsTopTracksRequest.Builder.class);
        when(builderMock.build()).thenAnswer((Answer<GetArtistsTopTracksRequest>) invocation -> {
            GetArtistsTopTracksRequest requestMock = mock(GetArtistsTopTracksRequest.class);
            when(requestMock.execute()).thenReturn(tracks);
            return requestMock;
        });
        when(spotifyApi.getArtistsTopTracks(anyString(), any(CountryCode.class))).thenReturn(builderMock);

        List<Track> result = service.showMyPlaylist("Artist1", "Artist2", "Artist3");

        verify(spotifyApi, times(3)).getArtistsTopTracks(anyString(), ArgumentMatchers.any(CountryCode.class));
        verify(repository).set(anyList());
        Assertions.assertNotNull(result);
        assertEquals(15, result.size());
        assertEquals(service.playlistName, "Top 5 from Artist1, Artist2 and Artist3");
    }

    @Test
    void testCreateMyPlaylist() throws IOException, ParseException, SpotifyWebApiException {
        MyUser userMock = mock(MyUser.class);
        when(userService.getUser()).thenReturn(userMock);
        when(userMock.getUserId()).thenReturn("userId");

        Playlist playlistMock = mock(Playlist.class);
        when(playlistMock.getId()).thenReturn("playlistId");

        CreatePlaylistRequest.Builder createPlaylistBuilderMock = mock(CreatePlaylistRequest.Builder.class);
        CreatePlaylistRequest createPlaylistRequestMock = mock(CreatePlaylistRequest.class);
        when(spotifyApi.createPlaylist(anyString(), anyString())).thenReturn(createPlaylistBuilderMock);
        when(createPlaylistBuilderMock.build()).thenReturn(createPlaylistRequestMock);
        when(createPlaylistRequestMock.execute()).thenReturn(playlistMock);

        AddItemsToPlaylistRequest.Builder addItemsBuilderMock = mock(AddItemsToPlaylistRequest.Builder.class);
        AddItemsToPlaylistRequest addItemsRequestMock = mock(AddItemsToPlaylistRequest.class);
        when(spotifyApi.addItemsToPlaylist(anyString(), any(String[].class))).thenReturn(addItemsBuilderMock);
        when(addItemsBuilderMock.build()).thenReturn(addItemsRequestMock);
        SnapshotResult snapshotResultMock = mock(SnapshotResult.class);
        when(addItemsRequestMock.execute()).thenReturn(snapshotResultMock);

        List<Track> trackListMock = Arrays.asList(mock(Track.class), mock(Track.class), mock(Track.class));
        when(repository.get()).thenReturn(trackListMock);
        service.playlistName = "";

        String result = service.createMyPlaylist();

        verify(spotifyApi).createPlaylist("userId", service.playlistName);
        verify(spotifyApi).addItemsToPlaylist(eq("playlistId"), any(String[].class));
    }
}
