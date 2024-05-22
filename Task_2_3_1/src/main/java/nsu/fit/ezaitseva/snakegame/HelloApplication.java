package nsu.fit.ezaitseva.snakegame;

import java.io.IOException;
import nsu.fit.ezaitseva.snakegame.console.ConsoleSnakePresenter;
import nsu.fit.ezaitseva.snakegame.fx.SnakeFXApp;

/** start for application. */
public class HelloApplication {
  /**
   * main.
   *
   * @param args args
   */
  public static void main(String[] args) {
    if (args.length == 0 || args[0].equals("--javafx")) {
      SnakeFXApp app = new SnakeFXApp();
      app.run();
    } else {
      ConsoleSnakePresenter presenter;
      try {
        presenter = new ConsoleSnakePresenter();
        presenter.start();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
