package at.fhhgb.mc.snake.game.renderer;

import at.fhhgb.mc.snake.game.options.GameOptions;

public abstract class GameRenderer {
    protected GameOptions options;

    public GameRenderer(GameOptions options) {
        this.options = options;
    }

    public abstract GameCell[][] getGameGrid();
    public abstract GameCell getCellAt(int x, int y);
    public abstract void update();
}
