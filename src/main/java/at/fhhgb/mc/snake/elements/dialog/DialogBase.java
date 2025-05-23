package at.fhhgb.mc.snake.elements.dialog;

import at.fhhgb.mc.snake.Main;
import at.fhhgb.mc.snake.controller.dialog.DialogBaseController;
import at.fhhgb.mc.snake.log.GLog;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Window;

import java.io.IOException;

public abstract class DialogBase<T> extends Dialog<DialogResult<T>> {

    protected DialogBase(Window owner, String fxmlName) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxmlName));
            Pane root = loader.load();
            DialogBaseController<T> controller = loader.getController();

            this.initOwner(owner);
            this.initModality(Modality.APPLICATION_MODAL);
            this.getDialogPane().setContent(root);
            this.setResultConverter(controller::getResult);

            this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        } catch(IOException ex) {
            GLog.error(ex.getMessage());
        }
    }
}
