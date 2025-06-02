package at.fhhgb.mc.snake.game.entity;

import at.fhhgb.mc.snake.game.event.entity.EntityDeathEvent;
import at.fhhgb.mc.snake.game.event.entity.EntityEvent;
import at.fhhgb.mc.snake.game.renderer.GameCell;
import at.fhhgb.mc.snake.game.struct.Point2D;

import java.util.List;

public class WallEntity extends AbstractEntity {
    public WallEntity(Point2D position) {
        super(position);
    }

    @Override
    public int getRenderingPriority() {
        return 20;
    }

    @Override
    public GameCell.State getType() {
        return GameCell.State.WALL;
    }

    @Override
    public List<EntityEvent> onConsume() {
        return List.of(
            new EntityDeathEvent(this)
        );
    }
}
