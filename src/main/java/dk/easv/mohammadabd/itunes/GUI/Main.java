package dk.easv.mohammadabd.itunes.GUI;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.mohammadabd.itunes.DAL.dbConnector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

import dk.easv.mohammadabd.itunes.DAL.DBsong;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException, SQLServerException {
        // Load the FXML and initialize the scene
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/dk/easv/mohammadabd/itunes/GUI/MainView.fxml"));
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