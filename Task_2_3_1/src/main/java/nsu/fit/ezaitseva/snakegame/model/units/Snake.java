package nsu.fit.ezaitseva.snakegame.model.units;

import java.util.Deque;
import java.util.LinkedList;
import nsu.fit.ezaitseva.snakegame.model.units.snake.Direction;


/**
 * The type Snake.
 */
public final class Snake {
    private final Deque<SnakeBody> body = new LinkedList<>();
    private final SnakeBody head;

    private boolean isControllable = true;

    private int snakeFullness = 0;

    /**
     * Is growing boolean.
     *
     * @return the boolean
     */
    public boolean isGrowing() {
        return snakeFullness > 0;
    }

    /**
     * Instantiates a new Snake.
     *
     * @param head the head
     */
    public Snake(SnakeBody head) {
        this.head = head;
        head.setSnake(this);
    }

    /**
     * Instantiates a new Snake.
     *
     * @param x the x
     * @param y the y
     */
    public Snake(int x, int y) {
        head = new SnakeBody(x, y, Direction.getRandomDirection());
    }

    /**
     * Instantiates a new Snake.
     *
     * @param x         the x
     * @param y         the y
     * @param direction the direction
     */
    public Snake(int x, int y, Direction direction) {
        head = new SnakeBody(x, y, direction);
    }

    /**
     * Sets direction.
     *
     * @param direction the direction
     */
    public void setDirection(Direction direction) {
        head.setDirection(direction);
    }

    /**
     * Change direction boolean.
     *
     * @param direction the direction
     * @return the boolean
     */
    public boolean changeDirection(Direction direction) {
        if (direction.isOpposite(head.getDirection())) {
            return false;
        }
        head.setDirection(direction);
        return true;
    }

    /**
     * Gets head.
     *
     * @return the head
     */
    public SnakeBody getHead() {
        return head;
    }

    /**
     * Gets body.
     *
     * @return the body
     */
    public Deque<SnakeBody> getBody() {
        return body;
    }

    /**
     * Is controllable boolean.
     *
     * @return the boolean
     */
    public boolean isControllable() {
        return isControllable;
    }

    /**
     * Sets controllable.
     *
     * @param controllable the controllable
     */
    public void setControllable(boolean controllable) {
        isControllable = controllable;
    }

    /**
     * Move.
     *
     * @param x the x
     * @param y the y
     */
    public void move(int x, int y) {
        if (isControllable) {
            var newBody = new SnakeBody(head.getX(), head.getY(), head.getDirection());
            newBody.setSnake(this);
            body.addFirst(newBody);
            if (snakeFullness <= 0) {
                body.pollLast();

            }
            if (snakeFullness > 0) {
                snakeFullness--;
            }
            head.move(x, y);
        }

    }

    /**
     * Eat.
     *
     * @param food the food
     */
    public void eat(Food food) {
        if (isControllable) {
            SnakeBody bod;
            snakeFullness += food.getValue();
        }

    }

    /**
     * Length int.
     *
     * @return the int
     */
    public int length() {
        return body.size() + 1;
    }
}
