package at.fhhgb.mc.snake.game.event.entity;

import at.fhhgb.mc.snake.game.entity.AbstractEntity;

public class EntityGrowthEvent extends EntityEvent {
    private final int growthSize;

    public EntityGrowthEvent(AbstractEntity source, int growthSize) {
        super(source);
        this.growthSize = growthSize;
    }

    public int getGrowthSize() {
        return this.growthSize;
    }

    @Override
    public EntityEventType getEventType() {
        return EntityEventType.GROWTH;
    }
}
