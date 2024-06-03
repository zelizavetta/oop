package nsu.fit.ezaitseva.snakegame.model.players;

import nsu.fit.ezaitseva.snakegame.model.game.logic.Game;
import nsu.fit.ezaitseva.snakegame.model.units.snake.Direction;

/**
 * The type Player listener.
 */
abstract public class PlayerListener {
    /**
     * The Game.
     */
    protected final Game game;
    /**
     * The My snake id.
     */
    protected final Integer mySnakeId;

    /**
     * Instantiates a new Player listener.
     *
     * @param game    the game
     * @param snakeId the snake id
     */
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
