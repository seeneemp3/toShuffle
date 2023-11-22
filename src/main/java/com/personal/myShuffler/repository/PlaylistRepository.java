package com.personal.myShuffler.repository;

import se.michaelthelin.spotify.model_objects.specification.Track;

import java.util.List;
public interface PlaylistRepository {
    void set(List<Track> playlist);
    List<Track> get();
}
