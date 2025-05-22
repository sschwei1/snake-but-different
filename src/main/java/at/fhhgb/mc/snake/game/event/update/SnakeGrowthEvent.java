package at.fhhgb.mc.snake.game.event.update;

public class SnakeGrowthEvent extends UpdateEvent {
    private final int totalSize;
    private final int growthSize;

    public SnakeGrowthEvent(int totalSize, int growthSize) {
        this.totalSize = totalSize;
        this.growthSize = growthSize;
    }

    public int getTotalSize() {
        return this.totalSize;
    }

    public int getGrowthSize() {
        return this.growthSize;
    }

    @Override
    public UpdateEventType getType() {
        return UpdateEventType.SNAKE_GROWTH;
    }
}
