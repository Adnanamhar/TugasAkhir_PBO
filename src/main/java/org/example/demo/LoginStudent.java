package org.example.demo;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.example.demo.Database.User;
import org.example.demo.Student.MenuStudent;

import static org.example.demo.LoginAdmin.bgAll;

public class LoginStudent extends Application {
    public static TextField nimTextField = new TextField();

    @Override
    public void start(Stage primaryStage) {

        // Create the AnchorPane
        AnchorPane root = new AnchorPane();

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
        Label titleLabel = new Label("Login Student");
        titleLabel.setFont(new Font("System Bold", 36));
        titleLabel.setLayoutX(231);
        titleLabel.setLayoutY(55);

        Label nimLabel = new Label("NIM");
        nimLabel.setFont(new Font(18));
        nimLabel.setLayoutX(136);
        nimLabel.setLayoutY(126);

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

        nimTextField.setLayoutX(163);
        nimTextField.setLayoutY(154);
        nimTextField.setPrefSize(381, 40);

        PasswordField passwordTextField = new PasswordField();
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
            String nim = nimTextField.getText();
            String password = passwordTextField.getText();
            if(nim.isEmpty()) {
                errorLabel.setText("NIM empty.");
                return;
            }
            if(password.isEmpty()) {
                errorLabel.setText("Password empty.");
                return;
            }

            boolean find = false;
            for (int i = 0; i < User.students.size(); i++) {
                if(nim.equals(User.students.get(i).getNim())) {
                    find = true;
                    if(nim.equals(password)) {
                        User.loginStudent = nim;
                        MenuStudent menuStudent = new MenuStudent();
                        menuStudent.start(primaryStage);
                    } else {
                        errorLabel.setText("Incorrect password.");
                    }
                }
            }
            if(!find) {
                errorLabel.setText("NIM not found.");
            }
        });

        // Add event handler for Enter key press
        passwordTextField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                loginButton.fire();
            }
        });

        Button adminLoginButton = new Button("Login Admin");
        adminLoginButton.setFont(new Font("System Bold", 14));
        adminLoginButton.setLayoutX(14);
        adminLoginButton.setLayoutY(446);
        adminLoginButton.setPrefSize(119, 40);

        adminLoginButton.setOnAction(actionEvent -> {
            LoginAdmin loginAdmin = new LoginAdmin();
            loginAdmin.start(primaryStage);
        });

        // Add all components to the AnchorPane
        root.getChildren().addAll(titleLabel, nimLabel, passwordLabel, nimTextField, passwordTextField, loginButton, adminLoginButton, errorLabel);


        Scene scene = new Scene(root);
        primaryStage.setTitle("Login Form");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
        primaryStage.show();

        // Apply theme
        DarkLightMode.applyTheme(root);
    }

    public static void main(String[] args) {
        launch(args);
    }
}