package nsu.fit.ezaitseva.snakegame.model.game.logic.events;

import nsu.fit.ezaitseva.snakegame.model.game.logic.Game;
import nsu.fit.ezaitseva.snakegame.model.units.Empty;
import nsu.fit.ezaitseva.snakegame.model.units.Food;
import nsu.fit.ezaitseva.snakegame.model.units.Snake;
import nsu.fit.ezaitseva.snakegame.model.units.SnakeBody;

import java.util.LinkedList;
import java.util.List;

public class SnakeDeath extends SnakeEvent {
    public SnakeDeath(Snake snake, Game game) {
        super(snake, game);
    }


    public void run() {
        snake.setControllable(false);
        List<SnakeBody> snakeBodies = new LinkedList<>(snake.getBody());
        SnakeBody head = snake.getHead();

        snakeBodies.forEach((snakeBody -> {
            if (game.getUnitAt(snakeBody.getX(), snakeBody.getY()) instanceof SnakeBody) {
//                if (((snakeBody.getX() ^ snakeBody.getY() % 2) & 1) == 0) {
//                    game.setGameUnit(new Food(snakeBody.getX(), snakeBody.getY(), 1));
//                    game.getGameLogic().addToFood(1);
//                } else {
//                    game.setGameUnit(new Empty(snakeBody));
//                }
                game.setGameUnit(new Empty(snakeBody));
            }
        }));
        game.setGameUnit(new Food(head.getX(), head.getY(), 3));

    }
}
