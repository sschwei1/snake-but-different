package at.fhhgb.mc.snake.game;

import at.fhhgb.mc.snake.game.entity.AbstractEntity;
import at.fhhgb.mc.snake.game.entity.FoodEntity;
import at.fhhgb.mc.snake.game.entity.SnakePartEntity;
import at.fhhgb.mc.snake.game.event.game.GameEvent;
import at.fhhgb.mc.snake.game.event.game.GrowthEvent;
import at.fhhgb.mc.snake.game.event.game.PointsEvent;
import at.fhhgb.mc.snake.game.event.update.GameOverEvent;
import at.fhhgb.mc.snake.game.event.update.PointsChangeEvent;
import at.fhhgb.mc.snake.game.event.update.SnakeGrowthEvent;
import at.fhhgb.mc.snake.game.event.update.UpdateEvent;
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
    private GameOptions options;

    private final Pane container;
    private AbstractGameRenderer renderer;
    private Timeline timer;

    private EventHandler<KeyEvent> keyEventHandler;
    private EventTarget eventTarget;

    private Consumer<PointsChangeEvent> onPointsUpdate;
    private Consumer<GameOverEvent> onGameOver;
    private Consumer<SnakeGrowthEvent> onSnakeGrowth;

    // TODO: not yet implemented update events
    private Consumer<UpdateEvent> onGameStart;
    private Consumer<UpdateEvent> onGamePause;
    private Consumer<UpdateEvent> onGameResume;

    private Random random;
    private boolean isRunning;
    private boolean isPaused;

    private Snake.Direction queuedDirection;
    private Snake.Direction currentDirection;
    private Snake snake;
    private int score;
    private List<FoodEntity> placedFood;

    public SnakeGame(Pane container) {
        this.options = GameOptions.getInstance();
        this.container = container;
        this.initGame();
    }

    public void updateOptions(GameOptions options) {
        this.cleanup();
        this.options = options;
        this.initContainer();
    }

    private void initGame() {
        this.initData();
        this.initContainer();
        this.initKeyListener();

        this.spawnFood();
        this.initTimer();
    }

    private void initData() {
        this.random = new Random();
        this.isRunning = false;
        this.isPaused = false;

        this.renderer = new DefaultRenderer(this.options);

        this.currentDirection = Snake.Direction.RIGHT;
        this.snake = new Snake();
        this.score = 0;
        this.placedFood = new LinkedList<>();

        this.onPointsUpdate = null;
        this.onGameOver = null;
        this.onSnakeGrowth = null;
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

        container.getChildren().clear();
        container.getChildren().add(grid);
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

    public void startGame() {
        this.isRunning = true;
        this.timer.play();
    }

    private void endGame() {
        this.isRunning = false;
        this.timer.stop();

        if(this.onGameOver != null) {
            this.onGameOver.accept(new GameOverEvent(this.score, this.snake));
        }
    }

    public void cleanup() {
        this.isRunning = false;
        this.timer.stop();
        this.eventTarget.removeEventHandler(KeyEvent.KEY_PRESSED, this.keyEventHandler);
        this.container.getChildren().clear();
        this.snake.getParts().clear();
        this.placedFood.clear();
    }

    private void spawnFood() {
        int fieldSize = this.options.getGameWidth() * this.options.getGameHeight();
        int foodPos = this.random.nextInt(fieldSize - this.snake.getParts().size());

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

        this.placedFood.add(new FoodEntity(foodX, foodY, 5, 100));
    }

    private void gameLoop(ActionEvent event) {
        this.updateEntities();
        this.updateData();
        renderer.update(this.getEntities());
    }

    private void updateEntities() {
        GLog.info("Start updating Entities");
        if(this.queuedDirection != null && this.queuedDirection != this.currentDirection) {
            this.currentDirection = this.queuedDirection;
            this.queuedDirection = null;
        }

        this.snake.move(this.currentDirection);
        GLog.info("End updating Entities");
    }

    private void updateData() {
        GLog.info("Start updating Data");
        Point2D headPos = this.snake.getParts().getFirst().getPosition();
        List<AbstractEntity> consumedEntities = this.getEntities().stream()
            .filter(e -> e.getPosition().equals(headPos))
            .toList();

        GLog.info("Snake Head: " + headPos);

        for(AbstractEntity entity : consumedEntities) {
            GLog.info("Consume: " + entity.getType());
            this.consumeEvents(entity.onConsume());

            if(entity instanceof FoodEntity && this.placedFood.contains(entity)) {
                this.placedFood.remove(entity);
                this.spawnFood();
            }
        }

        GLog.info("End updating Data");
    }

    private void consumeEvents(List<GameEvent> events) {
        for(GameEvent event : events) {
            switch(event.getEventType()) {
                case POINTS: handlePointsEvent(event);  break;
                case GROWTH: handleGrowthEvent(event);  break;
                case DEATH:  handleDeathEvent();        break;
            }
        }
    }

    private List<AbstractEntity> getEntities() {
        ArrayList<AbstractEntity> entities = new ArrayList<>();
        entities.addAll(this.snake.getParts());
        entities.addAll(this.placedFood);
        return entities;
    }

    private void handlePointsEvent(GameEvent event) {
        if(!(event instanceof PointsEvent pointsEvent)) return;

        this.score += pointsEvent.getPointsChange();

        if(this.onPointsUpdate != null) {
            this.onPointsUpdate.accept(
                new PointsChangeEvent(this.score, pointsEvent.getPointsChange())
            );
        }
    }

    private void handleGrowthEvent(GameEvent event) {
        if(!(event instanceof GrowthEvent growthEvent)) return;

        this.snake.increaseSizeBy(growthEvent.getGrowthSize());

        if(this.onSnakeGrowth != null) {
            this.onSnakeGrowth.accept(
                new SnakeGrowthEvent(this.snake.getParts().size(), growthEvent.getGrowthSize())
            );
        }
    }

    private void handleDeathEvent() {
        GLog.error("Game Over");
        this.endGame();
    }

    private void handleKeyEvent(KeyEvent event) {
        if(!this.isRunning) return;

        this.handleUpdateDirection(event);
        this.handlePause(event);
        event.consume();
    }

    private void handleUpdateDirection(KeyEvent event) {
        switch (event.getCode()) {
            case KeyCode.UP:
            case KeyCode.W:
                this.updateQueuedDirection(Snake.Direction.UP);
                break;

            case KeyCode.DOWN:
            case KeyCode.S:
                this.updateQueuedDirection(Snake.Direction.DOWN);
                break;

            case LEFT:
            case KeyCode.A:
                this.updateQueuedDirection(Snake.Direction.LEFT);
                break;

            case KeyCode.RIGHT:
            case KeyCode.D:
                this.updateQueuedDirection(Snake.Direction.RIGHT);
                break;
        }
    }

    private void updateQueuedDirection(Snake.Direction newDirection) {
        if(
            this.currentDirection == Snake.Direction.UP    && newDirection == Snake.Direction.DOWN  ||
            this.currentDirection == Snake.Direction.DOWN  && newDirection == Snake.Direction.UP    ||
            this.currentDirection == Snake.Direction.LEFT  && newDirection == Snake.Direction.RIGHT ||
            this.currentDirection == Snake.Direction.RIGHT && newDirection == Snake.Direction.LEFT
        ) {
            return;
        }

        this.queuedDirection = newDirection;
    }

    private void handlePause(KeyEvent event) {
        if(event.getCode() != KeyCode.P) {
            return;
        }

        GLog.error("Pause triggered");

        this.isPaused = !this.isPaused;

        if(this.isPaused) {
            this.timer.stop();
        } else {
            this.timer.play();
        }
    }

    public void setOnPointsUpdate(Consumer<PointsChangeEvent> onPointsUpdate) {
        this.onPointsUpdate = onPointsUpdate;
    }

    public void setOnGameOver(Consumer<GameOverEvent> onGameOver) {
        this.onGameOver = onGameOver;
    }

    public void setOnSnakeGrowth(Consumer<SnakeGrowthEvent> onSnakeGrowth) {
        this.onSnakeGrowth = onSnakeGrowth;
    }
}
