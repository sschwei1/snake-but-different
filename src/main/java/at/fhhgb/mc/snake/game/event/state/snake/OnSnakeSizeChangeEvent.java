package at.fhhgb.mc.snake.game.event.state.snake;

import at.fhhgb.mc.snake.game.event.state.ChangeEvent;

public class OnSnakeSizeChangeEvent extends ChangeEvent {
    public OnSnakeSizeChangeEvent(int total, int change) {
        super(total, change);
    }

    @Override
    public StateEventType getType() {
        return StateEventType.SNAKE_SIZE_CHANGE;
    }
}
