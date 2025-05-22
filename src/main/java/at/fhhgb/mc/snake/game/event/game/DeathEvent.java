package at.fhhgb.mc.snake.game.event.game;

import at.fhhgb.mc.snake.game.entity.AbstractEntity;

public class DeathEvent extends GameEvent {
    public DeathEvent(AbstractEntity source) {
        super(source);
    }

    @Override
    public GameEventType getEventType() {
        return GameEventType.DEATH;
    }
}
