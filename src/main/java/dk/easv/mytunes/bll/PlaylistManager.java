package dk.easv.mytunes.bll;
import java.util.HashMap;
import java.util.Map;

public class PlaylistManager {
    private Map<String, Playlist> playlists;

    public PlaylistManager() {
        this.playlists = new HashMap<>();
}
    public Playlist createPlaylist(String name) {
        Playlist playlist = new Playlist(name);
        playlists.put(name, playlist);
        System.out.println("Created playlist: " + name);
        return playlist;
    }

    public void deletePlaylist(Playlist playlist) {
        playlists.remove(playlist.getName());
        System.out.println("Deleted playlist: " + playlist.getName());
    }

    // retrieve a list all playlists
    public Map<String, Playlist> getPlaylists() {
        return playlists;
    }

    // Method to list all available playlists

    public void listPlaylists() {
        System.out.println("Available Playlists:");
        for (String name : playlists.keySet()) {
            System.out.println("- " + name);
        }
    }






}
