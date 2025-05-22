package at.fhhgb.mc.snake.game.event.update;

public class ResumeEvent extends UpdateEvent {
    @Override
    public UpdateEventType getType() {
        return UpdateEventType.RESUME;
    }
}
