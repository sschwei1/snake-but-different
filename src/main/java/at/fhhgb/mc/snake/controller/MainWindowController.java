package at.fhhgb.mc.snake.controller;

import at.fhhgb.mc.snake.elements.dialog.DialogResult;
import at.fhhgb.mc.snake.elements.dialog.GameSpeedDialog;
import at.fhhgb.mc.snake.game.SnakeGame;
import at.fhhgb.mc.snake.game.options.GameOptions;
import at.fhhgb.mc.snake.log.GLog;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MainWindowController {
    @FXML private MenuItem startButton;
    @FXML private MenuItem exitButton;
    @FXML private MenuItem gameSpeedButton;

    @FXML private VBox gameOverOverlay;

    @FXML private Label gameOverPoints;
    @FXML private Label gameOverSize;

    @FXML private Label snakeSize;
    @FXML private Label gamePoints;

    @FXML private Pane gameContainer;

    private final BooleanProperty gameRunning = new SimpleBooleanProperty(false);

    private final IntegerProperty score = new SimpleIntegerProperty(0);
    private final IntegerProperty size = new SimpleIntegerProperty(0);

    private SnakeGame activeGame;
    private GameOptions gameOptions;

    @FXML
    public void initialize() {
        this.startButton.disableProperty().bind(gameRunning);
        this.exitButton.disableProperty().bind(gameRunning);
        this.gameSpeedButton.disableProperty().bind(gameRunning);

        this.gameOverOverlay.visibleProperty().set(false);

        this.gameOverPoints.textProperty().bind(this.score.asString());
        this.gameOverSize.textProperty().bind(this.size.asString());

        this.gamePoints.textProperty().bind(this.score.asString());
        this.snakeSize.textProperty().bind(this.size.asString());

        this.activeGame = null;
        this.gameOptions = GameOptions.getInstance();
    }

    @FXML
    private void onStartButtonClick() {
        if(this.activeGame != null) {
            this.activeGame.cleanup();
        } else {
            this.gameOverOverlay.visibleProperty().bind(this.gameRunning.not());
        }

        this.activeGame = new SnakeGame(this.gameContainer, this.gameOptions)
            .setOnPointsUpdate(event -> this.score.set(event.getTotal()))
            .setOnSnakeGrowth(event -> this.size.set(event.getTotal()))
            .setOnGameStart(event -> this.gameRunning.set(true))
            .setOnGameOver(event -> this.gameRunning.set(false));

        this.activeGame.start();
    }

    @FXML
    private void onExitButtonClick() {
        System.exit(0);
    }

    @FXML
    private void onGameSpeedButtonClick() {
        GameSpeedDialog dialog = new GameSpeedDialog(
            this.gameContainer.getScene().getWindow(),
            this.gameOptions
        );
        DialogResult<Integer> speedResult = dialog.showDialog();

        if(speedResult.getAction() != DialogResult.DialogAction.OK) {
            GLog.info("Dialog cancelled");
            return;
        }

        GLog.info("New Value: " + speedResult.getResult());
        this.gameOptions.setTickSpeed(speedResult.getResult());
    }
}