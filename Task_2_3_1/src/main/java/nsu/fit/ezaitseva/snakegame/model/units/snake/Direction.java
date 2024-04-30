package nsu.fit.ezaitseva.snakegame.model.units.snake;

import java.util.Random;

/**
 * Direction, with angles and indexes of direction. Useful for view and gamelogic
 */
public enum Direction {
    LEFT(0), UP(1), RIGHT(2), DOWN(3);
    private final int numberDirection;

    Direction(int numberDirection) {
        this.numberDirection = numberDirection;
    }

    public boolean isOpposite(Direction direction) {
        return (Math.abs(numberDirection - direction.numberDirection) % 3) == 2;
    }

    static boolean isOpposite(Direction direction1, Direction direction2) {
        return direction1.isOpposite(direction2);
    }

    public int getAngle() {
        return switch (this) {
            case LEFT -> -90;
            case UP -> 180;
            case RIGHT -> 90;
            case DOWN -> 0;
        };
    }

    public static Direction getRandomDirection() {
        Random random = new Random();
        final int val = random.nextInt(4);
        return getDirection(val);
    }

    public static Direction getDirection(int i) {
        return switch (i) {
            case 0 -> LEFT;
            case 1 -> UP;
            case 2 -> RIGHT;
            case 3 -> DOWN;
            default -> throw new IllegalStateException("Unexpected value: " + i);
        };
    }

    public Direction changeDirection(boolean isRighter) {
        int delta = -1;
        if (isRighter) {
            delta = 1;
        }
        return getDirection((numberDirection + delta + 4) % 4);
    }
}
