package scrapper.gui;

import scrapper.logic.Main;
import scrapper.logic.ShopScrapper;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MainGUI extends Application {

    private String selectedFilePath;

    @Override
    public void start(Stage stage) {
        Button button = new Button("Start scrapping");
        Button browserPath = new Button("Select your browser");

        // Chose Browser
        browserPath.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select your browser");
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                selectedFilePath = selectedFile.getAbsolutePath();
                System.out.println("Selected element : " + selectedFilePath);
                ShopScrapper.setBrowserRoot(selectedFilePath);
            }
        });

        // Start scrapping
        button.setOnAction(event -> {
            Main.main(new String[0]);
        });

        VBox root = new VBox(10, browserPath, button); // les deux boutons l’un sous l’autre
        Scene scene = new Scene(root, 300, 200);
        stage.setScene(scene);
        stage.setTitle("Test Tab");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
