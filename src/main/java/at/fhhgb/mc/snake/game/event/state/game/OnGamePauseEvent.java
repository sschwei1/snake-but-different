package at.fhhgb.mc.snake.game.event.state.game;

import at.fhhgb.mc.snake.game.event.state.StateEvent;

public class OnGamePauseEvent extends StateEvent {
    @Override
    public StateEventType getType() {
        return StateEventType.GAME_PAUSE;
    }
}
