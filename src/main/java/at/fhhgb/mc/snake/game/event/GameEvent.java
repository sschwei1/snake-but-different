package at.fhhgb.mc.snake.game.event;

import at.fhhgb.mc.snake.game.entity.AbstractEntity;

public abstract class GameEvent {
    public enum EventType {
        GROWTH, DEATH, POINTS
    }

    private final AbstractEntity source;

    public GameEvent(AbstractEntity source) {
        this.source = source;
    }

    public AbstractEntity getSource() {
        return this.source;
    }

    public abstract EventType getEventType();
}
