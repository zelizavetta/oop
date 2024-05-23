package nsu.fit.ezaitseva.snakegame.console.menu.states;

/**
 * menu page.
 */
public enum MenuPage {
    Game(0),
    Settings(1),
    FieldConstructor(2),
    Exit(3);
    private final int index;

    /**
     * menu page constructor.
     *
     * @param index menu index
     */
    MenuPage(int index) {
        this.index = index;
    }

    /**
     * getting menu meth.
     *
     * @param index index menu
     * @return program behavior
     */
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
