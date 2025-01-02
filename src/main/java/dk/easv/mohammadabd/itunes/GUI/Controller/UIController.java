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
import javafx.scene.control.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;

import java.io.IOException;

public class UIController {

    // private final PlaylistManager playlistManager = new PlaylistManager();
    private SongManager songManager = new SongManager();
    Player player = new Player(songManager.getAllSongs());

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

        // Add the songs to media player to be ready to play
        songsObservable.forEach(song -> songManager.addSong(song));
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

    @FXML
    public void enableDragAndDropForSongs() {
        songTableView.setRowFactory(tv -> {
            TableRow<Song> row = new TableRow<>();

            // Set up drag detected event
            row.setOnDragDetected(event -> {
                if (!row.isEmpty()) {
                    Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent content = new ClipboardContent();
                    content.putString(String.valueOf(row.getIndex()));
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
                    int draggedIndex = Integer.parseInt(db.getString());
                    Song draggedSong = songTableView.getItems().remove(draggedIndex);

                    int dropIndex;
                    if (row.isEmpty()) {
                        dropIndex = songTableView.getItems().size();
                    } else {
                        dropIndex = row.getIndex();
                    }

                    songTableView.getItems().add(dropIndex, draggedSong);
                    songTableView.getSelectionModel().select(dropIndex);

                    // Update the underlying observable list
                    songsObservable.setAll(songTableView.getItems());

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
            player.playSong(selectedSong); // Play the selected song directly
            System.out.println("Selected song: " + selectedSong.getTitle());
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
            Song selectedSong = songTableView.getSelectionModel().getSelectedItem();
            if (selectedSong != null) {
                if (player.isPaused()) {
                    player.resume(); // Resume if paused
                } else {
                    player.playSong(selectedSong); // Play the selected song
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