package at.fhhgb.mc.snake.elements.dialog;

import at.fhhgb.mc.snake.game.options.GameOptions;
import at.fhhgb.mc.snake.game.options.GameSizeConfig;
import javafx.stage.Window;

public class GameStartDialog extends DialogBase<GameSizeConfig> {
    private final static String fxmlPath = "dialog/game-start-dialog.fxml";

    public GameStartDialog(Window owner, GameOptions options) {
        super(owner, fxmlPath, options);
    }
}
