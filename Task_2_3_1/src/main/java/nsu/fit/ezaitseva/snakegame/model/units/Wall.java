package nsu.fit.ezaitseva.snakegame.model.units;


/**
 * The type Wall.
 */
public final class Wall extends GameUnit {
    /**
     * Instantiates a new Wall.
     *
     * @param x the x
     * @param y the y
     */
    public Wall(int x, int y) {
        super(x, y);
    }

    @Override
    public GameUnit getCopy() {
        return new Wall(getX(), getY());
    }
}
