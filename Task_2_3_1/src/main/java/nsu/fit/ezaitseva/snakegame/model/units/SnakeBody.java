package nsu.fit.ezaitseva.snakegame.model.units;

import nsu.fit.ezaitseva.snakegame.model.units.snake.Direction;

/**
 * The type Snake body.
 */
public final class SnakeBody extends GameUnit {
    private Direction direction;
    private Snake snake;

    /**
     * Instantiates a new Snake body.
     *
     * @param x         the x
     * @param y         the y
     * @param direction the direction
     */
    public SnakeBody(int x, int y, Direction direction) {
        super(x, y);
        this.direction = direction;
    }

    /**
     * Instantiates a new Snake body.
     *
     * @param x the x
     * @param y the y
     */
    public SnakeBody(int x, int y) {
        this(x, y, Direction.LEFT);
//        this(x, y, Direction.getRandomDirection());
    }

    @Override
    public GameUnit getCopy() {
        return new SnakeBody(getX(), getY());
    }

    /**
     * Gets direction.
     *
     * @return the direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Sets direction.
     *
     * @param direction the direction
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Is head boolean.
     *
     * @return the boolean
     */
    public boolean isHead() {
        return snake.getHead().equals(this);
    }

    /**
     * Gets snake.
     *
     * @return the snake
     */
    public Snake getSnake() {
        return snake;
    }

    /**
     * Sets snake.
     *
     * @param snake the snake
     */
    public void setSnake(Snake snake) {
        this.snake = snake;
    }

}
