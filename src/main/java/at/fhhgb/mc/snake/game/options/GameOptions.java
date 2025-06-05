package at.fhhgb.mc.snake.game.options;

import at.fhhgb.mc.snake.game.struct.Point2D;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class GameOptions {
    private int tickSpeed;
    private int initialSnakeLength;

    private GameFieldConfig gameFieldConfig;
    private FoodConfig foodConfig;

    private GameOptions() { }

    public int getTickSpeed() {
        return tickSpeed;
    }

    public GameOptions setTickSpeed(int moveSpeed) {
        this.tickSpeed = moveSpeed;
        return this;
    }

    public int getGameWidth() {
        int offset = this.gameFieldConfig.wallEnabled() ? 2 : 0;
        return offset + this.gameFieldConfig.width();
    }

    public int getGameWidthWithoutOffset() {
        return this.gameFieldConfig.width();
    }

    public int getGameHeight() {
        int offset = this.gameFieldConfig.wallEnabled() ? 2 : 0;
        return offset + this.gameFieldConfig.height();
    }

    public int getGameHeightWithoutOffset() {
        return this.gameFieldConfig.height();
    }

    public GameOptions setGameSizeConfig(GameFieldConfig gameFieldConfig) {
        this.gameFieldConfig = gameFieldConfig;
        return this;
    }

    public GameOptions setFoodConfig(FoodConfig foodConfig) {
        this.foodConfig = foodConfig;
        return this;
    }

    public int getFieldSize() {
        return this.getGameWidth() * this.getGameHeight();
    }

    public int getFieldSizeWithoutOffset() {
        return this.getGameWidthWithoutOffset() * this.getGameHeightWithoutOffset();
    }

    public int getInitialSnakeLength() {
        return initialSnakeLength;
    }

    public GameOptions setInitialSnakeLength(int initialSnakeLength) {
        this.initialSnakeLength = initialSnakeLength;
        return this;
    }

    public Point2D getStartingPosition() {
        return this.gameFieldConfig.startingPos();
    }

    public boolean isWallEnabled() {
        return this.gameFieldConfig.wallEnabled();
    }

    public List<FoodConfig.FoodValueConfig> getAvailableFoods() {
        return this.foodConfig.availableFood();
    }

    public static GameOptions getEasyMode() {
        GameOptions easyOptions = new GameOptions();
        easyOptions.tickSpeed = 100;
        easyOptions.initialSnakeLength = 5;
        easyOptions.gameFieldConfig = new GameFieldConfig(20, 20, new Point2D(1,1), false);
        easyOptions.foodConfig = new FoodConfig(1, new ArrayList<>() {{
            add(
                new FoodConfig.FoodValueConfig(
                    20,
                    1,
                    1,
                    Color.RED
                )
            );
        }});

        return easyOptions;
    }

    public static GameOptions getMediumMode() {
        GameOptions mediumOptions = new GameOptions();
        mediumOptions.tickSpeed = 100;
        mediumOptions.initialSnakeLength = 5;
        mediumOptions.gameFieldConfig = new GameFieldConfig(20, 20, new Point2D(1,1), false);
        mediumOptions.foodConfig = new FoodConfig(1, new ArrayList<>() {{
            add(new FoodConfig.FoodValueConfig(100, 3, 1, Color.RED));
        }});

        return mediumOptions;
    }

    public static GameOptions getHardMode() {
        GameOptions hardOptions = new GameOptions();
        hardOptions.tickSpeed = 50;
        hardOptions.initialSnakeLength = 5;
        hardOptions.gameFieldConfig = new GameFieldConfig(20, 20, new Point2D(1,1), false);
        hardOptions.foodConfig = new FoodConfig(1, new ArrayList<>() {{
            add(new FoodConfig.FoodValueConfig(200, 5, 1, Color.RED));
        }});

        return hardOptions;
    }

    public static GameOptions getDefaultCustomOptions() {
        GameOptions defaultOptions = new GameOptions();
        defaultOptions.tickSpeed = 50;
        defaultOptions.initialSnakeLength = 5;
        defaultOptions.gameFieldConfig = new GameFieldConfig(20, 20, new Point2D(1,1), false);
        defaultOptions.foodConfig = new FoodConfig(1, new ArrayList<>() {{
            add(new FoodConfig.FoodValueConfig(100, 5, 1, Color.YELLOW));
            add(new FoodConfig.FoodValueConfig(200, 8, 1, Color.ORANGE));
            add(new FoodConfig.FoodValueConfig(300, 10, 1, Color.RED));
        }});

        return defaultOptions;
    }

    private static GameOptions readGameOptionsFromFile() {
        // TODO: Implement file reading logic
        return getDefaultCustomOptions();
    }

    private static void writeGameOptionsToFile(GameOptions options) {
        // TODO: Implement file writing logic
    }
}
