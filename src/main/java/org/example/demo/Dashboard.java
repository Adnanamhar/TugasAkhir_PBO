package org.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.example.demo.Database.Book;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends Application {

    private ImageView bookCover; // Definisikan di tingkat kelas

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        Image backgroundImage = new Image("file:src/main/java/org/example/demo/Image/bg1.png");
        ImageView backgroundView = new ImageView(backgroundImage);
        root.getChildren().add(backgroundView);

        // Sesuaikan dengan lebar yang diinginkan
        backgroundView.setFitWidth(800);
        backgroundView.setFitHeight(600);

        // Top section
        HBox topSection = new HBox();
        topSection.setPadding(new Insets(10));
        topSection.setSpacing(20);
        topSection.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        // Logo
        ImageView logo = new ImageView("file:src/main/java/org/example/demo/Image/github.png");
        logo.setFitHeight(50);
        logo.setFitWidth(50);

        // Application name
        Label appName = new Label("MyLibrary");
        appName.setFont(new Font("System Bold", 38));

        // Sign in button with popup
        Button signInButton = new Button("Sign In");
        signInButton.setFont(new Font(14));
        signInButton.setBackground(Background.EMPTY);

        // Create popup for Sign In
        Popup signInPopup = new Popup();
        VBox popupContent = new VBox(10);
        popupContent.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(5), Insets.EMPTY)));
        popupContent.setPadding(new Insets(10));

        Button studentSignInButton = new Button("Sign In to Student");
        studentSignInButton.setPrefWidth(150);
        studentSignInButton.setOnAction(e -> {
            LoginStudent loginStudent = new LoginStudent();
            loginStudent.start(new Stage());
            signInPopup.hide();
        });

        Button adminSignInButton = new Button("Sign In to Admin");
        adminSignInButton.setPrefWidth(150);
        adminSignInButton.setOnAction(e -> {
            LoginAdmin loginAdmin = new LoginAdmin();
            loginAdmin.start(new Stage());
            signInPopup.hide();
        });

        popupContent.getChildren().addAll(studentSignInButton, adminSignInButton);
        signInPopup.getContent().add(popupContent);

        signInButton.setOnMouseEntered(e -> {
            signInPopup.show(primaryStage, signInButton.localToScreen(signInButton.getBoundsInLocal()).getMinX(), signInButton.localToScreen(signInButton.getBoundsInLocal()).getMaxY());
        });

        popupContent.setOnMouseExited(e -> {
            signInPopup.hide();
        });

        // Language button with popup
        Button languageButton = new Button("Language");
        languageButton.setFont(new Font(14));
        languageButton.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

        Popup languagePopup = new Popup();
        VBox languageContent = new VBox(10);
        languageContent.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(5), Insets.EMPTY)));
        languageContent.setPadding(new Insets(10));

        Button englishButton = new Button("Indonesia");
        englishButton.setPrefWidth(100);
        Button arabicButton = new Button("Arabic");
        arabicButton.setPrefWidth(100);

        languageContent.getChildren().addAll(englishButton, arabicButton);
        languagePopup.getContent().add(languageContent);

        languageButton.setOnMouseEntered(e -> {
            languagePopup.show(primaryStage, languageButton.localToScreen(languageButton.getBoundsInLocal()).getMinX(), languageButton.localToScreen(languageButton.getBoundsInLocal()).getMaxY());
        });

        languageContent.setOnMouseExited(e -> {
            languagePopup.hide();
        });

        // Help button with popup
        Button helpButton = new Button("Help");
        helpButton.setFont(new Font(14));
        helpButton.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

        Popup helpPopup = new Popup();
        VBox helpContent = new VBox(10);
        helpContent.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(5), Insets.EMPTY)));
        helpContent.setPadding(new Insets(10));

        Label helpLabel = new Label("Help!\nadasjdljsalndsa cuiehiuahdsnakndksankddalskjksnaaknxlnxasknannxjkcaskjckjsac");
        helpContent.getChildren().add(helpLabel);
        helpPopup.getContent().add(helpContent);

        helpButton.setOnMouseEntered(e -> {
            helpPopup.show(primaryStage, helpButton.localToScreen(helpButton.getBoundsInLocal()).getMinX(), helpButton.localToScreen(helpButton.getBoundsInLocal()).getMaxY());
        });

        helpContent.setOnMouseExited(e -> {
            helpPopup.hide();
        });

        // Right side buttons
        HBox rightButtons = new HBox(10, signInButton, languageButton, helpButton);
        rightButtons.setPadding(new Insets(10));
        rightButtons.setAlignment(Pos.TOP_RIGHT);
        HBox.setHgrow(rightButtons, Priority.ALWAYS);

        // Adding elements to the top section
        topSection.getChildren().addAll(logo, appName, rightButtons);

        // Center section
        HBox centerSection = new HBox(50);
        centerSection.setPadding(new Insets(20));

        // Slogan section
        VBox sloganSection = new VBox(20);
        sloganSection.setPadding(new Insets(50));
        sloganSection.setAlignment(Pos.CENTER);

        Label sloganLabel = new Label("Welcome to MyLibrary!");
        sloganLabel.setFont(new Font("System Bold", 28));

        Button centerSignInButton = new Button("Sign In");
        centerSignInButton.setFont(new Font(14));
        centerSignInButton.setBackground(Background.EMPTY);
        centerSignInButton.setOnAction(e -> {
            LoginStudent loginStudent = new LoginStudent();
            loginStudent.start(new Stage());
        });

        sloganSection.getChildren().addAll(sloganLabel, centerSignInButton);

    }

    private List<Book> loadBooks() {
        // Mock data, replace with actual book data from database
        List<Book> books = new ArrayList<>();
        books.add(new Book("1", "Selalu Ada Cinta Di Setiap Cerita", "Author 1", "Category 1", 10));
        books.add(new Book("2", "Nak, Kamu Gapapa Kan?", "Author 2", "Category 2", 5));

        return books;
    }

    public static void main(String[] args) {
        launch(args);
    }
}