package at.fhhgb.mc.snake.controller.dialog;

import at.fhhgb.mc.snake.elements.dialog.DialogResult;
import at.fhhgb.mc.snake.game.options.GameOptions;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class GameSpeedDialogController extends DialogBaseController<Integer> {
    @FXML private Label speedSliderLabel;
    @FXML private Slider speedSlider;

    private final static int SLIDER_TO_SPEED_FACTOR = 10;

    private final IntegerProperty sliderValue = new SimpleIntegerProperty(0);
    private final IntegerProperty speedValue = new SimpleIntegerProperty(0);

    @FXML
    public void initialize() {
        this.sliderValue.set((int) this.speedSlider.getMin());
        this.speedValue.bind(this.sliderValue.multiply(SLIDER_TO_SPEED_FACTOR));

        this.speedSlider.valueProperty().bindBidirectional(this.sliderValue);
        this.speedSliderLabel.textProperty().bind(
            this.speedValue.asString("Game Speed: %dms")
        );
    }

    @Override
    public void initializeWithOptions(GameOptions options) {
        this.sliderValue.set(options.getTickSpeed() / SLIDER_TO_SPEED_FACTOR);
    }

    @Override
    public DialogResult<Integer> getResult(ButtonType buttonType) {
        if(buttonType.getButtonData() != ButtonBar.ButtonData.OK_DONE) {
            return DialogResult.invalid();
        }

        return new DialogResult<>(
            DialogResult.DialogAction.OK,
            speedValue.get()
        );
    }
}
