package at.fhhgb.mc.snake.game.options;

import at.fhhgb.mc.snake.game.struct.Point2D;

import java.util.ArrayList;
import java.util.List;

public class GameOptions {
    private static GameOptions instance;

    private int tickSpeed;
    private int initialSnakeLength;

    private GameFieldConfig gameFieldConfig;
    private FoodConfiguration foodConfiguration;

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

    public List<FoodConfiguration.FoodValueConfig> getAvailableFoods() {
        return this.foodConfiguration.availableFood();
    }

    public static GameOptions updateOptions() {
        instance = readGameOptionsFromFile();
        return instance;
    }

    public static GameOptions updateOptions(GameOptions options) {
        writeGameOptionsToFile(options);
        instance = options;
        return instance;
    }

    public static GameOptions resetToDefault() {
        GameOptions defaultOptions = new GameOptions();
        defaultOptions.tickSpeed = 50;
        defaultOptions.initialSnakeLength = 5;
        defaultOptions.gameFieldConfig = new GameFieldConfig(20, 20, new Point2D(1,1), true);
        defaultOptions.foodConfiguration = new FoodConfiguration(new ArrayList<>() {{
            add(new FoodConfiguration.FoodValueConfig(100, 5, 1));
            add(new FoodConfiguration.FoodValueConfig(200, 8, 1));
            add(new FoodConfiguration.FoodValueConfig(300, 10, 1));
        }});

        return updateOptions(defaultOptions);
    }

    public static GameOptions getInstance() {
        if(instance == null) {
            instance = readGameOptionsFromFile();
        }

        return instance;
    }

    public static GameOptions getClone() {
        return readGameOptionsFromFile();
    }

    private static GameOptions readGameOptionsFromFile() {
        // TODO: Implement file reading logic
        return resetToDefault();
    }

    private static void writeGameOptionsToFile(GameOptions options) {
        // TODO: Implement file writing logic
    }
}
