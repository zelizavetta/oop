package nsu.fit.ezaitseva.snakegame.model.players;

import nsu.fit.ezaitseva.snakegame.model.basic.Point;
import nsu.fit.ezaitseva.snakegame.model.game.logic.Game;
import nsu.fit.ezaitseva.snakegame.model.units.snake.Direction;


/**
 * The type Human player.
 */
public class HumanPlayer extends PlayerListener {
    private Direction direction = Direction.getRandomDirection();

    /**
     * Instantiates a new Human player.
     *
     * @param game    the game
     * @param snakeId the snake id
     */
    public HumanPlayer(Game game, Integer snakeId) {
        super(game, snakeId);
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

    @Override
    public Direction nextDirection() {
        return direction;
    }

    /**
     * Gets coordinates.
     *
     * @return the coordinates
     */
    public Point getCoordinates() {
        return game.getSnakeMap().get(mySnakeId).getHead().getCopy();
    }

    /**
     * Gets score.
     *
     * @return the score
     */
    public int getScore() {
        return game.getSnakeMap().get(mySnakeId).getBody().size();
    }
}
