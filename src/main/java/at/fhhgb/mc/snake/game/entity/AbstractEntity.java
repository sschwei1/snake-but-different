package at.fhhgb.mc.snake.game.entity;

import at.fhhgb.mc.snake.game.event.entity.EntityEvent;
import at.fhhgb.mc.snake.game.renderer.GameCell;
import at.fhhgb.mc.snake.game.struct.Point2D;

import java.util.List;

public abstract class AbstractEntity implements Consumable {
    protected Point2D position;

    public AbstractEntity(Point2D position) {
        this.position = position;
    }

    public Point2D getPosition() {
        return position;
    }

    public AbstractEntity setPosition(Point2D position) {
        this.position = position;
        return this;
    }

    public boolean shouldRemoveOnConsume() {
        return false;
    }

    public abstract int getRenderingPriority();
    public abstract GameCell.State getType();
    public abstract List<EntityEvent> onConsume();

    @Override
    public String toString() {
        String className = this.getClass().getSimpleName();
        return className + " [position=" + position + ", type=" + getType() + ", hash=" + this.hashCode() + "]";
    }
}
