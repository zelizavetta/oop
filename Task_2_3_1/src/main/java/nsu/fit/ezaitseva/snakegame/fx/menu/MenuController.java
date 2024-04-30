package nsu.fit.ezaitseva.snakegame.fx.menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nsu.fit.ezaitseva.snakegame.console.GameSettings;
import nsu.fit.ezaitseva.snakegame.fx.GlobalGameSettings;
import nsu.fit.ezaitseva.snakegame.fx.constructor.ConstructorController;
import nsu.fit.ezaitseva.snakegame.fx.game.GameController;

import java.io.IOException;

public class MenuController {
    private final GameSettings gameSettings = GlobalGameSettings.gameSettings;


    public void switchToPlay(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game-view.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        GameController gameController = loader.getController();
        gameController.init();
        stage.setScene(scene);
        stage.show();

    }

    public void switchToSettings(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/settings.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
    }

    public void switchToConstructor(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/constructor-view.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        ConstructorController constructorController = loader.getController();
        constructorController.init();
        stage.show();
    }


    public void exit(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}