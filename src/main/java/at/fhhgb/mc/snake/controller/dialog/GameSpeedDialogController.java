package at.fhhgb.mc.snake.controller.dialog;

import at.fhhgb.mc.snake.elements.dialog.DialogResult;
import at.fhhgb.mc.snake.log.GLog;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class GameSpeedDialogController extends DialogBaseController<String> {
    @FXML private TextField inputField;

    private final StringProperty speed = new SimpleStringProperty("");

    @FXML
    public void initialize() {
        this.inputField.textProperty().bindBidirectional(speed);
    }

    @Override
    public DialogResult<String> getResult(ButtonType buttonType) {
        if(buttonType != ButtonType.OK) {
            GLog.info("Dialog cancelled");
            return new DialogResult<>(
                DialogResult.DialogAction.CANCEL,
                null
            );
        }

        GLog.info("Dialog Submitted");
        return new DialogResult<>(
            DialogResult.DialogAction.OK,
            this.speed.get()
        );
    }
}
