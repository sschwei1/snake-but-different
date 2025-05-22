package at.fhhgb.mc.snake.game.entity;

import at.fhhgb.mc.snake.game.event.entity.EntityEvent;

import java.util.List;

public interface Consumable {
    List<EntityEvent> onConsume();
}
