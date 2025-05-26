package at.fhhgb.mc.snake.game.options;

import at.fhhgb.mc.snake.game.struct.Point2D;

public class GameOptions {
    private static GameOptions instance;

    private int tickSpeed;
    private int initialSnakeLength;

    private GameSizeConfig gameSizeConfig;

    private GameOptions() { }

    public int getTickSpeed() {
        return tickSpeed;
    }

    public GameOptions setTickSpeed(int moveSpeed) {
        this.tickSpeed = moveSpeed;
        return this;
    }

    public int getGameWidth() {
        return this.gameSizeConfig.width();
    }

    public int getGameHeight() {
        return this.gameSizeConfig.height();
    }

    public GameOptions setGameSizeConfig(GameSizeConfig gameSizeConfig) {
        this.gameSizeConfig = gameSizeConfig;
        return this;
    }

    public int getInitialSnakeLength() {
        return initialSnakeLength;
    }

    public GameOptions setInitialSnakeLength(int initialSnakeLength) {
        this.initialSnakeLength = initialSnakeLength;
        return this;
    }

    public Point2D getStartingPosition() {
        return this.gameSizeConfig.startingPos();
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
        defaultOptions.gameSizeConfig = new GameSizeConfig(20, 20, new Point2D(1,1));

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
