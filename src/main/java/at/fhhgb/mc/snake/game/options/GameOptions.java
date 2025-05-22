package at.fhhgb.mc.snake.game.options;

import at.fhhgb.mc.snake.game.struct.Point2D;

public class GameOptions {
    private static GameOptions instance;

    private int tickSpeed;
    private int gameWidth;
    private int gameHeight;
    private int initialSnakeLength;
    private Point2D startingPosition;

    private GameOptions() { }

    public int getTickSpeed() {
        return tickSpeed;
    }

    public GameOptions setTickSpeed(int moveSpeed) {
        this.tickSpeed = moveSpeed;
        return this;
    }

    public int getGameWidth() {
        return gameWidth;
    }

    public GameOptions setGameWidth(int gameWidth) {
        this.gameWidth = gameWidth;
        return this;
    }

    public int getGameHeight() {
        return gameHeight;
    }

    public GameOptions setGameHeight(int gameHeight) {
        this.gameHeight = gameHeight;
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
        return startingPosition;
    }

    public GameOptions setStartingPosition(Point2D startingPosition) {
        this.startingPosition = startingPosition;
        return this;
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
        defaultOptions.gameWidth = 50;
        defaultOptions.gameHeight = 50;
        defaultOptions.initialSnakeLength = 5;
        defaultOptions.startingPosition = new Point2D(1,1);

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
