package nsu.fit.ezaitseva.snakegame.fx.gameview;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.SnapshotParameters;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import nsu.fit.ezaitseva.snakegame.model.units.snake.Direction;

/**
 * class for snake drawer.
 */
public class SnakeDrawer {
    private final SnapshotParameters parameters;
    private final int playersAmount;
    private final Map<Integer, Map<Direction, HeadBody>> imageViewMap = new HashMap<>();


    /**
     * class-constructor for snake drawer.
     *
     * @param playersAmount amount of players
     */
    public SnakeDrawer(int playersAmount) {
        this.playersAmount = playersAmount;
        parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        initSkinMap();
    }

    /**
     * init for skin map.
     */
    private void initSkinMap() {
        for (int id = 0; id < playersAmount; id++) {
            ImageView headView = new ImageView(ImageCollector.snakeHead);
            ImageView bodyView = new ImageView(ImageCollector.snakeBody);
            ColorAdjust color = new ColorAdjust((double) id / playersAmount * 1.1, 0, 0, 0);
            headView.setEffect(color);
            bodyView.setEffect(color);
            Map<Direction, HeadBody> directionMap = new HashMap<>();
            for (Direction value : Direction.values()) {
                double angle = value.getAngle();
                headView.setRotate(angle);
                bodyView.setRotate(angle);
                directionMap.put(value, new HeadBody(headView.snapshot(parameters, null),
                        bodyView.snapshot(parameters, null)));
                headView.setRotate(-angle);
                bodyView.setRotate(-angle);
            }
            imageViewMap.put(id, directionMap);
        }
    }

    private record HeadBody(Image headImage, Image bodyImage) {
    }


    /**
     * getting head image.
     *
     * @param headDirection head direction
     * @param id            id
     * @return image
     */
    public Image getHeadImage(Direction headDirection, int id) {
        HeadBody headBody = imageViewMap.get(id).get(headDirection);
        return headBody.headImage;
    }

    /**
     * getting head image.
     *
     * @param id id
     * @return image
     */
    public Image getHeadImage(int id) {
        HeadBody headBody = imageViewMap.get(id).get(Direction.UP);
        return headBody.headImage;
    }

    /**
     * getting body image.
     *
     * @param bodyDirection body direction
     * @param id            id
     * @return image
     */
    public Image getBodyImage(Direction bodyDirection, int id) {
        HeadBody headBody = imageViewMap.get(id).get(bodyDirection);
        return headBody.bodyImage;
    }


}