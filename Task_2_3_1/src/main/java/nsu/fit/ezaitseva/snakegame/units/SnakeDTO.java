package nsu.fit.ezaitseva.snakegame.units;

import java.util.List;

public record SnakeDTO(List<SnakeBodyDTO> body, SnakeHeadDTO head, boolean isAlive, Integer id) {

}
