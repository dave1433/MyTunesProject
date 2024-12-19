package com.rdco24.mytunesprojectdemodave.gui;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.rdco24.mytunesprojectdemodave.dal.db.dbConnector;
import com.rdco24.mytunesprojectdemodave.dal.db.DBsong;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MyTunesApp extends Application {

    @Override
    public void start(Stage stage) throws IOException, SQLServerException {
        // Load the FXML and initialize the scene
        FXMLLoader fxmlLoader = new FXMLLoader(MyTunesApp.class.getResource("/com/rdco24/mytunesprojectdemodave/gui/MyTunes-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 820, 500);
        stage.setTitle("Music Player");
        stage.setScene(scene);
        stage.show();

        // database connection
        dbConnector db = new dbConnector();
        db.getConnection();

        DBsong dbs = new DBsong();

        System.out.println(dbs.getAllSongs());
    }

    public static void main(String[] args) {
        launch();
    }

}