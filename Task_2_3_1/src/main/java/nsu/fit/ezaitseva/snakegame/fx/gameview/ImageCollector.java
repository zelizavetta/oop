package nsu.fit.ezaitseva.snakegame.fx.gameview;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

public class ImageCollector {
    public final static Image light_grass = loadImage("light_grass.png");
    public final static Image dark_grass = loadImage("dark_grass.png");
    public final static Image wall = loadImage("wall.png");
    public final static Image snakeBody = loadImage("snakeBody.png");
    public final static Image snakeHead = loadImage("snakeHead.png");
    public final static Image pointer = loadImage("pointer.png");
    public final static Image winner = loadImage("win.png");
    public static int maxFood = 3;
    private static final Random random = new Random();

    public static Image getFood(int foodValue) {
        if (foodValue > maxFood) {
            foodValue = maxFood;
        }
        foodValue -= 1;
        return loadImage("food_" + foodValue + ".png");
    }


    private static Image loadImage(String filename) {

        try {
            return new Image(new FileInputStream("resources\\textures\\" + filename));
        } catch (FileNotFoundException e) {
            WritableImage constr = new WritableImage(5, 5);
            constr.getPixelWriter().setColor(2, 2, new Color(1, 0, 0, 1));
            return constr;
        }
    }
}