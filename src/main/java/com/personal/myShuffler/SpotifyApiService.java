package com.personal.myShuffler;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SpotifyApiService {
    private final OAuth2AuthenticationToken token;
    private final OAuth2AuthorizedClientService authorizedClientService;
    private static SpotifyApi spotifyApi;

    public SpotifyApi get (){
        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
                token.getAuthorizedClientRegistrationId(),
                token.getName());

        spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(authorizedClient.getAccessToken().getTokenValue())
                .setRefreshToken(Objects.requireNonNull(authorizedClient.getRefreshToken()).getTokenValue())
                .build();
        return spotifyApi;
    }


}
