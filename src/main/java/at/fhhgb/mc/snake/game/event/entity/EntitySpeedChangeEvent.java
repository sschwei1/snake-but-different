package at.fhhgb.mc.snake.game.event.entity;

import at.fhhgb.mc.snake.game.entity.AbstractEntity;

public class EntitySpeedChangeEvent extends EntityEvent {
    private final double speedIncreaseFactor;

    public EntitySpeedChangeEvent(AbstractEntity source, double speedIncreaseFactor) {
        super(source);
        this.speedIncreaseFactor = speedIncreaseFactor;
    }

    public double getSpeedIncreaseFactor() {
        return this.speedIncreaseFactor;
    }

    @Override
    public EntityEventType getEventType() {
        return EntityEventType.SPEED_CHANGE;
    }
}
