package at.fhhgb.mc.snake.game.event.state.snake;

import at.fhhgb.mc.snake.game.event.state.ChangeEvent;

public class OnSnakePointsChangeEvent extends ChangeEvent {
    public OnSnakePointsChangeEvent(int total, int change) {
        super(total, change);
    }

    @Override
    public StateEventType getType() {
        return StateEventType.SNAKE_POINTS_CHANGE;
    }
}
