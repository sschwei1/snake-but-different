package at.fhhgb.mc.snake.controller.dialog;

import at.fhhgb.mc.snake.controller.models.FoodConfigModel;
import at.fhhgb.mc.snake.elements.NumericTextFieldTableCell;
import at.fhhgb.mc.snake.elements.dialog.DialogResult;
import at.fhhgb.mc.snake.game.options.FoodConfig;
import at.fhhgb.mc.snake.game.options.GameOptions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.util.stream.Collectors;

public class FoodConfigDialogController extends DialogBaseController<FoodConfig> {
    @FXML private TableView<FoodConfigModel> foodTable;

    @FXML private TableColumn<FoodConfigModel, Integer> pointsIncreaseColumn;
    @FXML private TableColumn<FoodConfigModel, Integer> lengthIncreaseColumn;
    @FXML private TableColumn<FoodConfigModel, Integer> spawnNewFoodAmountColumn;
    @FXML private TableColumn<FoodConfigModel, Color> colorColumn;

    private final ObservableList<FoodConfigModel> foodList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        this.foodTable.setItems(this.foodList);

        this.foodTable.setEditable(true);
        this.setupIntColumn(this.pointsIncreaseColumn, FoodConfigModel.INT_COLUMN.POINTS_INCREASE);
        this.setupIntColumn(this.lengthIncreaseColumn, FoodConfigModel.INT_COLUMN.LENGTH_INCREASE);
        this.setupIntColumn(this.spawnNewFoodAmountColumn, FoodConfigModel.INT_COLUMN.SPAWN_NEW_AMOUNT);

        this.setupColorColumn(this.colorColumn);
    }

    @Override
    public void initializeWithOptions(GameOptions options) {
        this.foodList.addAll(
            options.getAvailableFoods()
                .stream()
                .map(FoodConfigModel::new)
                .collect(Collectors.toCollection(FXCollections::observableArrayList))
        );
    }

    @FXML
    private void onAddFoodClick() {
        this.foodList.add(FoodConfigModel.getDefault());
    }

    @FXML
    private void onRemoveFoodClick() {
        FoodConfigModel selected = this.foodTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            this.foodList.remove(selected);
        }
    }

    @Override
    public DialogResult<FoodConfig> getResult(ButtonType buttonType) {
        if(buttonType.getButtonData() != ButtonBar.ButtonData.OK_DONE) {
            return DialogResult.invalid();
        }

        return new DialogResult<>(
            DialogResult.DialogAction.OK,
            new FoodConfig(
                1,
                this.foodList.stream()
                    .map(FoodConfigModel::toFoodValueConfig)
                    .toList()
            )
        );
    }

    private void setupIntColumn(TableColumn<FoodConfigModel, Integer> column, FoodConfigModel.INT_COLUMN type) {
        column.setCellValueFactory(
            cellData ->
                cellData.getValue().getProperty(type).asObject()
        );

        column.setCellFactory(
            col -> new NumericTextFieldTableCell<>()
        );

        column.setOnEditCommit(evt -> {
            Integer newValue = evt.getNewValue();
            FoodConfigModel model = evt.getRowValue();
            model.update(type, newValue);
        });
    }

    private void setupColorColumn(TableColumn<FoodConfigModel, Color> column) {
        column.setCellValueFactory(cellData -> cellData.getValue().colorProperty());

        column.setCellFactory(col -> new TableCell<>() {
            private final ColorPicker colorPicker = new ColorPicker();
            {
                colorPicker.setOnAction(e -> {
                    FoodConfigModel model = getTableView().getItems().get(getIndex());
                    model.colorProperty().set(colorPicker.getValue());
                });
            }

            @Override
            protected void updateItem(Color color, boolean empty) {
                super.updateItem(color, empty);

                if (empty || color == null) {
                    setGraphic(null);
                } else {
                    colorPicker.setValue(color);
                    setGraphic(colorPicker);
                }
            }
        });
    }
}
