package nsu.fit.ezaitseva.snakegame.fx.gameview;

import java.util.Map;
import javafx.scene.image.Image;
import nsu.fit.ezaitseva.snakegame.console.GameSettings;
import nsu.fit.ezaitseva.snakegame.fx.GlobalGameSettings;
import nsu.fit.ezaitseva.snakegame.model.game.field.GameField;
import nsu.fit.ezaitseva.snakegame.model.game.logic.Game;
import nsu.fit.ezaitseva.snakegame.model.units.Food;
import nsu.fit.ezaitseva.snakegame.model.units.GameUnit;
import nsu.fit.ezaitseva.snakegame.model.units.Snake;
import nsu.fit.ezaitseva.snakegame.model.units.SnakeBody;

/**
 * class for default game presenter.
 */
public class DefaultGamePresenter {
    private final GameView gameView;
    private final int gameWidth;
    private final int gameHeight;
    private final double cellWidth;
    private final double cellHeight;
    private final GameSettings gameSettings = GlobalGameSettings.gameSettings;
    protected Game game;
    private final SnakeDrawer snakeDrawer;

    /**
     * class-constructor for default game presenter.
     *
     * @param gameView game view
     */
    public DefaultGamePresenter(GameView gameView) {
        this.gameView = gameView;
        game = gameSettings.getGame().getCopy();
        gameWidth = game.width();
        gameHeight = game.height();
        cellWidth = this.gameView.getViewWidth() / gameWidth;
        cellHeight = this.gameView.getViewHeight() / gameHeight;
        snakeDrawer = new SnakeDrawer(game.getSnakeMap().size());
        this.gameView.setSize(cellWidth, cellHeight);
    }

    /**
     * update for default game presenter.
     */
    public void update() {
        gameView.clear();
        drawField();
        drawGameObjects();
    }

    /**
     * draw game objects.
     */
    protected void drawGameObjects() {
        GameField field = game.getField();
        Map<Integer, Snake> snakeMap = game.getSnakeMap();
        for (GameUnit gameUnit : field.getAll()) {
            if (gameUnit instanceof Food food) {
                drawFood(food);
            }
        }
        snakeMap.forEach(this::drawSnake);
    }

    /**
     * draw cell meth.
     *
     * @param x x
     * @param y y
     */
    protected void drawCell(int x, int y) {
        Image image;
        if (((x + y) & 1) == 0) {
            image = ImageCollector.light_grass;
        } else {
            image = ImageCollector.dark_grass;
        }
        gameView.drawImage(image, x, y);
    }

    /**
     * draw field meth.
     */
    protected void drawField() {
        for (int x = 0; x < gameWidth; x++) {
            for (int y = 0; y < gameHeight; y++) {
                drawCell(x, y);
            }
        }
    }


    /**
     * draw food meth.
     *
     * @param food food
     */
    protected void drawFood(Food food) {
        gameView.drawImage(ImageCollector.getFood(food.getValue()),
                food.getX(), food.getY());
    }

    /**
     * draw snake meth.
     *
     * @param id    id
     * @param snake snake
     */
    protected void drawSnake(int id, Snake snake) {
        if (snake.isControllable()) {

            for (SnakeBody snakeBody : snake.getBody()) {
                Image bodyImage = snakeDrawer.getBodyImage(snakeBody.getDirection(), 0);
                gameView.drawImage(bodyImage, snakeBody.getX() * cellWidth,
                        snakeBody.getY() * cellWidth, cellWidth, cellHeight);
            }
            SnakeBody head = snake.getHead();
            Image headImage = snakeDrawer.getHeadImage(head.getDirection(), 0);
            gameView.drawImage(headImage, head.getX() * cellWidth, head.getY() * cellWidth,
                    cellWidth, cellHeight);
        }
    }

    /**
     * getting for game.
     *
     * @return game
     */
    public Game getGame() {
        return game;
    }

    /**
     * setting game.
     *
     * @param game game
     */
    public void setGame(Game game) {
        this.game = game;
    }
}