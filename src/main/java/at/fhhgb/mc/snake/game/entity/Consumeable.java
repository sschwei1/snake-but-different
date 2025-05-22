package at.fhhgb.mc.snake.game.entity;

import at.fhhgb.mc.snake.game.event.game.GameEvent;

import java.util.List;

public interface Consumeable {
    List<GameEvent> onConsume();
}
