package at.fhhgb.mc.snake.game.event.state.game;

import at.fhhgb.mc.snake.game.Snake;
import at.fhhgb.mc.snake.game.event.state.StateEvent;

public class OnGameOverEvent extends StateEvent {
    private final int totalPoints;
    private final Snake snake;

    public OnGameOverEvent(int totalPoints, Snake snake) {
        this.totalPoints = totalPoints;
        this.snake = snake;
    }

    public int getTotalPoints() {
        return this.totalPoints;
    }

    public Snake getSnake() {
        return this.snake;
    }

    @Override
    public StateEventType getType() {
        return StateEventType.GAME_OVER;
    }
}
