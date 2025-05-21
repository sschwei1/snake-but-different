package at.fhhgb.mc.snake.game;

import at.fhhgb.mc.snake.game.entity.SnakePartEntity;
import at.fhhgb.mc.snake.game.options.GameOptions;
import at.fhhgb.mc.snake.game.struct.Point2D;

import java.util.Collection;
import java.util.LinkedList;

public class Snake {
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private LinkedList<SnakePartEntity> parts;
    private final GameOptions options;

    public Snake() {
        this.options = GameOptions.getInstance();
        this.initSnake(options.getInitialSnakeLength());
    }

    private void initSnake(int size) {
        this.parts = new LinkedList<>();
        this.increaseSizeBy(size);
    }

    public void increaseSize() {
        if(this.parts.size() == 1) {

        }

        if(!this.parts.isEmpty()) {
            SnakePartEntity newPart = this.parts.getLast().clone();
            this.parts.add(newPart);
            return;
        }

        Point2D startingPos = this.options.getStartingPosition().clone();
        startingPos.setMaxX(options.getGameWidth());
        startingPos.setMaxY(options.getGameHeight());

        SnakePartEntity head = new SnakePartEntity(startingPos);
        head.setIsHead(true);

        this.parts.addFirst(head);
    }

    public void increaseSizeBy(int amount) {
        for(int i = 0; i < amount; i++) {
            this.increaseSize();
        }
    }

    public void move(Direction direction) {
        if(this.parts.isEmpty()) {
            throw new IllegalStateException("Unable to move empty snake.");
        }

        for(int i = this.parts.size() - 1; i > 0; i--) {
            SnakePartEntity prevPart = this.parts.get(i - 1);
            this.parts.get(i).setPosition(prevPart.getPosition().clone());
        }

        this.parts.getFirst().getPosition().move(direction);
    }

    public Collection<SnakePartEntity> getParts() {
        return this.parts;
    }
}
