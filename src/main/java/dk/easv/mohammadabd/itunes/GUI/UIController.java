package dk.easv.mohammadabd.itunes.GUI;

import dk.easv.mohammadabd.itunes.BE.Playlist;
import dk.easv.mohammadabd.itunes.BE.Song;
import dk.easv.mohammadabd.itunes.GUI.model.SongManager;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class UIController {

    // private final PlaylistManager playlistManager = new PlaylistManager();
    private SongManager songManager = new SongManager();

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

        songManager = new SongManager();  // Initialize songManager
        searchField.setOnKeyReleased(event -> onSearchFieldUpdated());

        // Initialize observable list for TableViews
        songsObservable = FXCollections.observableArrayList(songManager.getAllSongs());
        songTableView.setItems(songsObservable);

        playlistsObservable = FXCollections.observableArrayList(songManager.getAllPlaylists());
        playlistTableView.setItems(playlistsObservable);

        // listener for Playlist TableView selection
        playlistTableView.setOnMouseClicked(this::handlePlaylistSelection);
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
    private void onRemoveSongClicked() {
        Song selectedSong = songTableView.getSelectionModel().getSelectedItem();
        if (selectedSong != null) {
            songManager.removeSong(selectedSong);
            refreshTableView();
        }
    }

    @FXML
    private void onCreatePlaylistClicked() {

    }

    @FXML
    private void onDeletePlaylistClicked() {

    }

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

    private void refreshTableView() {
        songsObservable.setAll(songManager.getAllSongs());
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
            newStage.show();  // Show the new window

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onCreatePlaylistWindow() {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/mohammadabd/itunes/GUI/newPlaylistWindow.fxml"));
            Scene scene = new Scene(loader.load());

            // Create a new stage (window)
            Stage newStage = new Stage();
            newStage.setTitle("New PlayList Window");
            newStage.setScene(scene);
            newStage.show();  // Show the new window

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onNewSongWindow() {
        try {

            Song newSong = new Song(1, "Smooth Criminal", "Mickel Jakson", "Pop", 8400, "path/to/file", "Moon", 5);
            songManager.addSong(newSong);
            refreshTableView();
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/mohammadabd/itunes/GUI/newSongWindow.fxml"));
            Scene scene = new Scene(loader.load());

            // Create a new stage (window)
            Stage newStage = new Stage();
            newStage.setTitle("New Song Window");
            newStage.setScene(scene);
            newStage.show();  // Show the new window

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onAddSongClicked(ActionEvent actionEvent) {
    }
}


