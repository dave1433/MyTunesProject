package dk.easv.mohammadabd.itunes.GUI.Controller;

import dk.easv.mohammadabd.itunes.BE.Song;
import dk.easv.mohammadabd.itunes.DAL.DBsong;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddSongController {

    @FXML
    private TextField songTitleField;

    @FXML
    private TextField songArtistField;

    @FXML
    private TextField songAlbumField;

    @FXML
    private TextField songGenreField;

    @FXML
    private TextField songTimeField;

    @FXML
    private TextField songFilePathField;

    @FXML
    private Button chooseSong;

    @FXML
    private Button addSongBtn;

    @FXML
    private TextField playlist_idField;

    private final DBsong dbs = new DBsong(); // Reference to the main controller



    /**
     * Event handler for the "Choose" button.
     */
    @FXML
    private void onChooseSong() {
        // Logic to handle the song file selection (e.g., opening a FileChooser dialog)
        System.out.println("Choose Song button clicked.");
        // Add a FileChooser here if needed.
    }

    /**
     * Event handler for the "Add" button.
     */
    @FXML
    private void onAddSong() {
        // Collect data from the input fields
        String title = songTitleField.getText();
        String artist = songArtistField.getText();
        String album = songAlbumField.getText();
        String genre = songGenreField.getText();
        long time = Long.parseLong(songTimeField.getText());
        String filePath = songFilePathField.getText();
        int playlist_id = Integer.parseInt(playlist_idField.getText());

        // Validate inputs
        if (title.isEmpty() || artist.isEmpty() || filePath.isEmpty()) {
            System.out.println("Please fill in all required fields!");
            return;
        }else {
            dbs.addSong(new Song(1,title, artist,  genre,  time, filePath , album, playlist_id ));
        }




        // Optionally, clear the fields after adding the song
        clearFields();
    }

    /**
     * Clears all input fields.
     */
    private void clearFields() {
        songTitleField.clear();
        songArtistField.clear();
        songAlbumField.clear();
        songGenreField.clear();
        songTimeField.clear();
        songFilePathField.clear();
    }
}