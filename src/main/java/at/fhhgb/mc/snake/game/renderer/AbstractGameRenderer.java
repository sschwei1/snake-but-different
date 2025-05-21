package at.fhhgb.mc.snake.game.renderer;

import at.fhhgb.mc.snake.game.entity.AbstractEntity;
import at.fhhgb.mc.snake.game.options.GameOptions;
import at.fhhgb.mc.snake.game.struct.Point2D;

import java.util.Collection;

public abstract class AbstractGameRenderer {
    protected GameOptions options;

    public AbstractGameRenderer(GameOptions options) {
        this.options = options;
    }

    public GameCell getCellAt(Point2D point) {
        return getCellAt(point.getX(), point.getY());
    }

    public abstract GameCell[][] getGameGrid();
    public abstract GameCell getCellAt(int x, int y);
    public abstract void update(Collection<AbstractEntity> entites);
}
