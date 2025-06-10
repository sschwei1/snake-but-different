package at.fhhgb.mc.snake.game.entity;

import at.fhhgb.mc.snake.game.event.entity.*;
import at.fhhgb.mc.snake.game.options.FoodConfig;
import at.fhhgb.mc.snake.game.renderer.GameCell;
import at.fhhgb.mc.snake.game.struct.Point2D;
import at.fhhgb.mc.snake.log.GLog;
import javafx.scene.paint.Color;

import java.util.List;

public class FoodEntity extends AbstractEntity {
    private final int sizeIncrease;
    private final int pointsIncrease;
    private final int spawnNewAmount;
    private final double speedIncreaseFactor;

    public FoodEntity(Point2D position) {
        this(position, 1, 100, 1, 1, Color.RED);
    }

    public FoodEntity(Point2D position, FoodConfig.FoodValueConfig valueConfig) {
        this(
            position,
            valueConfig.lengthIncrease(),
            valueConfig.pointIncrease(),
            valueConfig.spawnNewAmount(),
            1,
            valueConfig.color()
        );
    }

    public FoodEntity(Point2D position, FoodConfig.FoodValueConfig foodValueConfig, double speedIncreaseFactor) {
        this(
            position,
            foodValueConfig.lengthIncrease(),
            foodValueConfig.pointIncrease(),
            foodValueConfig.spawnNewAmount(),
            speedIncreaseFactor,
            foodValueConfig.color()
        );
    }

    public FoodEntity(
        Point2D position,
        int sizeIncrease,
        int pointsIncrease,
        int spawnNewAmount,
        double speedIncreaseFactor,
        Color color
    ) {
        super(position, color);
        this.sizeIncrease = sizeIncrease;
        this.pointsIncrease = pointsIncrease;
        this.spawnNewAmount = spawnNewAmount;
        this.speedIncreaseFactor = speedIncreaseFactor;

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
            new EntitySpawnFoodEvent(this, this.spawnNewAmount),
            new EntitySpeedChangeEvent(this, this.speedIncreaseFactor)
        );
    }
}
