package nsu.fit.ezaitseva.snakegame.units;

import nsu.fit.ezaitseva.snakegame.model.game.logic.Game;
import nsu.fit.ezaitseva.snakegame.model.units.Food;
import nsu.fit.ezaitseva.snakegame.model.units.SnakeBody;
import nsu.fit.ezaitseva.snakegame.model.units.Wall;

import java.util.ArrayList;
import java.util.List;

public record GameStateDto(List<SnakeDto> snakes,
                           List<WallDTO> walls,
                           List<FoodDto> FoodDtoS) {
    public static GameStateDto getGameState(Game game) {
        List<FoodDto> foods = new ArrayList<>();
        List<WallDTO> walls = new ArrayList<>();
        game.getField().getAll().forEach((unit -> {
            if (unit instanceof Food food) {
                foods.add(new FoodDto(new PointDTO(food.getX(), food.getY()), food.getValue()));
            }
            if (unit instanceof Wall wall) {
                walls.add(new WallDTO(new PointDTO(wall.getX(), wall.getY())));
            }
        }));
        List<SnakeDto> SnakeDtoS = new ArrayList<>();
        game.getSnakeMap().forEach((id, snake) -> {
            List<SnakeBodyDTO> bodyDtos = new ArrayList<>();
            snake.getBody().forEach(snakeBody -> {
                bodyDtos.add(new SnakeBodyDTO(new PointDTO(snakeBody.getX(), snakeBody.getY()),
                        DirectionDTO.valueOf(snakeBody.getDirection().toString())));
            });
            SnakeBody head = snake.getHead();
            SnakeDtoS.add(new SnakeDto
                    (bodyDtos, new SnakeHeadDTO(
                            new PointDTO(head.getX(), head.getY()),
                            DirectionDTO.valueOf(head.getDirection().name())),
                            snake.isControllable(), id)
            );
        });
        return new GameStateDto(SnakeDtoS, walls, foods);
    }
}
