package nsu.fit.ezaitseva.snakegame.model.units;

import nsu.fit.ezaitseva.snakegame.model.units.snake.Direction;

public final class SnakeBody extends GameUnit {
    private Direction direction;
    private Snake snake;

    public SnakeBody(int x, int y, Direction direction) {
        super(x, y);
        this.direction = direction;
    }

    public SnakeBody(int x, int y) {
        this(x, y, Direction.LEFT);
//        this(x, y, Direction.getRandomDirection());
    }

    @Override
    public GameUnit getCopy() {
        return new SnakeBody(getX(), getY());
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public boolean isHead() {
        return snake.getHead().equals(this);
    }

    public Snake getSnake() {
        return snake;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

}
