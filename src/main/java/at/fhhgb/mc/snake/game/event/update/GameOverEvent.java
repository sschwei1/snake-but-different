package at.fhhgb.mc.snake.game.event.update;

import at.fhhgb.mc.snake.game.Snake;

public class GameOverEvent extends UpdateEvent {
    private final int totalPoints;
    private final Snake snake;

    public GameOverEvent(int totalPoints, Snake snake) {
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
    public UpdateEventType getType() {
        return UpdateEventType.GAME_OVER;
    }
}
