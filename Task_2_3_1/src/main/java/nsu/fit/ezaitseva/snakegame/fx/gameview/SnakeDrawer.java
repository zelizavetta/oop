package nsu.fit.ezaitseva.snakegame.fx.gameview;

import javafx.scene.SnapshotParameters;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import java.util.HashMap;
import java.util.Map;
import nsu.fit.ezaitseva.snakegame.model.units.snake.Direction;

public class SnakeDrawer {
    private final SnapshotParameters parameters;
    private final int playersAmount;
    private final Map<Integer, Map<Direction, HeadBody>> imageViewMap = new HashMap<>();


    public SnakeDrawer(int playersAmount) {
        this.playersAmount = playersAmount;
        parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        initSkinMap();
    }

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


    public Image getHeadImage(Direction headDirection, int id) {
        HeadBody headBody = imageViewMap.get(id).get(headDirection);
        return headBody.headImage;
    }

    public Image getHeadImage(int id) {
        HeadBody headBody = imageViewMap.get(id).get(Direction.UP);
        return headBody.headImage;
    }

    public Image getBodyImage(Direction bodyDirection, int id) {
        HeadBody headBody = imageViewMap.get(id).get(bodyDirection);
        return headBody.bodyImage;
    }


}