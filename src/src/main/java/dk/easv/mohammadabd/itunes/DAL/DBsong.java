package dk.easv.mohammadabd.itunes.DAL;

import dk.easv.mohammadabd.itunes.BE.Song;

import javax.management.DescriptorKey;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBsong {
    // Method to fetch data from the song table
    public List<Song> getAllSongs() {
        List<Song> songs = new ArrayList<>();
        // Correct the column names based on the database table schema
        String query = "select * from myTunesOG.songs";

        // Attempt to connect to the database and execute the query
        try (Connection connection = new dbConnector().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            System.out.println("Executing query: " + query);

            // Process the results
            while (resultSet.next()) {
                int id = resultSet.getInt("song_id");
                String title = resultSet.getString("title");
                String artist = resultSet.getString("album_id");
                String genre = resultSet.getString("genre");
                long duration = resultSet.getLong("duration");
                String filePath = resultSet.getString("file_Path");
                String album = resultSet.getString("album_id");
                int playlistId = resultSet.getInt("playlist_id");

                // Add a new Song object to the list
                songs.add(new Song(id, title, artist, genre, duration, filePath, album, playlistId));
            }

        } catch (SQLException e) {
            System.err.println("Error while fetching songs from database.");
            e.printStackTrace();
        }

        // Check if songs list is empty
        if (songs.isEmpty()) {
            System.out.println("No songs found in the database.");
        }

        return songs;
    }

    public List<Song> getplaylist_id(int playlistid) {
        List<Song> songslist = new ArrayList<>();
        // Query to fetch songs for the given playlist ID
        String query = "SELECT * FROM myTunesOG.songs WHERE playlist_id = ?";
        // Attempt to connect to the database and execute the query
        try (Connection connection = new dbConnector().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the parameter for the query (playlist_id)
            preparedStatement.setInt(1, playlistid);
            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                System.out.println("Executing query: " + query);

                // Iterate through all the result rows and process each song
                while (resultSet.next()) {
                    int id = resultSet.getInt("song_id");
                    String title = resultSet.getString("title");
                    String artist = resultSet.getString("artist");  // Corrected: Assuming there's an "artist" column
                    String genre = resultSet.getString("genre");
                    long duration = resultSet.getLong("duration");
                    String filePath = resultSet.getString("file_Path");
                    String album = resultSet.getString("album_id");
                    int playlistId = resultSet.getInt("playlist_id");

                    // Create a Song object from the data
                    Song song = new Song(id, title, artist, genre, duration, filePath, album, playlistId);
                    System.out.println("Loaded song: ID=" + id + ", Title=" + title + ", Artist=" + artist + ", Genre=" + genre + ", Duration=" + duration + ", FilePath=" + filePath + ", Album=" + album + ", PlaylistId=" + playlistId);

                    // Add the song to the list
                    songslist.add(song);
                }

                // If no songs were found, print a message
                if (songslist.isEmpty()) {
                    System.out.println("No songs found for playlist_id " + playlistid);
                }

            }

        } catch (SQLException e) {
            System.err.println("Error while fetching songs with playlist_ID: " + playlistid);
            e.printStackTrace();
        }

        return songslist;
    }

    public Song getSongById(int songId) {
        Song song = null;

        // Query to fetch a specific song by its ID
        String query = "SELECT * FROM myTunesOG.songs WHERE song_id = ?";

        // Attempt to connect to the database and execute the query
        try (Connection connection = new dbConnector().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the parameter for the query
            preparedStatement.setInt(1, songId);

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                System.out.println("Executing query: " + query);

                // Process the result (if a song is found)
                if (resultSet.next()) {
                    int id = resultSet.getInt("song_id");
                    String title = resultSet.getString("title");
                    String artist = resultSet.getString("album_id");
                    String genre = resultSet.getString("genre");
                    long duration = resultSet.getLong("duration");
                    String filePath = resultSet.getString("file_Path");
                    String album = resultSet.getString("album_id");
                    int playlistId = resultSet.getInt("playlist_id");
                    // Create and return the Song object
                    song = new Song(id, title, artist, genre, duration, filePath, album, playlistId);
                    System.out.println("Loaded song: ID=" + id + ", Title=" + title + ", Artist=" + artist);
                } else {
                    System.out.println("No song found with ID: " + songId);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error while fetching song with ID: " + songId);
            e.printStackTrace();
        }

        return song;
    }

    public Song getSongByTitle(String songTitle) {
        Song song = null;

        // Query to fetch a specific song by its title
        String query = "SELECT * FROM myTunesOG.songs WHERE title = ?";

        // Attempt to connect to the database and execute the query
        try (Connection connection = new dbConnector().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the parameter for the query
            preparedStatement.setString(1, songTitle);

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                System.out.println("Executing query: " + query);

                // Process the result (if a song is found)
                if (resultSet.next()) {
                    int id = resultSet.getInt("song_id");
                    String title = resultSet.getString("title");
                    String artist = resultSet.getString("album_id");
                    String genre = resultSet.getString("genre");
                    long duration = resultSet.getLong("duration");
                    String filePath = resultSet.getString("file_Path");
                    String album = resultSet.getString("album_id");
                    int playlistId = resultSet.getInt("playlist_id");
                    // Create and return the Song object
                    song = new Song(id, title, artist, genre, duration, filePath, album, playlistId);
                    System.out.println("Loaded song: Title='" + title + "', ID=" + id + ", Artist=" + artist);
                } else {
                    System.out.println("No song found with Title: " + songTitle);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error while fetching song with Title: " + songTitle);
            e.printStackTrace();
        }

        return song;
    }

    public List<Song> getSongsByGenre(String genre) {
        List<Song> songs = new ArrayList<>();

        // Query to fetch songs by genre ID
        String query = "SELECT * FROM myTunesOG.songs WHERE genre = ?";

        // Attempt to connect to the database and execute the query
        try (Connection connection = new dbConnector().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the parameter for the query
            preparedStatement.setString(1, genre);

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                System.out.println("Executing query: " + query);

                // Process the results
                while (resultSet.next()) {
                    int id = resultSet.getInt("song_id");
                    String title = resultSet.getString("title");
                    String artist = resultSet.getString("album_id");
                    String Genre = resultSet.getString("genre");
                    long duration = resultSet.getLong("duration");
                    String filePath = resultSet.getString("file_Path");
                    String album = resultSet.getString("album_id");
                    int playlistId = resultSet.getInt("playlist_id");
                    // Add the song to the list
                    songs.add(new Song(id, title, artist, Genre, duration, filePath, album, playlistId));
                    System.out.println("Loaded song: Title='" + title + "', Genre='" + Genre + "'");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error while fetching songs with Genre ID: " + genre);
            e.printStackTrace();
        }

        // Check if no songs were found
        if (songs.isEmpty()) {
            System.out.println("No songs found with Genre ID: " + genre);
        }

        return songs;
    }



    public List<Song> getSongByArtist(String artist) {
        List<Song> songs = new ArrayList<>();

        // Query to fetch songs by genre ID
        String query = "SELECT * FROM myTunesOG.songs WHERE artist = ?";

        // Attempt to connect to the database and execute the query
        try (Connection connection = new dbConnector().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the parameter for the query
            preparedStatement.setString(1, artist);

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                System.out.println("Executing query: " + query);

                // Process the results
                while (resultSet.next()) {
                    int id = resultSet.getInt("song_id");
                    String title = resultSet.getString("title");
                    String Artist = resultSet.getString("album_id");
                    String Genre = resultSet.getString("genre");
                    long duration = resultSet.getLong("duration");
                    String filePath = resultSet.getString("file_Path");
                    String album = resultSet.getString("album_id");
                    int playlistId = resultSet.getInt("playlist_id");
                    // Add the song to the list
                    songs.add(new Song(id, title, Artist, Genre, duration, filePath, album, playlistId));
                    System.out.println("Loaded song: Title='" + title + "', Genre='" + Genre + "'");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error while fetching songs by Artist name: " + artist);
            e.printStackTrace();
        }

        // Check if no songs were found
        if (songs.isEmpty()) {
            System.out.println("No songs found with Artist name: " + artist);
        }

        return songs;
    }

    // add new song to database
    public boolean addSong(Song song) {
        String query = "INSERT INTO myTunesOG.Songs (title, artist, genre, duration, file_Path, album_id, playlist_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // Try-with-resources ensures the connection gets closed automatically
        try (Connection connection = new dbConnector().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set parameters for the query
            preparedStatement.setString(1, song.getTitle());
            preparedStatement.setString(2, song.getArtist());
            preparedStatement.setString(3, song.getGenre());
            preparedStatement.setLong(4, song.getDuration());
            preparedStatement.setString(5, song.getFilePath());
            preparedStatement.setString(6, song.getAlbum());
            preparedStatement.setInt(7, song.getPlaylist_id());

            // Execute the insert query
            int rowsInserted = preparedStatement.executeUpdate();
            System.out.println("Successfully added new song: " + song.getTitle());

            return rowsInserted > 0;

        } catch (SQLException e) {
            System.err.println("Error while adding new song to database.");
            e.printStackTrace();
            return false;
        }
    }


    public boolean updateSong(Song song, int id) {
        String query = "UPDATE myTunesOG.Songs SET title = ?, artist = ?, genre = ?, duration = ?, file_Path = ?, album_id = ?, playlist_id = ? WHERE song_id = ?";

        // Try-with-resources ensures the connection gets closed automatically
        try (Connection connection = new dbConnector().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set parameters for the query
            preparedStatement.setString(1, song.getTitle());
            preparedStatement.setString(2, song.getArtist());
            preparedStatement.setString(3, song.getGenre());
            preparedStatement.setLong(4, song.getDuration());
            preparedStatement.setString(5, song.getFilePath());
            preparedStatement.setString(6, song.getAlbum());
            preparedStatement.setInt(7, song.getPlaylist_id());

            preparedStatement.setString(8, String.valueOf(id));


            // Execute the update query
            int rowsUpdated = preparedStatement.executeUpdate();
            System.out.println("Successfully updated song: " + song.getTitle());

            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.err.println("Error while updating song in database.");
            e.printStackTrace();
            return false;
        }
    }


    public boolean deleteSong(int songId) {
        String query = "DELETE FROM myTunesOG.Songs WHERE song_id = ?";

        // Try-with-resources ensures the connection gets closed automatically
        try (Connection connection = new dbConnector().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the song_id to the prepared statement
            preparedStatement.setInt(1, songId);

            // Execute the delete query
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Successfully deleted song with ID: " + songId);
            }

            return rowsDeleted > 0;

        } catch (SQLException e) {
            System.err.println("Error while deleting song from database.");
            e.printStackTrace();
            return false;
        }
    }


}