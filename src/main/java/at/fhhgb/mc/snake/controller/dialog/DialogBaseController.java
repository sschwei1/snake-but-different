package at.fhhgb.mc.snake.controller.dialog;

import at.fhhgb.mc.snake.elements.dialog.DialogResult;
import javafx.scene.control.ButtonType;

public abstract class DialogBaseController<T> {
    public abstract DialogResult<T> getResult(ButtonType buttonType);
}
