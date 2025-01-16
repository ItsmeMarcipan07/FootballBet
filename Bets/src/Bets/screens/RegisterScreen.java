package Bets.screens;

import Bets.models.Bet;
import Bets.models.User;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;


public class RegisterScreen {

    private VBox layout;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/betting_app";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    public RegisterScreen(Stage primaryStage) {
        Label titleLabel = new Label("Registration section");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        Button registerButton = new Button("Register");
        Button backButton = new Button("Back");
        Label messageLabel = new Label();

        registerButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String email = emailField.getText();

            if (validateRegister(username, password, email)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Registration Successful");
                alert.setHeaderText(null);
                alert.setContentText("Successfully registered! Please log in to continue.");
                alert.showAndWait();

                LoginScreen loginScreen = new LoginScreen(primaryStage);
                primaryStage.setScene(new Scene(loginScreen.getLayout(), 300, 200));
            } else {
                messageLabel.setText("Registration failed. Please try again.");
            }
        });

        backButton.setOnAction(e -> {
            LoginScreen loginScreen = new LoginScreen(primaryStage);
            primaryStage.setScene(new Scene(loginScreen.getLayout(), 300, 200));
        });

        layout = new VBox(10, titleLabel, usernameField, passwordField, emailField, registerButton, backButton, messageLabel);
    }

    private boolean validateRegister(String username, String password, String email) {
        String checkQuery = "SELECT COUNT(*) FROM users WHERE username = ? OR email = ?";
        String insertQuery = "INSERT INTO users (username, password, balance, email) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {

            checkStmt.setString(1, username);
            checkStmt.setString(2, email);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    // Вече съществува потребител с този имейл или име
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Registration Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Username or email already exists. Please try another.");
                    alert.showAndWait();
                    return false;
                }
            }

            if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Validation Error");
                alert.setHeaderText(null);
                alert.setContentText("All fields are required. Please fill them in.");
                alert.showAndWait();
                return false;
            }

            try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                insertStmt.setString(1, username);
                insertStmt.setString(2, password);
                insertStmt.setDouble(3, 0.0); // Балансът по подразбиране е 0.0
                insertStmt.setString(4, email);
                insertStmt.executeUpdate();
            }

            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while connecting to the database. Please try again later.");
            alert.showAndWait();
            return false;
        }
    }


    public VBox getLayout() {
        return layout;
    }
}
