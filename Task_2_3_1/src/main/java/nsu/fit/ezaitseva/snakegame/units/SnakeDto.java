package nsu.fit.ezaitseva.snakegame.units;

import java.util.List;

/**
 * snake class.
 *
 * @param body    body
 * @param head    head
 * @param isAlive alive or note
 * @param id      snake id
 */
public record SnakeDto(List<SnakeBodyDto> body, SnakeHeadDto head, boolean isAlive, Integer id) {

}
