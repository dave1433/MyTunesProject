package com.rdco24.mytunesprojectdemodave.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MyTunesController implements Initializable {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to MyTunes Application!");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}