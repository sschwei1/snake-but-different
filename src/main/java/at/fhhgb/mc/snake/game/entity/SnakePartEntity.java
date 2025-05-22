package at.fhhgb.mc.snake.game.entity;

import at.fhhgb.mc.snake.game.event.entity.EntityDeathEvent;
import at.fhhgb.mc.snake.game.event.entity.EntityEvent;
import at.fhhgb.mc.snake.game.renderer.GameCell;
import at.fhhgb.mc.snake.game.struct.Point2D;

import java.util.List;

public class SnakePartEntity extends AbstractEntity implements Cloneable {
    private boolean isHead;

    public SnakePartEntity(Point2D position) {
        this(position, false);
    }

    public SnakePartEntity(int x, int y) {
        this(x, y, false);
    }

    public SnakePartEntity(int x, int y, boolean isHead) {
        super(x, y);
        this.isHead = isHead;
    }

    public SnakePartEntity(Point2D position, boolean isHead) {
        super(position);
        this.isHead = isHead;
    }

    @Override
    public GameCell.State getType() {
        return this.isHead ?
            GameCell.State.SNAKE_HEAD :
            GameCell.State.SNAKE;
    }

    @Override
    public List<EntityEvent> onConsume() {
        if (this.isHead) {
            return List.of();
        }

        return List.of(
            new EntityDeathEvent(this)
        );
    }

    @Override
    public SnakePartEntity clone() {
        try {
            SnakePartEntity clone = (SnakePartEntity) super.clone();
            clone.position = this.position.clone();
            clone.isHead = false;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
