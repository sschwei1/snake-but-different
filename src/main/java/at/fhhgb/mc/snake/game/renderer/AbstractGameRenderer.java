package at.fhhgb.mc.snake.game.renderer;

import at.fhhgb.mc.snake.game.entity.AbstractEntity;
import at.fhhgb.mc.snake.game.entity.manager.EntityManager;
import at.fhhgb.mc.snake.game.options.GameOptions;
import at.fhhgb.mc.snake.game.struct.Point2D;

import java.util.List;

public abstract class AbstractGameRenderer {
    protected final GameOptions options;
    protected final EntityManager entityManager;

    public AbstractGameRenderer(GameOptions options, EntityManager entityManager) {
        this.options = options;
        this.entityManager = entityManager;
    }

    public GameCell getCellAt(Point2D point) {
        return getCellAt(point.getX(), point.getY());
    }

    public abstract GameCell[][] getGameGrid();
    public abstract GameCell getCellAt(int x, int y);
    public abstract void update();
}
