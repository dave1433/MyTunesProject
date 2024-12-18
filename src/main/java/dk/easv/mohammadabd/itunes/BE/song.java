package dk.easv.mohammadabd.itunes.BE;

public class song {
    private int ID;
    private String title;
    private String artist;
    private String genre;
    private float duration;
    private String filePath;

    public song(int ID, String title, String artist, String genre, float duration, String filePath) {
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
    public float getDuration() {
        return duration;
    }
    public String getFilePath() {
        return filePath;
    }

    public void setID(int ID) {this.ID = ID;}
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
    public void setDuration(float duration) {
        this.duration = duration;
    }
}
