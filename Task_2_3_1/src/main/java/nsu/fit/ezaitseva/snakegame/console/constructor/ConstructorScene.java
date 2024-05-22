package nsu.fit.ezaitseva.snakegame.console.constructor;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import nsu.fit.ezaitseva.snakegame.console.common.FieldView;
import nsu.fit.ezaitseva.snakegame.model.game.field.FieldConstructor;
import nsu.fit.ezaitseva.snakegame.model.game.field.GameField;
import nsu.fit.ezaitseva.snakegame.model.game.field.FieldDao;
import nsu.fit.ezaitseva.snakegame.model.game.logic.Game;
import nsu.fit.ezaitseva.snakegame.model.units.Empty;
import nsu.fit.ezaitseva.snakegame.model.units.Food;
import nsu.fit.ezaitseva.snakegame.model.units.SnakeBody;
import nsu.fit.ezaitseva.snakegame.model.units.Wall;
import nsu.fit.ezaitseva.snakegame.units.DirectionDTO;
import nsu.fit.ezaitseva.snakegame.units.PointDTO;
import nsu.fit.ezaitseva.snakegame.units.SnakeBodyDTO;
import nsu.fit.ezaitseva.snakegame.units.SnakeHeadDTO;
import nsu.fit.ezaitseva.snakegame.units.WallDTO;
import nsu.fit.ezaitseva.snakegame.units.FoodDto;
import nsu.fit.ezaitseva.snakegame.units.GameStateDto;
import nsu.fit.ezaitseva.snakegame.units.SnakeDto;

/**
 * class-constructor for scene.
 */
public class ConstructorScene {
  private FieldConstructor fieldConstructor;
  private Screen screen;
  private TerminalPosition cursorPosition = new TerminalPosition(0, 0);
  private FieldDao FieldDao;
  private int width;
  private int height;

  public ConstructorScene(File file, Screen screen) {
    this.screen = screen;
    FieldDao = new FieldDao(file);
    GameField field = FieldDao.getField().getField();
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
            case ArrowDown ->
                cursorPosition =
                    cursorPosition.withRow(Math.min(height - 1, cursorPosition.getRow() + 1));
            case ArrowUp ->
                cursorPosition = cursorPosition.withRow(Math.max(0, cursorPosition.getRow() - 1));
            case ArrowLeft ->
                cursorPosition =
                    cursorPosition.withColumn(Math.max(0, cursorPosition.getColumn() - 1));
            case ArrowRight ->
                cursorPosition =
                    cursorPosition.withColumn(Math.min(width - 1, cursorPosition.getColumn() + 1));
            case Delete -> {
              fieldConstructor.setUnit(
                  new Empty(cursorPosition.getColumn(), cursorPosition.getRow()));
            }
            case Character -> {
              switch (keyStroke.getCharacter()) {
                case 's', 'S' ->
                    fieldConstructor.setUnit(
                        new SnakeBody(cursorPosition.getColumn(), cursorPosition.getRow()));
                case 'w', 'W' -> {
                  fieldConstructor.setUnit(
                      new Wall(cursorPosition.getColumn(), cursorPosition.getRow()));
                }
                case 'f', 'F' -> {
                  fieldConstructor.setUnit(
                      new Food(cursorPosition.getColumn(), cursorPosition.getRow(), 1));
                }
                case 'E', 'e' -> {
                  fieldConstructor.setUnit(
                      new Empty(cursorPosition.getColumn(), cursorPosition.getRow()));
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
    FieldDao.saveField(game);
    return game;
  }

  private GameStateDto getGameStateDto() {
    List<FoodDto> foods = new ArrayList<>();
    List<WallDTO> walls = new ArrayList<>();
    fieldConstructor
        .getAll()
        .forEach(
            (unit -> {
              if (unit instanceof Food food) {
                foods.add(new FoodDto(new PointDTO(food.getX(), food.getY()), food.getValue()));
              }
              if (unit instanceof Wall wall) {
                walls.add(new WallDTO(new PointDTO(wall.getX(), wall.getY())));
              }
            }));
    List<SnakeDto> SnakeDtoS = new ArrayList<>();
    fieldConstructor
        .getSnakes()
        .forEach(
            snake -> {
              List<SnakeBodyDTO> bodyDtos = new ArrayList<>();
              //                bodyDtos.add(new SnakeBodyDTO(new PointDTO(snake.getX(),
              // snake.getY()), DirectionDTO.UP));
              SnakeDtoS.add(
                  new SnakeDto(
                      bodyDtos,
                      new SnakeHeadDTO(
                          new PointDTO(snake.getX(), snake.getY()),
                          DirectionDTO.valueOf(snake.getDirection().name())),
                      true,
                      0));
            });
    return new GameStateDto(SnakeDtoS, walls, foods);
  }

  private void refreshScreen(FieldView fieldView) {
    fieldView.update(getGameStateDto());
    screen.setCursorPosition(cursorPosition);
    try {
      screen.refresh();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
