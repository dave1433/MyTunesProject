package dk.easv.mohammadabd.itunes.GUI;
import dk.easv.mohammadabd.itunes.BE.Song;
import dk.easv.mohammadabd.itunes.DAL.DBsong;
import dk.easv.mohammadabd.itunes.GUI.Controller.PlaylistController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.Time;

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
      //  DBsong dbs = new DBsong();

        // Fetch all songs from the database
       // var songs = dbs.getAllSongs();
        //System.out.println("Fetched " + songs.size() + " songs from the database. & fetch genre based on get(1) which is in this example: " + songs.get(1).getGenre());
        // getting a song by the id
        //dbs.getSongById(2);

        //getting a song by title
       // dbs.getSongByTitle("The Best Of Me");
       // dbs.getSongByTitle("Hello");

        //getting a song by genre
        //dbs.getSongsByGenre("Rock");

        //getting a song by artist name
       // dbs.getSongByArtist("Bad boys");
       // Song song = new Song(1, "test", "test", "test", 14000, "./music/file.mp3", "test", 1);


        // add song to the table
        //dbs.addSong(song);


        //update song by id
       // dbs.updateSong(song, 1);

        // delete a song from the table
       // dbs.deleteSong(1);


        // using the BLL layer
        PlaylistController controller = new PlaylistController();

        // Display all playlists
       // controller.showAllPlaylists();

        // Create a new playlist
       // controller.createNewPlaylist("Rock Classics", 5, 15000);

        // Show playlist by ID
       // controller.showPlaylistById(1);

        // Update an existing playlist
       // controller.updatePlaylist(1, "Rock Classics Updated", 6, 16000);

        // Delete a playlist
      //  controller.deletePlaylist(3);

        // Add a song to a playlist (assuming you have a song object)
        Song song2 = new Song(1, "Bohemian Rhapsody", "Queen", "Rock", 8700, "/path/to/song", "A Night at the Opera", 4);
        //controller.addSongToPlaylist(1, song2);
       // controller.addSongToPlaylist(1, song);
       // controller.showPlaylistById(1);
       // controller.showPlayListSongs(1);


        // getting all the playlists
        controller.showAllPlaylists();

        controller.showPlayListSongs(4);
        controller.showPlaylistById(4);
    }


    public static void main(String[] args) {
        launch();
    }
}