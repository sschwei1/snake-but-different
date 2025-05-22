package at.fhhgb.mc.snake.game.event.update;

public class PauseEvent extends UpdateEvent {
    @Override
    public UpdateEventType getType() {
        return UpdateEventType.PAUSE;
    }
}
