package dk.easv.mohammadabd.itunes.GUI.model;

import dk.easv.mohammadabd.itunes.BE.Song;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SongManager {
    private final List<Song> songs;

    public SongManager() {
        this.songs = new ArrayList<>();
    }

    public void addSong(Song song) {
        songs.add(song);
        System.out.println("Song added: " + song.getTitle());
    }

    public void removeSong(Song song) {
        songs.remove(song);
        System.out.println("Song removed: " + song.getTitle());
    }

    public List<Song> filterSongs(String query) {
        return songs.stream()
                .filter(song -> song.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                        song.getArtist().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Song> getSongs() {
        return new ArrayList<>(songs);
    }
}

