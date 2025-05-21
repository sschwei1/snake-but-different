package at.fhhgb.mc.snake.game.event;

public abstract class GameEvent {
    public enum EventType {
        GROWTH, DEATH, POINTS
    }

    public abstract EventType getEventType();
}
