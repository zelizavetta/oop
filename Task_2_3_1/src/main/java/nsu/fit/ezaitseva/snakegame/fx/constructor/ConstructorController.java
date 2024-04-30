package nsu.fit.ezaitseva.snakegame.fx.constructor;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import nsu.fit.ezaitseva.snakegame.console.GameSettings;
import nsu.fit.ezaitseva.snakegame.fx.GlobalGameSettings;
import nsu.fit.ezaitseva.snakegame.fx.gameview.DefaultGamePresenter;
import nsu.fit.ezaitseva.snakegame.fx.gameview.GameView;
import nsu.fit.ezaitseva.snakegame.model.game.field.FieldConstructor;
import nsu.fit.ezaitseva.snakegame.model.game.field.FieldDAO;
import nsu.fit.ezaitseva.snakegame.model.game.field.GameField;
import nsu.fit.ezaitseva.snakegame.model.game.logic.Game;
import nsu.fit.ezaitseva.snakegame.model.units.*;

import java.io.IOException;

public class ConstructorController implements GameView {
    public Canvas canvas;
    public RadioButton wallRadioButton;
    public RadioButton foodRadioButton;
    public RadioButton snakeRadioButton;
    public RadioButton emptyRadioButton;
    private GraphicsContext gc;
    private double cellWidth;
    private double cellHeight;
    public ChoiceBox<GameUnitEnum> unitChoiceBox;
    private Game game;
    private FieldConstructor fieldConstructor;
    private GameUnit currentGameUnit = new Empty(0, 0);
    private FieldDAO fieldDAO;

    private DefaultGamePresenter defaultGamePresenter;

    public void init() {
        defaultGamePresenter = new DefaultGamePresenter(this);
        GameSettings gameSettings = GlobalGameSettings.gameSettings;
        gc = canvas.getGraphicsContext2D();
        fieldDAO = new FieldDAO(gameSettings.getFile());
        GameField field = fieldDAO.getField().getField();
        fieldConstructor = new FieldConstructor(field.width(), field.height());
        field.getAll().forEach((gameUnit) -> fieldConstructor.setUnit(gameUnit));
        game = fieldConstructor.getGameField();
        cellWidth = canvas.getWidth() / (game.width());
        cellHeight = canvas.getHeight() / (game.height());
        update();

        ToggleGroup group = new ToggleGroup();
        emptyRadioButton.setToggleGroup(group);
        snakeRadioButton.setToggleGroup(group);
        wallRadioButton.setToggleGroup(group);
        foodRadioButton.setToggleGroup(group);


        wallRadioButton.setOnAction((event -> {
            currentGameUnit = new Wall(0, 0);
        }));
        foodRadioButton.setOnAction((event -> {
            currentGameUnit = new Food(0, 0, 1);
        }));
        snakeRadioButton.setOnAction((event -> {
            currentGameUnit = new SnakeBody(0, 0);
        }));
        emptyRadioButton.setOnAction((event -> {
            currentGameUnit = new Empty(0, 0);
        }));
    }

    public void update() {
        defaultGamePresenter.setGame(game);
        defaultGamePresenter.update();
    }

    public void mouseClick(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();
        System.out.println(canvas.getLayoutX() + " " + canvas.getLayoutY());
        if (x > canvas.getWidth() || y > canvas.getHeight()) {
            return;
        }
        int xCell = (int) (x / cellWidth);
        int yCell = (int) (y / cellHeight);
        fieldConstructor.setUnit(currentGameUnit.getCopy(xCell, yCell));
        game = fieldConstructor.getGameField();
        update();
    }

    public void switchToMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/menu-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void saveField(ActionEvent event) {
        fieldDAO.saveField(game);
        GlobalGameSettings.gameSettings.setGame(game);
    }

    private enum GameUnitEnum {
        Snake,
        Food,
        Empty,
        Wall
    }


    @Override
    public double getViewWidth() {
        return canvas.getWidth();
    }

    @Override
    public double getViewHeight() {
        return canvas.getHeight();
    }

    @Override
    public void setSize(double cellWidth, double cellHeight) {
        this.cellHeight = cellHeight;
        this.cellWidth = cellWidth;
    }

    @Override
    public void drawImage(Image img, double x, double y, double width, double height) {
        gc.drawImage(img, x, y, width, height);
    }

    @Override
    public void drawImage(Image img, double x, double y) {
        drawImage(img, x * cellWidth, y * cellHeight,
                cellWidth, cellHeight);
    }

    @Override
    public void clear() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }


}