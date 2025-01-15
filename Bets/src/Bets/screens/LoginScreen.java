package Bets.screens;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Bets.screens.RegisterScreen;
import Bets.screens.HomeScreen;
import Bets.User;
import Bets.Bet;
import java.awt.*;
import java.sql.*;
import java.util.List;

public class LoginScreen {

    private VBox layout;
    private User currentUser;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/betting_app";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";
    private Bet bet;
    public LoginScreen(Stage primaryStage) {
        Label titleLabel = new Label("Login section");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");
        Label messageLabel = new Label();

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            System.out.println("Login button clicked!");
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);

            // Validирай логина и получи потребителя
            currentUser = validateLogin(username, password);
            if (currentUser != null) {
                System.out.println("Login successful!");
                HomeScreen homeScreen = new HomeScreen(primaryStage, currentUser);
                primaryStage.setScene(new Scene(homeScreen.getLayout(), 400, 300));
            } else {
                System.out.println("Invalid credentials. Try again.");
                messageLabel.setText("Invalid credentials. Try again.");
            }
        });

        registerButton.setOnAction(e -> {
            RegisterScreen registerScreen = new RegisterScreen(primaryStage);
            primaryStage.setScene(new Scene(registerScreen.getLayout(), 320, 250));
        });

        layout = new VBox(10, titleLabel, usernameField, passwordField, loginButton, registerButton, messageLabel);
    }
    private User validateLogin(String username, String password) {
        String query = "SELECT * FROM Users WHERE username = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String dbUsername = rs.getString("username");
                String dbPassword = rs.getString("password");
                double balance = rs.getDouble("balance");

                // Създаване на обект User с 4 параметъра
                return new User(id, dbUsername, dbPassword, balance);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null; // Връщаме null ако няма потребител с тези данни
    }
    public VBox getLayout() {
        return layout;
    }
}
