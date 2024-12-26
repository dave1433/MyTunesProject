package dk.easv.mohammadabd.itunes.BLL;

import dk.easv.mohammadabd.itunes.DAL.DBplaylist;
import dk.easv.mohammadabd.itunes.BE.Playlist;
import dk.easv.mohammadabd.itunes.BE.Song;

import java.util.List;

public class PlaylistManager {

    private DBplaylist dbPlaylist; // Data Access Layer (DAL) for playlists

    // Constructor
    public PlaylistManager() {
        dbPlaylist = new DBplaylist();
    }

    // Fetch all playlists from the database
    public List<Playlist> getAllPlaylists() {
        return dbPlaylist.getAllPlaylists(); // Fetches playlists using DBplaylist
    }

    // Get playlist by ID
    public Playlist getPlaylistById(int playlistId) {
        return dbPlaylist.getPlaylistById(playlistId); // Fetches playlist by ID using DBplaylist
    }

    // Add a new playlist to the database
    public boolean addPlaylist(String name, int totalSongs, long totalDuration) {
        Playlist playlist = new Playlist(name); // Create a new Playlist object
        playlist.setTotalSongs(totalSongs);
        playlist.setTotalDuration(totalDuration);

        return dbPlaylist.addPlaylist(playlist); // Add the playlist to the database
    }

    // Update an existing playlist
    public boolean updatePlaylist(int playlistId, String newName, int newTotalSongs, long newTotalDuration) {
        Playlist playlist = new Playlist(newName); // Create a new Playlist object
        playlist.setId(playlistId);
        playlist.setTotalSongs(newTotalSongs);
        playlist.setTotalDuration(newTotalDuration);

        return dbPlaylist.updatePlaylist(playlist, playlistId); // Update the playlist in the database
    }

    // Delete a playlist
    public boolean deletePlaylist(int playlistId) {
        return dbPlaylist.deletePlaylist(playlistId); // Delete the playlist from the database
    }

    // Optionally, add a song to the playlist (you might have methods in the BLL to manage songs as well)
    public boolean addSongToPlaylist(int playlistId, Song song) {
        // For this example, you could add logic to associate a song with a playlist in your database
        // You may need a method in DBplaylist to associate songs with playlists
        // For simplicity, assume there’s a method that adds a song to a playlist in the database.

        System.out.println("Adding song to playlist " + playlistId + ": " + song.getTitle());
        return true;
    }

    public boolean getSongById(int playlistId, Song song) {

        return dbPlaylist.getSongsInPlaylist(playlistId, song);

    }


}
