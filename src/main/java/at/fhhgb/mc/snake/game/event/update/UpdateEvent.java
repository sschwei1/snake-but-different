package at.fhhgb.mc.snake.game.event.update;

public abstract class UpdateEvent {
    public enum UpdateEventType {
        POINTS_CHANGE, SNAKE_GROWTH, GAME_OVER
    }

    public abstract UpdateEventType getType();
}
