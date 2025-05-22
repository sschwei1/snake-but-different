package at.fhhgb.mc.snake.game.event.entity;

import at.fhhgb.mc.snake.game.entity.AbstractEntity;

public class EntityDeathEvent extends EntityEvent {
    public EntityDeathEvent(AbstractEntity source) {
        super(source);
    }

    @Override
    public EntityEventType getEventType() {
        return EntityEventType.DEATH;
    }
}
