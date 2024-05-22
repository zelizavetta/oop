package nsu.fit.ezaitseva.snakegame.fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import nsu.fit.ezaitseva.snakegame.console.GameSettings;

public class SnakeFXApp extends Application {
    private GameSettings gameSettings = GlobalGameSettings.gameSettings;

    @Override
    public void start(Stage primaryStage) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu-view.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        gameSettings = new GameSettings();
        primaryStage.setScene(scene);
        primaryStage.setTitle("SnakeGame");
        primaryStage.show();

    }

    public void run() {
        launch();
    }

    public GameSettings getGameSettings() {
        return gameSettings;
    }

    public void setGameSettings(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
    }

}