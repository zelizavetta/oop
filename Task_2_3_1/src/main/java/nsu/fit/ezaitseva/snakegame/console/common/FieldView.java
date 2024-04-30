package nsu.fit.ezaitseva.snakegame.console.common;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;
import nsu.fit.ezaitseva.snakegame.console.game.sprites.UnitsCharacters;
import nsu.fit.ezaitseva.snakegame.units.*;

import java.awt.*;
import java.io.IOException;

public class FieldView {
    protected Screen screen;

    public FieldView(Screen screen) {
        this.screen = screen;
    }

    public void update(GameStateDTO gameStateDTO) {
        screen.clear();
        gameStateDTO.walls().forEach(this::drawWall);
        gameStateDTO.foodDTOS().forEach(this::drawFood);
        gameStateDTO.snakes().forEach(snakeDTO -> {
            if (snakeDTO.isAlive()) {
                drawSnake(snakeDTO);
            }
        });
        try {
            screen.refresh();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void drawFood(FoodDTO foodDTO) {
        setCharacterAtPoint(foodDTO.foodPoint(), new TextCharacter(UnitsCharacters.FOOD1, TextColor.ANSI.GREEN,
                TextColor.ANSI.BLACK));
    }

    private void drawSnake(SnakeDTO snakeDTO) {
        var snakeHead = snakeDTO.head();
        Character headCharacter = UnitsCharacters.HEADDOWN;
        switch (snakeHead.directionDTO()) {
            case UP -> headCharacter = UnitsCharacters.HEADUP;
            case DOWN -> headCharacter = UnitsCharacters.HEADDOWN;
            case LEFT -> headCharacter = UnitsCharacters.HEADLEFT;
            case RIGHT -> headCharacter = UnitsCharacters.HEADRIGHT;
        }
        TextColor textColor1 = new TextColor.ANSI.RGB(200, 200, 200);
        if (snakeDTO.id() == 0) {
            textColor1 = new TextColor.ANSI.RGB(250, 250, 150);
        }
        final TextColor textColor = textColor1;
        setCharacterAtPoint(new PointDTO(snakeHead.pointDTO().x(), snakeHead.pointDTO().y()),
                new TextCharacter(headCharacter, textColor, TextColor.ANSI.BLACK));

        Color color = textColor.toColor();
        int size = snakeDTO.body().size();
        class IntWrapper {
            int val;

            public IntWrapper(int val) {
                this.val = val;
            }
        }
        IntWrapper intWrapper = new IntWrapper(size);
        snakeDTO.body().forEach(snakeBodyDTO -> {
            int i = intWrapper.val--;
            int k = (i + size) >> 1;
            var snakeBody = snakeBodyDTO.pointDTO();
            setCharacterAtPoint(snakeBody, new TextCharacter(UnitsCharacters.BODY,
                    new TextColor.RGB(color.getRed() * k / size, color.getGreen() * k / size, color.getBlue() * k / size),
                    TextColor.ANSI.BLACK));
        });

    }

    private void drawWall(WallDTO wall) {
        setCharacterAtPoint(wall.point(), new TextCharacter(UnitsCharacters.WALL, TextColor.ANSI.YELLOW,
                TextColor.ANSI.BLACK));
    }

    private void setCharacterAtPoint(PointDTO point, Character character) {
        screen.setCharacter(point.x(), point.y(), new TextCharacter(character));
    }

    private void setCharacterAtPoint(PointDTO point, TextCharacter textCharacter) {
        screen.setCharacter(point.x(), point.y(), textCharacter);
    }
}