package nsu.fit.ezaitseva.snakegame.console.constructor;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import nsu.fit.ezaitseva.snakegame.console.common.FieldView;
import nsu.fit.ezaitseva.snakegame.model.game.field.FieldConstructor;
import nsu.fit.ezaitseva.snakegame.model.game.field.FieldDAO;
import nsu.fit.ezaitseva.snakegame.model.game.field.GameField;
import nsu.fit.ezaitseva.snakegame.model.game.logic.Game;
import nsu.fit.ezaitseva.snakegame.model.units.Empty;
import nsu.fit.ezaitseva.snakegame.model.units.Food;
import nsu.fit.ezaitseva.snakegame.model.units.SnakeBody;
import nsu.fit.ezaitseva.snakegame.model.units.Wall;
import nsu.fit.ezaitseva.snakegame.units.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConstructorScene {
    private FieldConstructor fieldConstructor;
    private Screen screen;
    private TerminalPosition cursorPosition = new TerminalPosition(0, 0);
    private FieldDAO fieldDAO;
    private int width;
    private int height;

    public ConstructorScene(File file, Screen screen) {
        this.screen = screen;
        fieldDAO = new FieldDAO(file);
        GameField field = fieldDAO.getField().getField();
        fieldConstructor = new FieldConstructor(field.width(), field.height());
        field.getAll().forEach((gameUnit) -> fieldConstructor.setUnit(gameUnit));
        width = field.width();
        height = field.height();

    }

    public Game start() {
        ConstructorView gameScene = new ConstructorView(screen);
        boolean escapeFlag = true;
        refreshScreen(gameScene);
        while (escapeFlag) {
            try {
                KeyStroke keyStroke = screen.readInput();
                if (keyStroke != null) {
                    switch (keyStroke.getKeyType()) {
                        case Escape -> {
                            escapeFlag = false;
                        }
                        case ArrowDown -> cursorPosition = cursorPosition.withRow(Math.min(height - 1,
                                cursorPosition.getRow() + 1));
                        case ArrowUp -> cursorPosition = cursorPosition.withRow(Math.max(0,
                                cursorPosition.getRow() - 1));
                        case ArrowLeft -> cursorPosition = cursorPosition.withColumn(Math.max(0,
                                cursorPosition.getColumn() - 1));
                        case ArrowRight -> cursorPosition = cursorPosition.withColumn(Math.min(width - 1,
                                cursorPosition.getColumn() + 1));
                        case Delete -> {
                            fieldConstructor.setUnit(new Empty(cursorPosition.getColumn(),
                                    cursorPosition.getRow()));
                        }
                        case Character -> {
                            switch (keyStroke.getCharacter()) {
                                case 's', 'S' -> fieldConstructor.setUnit(new SnakeBody(cursorPosition.getColumn(),
                                        cursorPosition.getRow()));
                                case 'w', 'W' -> {
                                    fieldConstructor.setUnit(new Wall(cursorPosition.getColumn(),
                                            cursorPosition.getRow()));
                                }
                                case 'f', 'F' -> {
                                    fieldConstructor.setUnit(new Food(cursorPosition.getColumn(),
                                            cursorPosition.getRow(), 1));
                                }
                                case 'E', 'e' -> {
                                    fieldConstructor.setUnit(new Empty(cursorPosition.getColumn(),
                                            cursorPosition.getRow()));
                                }
                            }
                        }

                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            refreshScreen(gameScene);
        }
        Game game = fieldConstructor.getGameField();
        fieldDAO.saveField(game);
        return game;
    }

    private GameStateDTO getGameStateDTO() {
        List<FoodDTO> foods = new ArrayList<>();
        List<WallDTO> walls = new ArrayList<>();
        fieldConstructor.getAll().forEach((unit -> {
            if (unit instanceof Food food) {
                foods.add(new FoodDTO(new PointDTO(food.getX(), food.getY()), food.getValue()));
            }
            if (unit instanceof Wall wall) {
                walls.add(new WallDTO(new PointDTO(wall.getX(), wall.getY())));
            }
        }));
        List<SnakeDTO> snakeDTOS = new ArrayList<>();
        fieldConstructor.getSnakes().forEach(snake -> {
            List<SnakeBodyDTO> bodyDTOS = new ArrayList<>();
//                bodyDTOS.add(new SnakeBodyDTO(new PointDTO(snake.getX(), snake.getY()), DirectionDTO.UP));
            snakeDTOS.add(new SnakeDTO
                    (bodyDTOS, new SnakeHeadDTO(
                            new PointDTO(snake.getX(), snake.getY()),
                            DirectionDTO.valueOf(snake.getDirection().name())),
                            true, 0)
            );
        });
        return new GameStateDTO(snakeDTOS, walls, foods);
    }

    private void refreshScreen(FieldView fieldView) {
        fieldView.update(getGameStateDTO());
        screen.setCursorPosition(cursorPosition);
        try {
            screen.refresh();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}