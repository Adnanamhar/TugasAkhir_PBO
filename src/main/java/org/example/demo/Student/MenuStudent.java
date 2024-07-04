package org.example.demo.Student;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import org.example.demo.DarkLightMode;
import org.example.demo.LoginStudent;

import static org.example.demo.LoginAdmin.bgAll;

public class MenuStudent extends Application {

    @Override
    public void start(Stage primaryStage) {

        AnchorPane root = new AnchorPane();
        root.setPrefSize(700, 500);

        ImageView backgroundViews = new ImageView(new Image(bgAll));
        backgroundViews.setPreserveRatio(false);

        root.getChildren().add(backgroundViews);

        root.widthProperty().addListener((obs, oldVal, newVal) -> {
            backgroundViews.setFitWidth(newVal.doubleValue());
        });
        root.heightProperty().addListener((obs, oldVal, newVal) -> {
            backgroundViews.setFitHeight(newVal.doubleValue());
        });

        Label titleLabel = new Label("Student Menu");
        titleLabel.setLayoutX(243.0);
        titleLabel.setLayoutY(60.0);
        titleLabel.setFont(new Font("System Bold", 36.0));

        Button logoutButton = new Button("Logout");
        logoutButton.setLayoutX(14.0);
        logoutButton.setLayoutY(446.0);
        logoutButton.setPrefSize(119.0, 40.0);
        logoutButton.setFont(new Font("System Bold", 14.0));

        logoutButton.setOnAction(actionEvent -> {
            LoginStudent loginStudent = new LoginStudent();
            loginStudent.start(primaryStage);
        });

        Button borrowBookButton = new Button("Borrow Book");
        borrowBookButton.setLayoutX(275.0);
        borrowBookButton.setLayoutY(172.0);
        borrowBookButton.setPrefSize(177.0, 68.0);
        borrowBookButton.setFont(new Font("System Bold", 18.0));

        borrowBookButton.setOnAction(actionEvent -> {
            BorrowBook borrowBook = new BorrowBook();
            borrowBook.start(primaryStage);
        });

        Button borrowedBookButton = new Button("Borrowed Book");
        borrowedBookButton.setLayoutX(275.0);
        borrowedBookButton.setLayoutY(277.0);
        borrowedBookButton.setPrefSize(177.0, 68.0);
        borrowedBookButton.setFont(new Font("System Bold", 18.0));

        borrowedBookButton.setOnAction(actionEvent -> {
            Borrowed borrowed = new Borrowed();
            borrowed.start(primaryStage);
        });

        root.getChildren().addAll(titleLabel, logoutButton, borrowBookButton, borrowedBookButton);

        Scene scene = new Scene(root);
        primaryStage.setTitle("Student Menu");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Apply theme
        DarkLightMode.applyTheme(root);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
