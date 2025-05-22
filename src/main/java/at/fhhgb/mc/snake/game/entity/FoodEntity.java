package at.fhhgb.mc.snake.game.entity;

import at.fhhgb.mc.snake.game.event.entity.EntityEvent;
import at.fhhgb.mc.snake.game.event.entity.EntityGrowthEvent;
import at.fhhgb.mc.snake.game.event.entity.EntityPointsEvent;
import at.fhhgb.mc.snake.game.renderer.GameCell;
import at.fhhgb.mc.snake.game.struct.Point2D;
import at.fhhgb.mc.snake.log.GLog;

import java.util.List;

public class FoodEntity extends AbstractEntity {
    private final int sizeIncrease;
    private final int points;

    public FoodEntity(Point2D position) {
        this(position, 1, 100);
    }

    public FoodEntity(int x, int y) {
        this(x, y, 1, 100);
    }

    public FoodEntity(Point2D position, int sizeIncrease, int points) {
        super(position);
        this.sizeIncrease = sizeIncrease;
        this.points = points;

        GLog.info("Init Food");
    }

    public FoodEntity(int x, int y, int sizeIncrease, int points) {
        super(x, y);
        this.sizeIncrease = sizeIncrease;
        this.points = points;

        GLog.info("Init Food");
    }

    @Override
    public GameCell.State getType() {
        return GameCell.State.FOOD;
    }

    @Override
    public List<EntityEvent> onConsume() {
        return List.of(
            new EntityGrowthEvent(this, this.sizeIncrease),
            new EntityPointsEvent(this, this.points)
        );
    }
}
