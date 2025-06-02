package at.fhhgb.mc.snake.game.event.state.game;

import at.fhhgb.mc.snake.game.Snake;
import at.fhhgb.mc.snake.game.event.state.StateEvent;

public class OnGameOverEvent extends StateEvent {
    private final int totalPoints;
    private final Snake snake;
    private final boolean isWin;

    public OnGameOverEvent(int totalPoints, Snake snake) {
        this(totalPoints, snake, false);
    }

    public OnGameOverEvent(int totalPoints, Snake snake, boolean isWin) {
        this.totalPoints = totalPoints;
        this.snake = snake;
        this.isWin = isWin;
    }

    public int getTotalPoints() {
        return this.totalPoints;
    }

    public Snake getSnake() {
        return this.snake;
    }

    public boolean isWin() {
        return this.isWin;
    }

    @Override
    public StateEventType getType() {
        return StateEventType.GAME_OVER;
    }
}
