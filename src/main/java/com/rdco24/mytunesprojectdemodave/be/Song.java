package com.rdco24.mytunesprojectdemodave.be;


public class Song {
    private String title;
    private String artist;
    private double duration;
    private String genre;
    private String filePath;

    public Song(String title, String artist, double duration, String genre,String filePath){
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.genre=genre;
        this.filePath = filePath;
    }

    public Song(int id, String title, String artist, double duration) {
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getArtist(){
        return artist;
    }
    public void setArtist(String artist){
        this.artist= artist;
    }
    public double getDuration(){
        return duration;
    }
    public void setDuration(double duration){
        this.duration = duration;
    }
    public String getGenre(){
        return genre;
    }
    public void setGenre(String genre){
        this.genre=genre;
    }
    public String getFilePath(){
        return filePath;
    }
    public void setFilePath(String filePath){
        this.filePath=filePath;
    }
    public String toString() {
        return "Song{" +
                "title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", genre='" + genre + '\'' +
                ", duration=" + duration +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
