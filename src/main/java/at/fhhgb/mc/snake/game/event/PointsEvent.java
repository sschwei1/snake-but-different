package at.fhhgb.mc.snake.game.event;

public class PointsEvent extends GameEvent {
    private final int amount;

    public PointsEvent(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return this.amount;
    }

    @Override
    public EventType getEventType() {
        return EventType.POINTS;
    }
}
