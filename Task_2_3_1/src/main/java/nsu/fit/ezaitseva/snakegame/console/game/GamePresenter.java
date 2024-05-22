package nsu.fit.ezaitseva.snakegame.console.game;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import nsu.fit.ezaitseva.snakegame.console.GameSettings;
import nsu.fit.ezaitseva.snakegame.console.game.scenes.GameScene;
import nsu.fit.ezaitseva.snakegame.console.game.scenes.WinnerScene;
import nsu.fit.ezaitseva.snakegame.console.settings.UserMode;
import nsu.fit.ezaitseva.snakegame.model.game.logic.Game;
import nsu.fit.ezaitseva.snakegame.model.players.CommonBotPlayer;
import nsu.fit.ezaitseva.snakegame.model.players.EuristickBot;
import nsu.fit.ezaitseva.snakegame.model.players.HumanPlayer;
import nsu.fit.ezaitseva.snakegame.model.players.PlayerListener;
import nsu.fit.ezaitseva.snakegame.model.units.snake.Direction;
import nsu.fit.ezaitseva.snakegame.units.gameStateDto;

/** class for presenter of the game. */
public class GamePresenter {

  private Game game;
  private Screen screen;
  private boolean escapeFlag = false;
  private GameSettings gameSettings;

  /**
   * constructor.
   *
   * @param game game
   * @param gameSettings settings of the game
   * @param screen screen
   */
  public GamePresenter(Game game, GameSettings gameSettings, Screen screen) {
    this.game = game;
    this.screen = screen;
    this.gameSettings = gameSettings;
  }

  /**
   * start game.
   *
   * @return 1 if success
   */
  public int start() {
    GameScene gameScene = new GameScene(screen);
    gameScene.drawStartScreen(gameStateDto.getGameState(game));
    HumanPlayer humanPlayer = new HumanPlayer(game, 0);
    if (gameSettings.getUserMode() == UserMode.Player) {
      game.addPlayer(0, humanPlayer);
    } else {
      game.addPlayer(0, getBot(0));
    }
    game.getSnakeMap()
        .forEach(
            ((id, snake) -> {
              if (id != 0) {
                game.addPlayer(id, getBot(id));
              }
            }));
    try {
      screen.readInput();
      screen.clear();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    DirectionWrapper directionWrapper = new DirectionWrapper(null);

    Timer timer = new Timer();
    BooleanWrapper booleanWrapper = new BooleanWrapper(true);
    timer.schedule(
        new TimerTask() {
          @Override
          public void run() {
            if (!booleanWrapper.flag) {
              this.cancel();
            }
            gameScene.update(gameStateDto.getGameState(game));
            try {
              screen.refresh();
            } catch (IOException e) {
              throw new RuntimeException(e);
            }
            if (directionWrapper.direction != null) {
              humanPlayer.setDirection(directionWrapper.direction);
            }
            if (game.tick()) {
              try {
                Thread.sleep(10 * ((long) (8 - gameSettings.getGameSpeed())));
              } catch (InterruptedException e) {
                throw new RuntimeException(e);
              }
            } else {
              booleanWrapper.flag = false;
              this.cancel();
            }
          }
        },
        100,
        10 * ((long) (8 - gameSettings.getGameSpeed())));

    while (loopCondition() && booleanWrapper.flag) {
      try {
        KeyStroke keyStroke = null;
        KeyStroke tmpStroke;
        while ((tmpStroke = screen.pollInput()) != null) {
          keyStroke = tmpStroke;
        }
        if (keyStroke != null) {
          switch (keyStroke.getKeyType()) {
            case Escape -> {
              escapeFlag = true;
            }
            default -> directionWrapper.direction = gameSettings.keyDirection(keyStroke);
          }
        }
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
    WinnerScene winnerScene = new WinnerScene(screen, game.getResults());
    winnerScene.showWinners();
    timer.cancel();
    while (true) {
      KeyStroke key = null;
      try {
        key = screen.readInput();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      if (key.getKeyType().equals(KeyType.Escape)) {
        break;
      }
    }

    return 1;
  }

  public boolean loopCondition() {

    return !escapeFlag;
  }

  private PlayerListener getBot(int id) {
    if (true) {
      return new EuristickBot(game, id);
    } else {
      return new CommonBotPlayer(game, id);
    }
  }

  private static class DirectionWrapper {
    Direction direction;

    public DirectionWrapper(Direction direction) {
      this.direction = direction;
    }
  }

  private static class BooleanWrapper {
    boolean flag;

    public BooleanWrapper(boolean flag) {
      this.flag = flag;
    }
  }
}
