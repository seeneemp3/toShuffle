package com.personal.myShuffler.service;

import com.personal.myShuffler.model.MyUser;
import com.personal.myShuffler.security.SpotifySecurityContext;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.User;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class MyUserService {

    private final SpotifySecurityContext securityContext;

    public MyUser getUser() throws IOException, ParseException, SpotifyWebApiException {
        User user = securityContext.getSpotifyApi().getCurrentUsersProfile().build().execute();
        return new MyUser(user.getId());
    }

}
