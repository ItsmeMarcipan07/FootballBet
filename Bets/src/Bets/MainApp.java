package Bets;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Bets.screens.LoginScreen;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Betting App");
        LoginScreen loginScreen = new LoginScreen(primaryStage);
        primaryStage.setScene(new Scene(loginScreen.getLayout(), 300, 200));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}