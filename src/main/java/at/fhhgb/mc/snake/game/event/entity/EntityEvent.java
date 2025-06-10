package at.fhhgb.mc.snake.game.event.entity;

import at.fhhgb.mc.snake.game.entity.AbstractEntity;

public abstract class EntityEvent {
    public enum EntityEventType {
        GROWTH, DEATH, POINTS, SPAWN_FOOD, SPEED_CHANGE
    }

    private final AbstractEntity source;

    public EntityEvent(AbstractEntity source) {
        this.source = source;
    }

    public AbstractEntity getSource() {
        return this.source;
    }

    public abstract EntityEventType getEventType();
}
