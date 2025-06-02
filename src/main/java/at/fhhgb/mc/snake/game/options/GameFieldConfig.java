package at.fhhgb.mc.snake.game.options;

import at.fhhgb.mc.snake.game.struct.Point2D;

public record GameFieldConfig(int width, int height, Point2D startingPos, boolean wallEnabled) {

}
