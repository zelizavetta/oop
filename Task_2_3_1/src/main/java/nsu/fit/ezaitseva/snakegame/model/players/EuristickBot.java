package nsu.fit.ezaitseva.snakegame.model.players;

import java.util.Map;
import nsu.fit.ezaitseva.snakegame.model.game.logic.Game;
import nsu.fit.ezaitseva.snakegame.model.game.logic.GameLogic;
import nsu.fit.ezaitseva.snakegame.model.units.*;
import nsu.fit.ezaitseva.snakegame.model.units.snake.Direction;


public class EuristickBot extends PlayerListener {
    private final int range;

    public EuristickBot(Game game, Integer snakeId) {
        this(game, snakeId, 4);
    }

    public EuristickBot(Game game, Integer snakeId, int range) {

        super(game, snakeId);
        this.range = range;
    }

    @Override
    public Direction nextDirection() {
        SnakeBody snakeHead = game.getSnakeMap().get(mySnakeId).getHead();
        double continuePenalty = calculateTotalPenalty(getNextUnit(nextPoint(snakeHead)));
        GameUnit nextUnit;
        nextUnit = getNextUnit(
                nextPoint(snakeHead.getDirection().changeDirection(true),
                        snakeHead.getX(), snakeHead.getY()));
        double rightPenalty = calculateTotalPenalty(nextUnit);
        nextUnit = getNextUnit(
                nextPoint(snakeHead.getDirection().changeDirection(false),
                        snakeHead.getX(), snakeHead.getY()));
        double leftPenalty = calculateTotalPenalty(nextUnit);
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
            return 40;
        }
        if (gameUnit instanceof Wall) {
            return 80;
        }
        if (gameUnit instanceof Empty) {
            return -1;
        }
        if (gameUnit instanceof Food food) {
            return -((double) food.getValue() + 1) / 2 * 130;
        }
        if (gameUnit instanceof SnakeBody snakeBody) {
            double res = 50;
            if (!snakeBody.getSnake().getBody().isEmpty() && snakeBody.getSnake().getBody().getLast() == snakeBody) {
                res = 10;
            }
            if (snakeBody.getSnake() == game.getSnakeMap().get(mySnakeId)) {
                if (snakeBody.isHead()) {
                    res = 0;
                }
            } else {
                res += 40;
            }
            return res;
        }
        return 0;
    }

    private double distanceScale(double distance) {
        return 1 / (distance * distance + 2 * distance + 0.2);
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
}
