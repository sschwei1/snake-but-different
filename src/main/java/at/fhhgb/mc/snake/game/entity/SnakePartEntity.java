package at.fhhgb.mc.snake.game.entity;

import at.fhhgb.mc.snake.game.event.entity.EntityDeathEvent;
import at.fhhgb.mc.snake.game.event.entity.EntityEvent;
import at.fhhgb.mc.snake.game.renderer.GameCell;
import at.fhhgb.mc.snake.game.struct.Point2D;
import javafx.scene.paint.Color;

import java.util.List;

public class SnakePartEntity extends AbstractEntity implements Cloneable {
    private boolean isHead;

    public SnakePartEntity(Point2D position) {
        this(position, false);
    }

    public SnakePartEntity(Point2D position, boolean isHead) {
        super(position, Color.GREEN);
        this.isHead = isHead;
    }

    @Override
    public Color getColor() {
        return this.isHead ? Color.LIGHTGREEN : Color.GREEN;
    }

    @Override
    public int getRenderingPriority() {
        return this.isHead ? 30 : 10;
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
