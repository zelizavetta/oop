package nsu.fit.ezaitseva.snakegame.console.menu.states;

public enum MenuPage {
    Game(0),
    Settings(1),
    FieldConstructor(2),
    Exit(3);
    private final int index;

    MenuPage(int index) {
        this.index = index;
    }

    public static MenuPage getMenuPage(int index) {
        return switch (index) {
            case 0 -> Game;
            case 1 -> Settings;
            case 2 -> FieldConstructor;
            case 3 -> Exit;
            default -> Game;
        };
    }
}
