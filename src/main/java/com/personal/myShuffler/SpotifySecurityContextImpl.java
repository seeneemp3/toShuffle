package com.personal.myShuffler;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.SpotifyApi;

@Component
@RequiredArgsConstructor
public class SpotifySecurityContextImpl implements SpotifySecurityContext {

    private final OAuth2AuthorizedClientService clientService;
    public SpotifyApi spotifyApi;

    public String getAccessToken() {
        return getClient().getAccessToken().getTokenValue();
    }
    public String getRefreshToken() {
        return getClient().getRefreshToken().getTokenValue();
    }

    @Override
    public SpotifyApi getSpotifyApi() {
        return SpotifyApi.builder().setAccessToken(getAccessToken()).setRefreshToken(getRefreshToken()).build();
    }


    private OAuth2AuthorizedClient getClient() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
            String clientRegistrationId = oauthToken.getAuthorizedClientRegistrationId();
            if ("spotify".equals(clientRegistrationId)) {
                return clientService.loadAuthorizedClient(clientRegistrationId, oauthToken.getName());
            }
        }
        throw new RuntimeException("OAuth2AuthorizedClient not found");
    }

}

