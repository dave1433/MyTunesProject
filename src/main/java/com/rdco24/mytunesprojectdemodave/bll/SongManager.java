package com.rdco24.mytunesprojectdemodave.bll;

import com.rdco24.mytunesprojectdemodave.dal.db.DBManager;

import java.util.List;

public class SongManager {

    private final DBManager databaseManager;

    // Constructor
    public SongManager(DBManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    // Save a song
    public void saveSong(String song) {
        databaseManager.saveSong(song);
    }

    // Save playlists
    public void savePlaylists(List<String> playlists) {
        databaseManager.savePlaylist(playlists);
    }

    // Load songs
    public List<String> getSongs() {
        return databaseManager.loadSongs();
    }

    // Load playlists
    public List<String> getPlaylists() {
        return databaseManager.loadPlaylists();
    }
}