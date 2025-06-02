package at.fhhgb.mc.snake.controller.dialog;

import at.fhhgb.mc.snake.elements.dialog.DialogResult;
import at.fhhgb.mc.snake.game.options.GameOptions;
import at.fhhgb.mc.snake.game.options.GameFieldConfig;
import at.fhhgb.mc.snake.game.struct.Point2D;
import at.fhhgb.mc.snake.log.GLog;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class GameStartDialogController extends DialogBaseController<GameFieldConfig> {
    @FXML private TextField rowInput;
    @FXML private TextField columnInput;

    @FXML private TextField initialRow;
    @FXML private TextField initialColumn;

    @FXML private CheckBox enableWalls;

    private final IntegerProperty rowInputValue = new SimpleIntegerProperty(10);
    private final IntegerProperty columnInputValue = new SimpleIntegerProperty(10);
    private final IntegerProperty initialRowInputValue = new SimpleIntegerProperty(1);
    private final IntegerProperty initialColumnInputValue = new SimpleIntegerProperty(1);
    private final BooleanProperty enableWallsValue = new SimpleBooleanProperty(false);


    @FXML
    public void initialize() {
        DialogBaseController.BindTextFieldToIntegerProperty(this.rowInput, this.rowInputValue);
        DialogBaseController.BindTextFieldToIntegerProperty(this.columnInput, this.columnInputValue);
        DialogBaseController.BindTextFieldToIntegerProperty(this.initialRow, this.initialRowInputValue);
        DialogBaseController.BindTextFieldToIntegerProperty(this.initialColumn, this.initialColumnInputValue);
        this.enableWalls.selectedProperty().bindBidirectional(this.enableWallsValue);
    }

    @Override
    public void initializeWithOptions(GameOptions options) {
        this.rowInput.setText(String.valueOf(options.getGameHeightWithoutOffset()));
        this.columnInput.setText(String.valueOf(options.getGameWidthWithoutOffset()));

        Point2D initialPosition = options.getStartingPosition();
        this.initialRow.setText(String.valueOf(initialPosition.getY()));
        this.initialColumn.setText(String.valueOf(initialPosition.getX()));
        this.enableWalls.setSelected(options.isWallEnabled());
    }

    @Override
    public BooleanBinding getInputsValidBinding() {
        return new BooleanBinding() {
            {
                super.bind(
                    rowInputValue,
                    columnInputValue,
                    initialRowInputValue,
                    initialColumnInputValue
                );
            }

            @Override
            protected boolean computeValue() {
                return rowInputValue.get() >= 10 && rowInputValue.get() <= 100 &&
                    columnInputValue.get() >= 10 && columnInputValue.get() <= 100 &&

                    initialRowInputValue.get() >= 0 &&
                    initialRowInputValue.get() < rowInputValue.get() &&

                    initialColumnInputValue.get() >= 0 &&
                    initialColumnInputValue.get() < columnInputValue.get();
            }
        };
    }

    @Override
    public DialogResult<GameFieldConfig> getResult(ButtonType buttonType) {
        GLog.info("Row Val: " + this.rowInputValue.get());

        if(buttonType.getButtonData() != ButtonBar.ButtonData.OK_DONE) {
            return DialogResult.invalid();
        }

        return new DialogResult<>(
            DialogResult.DialogAction.OK,
            new GameFieldConfig(
                this.columnInputValue.get(),
                this.rowInputValue.get(),
                new Point2D(
                    this.initialColumnInputValue.get(),
                    this.initialRowInputValue.get()
                ),
                this.enableWallsValue.get()
            )
        );
    }
}
