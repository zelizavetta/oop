package nsu.fit.ezaitseva.snakegame.model.players;

import nsu.fit.ezaitseva.snakegame.model.game.logic.Game;
import nsu.fit.ezaitseva.snakegame.model.units.snake.Direction;

abstract public class PlayerListener {
    protected final Game game;
    protected final Integer mySnakeId;

    public PlayerListener(Game game, Integer snakeId) {
        this.game = game;
        mySnakeId = snakeId;
    }

    /**
     * The next direction for the snake, which will be transferred to the inside of the game.
     * However, a snake cannot turn 180 degrees at a time. So sometimes the directions won't work.
     *
     * @return the direction
     */
    abstract public Direction nextDirection();
}
