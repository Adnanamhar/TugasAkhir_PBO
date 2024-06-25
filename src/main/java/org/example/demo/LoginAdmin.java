package org.example.demo;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import org.example.demo.Admin.MenuAdmin;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class LoginAdmin extends Application {

    private int attempts = 0; // Jumlah percobaan
    private LocalDateTime lastAttemptTime; // Waktu terakhir kali percobaan

    @Override
    public void start(Stage primaryStage) {

        // Create the AnchorPane
        AnchorPane root = new AnchorPane();
        root.setPrefSize(700, 500);

        // Create the Labels
        Label titleLabel = new Label("Login Admin");
        titleLabel.setFont(new Font("System Bold", 36));
        titleLabel.setLayoutX(239);
        titleLabel.setLayoutY(54);

        Label usernameLabel = new Label("Username");
        usernameLabel.setFont(new Font(18));
        usernameLabel.setLayoutX(136);
        usernameLabel.setLayoutY(126);

        Label passwordLabel = new Label("Password");
        passwordLabel.setFont(new Font(18));
        passwordLabel.setLayoutX(139);
        passwordLabel.setLayoutY(212);

        Label errorLabel = new Label();
        errorLabel.setFont(new Font(18));
        errorLabel.setLayoutX(157);
        errorLabel.setLayoutY(395);
        errorLabel.setPrefSize(381, 17);
        errorLabel.setTextFill(Color.RED);

        // Create the TextFields
        TextField usernameTextField = new TextField();
        usernameTextField.setLayoutX(163);
        usernameTextField.setLayoutY(154);
        usernameTextField.setPrefSize(381, 40);

        TextField passwordTextField = new TextField();
        passwordTextField.setLayoutX(163);
        passwordTextField.setLayoutY(245);
        passwordTextField.setPrefSize(381, 40);

        // Create the Buttons
        Button loginButton = new Button("Login");
        loginButton.setFont(new Font("System Bold", 18));
        loginButton.setLayoutX(279);
        loginButton.setLayoutY(318);
        loginButton.setPrefSize(149, 61);

        loginButton.setOnAction(actionEvent -> {
            errorLabel.setText("");
            String username = usernameTextField.getText();
            String password = passwordTextField.getText();

            if (username.isEmpty()) {
                errorLabel.setText("Username empty");
                return;
            }
            if (password.isEmpty()) {
                errorLabel.setText("Password empty");
                return;
            }

            // Check if waiting time is required due to multiple failed attempts
            if (lastAttemptTime != null && attempts >= 3) {
                LocalDateTime currentTime = LocalDateTime.now();
                LocalDateTime unlockTime = lastAttemptTime.plusSeconds(5); // Menunggu 5 detik
                if (currentTime.isBefore(unlockTime)) {
                    // Menghitung sisa waktu untuk menunggu
                    long secondsRemaining = ChronoUnit.SECONDS.between(currentTime, unlockTime);
                    errorLabel.setText("Too many attempts. Please wait " + secondsRemaining + " seconds.");
                    return;
                } else {
                    // Reset attempts dan lastAttemptTime karena waktu tunggu telah berakhir
                    attempts = 0;
                    lastAttemptTime = null;
                }
            }

            // Validasi username dan password
            if (username.equals("adnan") && password.equals("admin")) {
                // Login berhasil
                MenuAdmin menuAdmin = new MenuAdmin();
                menuAdmin.start(primaryStage);
            } else {
                // Login gagal
                attempts++;
                lastAttemptTime = LocalDateTime.now();
                if (attempts < 3) {
                    errorLabel.setText("Username or password incorrect. Tersisa " + (3 - attempts));
                } else {
                    errorLabel.setText("Kesempatan habis. Please wait 5 seconds.");
                }
            }
        });

        Button studentLoginButton = new Button("Login Student");
        studentLoginButton.setFont(new Font("System Bold", 14));
        studentLoginButton.setLayoutX(14);
        studentLoginButton.setLayoutY(446);
        studentLoginButton.setPrefSize(119, 40);

        studentLoginButton.setOnAction(actionEvent -> {
            LoginStudent loginStudent = new LoginStudent();
            loginStudent.start(primaryStage);
        });

        // Add all components to the AnchorPane
        root.getChildren().addAll(titleLabel, usernameLabel, passwordLabel, usernameTextField, passwordTextField, loginButton, studentLoginButton, errorLabel);

        // Create the Scene and set it on the Stage
        Scene scene = new Scene(root);
        primaryStage.setTitle("Login Form");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
