package at.fhhgb.mc.snake.game;

import at.fhhgb.mc.snake.game.options.GameOptions;
import at.fhhgb.mc.snake.game.renderer.DefaultRenderer;
import at.fhhgb.mc.snake.game.renderer.GameCell;
import at.fhhgb.mc.snake.game.renderer.GameRenderer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class SnakeGame {
    private GameOptions options;
    private final Pane container;
    private GameRenderer renderer;

    private Timeline timer;

    public SnakeGame(GameOptions options, Pane container) {
        this.options = options;
        this.container = container;
        this.renderer = new DefaultRenderer(options);

        this.initGame();
    }

    private void initGame() {
        this.initContainer();
    }

    public void updateOptions(GameOptions options) {
        this.options = options;
        this.initContainer();
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

    public void startGame() {
        this.timer = new Timeline(
            new KeyFrame(Duration.millis(500), event -> {
                renderer.update();
            })
        );

        this.timer.setCycleCount(Timeline.INDEFINITE);
        this.timer.play();
    }

    public void update() {

    }
}
