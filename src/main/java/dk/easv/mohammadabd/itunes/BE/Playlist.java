package dk.easv.mohammadabd.itunes.BE;

import dk.easv.mohammadabd.itunes.BE.Song;
import dk.easv.mohammadabd.itunes.DAL.DBplaylist;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private int id; // ID for the playlist, fetched from DB
    private String name;
    private List<Song> songs; // List to store songs associated with this playlist
    private int totalSongs;
    private long totalDuration; // Total duration of all songs in milliseconds

    // Constructor
    public Playlist(String name) {
        this.name = name;
        this.songs = new ArrayList<>();
        this.totalSongs = 0;
        this.totalDuration = 0;
    }

    // Constructor to load an existing playlist from the DB
    public Playlist(int id, String name, int totalSongs, long totalDuration) {
        this.id = id;
        this.name = name;
        this.songs = new ArrayList<>();
        this.totalSongs = totalSongs;
        this.totalDuration = totalDuration;
    }



    // Getter and Setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaylistName() {
        return name;
    }

    public SimpleStringProperty getName() {
        return new SimpleStringProperty(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Song> getSongs() {
        return songs;
    }


    public void setSongs(List<Song> songs) {
        this.songs = songs;
        updatePlaylistDetails();
    }

    public int getTotalSongs() {
        return totalSongs;
    }

    public long getTotalDuration() {
        return totalDuration;
    }

    // Method to add a song to the playlist
    public void addSong(Song song) {
        songs.add(song);
        totalSongs++;
        totalDuration += song.getDuration(); // Add song duration to total
        System.out.println("Added song: " + song.getTitle() + " to playlist " + name);
    }

    // Method to remove a song from the playlist
    public void removeSong(Song song) {
        songs.remove(song);
        totalSongs--;
        totalDuration -= song.getDuration(); // Subtract song duration from total
        System.out.println("Removed song: " + song.getTitle() + " from playlist " + name);
    }

    // Method to update the total duration and total songs count
    public void updatePlaylistDetails() {
        totalSongs = songs.size();
        totalDuration = 0;
        for (Song song : songs) {
            totalDuration += song.getDuration(); // Sum the song durations
        }
    }

    // Fetch playlist details from the database
    public static Playlist getPlaylistById(int playlistId) {
        DBplaylist dbPlaylist = new DBplaylist();
        Playlist playlist = dbPlaylist.getPlaylistById(playlistId);

        if (playlist != null) {
            // You can load songs for the playlist if needed (assuming you have a way to link them)
            // playlist.setSongs(new DBsong().getSongsByPlaylistId(playlistId));
        }

        return playlist;
    }

    // Add the playlist to the database
    public boolean savePlaylist() {
        DBplaylist dbPlaylist = new DBplaylist();
        return dbPlaylist.addPlaylist(this);
    }

    // Update playlist in the database
    public boolean updatePlaylist() {
        DBplaylist dbPlaylist = new DBplaylist();
        return dbPlaylist.updatePlaylist(this, this.id);
    }

    // Delete the playlist from the database
    public boolean deletePlaylist() {
        DBplaylist dbPlaylist = new DBplaylist();
        return dbPlaylist.deletePlaylist(this.id);
    }

    // Method to fetch all playlists from the database
    public static List<Playlist> getAllPlaylists() {
        DBplaylist dbPlaylist = new DBplaylist();
        return dbPlaylist.getAllPlaylists();
    }

    public void setTotalSongs(int totalSongs) {
        this.totalSongs = totalSongs;
    }

    public void setTotalDuration(long totalDuration) {
        this.totalDuration = totalDuration;
    }

    // ToString method for displaying playlist info
    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", totalSongs=" + totalSongs +
                ", totalDuration=" + totalDuration +
                ", songs=" + songs +
                ", playlistId=" + id +
                '}';
    }
}