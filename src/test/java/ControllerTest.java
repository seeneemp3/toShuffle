import com.personal.myShuffler.MyShufflerApplication;
import com.personal.myShuffler.controller.MainController;
import com.personal.myShuffler.security.SecurityConfig;
import com.personal.myShuffler.service.MyPlaylistService;
import com.personal.myShuffler.service.SearchArtistService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MainController.class)
@ContextConfiguration(classes = {MyShufflerApplication.class, SecurityConfig.class})
public class ControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SearchArtistService searchArtistService;
    @MockBean
    private MyPlaylistService playlistService;


    @Test
    @WithMockUser
    public void testHome() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("main"));
    }

    @Test
    public void testUnauthorizedAndRedirect() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser
    public void testSearchArtists_Success() throws Exception {
        List<String> mockResponse = Arrays.asList("Artist1", "Artist2", "Artist3");
        when(searchArtistService.searchResults("query")).thenReturn(ResponseEntity.ok(mockResponse));

        mockMvc.perform(get("/search").param("query", "query"))
                .andExpect(status().isOk())
                .andExpect(content().json("[\"Artist1\",\"Artist2\", \"Artist3\"]"));

        verify(searchArtistService).searchResults("query");
    }

    @Test
    @WithMockUser
    public void testShowPlaylist_Success() throws Exception {
        List<Track> mockTracks = Arrays.asList(new Track.Builder().build(), new Track.Builder().build(), new Track.Builder().build());
        when(playlistService.showMyPlaylist("Dr. Dre", "Jay-Z", "Kanye West")).thenReturn(mockTracks);

        mockMvc.perform(get("/showPlaylist")
                        .param("searchInput1", "Dr. Dre")
                        .param("searchInput2", "Jay-Z")
                        .param("searchInput3", "Kanye West"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));

        verify(playlistService).showMyPlaylist("Dr. Dre", "Jay-Z", "Kanye West");
    }

    @Test
    @WithMockUser
    public void testShowPlaylist_Error() throws Exception {
        when(playlistService.showMyPlaylist("Dr. Dre", "Jay-Z", "Kanye West")).thenThrow(new IOException());

        mockMvc.perform(get("/showPlaylist")
                        .param("searchInput1", "Dr. Dre")
                        .param("searchInput2", "Jay-Z")
                        .param("searchInput3", "Kanye West"))
                .andExpect(status().isInternalServerError());

        verify(playlistService).showMyPlaylist("Dr. Dre", "Jay-Z", "Kanye West");
    }
}
