package com.aleddineabsi.scrapper.gui;

import com.aleddineabsi.scrapper.logic.Main;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainGUI extends Application {

    @Override
    public void start(Stage stage) {
        Button button = new Button("Start scrapping");
        button.setOnAction(event -> {
            Main.main(new String[0]);
        });
        StackPane root = new StackPane(button);
        stage.setTitle("Test Tab");
        Label label = new Label("Hello World!");
        Scene scene = new Scene(root, 300, 200);
        stage.setScene(scene);
        stage.show();
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
