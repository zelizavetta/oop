package nsu.fit.ezaitseva.snakegame.units;

import java.util.Objects;

/**
 * point class.
 *
 * @param x x
 * @param y y
 */
public record PointDto(int x, int y) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointDto pointDto = (PointDto) o;
        return x == pointDto.x && y == pointDto.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
