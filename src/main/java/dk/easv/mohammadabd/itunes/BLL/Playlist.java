package dk.easv.mohammadabd.itunes.BLL;

import dk.easv.mohammadabd.itunes.BE.Song;
import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private String name;
    private List<Song> songs;
    public Playlist(String name) {
        this.name = name;
        this.songs = new ArrayList<>(); // Initialize the song list
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void addSong(Song song) {
        songs.add(song);
        System.out.println("Added song: " + song.getTitle() + " to playlist " + name);
    }

    public void removeSong(Song song) {
        songs.remove(song);
        System.out.println("Removed song: " + song.getTitle() + " from playlist " + name);
    }

    public void moveSong(int fromIndex, int toIndex) {

//        To be implemented, moving songs from position on the playlist
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "name='" + name + '\'' +
                ", songs=" + songs +
                '}';
    }
}

