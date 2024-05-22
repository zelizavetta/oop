package nsu.fit.ezaitseva.snakegame.console.settings;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;
import java.io.IOException;
import nsu.fit.ezaitseva.snakegame.console.game.sprites.utils.ConsoleUtils;

public class SettingsView {
    private Screen screen;
    private TerminalPosition gameSpeedPosition;
    private int speed;

    public SettingsView(Screen screen, int speed) {
        this.screen = screen;
        screen.clear();
        TerminalSize size = screen.getTerminalSize();
        initialisePositions();
        this.speed = speed;
        ConsoleUtils.printLine(
                screen,
                "Settings",
                new TerminalPosition(Math.min(3, size.getColumns() / 2 - 3), 1),
                TextColor.ANSI.CYAN);
        screen.setCursorPosition(gameSpeedPosition.withColumn(gameSpeedPosition.getColumn() - 1));
        drawText();
    }

    private void initialisePositions() {
        TerminalSize size = screen.getTerminalSize();
        gameSpeedPosition =
                new TerminalPosition(
                        Math.max(size.getColumns() / 2 - 7, 1), Math.max(1, size.getRows() / 2 - 3));
    }

    public void clear() {
        screen.clear();
    }

    public void close() {
        screen.clear();
        try {
            screen.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void changeSpeed(int speed) throws IOException {
        this.speed = speed;
        drawText();
    }

    private void drawText() {
        ConsoleUtils.printLine(
                screen,
                "Settings",
                new TerminalPosition(Math.min(3, screen.getTerminalSize().getColumns() / 2 - 3), 1),
                TextColor.ANSI.CYAN);
        String text =
                (canDecrease() ? "<" : " ")
                        + "Speed |"
                        + "#".repeat(speed)
                        + " ".repeat(5 - speed)
                        + "|"
                        + (canIncrease() ? ">" : " ");
        ConsoleUtils.printLine(screen, text, gameSpeedPosition);
        try {
            screen.refresh();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean canIncrease() {
        return speed < 5;
    }

    private boolean canDecrease() {
        return speed > 1;
    }
}
