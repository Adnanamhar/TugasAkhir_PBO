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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import org.example.demo.DarkLightMode;
import org.example.demo.Database.Book;
import org.example.demo.Database.User;
import org.example.demo.LoginStudent;
import org.example.demo.SendEmail;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class BorrowBook extends Application {

    public static final String Email1 = "adnanamhar123@gmail.com";
    public static final String Email2 = "fahmimajid01@gmail.com";
    private boolean isBookIdSet = false;  // Flag to track if the Book ID has been set

    @Override
    public void start(Stage primaryStage) {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(700, 500);

        Label titleLabel = new Label("Borrow Book");
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
        TableColumn<Book, String> column5 = new TableColumn<>("Stock");
        column5.setPrefWidth(80);
        column5.setCellValueFactory(new PropertyValueFactory<>("stock"));

        tableView.getColumns().addAll(column1, column2, column3, column4, column5);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ObservableList<Book> bookData = FXCollections.observableArrayList();
        for (Book book : User.books) {
            if (book.getStock() > 0) {
                bookData.add(book);
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
            if (inputID.isEmpty()) {
                errorLabel.setText("ID empty");
                showPopupNotification(primaryStage, "ID empty");
                return;
            }


            boolean find = false;
            for (Book book : User.books) {
                if (book.getId_buku().equals(inputID)) {
                    find = true;
                    if (book.getStock() > 0) {
                        book.setStock(book.getStock() - 1);
                        int indexBorrowBooks = -1;
                        for (int i = 0; i < User.borrowBooks.size(); i++) {
                            if (User.borrowBooks.get(i).get(0).equals(User.loginStudent)) {
                                indexBorrowBooks = i;
                                break;
                            }
                        }
                        book.setDuration(7);
                        if (indexBorrowBooks < 0) {
                            ArrayList<String> temp = new ArrayList<>();
                            temp.add(User.loginStudent);
                            temp.add(book.getId_buku());
                            User.borrowBooks.add(temp);
                        } else {
                            User.borrowBooks.get(indexBorrowBooks).add(book.getId_buku());
                        }
                        start(primaryStage);
                        LocalDateTime now = LocalDateTime.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(" yyyy-MM-dd ");
                        String formattedDateTime = now.format(formatter);
                        LocalDateTime returnDate = now.plus(7, ChronoUnit.DAYS);
                        String formattedReturnDate = returnDate.format(formatter);

                        SendEmail sendEmail = new SendEmail();

                        try {
                            String subject = "Peminjaman Buku Berhasil!";
                            String body = "Terimakasih telah berkunjung ke perpustakaan pusat UMM.\n"
                                    + "Berikut lampiran tentang buku yang telah dipinjam :\n\n"
                                    + "Book ID    : " + book.getId_buku() + "\n"
                                    + "Title      : " + book.getTitle() + "\n"
                                    + "Category   : " + book.getCategory() + "\n"
                                    + "Duration of borrowing : " + " 7 days\n\n"
                                    + "Batas pengembalian   : " + LocalDate.now().plusDays(7) + "\n\n"
                                    + sendEmail.dateinfo();
                          if(LoginStudent.nimTextField.getText().equals("202310370311001")) {
                              sendEmail.sendEmail(Email1, subject, body);
                              showPopupNotification(primaryStage, "NIM: " + User.loginStudent + "\n Book Borrowed Successfully On: " + formattedDateTime + "\n Book Must Be Returned On: " + formattedReturnDate + " (7 Days)" + Email1);

                          }else{
                              sendEmail.sendEmail(Email2, subject, body);
                              showPopupNotification(primaryStage, "NIM: " + User.loginStudent + "\n Book Borrowed Successfully On: " + formattedDateTime + "\n Book Must Be Returned On: " + formattedReturnDate + " (7 Days)" + Email2);
                          }

                        } catch (Exception e) {
                            System.out.println("Error sending email");
                        }
                        return;
                    }
                }

            }


            if (!find) {
                errorLabel.setText("Book ID not found");
                showPopupNotification(primaryStage, "Book ID not found");
            }
        });

        // Menambahkan key listener ke root untuk menangani tombol Enter
        root.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (!isBookIdSet) {
                    Book selectedBook = tableView.getSelectionModel().getSelectedItem();
                    if (selectedBook != null) {
                        bookIdField.setText(selectedBook.getId_buku());
                        isBookIdSet = true;  // Set flag to true after setting Book ID
                    }
                } else {
                    okButton.fire();
                    isBookIdSet = false;  // Reset flag after borrowing book
                }
            }
        });

        Scene scene = new Scene(root);
        primaryStage.setTitle("Borrow Book");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Apply theme
        DarkLightMode.applyTheme(root);
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
