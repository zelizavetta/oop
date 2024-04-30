package nsu.fit.ezaitseva.snakegame.model.players;

import nsu.fit.ezaitseva.snakegame.model.game.logic.Game;
import nsu.fit.ezaitseva.snakegame.model.game.logic.GameLogic;
import nsu.fit.ezaitseva.snakegame.model.units.*;
import nsu.fit.ezaitseva.snakegame.model.units.snake.Direction;

import java.util.Map;
import java.util.Random;

public class CustomizableEuristickBot extends PlayerListener {

    private final int range;
    /**
     * Should be from 0 to 1.
     */
    private double correct = 1.0;

    private double distanceSquare = 1.0;
    private double distanceLinear = 2.0;
    private double distanceConstant = 0.2;
    private double tailPenalty = 10;
    private double anotherSnakePenalty = 40;
    private double emptyPenalty = -5.0;
    private double wallPenalty = 80.0;
    private double foodPenalty = -100.0;
    private double snakeBodyPenalty = 80.0;
    private double nullPenalty = 40.0;
    private final Random random = new Random();

    public Double[] getCoefficientArr() {
        return new Double[]{distanceSquare, distanceLinear, distanceConstant,
                tailPenalty, anotherSnakePenalty, emptyPenalty,
                wallPenalty, foodPenalty, snakeBodyPenalty, nullPenalty};
    }

    public void setCoefficient(Double[] coefficient) {
//        distanceSquare = coefficient[0];
//        distanceLinear = coefficient[1];
//        distanceConstant = coefficient[2];
        tailPenalty = coefficient[3];
        anotherSnakePenalty = coefficient[4];
        emptyPenalty = coefficient[5];
        wallPenalty = coefficient[6];
        foodPenalty = coefficient[7];
        snakeBodyPenalty = coefficient[8];
        nullPenalty = coefficient[9];

    }

    public CustomizableEuristickBot(Game game, Integer snakeId) {
        this(game, snakeId, 3);
    }

    public CustomizableEuristickBot(Game game, Integer snakeId, int range) {
        super(game, snakeId);
        this.range = range;
    }

    public Direction nextDirection() {
        SnakeBody snakeHead = game.getSnakeMap().get(mySnakeId).getHead();
        double continuePenalty = calculateTotalPenalty(getNextUnit(nextPoint(snakeHead)));
        continuePenalty *= Math.max(random.nextDouble(), correct);
        GameUnit nextUnit;
        nextUnit = getNextUnit(
                nextPoint(snakeHead.getDirection().changeDirection(true),
                        snakeHead.getX(), snakeHead.getY()));
        double rightPenalty = calculateTotalPenalty(nextUnit);
        rightPenalty *= Math.max(random.nextDouble(), correct);
        nextUnit = getNextUnit(
                nextPoint(snakeHead.getDirection().changeDirection(false),
                        snakeHead.getX(), snakeHead.getY()));
        double leftPenalty = calculateTotalPenalty(nextUnit);
        leftPenalty *= Math.max(random.nextDouble(), correct);
        if (continuePenalty <= rightPenalty && continuePenalty <= leftPenalty) {
            return null;
        }
        if (rightPenalty < leftPenalty) {
            return snakeHead.getDirection().changeDirection(true);
        } else {
            return snakeHead.getDirection().changeDirection(false);
        }
    }

    private double getPenalty(GameUnit gameUnit) {
        if (gameUnit == null) {
            return nullPenalty;
        }
        if (gameUnit instanceof Food food) {
            return ((double) food.getValue() + 1) / 2 * foodPenalty;
        }
        if (gameUnit instanceof Empty) {
            return emptyPenalty;
        }
        if (gameUnit instanceof Wall) {
            return wallPenalty;
        }
        if (gameUnit instanceof SnakeBody snakeBody) {
            double res = snakeBodyPenalty;
            if (!snakeBody.getSnake().getBody().isEmpty() && snakeBody.getSnake().getBody().getLast() == snakeBody) {
                res = tailPenalty;
            }
            if (snakeBody.getSnake() == game.getSnakeMap().get(mySnakeId)) {
                if (snakeBody.isHead()) {
                    res = 0;
                }
            } else {
                res += anotherSnakePenalty;
            }
            return res;
        }
        return 0;
    }

    private double distanceScale(double distance) {
        double koef = (distance * distance * Math.abs(distanceSquare)
                + Math.abs(distanceLinear) * distance
                + Math.abs(distanceConstant));
        if (Math.abs(koef) < 0.001) {
            koef = 0.001;
        }
        return 1 / koef;
    }

    private Map.Entry<Integer, Integer> nextPoint(Direction direction, int x, int y) {
        return GameLogic.nextStep(direction, x, y);
    }

    private Map.Entry<Integer, Integer> nextPoint(SnakeBody head) {
        return nextPoint(head.getDirection(), head.getX(), head.getY());
    }


    private GameUnit getNextUnit(Map.Entry<Integer, Integer> pair) {
        int nextX = pair.getKey();
        int nextY = pair.getValue();
        return game.getUnitAt(nextX, nextY);
    }

    private double calculateTotalPenalty(GameUnit gameUnit) {
        if (gameUnit == null) {
            return -10000;
        }
        int x = gameUnit.getX();
        int y = gameUnit.getY();
        double res = 0;
        for (int i = x - range; i <= x + range; i++) {
            for (int j = y - range; j <= y + range; j++) {
                res += getPenalty(game.getUnitAt(i, j)) * distanceScale(Math.abs(i - x) + Math.abs(j - y));
            }
        }
        return res;
    }

    public double getCorrect() {
        return correct;
    }

    public void setCorrect(double correct) {
        this.correct = correct;
    }
}
