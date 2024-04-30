package nsu.fit.ezaitseva.snakegame.model.game.logic.events;

import nsu.fit.ezaitseva.snakegame.model.game.logic.Game;
import nsu.fit.ezaitseva.snakegame.model.units.Empty;
import nsu.fit.ezaitseva.snakegame.model.units.Food;
import nsu.fit.ezaitseva.snakegame.model.units.Snake;

public class SnakeEating extends SnakeEvent {
    private final Food food;

    public SnakeEating(Snake snake, Game game, Food food) {
        super(snake, game);
        this.food = food;
    }

    @Override
    public void run() {
        snake.eat(food);
        game.setGameUnit(new Empty(food.getX(), food.getY()));
    }
}
