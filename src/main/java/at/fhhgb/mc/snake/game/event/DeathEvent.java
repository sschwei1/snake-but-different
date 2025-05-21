package at.fhhgb.mc.snake.game.event;

public class DeathEvent extends GameEvent {
    @Override
    public EventType getEventType() {
        return EventType.DEATH;
    }
}
