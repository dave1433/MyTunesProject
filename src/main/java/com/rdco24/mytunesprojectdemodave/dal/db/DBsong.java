package com.rdco24.mytunesprojectdemodave.dal.db;


import com.rdco24.mytunesprojectdemodave.be.Song;
import com.rdco24.mytunesprojectdemodave.dal.db.dbConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBsong {

    // Method to fetch data from the song table
    public List<Song> getAllSongs() {
        List<Song> songs = new ArrayList<>();
        String query = "SELECT * FROM songs"; // Adjust the query as per your table structure/schema

        try (Connection connection = new dbConnector().getConnection(); 
             PreparedStatement preparedStatement = connection.prepareStatement(query); 
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                // Example columns in a table named 'song' (adapt based on your table structure)
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String artist = resultSet.getString("artist");
                double duration = resultSet.getDouble("duration");

                // Add the song object to the list (ensure a Song class exists in your BE folder)
                songs.add(new Song(id, title, artist, duration));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return songs;
    }
}