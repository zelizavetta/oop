package nsu.fit.ezaitseva.snakegame.model.game.field;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nsu.fit.ezaitseva.snakegame.model.game.logic.Game;
import nsu.fit.ezaitseva.snakegame.model.units.Empty;
import nsu.fit.ezaitseva.snakegame.model.units.GameUnit;
import nsu.fit.ezaitseva.snakegame.model.units.Snake;
import nsu.fit.ezaitseva.snakegame.model.units.SnakeBody;

/**
 * class for fields.
 */
public class FieldConstructor {
    private final GameField gameField;
    private final Set<SnakeBody> snakeHeads = new HashSet<>();

    /**
     * class-constructor for field.
     *
     * @param width  width
     * @param height height
     */
    public FieldConstructor(int width, int height) {
        gameField = new GameField(width, height);
    }

    /**
     * Sets unit.
     *
     * @param unit the unit
     */
    public void setUnit(GameUnit unit) {
        int x = unit.getX();
        int y = unit.getY();
        if (x > gameField.width() || y > gameField.height() || x < 0 || y < 0) {
            throw new RuntimeException();
        }
        if (gameField.get(x, y) instanceof SnakeBody) {
            snakeHeads.remove(gameField.get(x, y));
        }
        if (unit instanceof SnakeBody) {
            snakeHeads.add((SnakeBody) unit);
        }
        gameField.set(unit);
    }

    /**
     * Remove unit.
     *
     * @param x the x
     * @param y the y
     */
    public void removeUnit(int x, int y) {
        gameField.set(new Empty(x, y));
    }

    /**
     * Gets unit.
     *
     * @param x the x
     * @param y the y
     * @return the unit
     */
    public GameUnit getUnit(int x, int y) {
        return gameField.get(x, y);
    }

    /**
     * Gets game field.
     *
     * @return the game field
     */
    public Game getGameField() {
        Map<Integer, Snake> snakes = new HashMap<>();
        int i = 0;
        for (SnakeBody snakeBody : snakeHeads) {
            snakes.put(i, new Snake(snakeBody));
            i++;
        }
        return new Game(gameField, snakes);
    }

    /**
     * Gets all.
     *
     * @return the all
     */
    public List<GameUnit> getAll() {
        return gameField.getAll();
    }

    /**
     * Gets snakes.
     *
     * @return the snakes
     */
    public List<SnakeBody> getSnakes() {
        return snakeHeads.stream().toList();
    }

    /**
     * Width int.
     *
     * @return the int
     */
    public int width() {
        return gameField.width();
    }

    /**
     * Height int.
     *
     * @return the int
     */
    public int height() {
        return gameField.height();
    }
}
