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
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;

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
        backgroundView.setPreserveRatio(false); // Tidak mempertahankan rasio aspek

        root.getChildren().add(backgroundView);

        // Listener untuk menyesuaikan ukuran background ketika ukuran scene berubah
        root.widthProperty().addListener((obs, oldVal, newVal) -> {
            backgroundView.setFitWidth(newVal.doubleValue());
        });
        root.heightProperty().addListener((obs, oldVal, newVal) -> {
            backgroundView.setFitHeight(newVal.doubleValue());
        });

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

        // Book display
        VBox bookDisplay = new VBox(10);
        bookDisplay.setAlignment(Pos.CENTER_RIGHT);
        bookDisplay.setPadding(new Insets(10));

        // Load books (example)
        List<Book> books = loadBooks();
        if (!books.isEmpty()) {
            Book book = books.get(0);
            VBox bookBox = new VBox(5);
            bookBox.setAlignment(Pos.CENTER);

            bookCover = new ImageView("file:src/main/java/org/example/demo/Image/buku1.png");
            bookCover.setFitHeight(200);
            bookCover.setFitWidth(150);

            // Effect when mouse entered
            bookCover.setOnMouseEntered(e -> {
                bookCover.setFitHeight(220);
                bookCover.setFitWidth(170);
            });

            bookCover.setOnMouseExited(e -> {
                bookCover.setFitHeight(200);
                bookCover.setFitWidth(150);
            });

            Label bookTitle = new Label(book.getTitle());
            bookTitle.setFont(new Font(18));
            bookTitle.setWrapText(true);

            bookBox.getChildren().addAll(bookCover, bookTitle);
            bookDisplay.getChildren().add(bookBox);
        }

        // Background images dan book covers
        List<String> backgroundImages = List.of(
                "file:src/main/java/org/example/demo/Image/bg1.png",
                "file:src/main/java/org/example/demo/Image/bg2.png"
        );

        List<String> bookCovers = List.of(
                "file:src/main/java/org/example/demo/Image/buku1.png",
                "file:src/main/java/org/example/demo/Image/buku2.png"
        );

        // Circle navigation control
        HBox navigationControl = new HBox(10);
        navigationControl.setAlignment(Pos.CENTER);
        navigationControl.setPadding(new Insets(10));

        List<Circle> navCircles = new ArrayList<>();
        for (int i = 0; i < backgroundImages.size(); i++) {
            Circle circle = new Circle(10, i == 0 ? Paint.valueOf("black") : Paint.valueOf("white"));
            final int index = i;
            circle.setOnMouseClicked((MouseEvent e) -> {
                // Ubah background image
                backgroundView.setImage(new Image(backgroundImages.get(index)));

                // Ubah book cover image
                bookCover.setImage(new Image(bookCovers.get(index)));

                // Perbarui warna circle
                for (Circle c : navCircles) {
                    c.setFill(Paint.valueOf("white"));
                }
                circle.setFill(Paint.valueOf("black"));
            });
            navCircles.add(circle);
            navigationControl.getChildren().add(circle);
        }

        bookDisplay.getChildren().add(navigationControl);

        centerSection.getChildren().addAll(sloganSection, bookDisplay);

        root.setTop(topSection);
        root.setCenter(centerSection);

        // Mengambil ukuran layar utama
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX(screenBounds.getMinX());
        primaryStage.setY(screenBounds.getMinY());
        primaryStage.setWidth(screenBounds.getWidth());
        primaryStage.setHeight(screenBounds.getHeight());

        Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
        primaryStage.setTitle("Perpustakaanku");
        primaryStage.setScene(scene);
        primaryStage.show();

        // DarkLight Mode (if needed)
        DarkLightMode.applyTheme(root);

        // Make the layout responsive
        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            double width = newVal.doubleValue();
            if (width < 600) {
                topSection.setSpacing(10);
                centerSection.setSpacing(10);
                sloganSection.setPadding(new Insets(10));
            } else {
                topSection.setSpacing(20);
                centerSection.setSpacing(50);
                sloganSection.setPadding(new Insets(50));
            }
        });
    }

    private List<Book> loadBooks() {
        // Mock data, replace with actual book data from database
        List<Book> books = new ArrayList<>();
        books.add(new Book("1", "Selalu Ada Cinta Di Setiap Cerita", "Author 1", "Category 1", 10));
        books.add(new Book("2", "Rentang Waktu", "Author 2", "Category 2", 5));

        return books;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
