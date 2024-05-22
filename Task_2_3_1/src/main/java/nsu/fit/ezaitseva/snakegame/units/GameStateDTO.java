package nsu.fit.ezaitseva.snakegame.units;

import nsu.fit.ezaitseva.snakegame.model.game.logic.Game;
import nsu.fit.ezaitseva.snakegame.model.units.Food;
import nsu.fit.ezaitseva.snakegame.model.units.SnakeBody;
import nsu.fit.ezaitseva.snakegame.model.units.Wall;

import java.util.ArrayList;
import java.util.List;

public record gameStateDto(List<snakeDto> snakes,
                           List<WallDTO> walls,
                           List<foodDto> foodDtoS) {
    public static gameStateDto getGameState(Game game) {
        List<foodDto> foods = new ArrayList<>();
        List<WallDTO> walls = new ArrayList<>();
        game.getField().getAll().forEach((unit -> {
            if (unit instanceof Food food) {
                foods.add(new foodDto(new PointDTO(food.getX(), food.getY()), food.getValue()));
            }
            if (unit instanceof Wall wall) {
                walls.add(new WallDTO(new PointDTO(wall.getX(), wall.getY())));
            }
        }));
        List<snakeDto> snakeDtoS = new ArrayList<>();
        game.getSnakeMap().forEach((id, snake) -> {
            List<SnakeBodyDTO> bodyDtos = new ArrayList<>();
            snake.getBody().forEach(snakeBody -> {
                bodyDtos.add(new SnakeBodyDTO(new PointDTO(snakeBody.getX(), snakeBody.getY()),
                        DirectionDTO.valueOf(snakeBody.getDirection().toString())));
            });
            SnakeBody head = snake.getHead();
            snakeDtoS.add(new snakeDto
                    (bodyDtos, new SnakeHeadDTO(
                            new PointDTO(head.getX(), head.getY()),
                            DirectionDTO.valueOf(head.getDirection().name())),
                            snake.isControllable(), id)
            );
        });
        return new gameStateDto(snakeDtoS, walls, foods);
    }
}
