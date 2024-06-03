package nsu.fit.ezaitseva.snakegame.fx;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nsu.fit.ezaitseva.snakegame.console.GameSettings;

/**
 * The type Snake fx app.
 */
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

    /**
     * Run.
     */
    public void run() {
        launch();
    }

    /**
     * Gets game settings.
     *
     * @return the game settings
     */
    public GameSettings getGameSettings() {
        return gameSettings;
    }

    /**
     * Sets game settings.
     *
     * @param gameSettings the game settings
     */
    public void setGameSettings(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
    }

}