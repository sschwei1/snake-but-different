package at.fhhgb.mc.snake.game.entity;

import at.fhhgb.mc.snake.game.event.entity.EntityEvent;
import at.fhhgb.mc.snake.game.event.entity.EntityGrowthEvent;
import at.fhhgb.mc.snake.game.event.entity.EntityPointsEvent;
import at.fhhgb.mc.snake.game.event.entity.EntitySpawnFoodEvent;
import at.fhhgb.mc.snake.game.options.FoodConfiguration;
import at.fhhgb.mc.snake.game.renderer.GameCell;
import at.fhhgb.mc.snake.game.struct.Point2D;
import at.fhhgb.mc.snake.log.GLog;

import java.util.List;

public class FoodEntity extends AbstractEntity {
    private final int sizeIncrease;
    private final int pointsIncrease;
    private final int spawnNewAmount;

    public FoodEntity(Point2D position) {
        this(position, 1, 100, 1);
    }

    public FoodEntity(Point2D position, FoodConfiguration.FoodValueConfig foodValueConfig) {
        this(position, foodValueConfig.lengthIncrease(), foodValueConfig.pointIncrease(), foodValueConfig.spawnNewAmount());
    }

    public FoodEntity(Point2D position, int sizeIncrease, int pointsIncrease, int spawnNewAmount) {
        super(position);
        this.sizeIncrease = sizeIncrease;
        this.pointsIncrease = pointsIncrease;
        this.spawnNewAmount = spawnNewAmount;

        GLog.info("Init Food");
    }

    @Override
    public boolean shouldRemoveOnConsume() {
        return true;
    }

    @Override
    public int getRenderingPriority() {
        return 20;
    }

    @Override
    public GameCell.State getType() {
        return GameCell.State.FOOD;
    }

    @Override
    public List<EntityEvent> onConsume() {
        return List.of(
            new EntityGrowthEvent(this, this.sizeIncrease),
            new EntityPointsEvent(this, this.pointsIncrease),
            new EntitySpawnFoodEvent(this, this.spawnNewAmount)
        );
    }
}
