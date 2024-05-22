package nsu.fit.ezaitseva.snakegame.units;

import java.util.ArrayList;
import java.util.List;
import nsu.fit.ezaitseva.snakegame.model.game.logic.Game;
import nsu.fit.ezaitseva.snakegame.model.units.Food;
import nsu.fit.ezaitseva.snakegame.model.units.SnakeBody;
import nsu.fit.ezaitseva.snakegame.model.units.Wall;

/**
 * states of the game.
 *
 * @param snakes   snakes
 * @param walls    walls
 * @param FoodDtoS food
 */
public record GameStateDto(List<SnakeDto> snakes,
                           List<WallDto> walls,
                           List<FoodDto> FoodDtoS) {
    public static GameStateDto getGameState(Game game) {
        List<FoodDto> foods = new ArrayList<>();
        List<WallDto> walls = new ArrayList<>();
        game.getField().getAll().forEach((unit -> {
            if (unit instanceof Food food) {
                foods.add(new FoodDto(new PointDto(food.getX(), food.getY()), food.getValue()));
            }
            if (unit instanceof Wall wall) {
                walls.add(new WallDto(new PointDto(wall.getX(), wall.getY())));
            }
        }));
        List<SnakeDto> SnakeDtoS = new ArrayList<>();
        game.getSnakeMap().forEach((id, snake) -> {
            List<SnakeBodyDto> bodyDtos = new ArrayList<>();
            snake.getBody().forEach(snakeBody -> {
                bodyDtos.add(new SnakeBodyDto(new PointDto(snakeBody.getX(), snakeBody.getY()),
                        DirectionDto.valueOf(snakeBody.getDirection().toString())));
            });
            SnakeBody head = snake.getHead();
            SnakeDtoS.add(new SnakeDto
                    (bodyDtos, new SnakeHeadDto(
                            new PointDto(head.getX(), head.getY()),
                            DirectionDto.valueOf(head.getDirection().name())),
                            snake.isControllable(), id)
            );
        });
        return new GameStateDto(SnakeDtoS, walls, foods);
    }
}
