package nsu.fit.ezaitseva.snakegame.model.units;

import nsu.fit.ezaitseva.snakegame.model.basic.Point;

/**
 * Base of game logic after point.
 */
public abstract class GameUnit extends Point {
    public GameUnit(int x, int y) {
        super(x, y);
    }

    public GameUnit(GameUnit unit) {
        super(unit.getX(), unit.getY());
    }

    /**
     * Create new instance of this.
     *
     * @return new instance of gameUnit
     */
    abstract public GameUnit getCopy();

    public GameUnit getCopy(int x, int y) {
        GameUnit gameUnit = getCopy();
        gameUnit.setX(x);
        gameUnit.setY(y);
        return gameUnit;
    }


}
