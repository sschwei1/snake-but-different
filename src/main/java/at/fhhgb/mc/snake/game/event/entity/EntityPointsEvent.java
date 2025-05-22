package at.fhhgb.mc.snake.game.event.entity;

import at.fhhgb.mc.snake.game.entity.AbstractEntity;

public class EntityPointsEvent extends EntityEvent {
    private final int pointsChange;

    public EntityPointsEvent(AbstractEntity source, int pointsChange) {
        super(source);
        this.pointsChange = pointsChange;
    }

    public int getPointsChange() {
        return this.pointsChange;
    }

    @Override
    public EntityEventType getEventType() {
        return EntityEventType.POINTS;
    }
}
