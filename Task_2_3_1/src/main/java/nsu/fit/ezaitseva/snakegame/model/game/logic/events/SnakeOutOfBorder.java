package nsu.fit.ezaitseva.snakegame.model.game.logic.events;

import nsu.fit.ezaitseva.snakegame.model.game.logic.Game;
import nsu.fit.ezaitseva.snakegame.model.units.Snake;

public class SnakeOutOfBorder extends SnakeEvent {
    public SnakeOutOfBorder(Snake snake, Game game) {
        super(snake, game);
    }

    @Override
    public void run() {
        new SnakeDeath(snake, game).run();
    }
}
