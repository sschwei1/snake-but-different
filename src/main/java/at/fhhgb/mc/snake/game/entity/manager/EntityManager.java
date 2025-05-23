package at.fhhgb.mc.snake.game.entity.manager;

import at.fhhgb.mc.snake.game.Snake;
import at.fhhgb.mc.snake.game.entity.AbstractEntity;
import at.fhhgb.mc.snake.game.event.entity.EntityEvent;
import at.fhhgb.mc.snake.game.renderer.GameCell;
import at.fhhgb.mc.snake.game.struct.Point2D;

import java.util.*;

public class EntityManager {
    private static class DummyEntity extends AbstractEntity {
        private final int hash;

        public DummyEntity(Point2D position, int hash) {
            super(position);
            this.hash = hash;
        }

        @Override
        public GameCell.State getType() {
            return null;
        }

        @Override
        public List<EntityEvent> onConsume() {
            return List.of();
        }

        @Override
        public int hashCode() {
            return this.hash;
        }
    }

    private final TreeSet<AbstractEntity> entities;

    public EntityManager() {
        Comparator<AbstractEntity> comparator = Comparator
            .comparing(AbstractEntity::getPosition)
            .thenComparing(AbstractEntity::hashCode);

        this.entities = new TreeSet<>(comparator);
    }

    public boolean registerEntity(AbstractEntity entity) {
        return this.entities.add(entity);
    }

    public boolean unregisterEntity(AbstractEntity entity) {
        return this.entities.remove(entity);
    }

    public boolean has(AbstractEntity entity) {
        return this.entities.contains(entity);
    }

    public void moveEntity(AbstractEntity entity, Point2D newPosition) {
        if(!this.has(entity)) {
            entity.setPosition(newPosition);
            return;
        }

        this.unregisterEntity(entity);
        entity.setPosition(newPosition);
        this.registerEntity(entity);
    }

    public void moveEntity(AbstractEntity entity, Snake.Direction direction) {
        if(!this.has(entity)) {
            entity.getPosition().move(direction);
            return;
        }

        this.unregisterEntity(entity);
        entity.getPosition().move(direction);
        this.registerEntity(entity);
    }

    public Collection<AbstractEntity> getEntities() {
        return this.entities;
    }

    public List<AbstractEntity> getEntitiesWithPosition(Point2D position) {
        DummyEntity from = new DummyEntity(position, Integer.MIN_VALUE);
        DummyEntity to = new DummyEntity(position, Integer.MAX_VALUE);

        return new ArrayList<>(this.entities.subSet(from, true, to, true));
    }

    public void clear() {
        this.entities.clear();
    }
}
