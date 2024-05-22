package nsu.fit.ezaitseva.snakegame.console;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import nsu.fit.ezaitseva.snakegame.console.settings.SavedSettings;
import nsu.fit.ezaitseva.snakegame.console.settings.UserMode;
import nsu.fit.ezaitseva.snakegame.model.game.field.fieldDao;
import nsu.fit.ezaitseva.snakegame.model.game.logic.Game;
import nsu.fit.ezaitseva.snakegame.model.units.snake.Direction;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GameSettings {
  private int gameSpeed = 1;

  private double difficult = 1;
  private UserMode userMode = UserMode.Player;
  private Game game;
  private File file;
  private final File settingFile = new File("resources/settings.json");

  private final Map<Character, Direction> keyCharacterDirectionMap;
  private final Map<KeyType, Direction> keyTypeDirectionMap;

  public GameSettings() {
    this.file = new File("C:\\Users\\Elisa\\oop\\Task_2_3_1\\src\\main\\resources\\maps", "1");
    setGame(new fieldDao(file).getField());
    keyCharacterDirectionMap = new HashMap<>();
    keyTypeDirectionMap = new HashMap<>();
    initializeKeyMap();
    initializeFromSaved();
  }

  private void initializeFromSaved() {

    if (settingFile.exists()) {
      try {
        SavedSettings savedSettings =
            new ObjectMapper().readValue(settingFile, SavedSettings.class);
        gameSpeed = savedSettings.speed();
        userMode = savedSettings.userMode();
        difficult = savedSettings.difficult();
      } catch (IOException e) {
        System.err.println("There is problem with json");
      }
    } else {
      SavedSettings savedSettings = new SavedSettings(gameSpeed, userMode, difficult);
      try {
        new ObjectMapper().writeValue(settingFile, savedSettings);
      } catch (IOException e) {
        System.err.println("Not saved");
      }
    }
  }

  public void saveIntoFile() {
    SavedSettings savedSettings = new SavedSettings(gameSpeed, userMode, difficult);
    try {
      new ObjectMapper().writeValue(settingFile, savedSettings);
    } catch (IOException e) {
      System.err.println("Not saved");
    }
  }

  public Game getRandomGame() {
    return null;
  }

  public Game getCustom() {
    setGame(new fieldDao(file).getField());
    return game;
  }

  private void initializeKeyMap() {
    keyTypeDirectionMap.put(KeyType.ArrowDown, Direction.UP);
    keyTypeDirectionMap.put(KeyType.ArrowUp, Direction.DOWN);
    keyTypeDirectionMap.put(KeyType.ArrowLeft, Direction.LEFT);
    keyTypeDirectionMap.put(KeyType.ArrowRight, Direction.RIGHT);

    keyCharacterDirectionMap.put('s', Direction.UP);
    keyCharacterDirectionMap.put('w', Direction.DOWN);
    keyCharacterDirectionMap.put('l', Direction.LEFT);
    keyCharacterDirectionMap.put('r', Direction.RIGHT);
    keyCharacterDirectionMap.put('S', Direction.UP);
    keyCharacterDirectionMap.put('W', Direction.DOWN);
    keyCharacterDirectionMap.put('L', Direction.LEFT);
    keyCharacterDirectionMap.put('R', Direction.RIGHT);
  }

  public Direction keyDirection(KeyStroke keyStroke) {
    if (keyStroke == null) {
      return null;
    }
    if (keyStroke.getKeyType().equals(KeyType.Character)) {
      return keyCharacterDirectionMap.get(keyStroke.getCharacter());
    } else {
      return keyTypeDirectionMap.get(keyStroke.getKeyType());
    }
  }

  public File getFile() {
    return file;
  }

  public int getGameSpeed() {
    return gameSpeed;
  }

  public void setGameSpeed(int gameSpeed) {
    this.gameSpeed = gameSpeed;
  }

  public UserMode getUserMode() {
    return userMode;
  }

  public void setUserMode(UserMode userMode) {
    this.userMode = userMode;
  }

  public Game getGame() {
    return game;
  }

  public void setGame(Game game) {
    this.game = game;
  }

  public double getDifficult() {
    return difficult;
  }

  public void setDifficult(double difficult) {
    this.difficult = difficult;
  }
}
