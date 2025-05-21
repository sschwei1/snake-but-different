package at.fhhgb.mc.snake.game.entity;

import at.fhhgb.mc.snake.game.renderer.GameCell;
import at.fhhgb.mc.snake.game.struct.Point2D;

public class FoodEntity extends AbstractEntity {
    public FoodEntity(Point2D position) {
        super(position);
    }

    public FoodEntity(int x, int y) {
        super(x, y);
    }

    @Override
    public GameCell.State getType() {
        return GameCell.State.FOOD;
    }

    @Override
    public void onConsume() {

    }
}
