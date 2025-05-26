package at.fhhgb.mc.snake.controller.dialog;

import at.fhhgb.mc.snake.elements.dialog.DialogResult;
import at.fhhgb.mc.snake.game.options.GameOptions;
import at.fhhgb.mc.snake.log.GLog;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.IntegerProperty;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;

import java.util.function.UnaryOperator;

public abstract class DialogBaseController<T> {
    public abstract DialogResult<T> getResult(ButtonType buttonType);
    public abstract void initializeWithOptions(GameOptions options);

    public BooleanBinding getInputsValidBinding() {
        return new BooleanBinding() {
            @Override
            protected boolean computeValue() {
                return true;
            }
        };
    }

    protected static void BindTextFieldToIntegerProperty(TextField textField, IntegerProperty valueProperty) {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("-?\\d*")) {
                return change;
            }
            return null;
        };

        IntegerStringConverter converter = new IntegerStringConverter();
        TextFormatter<Integer> textFormatter = new TextFormatter<>(converter, 0, filter);
        textField.setTextFormatter(textFormatter);

        textField.textProperty().addListener((obs, oldValue, newValue) -> {
            GLog.info("Text changed to " + newValue);
            try {
                int parsedValue = Integer.parseInt(newValue);
                valueProperty.set(parsedValue);
                GLog.info("IntegerProperty updated to " + valueProperty.get());
            } catch (NumberFormatException e) {
                GLog.error("Invalid integer input: " + newValue);
                valueProperty.set(-1);
            }
        });
    }
}
