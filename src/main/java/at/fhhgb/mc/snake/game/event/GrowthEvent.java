package at.fhhgb.mc.snake.game.event;

public class GrowthEvent extends GameEvent {
    private final int amount;

    public GrowthEvent(int amount) {
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
