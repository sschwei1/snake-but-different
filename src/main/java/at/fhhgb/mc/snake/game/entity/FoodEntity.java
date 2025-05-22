package at.fhhgb.mc.snake.game.entity;

import at.fhhgb.mc.snake.game.event.GameEvent;
import at.fhhgb.mc.snake.game.event.GrowthEvent;
import at.fhhgb.mc.snake.game.event.PointsEvent;
import at.fhhgb.mc.snake.game.renderer.GameCell;
import at.fhhgb.mc.snake.game.struct.Point2D;
import at.fhhgb.mc.snake.log.GLog;

import java.util.List;

public class FoodEntity extends AbstractEntity {
    private int sizeIncrease;
    private int points;

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

    public int getSizeIncrease() {
        return this.sizeIncrease;
    }

    public FoodEntity setSizeIncrease(int sizeIncrease) {
        this.sizeIncrease = sizeIncrease;
        return this;
    }

    public int getPoints() {
        return this.points;
    }

    public FoodEntity setPoints(int points) {
        this.points = points;
        return this;
    }

    @Override
    public GameCell.State getType() {
        return GameCell.State.FOOD;
    }

    @Override
    public List<GameEvent> onConsume() {
        return List.of(
            new GrowthEvent(this, this.sizeIncrease),
            new PointsEvent(this, this.points)
        );
    }
}
