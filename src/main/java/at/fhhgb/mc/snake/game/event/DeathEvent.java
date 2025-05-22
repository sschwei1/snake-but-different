package at.fhhgb.mc.snake.game.event;

import at.fhhgb.mc.snake.game.entity.AbstractEntity;

public class DeathEvent extends GameEvent {
    public DeathEvent(AbstractEntity source) {
        super(source);
    }

    @Override
    public EventType getEventType() {
        return EventType.DEATH;
    }
}
