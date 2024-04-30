package nsu.fit.ezaitseva.snakegame.model.units;

/**
 * Empty cell without any other units.
 */
public final class Empty extends GameUnit {
    public Empty(int x, int y) {
        super(x, y);
    }

    public Empty(GameUnit unit) {
        super(unit.getX(), unit.getY());
    }

    @Override
    public GameUnit getCopy() {
        return new Empty(getX(), getY());
    }
}
