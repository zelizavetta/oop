package nsu.fit.ezaitseva.snakegame.model.units;


public final class Wall extends GameUnit {
    public Wall(int x, int y) {
        super(x, y);
    }

    @Override
    public GameUnit getCopy() {
        return new Wall(getX(), getY());
    }
}
