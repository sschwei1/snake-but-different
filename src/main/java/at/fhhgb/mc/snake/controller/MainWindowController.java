package at.fhhgb.mc.snake.controller;

import at.fhhgb.mc.snake.game.SnakeGame;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MainWindowController {
    @FXML private VBox gameOverOverlay;

    @FXML private Label gameOverPoints;
    @FXML private Label gameOverSize;

    @FXML private Label snakeSize;
    @FXML private Label gamePoints;

    @FXML private Pane gameContainer;

    private final BooleanProperty showGameOver = new SimpleBooleanProperty(false);

    private final IntegerProperty score = new SimpleIntegerProperty(0);
    private final IntegerProperty size = new SimpleIntegerProperty(0);

    private SnakeGame runningGame;

    @FXML
    public void initialize() {
        this.gameOverOverlay.visibleProperty().bind(this.showGameOver);

        this.gameOverPoints.textProperty().bind(this.score.asString());
        this.gameOverSize.textProperty().bind(this.size.asString());

        this.gamePoints.textProperty().bind(this.score.asString());
        this.snakeSize.textProperty().bind(this.size.asString());

        this.runningGame = null;
    }

    @FXML
    protected void onStartButtonClick() {
        if(this.runningGame != null) {
            this.runningGame.cleanup();
        }

        this.runningGame = new SnakeGame(this.gameContainer)
            .setOnPointsUpdate(event -> this.score.set(event.getTotal()))
            .setOnSnakeGrowth(event -> this.size.set(event.getTotal()))
            .setOnGameStart(event -> this.showGameOver.set(false))
            .setOnGameOver(event -> this.showGameOver.set(true));

        this.runningGame.start();
    }

    @FXML
    protected void onExitButtonClick() {
        System.exit(0);
    }
}