package nsu.fit.ezaitseva.snakegame.console.settings;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import java.io.IOException;
import nsu.fit.ezaitseva.snakegame.console.GameSettings;

/**
 * setting of console scene.
 */
public class ConsoleSettingsScene {

    private SettingsView settingsView;

    private Screen screen;
    private int state = 0;
    private GameSettings gameSettings;

    /**
     * class-constructor for console scene.
     *
     * @param screen       screen
     * @param gameSettings game settings
     */
    public ConsoleSettingsScene(Screen screen, GameSettings gameSettings) {
        this.screen = screen;
        this.gameSettings = gameSettings;
    }

    /**
     * start of console scene.
     *
     * @throws IOException if exception
     */
    public void start() throws IOException {
        screen.clear();
        settingsView = new SettingsView(screen, gameSettings.getGameSpeed());
        while (true) {
            KeyStroke keyStroke = screen.readInput();
            KeyType keyType = keyStroke.getKeyType();
            if (keyType.equals(KeyType.Enter)) {
                break;
            }
            if (keyType.equals(KeyType.Escape)) {
                break;
            }
            if (keyType.equals(KeyType.ArrowRight)) {
                tryIncreaseSpeed(1);
            }
            if (keyType.equals(KeyType.ArrowLeft)) {
                tryIncreaseSpeed(-1);
            }
            settingsView.changeSpeed(gameSettings.getGameSpeed());
        }
    }

    /**
     * increasing speed if possible.
     *
     * @param deltaValue delta value for speed
     * @return possible or not
     */
    private boolean tryIncreaseSpeed(int deltaValue) {
        int newSpeed = gameSettings.getGameSpeed() + deltaValue;
        if (newSpeed < 1 || newSpeed > 5) {
            return false;
        }
        gameSettings.setGameSpeed(newSpeed);
        return true;
    }
}
