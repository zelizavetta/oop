package nsu.fit.ezaitseva.snakegame.units;

public enum DirectionDTO {
    LEFT, UP, RIGHT, DOWN;



    public int getAngle() {
        return switch (this) {
            case LEFT -> -90;
            case UP -> 180;
            case RIGHT -> 90;
            case DOWN -> 0;
        };
    }

}
