package dk.easv.mohammadabd.itunes.GUI;

import dk.easv.mohammadabd.itunes.BE.Song;
import dk.easv.mohammadabd.itunes.BLL.Playlist;
import dk.easv.mohammadabd.itunes.BLL.PlaylistManager;
import dk.easv.mohammadabd.itunes.BLL.SongManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class UIController {

    private final PlaylistManager playlistManager = new PlaylistManager();
    private final SongManager songManager = new SongManager();

    @FXML
    private TableView<Song> songTableView;
    @FXML
    private TableColumn<Song, String> titleColumn;
    @FXML
    private TableColumn<Song, String> artistColumn;
    @FXML
    private TableColumn<Song, String> genreColumn;

    @FXML
    private TextField searchField;

    private ObservableList<Song> songsObservable;

    @FXML
    public void initialize() {
        // Initialize TableView columns
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        artistColumn.setCellValueFactory(cellData -> cellData.getValue().artistProperty());
        genreColumn.setCellValueFactory(cellData -> cellData.getValue().genreProperty());

        // Initialize observable list for TableView
        songsObservable = FXCollections.observableArrayList(songManager.getSongs());
        songTableView.setItems(songsObservable);
    }

    @FXML
    private void onAddSongClicked() {
        // Example of adding a new song
        Song newSong = new Song(1, "New Song", "Unknown Artist", "Pop", null, "path/to/file");
        songManager.addSong(newSong);
        refreshTableView();
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
        playlistManager.createPlaylist("New Playlist");
    }

    @FXML
    private void onDeletePlaylistClicked() {
        String playlistName = "New Playlist"; // Replace with the actual selected playlist name
        Playlist playlist = playlistManager.getPlaylist(playlistName);
        if (playlist != null) {
            playlistManager.deletePlaylist(playlist);
        } else {
            System.out.println("Playlist not found: " + playlistName);
        }
    }

    @FXML
    private void onSearchFieldUpdated() {
        String query = searchField.getText();
        if (query.isEmpty()) {
            songsObservable.setAll(songManager.getSongs());
        } else {
            songsObservable.setAll(songManager.filterSongs(query));
        }
    }

    private void refreshTableView() {
        songsObservable.setAll(songManager.getSongs());
    }
}
