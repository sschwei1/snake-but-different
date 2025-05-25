package at.fhhgb.mc.snake.game;

import at.fhhgb.mc.snake.game.entity.AbstractEntity;
import at.fhhgb.mc.snake.game.entity.FoodEntity;
import at.fhhgb.mc.snake.game.entity.SnakePartEntity;
import at.fhhgb.mc.snake.game.entity.manager.EntityManager;
import at.fhhgb.mc.snake.game.event.entity.EntityEvent;
import at.fhhgb.mc.snake.game.event.entity.EntityGrowthEvent;
import at.fhhgb.mc.snake.game.event.entity.EntityPointsEvent;
import at.fhhgb.mc.snake.game.event.state.StateEvent;
import at.fhhgb.mc.snake.game.event.state.game.OnGameOverEvent;
import at.fhhgb.mc.snake.game.event.state.game.OnGameStartEvent;
import at.fhhgb.mc.snake.game.event.state.game.OnGamePauseEvent;
import at.fhhgb.mc.snake.game.event.state.game.OnGameResumeEvent;
import at.fhhgb.mc.snake.game.event.state.snake.OnSnakePointsChangeEvent;
import at.fhhgb.mc.snake.game.event.state.snake.OnSnakeSizeChangeEvent;
import at.fhhgb.mc.snake.game.options.GameOptions;
import at.fhhgb.mc.snake.game.renderer.DefaultRenderer;
import at.fhhgb.mc.snake.game.renderer.GameCell;
import at.fhhgb.mc.snake.game.renderer.AbstractGameRenderer;
import at.fhhgb.mc.snake.game.struct.Point2D;
import at.fhhgb.mc.snake.log.GLog;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.util.*;
import java.util.function.Consumer;

public class SnakeGame {
    private final GameOptions options;

    private final Pane container;
    private AbstractGameRenderer renderer;
    private Timeline timer;

    private EventHandler<KeyEvent> keyEventHandler;
    private EventTarget eventTarget;

    private Consumer<OnSnakePointsChangeEvent> onPointsUpdate;
    private Consumer<OnGameOverEvent> onGameOver;
    private Consumer<OnSnakeSizeChangeEvent> onSnakeGrowth;
    private Consumer<OnGameStartEvent> onGameStart;
    private Consumer<OnGamePauseEvent> onGamePause;
    private Consumer<OnGameResumeEvent> onGameResume;

    private Random random;
    private boolean isRunning;
    private boolean isPaused;
    private boolean inverseDirection;

    private List<Snake.Direction> queuedDirections;
    private Snake.Direction currentDirection;
    private Snake snake;
    private int score;

    private EntityManager entityManager;

    public SnakeGame(Pane container, GameOptions options) {
        this.options = options;
        this.container = container;
        this.initGame();
    }

    private void initGame() {
        this.initData();
        this.initContainer();
        this.initTimer();
        this.initKeyListener();

        this.spawnFood();
        Objects.requireNonNull(this.renderer).update();
    }

    private void initData() {
        this.random = new Random();
        this.isRunning = false;
        this.isPaused = false;
        this.inverseDirection = false;

        this.entityManager = new EntityManager();
        this.renderer = new DefaultRenderer(this.options, this.entityManager);

        this.queuedDirections = new LinkedList<>();
        this.currentDirection = Snake.Direction.RIGHT;
        this.snake = new Snake(this.options, this.entityManager);
        this.score = 0;

        this.onPointsUpdate = null;
        this.onGameOver = null;
        this.onSnakeGrowth = null;
        this.onGameStart = null;
        this.onGamePause = null;
        this.onGameResume = null;
    }

    private void initContainer() {
        GridPane grid = new GridPane();

        for (int i = 0; i < this.options.getGameWidth(); i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setHgrow(Priority.ALWAYS);
            grid.getColumnConstraints().add(column);
        }

        for (int i = 0; i < this.options.getGameHeight(); i++) {
            RowConstraints row = new RowConstraints();
            row.setVgrow(Priority.ALWAYS);
            grid.getRowConstraints().add(row);
        }

        GameCell[][] gameView = this.renderer.getGameGrid();

        for(int row = 0; row < this.options.getGameHeight(); row++) {
            for(int col = 0; col < this.options.getGameWidth(); col++) {
                grid.add(gameView[col][row], col, row);
            }
        }

        this.container.getChildren().clear();
        this.container.getChildren().add(grid);
    }

    private void initTimer() {
        this.timer = new Timeline(
            new KeyFrame(Duration.millis(this.options.getTickSpeed()), this::gameLoop)
        );

        this.timer.setCycleCount(Timeline.INDEFINITE);
    }

    private void initKeyListener() {
        GLog.info("Init KeyListener");
        this.eventTarget = Optional
            .<EventTarget>ofNullable(this.container.getScene())
            .orElse(this.container);

        if(this.eventTarget == this.container) {
            this.container.setFocusTraversable(true);
        }

        /*
         * this::keyEventHandler returns a new function ref object each time
         * so we need to store it, to correctly clean it up
         */
        this.keyEventHandler = this::handleKeyEvent;
        this.eventTarget.addEventHandler(KeyEvent.KEY_PRESSED, this.keyEventHandler);
    }

    public void start() {
        this.isRunning = true;
        this.emitInitialData();
        this.emitStateEvent(this.onGameStart, new OnGameStartEvent());

        this.timer.play();
    }

    private void emitInitialData() {
        this.emitStateEvent(
            this.onPointsUpdate,
            new OnSnakePointsChangeEvent(this.score, 0)
        );

        this.emitStateEvent(
            this.onSnakeGrowth,
            new OnSnakeSizeChangeEvent(this.snake.getSize(), 0)
        );
    }

    private void end() {
        this.isRunning = false;
        this.timer.stop();

        this.emitStateEvent(
            this.onGameOver,
            new OnGameOverEvent(this.score, this.snake)
        );
    }

    public void cleanup() {
        this.isRunning = false;
        this.timer.stop();
        this.eventTarget.removeEventHandler(KeyEvent.KEY_PRESSED, this.keyEventHandler);
        this.container.getChildren().clear();
        this.snake.getParts().clear();
        this.entityManager.clear();
    }

    private void spawnFood() {
        int fieldSize = this.options.getGameWidth() * this.options.getGameHeight();
        int foodPos = this.random.nextInt(fieldSize - this.snake.getSize());

        List<SnakePartEntity> sorted = this.snake.getParts().stream()
            .sorted(Comparator.comparingInt(p -> p.getPosition().asInt()))
            .toList();

        for(SnakePartEntity part : sorted) {
            if(part.getPosition().asInt() <= foodPos) {
                foodPos++;
            }
        }

        int foodX = foodPos % this.options.getGameWidth();
        int foodY = foodPos / this.options.getGameWidth();

        Point2D foodPosition = new Point2D(foodX, foodY, this.options.getGameWidth(), this.options.getGameHeight());
        this.entityManager.registerEntity(new FoodEntity(foodPosition, 5, 100));
    }

    private void gameLoop(ActionEvent event) {
        this.updateEntities();
        this.updateData();
        renderer.update();
    }

    private void updateEntities() {
        GLog.info("Start updating Entities");
        if(!this.queuedDirections.isEmpty()) {
            this.currentDirection = this.queuedDirections.removeFirst();
        }

        this.snake.move(this.currentDirection);
        GLog.info("End updating Entities");
    }

    private void updateData() {
        GLog.info("Start updating Data");
        Point2D headPos = this.snake.getParts().getFirst().getPosition();
        List<AbstractEntity> consumedEntities = this.entityManager.getEntitiesWithPosition(headPos);

        GLog.info("Snake Head: " + headPos);

        for(AbstractEntity entity : consumedEntities) {
            GLog.info("Consume: " + entity.getType());
            this.consumeEvents(entity.onConsume());

            if(entity instanceof FoodEntity && this.entityManager.has(entity)) {
                this.entityManager.unregisterEntity(entity);
                this.spawnFood();
            }
        }

        GLog.info("End updating Data");
    }

    private void consumeEvents(List<EntityEvent> events) {
        for(EntityEvent event : events) {
            switch(event.getEventType()) {
                case POINTS: handlePointsEvent(event);  break;
                case GROWTH: handleGrowthEvent(event);  break;
                case DEATH:  handleDeathEvent();        break;
            }
        }
    }

    private void handlePointsEvent(EntityEvent event) {
        if(!(event instanceof EntityPointsEvent pointsEvent)) return;

        this.score += pointsEvent.getPointsChange();

        this.emitStateEvent(
            this.onPointsUpdate,
            new OnSnakePointsChangeEvent(this.score, pointsEvent.getPointsChange())
        );
    }

    private void handleGrowthEvent(EntityEvent event) {
        if(!(event instanceof EntityGrowthEvent growthEvent)) return;

        this.snake.increaseSizeBy(growthEvent.getGrowthSize());

        this.emitStateEvent(
            this.onSnakeGrowth,
            new OnSnakeSizeChangeEvent(this.snake.getSize(), growthEvent.getGrowthSize())
        );
    }

    private void handleDeathEvent() {
        GLog.info("Game Over");
        this.end();
    }

    private void handleKeyEvent(KeyEvent event) {
        if(!this.isRunning) return;

        this.handleUpdateDirection(event);
        this.handlePause(event);
        event.consume();
    }

    private void handleUpdateDirection(KeyEvent event) {
        Snake.Direction newDirection = switch (event.getCode()) {
            case W, UP      -> Snake.Direction.UP;
            case S, DOWN    -> Snake.Direction.DOWN;
            case A, LEFT    -> Snake.Direction.LEFT;
            case D, RIGHT   -> Snake.Direction.RIGHT;
            default -> null;
        };

        if(newDirection == null) {
            return;
        }

        if(this.inverseDirection) {
            newDirection = newDirection.inverse();
        }

        this.updateQueuedDirection(newDirection);
    }

    private void updateQueuedDirection(Snake.Direction newDirection) {
        if(this.queuedDirections.size() >= 2) {
            this.queuedDirections.clear();
        }

        Snake.Direction compareDirection = this.queuedDirections.isEmpty()
            ? this.currentDirection
            : this.queuedDirections.getLast();

        if(newDirection.isInverseOrEqual(compareDirection)) {
            return;
        }

        this.queuedDirections.add(newDirection);
    }

    private void handlePause(KeyEvent event) {
        if(event.getCode() != KeyCode.P) {
            return;
        }

        GLog.info("Pause triggered");

        this.isPaused = !this.isPaused;

        if(this.isPaused) {
            this.timer.stop();
            this.emitStateEvent(this.onGamePause, new OnGamePauseEvent());
            return;
        }

        this.timer.play();
        this.emitStateEvent(this.onGameResume, new OnGameResumeEvent());
    }

    private <T extends StateEvent> void emitStateEvent(Consumer<T> eventHandler, T event) {
        if(eventHandler != null) {
            eventHandler.accept(event);
        }
    }

    public SnakeGame setOnPointsUpdate(Consumer<OnSnakePointsChangeEvent> onPointsUpdate) {
        this.onPointsUpdate = onPointsUpdate;
        return this;
    }

    public SnakeGame setOnGameOver(Consumer<OnGameOverEvent> onGameOver) {
        this.onGameOver = onGameOver;
        return this;
    }

    public SnakeGame setOnSnakeGrowth(Consumer<OnSnakeSizeChangeEvent> onSnakeGrowth) {
        this.onSnakeGrowth = onSnakeGrowth;
        return this;
    }

    public SnakeGame setOnGameStart(Consumer<OnGameStartEvent> onGameStart) {
        this.onGameStart = onGameStart;
        return this;
    }

    public SnakeGame setOnPause(Consumer<OnGamePauseEvent> onGamePause) {
        this.onGamePause = onGamePause;
        return this;
    }

    public SnakeGame setOnResume(Consumer<OnGameResumeEvent> onGameResume) {
        this.onGameResume = onGameResume;
        return this;
    }
}
