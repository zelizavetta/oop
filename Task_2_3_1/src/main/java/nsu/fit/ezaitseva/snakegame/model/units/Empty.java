package nsu.fit.ezaitseva.snakegame.model.units;


/**
 * The type Empty.
 */
public final class Empty extends GameUnit {
    /**
     * Instantiates a new Empty.
     *
     * @param x the x
     * @param y the y
     */
    public Empty(int x, int y) {
        super(x, y);
    }

    /**
     * Instantiates a new Empty.
     *
     * @param unit the unit
     */
    public Empty(GameUnit unit) {
        super(unit.getX(), unit.getY());
    }

    @Override
    public GameUnit getCopy() {
        return new Empty(getX(), getY());
    }
}
