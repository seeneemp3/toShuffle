package com.personal.myShuffler.security;

import se.michaelthelin.spotify.SpotifyApi;

public interface SpotifySecurityContext {
   String getAccessToken();
   String getRefreshToken();
   SpotifyApi getSpotifyApi();
}
