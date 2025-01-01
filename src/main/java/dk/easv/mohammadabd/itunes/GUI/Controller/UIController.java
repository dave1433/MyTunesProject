package dk.easv.mohammadabd.itunes.GUI.Controller;

import dk.easv.mohammadabd.itunes.BE.Song;
import dk.easv.mohammadabd.itunes.GUI.model.SongManager;
import dk.easv.mohammadabd.itunes.GUI.model.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class UIController {

    private SongManager songManager = new SongManager();
    private Player player;

    @FXML
    private TableView<Song> songTableView;
    @FXML
    private TableColumn<Song, String> titleColumn;
    @FXML
    private TableColumn<Song, String> artistColumn;
    @FXML
    private TableColumn<Song, String> genreColumn;
    @FXML
    private TableColumn<Song, String> albumColumn;
    @FXML
    private TextField searchField;
    @FXML
    private Button resumePauseButton;
    @FXML
    private Button playButton, pauseButton, nextButton, previousButton, skipBackwardButton;

    private ObservableList<Song> songsObservable;

    @FXML
    public void initialize() {
        // Initialize TableView columns
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        artistColumn.setCellValueFactory(cellData -> cellData.getValue().artistProperty());
        genreColumn.setCellValueFactory(cellData -> cellData.getValue().genreProperty());
        albumColumn.setCellValueFactory(cellData -> cellData.getValue().albumProperty());

        songManager = new SongManager(); // Initialize songManager
        searchField.setOnKeyReleased(event -> onSearchFieldUpdated());

        // Initialize observable list for TableView
        songsObservable = FXCollections.observableArrayList(songManager.getSongs());
        songTableView.setItems(songsObservable);

        // Initialize player and set up media control buttons
        player = new Player(songsObservable);
    }



    // Song Management
    public void onAddSongClicked() {
        try {
            Song newSong = new Song(1, "Smooth Criminal", "Michael Jackson", "Pop", 8400, "eterna-cancao-wav-12569.wav", "Moon", 5);
            songManager.addSong(newSong);
            refreshTableView();
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/mohammadabd/itunes/GUI/newSongWindow.fxml"));
            Scene scene = new Scene(loader.load());

            // Create a new stage (window)
            Stage newStage = new Stage();
            newStage.setTitle("New Song Window");
            newStage.setScene(scene);
            newStage.setResizable(false); // Disable resizing
            newStage.show(); // Show the new window

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onEditSongClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/mohammadabd/itunes/GUI/editSongWindow.fxml"));
            Scene scene = new Scene(loader.load());

            Stage newStage = new Stage();
            newStage.setTitle("Edit Song Window");
            newStage.setScene(scene);
            newStage.setResizable(false); // Disable resizing
            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onRemoveSongClicked() {
        Song selectedSong = songTableView.getSelectionModel().getSelectedItem();
        if (selectedSong != null) {
            songManager.removeSong(selectedSong);
            refreshTableView();
        }
    }

    // Playlist Management
    public void onCreatePlaylistClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/mohammadabd/itunes/GUI/newPlaylistWindow.fxml"));
            Scene scene = new Scene(loader.load());

            Stage newStage = new Stage();
            newStage.setTitle("New Playlist Window");
            newStage.setScene(scene);
            newStage.setResizable(false); // Disable resizing
            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onEditPlaylistClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/mohammadabd/itunes/GUI/editPlaylistWindow.fxml"));
            Scene scene = new Scene(loader.load());

            Stage newStage = new Stage();
            newStage.setTitle("Edit Playlist Window");
            newStage.setScene(scene);
            newStage.setResizable(false); // Disable resizing
            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onDeletePlaylistClicked() {
        // Implement playlist deletion logic here
    }

    // Search
    @FXML
    private void onSearchFieldUpdated() {
        String query = searchField.getText();
        if (query.isEmpty()) {
            songsObservable.setAll(songManager.getSongs());
        } else {
            songsObservable.setAll(songManager.filterSongs(query));
        }
    }

    // Media Controls
    @FXML
    private void onPlayButtonClicked() {
        Song selectedSong = songTableView.getSelectionModel().getSelectedItem();
        if (selectedSong != null) {
            // testing the media player
            // Add some songs to the SongManager
            SongManager songManager = new SongManager();
            songManager.addSong(new Song(1, "eterna-cancao-wav-12569", "eterna", "romanitic", 21000, "eterna-cancao-wav-12569.wav", "new evning", 4));
            songManager.addSong(new Song(2, "see-you-later", "jack pop", "pop", 14710, "see-you-later-203103.mp3", "new morning", 1));

            // Create a Player instance with the songs from SongManager
            Player player = new Player(songManager.getSongs());
            System.out.println(songManager.getSongs());

            // Play the first song
            player.playSong();
        } else {
            System.out.println("No song selected to play.");
        }
    }


    @FXML
    private void onResumePauseButtonClicked() {
        if (player.isPlaying()) {
            player.pause(); // Pause the song
            resumePauseButton.setText("⏯"); // Change to resume icon
        } else {
            player.playSong(); // Resume the song
            resumePauseButton.setText("⏸"); // Change to pause icon
        }
    }

    @FXML
    private void onPauseButtonClicked() {
        player.pause();
    }

    @FXML
    private void onNextButtonClicked() {
        player.playNextSong();
    }

    @FXML
    private void onPreviousButtonClicked() {
        player.playPreviousSong();
    }

    @FXML
    private void onSkipBackwardButtonClicked() {
        player.skipBackward();
    }


    // Utility
    private void refreshTableView() {
        songsObservable.setAll(songManager.getSongs());
    }
}
