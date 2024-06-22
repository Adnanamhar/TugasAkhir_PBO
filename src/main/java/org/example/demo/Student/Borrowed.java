package org.example.demo.Student;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import org.example.demo.Database.Book;
import org.example.demo.Database.User;

public class Borrowed extends Application {

    @Override
    public void start(Stage primaryStage) {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(700, 500);

        Label titleLabel = new Label("Return Book");
        titleLabel.setLayoutX(14.0);
        titleLabel.setLayoutY(16.0);
        titleLabel.setFont(new Font("System Bold", 36.0));

        Button backButton = new Button("Back");
        backButton.setLayoutX(14.0);
        backButton.setLayoutY(446.0);
        backButton.setPrefSize(119.0, 40.0);
        backButton.setFont(new Font("System Bold", 14.0));

        backButton.setOnAction(actionEvent -> {
            MenuStudent menuStudent = new MenuStudent();
            menuStudent.start(primaryStage);
        });

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setLayoutX(33.0);
        scrollPane.setLayoutY(102.0);
        scrollPane.setPrefSize(639.0, 329.0);

        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        TableView<Book> tableView = new TableView<>();
        tableView.setPrefSize(639.0, 329.0);

        TableColumn<Book, String> column1 = new TableColumn<>("ID Book");
        column1.setPrefWidth(140);
        column1.setCellValueFactory(new PropertyValueFactory<>("id_buku"));
        TableColumn<Book, String> column2 = new TableColumn<>("Title");
        column2.setPrefWidth(80);
        column2.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<Book, String> column3 = new TableColumn<>("Author");
        column3.setPrefWidth(80);
        column3.setCellValueFactory(new PropertyValueFactory<>("author"));
        TableColumn<Book, String> column4 = new TableColumn<>("Category");
        column4.setPrefWidth(80);
        column4.setCellValueFactory(new PropertyValueFactory<>("category"));
        TableColumn<Book, String> column5 = new TableColumn<>("Duration");
        column5.setPrefWidth(80);
        column5.setCellValueFactory(new PropertyValueFactory<>("duration"));

        tableView.getColumns().addAll(column1, column2, column3, column4, column5);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ObservableList<Book> bookData = FXCollections.observableArrayList();
        for (int i = 0; i < User.borrowBooks.size(); i++) {
            if(User.borrowBooks.get(i).get(0).equals(User.loginStudent)) {
                for (int j = 1; j < User.borrowBooks.get(i).size(); j++) {
                    for (int k = 0; k < User.books.size(); k++) {
                        if(User.borrowBooks.get(i).get(j).equals(User.books.get(k).getId_buku())) {
                            bookData.add(User.books.get(k));
                        }
                    }
                }
            }
        }
        tableView.setItems(bookData);

        scrollPane.setContent(tableView);

        Label bookIdLabel = new Label("Book ID:");
        bookIdLabel.setLayoutX(319.0);
        bookIdLabel.setLayoutY(43.0);
        bookIdLabel.setFont(new Font(18.0));

        TextField bookIdField = new TextField();
        bookIdField.setLayoutX(395.0);
        bookIdField.setLayoutY(39.0);
        bookIdField.setPrefSize(193.0, 35.0);

        Label errorLabel = new Label();
        errorLabel.setLayoutX(395.0);
        errorLabel.setLayoutY(77.0);
        errorLabel.setPrefSize(292.0, 17.0);
        errorLabel.setTextFill(Color.RED);

        Button okButton = new Button("OK");
        okButton.setLayoutX(603.0);
        okButton.setLayoutY(37.0);
        okButton.setPrefSize(40.0, 40.0);

        root.getChildren().addAll(titleLabel, backButton, scrollPane, bookIdLabel, bookIdField, okButton, errorLabel);

        okButton.setOnAction(actionEvent -> {
            errorLabel.setText("");
            String inputID = bookIdField.getText();
            if(inputID.isEmpty()) {
                errorLabel.setText("ID empty");
                showPopupNotification(primaryStage, "ID empty");
                return;
            }

            boolean find = false;
            for(int i = 0; i < User.borrowBooks.size(); i++) {
                if(User.borrowBooks.get(i).get(0).equals(User.loginStudent)) {
                    for (int j = 1; j < User.borrowBooks.get(i).size(); j++) {
                        if(User.borrowBooks.get(i).get(j).equals(inputID)) {
                            find = true;
                            User.borrowBooks.get(i).remove(j);
                            for (int k = 0; k < User.books.size(); k++) {
                                if(User.books.get(k).getId_buku().equals(inputID)) {
                                    User.books.get(k).setStock(User.books.get(k).getStock() + 1);
                                    start(primaryStage);
                                    showPopupNotification(primaryStage, "Book returned successfully!");
                                    return;
                                }
                            }
                            break;
                        }
                    }
                }
            }
            if(!find) {
                errorLabel.setText("Book ID not found");
                showPopupNotification(primaryStage, "Book ID not found");
            }
        });

        Scene scene = new Scene(root);
        primaryStage.setTitle("Return Book");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showPopupNotification(Stage ownerStage, String message) {
        Popup popup = new Popup();
        popup.setAutoHide(true);

        VBox popupContent = new VBox();
        popupContent.setPadding(new Insets(10));
        popupContent.setStyle("-fx-background-color: #000000; -fx-background-radius: 10;");

        Text messageText = new Text(message);
        messageText.setFill(Color.WHITE);
        popupContent.getChildren().add(messageText);

        popup.getContent().add(popupContent);

        // Calculate position to show popup at the bottom center of the screen
        double xPos = ownerStage.getX() + (ownerStage.getWidth() / 2) - (popupContent.getWidth() / 2);
        double yPos = ownerStage.getY() + ownerStage.getHeight() - popupContent.getHeight() - 10;

        popup.show(ownerStage, xPos, yPos);

        // Hide popup after 3 seconds
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> popup.hide()));
        timeline.setCycleCount(1);
        timeline.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}