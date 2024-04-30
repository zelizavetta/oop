package nsu.fit.ezaitseva.snakegame.model.game.logic.events;

import nsu.fit.ezaitseva.snakegame.model.game.logic.Game;
import nsu.fit.ezaitseva.snakegame.model.units.Snake;

public abstract class SnakeEvent  implements Runnable{
    protected Snake snake;
    protected Game game;

    public SnakeEvent(Snake snake, Game game) {
        this.snake = snake;
        this.game = game;
    }
}
