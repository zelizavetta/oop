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

    public void removeUnit(int x, int y) {
        gameField.set(new Empty(x, y));
    }

    public GameUnit getUnit(int x, int y) {
        return gameField.get(x, y);
    }

    public Game getGameField() {
        Map<Integer, Snake> snakes = new HashMap<>();
        int i = 0;
        for (SnakeBody snakeBody : snakeHeads) {
            snakes.put(i, new Snake(snakeBody));
            i++;
        }
        return new Game(gameField, snakes);
    }

    public List<GameUnit> getAll() {
        return gameField.getAll();
    }

    public List<SnakeBody> getSnakes() {
        return snakeHeads.stream().toList();
    }

    public int width() {
        return gameField.width();
    }

    public int height() {
        return gameField.height();
    }
}
