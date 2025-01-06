package dk.easv.mohammadabd.itunes.GUI.Controller;

import dk.easv.mohammadabd.itunes.BLL.PlaylistManager;
import dk.easv.mohammadabd.itunes.BE.Playlist;
import dk.easv.mohammadabd.itunes.BE.Song;
import dk.easv.mohammadabd.itunes.GUI.model.Player;
import dk.easv.mohammadabd.itunes.GUI.model.SongManager;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
    private Label durationText;

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
        playlistController.updateSongsInPlaylists(playlistsObservable);
        playlistTableView.setItems(playlistsObservable);

        // play the song when double click on the song in the list view
        songTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Detect double-click
                handleSongSelection();
            }
        });


        playlistTableView.setOnMouseClicked( this::handlePlaylistSelection);
        songsObservable.forEach(song -> songManager.addSong(song));
        // Set up listener for slider dragging and updating song progress
        progressSlider.setValue(0); // Initial value for the slider
        progressSlider.setMax(100); // Max value for the slider (we'll map it to the song duration later)

        player.setProgressListener (currentTimeInSeconds -> updateProgressSlider(currentTimeInSeconds));

    }




    // slider methods
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

    private int convertMinutesToSeconds(int minutes) {
        return minutes * 60;
    }

    @FXML
    private void onSliderMouseReleased(MouseEvent event) {
        double seekTime = progressSlider.getValue(); // Slider value (percentage)
        double totalDuration = player.getDuration(); // Total duration in seconds
        double newSeekTime = (seekTime / 100) * totalDuration; // Calculate new time in seconds

        player.seekTo(Duration.seconds(newSeekTime)); // Seek to the calculated time
        System.out.println("Slider released, seeking to: " + newSeekTime + " seconds");

        if (wasPlayingBeforeSliderDrag) {
            player.playSong(); // Resume playback
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
        double totalDuration = player.getDuration();
        if (totalDuration <= 0) {
            progressSlider.setValue(0); // Prevent division by zero
            return;
        }

        double percentage = (currentTimeInSeconds / totalDuration) * 100;

        if (!progressSlider.isValueChanging()) {
            progressSlider.setValue(percentage); // Update the slider only if not being manually adjusted
        }
    }



    private void startUpdatingSlider() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {

            updateDurationText();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
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

    @FXML
    private void onEditSongClicked() {
        try {
            // Load the FXML file for the popup window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/mohammadabd/itunes/GUI/editSongWindow.fxml"));
            Parent root = loader.load();

            // Get the controller and pass the TableView
            SongController songController = loader.getController();
            songController.setSongTableView(songTableView);

            // Create and configure the new window
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Edit Song");

            // Set an on-close event handler
            stage.setOnCloseRequest(event -> {
                refreshTableView(); // Call a method in the SongController when the window closes
            });

            // Show the window
            stage.show();
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

        refreshTableView();

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



    public void playPlayListSongs() {
        // add the songs in the play list to the player list
        songManager.removeAllSongs();
        songsInPlaylistObservable.forEach(song ->songManager.addSong(song));
        songManager.updatePlaylist(songsInPlaylistObservable);


        player.changeSongs(songManager.getMediaPlayerSongs());
        player.setCurrentSongIndex(0);
        onResumePauseButtonClicked();
        player.playSong();
        System.out.println("play playlist it works!!");
    }


    private void handlePlaylistSelection(MouseEvent event) {
        Playlist selectedPlaylist = playlistTableView.getSelectionModel().getSelectedItem();
        if (selectedPlaylist != null) {
            playlistSongColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
            playlistSongDurationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDuration()+""));

            songsInPlaylistObservable = FXCollections.observableArrayList(songManager.getSongByPlayListId(selectedPlaylist.getId()));
            songsInPlaylistTableView.setItems(songsInPlaylistObservable);


        }

    }

    @FXML
    private void updateDurationText() {

        double totalDuration = player.getDuration(); // Total song duration in seconds
        double currentTime = player.getCurrentSongDuration();
        String formattedCurrentTime = formatDuration(currentTime);
        String formattedTotalDuration = formatDuration(totalDuration);

        durationText.setText(formattedCurrentTime + " / " + formattedTotalDuration);
    }


    private void handleSongSelection() {
        Song selectedSong = songTableView.getSelectionModel().getSelectedItem();
        if (selectedSong != null) {
            songManager.removeAllSongs();
            songsObservable.forEach(song -> songManager.addSong(song));
            player.changeSongs(songManager.getMediaPlayerSongs());

            System.out.println("Selected song " + selectedSong.getTitle());
            player.playSong(selectedSong); // Play the selected song directly
            startUpdatingSlider(); // Start updating the slider
            onResumePauseButtonClicked(); // shift resume/pause button
            updateDurationText();
        } else {
            System.out.println("No song selected.");
        }
    }

    private String formatDuration(double seconds) {
        int minutes = (int) seconds / 60;
        int remainingSeconds = (int) seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
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
    private void onAddtoPlaylistClicked(){
        // Get the selected song and playlist
        Song selectedSong = songTableView.getSelectionModel().getSelectedItem();
        Playlist selectedPlaylist = playlistTableView.getSelectionModel().getSelectedItem();

        // Check if both song and playlist are selected
        if (selectedSong == null || selectedPlaylist == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Selection Required");
            alert.setHeaderText(null);
            alert.setContentText("Please select a song and a playlist.");
            alert.showAndWait();
            return;
        }

        // Call PlaylistManager to add the song to the playlist
        PlaylistManager playlistManager = new PlaylistManager();
        boolean success = playlistManager.addSongToPlaylist(selectedPlaylist.getId(), selectedSong);

        // Notify the user of success or failure
        if (success) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Song Added");
            alert.setHeaderText(null);
            alert.setContentText("The song has been added to the playlist.");
            alert.showAndWait();

            // Refresh the playlist's songs
            songsInPlaylistObservable.setAll(playlistManager.getPlayListSongs(selectedPlaylist.getId()));
            songsInPlaylistTableView.setItems(songsInPlaylistObservable);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to add the song to the playlist.");
            alert.showAndWait();
        }
    }


    @FXML
    private void onNextButtonClicked() {
        player.playNextSong();
        updateDurationText();
    }

    @FXML
    private void onPreviousButtonClicked() {
        player.playPreviousSong();
        updateDurationText();
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

        // refresh all the playlist list view
        playlistsObservable.setAll(songManager.getAllPlaylists());
        playlistTableView.setItems(playlistsObservable);

        // refresh all the songs in the playlists
        if(playlistTableView.getSelectionModel().getSelectedItem() != null) {
            songsInPlaylistObservable.setAll(songManager.getSongByPlayListId(playlistTableView.getSelectionModel().getSelectedItem().getId()));
            songsInPlaylistTableView.setItems(songsInPlaylistObservable);
        }

    }

    public void RemoveFromPlayList() {
        playlistController.deletePlaylist( songsInPlaylistTableView.getSelectionModel().getSelectedItem().getPlaylist_id());
        songsInPlaylistObservable.setAll(songsObservable);

        refreshTableView();

    }
}