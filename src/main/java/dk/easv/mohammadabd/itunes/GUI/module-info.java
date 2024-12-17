module dk.easv.mohammadabd.itunes {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;  // Include this if you're using javafx.base functionality (like for Property and ObservableValue)

    // Opening these packages to javafx.fxml for reflective access (required for FXMLLoader)
    opens dk.easv.mohammadabd.itunes to javafx.fxml;
    opens dk.easv.mohammadabd.itunes.GUI to javafx.fxml;

    // Export the necessary packages that you want to make available to other modules
    exports dk.easv.mohammadabd.itunes.GUI;
}
