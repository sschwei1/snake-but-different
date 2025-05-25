package at.fhhgb.mc.snake.elements.dialog;

import at.fhhgb.mc.snake.game.options.GameOptions;
import javafx.stage.Window;

public class GameSpeedDialog extends DialogBase<Integer> {
    private final static String fxmlPath = "dialog/game-speed-dialog.fxml";

    public GameSpeedDialog(Window owner, GameOptions options) {
        super(owner, fxmlPath, options);
    }
}
