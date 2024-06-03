package nsu.fit.ezaitseva.snakegame.fx.menu;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nsu.fit.ezaitseva.snakegame.fx.game.GameController;

/**
 * The type Menu controller.
 */
public class MenuController {


    /**
     * Switch to play.
     *
     * @param event the event
     * @throws IOException the io exception
     */
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


    /**
     * Exit.
     *
     * @param event the event
     */
    public void exit(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}