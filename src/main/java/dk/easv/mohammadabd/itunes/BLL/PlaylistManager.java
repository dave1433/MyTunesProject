package dk.easv.mohammadabd.itunes.BLL;

import dk.easv.mohammadabd.itunes.DAL.DBplaylist;
import dk.easv.mohammadabd.itunes.BE.Playlist;
import dk.easv.mohammadabd.itunes.BE.Song;
import dk.easv.mohammadabd.itunes.DAL.DBsong;
import java.util.List;

public class PlaylistManager {

    private DBplaylist dbPlaylist; // Data Access Layer (DAL) for playlists
    private DBsong dbSong; // Data Access Layer (DAL) for songs

    // Constructor
    public PlaylistManager() {
        dbPlaylist = new DBplaylist(); // Initialize dbPlaylist
        dbSong = new DBsong(); // Initialize dbSong
    }

    // Fetch all playlists from the database
    public List<Playlist> getAllPlaylists() {
        return dbPlaylist.getAllPlaylists(); // Fetches playlists using DBplaylist
    }

    // Get playlist by ID
    public Playlist getPlaylistById(int playlistId) {
        return dbPlaylist.getPlaylistById(playlistId); // Fetches playlist by ID using DBplaylist
    }

    // Get songs by playlist ID
    public List<Song> getPlayListSongs(int playlistId) {
        if (dbSong == null) {
            throw new IllegalStateException("dbSong is not initialized");
        }
        return dbSong.getplaylist_id(playlistId); // Fetches songs by playlist ID using DBsong
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

    // Optionally, add a song to the playlist
    public boolean addSongToPlaylist(int playlistId, Song song) {
        System.out.println("Adding song to playlist " + playlistId + ": " + song.getTitle());
        // Implement actual song adding logic here, if necessary
        return true;
    }

    // Get songs in a playlist by ID (assuming this is the correct method in DBplaylist)
    public boolean getSongById(int playlistId, Song song) {
        return dbPlaylist.getSongsInPlaylist(playlistId, song); // This method seems to interact with DBplaylist
    }
}
