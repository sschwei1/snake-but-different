package at.fhhgb.mc.snake.controller.dialog;

import at.fhhgb.mc.snake.elements.dialog.DialogResult;
import at.fhhgb.mc.snake.game.options.GameOptions;
import javafx.scene.control.ButtonType;

public abstract class DialogBaseController<T> {
    public abstract DialogResult<T> getResult(ButtonType buttonType);
    public abstract void initializeWithOptions(GameOptions options);
}
