package nsu.fit.ezaitseva.snakegame.fx.menu;

import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class SnakeGameTitle extends Pane {
    private Text text;

    public SnakeGameTitle(String name) {
        String spread = "";
        for (char c : name.toCharArray()) {
            spread += c + " ";
        }

        text = new Text(spread);

        text.setFill(Color.ORANGE);
        text.setEffect(new Lighting());
        text.setEffect(new DropShadow(10, 5, 5, Color.BLACK));
        text.setFont(new Font(48));
        getChildren().addAll(text);
    }

    public double getTitleWidth() {
        return text.getLayoutBounds().getWidth();
    }

    public double getTitleHeight() {
        return text.getLayoutBounds().getHeight();
    }
}