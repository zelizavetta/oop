package nsu.fit.ezaitseva.snakegame.units;

import nsu.fit.ezaitseva.snakegame.model.game.logic.Game;
import nsu.fit.ezaitseva.snakegame.model.units.Food;
import nsu.fit.ezaitseva.snakegame.model.units.SnakeBody;
import nsu.fit.ezaitseva.snakegame.model.units.Wall;

import java.util.ArrayList;
import java.util.List;

public record GameStateDTO(List<SnakeDTO> snakes,
                           List<WallDTO> walls,
                           List<FoodDTO> foodDTOS) {
    public static GameStateDTO getGameState(Game game) {
        List<FoodDTO> foods = new ArrayList<>();
        List<WallDTO> walls = new ArrayList<>();
        game.getField().getAll().forEach((unit -> {
            if (unit instanceof Food food) {
                foods.add(new FoodDTO(new PointDTO(food.getX(), food.getY()), food.getValue()));
            }
            if (unit instanceof Wall wall) {
                walls.add(new WallDTO(new PointDTO(wall.getX(), wall.getY())));
            }
        }));
        List<SnakeDTO> snakeDTOS = new ArrayList<>();
        game.getSnakeMap().forEach((id, snake) -> {
            List<SnakeBodyDTO> bodyDTOS = new ArrayList<>();
            snake.getBody().forEach(snakeBody -> {
                bodyDTOS.add(new SnakeBodyDTO(new PointDTO(snakeBody.getX(), snakeBody.getY()),
                        DirectionDTO.valueOf(snakeBody.getDirection().toString())));
            });
            SnakeBody head = snake.getHead();
            snakeDTOS.add(new SnakeDTO
                    (bodyDTOS, new SnakeHeadDTO(
                            new PointDTO(head.getX(), head.getY()),
                            DirectionDTO.valueOf(head.getDirection().name())),
                            snake.isControllable(), id)
            );
        });
        return new GameStateDTO(snakeDTOS, walls, foods);
    }
}
