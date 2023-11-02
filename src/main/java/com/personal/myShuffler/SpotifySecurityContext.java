package com.personal.myShuffler;

import se.michaelthelin.spotify.SpotifyApi;

public interface SpotifySecurityContext {
   String getAccessToken();
   String getRefreshToken();
   SpotifyApi getSpotifyApi();
}
