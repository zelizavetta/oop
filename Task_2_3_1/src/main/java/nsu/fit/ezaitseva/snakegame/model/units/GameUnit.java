package nsu.fit.ezaitseva.snakegame.model.units;

import nsu.fit.ezaitseva.snakegame.model.basic.Point;


/**
 * The type Game unit.
 */
public abstract class GameUnit extends Point {
    /**
     * Instantiates a new Game unit.
     *
     * @param x the x
     * @param y the y
     */
    public GameUnit(int x, int y) {
        super(x, y);
    }

    /**
     * Instantiates a new Game unit.
     *
     * @param unit the unit
     */
    public GameUnit(GameUnit unit) {
        super(unit.getX(), unit.getY());
    }

    /**
     * Create new instance of this.
     *
     * @return new instance of gameUnit
     */
    abstract public GameUnit getCopy();

    /**
     * Gets copy.
     *
     * @param x the x
     * @param y the y
     * @return the copy
     */
    public GameUnit getCopy(int x, int y) {
        GameUnit gameUnit = getCopy();
        gameUnit.setX(x);
        gameUnit.setY(y);
        return gameUnit;
    }


}
