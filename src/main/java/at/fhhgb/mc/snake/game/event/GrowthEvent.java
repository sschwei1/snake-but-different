package at.fhhgb.mc.snake.game.event;

import at.fhhgb.mc.snake.game.entity.AbstractEntity;

public class GrowthEvent extends GameEvent {
    private final int amount;

    public GrowthEvent(AbstractEntity source, int amount) {
        super(source);
        this.amount = amount;
    }

    public int getAmount() {
        return this.amount;
    }

    @Override
    public EventType getEventType() {
        return EventType.GROWTH;
    }
}
