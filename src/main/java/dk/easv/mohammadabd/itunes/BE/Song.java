package dk.easv.mohammadabd.itunes.BE;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Time;

public class Song {
    private int ID;
    private final StringProperty title;
    private final StringProperty artist;
    private final StringProperty genre;
    private Time duration;
    private String filePath;
    private String album;
    public Song(int ID, String title, String artist, String genre, Time duration, String filePath, String album) {
        this.ID = ID;
        this.title = new SimpleStringProperty(title);
        this.artist = new SimpleStringProperty(artist);
        this.genre = new SimpleStringProperty(genre);
        this.duration = duration;
        this.filePath = filePath;
        this.album = album;
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

    public Time getDuration() {
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

    public void setDuration(Time duration) {
        this.duration = duration;
    }
}
