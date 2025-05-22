package at.fhhgb.mc.snake.game.entity;

import at.fhhgb.mc.snake.game.event.game.DeathEvent;
import at.fhhgb.mc.snake.game.event.game.GameEvent;
import at.fhhgb.mc.snake.game.renderer.GameCell;
import at.fhhgb.mc.snake.game.struct.Point2D;

import java.util.List;

public class SnakePartEntity extends AbstractEntity implements Cloneable {
    private boolean isHead;

    public SnakePartEntity(Point2D position) {
        super(position);
    }

    public SnakePartEntity(int x, int y) {
        super(x, y);
    }

    public boolean isHead() {
        return isHead;
    }

    public SnakePartEntity setIsHead(boolean isHead) {
        this.isHead = isHead;
        return this;
    }

    @Override
    public GameCell.State getType() {
        return this.isHead ?
            GameCell.State.SNAKE_HEAD :
            GameCell.State.SNAKE;
    }

    @Override
    public List<GameEvent> onConsume() {
        if (this.isHead) {
            return List.of();
        }

        return List.of(
            new DeathEvent(this)
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
