package at.fhhgb.mc.snake.game;

import at.fhhgb.mc.snake.game.entity.SnakePartEntity;
import at.fhhgb.mc.snake.game.options.GameOptions;
import at.fhhgb.mc.snake.game.struct.Point2D;
import at.fhhgb.mc.snake.log.GLog;

import java.util.LinkedList;
import java.util.List;

public class Snake {
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private LinkedList<SnakePartEntity> parts;
    private final GameOptions options;

    public Snake() {
        this.options = GameOptions.getInstance();
        GLog.info("Init Snake: " + options.getInitialSnakeLength());
        this.initSnake(options.getInitialSnakeLength());
    }

    private void initSnake(int size) {
        this.parts = new LinkedList<>();
        this.increaseSizeBy(size);
    }

    public void increaseSize() {
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

        SnakePartEntity head = this.parts.getFirst();

        if(this.parts.size() > 1) {
            SnakePartEntity partToMove = this.parts.getLast();
            partToMove.setPosition(head.getPosition().clone());
            this.parts.removeLast();
            this.parts.add(1, partToMove);        }

        head.getPosition().move(direction);
    }

    public List<SnakePartEntity> getParts() {
        return this.parts;
    }
}
