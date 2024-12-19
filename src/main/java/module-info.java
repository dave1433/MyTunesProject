module dk.easv.mohammadabd.itunes.itunes {
    requires javafx.controls;
    requires javafx.fxml;


    opens dk.easv.mohammadabd.itunes to javafx.fxml;
    exports dk.easv.mohammadabd.itunes;
    exports dk.easv.mohammadabd.itunes.GUI;
    opens dk.easv.mohammadabd.itunes.GUI to javafx.fxml;
}