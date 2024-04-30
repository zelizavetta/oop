package nsu.fit.ezaitseva.snakegame.fx.gameview;

import javafx.scene.image.Image;

public interface GameView {
    double getViewWidth();

    double getViewHeight();

    void setSize(double cellWidth, double cellHeight);

    void clear();

    void drawImage(Image wall, double x, double y);

    void drawImage(Image bodyImage, double x, double y, double width, double height);
}