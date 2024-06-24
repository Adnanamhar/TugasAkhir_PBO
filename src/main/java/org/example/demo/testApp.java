package org.example.demo;

import javafx.application.Application;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class testApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Dark Light Mode Switch Example");

        VBox root = new VBox();
        Scene scene = new Scene(root, 400, 300);

        // Button to switch themes
        Button switchButton = new Button("Switch Mode");
        switchButton.getStyleClass().add("button");
        switchButton.setOnAction(event -> {
            DarkLightMode.toggleTheme();
            DarkLightMode.applyTheme(root);
        });

        root.getChildren().add(switchButton);

        // Apply the initial theme
        DarkLightMode.applyTheme(root);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

