package at.fhhgb.mc.snake.controller;

import at.fhhgb.mc.snake.elements.dialog.DialogResult;
import at.fhhgb.mc.snake.elements.dialog.FoodConfigDialog;
import at.fhhgb.mc.snake.elements.dialog.GameSpeedDialog;
import at.fhhgb.mc.snake.elements.dialog.GameStartDialog;
import at.fhhgb.mc.snake.game.SnakeGame;
import at.fhhgb.mc.snake.game.options.FoodConfig;
import at.fhhgb.mc.snake.game.options.GameOptions;
import at.fhhgb.mc.snake.game.options.GameFieldConfig;
import at.fhhgb.mc.snake.log.GLog;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.Arrays;

public class MainWindowController {
    private enum GameState {
        INIT,
        GAME_OVER,
        PAUSED,
        RUNNING
    }

    @FXML private MenuItem startButton;
    @FXML private MenuItem gameSpeedButton;
    @FXML private MenuItem foodConfigButton;

    @FXML private MenuItem easyPresetButton;
    @FXML private MenuItem mediumPresetButton;
    @FXML private MenuItem hardPresetButton;
    @FXML private MenuItem defaultPresetButton;

    @FXML private VBox gameOverOverlay;
    @FXML private VBox gamePauseOverlay;

    @FXML private Label gameOverPoints;
    @FXML private Label gameOverSize;

    @FXML private Label snakeSize;
    @FXML private Label gamePoints;

    @FXML private Pane gameContainer;

    private final ObjectProperty<GameState> gameStateProperty = new SimpleObjectProperty<>(GameState.INIT);

    private final IntegerProperty score = new SimpleIntegerProperty(0);
    private final IntegerProperty size = new SimpleIntegerProperty(0);

    private SnakeGame activeGame;
    private GameOptions gameOptions;

    @FXML
    public void initialize() {
        BooleanBinding buttonEnabled = this.getGameStateBinding(GameState.INIT, GameState.GAME_OVER);
        this.startButton.disableProperty().bind(buttonEnabled.not());
        this.gameSpeedButton.disableProperty().bind(buttonEnabled.not());
        this.foodConfigButton.disableProperty().bind(buttonEnabled.not());

        this.easyPresetButton.disableProperty().bind(buttonEnabled.not());
        this.mediumPresetButton.disableProperty().bind(buttonEnabled.not());
        this.hardPresetButton.disableProperty().bind(buttonEnabled.not());
        this.defaultPresetButton.disableProperty().bind(buttonEnabled.not());

        this.gameOverOverlay.visibleProperty().bind(this.getGameStateBinding(GameState.GAME_OVER));
        this.gamePauseOverlay.visibleProperty().bind(this.getGameStateBinding(GameState.PAUSED) );

        this.gameOverPoints.textProperty().bind(this.score.asString());
        this.gameOverSize.textProperty().bind(this.size.asString());

        this.gamePoints.textProperty().bind(this.score.asString());
        this.snakeSize.textProperty().bind(this.size.asString());

        this.activeGame = null;
        this.gameOptions = GameOptions.getDefaultCustomOptions();
    }

    @FXML
    private void onStartButtonClick() {
        GameStartDialog gameStartDialog = new GameStartDialog(
            this.gameContainer.getScene().getWindow(),
            this.gameOptions
        );
        DialogResult<GameFieldConfig> startResult = gameStartDialog.showDialog();

        if(startResult.action() != DialogResult.DialogAction.OK) {
            return;
        }

        GLog.info("Starting game!");
        this.gameOptions.setGameSizeConfig(startResult.result());
        this.startNewGame();
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

        if(speedResult.action() != DialogResult.DialogAction.OK) {
            return;
        }

        GLog.info("Speed Value updated: " + speedResult.result());
        this.gameOptions.setTickSpeed(speedResult.result());
    }

    @FXML
    private void onFoodConfigButtonClick() {
        FoodConfigDialog dialog = new FoodConfigDialog(
            this.gameContainer.getScene().getWindow(),
            this.gameOptions
        );
        DialogResult<FoodConfig> foodResult = dialog.showDialog();
        if(foodResult.action() != DialogResult.DialogAction.OK) {
            return;
        }

        GLog.info("Food Config updated: " + foodResult.result());
        this.gameOptions.setFoodConfig(foodResult.result());
    }

    @FXML
    private void onEasyPresetButtonClick() {
        this.gameOptions = GameOptions.getEasyMode();
        this.startNewGame();
    }

    @FXML
    private void onMediumPresetButtonClick() {
        this.gameOptions = GameOptions.getMediumMode();
        this.startNewGame();
    }

    @FXML
    private void onHardPresetButtonClick() {
        this.gameOptions = GameOptions.getHardMode();
        this.startNewGame();
    }

    @FXML
    private void onResetToDefaultPreset() {
        this.gameOptions = GameOptions.getDefaultCustomOptions();
        this.startNewGame();
    }

    @FXML
    private void startNewGame() {
        if(this.activeGame != null) {
            this.activeGame.cleanup();
        }

        this.activeGame = new SnakeGame(this.gameContainer, this.gameOptions)
            .setOnPointsUpdate(event -> this.score.set(event.getTotal()))
            .setOnSnakeGrowth(event -> this.size.set(event.getTotal()))
            .setOnPause(_ -> this.gameStateProperty.set(GameState.PAUSED))
            .setOnResume(_ -> this.gameStateProperty.set(GameState.RUNNING))
            .setOnGameStart(_ -> this.gameStateProperty.set(GameState.RUNNING))
            .setOnGameOver(_ -> this.gameStateProperty.set(GameState.GAME_OVER));

        this.activeGame.start();
    }

    private BooleanBinding getGameStateBinding(GameState... gameStates) {
        return Bindings.createBooleanBinding(
            () -> Arrays.stream(gameStates).anyMatch(state ->
                state == this.gameStateProperty.get()
            ),
            this.gameStateProperty
        );
    }
}