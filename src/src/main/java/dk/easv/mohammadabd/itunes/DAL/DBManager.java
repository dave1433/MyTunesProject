package dk.easv.mohammadabd.itunes.DAL;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private final StringProperty song;
    private final ListProperty<String> playlist;
    private Connection connection;

    public DBManager() {
        this.song = new SimpleStringProperty();
        this.playlist = new SimpleListProperty<>();

    }

    //Getter and setter for 'song'

    public String getSong() {
        return song.get();
    }

    public void setSong(String song) {
        this.song.set(song);
    }

    public StringProperty songProperty() {
        return song;
    }

    //Getter and setter for 'playlist'

    public ObservableList<String> getPlaylist() {
        return playlist.get();
    }

    public void setPlaylist(List<String> playlist) {
        this.playlist.set(FXCollections.observableArrayList(playlist));
    }

    public ListProperty<String> playlistProperty() {
        return playlist;
    }


    public void saveSong(String song){
        String query = "INSERT INTO myTunesOGsongs (songs) VALUES (?)";
        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1,song);
            statement.executeUpdate();
            System.out.println("Song saved: " + song);
        }catch (SQLException e) {
            System.err.println("error saving song: " + e.getMessage());
        }
    }

    public void savePlaylist(List<String>playlist){
        String query = "INSERT INTO myTunesOG.playlists (playlist) VALUES (?)";
        try(PreparedStatement statement = connection.prepareStatement(query)){
            for(String playlists:playlist){
                statement.setString(1, playlists);
                statement.addBatch();
            }
            statement.executeBatch();
            System.out.println("Playlist saved: " + playlist);
        } catch (SQLException e){
            System.err.println("error saving playlist: " + e.getMessage());
        }
    }

    public List<String> loadSongs() {
        List<String> songs = new ArrayList<>();
        String query = "SELECT song FROM myTunesOG.songs";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                songs.add(resultSet.getString("name"));
            }
            System.out.println("Songs loaded: " + songs);
        } catch (SQLException e) {
            System.err.println("Error loading songs: " + e.getMessage());
        }
        return songs;
    }
    public List<String> loadPlaylists(){
        List<String>loadPlaylists = new ArrayList<>();
        String query = "SELECT playlists FROM myTunesOG.playlists";
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()){
                playlist.add(resultSet.getString("name"));
            }
            System.out.println("Playlist loaded: " + playlist);
        } catch (SQLException e) {
            System.err.println("Error loading songs: " + e.getMessage());
        }
        return loadPlaylists;
    }

    public static void main (String[] args) {
        DBManager manager = new DBManager();

        manager.saveSong("Imagine - John Lenon");
        manager.savePlaylist(List.of("CHill Vibes", "Workout", "Classics"));

        List<String>loadedSongs = manager.loadSongs();
        List<String> loadedPlaylists = manager.loadPlaylists();

        System.out.println("Loaded song:" + loadedSongs);
        System.out.println("Loaded playlist:" + loadedPlaylists);
    }

}