package ru.makar.icss.lab3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;

import static ru.makar.icss.lab3.Constants.PATH_APP_STYLE;
import static ru.makar.icss.lab3.Constants.PATH_MAIN_LAYOUT;

public class Launcher extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        URL resource = getClass().getClassLoader().getResource(PATH_MAIN_LAYOUT);
        if (resource == null) {
            System.err.println(PATH_MAIN_LAYOUT + " not found");
            System.exit(0);
        }
        AnchorPane root = FXMLLoader.load(resource);
        root.getStylesheets().add(PATH_APP_STYLE);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Генератор отчетов");
        stage.show();
        stage.setResizable(false);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
