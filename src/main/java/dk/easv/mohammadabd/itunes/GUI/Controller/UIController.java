package dk.easv.mohammadabd.itunes.GUI.Controller;

import dk.easv.mohammadabd.itunes.BE.Playlist;
import dk.easv.mohammadabd.itunes.BE.Song;
import dk.easv.mohammadabd.itunes.GUI.model.Player;
import dk.easv.mohammadabd.itunes.GUI.model.SongManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.control.Slider;
import javafx.scene.input.DragEvent;

import java.io.IOException;

public class UIController {

    // private final PlaylistManager playlistManager = new PlaylistManager();
    private SongManager songManager = new SongManager();
    Player player = new Player(songManager.getAllSongs());


    @FXML
    private Slider progressSlider;

    @FXML
    private TableView<Song> songTableView;
    @FXML
    private TableView<Playlist> playlistTableView;
    @FXML
    private TableView<Song> songsInPlaylistTableView;

    @FXML
    private TableColumn<Song, String> titleColumn;
    @FXML
    private TableColumn<Song, String> artistColumn;
    @FXML
    private TableColumn<Song, String> genreColumn;
    @FXML
    private TableColumn<Song, String> albumColumn;
    @FXML
    private TableColumn<Song, String> durationColumn;

    @FXML
    private TableColumn<Song, String> playlistSongColumn;
    @FXML
    private TableColumn<Song, String> playlistSongDurationColumn;

    @FXML
    private Button resumePauseButton;

    @FXML
    private TableColumn<Playlist, String> playlistNameColumn;

    @FXML
    private TableColumn<Playlist, String> playlistSongsColumn;


    @FXML
    private TableColumn<Playlist, String> playlistDurationColumn;

    @FXML
    private TextField searchField;

    private ObservableList<Song> songsObservable;
    private ObservableList<Playlist> playlistsObservable;
    private ObservableList<Song> songsInPlaylistObservable;

    private ObservableList<Song> songs;

    @FXML
    public void initialize() {
        // Initialize Songs TableView columns
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        artistColumn.setCellValueFactory(cellData -> cellData.getValue().artistProperty());
        genreColumn.setCellValueFactory(cellData -> cellData.getValue().genreProperty());
        albumColumn.setCellValueFactory(cellData -> cellData.getValue().albumProperty());
        durationColumn.setCellValueFactory(cellData -> cellData.getValue().durationProperty());

        // Initialize Playlist TableView columns
        playlistNameColumn.setCellValueFactory(cellData -> cellData.getValue().getName());
        playlistSongsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTotalSongs()+""));
        playlistDurationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTotalDuration()+""));

        searchField.setOnKeyReleased(event -> onSearchFieldUpdated());

        // Initialize observable list for TableViews
        songsObservable = FXCollections.observableArrayList(songManager.getAllSongs());
        songTableView.setItems(songsObservable);

        playlistsObservable = FXCollections.observableArrayList(songManager.getAllPlaylists());
        playlistTableView.setItems(playlistsObservable);

        // listener for Playlist TableView selection
        playlistTableView.setOnMouseClicked(this::handlePlaylistSelection);

        // add the songs to media player to be ready to play
        songsObservable.forEach(song -> songManager.addSong(song));
        // Set up listener for slider dragging and updating song progress
        progressSlider.setValue(0); // Initial value for the slider
        progressSlider.setMax(100); // Max value for the slider (we'll map it to the song duration later)

        player.setMediaPlayerProgressListener(currentTimeInSeconds -> updateProgressSlider(currentTimeInSeconds));
    }

    private boolean wasPlayingBeforeSliderDrag = false;

    @FXML
    private void onSliderMousePressed(MouseEvent event) {
        if (player.isPlaying()) {
            wasPlayingBeforeSliderDrag = true;
            player.pause(); // Pause playback while the slider is being dragged
        } else {
            wasPlayingBeforeSliderDrag = false;
        }

        System.out.println("Mouse pressed on slider. Was playing: " + wasPlayingBeforeSliderDrag);
    }


    @FXML
    private void onSliderMouseReleased(MouseEvent event) {
        double seekPercentage = progressSlider.getValue();
        double totalDuration = player.getCurrentSongDuration();

        System.out.println("Slider value: " + seekPercentage);
        System.out.println("Total duration: " + totalDuration);

        if (totalDuration > 0) {
            // Calculate the seek time in milliseconds or seconds depending on your system
            double seekTime = (seekPercentage / 100) * totalDuration;
            System.out.println("Calculated seek time: " + seekTime);

            // Seek to the calculated position
            player.seekTo(seekTime);

            // Resume playback if it was playing before dragging the slider
            if (wasPlayingBeforeSliderDrag) {
                player.resume(); // Resume playback without reinitializing
                wasPlayingBeforeSliderDrag = false;
            }
        }
    }

    @FXML
    // When drag is detected (dragging has started)
    private void onSliderDragDetected(DragEvent event) {
        // Handle the drag event here, if necessary
        System.out.println("Drag detected on slider");
        event.consume(); // Consume to prevent the default behavior
    }
    @FXML
    // When the drag is done (you can finalize actions here)
    private void onSliderDragDone(DragEvent event) {
        // You can finalize drag operations here if necessary
        System.out.println("Drag finished");
        event.consume(); // Consume the event to prevent default drag actions
    }
    public void updateProgressSlider(double currentTimeInSeconds) {
        double totalDuration = player.getCurrentSongDuration();

        // Ensure totalDuration > 0 to prevent divide-by-zero errors
        if (totalDuration > 0) {
            double percentage = (currentTimeInSeconds / totalDuration) * 100;

            if (!progressSlider.isValueChanging()) {
                progressSlider.setValue(percentage);
            }
        }
    }
    // Song Management
    public void onAddSongClicked() {
        try {

            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/mohammadabd/itunes/GUI/newSongWindow.fxml"));
            Scene scene = new Scene(loader.load());

            // Create a new stage (window)
            Stage newStage = new Stage();
            newStage.setTitle("New Song Window");
            newStage.setScene(scene);
            newStage.setResizable(false); // Disable resizing
            newStage.show();  // Show the new window

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onEditSongClicked() {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/mohammadabd/itunes/GUI/editSongWindow.fxml"));
            Scene scene = new Scene(loader.load());

            // Create a new stage (window)
            Stage newStage = new Stage();
            newStage.setTitle("Edit Song Window");
            newStage.setScene(scene);
            newStage.setResizable(false); // Disable resizing
            newStage.show();  // Show the new window

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
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/mohammadabd/itunes/GUI/newPlaylistWindow.fxml"));
            Scene scene = new Scene(loader.load());

            // Create a new stage (window)
            Stage newStage = new Stage();
            newStage.setTitle("New PlayList Window");
            newStage.setScene(scene);
            newStage.setResizable(false); // Disable resizing
            newStage.show();  // Show the new window

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onEditPlaylistClicked() {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/mohammadabd/itunes/GUI/editPlaylistWindow.fxml"));
            Scene scene = new Scene(loader.load());

            // Create a new stage (window)
            Stage newStage = new Stage();
            newStage.setTitle("Edit PlayList Window");
            newStage.setScene(scene);
            newStage.setResizable(false); // Disable resizing
            newStage.show();  // Show the new window

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onDeletePlaylistClicked() {

    }

    // Handlers
    private void handlePlaylistSelection(MouseEvent event) {
        Playlist selectedPlaylist = playlistTableView.getSelectionModel().getSelectedItem();
        if (selectedPlaylist != null) {
            playlistSongColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
            playlistSongDurationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDuration()+""));

            songsInPlaylistObservable = FXCollections.observableArrayList(songManager.getSongByPlayListId(selectedPlaylist.getId()));
            songsInPlaylistTableView.setItems(songsInPlaylistObservable);
        }
    }

    private void handleSongSelection() {
        Song selectedSong = songTableView.getSelectionModel().getSelectedItem();
        if (selectedSong != null) {
            songManager.removeAllSongs();
            songManager.addSong(selectedSong);
            player.playSong();

            System.out.println("Selected song " + selectedSong.getTitle());
        }
    }


    // Search
    @FXML
    private void onSearchFieldUpdated() {
        String query = searchField.getText();
        if (query.isEmpty()) {
            songsObservable.setAll(songManager.getAllSongs());  // Show all songs if the search field is empty
            System.out.println("Retrieve all songs to Songs ListView");

        } else {
            songsObservable.setAll(songManager.filterSongs(query));  // Filter songs based on the query
            System.out.println("Searching for " + query);

        }
    }

    // Media Controls
    @FXML
    private void onResumePauseButtonClicked() {
        Song selectedSong = songTableView.getSelectionModel().getSelectedItem();
        if (!player.isPlaying()) {
            if (selectedSong != null) {
//                handleSongSelection();

                if (player.isPaused()) {
                    player.resume(); // Resume from the paused position
                } else {
                    player.playSong();
                }
                resumePauseButton.setText("⏸"); // Change to pause icon
            } else {
                System.out.println("No song selected to play.");
            }
        } else {
            player.pause(); // Pause the song
            resumePauseButton.setText("⏯"); // Change to resume icon
        }
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

    @FXML
    private void onSkipForwardButtonClicked() {
        player.skipForward();
    }

    // Utility
    private void refreshTableView() {
        songManager.removeAllSongs();

        songsObservable.setAll(songManager.getAllSongs());
        // add the songs to media player to be ready to play
        songsObservable.forEach(song -> songManager.addSong(song));
    }
}