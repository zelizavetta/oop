package nsu.fit.ezaitseva.snakegame;


import nsu.fit.ezaitseva.snakegame.console.ConsoleSnakePresenter;
import nsu.fit.ezaitseva.snakegame.fx.SnakeFXApp;

import java.io.IOException;

public class HelloApplication {
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