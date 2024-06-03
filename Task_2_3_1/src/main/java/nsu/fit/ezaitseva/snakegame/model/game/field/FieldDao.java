package nsu.fit.ezaitseva.snakegame.model.game.field;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import nsu.fit.ezaitseva.snakegame.model.game.field.data.FoodData;
import nsu.fit.ezaitseva.snakegame.model.game.field.data.PointData;
import nsu.fit.ezaitseva.snakegame.model.game.logic.Game;
import nsu.fit.ezaitseva.snakegame.model.units.Food;
import nsu.fit.ezaitseva.snakegame.model.units.SnakeBody;
import nsu.fit.ezaitseva.snakegame.model.units.Wall;


/**
 * field class.
 */
public class FieldDao {
    private File file;


    /**
     * field constructor.
     *
     * @param file file
     */
    public FieldDao(File file) {
        this.file = file;
    }

    /**
     * Sets input stream.
     *
     * @param inputStream the input stream
     */
    public void setInputStream(File inputStream) {
        this.file = inputStream;
    }

    /**
     * Gets field.
     *
     * @return the field
     */
    public Game getField() {
        FieldData fieldData = null;
        try {
            System.out.println(file.getAbsolutePath());
            fieldData = new ObjectMapper().readValue(new FileInputStream(file), FieldData.class);
        } catch (IOException e) {
            System.err.println("There is no field");
            throw new RuntimeException(e);
        }
        FieldConstructor fieldConstructor = new FieldConstructor(
                fieldData.width(), fieldData.height());
        fieldData.foods().forEach((foodData) ->
                fieldConstructor.setUnit(new Food(foodData.point().x(),
                foodData.point().y(), foodData.value())));
        fieldData.walls().forEach((pointData) ->
                fieldConstructor.setUnit(
                        new Wall(pointData.x(), pointData.y())
                ));
        fieldData.snakeHeads().forEach((snakeHead) ->
                fieldConstructor.setUnit(
                        new SnakeBody(snakeHead.x(), snakeHead.y())
                ));
        return fieldConstructor.getGameField();
    }

    /**
     * Save field.
     *
     * @param game the game
     */
    public void saveField(Game game) {
        List<FoodData> foods = new ArrayList<>();
        List<PointData> walls = new ArrayList<>();
        List<PointData> snakeHeads = new ArrayList<>();
        game.getField().getAll().forEach((gameUnit) -> {
            if (gameUnit instanceof Food food) {
                foods.add(new FoodData(new PointData(food.getX(), food.getY()), food.getValue()));
            }
            if (gameUnit instanceof Wall wall) {
                walls.add(new PointData(wall.getX(), wall.getY()));
            }
            if (gameUnit instanceof SnakeBody) {
                snakeHeads.add(new PointData(gameUnit.getX(), gameUnit.getY()));
            }
        });
        FieldData fieldData = new FieldData(foods, walls, snakeHeads, game.width(), game.height());
        try {
            new ObjectMapper().writeValue(file, fieldData);
        } catch (IOException e) {
            System.err.println("LOX");
            throw new RuntimeException(e);
        }
    }
}

/**
 * filed data class.
 *
 * @param foods      foods
 * @param walls      walls
 * @param snakeHeads snake head
 * @param width      width
 * @param height     height
 */
record FieldData(List<FoodData> foods, List<PointData> walls,
                 List<PointData> snakeHeads, int width,
                 int height) {

}
