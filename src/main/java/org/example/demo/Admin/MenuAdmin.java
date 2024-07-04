package org.example.demo.Admin;

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
import org.example.demo.LoginAdmin;

import static org.example.demo.LoginAdmin.bgAll;

public class MenuAdmin extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create the AnchorPane
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

        // Create the Labels
        Label titleLabel = new Label("Admin Menu");
        titleLabel.setFont(new Font("System Bold", 36));
        titleLabel.setLayoutX(239);
        titleLabel.setLayoutY(54);

        // Create the Buttons
        Button logoutButton = new Button("Logout");
        logoutButton.setFont(new Font("System Bold", 14));
        logoutButton.setLayoutX(14);
        logoutButton.setLayoutY(446);
        logoutButton.setPrefSize(119, 40);

        logoutButton.setOnAction(actionEvent -> {
            LoginAdmin loginAdmin = new LoginAdmin();
            loginAdmin.start(primaryStage);
        });

        Button listStudentButton = new Button("List Student");
        listStudentButton.setFont(new Font("System Bold", 18));
        listStudentButton.setLayoutX(103);
        listStudentButton.setLayoutY(159);
        listStudentButton.setPrefSize(212, 76);

        listStudentButton.setOnAction(actionEvent -> {
            ListStudent listStudent = new ListStudent();
            listStudent.start(primaryStage);
        });

        Button listBookButton = new Button("List Book");
        listBookButton.setFont(new Font("System Bold", 18));
        listBookButton.setLayoutX(103);
        listBookButton.setLayoutY(294);
        listBookButton.setPrefSize(212, 76);

        listBookButton.setOnAction(actionEvent -> {
            ListBook listBook = new ListBook();
            try {
                listBook.start(primaryStage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Button addStudentButton = new Button("Add Student");
        addStudentButton.setFont(new Font("System Bold", 18));
        addStudentButton.setLayoutX(393);
        addStudentButton.setLayoutY(159);
        addStudentButton.setPrefSize(212, 76);

        addStudentButton.setOnAction(actionEvent -> {
            StudentAdd studentAdd = new StudentAdd();
            studentAdd.start(primaryStage);
        });

        Button addBookButton = new Button("Add Book");
        addBookButton.setFont(new Font("System Bold", 18));
        addBookButton.setLayoutX(393);
        addBookButton.setLayoutY(294);
        addBookButton.setPrefSize(212, 76);

        addBookButton.setOnAction(actionEvent -> {
            BookAdd bookAdd = new BookAdd();
            bookAdd.start(primaryStage);
        });

        // Add all components to the AnchorPane
        root.getChildren().addAll(titleLabel, logoutButton, listStudentButton, listBookButton, addStudentButton, addBookButton);

        // Create the Scene and set it on the Stage
        Scene scene = new Scene(root);
        primaryStage.setTitle("Admin Menu");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Apply theme
        DarkLightMode.applyTheme(root);
    }

    public static void main(String[] args) {
        launch(args);
    }
}