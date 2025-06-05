package at.fhhgb.mc.snake.elements.dialog;

import at.fhhgb.mc.snake.game.options.FoodConfig;
import at.fhhgb.mc.snake.game.options.GameOptions;
import javafx.stage.Window;

public class FoodConfigDialog extends DialogBase<FoodConfig> {
    private final static String fxmlPath = "dialog/food-config-dialog.fxml";

    public FoodConfigDialog(Window owner, GameOptions options) {
        super(owner, fxmlPath, options);

        this.getDialogPane().setPrefWidth(700);
    }
}
