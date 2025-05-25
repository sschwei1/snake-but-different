package at.fhhgb.mc.snake.game;

import at.fhhgb.mc.snake.game.entity.SnakePartEntity;
import at.fhhgb.mc.snake.game.entity.manager.EntityManager;
import at.fhhgb.mc.snake.game.options.GameOptions;
import at.fhhgb.mc.snake.game.struct.Point2D;
import at.fhhgb.mc.snake.log.GLog;

import java.util.LinkedList;
import java.util.List;

public class Snake {
    public enum Direction {
        UP, DOWN, LEFT, RIGHT;

        public Direction inverse() {
            return switch (this) {
                case UP -> DOWN;
                case DOWN -> UP;
                case LEFT -> RIGHT;
                case RIGHT -> LEFT;
            };
        }

        public boolean isInverse(Direction direction) {
            return this == direction.inverse();
        }

        public boolean isInverseOrEqual(Direction direction) {
            return this == direction || this.isInverse(direction);
        }
    }

    private LinkedList<SnakePartEntity> parts;
    private final GameOptions options;
    private final EntityManager entityManager;

    public Snake(GameOptions options, EntityManager entityManager) {
        this.options = options;
        this.entityManager = entityManager;

        GLog.info("Init Snake: " + options.getInitialSnakeLength());
        this.initSnake(options.getInitialSnakeLength());
    }

    public int getSize() {
        return this.parts.size();
    }

    private void initSnake(int size) {
        this.parts = new LinkedList<>();
        this.increaseSizeBy(size);
    }

    public void increaseSize() {
        if(!this.parts.isEmpty()) {
            SnakePartEntity newPart = this.parts.getLast().clone();
            this.parts.add(newPart);
            this.entityManager.registerEntity(newPart);
            return;
        }

        Point2D startingPos = this.options.getStartingPosition().clone();
        startingPos.setMaxX(options.getGameWidth());
        startingPos.setMaxY(options.getGameHeight());

        SnakePartEntity head = new SnakePartEntity(startingPos,true);

        this.parts.addFirst(head);
        this.entityManager.registerEntity(head);
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
            this.entityManager.moveEntity(partToMove, head.getPosition().clone());
            this.parts.removeLast();
            this.parts.add(1, partToMove);
        }

        this.entityManager.moveEntity(head, direction);
    }

    public List<SnakePartEntity> getParts() {
        return this.parts;
    }
}
