package nsu.fit.ezaitseva.snakegame.fx.game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import nsu.fit.ezaitseva.snakegame.console.GameSettings;
import nsu.fit.ezaitseva.snakegame.console.settings.UserMode;
import nsu.fit.ezaitseva.snakegame.fx.GlobalGameSettings;
import nsu.fit.ezaitseva.snakegame.fx.gameview.DefaultGamePresenter;
import nsu.fit.ezaitseva.snakegame.fx.gameview.ImageCollector;
import nsu.fit.ezaitseva.snakegame.fx.gameview.SnakeDrawer;
import nsu.fit.ezaitseva.snakegame.model.players.CustomizableEuristickBot;
import nsu.fit.ezaitseva.snakegame.model.players.HumanPlayer;
import nsu.fit.ezaitseva.snakegame.model.players.PlayerListener;
import nsu.fit.ezaitseva.snakegame.model.units.Snake;
import nsu.fit.ezaitseva.snakegame.model.units.SnakeBody;
import nsu.fit.ezaitseva.snakegame.model.units.snake.Direction;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class GamePresenter extends DefaultGamePresenter {
    private final GameController gameController;
    private Timeline timeline = new Timeline();
    private HumanPlayer humanPlayer;
    private final KeyResolver keyResolver = new KeyResolver();
    private final int gameWidth;
    private final int gameHeight;
    private final double cellWidth;
    private final double cellHeight;
    private final GameSettings gameSettings = GlobalGameSettings.gameSettings;
    private final SnakeDrawer snakeDrawer;

    public GamePresenter(GameController gameController) {
        super(gameController);
        this.gameController = gameController;
        game = gameSettings.getGame().getCopy();
        gameWidth = game.width();
        gameHeight = game.height();
        cellWidth = gameController.getCanvasWidth() / gameWidth;
        cellHeight = gameController.getCanvasHeight() / gameHeight;
        snakeDrawer = new SnakeDrawer(game.getSnakeMap().size());//TODO:
        gameController.setSize(cellWidth, cellHeight);
    }

    public void init() {
        initPlayers();
    }

    public KeyResolver getKeyResolver() {
        return keyResolver;
    }

    private void initPlayers() {
        humanPlayer = new HumanPlayer(game, 0);
        if (gameSettings.getUserMode() == UserMode.Player) {
            game.addPlayer(0, humanPlayer);
            gameController.initPersonStatistic("0", ImageCollector.snakeBody);
        } else {
            game.addPlayer(0, getBot(0));
        }
        game.getSnakeMap().forEach(((id, snake) -> {
            if (id != 0) {
                game.addPlayer(id, getBot(id));
            }
        }));
    }

    @Override
    public void update() {
        gameController.clear();
        drawField();
        drawGameObjects();
        updateStatistics();
        updateLeaders();
    }

    private void updateStatistics() {
        if (humanPlayer != null) {
            gameController.updatePersonStatistic(humanPlayer.getScore() + "",
                    ImageCollector.snakeHead);
        }

    }


    private void restart() {
        timeline.stop();
        game = GlobalGameSettings.gameSettings.getGame().getCopy();
        update();
        initPlayers();
        gameController.hideWinner();
        timeline.play();
    }

    @Override
    public void drawSnake(int id, Snake snake) {
        if (snake.isControllable()) {

            for (SnakeBody snakeBody : snake.getBody()) {
                Image bodyImage = snakeDrawer.getBodyImage(snakeBody.getDirection(), id);
                gameController.drawImage(bodyImage, snakeBody.getX() * cellWidth, snakeBody.getY() * cellWidth,
                        cellWidth, cellHeight);
            }
            SnakeBody head = snake.getHead();
            Image headImage = snakeDrawer.getHeadImage(head.getDirection(), id);
            gameController.drawImage(headImage, head.getX() * cellWidth, head.getY() * cellWidth,
                    cellWidth, cellHeight);
        }

    }

    void startGame() {
        timeline = new Timeline(new KeyFrame(Duration.millis((double) 100 / (gameSettings.getGameSpeed())), ev -> {
            timeline.stop();
            if (!game.tick()) {
                gameController.showWinner();
                return;
            }
            update();
            timeline.play();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private PlayerListener getBot(int id) {
        PlayerListener playerListener;
        switch (new Random().nextInt(6)) {

            case 0 -> {
                CustomizableEuristickBot euristickBot = new CustomizableEuristickBot(game, id);
                euristickBot.setCoefficient(new Double[]{
                        0.09469544421753545, -0.47795602129867, 0.8347318158305251, -0.6973720681226039, 0.9464604177752554, -0.5310471017261655, 0.4256935038622005, -1.1735202638901958, 1.2132739993475155, 0.7046690479095463
                        //0.7664159201915209, -1.8893220925635683, 0.3414123838630473, -0.2366503429610878, 0.5539281285241162, -1.3241101679514, 1.2229872111885416, -1.999601995370626, 1.354171404201951, 0.4315954979823856
                });
                playerListener = euristickBot;
                euristickBot.setCorrect(gameSettings.getDifficult());
            }
            case 1 -> {
                CustomizableEuristickBot euristickBot = new CustomizableEuristickBot(game, id);
                euristickBot.setCoefficient(new Double[]{
                        0.113355912365809, -0.5392756791650084, 0.8736243720174397, -0.6029860009717296, 1.0418435344411954, -0.5325674024668574, 0.4920820254381191, -1.0827373196482935, 1.199074784965746, 0.6805539788418411
                        //0.7664221551452993, -2.3292002242198406, 0.3750945478344285, -1.0191706203924107, 0.990466926647421, -0.24505616190359297, 0.47664983093143076, -2.0664856125919777, 1.0459271897885696, 0.7929273437018975
                });
                playerListener = euristickBot;
                euristickBot.setCorrect(gameSettings.getDifficult());
            }
            case 2 -> {
                CustomizableEuristickBot euristickBot = new CustomizableEuristickBot(game, id);
                euristickBot.setCoefficient(new Double[]{
                        0.09865971684861066, -0.4643200390092408, 0.7857669324215983, -0.6228303030795024, 0.9228189402716853, -0.49254778454935, 0.4148989662520721, -1.2272530568992939, 1.2165053092305018, 0.7029863307757604
                });
                playerListener = euristickBot;
                euristickBot.setCorrect(gameSettings.getDifficult());
            }
            default -> {
                CustomizableEuristickBot euristickBot = new CustomizableEuristickBot(game, id);
                euristickBot.setCorrect(gameSettings.getDifficult());
                playerListener = euristickBot;
            }
        }

        return playerListener;
    }

    public void stop() {
        timeline.stop();
    }

    public void updateLeaders() {
        Map<Integer, Integer> results = game.getResults();
        List<Map.Entry<Integer, Integer>> listOfWinners = results.entrySet().stream()
                .sorted((entry1, entry2) -> -Long.compare(entry1.getValue(), entry2.getValue()))
                .limit(3).toList();
        for (int i = 0; i < listOfWinners.size(); i++) {
            Map.Entry<Integer, Integer> player = listOfWinners.get(i);
            gameController.updateLeaderBoard(i,
                    snakeDrawer.getHeadImage(player.getKey()),
                    player.getValue(),
                    player.getKey() + "");

        }
    }


    public class KeyResolver {
        public void resolveKeyCode(KeyCode keyCode) {
            if (keyCode == null) return;
            Runnable action = keyMap.get(keyCode);
            if (action != null) {
                action.run();
            }
        }

        private void changeDirection(Direction direction) {
            if (humanPlayer != null) {
                humanPlayer.setDirection(direction);
            }
        }

        private final Map<KeyCode, Runnable> keyMap
                = Map.of(
                KeyCode.R, GamePresenter.this::restart,
                KeyCode.Q, () -> {
                    gameController.exitToMenu();
                },

                KeyCode.UP, () -> {
                    changeDirection(Direction.DOWN);
                },

                KeyCode.DOWN, () -> {
                    changeDirection(Direction.UP);
                },

                KeyCode.LEFT, () -> {
                    changeDirection(Direction.LEFT);
                },

                KeyCode.RIGHT, () -> {
                    changeDirection(Direction.RIGHT);
                }
        );
    }
}