package nsu.fit.ezaitseva.snakegame.model.game.logic;

import java.util.Map;
import java.util.Random;
import nsu.fit.ezaitseva.snakegame.model.game.logic.events.SnakeDeath;
import nsu.fit.ezaitseva.snakegame.model.game.logic.events.SnakeEating;
import nsu.fit.ezaitseva.snakegame.model.game.logic.events.SnakeOutOfBorder;
import nsu.fit.ezaitseva.snakegame.model.units.Empty;
import nsu.fit.ezaitseva.snakegame.model.units.Food;
import nsu.fit.ezaitseva.snakegame.model.units.GameUnit;
import nsu.fit.ezaitseva.snakegame.model.units.Snake;
import nsu.fit.ezaitseva.snakegame.model.units.SnakeBody;
import nsu.fit.ezaitseva.snakegame.model.units.Wall;
import nsu.fit.ezaitseva.snakegame.model.units.snake.Direction;

/**
 * The type Game logic.
 */
public class GameLogic {
    private Game game;
    private long amountOfFood;


    /**
     * Instantiates a new Game logic.
     *
     * @param game the game
     */
    public GameLogic(Game game) {
        this.game = game;
        amountOfFood = game.getField().getAll().stream().
                filter((unit -> unit instanceof Food)).count();
    }

    /**
     * Проверяет, может ли змея двинуться. Так же разрешает коллизии с помощью событий.
     *
     * @param snake - змея которая двинется
     * @return true если всё прошло нормально и змея подвинулась
     */
    public boolean moveSnake(Snake snake) {
        SnakeBody head = snake.getHead();
        int nextX = head.getX(), nextY = head.getY();
        switch (head.getDirection()) {
            case LEFT -> nextX--;
            case UP -> nextY++;
            case RIGHT -> nextX++;
            case DOWN -> nextY--;
            default -> {}
        }
        if (nextX < 0 || nextX >= game.width()
                || nextY < 0 || nextY >= game.height()) {
            SnakeOutOfBorder event = new SnakeOutOfBorder(snake, game);
            event.run();
            return false;
        }
        if (!interaction(snake, game.getUnitAt(nextX, nextY))) {
            return false;
        }
        snake.move(nextX, nextY);
        return true;
    }

    /**
     * Interaction boolean.
     *
     * @param snake the snake
     * @param unit  the unit
     * @return the boolean
     */
    public boolean interaction(Snake snake, GameUnit unit) {
        if (unit instanceof Food) {
            SnakeWithFood(snake, (Food) unit);
            return true;
        }
        if (unit instanceof Empty) {
            return true;
        }
        if (unit instanceof Wall) {
            SnakeWithWall(snake, (Wall) unit);
            return false;
        }
        if (unit instanceof SnakeBody) {
            return SnakeToSnake(snake, (SnakeBody) unit);
        }
        return true;
    }

    /**
     * Snake with food boolean.
     *
     * @param snake the snake
     * @param food  the food
     * @return the boolean
     */
    public boolean SnakeWithFood(Snake snake, Food food) {
        new SnakeEating(snake, game, food).run();
        amountOfFood--;
        return true;
    }

    /**
     * Snake with wall boolean.
     *
     * @param snake the snake
     * @param wall  the wall
     * @return the boolean
     */
    public boolean SnakeWithWall(Snake snake, Wall wall) {
        new SnakeDeath(snake, game).run();
        return false;
    }

    /**
     * Snake to snake boolean.
     *
     * @param movingSnake  the moving snake
     * @param stayingSnake the staying snake
     * @return the boolean
     */
    public boolean SnakeToSnake(Snake movingSnake, SnakeBody stayingSnake) {
        new SnakeDeath(movingSnake, game).run();
        return false;

    }

    private Random random = new Random();

    /**
     * Пытается добавить еду, учитывая ограничения.
     *
     * @return true if food was added
     */
    public boolean spawnFood() {
        if (amountOfFood > 20) {
            return false;
        }

        int x = random.nextInt(game.width());
        int y = random.nextInt(game.height());
        if (game.getUnitAt(x, y) instanceof Empty) {
            game.setGameUnit(new Food(x, y, random.nextInt(2) + 1
            ));
            amountOfFood++;
            return true;
        } else {
            return trySpawnFood(x - 1, y) || trySpawnFood(x, y - 1)
                    || trySpawnFood(x + 1, y) || trySpawnFood(x - 1, y);
        }
    }

    /**
     * Пытается добавить еду. Если место занято, то не добовляет
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return true if food was added
     */
    private boolean trySpawnFood(int x, int y) {
        if (x > 0 && y > 0
                && x < game.width() && y < game.height()
                && game.getUnitAt(x, y) instanceof Empty) {
            game.setGameUnit(new Food(x, y, 1));
            amountOfFood++;
            return true;
        }
        return false;
    }

    /**
     * Ad dto food.
     *
     * @param delta the delta
     */
    public void adDtoFood(int delta) {
        amountOfFood += delta;
    }

    /**
     * Next step map . entry.
     *
     * @param direction the direction
     * @param x         the x
     * @param y         the y
     * @return the map . entry
     */
    public static Map.Entry<Integer, Integer> nextStep(Direction direction, int x, int y) {
        switch (direction) {
            case LEFT -> x--;
            case UP -> y++;
            case RIGHT -> x++;
            case DOWN -> y--;
            default -> {}
        }
        int finalX = x;
        int finalY = y;
        return new Map.Entry<Integer, Integer>() {
            @Override
            public Integer getKey() {
                return finalX;
            }

            @Override
            public Integer getValue() {
                return finalY;
            }

            @Override
            public Integer setValue(Integer integer) {
                throw new UnsupportedOperationException();
            }
        };
    }

}
