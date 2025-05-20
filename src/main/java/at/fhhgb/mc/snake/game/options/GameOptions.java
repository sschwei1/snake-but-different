package at.fhhgb.mc.snake.game.options;

public class GameOptions {
    private static GameOptions instance;

    private int moveSpeed;
    private int gameWidth;
    private int gameHeight;

    private GameOptions() { }

    public int getMoveSpeed() {
        return moveSpeed;
    }

    public GameOptions setMoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
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
        defaultOptions.moveSpeed = 100;
        defaultOptions.gameWidth = 25;
        defaultOptions.gameHeight = 25;

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
