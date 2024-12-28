package dk.easv.mohammadabd.itunes.BE;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Time;

public class Song {
    private int ID;
    private final StringProperty title;
    private final StringProperty artist;
    private final StringProperty genre;
    private long duration;
    private String filePath;
    private String album;
    private int playlist_id;

    public Song(int ID, String title, String artist, String genre, long duration, String filePath, String album, int playlist_id) {
        this.ID = ID;
        this.title = new SimpleStringProperty(title);
        this.artist = new SimpleStringProperty(artist);
        this.genre = new SimpleStringProperty(genre);
        this.duration = duration;
        this.filePath = filePath;
        this.album = album;
        this.playlist_id = playlist_id;
    }

    public int getPlaylist_id(){
        return playlist_id;
    }

    public void setPlaylist_id(int playlist_id){
        this.playlist_id = playlist_id;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getID() {
        return ID;
    }

    public StringProperty titleProperty() {
        return title;
    }

    public StringProperty artistProperty() {
        return artist;
    }

    public StringProperty genreProperty() {
        return genre;
    }

    public long getDuration() {
        return duration;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getArtist() {
        return artist.get();
    }

    public void setArtist(String artist) {
        this.artist.set(artist);
    }

    public String getGenre() {
        return genre.get();
    }

    public void setGenre(String genre) {
        this.genre.set(genre);
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
    @Override
    public String toString() {
        return "{" +
                "id=" + getID() +
                ", name='" + getTitle() +
                ", totalDuration=" + getDuration() +
                ", playlistId=" + getPlaylist_id() +
                '}';
    }
}



