package at.fhhgb.mc.snake.game.event.state;

public abstract class StateEvent {
    public enum StateEventType {
        GAME_START,
        GAME_OVER,
        GAME_PAUSE,
        GAME_RESUME,

        SNAKE_POINTS_CHANGE,
        SNAKE_SIZE_CHANGE
    }

    public abstract StateEventType getType();
}
