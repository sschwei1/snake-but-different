package at.fhhgb.mc.snake.game.event.game;

import at.fhhgb.mc.snake.game.entity.AbstractEntity;

public class PointsEvent extends GameEvent {
    private final int pointsChange;

    public PointsEvent(AbstractEntity source, int pointsChange) {
        super(source);
        this.pointsChange = pointsChange;
    }

    public int getPointsChange() {
        return this.pointsChange;
    }

    @Override
    public GameEventType getEventType() {
        return GameEventType.POINTS;
    }
}
