package at.fhhgb.mc.snake.controller;

import at.fhhgb.mc.snake.game.SnakeGame;
import at.fhhgb.mc.snake.game.options.GameOptions;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class MainWindowController {
    @FXML private Pane gameContainer;

    @FXML
    public void initialize() {
        System.out.println("MainWindowController initialized");
    }

    @FXML
    protected void onStartButtonClick() {
        GameOptions gameOptions = GameOptions.getInstance();
        SnakeGame snakeGame = new SnakeGame(gameOptions, gameContainer);
        snakeGame.startGame();
        System.out.println("Start button clicked");
    }

    @FXML
    protected void onExitButtonClick() {
        System.exit(0);
    }
}