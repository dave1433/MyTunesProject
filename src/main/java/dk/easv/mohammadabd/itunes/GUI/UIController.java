package dk.easv.mohammadabd.itunes.GUI;

import dk.easv.mohammadabd.itunes.BLL.Playlist;
import dk.easv.mohammadabd.itunes.BE.Song;
import dk.easv.mohammadabd.itunes.BLL.PlaylistManager;
import dk.easv.mohammadabd.itunes.BLL.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class UIController {

    @FXML private TableView<Song> songTableView; // Table to display songs
    @FXML private TableColumn<Song, String> titleColumn; // Song title column
    @FXML private TableColumn<Song, String> artistColumn; // Artist column
    @FXML private TableColumn<Song, String> albumColumn; // Album column
    @FXML private TextField playlistNameField; // Field for entering a playlist name
    @FXML private TextField songTitleField; // Field for entering a song title
    @FXML private Label statusLabel; // Status label for messages

    private final PlaylistManager playlistManager = new PlaylistManager(); // Playlist management
    private final Player player = new Player(); // Song player
    private ObservableList<Song> songList; // List to manage songs

    @FXML
    public void initialize() {
        // Initialize the table view columns
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        artistColumn.setCellValueFactory(cellData -> cellData.getValue().artistProperty());


        // Initialize the song list and bind it to the table
        songList = FXCollections.observableArrayList();
        songTableView.setItems(songList);
    }

    @FXML
    private void handlePlayButtonClick() {
        Song selectedSong = songTableView.getSelectionModel().getSelectedItem();
        if (selectedSong != null) {
            player.play(selectedSong);
            statusLabel.setText("Playing: " + selectedSong.getTitle());
        } else {
            statusLabel.setText("No song selected.");
        }
    }

    @FXML
    private void handleAddSongClick() {
        String playlistName = playlistNameField.getText();
        String songTitle = songTitleField.getText();

        if (playlistName.isEmpty() || songTitle.isEmpty()) {
            statusLabel.setText("Please enter both playlist name and song title.");
            return;
        }

        Playlist playlist = playlistManager.getPlaylist(playlistName);
        if (playlist == null) {
            playlist = playlistManager.createPlaylist(playlistName);
            statusLabel.setText("Created new playlist: " + playlistName);
        }

        Song song = new Song(songTitle, "Unknown Artist", "Unknown Album", "path/to/song.mp3"); // Replace with actual song logic
        playlist.addSong(song);
        songList.add(song);
        statusLabel.setText("Added song: " + songTitle + " to playlist: " + playlistName);
    }

    @FXML
    private void handleDeleteSongClick() {
        Song selectedSong = songTableView.getSelectionModel().getSelectedItem();
        if (selectedSong != null) {
            songList.remove(selectedSong);
            playlistManager.deleteSongFile(selectedSong); // Delete file from disk
            statusLabel.setText("Deleted song: " + selectedSong.getTitle());
        } else {
            statusLabel.setText("No song selected for deletion.");
        }
    }

    @FXML
    private void handleCreatePlaylistClick() {
        String playlistName = playlistNameField.getText();
        if (playlistName.isEmpty()) {
            statusLabel.setText("Please enter a playlist name.");
            return;
        }

        if (playlistManager.getPlaylist(playlistName) == null) {
            playlistManager.createPlaylist(playlistName);
            statusLabel.setText("Created new playlist: " + playlistName);
        } else {
            statusLabel.setText("Playlist already exists: " + playlistName);
        }
    }

    @FXML
    private void handleDeletePlaylistClick() {
        String playlistName = playlistNameField.getText();
        if (playlistName.isEmpty()) {
            statusLabel.setText("Please enter a playlist name.");
            return;
        }

        Playlist playlist = playlistManager.getPlaylist(playlistName);
        if (playlist != null) {
            playlistManager.deletePlaylist(playlist);
            statusLabel.setText("Deleted playlist: " + playlistName);
        } else {
            statusLabel.setText("Playlist not found: " + playlistName);
        }
    }

    @FXML
    private void handleStopPlaybackClick() {
        player.stop();
        statusLabel.setText("Playback stopped.");
    }
}
