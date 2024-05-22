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

    public int width() {
        return field.size();
    }

    public int height() {
        return field.get(0).size();
    }

    public GameUnit get(int x, int y) {
        if (x < 0 || x >= width() || y < 0 || y >= height()) {
            return null;
            //throw new exception
        }
        return field.get(x).get(y);
    }

    public void set(int x, int y, GameUnit unit) {
        field.get(x).set(y, unit);
    }

    public void set(GameUnit unit) {
        field.get(unit.getX()).set(unit.getY(), unit);
    }

    public List<GameUnit> getAll() {
        return field.stream().reduce(new ArrayList<>(), (acc, units) -> {
            acc.addAll(units);
            return acc;
        });
    }
}
