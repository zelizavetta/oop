package nsu.fit.ezaitseva.snakegame.console.common;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;
import java.awt.Color;
import java.io.IOException;
import nsu.fit.ezaitseva.snakegame.console.game.sprites.UnitsCharacters;
import nsu.fit.ezaitseva.snakegame.units.FoodDto;
import nsu.fit.ezaitseva.snakegame.units.GameStateDto;
import nsu.fit.ezaitseva.snakegame.units.PointDTO;
import nsu.fit.ezaitseva.snakegame.units.SnakeDto;
import nsu.fit.ezaitseva.snakegame.units.WallDTO;

/** class describing view for field. */
public class FieldView {
  protected Screen screen;

  public FieldView(Screen screen) {
    this.screen = screen;
  }

  /**
   * update meth.
   *
   * @param gameStateDto state of the game
   */
  public void update(GameStateDto gameStateDto) {
    screen.clear();
    gameStateDto.walls().forEach(this::drawWall);
    gameStateDto.FoodDtoS().forEach(this::drawFood);
    gameStateDto.snakes()
        .forEach(
            snakeDto -> {
              if (snakeDto.isAlive()) {
                drawSnake(snakeDto);
              }
            });
    try {
      screen.refresh();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void drawFood(FoodDto foodDto) {
    setCharacterAtPoint(
        foodDto.foodPoint(),
        new TextCharacter(UnitsCharacters.FOOD1, TextColor.ANSI.GREEN, TextColor.ANSI.BLACK));
  }

  private void drawSnake(SnakeDto snakeDto) {
    var snakeHead = snakeDto.head();
    Character headCharacter = UnitsCharacters.HEADDOWN;
    switch (snakeHead.directionDTO()) {
      case UP -> headCharacter = UnitsCharacters.HEADUP;
      case DOWN -> headCharacter = UnitsCharacters.HEADDOWN;
      case LEFT -> headCharacter = UnitsCharacters.HEADLEFT;
      case RIGHT -> headCharacter = UnitsCharacters.HEADRIGHT;
    }
    TextColor textColor1 = new TextColor.ANSI.RGB(200, 200, 200);
    if (snakeDto.id() == 0) {
      textColor1 = new TextColor.ANSI.RGB(250, 250, 150);
    }
    final TextColor textColor = textColor1;
    setCharacterAtPoint(
        new PointDTO(snakeHead.pointDTO().x(), snakeHead.pointDTO().y()),
        new TextCharacter(headCharacter, textColor, TextColor.ANSI.BLACK));

    Color color = textColor.toColor();
    int size = snakeDto.body().size();
    class IntWrapper {
      int val;

      public IntWrapper(int val) {
        this.val = val;
      }
    }

    IntWrapper intWrapper = new IntWrapper(size);
    snakeDto.body()
        .forEach(
            snakeBodyDTO -> {
              int i = intWrapper.val--;
              int k = (i + size) >> 1;
              var snakeBody = snakeBodyDTO.pointDTO();
              setCharacterAtPoint(
                  snakeBody,
                  new TextCharacter(
                      UnitsCharacters.BODY,
                      new TextColor.RGB(
                          color.getRed() * k / size,
                          color.getGreen() * k / size,
                          color.getBlue() * k / size),
                      TextColor.ANSI.BLACK));
            });
  }

  private void drawWall(WallDTO wall) {
    setCharacterAtPoint(
        wall.point(),
        new TextCharacter(UnitsCharacters.WALL, TextColor.ANSI.YELLOW, TextColor.ANSI.BLACK));
  }

  private void setCharacterAtPoint(PointDTO point, TextCharacter textCharacter) {
    screen.setCharacter(point.x(), point.y(), textCharacter);
  }
}
