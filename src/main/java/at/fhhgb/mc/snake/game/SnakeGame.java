package at.fhhgb.mc.snake.game;

import at.fhhgb.mc.snake.game.entity.AbstractEntity;
import at.fhhgb.mc.snake.game.event.GameEvent;
import at.fhhgb.mc.snake.game.options.GameOptions;
import at.fhhgb.mc.snake.game.renderer.DefaultRenderer;
import at.fhhgb.mc.snake.game.renderer.GameCell;
import at.fhhgb.mc.snake.game.renderer.AbstractGameRenderer;
import at.fhhgb.mc.snake.game.struct.Point2D;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventTarget;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SnakeGame {
    private GameOptions options;
    private final Pane container;
    private final AbstractGameRenderer renderer;
    private Timeline timer;

    private EventTarget eventTarget;

    private boolean isRunning;
    private boolean isPaused;

    private Snake.Direction queuedDirection;
    private Snake.Direction currentDirection;
    private final Snake snake;

    public SnakeGame(Pane container) {
        this.options = GameOptions.getInstance();
        this.container = container;
        this.renderer = new DefaultRenderer(this.options);

        this.isPaused = false;

        this.currentDirection = Snake.Direction.RIGHT;
        this.snake = new Snake();

        this.initGame();
    }

    public void updateOptions(GameOptions options) {
        this.cleanup();
        this.options = options;
        this.initContainer();
    }

    private void initGame() {
        this.initContainer();
        this.initTimer();
        this.initKeyListener();
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
        this.eventTarget = Optional
            .<EventTarget>ofNullable(this.container.getScene())
            .orElse(this.container);

        if(this.eventTarget == this.container) {
            this.container.setFocusTraversable(true);
        }

        this.eventTarget.addEventHandler(KeyEvent.KEY_PRESSED, this::handleKeyEvent);
    }

    public void startGame() {
        this.isRunning = true;
        this.timer.play();
    }

    public void stopGame() {
        this.isRunning = false;
        this.cleanup();
    }

    public void cleanup() {
        this.timer.stop();
        this.eventTarget.removeEventHandler(KeyEvent.KEY_PRESSED, this::handleKeyEvent);
        this.container.getChildren().clear();
    }

    private void gameLoop(ActionEvent event) {
        this.updateEntities();
        this.updateData();
        renderer.update(this.getEntities());
    }

    private void updateEntities() {
        if(this.queuedDirection != null && this.queuedDirection != this.currentDirection) {
            this.currentDirection = this.queuedDirection;
            this.queuedDirection = null;
        }

        this.snake.move(this.currentDirection);
    }

    private void updateData() {
        Point2D headPos = this.snake.getParts().getFirst().getPosition();
        GameCell headCell = this.renderer.getCellAt(headPos);
        List<AbstractEntity> consumedEntities = headCell.getEntities();

        for(AbstractEntity entity : consumedEntities) {
            this.consumeEvents(entity.onConsume());
        }
    }

    private void consumeEvents(List<GameEvent> events) {

    }

    private List<AbstractEntity> getEntities() {
        ArrayList<AbstractEntity> entities = new ArrayList<>();
        entities.addAll(this.snake.getParts());

        return entities;
    }

    private void handleKeyEvent(KeyEvent event) {
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

        this.isPaused = !this.isPaused;

        if(this.isPaused) {
            this.timer.stop();
        } else {
            this.timer.play();
        }
    }
}
