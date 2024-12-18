package dk.easv.mohammadabd.itunes.GUI;

import dk.easv.mohammadabd.itunes.DAL.DBsong;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Load the FXML and initialize the scene
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/dk/easv/mohammadabd/itunes/GUI/MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 820, 500);
        stage.setTitle("Music Player");
        stage.setScene(scene);
        stage.show();

        // Fetch songs from the database
        fetchAndDisplaySongs();
    }

    /**
     * Helper method to fetch songs from the database and display them in the console.
     */
    private void fetchAndDisplaySongs() {
        DBsong dbs = new DBsong();
        var songs = dbs.getAllSongs(); // Fetch all songs from the database
        System.out.println("Total songs fetched: " + songs.size());

        // Print out fetched songs
        for (var song : songs) {
            System.out.println("Song [ID=" + song.getID() +
                    ", Title='" + song.getTitle() +
                    "', Artist='" + song.getArtist() +
                    "', Genre='" + song.getGenre() +
                    "', Duration=" + song.getDuration() +
                    ", FilePath='" + song.getFilePath() + "']");
        }
    }

    public static void main(String[] args) {
        launch();
    }
}