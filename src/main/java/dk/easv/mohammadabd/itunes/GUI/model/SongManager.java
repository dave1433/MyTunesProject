package dk.easv.mohammadabd.itunes.GUI.model;

import dk.easv.mohammadabd.itunes.BE.Playlist;
import dk.easv.mohammadabd.itunes.BE.Song;
import dk.easv.mohammadabd.itunes.BLL.PlaylistManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class SongManager {
    private final List<Song> songs;
     PlaylistManager playlistManager = new PlaylistManager();
    public SongManager() {
        this.songs = new ArrayList<>();
    }

    public List<Song> getMediaPlayerSongs() {
        return this.songs;
    }

    public void updateSongOrder(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex < 0 || fromIndex >= songs.size() || toIndex >= songs.size()) {
            return; // Invalid indices
        }

        Song songToMove = songs.remove(fromIndex); // Remove the song from the original position
        songs.add(toIndex, songToMove); // Add it to the new position
    }

    public void updatePlaylist(ObservableList<Song> newPlaylist) {
        this.songs.clear();
        this.songs.addAll(newPlaylist);
    }


    public void addSong(Song song) {
        songs.add(song);
        System.out.println("Song added: " + song.getTitle());
    }

    public void removeSong(Song song) {
        if (songs.remove(song)) {
            System.out.println("Song removed: " + song.getTitle());
        } else {
            System.out.println("Song not found: " + song.getTitle());
        }
    }

    public void removeAllSongs() {
        // Check if the List is not null
        if (songs != null) {
            songs.clear();  // Removes all songs from the list
            System.out.println("All songs have been removed.");
        } else {
            System.out.println("The list of songs is null.");
        }
    }

    public List<Song> filterSongs(String query) {
         List<Song> searchForSong;
         searchForSong = this.getAllSongs();
        return searchForSong.stream()
                .filter(song -> song.getTitle().toLowerCase().contains(query.toLowerCase())
                        || song.getTitle().toLowerCase().contains(query.toLowerCase())
                        || song.getArtist().toLowerCase().contains(query.toLowerCase())
                        || song.getGenre().toLowerCase().contains(query.toLowerCase())

                )
                .collect(Collectors.toList());
    }

    public  List<Song> getAllSongs() {
        return playlistManager.getAllSongs();
    }

    public List<Playlist> getAllPlaylists() {
        return playlistManager.getAllPlaylists();
    }

    public Song getSongByIndex(int index) {
        if (index >= 0 && index < songs.size()) {
            return songs.get(index);
        } else {
            return null;
        }
    }

    public List<Song> getSongByPlayListId(int playlistId) {
        return playlistManager.getSongById(playlistId);
    }



}
