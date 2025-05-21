package at.fhhgb.mc.snake.game.entity;

import at.fhhgb.mc.snake.game.renderer.GameCell;
import at.fhhgb.mc.snake.game.struct.Point2D;

public abstract class AbstractEntity {
    protected Point2D position;

    public AbstractEntity(Point2D position) {
        this.position = position;
    }

    public AbstractEntity(int x, int y) {
        this.position = new Point2D(x, y);
    }

    public Point2D getPosition() {
        return position;
    }

    public AbstractEntity setPosition(Point2D position) {
        this.position = position;
        return this;
    }

    public abstract GameCell.State getType();
    public abstract void onConsume();
}
