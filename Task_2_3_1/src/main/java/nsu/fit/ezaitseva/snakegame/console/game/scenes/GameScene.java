package nsu.fit.ezaitseva.snakegame.console.game.scenes;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.screen.Screen;
import java.io.IOException;
import nsu.fit.ezaitseva.snakegame.console.common.FieldView;
import nsu.fit.ezaitseva.snakegame.console.game.sprites.utils.ConsoleUtils;
import nsu.fit.ezaitseva.snakegame.units.GameStateDto;

/**
 * class for game scene.
 */
public class GameScene extends FieldView {
    public GameScene(Screen screen) {
        super(screen);
    }

    /**
     * draw start scene meth.
     *
     * @param GameStateDto game state
     */
    public void drawStartScreen(GameStateDto GameStateDto) {
        var size = screen.getTerminalSize();
        update(GameStateDto);
        ConsoleUtils.printLine(
                screen,
                "Press any key to start",
                new TerminalPosition(size.getColumns() / 2 - 10, size.getRows() / 2 - 10));
        try {

            screen.refresh(Screen.RefreshType.COMPLETE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
