package at.fhhgb.mc.snake.game.event.entity;

import at.fhhgb.mc.snake.game.entity.AbstractEntity;

public class EntitySpawnFoodEvent extends EntityEvent {
    private final int amount;

    public EntitySpawnFoodEvent(AbstractEntity source) {
        this(source, 1);
    }

    public EntitySpawnFoodEvent(AbstractEntity source, int amount) {
        super(source);
        this.amount = amount;
    }

    public int getAmount() {
        return this.amount;
    }

    @Override
    public EntityEventType getEventType() {
        return EntityEventType.SPAWN_FOOD;
    }
}
