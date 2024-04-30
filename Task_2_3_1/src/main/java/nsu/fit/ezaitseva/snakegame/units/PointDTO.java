package nsu.fit.ezaitseva.snakegame.units;

import java.util.Objects;

public record PointDTO(int x, int y) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointDTO pointDTO = (PointDTO) o;
        return x == pointDTO.x && y == pointDTO.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
