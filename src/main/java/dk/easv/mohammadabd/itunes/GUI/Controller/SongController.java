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

public class SongController {

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

     final DBsong dbs = new DBsong(); // Reference to the database handler
    private File selectedFile; // To hold the chosen file

    /**
     * Event handler for the "Choose" button.
     */
    @FXML
    private void onChooseSong() {
        // Logic to handle the song file selection (e.g., opening a FileChooser dialog)
        System.out.println("Choose Song button clicked.");
        // Add a FileChooser here if needed.
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

    @FXML
    private void onChooseSong2() {
        // Logic to handle the song file selection (e.g., opening a FileChooser dialog)
        System.out.println("Choose Song button clicked.");
        // Add a FileChooser here if needed.
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
            try {
                if (title.isEmpty() || artist.isEmpty() || filePath.isEmpty())  {
                    System.out.println("Please fill in all required fields!");
                    return;
                }
                long duration = Long.parseLong(String.valueOf(time));
                // Validate file
                if (selectedFile == null || (!filePath.endsWith(".mp3") && !filePath.endsWith(".wav"))) {
                    System.out.println("Please select a valid music file (.mp3, .wav).");
                    return;
                }
                dbs.addSong(new Song(1, title, artist, genre, duration,  filePath, album, playlist_id));
                System.out.println("Song added successfully!");
                // Copy the file to the resources folder
                filePath = selectedFile.getName();
                Path targetPath = Path.of("src/main/resources/music/", filePath);
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
            // Optionally, clear the fields after adding the song
            clearFields();
        }


    }

    @FXML
    private void onEditSong() {
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
            try {
                if (title.isEmpty() || artist.isEmpty() || filePath.isEmpty())  {
                    System.out.println("Please fill in all required fields!");
                    return;
                }
                long duration = Long.parseLong(String.valueOf(time));
                // Validate file
                if (selectedFile == null || (!filePath.endsWith(".mp3") && !filePath.endsWith(".wav"))) {
                    System.out.println("Please select a valid music file (.mp3, .wav).");
                    return;
                }
                dbs.addSong(new Song(1, title, artist, genre, duration,  filePath, album, playlist_id));
                System.out.println("Song added successfully!");
                // Copy the file to the resources folder
                filePath = selectedFile.getName();
                Path targetPath = Path.of("src/main/resources/music/", filePath);
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
            // Optionally, clear the fields after adding the song
            clearFields();
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