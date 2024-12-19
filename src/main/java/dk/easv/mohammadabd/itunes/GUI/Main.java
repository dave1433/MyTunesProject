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

        // Fetch all songs from the database
        var songs = dbs.getAllSongs();
        System.out.println("Fetched " + songs.size() + " songs from the database. & fetch genre based on get(1) which is in this example: " + songs.get(1).getGenre());
        // getting a song by the id
        dbs.getSongById(2);

        //getting a song by title
        dbs.getSongByTitle("The Best Of Me");
        dbs.getSongByTitle("Hello");

        //getting a song by genre
        dbs.getSongsByGenre("Rock");

        //getting a song by artist name
        dbs.getSongByArtist("Bad boys");

    }

    public static void main(String[] args) {
        launch();
    }
}