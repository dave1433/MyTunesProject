package dk.easv.mohammadabd.itunes.DAL;

import dk.easv.mohammadabd.itunes.BE.Playlist;
import dk.easv.mohammadabd.itunes.BE.Song;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBplaylist {

    // Method to fetch a playlist by ID
    public Playlist getPlaylistById(int playlistId) {
        Playlist playlist = null;

        String query = "SELECT * FROM myTunesOG.playlists_table WHERE playlist_id = ?";

        try (Connection connection = new dbConnector().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, playlistId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Retrieve playlist details from the result set
                    int id = resultSet.getInt("playlist_id");
                    String name = resultSet.getString("name");
                    int totalSongs = resultSet.getInt("total_songs");
                    long totalDuration = resultSet.getLong("all_songs_duration");

                    // Create a Playlist object and return it
                    playlist = new Playlist(id, name, totalSongs, totalDuration);

                    // Optionally, load the songs associated with this playlist
                    // You could use the getSongsByPlaylistId method if available
                    // playlist.setSongs(new DBsong().getSongsByPlaylistId(playlistId));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error while fetching playlist with ID: " + playlistId);
            e.printStackTrace();
        }

        return playlist;
    }

    // Method to fetch all playlists from the database
    public List<Playlist> getAllPlaylists() {
        List<Playlist> playlists = new ArrayList<>();

        String query = "SELECT * FROM myTunesOG.playlists_table";

        try (Connection connection = new dbConnector().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("playlist_id");
                String name = resultSet.getString("name");
                int totalSongs = resultSet.getInt("total_songs");
                long totalDuration = resultSet.getLong("all_songs_duration");

                // Add the playlist to the list
                playlists.add(new Playlist(id, name, totalSongs, totalDuration));
            }

        } catch (SQLException e) {
            System.err.println("Error while fetching all playlists.");
            e.printStackTrace();
        }

        return playlists;
    }



    // Method to add a new playlist to the database
    public boolean addPlaylist(Playlist playlist) {
        String query = "INSERT INTO myTunesOG.playlists_table (name, total_songs, all_songs_duration) VALUES (?, ?, ?)";

        try (Connection connection = new dbConnector().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            // Set parameters for the query
            preparedStatement.setString(1, playlist.getName());
            preparedStatement.setInt(2, playlist.getTotalSongs());
            preparedStatement.setLong(3, playlist.getTotalDuration());

            // Execute the insert query
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                // Retrieve the generated playlist ID
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        playlist.setId(generatedKeys.getInt(1)); // Set the generated playlist ID
                    }
                }
                System.out.println("Playlist successfully added to the database.");
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error while adding playlist to database.");
            e.printStackTrace();
        }

        return false;
    }
    // Method to add a new playlist to the database
    public boolean getSongsInPlaylist(int playlist_id, Song song) {
        String query = "select * from myTunesOG.songs where playlist_id = ?";


        try (Connection connection = new dbConnector().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            // Set parameters for the query
            preparedStatement.setInt(1,  playlist_id);

            // test adding songs to the array and displaying it.


            // Execute the insert query
            ResultSet resultSet = preparedStatement.executeQuery();
            Playlist Playlistsong = new Playlist(null);
            // Retrieve the generated playlist ID
                try (resultSet) {
                    while (resultSet.next() && resultSet.getInt("playlist_id") == playlist_id) {
                        Song songsInPlayList = new Song(resultSet.getInt("song_id"), resultSet.getString("title") , resultSet.getString("artist") , resultSet.getString("genre"), resultSet.getInt("duration") , resultSet.getString("file_path") , resultSet.getString("album_id"), resultSet.getInt("playlist_id"));

                        Playlistsong.addSong(songsInPlayList);

                    }
                }
                System.out.println("PlayListSong ArrayList in playlist is : " + Playlistsong);
                return true;


        } catch (SQLException e) {
            System.err.println("Error while adding playlist to database.");
            e.printStackTrace();
        }

        return false;
    }

    // Method to update an existing playlist in the database
    public boolean updatePlaylist(Playlist playlist, int playlistId) {
        String query = "UPDATE myTunesOG.playlists_table SET name = ?, total_songs = ?, all_songs_duration = ? WHERE playlist_id = ?";

        try (Connection connection = new dbConnector().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, playlist.getName());
            preparedStatement.setInt(2, playlist.getTotalSongs());
            preparedStatement.setLong(3, playlist.getTotalDuration());
            preparedStatement.setInt(4, playlistId);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Playlist successfully updated in the database.");
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error while updating playlist in database.");
            e.printStackTrace();
        }

        return false;
    }

    // Method to delete a playlist from the database
    public boolean deletePlaylist(int playlistId) {
        String query = "DELETE FROM myTunesOG.playlists_table WHERE playlist_id = ?";

        try (Connection connection = new dbConnector().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, playlistId);

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Playlist successfully deleted from the database.");
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error while deleting playlist from database.");
            e.printStackTrace();
        }

        return false;
    }
}
