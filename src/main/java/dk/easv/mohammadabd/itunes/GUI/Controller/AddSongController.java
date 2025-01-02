package dk.easv.mohammadabd.itunes.GUI.Controller;

import dk.easv.mohammadabd.itunes.BE.Song;
import dk.easv.mohammadabd.itunes.DAL.DBsong;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;


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
    private TextField playlist_idField;

    private final DBsong dbs = new DBsong(); // Reference to the database handler
    private File selectedFile; // To hold the chosen file

    /**
     * Event handler for the "Choose" button.
     */
    @FXML
    private void onChooseSong() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a Music File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Audio Files", "*.mp3", "*.wav")
        );

        Stage stage = (Stage) chooseSong.getScene().getWindow();
        selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            songFilePathField.setText(selectedFile.getName()); // Set only the file name in the text field

        }
    }

    /**
     * Event handler for the "Add" button.
     */
    @FXML
    private void onAddSong() {
        try {
            // Collect data from the input fields
            String title = songTitleField.getText();
            String artist = songArtistField.getText();
            String album = songAlbumField.getText();
            String genre = songGenreField.getText();
            String fileName = songFilePathField.getText();
            String durationText = songTimeField.getText();
            int playlistId = Integer.parseInt(playlist_idField.getText());

            // Validate inputs
            if (title.isEmpty() || artist.isEmpty() || fileName.isEmpty() || durationText.isEmpty()) {
                System.out.println("Please fill in all required fields!");
                return;
            }

            long duration = Long.parseLong(durationText);

            // Validate file
            if (selectedFile == null || (!fileName.endsWith(".mp3") && !fileName.endsWith(".wav"))) {
                System.out.println("Please select a valid music file (.mp3, .wav).");
                return;
            }
            dbs.addSong(new Song(1, title, artist, genre, duration,  fileName, album, playlistId));

            System.out.println("Song added successfully!");

            // Copy the file to the resources folder
            fileName = selectedFile.getName();
            Path targetPath = Path.of("src/main/resources/music/", fileName);
            Files.createDirectories(targetPath.getParent());
            Files.copy(selectedFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File should be copied successfully!");
            // Add the song to the database

            // Optionally, clear the fields after adding the song
            clearFields();
        } catch (IOException e) {
            System.out.println("Error while saving the song file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input in numeric fields. Please check your inputs.");
        }
    }


    private void clearFields() {
        songTitleField.clear();
        songArtistField.clear();
        songAlbumField.clear();
        songGenreField.clear();
        songTimeField.clear();
        songFilePathField.clear();
        playlist_idField.clear();
        selectedFile = null;
    }
}
