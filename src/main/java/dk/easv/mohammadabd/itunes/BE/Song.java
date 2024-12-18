package dk.easv.mohammadabd.itunes.BE;

import java.sql.Time;

public class Song {
    private int ID;
    private String title;
    private String artist;
    private String genre;
    private Time duration;
    private String filePath;

    public Song(int ID, String title, String artist, String genre, Time duration, String filePath) {
        this.ID = ID;
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.duration = duration;
        this.filePath = filePath;
    }

    public int getID() {
        return ID;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getGenre() {
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }
}