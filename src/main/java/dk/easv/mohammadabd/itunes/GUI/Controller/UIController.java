package dk.easv.mohammadabd.itunes.GUI.Controller;

import dk.easv.mohammadabd.itunes.GUI.Controller.PlaylistController;
import dk.easv.mohammadabd.itunes.BE.Playlist;
import dk.easv.mohammadabd.itunes.BE.Song;
import dk.easv.mohammadabd.itunes.GUI.model.Player;
import dk.easv.mohammadabd.itunes.GUI.model.SongManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;
import javafx.scene.control.Slider;
import javafx.scene.input.DragEvent;
import javafx.util.Duration;


import java.io.IOException;

public class UIController {

    // private final PlaylistManager playlistManager = new PlaylistManager();
    private  SongManager songManager = new SongManager();
    Player player = new Player(songManager.getAllSongs());


    @FXML
    private Slider progressSlider;

    @FXML
    private Timeline sliderUpdater; // For updating the slider

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

    private PlaylistController playlistController = new PlaylistController();

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
        playlistSongsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTotalSongs() + ""));
        playlistDurationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTotalDuration() + ""));

        searchField.setOnKeyReleased(event -> onSearchFieldUpdated());

        // Enable drag-and-drop for song reordering
        enableDragAndDropForSongs();

        // Initialize observable list for TableViews
        songsObservable = FXCollections.observableArrayList(songManager.getAllSongs());
        songTableView.setItems(songsObservable);

        playlistsObservable = FXCollections.observableArrayList(songManager.getAllPlaylists());
        playlistTableView.setItems(playlistsObservable);

        // Add double-click event listener to songTableView
        songTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Detect double-click
                handleSongSelection();
            }
        });

        // Listener for Playlist TableView selection
        playlistTableView.setOnMouseClicked(this::handlePlaylistSelection);

        songsObservable.forEach(song -> songManager.addSong(song));
        // Set up listener for slider dragging and updating song progress
        progressSlider.setValue(0); // Initial value for the slider
        progressSlider.setMax(100); // Max value for the slider (we'll map it to the song duration later)

        player.setProgressListener (currentTimeInSeconds -> updateProgressSlider(currentTimeInSeconds));
    }



    private boolean wasPlayingBeforeSliderDrag = false;



    @FXML
    private void onSliderDragDetected(DragEvent event) {
        // Pause playback while the user is dragging the slider
        if (player.isPlaying()) {
            wasPlayingBeforeSliderDrag = true;
            player.pause();
        }
        System.out.println("Slider drag detected!");
    }

    @FXML
    private void onSliderMouseReleased(MouseEvent event) {
        // Update the player's progress and optionally resume playback
        double seekTime = progressSlider.getValue();
        double totalDuration = player.getCurrentSongDuration();
        double newSeekTime = (seekTime / 100) * totalDuration;

        player.seekTo(newSeekTime); // Seek to the new position
        System.out.println("Slider released, seeking to: " + newSeekTime + " seconds");

        // Resume playback if the song was playing before
        if (wasPlayingBeforeSliderDrag) {
            player.playSong();
            wasPlayingBeforeSliderDrag = false;
            startUpdatingSlider();
        }
    }

    @FXML
    private void onSliderDragDone(DragEvent event) {
        // This method can be used for additional actions when the drag is complete
        System.out.println("Slider drag done!");
    }
    @FXML
    public void updateProgressSlider(double currentTimeInSeconds) {
        double totalDuration = player.getCurrentSongDuration();
        if (totalDuration <= 0) {
            progressSlider.setValue(0); // Prevent division by zero
            return;
        }

        double percentage = (currentTimeInSeconds / totalDuration) * 100;

        if (!progressSlider.isValueChanging()) {
            progressSlider.setValue(percentage); // Update the slider only if not being manually adjusted
        }
    }


    public void startUpdatingSlider() {
        if (sliderUpdater != null) {
            sliderUpdater.stop(); // Stop any existing updater
        }

        // Update the slider every second based on the current playback time
        sliderUpdater = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            double currentTime = player.getCurrentSongDuration(); // Get the current playback time
            updateProgressSlider(currentTime); // Update the slider
        }));
        sliderUpdater.setCycleCount(Timeline.INDEFINITE);
        sliderUpdater.play();
    }

    public void stopUpdatingSlider() {
        if (sliderUpdater != null) {
            sliderUpdater.stop(); // Stop slider updates
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
        playlistController.deletePlaylist(playlistTableView.getSelectionModel().getSelectedItem().getId());
        playlistTableView.setItems(playlistsObservable);
    }


    @FXML
    private void updateSongOrder(int fromIndex, int toIndex) {
        // Update the observable list in SongManager
        songManager.updateSongOrder(fromIndex, toIndex);

        // Refresh the songsObservable to reflect the new order
        songsObservable.setAll(songManager.getMediaPlayerSongs());

        // Reinitialize the media player playlist with the updated order
        songManager.updatePlaylist(songsObservable);


    }

    @FXML
    public void enableDragAndDropForSongs() {
        songTableView.setRowFactory(tv -> {
            TableRow<Song> row = new TableRow<>();

            // Set up drag detected event
            row.setOnDragDetected(event -> {
                if (!row.isEmpty()) {
                    Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent content = new ClipboardContent();
                    content.putString(String.valueOf(row.getIndex())); // Store the index of the dragged item
                    db.setContent(content);
                    event.consume();
                }
            });

            // Set up drag over event
            row.setOnDragOver(event -> {
                if (event.getGestureSource() != row && event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.MOVE);
                }
                event.consume();
            });

            // Set up drag dropped event


            row.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasString()) {
                    int draggedIndex = Integer.parseInt(db.getString()); // Index of dragged song
                    Song draggedSong = songTableView.getItems().remove(draggedIndex);

                    int dropIndex;
                    if (row.isEmpty()) {
                        dropIndex = songTableView.getItems().size(); // Drop at the end
                    } else {
                        dropIndex = row.getIndex();
                    }

                    songTableView.getItems().add(dropIndex, draggedSong); // Add song to the new position
                    songTableView.getSelectionModel().select(dropIndex);

                    // Update the underlying observable list and data source
                    updateSongOrder(draggedIndex, dropIndex);

                    event.setDropCompleted(true);
                    event.consume();
                }
            });

            return row;
        });
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
            player.playSong(selectedSong); // Play the selected song directly
            startUpdatingSlider(); // Start updating the slider
        } else {
            System.out.println("No song selected.");
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
        if (!player.isPlaying()) {
            player.resume();
            startUpdatingSlider(); // Resume slider updates
            resumePauseButton.setText("⏸"); // Change to pause icon
        } else {
            player.pause(); // Pause the song
            stopUpdatingSlider(); // Stop slider updates
            resumePauseButton.setText("▶"); // Change to resume icon
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

    public void onSliderMousePressed(MouseEvent mouseEvent) {
    }
}