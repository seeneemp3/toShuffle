package com.personal.myShuffler.repository;

import org.springframework.stereotype.Repository;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.util.List;

@Repository
public class PlaylistRepositoryImpl implements PlaylistRepository {

    public List<Track> inMemoryPlaylist;

    @Override
    public void set(List<Track> playlist) {
        inMemoryPlaylist = playlist;
    }

    @Override
    public List<Track> get() {
       return inMemoryPlaylist;
    }
}
