package Bets;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;
import java.util.List;

public class MainApp extends Application {
    private User currentUser;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/betting_app";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";
    private Bet bet;

    VBox mainLayout = new VBox();
    private void showRegisterScreen(Stage primaryStage) {
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        Button registerButton = new Button("Register");
        Label messageLabel = new Label();

        registerButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String email = emailField.getText();

            if (validateRegister(username, password, email)) {
                // Показваме alert за успешна регистрация
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Registration Successful");
                alert.setHeaderText(null);
                alert.setContentText("Successfully registered! Please log in to continue.");
                alert.showAndWait();

                // Пренасочваме към началния екран (login screen)
                start(primaryStage);
            } else {
                // Показваме съобщение за неуспех
                messageLabel.setText("Registration failed. Please try again.");
            }
        });

        VBox registerLayout = new VBox(10, usernameField, passwordField, emailField, registerButton, messageLabel);
        primaryStage.setScene(new Scene(registerLayout, 300, 200));
        primaryStage.show();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Betting App");

        // Login Screen
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Passwords");
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
                showMainScreen(primaryStage);
            } else {
                System.out.println("Invalid credentials. Try again.");
                messageLabel.setText("Invalid credentials. Try again.");
            }
        });
        registerButton.setOnAction(e -> showRegisterScreen(primaryStage));

        VBox loginLayout = new VBox(10, usernameField, passwordField, loginButton,registerButton,messageLabel);
        primaryStage.setScene(new Scene(loginLayout, 300, 200));
        primaryStage.show();
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
    private boolean validateRegister(String username, String password, String email) {
        String query = "INSERT INTO users (username, password, balance,email) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                return false;
            }
            else {
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setString(4, email);
                stmt.setDouble(3, 0.0);
                stmt.executeUpdate();

                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }


    private void showMainScreen(Stage primaryStage) {
        if (currentUser != null) {
            Label welcomeLabel = new Label("Welcome, " + currentUser.getUsername());
            Label balanceLabel = new Label("Balance: " + currentUser.getBalance());
            Button viewMatchesButton = new Button("View Matches");
            Button betHistoryButton = new Button("Bet History");  // Добавяме бутон за история на залаганията
            Button logOutButton = new Button("Log out");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert Box");
            alert.setHeaderText(null);
            alert.setContentText("Successfully logged out!");
            // При натискане на бутона "View Matches" се извиква методът showMatches
            viewMatchesButton.setOnAction(e -> showMatches(primaryStage));
            logOutButton.setOnAction(e -> {start(primaryStage); alert.showAndWait();});
            // При натискане на бутона "Bet History" се извиква методът showBetHistory
            betHistoryButton.setOnAction(e -> showBetHistory(primaryStage));

            // Добавяме бутоните към основния layout
            VBox mainLayout = new VBox(10, welcomeLabel, balanceLabel,viewMatchesButton, betHistoryButton, logOutButton);

            // Настройваме сцената
            primaryStage.setScene(new Scene(mainLayout, 400, 300));
            primaryStage.show(); // Не забравяй да покажеш сцената
        } else {
            System.out.println("Error: Current user is not set.");
        }
    }

    private void showMatches(Stage primaryStage) {
        List<Match> matches = MatchDAO.getAllMatches();
        VBox matchesLayout = new VBox(10);

        for (Match match : matches) {
            Button matchButton = new Button(match.getTeam1() + " vs " + match.getTeam2());
            matchButton.setOnAction(e -> {placeBet(primaryStage, match);});
            matchesLayout.getChildren().add(matchButton);
        }

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> showMainScreen(primaryStage));
        matchesLayout.getChildren().add(backButton);

        primaryStage.setScene(new Scene(matchesLayout, 400, 300));
    }

    private void showBetHistory(Stage primaryStage) {
        List<Bet> bets = BetDAO.getBetsByUser(currentUser.getId());
        VBox historyLayout = new VBox(10);

        for (Bet bet : bets) {
            // Показваме информация за залагането, включително коефициента и дали е спечелено
            Label betLabel = new Label("Bet on Match: " + bet.getMatchId() +
                    ", Amount: " + bet.getBetAmount() +
                    ", Type: " + bet.getBetType() +
                    ", Odds: " + bet.getOdds() +   // Добавяме коефициент
                    ", Won: " + (bet.isWon() ? "Yes" : "No"));
            historyLayout.getChildren().add(betLabel);
        }

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> showMainScreen(primaryStage));
        historyLayout.getChildren().add(backButton);

        primaryStage.setScene(new Scene(historyLayout, 400, 300));
    }

    private void placeBet(Stage primaryStage, Match match) {
        TextField amountField = new TextField();
        amountField.setPromptText("Bet Amount");
        ComboBox<String> betTypeBox = new ComboBox<>();
        betTypeBox.getItems().addAll("Team1", "Draw", "Team2");

        // Добавяне на информация за коефициентите
        double oddsTeam1 = match.getCoefTeam1();
        double oddsDraw = match.getCoefDraw();
        double oddsTeam2 = match.getCoefTeam2();

        Label oddsLabel = new Label("Odds: " + "Team1: " + oddsTeam1 + ", Draw: " + oddsDraw + ", Team2: " + oddsTeam2);

        Button confirmButton = new Button("Place Bet");
        confirmButton.setOnAction(e -> {
            double betAmount = Double.parseDouble(amountField.getText());
            String betType = betTypeBox.getValue();
            double selectedOdds = 0.0;

            // Избиране на коефициент според типа на залога
            if (betType.equals("Team1")) {
                selectedOdds = oddsTeam1;
            } else if (betType.equals("Draw")) {
                selectedOdds = oddsDraw;
            } else if (betType.equals("Team2")) {
                selectedOdds = oddsTeam2;
            }

            if (betAmount <= currentUser.getBalance()) {
                BetDAO.placeBet(currentUser.getId(), match.getId(), betAmount, betType,selectedOdds);
                currentUser.setBalance(currentUser.getBalance() - betAmount);
                UserDAO.updateUserBalance(currentUser);
                BetDAO.updateWon(MatchDAO.simulateMatch(match), match.getId(), betType);
                showMainScreen(primaryStage);
            } else {
                new Alert(Alert.AlertType.ERROR, "Insufficient balance!").showAndWait();
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> showMatches(primaryStage));
        VBox betLayout = new VBox(10, new Label("Place Bet on:  " + match.getTeam1() + " vs " + match.getTeam2()),
                oddsLabel, amountField, betTypeBox, confirmButton, backButton);

        primaryStage.setScene(new Scene(betLayout, 400, 300));
    }
    public static void main(String[] args) {
        launch(args);
    }
}
