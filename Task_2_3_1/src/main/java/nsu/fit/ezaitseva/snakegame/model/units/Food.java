package nsu.fit.ezaitseva.snakegame.model.units;


public final class Food extends GameUnit {
    private final int value;

    public Food(int x, int y, int value) {
        super(x, y);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public GameUnit getCopy() {
        return new Food(getX(), getY(), value);
    }
}
