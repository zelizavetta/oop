package nsu.fit.ezaitseva.snakegame.console.game.sprites.utils;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;

public class ConsoleUtils {
    public static void printLine(Screen screen, String text, TerminalPosition position) {
        printLine(screen, text, position, TextColor.ANSI.DEFAULT);
    }

    public static void printLine(Screen screen, String text, TerminalPosition position, TextColor textColor) {
        TerminalPosition terminalPosition = new TerminalPosition(position.getColumn(), position.getRow());
        for (int i = 0; i < text.length(); i++) {
            Character character = text.charAt(i);
            screen.setCharacter(terminalPosition.withColumn(terminalPosition.getColumn() + i),
                    new TextCharacter(character, textColor, TextColor.ANSI.BLACK));

        }
    }
}