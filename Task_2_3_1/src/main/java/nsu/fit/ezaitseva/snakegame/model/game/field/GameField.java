package nsu.fit.ezaitseva.snakegame.model.game.field;

import java.util.ArrayList;
import java.util.List;
import nsu.fit.ezaitseva.snakegame.model.units.Empty;
import nsu.fit.ezaitseva.snakegame.model.units.GameUnit;

/**
 * class for game field.
 */
public class GameField {
    private final List<List<GameUnit>> field;

    /**
     * class-constructor for game field.
     *
     * @param width  width
     * @param height height
     */
    public GameField(int width, int height) {
        field = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            ArrayList<GameUnit> list = new ArrayList<>();
            for (int j = 0; j < height; j++) {
                list.add(new Empty(i, j));
            }
            field.add(list);
        }
    }

    /**
     * Width int.
     *
     * @return the int
     */
    public int width() {
        return field.size();
    }

    /**
     * Height int.
     *
     * @return the int
     */
    public int height() {
        return field.get(0).size();
    }

    /**
     * Get game unit.
     *
     * @param x the x
     * @param y the y
     * @return the game unit
     */
    public GameUnit get(int x, int y) {
        if (x < 0 || x >= width() || y < 0 || y >= height()) {
            return null;
            //throw new exception
        }
        return field.get(x).get(y);
    }

    /**
     * Set.
     *
     * @param x    the x
     * @param y    the y
     * @param unit the unit
     */
    public void set(int x, int y, GameUnit unit) {
        field.get(x).set(y, unit);
    }

    /**
     * Set.
     *
     * @param unit the unit
     */
    public void set(GameUnit unit) {
        field.get(unit.getX()).set(unit.getY(), unit);
    }

    /**
     * Gets all.
     *
     * @return the all
     */
    public List<GameUnit> getAll() {
        return field.stream().reduce(new ArrayList<>(), (acc, units) -> {
            acc.addAll(units);
            return acc;
        });
    }
}
