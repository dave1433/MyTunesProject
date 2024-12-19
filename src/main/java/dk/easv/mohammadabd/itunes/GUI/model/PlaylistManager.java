package dk.easv.mohammadabd.itunes.GUI.model;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import dk.easv.mohammadabd.itunes.BE.Song;

public class PlaylistManager {
    private Map<String, Playlist> playlists;

    public PlaylistManager() {
        this.playlists = new HashMap<>();
    }
    public Playlist createPlaylist(String name) {
        Playlist playlist = new Playlist(name);
        playlists.put(name, playlist);
        System.out.println("Created playlist: " + name);
        return playlist;
    }
    public Playlist getPlaylist(String name) {
        return playlists.get(name);
    }

    public void deletePlaylist(Playlist playlist) {
        playlists.remove(playlist.getName());
        System.out.println("Deleted playlist: " + playlist.getName());
    }

    // retrieve a list all playlists
    public Map<String, Playlist> getPlaylists() {
        return playlists;
    }

    // Method to list all available playlists

    public void listPlaylists() {
        System.out.println("Available Playlists:");
        for (String name : playlists.keySet()) {
            System.out.println("- " + name);
        }
    }
    public void deleteSongFromPlaylist(String playlistName, Song song, boolean deleteFile) {
        var playlist = playlists.get(playlistName);
        if (playlist != null) {
            playlist.removeSong(song);
            if (deleteFile) {
                File file = new File(song.getFilePath());
                if (file.delete()) {
                    System.out.println("File deleted: " + file.getAbsolutePath());
                } else {
                    System.out.println("Failed to delete file: " + file.getAbsolutePath());
                }
            }
        } else {
            System.out.println("Playlist not found: " + playlistName);
        }
    }







}

