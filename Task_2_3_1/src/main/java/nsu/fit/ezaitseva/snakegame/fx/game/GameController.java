package nsu.fit.ezaitseva.snakegame.fx.game;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import nsu.fit.ezaitseva.snakegame.fx.gameview.GameView;
import nsu.fit.ezaitseva.snakegame.fx.gameview.ImageCollector;

/**
 * game controller class.
 */
public class GameController implements GameView {
    @FXML
    public Text score;
    @FXML
    public Canvas canvas;
    public GraphicsContext gc;
    public Text scorePlayer1;
    public Text namePlayer2;
    public Text namePlayer1;
    public ImageView imagePlayer1;
    public ImageView imagePlayer2;
    public Text scorePlayer2;
    public ImageView imagePlayer3;
    public Text namePlayer3;
    public Text scorePlayer3;
    public ImageView personImage;
    public Text personName;
    public ImageView winnerImage;
    public Text winnerText;

    private GamePresenter gamePresenter;
    private LeaderBoard leaderBoard;
    private double cellWidth;
    private double cellHeight;

    /**
     * getting width.
     *
     * @return width
     */
    public double getCanvasWidth() {
        return canvas.getWidth();
    }

    /**
     * getting height.
     *
     * @return height
     */
    public double getCanvasHeight() {
        return canvas.getHeight();
    }


    /**
     * init meth.
     */
    public void init() {
        this.gamePresenter = new GamePresenter(this);

        canvas.getScene().setOnKeyPressed((event -> {
            gamePresenter.getKeyResolver().resolveKeyCode(event.getCode());
        }));
        gc = canvas.getGraphicsContext2D();

        hideWinner();

        initLeaderBoard();
        gamePresenter.update();
        gamePresenter.init();
        gamePresenter.startGame();
    }

    /**
     * hiding winner meth.
     */
    public void hideWinner() {
        winnerImage.setOpacity(0);
        winnerImage.setImage(ImageCollector.winner);
        winnerText.setOpacity(0);
    }

    /**
     * get view width.
     *
     * @return width
     */
    @Override
    public double getViewWidth() {
        return getCanvasWidth();
    }

    /**
     * getting view height.
     *
     * @return height
     */
    @Override
    public double getViewHeight() {
        return getCanvasHeight();
    }

    /**
     * setting size meth.
     *
     * @param cellWidth  width
     * @param cellHeight height
     */
    public void setSize(double cellWidth, double cellHeight) {
        this.cellHeight = cellHeight;
        this.cellWidth = cellWidth;
    }


    /**
     * init for leader board.
     */
    private void initLeaderBoard() {
        leaderBoard = new LeaderBoard(List.of(imagePlayer1, imagePlayer2, imagePlayer3),
                List.of(scorePlayer1, scorePlayer2, scorePlayer3),
                List.of(namePlayer1, namePlayer2, namePlayer3));
    }

    /**
     * update for person
     *
     * @param personScore          person score
     * @param personSnakeHeadImage person head snake image
     */
    public void updatePersonStatistic(String personScore, Image personSnakeHeadImage) {
        score.setText(personScore);
        personImage.setImage(personSnakeHeadImage);
    }

    /**
     * draw image meth.
     *
     * @param img    image
     * @param x      x
     * @param y      y
     * @param width  width
     * @param height height
     */
    public void drawImage(Image img, double x, double y, double width, double height) {
        gc.drawImage(img, x, y, width, height);
    }

    /**
     * draw image meth.
     *
     * @param img image
     * @param x   x
     * @param y   y
     */
    public void drawImage(Image img, double x, double y) {
        drawImage(img, x * cellWidth, y * cellHeight,
                cellWidth, cellHeight);
    }


    /**
     * show winner meth.
     */
    public void showWinner() {
        winnerImage.setOpacity(0.8);
        winnerText.setOpacity(1);
    }

    /**
     * clear for game.
     */
    public void clear() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    /**
     * updating leader board.
     *
     * @param index       index
     * @param playerImage image for player
     * @param newScore    updated score
     * @param playerName  player name
     */
    public void updateLeaderBoard(int index, Image playerImage, int newScore, String playerName) {
        leaderBoard.update(index, playerImage, newScore, playerName);
    }

    /**
     * init for person statistic.
     *
     * @param personName     person name
     * @param snakeHeadImage image for snake head.
     */
    public void initPersonStatistic(String personName,
                                    Image snakeHeadImage) {
        personImage.setImage(snakeHeadImage);
        this.personName.setText(personName);
    }


    private record LeaderBoard(List<ImageView> leaderViews, List<Text> boardScores, List<Text> boardNames) {
        public void update(int index, Image playerImage, int newScore, String playerName) {
            leaderViews.get(index).setImage(playerImage);
            boardScores.get(index).setText(newScore + "");
            boardNames.get(index).setText(playerName);
        }
    }


    /**
     * exit to menu.
     */
    void exitToMenu() {
        gamePresenter.stop();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/menu-view.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Stage stage = (Stage) (canvas.getScene().getWindow());
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


}