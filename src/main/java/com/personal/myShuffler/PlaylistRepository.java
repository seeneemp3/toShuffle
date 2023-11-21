package com.personal.myShuffler;

import se.michaelthelin.spotify.model_objects.specification.Track;

import java.util.List;
public interface PlaylistRepository {
    void set(List<Track> playlist);
    List<Track> get();
}
