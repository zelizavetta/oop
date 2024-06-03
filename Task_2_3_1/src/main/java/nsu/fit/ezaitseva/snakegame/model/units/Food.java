package nsu.fit.ezaitseva.snakegame.model.units;


/**
 * The type Food.
 */
public final class Food extends GameUnit {
    private final int value;

    /**
     * Instantiates a new Food.
     *
     * @param x     the x
     * @param y     the y
     * @param value the value
     */
    public Food(int x, int y, int value) {
        super(x, y);
        this.value = value;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public int getValue() {
        return value;
    }

    @Override
    public GameUnit getCopy() {
        return new Food(getX(), getY(), value);
    }
}
