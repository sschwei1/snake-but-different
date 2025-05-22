package at.fhhgb.mc.snake.game.event.update;

public class GameStartEvent extends UpdateEvent {
    @Override
    public UpdateEventType getType() {
        return UpdateEventType.START;
    }
}
