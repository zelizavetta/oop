package nsu.fit.ezaitseva.snakegame.console;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.File;
import java.io.IOException;
import nsu.fit.ezaitseva.snakegame.console.constructor.ConstructorScene;
import nsu.fit.ezaitseva.snakegame.console.game.GamePresenter;
import nsu.fit.ezaitseva.snakegame.console.game.sprites.utils.MenuConfig;
import nsu.fit.ezaitseva.snakegame.console.menu.ConsoleMenuScene;
import nsu.fit.ezaitseva.snakegame.console.menu.states.MenuPage;
import nsu.fit.ezaitseva.snakegame.console.settings.ConsoleSettingsScene;
import nsu.fit.ezaitseva.snakegame.model.game.logic.Game;


/**
 * presenter of snake.
 */
public class ConsoleSnakePresenter {
    private Terminal terminal;
    private Screen screen;
    private GameSettings settings;
    private final File mapDir;
    private TerminalSize defaultSize;

    /**
     * class-constructor for snake presenter.
     *
     * @throws IOException if exception
     */
    public ConsoleSnakePresenter() throws IOException {

        terminal =
                new DefaultTerminalFactory()
                        .setInitialTerminalSize(new TerminalSize(
                                MenuConfig.WIDTH, MenuConfig.HEIGHT))
                        .createTerminal();
        screen = new TerminalScreen(terminal);
        mapDir = new File("resources/maps");
        System.out.println(mapDir.getAbsolutePath());
        settings = new GameSettings();
        defaultSize = terminal.getTerminalSize();
    }

    /**
     * start of console snake presenter.
     *
     * @throws IOException if exception
     */
    public void start() throws IOException {

        ConsoleMenuScene menuScene = new ConsoleMenuScene(screen);
        boolean flag = true;
        while (flag) {
            MenuPage menuPage = menuScene.start();
            switch (menuPage) {
                case Game -> {
                    terminal.setCursorVisible(false);
                    screen.setCursorPosition(new TerminalPosition(1000, 1000));
                    Game game = settings.getGame().getCopy();
                    GamePresenter gamePresenter = new GamePresenter(game, settings, screen);
                    gamePresenter.start();
                }
                case Settings -> {
                    ConsoleSettingsScene settingsPresenter = new ConsoleSettingsScene(
                            screen, settings);
                    settingsPresenter.start();
                }
                case Exit -> {
                    flag = false;
                }
                case FieldConstructor -> {
                    ConstructorScene constructorScene = new ConstructorScene(
                            settings.getFile(), screen);
                    settings.setGame(constructorScene.start());
                }
                default -> {

                }
            }
        }
        menuScene.close();
    }
}
