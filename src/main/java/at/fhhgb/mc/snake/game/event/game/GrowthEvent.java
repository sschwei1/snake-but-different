package at.fhhgb.mc.snake.game.event.game;

import at.fhhgb.mc.snake.game.entity.AbstractEntity;

public class GrowthEvent extends GameEvent {
    private final int growthSize;

    public GrowthEvent(AbstractEntity source, int growthSize) {
        super(source);
        this.growthSize = growthSize;
    }

    public int getGrowthSize() {
        return this.growthSize;
    }

    @Override
    public GameEventType getEventType() {
        return GameEventType.GROWTH;
    }
}
