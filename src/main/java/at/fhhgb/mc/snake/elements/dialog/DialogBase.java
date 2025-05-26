package at.fhhgb.mc.snake.elements.dialog;

import at.fhhgb.mc.snake.Main;
import at.fhhgb.mc.snake.controller.dialog.DialogBaseController;
import at.fhhgb.mc.snake.game.options.GameOptions;
import at.fhhgb.mc.snake.log.GLog;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Window;

import java.io.IOException;

public abstract class DialogBase<T> extends Dialog<DialogResult<T>> {

    protected DialogBase(Window owner, String fxmlName, GameOptions options) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxmlName));
            Pane root = loader.load();
            DialogBaseController<T> controller = loader.getController();
            controller.initializeWithOptions(options);

            this.initOwner(owner);
            this.initModality(Modality.APPLICATION_MODAL);
            this.getDialogPane().setContent(root);
            this.setResultConverter(controller::getResult);

            this.setButtons(ButtonType.OK, ButtonType.CANCEL);

            Button okButton = (Button) this.getDialogPane().lookupButton(ButtonType.OK);
            okButton.disableProperty().bind(controller.getInputsValidBinding().not());
        } catch(IOException ex) {
            GLog.error(ex.getMessage());
        }
    }

    protected void setButtons(ButtonType... buttonTypes) {
        this.getDialogPane().getButtonTypes().clear();
        this.getDialogPane().getButtonTypes().setAll(buttonTypes);
    }

    public DialogResult<T> showDialog() {
        return this.showAndWait().orElse(DialogResult.invalid());
    }
}
