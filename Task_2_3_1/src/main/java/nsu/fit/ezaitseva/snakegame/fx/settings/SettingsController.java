package nsu.fit.ezaitseva.snakegame.fx.settings;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import nsu.fit.ezaitseva.snakegame.console.GameSettings;
import nsu.fit.ezaitseva.snakegame.console.settings.UserMode;
import nsu.fit.ezaitseva.snakegame.fx.GlobalGameSettings;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    private final GameSettings gameSettings = GlobalGameSettings.gameSettings;


    @FXML
    private CheckBox observerBox;
    @FXML
    private Slider levelSlider;
    @FXML
    private Slider difficultSlider;

    public void switchToMenu(ActionEvent event) throws IOException {
        gameSettings.saveIntoFile();
        Parent root = FXMLLoader.load(getClass().getResource("/menu-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        observerBox.selectedProperty().setValue(gameSettings.getUserMode().equals(UserMode.Observer));
        observerBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(true)) {
                gameSettings.setUserMode(UserMode.Observer);
            } else {
                gameSettings.setUserMode(UserMode.Player);
            }
            System.out.println(gameSettings.getUserMode());
        });
        difficultSlider.setValue(gameSettings.getDifficult());
        difficultSlider.setBlockIncrement(0.1);
        difficultSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            gameSettings.setDifficult(newValue.doubleValue());
        });
        levelSlider.setMin(1);
        levelSlider.setMax(5);
        levelSlider.setValue(gameSettings.getGameSpeed());
        levelSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            gameSettings.setGameSpeed(newValue.intValue());
        });


    }


}