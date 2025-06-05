package at.fhhgb.mc.snake.game.entity.manager;

import at.fhhgb.mc.snake.game.Snake;
import at.fhhgb.mc.snake.game.entity.AbstractEntity;
import at.fhhgb.mc.snake.game.event.entity.EntityEvent;
import at.fhhgb.mc.snake.game.options.GameOptions;
import at.fhhgb.mc.snake.game.renderer.GameCell;
import at.fhhgb.mc.snake.game.struct.Point2D;
import at.fhhgb.mc.snake.log.GLog;

import java.util.*;
import java.util.stream.IntStream;

public class EntityManager {
    private static class DummyEntity extends AbstractEntity {
        private final int hash;

        public DummyEntity(Point2D position, int hash) {
            super(position, null);
            this.hash = hash;
        }

        @Override
        public int getRenderingPriority() {
            return this.hash;
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
    private final HashSet<Point2D> emptyPositions;

    private final HashSet<Point2D> positionsToClear;
    private final HashSet<AbstractEntity> dirtyEntities;


    public EntityManager(GameOptions options) {
        Comparator<AbstractEntity> comparator = Comparator
            .comparing(AbstractEntity::getPosition)
            .thenComparing(AbstractEntity::getRenderingPriority)
            .thenComparing(AbstractEntity::hashCode);

        this.entities = new TreeSet<>(comparator);
        this.emptyPositions = IntStream.range(0, options.getFieldSize())
                                  .mapToObj(number -> Point2D.fromInt(
                                      number,
                                      options.getGameWidth(),
                                      options.getGameHeight()
                                  ))
                                    .collect(HashSet::new, HashSet::add, HashSet::addAll);

        this.positionsToClear = new HashSet<>();
        this.dirtyEntities = new HashSet<>();
    }

    public boolean register(AbstractEntity entity) {
        GLog.info("Register entity: " + entity);
        this.dirtyEntities.add(entity);
        this.emptyPositions.remove(entity.getPosition());
        return this.entities.add(entity);
    }

    public boolean unregister(AbstractEntity entity) {
        boolean wasRemoved = this.entities.remove(entity);

        this.positionsToClear.add(entity.getPosition().clone());
        Collection<AbstractEntity> subSet = this.getAllWithPosition(entity.getPosition());
        if(subSet.isEmpty()) {
            this.emptyPositions.add(entity.getPosition().clone());
        }

        return wasRemoved;
    }

    public boolean has(AbstractEntity entity) {
        return this.entities.contains(entity);
    }

    public void move(AbstractEntity entity, Point2D newPosition) {
        if(!this.has(entity)) {
            entity.setPosition(newPosition);
            return;
        }

        this.unregister(entity);
        entity.setPosition(newPosition);
        this.register(entity);
    }

    public void move(AbstractEntity entity, Snake.Direction direction) {
        if(!this.has(entity)) {
            entity.getPosition().move(direction);
            return;
        }

        this.unregister(entity);
        entity.getPosition().move(direction);
        this.register(entity);
    }

    public Collection<AbstractEntity> getAll() {
        return this.entities;
    }

    public List<AbstractEntity> getAllWithPosition(Point2D position) {
        DummyEntity from = new DummyEntity(position, Integer.MIN_VALUE);
        DummyEntity to = new DummyEntity(position, Integer.MAX_VALUE);

        return new ArrayList<>(this.entities.subSet(from, true, to, true));
    }

    public HashSet<Point2D> getEmptyPositions() {
        return this.emptyPositions;
    }

    public HashSet<Point2D> getPositionsToClear() {
        return this.positionsToClear;
    }

    public HashSet<AbstractEntity> getDirtyEntities() {
        return this.dirtyEntities;
    }

    public void resetDirtyCollections() {
        this.dirtyEntities.clear();
        this.positionsToClear.clear();
    }

    public void clear() {
        this.entities.clear();
        this.emptyPositions.clear();
        this.positionsToClear.clear();
        this.dirtyEntities.clear();
    }
}
