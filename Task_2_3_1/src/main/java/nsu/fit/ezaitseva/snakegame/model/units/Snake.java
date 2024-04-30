package nsu.fit.ezaitseva.snakegame.model.units;

import nsu.fit.ezaitseva.snakegame.model.units.snake.Direction;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Snake is head and list of its body.
 * Snake also has fullness and flag that snake is controllable.
 */
public final class Snake {
    private final Deque<SnakeBody> body = new LinkedList<>();
    private final SnakeBody head;
    /**
     * Флаг указывающий на то, что змейка не управляема и вместе с этим, что ёё не надо отображать.
     * В логике программы, змейка мертва, не должна отображаться и неконтроллируема - всё в одном.
     */
    private boolean isControllable = true;
    /**
     * Сколько змея накопила еды. Нужно для того, что при передвижении, хвост не исчезает, а остаётся на месте.
     */
    private int snakeFullness = 0;

    public boolean isGrowing() {
        return snakeFullness > 0;
    }

    public Snake(SnakeBody head) {
        this.head = head;
        head.setSnake(this);
    }

    public Snake(int x, int y) {
        head = new SnakeBody(x, y, Direction.getRandomDirection());
    }

    public Snake(int x, int y, Direction direction) {
        head = new SnakeBody(x, y, direction);
    }

    public void setDirection(Direction direction) {
        head.setDirection(direction);
    }

    public boolean changeDirection(Direction direction) {
        if (direction.isOpposite(head.getDirection())) {
            return false;
        }
        head.setDirection(direction);
        return true;
    }

    public SnakeBody getHead() {
        return head;
    }

    public Deque<SnakeBody> getBody() {
        return body;
    }

    public boolean isControllable() {
        return isControllable;
    }

    public void setControllable(boolean controllable) {
        isControllable = controllable;
    }

    /**
     * Двигается с изменением тела и положением головы
     *
     * @param x next x coordinate
     * @param y next y coordinate
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

    public void eat(Food food) {
        if (isControllable) {
            SnakeBody bod;
            snakeFullness += food.getValue();
        }

    }

    public int length() {
        return body.size() + 1;
    }
}
