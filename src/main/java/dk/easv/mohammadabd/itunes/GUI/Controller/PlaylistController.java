package dk.easv.mohammadabd.itunes.GUI.Controller;
import dk.easv.mohammadabd.itunes.BLL.PlaylistManager;
import dk.easv.mohammadabd.itunes.BE.Playlist;
import dk.easv.mohammadabd.itunes.BE.Song;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


import java.util.List;



public class PlaylistController {

    @FXML
    private TextField playlistNameField;

    private final PlaylistManager playlistManager;
    @FXML
    private void onPlcChangeText(String name) {
        int totalsong =0;
        long totalduration = 0;
        PlaylistManager plc = new PlaylistManager();
        plc.addPlaylist(name, totalsong, totalduration);
    }

    @FXML
    private void onPlcNewButton() {
        String playlistName = playlistNameField.getText().trim().toString();
        System.out.println(playlistName);
        onPlcChangeText(playlistName);
    }
    public PlaylistController() {
        playlistManager = new PlaylistManager(); // Instantiate the PlaylistManager

    }

    // Display all playlists
    public void showAllPlaylists() {
        List<Playlist> playlists = playlistManager.getAllPlaylists(); // Fetch all playlists
        if (playlists.isEmpty()) {
            System.out.println("No playlists found.");
        } else {
            for (Playlist playlist : playlists) {
                System.out.println(playlist);

            }
        }
    }

    // Display playlist by ID
    public void showPlaylistById(int playlistId) {
        Playlist playlist = playlistManager.getPlaylistById(playlistId); // Fetch playlist by ID
        if (playlist == null) {
            System.out.println("Playlist with ID " + playlistId + " not found.");
        } else {
            System.out.println(playlist); // Display playlist
        }
    }

    public void showPlayListSongs(int playlistId) {

        List<Song> playlist = playlistManager.getPlayListSongs(playlistId);
        if (playlist == null) {
            System.out.println("Playlist with ID " + playlistId + " not found.");
        } else {
            if(playlist.isEmpty()) {
                System.out.println("No songs found for playlist with ID " + playlistId + ".");
            }else{
                playlist.forEach(song -> System.out.println(song));
            }

        }
    }

    // Add a new playlist
    public void createNewPlaylist(String name, int totalSongs, long totalDuration) {
        boolean success = playlistManager.addPlaylist(name, totalSongs, totalDuration);
        if (success) {
            System.out.println("New playlist '" + name + "' has been created.");
        } else {
            System.out.println("Failed to create new playlist.");
        }
    }

    // Update an existing playlist
    public void updatePlaylist(int playlistId, String newName, int newTotalSongs, long newTotalDuration) {
        boolean success = playlistManager.updatePlaylist(playlistId, newName, newTotalSongs, newTotalDuration);
        if (success) {
            System.out.println("Playlist updated successfully.");
        } else {
            System.out.println("Failed to update playlist.");
        }
    }

    // Delete a playlist
    public void deletePlaylist(int playlistId) {
        boolean success = playlistManager.deletePlaylist(playlistId);
        if (success) {
            System.out.println("Playlist deleted successfully.");
        } else {
            System.out.println("Failed to delete playlist.");
        }
    }

    // Add song to playlist
    public void addSongToPlaylist(int playlistId, Song song) {
        boolean success = playlistManager.addSongToPlaylist(playlistId, song);
         playlistManager.getSongById(playlistId);
        if (success) {

            System.out.println("Song '" + song.getTitle() + "' added to playlist " + playlistId);

        } else {
            System.out.println("Failed to add song to playlist.");
        }
    }


}
