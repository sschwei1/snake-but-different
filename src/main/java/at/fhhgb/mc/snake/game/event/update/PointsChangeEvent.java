package at.fhhgb.mc.snake.game.event.update;

public class PointsChangeEvent extends UpdateEvent {
    private final int totalPoints;
    private final int changedPoints;

    public PointsChangeEvent(int totalPoints, int changedPoints) {
        this.totalPoints = totalPoints;
        this.changedPoints = changedPoints;
    }

    public int getTotalPoints() {
        return this.totalPoints;
    }

    public int getChangedPoints() {
        return this.changedPoints;
    }

    @Override
    public UpdateEventType getType() {
        return UpdateEventType.POINTS_CHANGE;
    }
}
