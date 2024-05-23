package nsu.fit.ezaitseva.snakegame.console.menu;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import java.io.IOException;
import nsu.fit.ezaitseva.snakegame.console.menu.states.MenuPage;

/**
 * console menu scene class.
 */
public class ConsoleMenuScene {
    private MenuView menuView;

    private Screen screen;
    private int state = 0;

    /**
     * console menu scene class-constructor.
     *
     * @param screen screen
     */
    public ConsoleMenuScene(Screen screen) {
        this.screen = screen;
    }

    /**
     * start menu page.
     *
     * @return menu page
     * @throws IOException if exception
     */
    public MenuPage start() throws IOException {
        screen.startScreen();
        menuView = new MenuView(screen);
        state = 0;
        while (true) {
            int newState = state;
            KeyStroke keyStroke = screen.readInput();
            KeyType keyType = keyStroke.getKeyType();
            if (keyType.equals(KeyType.Enter)) {
                break;
            }
            if (keyType.equals(KeyType.Escape)) {
                state = 3;
                break;
            }
            if (keyType.equals(KeyType.ArrowDown)) {
                newState++;
            }
            if (keyType.equals(KeyType.ArrowUp)) {
                newState--;
            }

            if (newState < 0) {
                newState = 0;
            }
            if (newState > 2) {
                newState = 2;
            }
            if (newState != state) {
                state = newState;
                menuView.setCursorPosition(state);
            }
        }
        menuView.clear();

        return MenuPage.getMenuPage(state);
    }

    /**
     * close menu method.
     */
    public void close() {
        menuView.close();
    }
}
