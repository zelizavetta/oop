package nsu.fit.ezaitseva.snakegame.model.game.logic.events;

import nsu.fit.ezaitseva.snakegame.model.game.logic.Game;
import nsu.fit.ezaitseva.snakegame.model.units.Snake;

/**
 * snake out of border case class.
 */
public class SnakeOutOfBorder extends SnakeEvent {
    /**
     * snake out of border case constructor class.
     *
     * @param snake snake
     * @param game  game
     */
    public SnakeOutOfBorder(Snake snake, Game game) {
        super(snake, game);
    }

    @Override
    public void run() {
        new SnakeDeath(snake, game).run();
    }
}
