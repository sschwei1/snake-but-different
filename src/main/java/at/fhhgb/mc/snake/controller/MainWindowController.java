package at.fhhgb.mc.snake.controller;

import at.fhhgb.mc.snake.game.SnakeGame;
import at.fhhgb.mc.snake.game.options.GameOptions;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class MainWindowController {
    @FXML private Label gamePoints;
    @FXML private Pane gameContainer;

    private final IntegerProperty score = new SimpleIntegerProperty(0);
    private SnakeGame runningGame;

    @FXML
    public void initialize() {
        this.gamePoints.textProperty().bind(score.asString());
        this.runningGame = null;
    }

    @FXML
    protected void onStartButtonClick() {
        if(this.runningGame != null) {
            this.runningGame.cleanup();
        }

        this.runningGame = new SnakeGame(gameContainer);
        this.runningGame.setOnPointsUpdate(event -> this.score.set(event.getTotalPoints()));
        this.runningGame.startGame();
    }

    @FXML
    protected void onExitButtonClick() {
        System.exit(0);
    }
}