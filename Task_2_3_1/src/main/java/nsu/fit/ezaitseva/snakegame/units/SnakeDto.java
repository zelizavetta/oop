package nsu.fit.ezaitseva.snakegame.units;

import java.util.List;

public record SnakeDto(List<SnakeBodyDTO> body, SnakeHeadDTO head, boolean isAlive, Integer id) {

}
