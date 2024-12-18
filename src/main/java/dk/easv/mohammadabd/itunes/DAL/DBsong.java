package dk.easv.mohammadabd.itunes.DAL;

import dk.easv.mohammadabd.itunes.BE.Song;

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
                String genre = resultSet.getString("genre_id");
                Time duration = resultSet.getTime("duration");
                String filePath = resultSet.getString("file_Path");

                // Add a new Song object to the list
                songs.add(new Song(id, title, artist, genre, duration, filePath));
                System.out.println("Loaded song: ID=" + id + ", Title=" + title + ", Artist=" + artist);
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
}