package at.fhhgb.mc.snake.elements.dialog;

import javafx.stage.Window;

public class GameSpeedDialog extends DialogBase<String> {
    public GameSpeedDialog(Window owner) {
        super(owner, "dialog/game-speed-dialog.fxml");
    }
}
