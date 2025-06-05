package at.fhhgb.mc.snake.elements;

import atlantafx.base.util.IntegerStringConverter;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.TextFieldTableCell;

import java.util.function.UnaryOperator;

public class NumericTextFieldTableCell<T> extends TextFieldTableCell<T, Integer> {
    public NumericTextFieldTableCell() {
        super(new IntegerStringConverter());

        TextField textField = (TextField) this.getGraphic();

        if(textField == null) {
            return;
        }

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("-?\\d*")) {
                return change;
            }

            return null;
        };

        textField.setTextFormatter(
            new TextFormatter<>(new IntegerStringConverter(), 0, filter)
        );
    }

    @Override
    public void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty && this.getGraphic() != null) {
            ((TextField) this.getGraphic()).setAlignment(Pos.CENTER_RIGHT);
        }
    }
}
