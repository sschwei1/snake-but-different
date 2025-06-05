package at.fhhgb.mc.snake.controller.models;

import at.fhhgb.mc.snake.game.options.FoodConfig;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;

public class FoodConfigModel {
    public enum INT_COLUMN {
        POINTS_INCREASE(100,0),
        LENGTH_INCREASE(1,1),
        SPAWN_NEW_AMOUNT(1,1);

        private final int defaultValue;
        private final int minValue;

        INT_COLUMN(int defaultValue, int minValue) {
            this.defaultValue = defaultValue;
            this.minValue = minValue;
        }

        public int validate(Integer value) {
            return value == null || value < this.minValue ?
                       this.defaultValue :
                       value;
        }
    }

    private final FoodConfig.FoodValueConfig foodConfig;

    private final IntegerProperty pointsIncrease;
    private final IntegerProperty lengthIncrease;
    private final IntegerProperty spawnNewAmount;
    private final ObjectProperty<Color> color;

    public FoodConfigModel(FoodConfig.FoodValueConfig foodConfig) {
        this.foodConfig = foodConfig;

        this.pointsIncrease = new SimpleIntegerProperty(foodConfig.pointIncrease());
        this.lengthIncrease = new SimpleIntegerProperty(foodConfig.lengthIncrease());
        this.spawnNewAmount = new SimpleIntegerProperty(foodConfig.spawnNewAmount());
        this.color = new SimpleObjectProperty<>(foodConfig.color());
    }

    public static FoodConfigModel getDefault() {
        return new FoodConfigModel(
            new FoodConfig.FoodValueConfig(
                INT_COLUMN.POINTS_INCREASE.defaultValue,
                INT_COLUMN.LENGTH_INCREASE.defaultValue,
                INT_COLUMN.SPAWN_NEW_AMOUNT.defaultValue,
                Color.RED
            )
        );
    }

    public IntegerProperty pointsIncreaseProperty() {
        return this.pointsIncrease;
    }

    public IntegerProperty lengthIncreaseProperty() {
        return this.lengthIncrease;
    }

    public IntegerProperty spawnNewAmountProperty() {
        return this.spawnNewAmount;
    }

    public ObjectProperty<Color> colorProperty() {
        return this.color;
    }

    public IntegerProperty getProperty(INT_COLUMN type) {
        return switch (type) {
            case POINTS_INCREASE -> this.pointsIncrease;
            case LENGTH_INCREASE -> this.lengthIncrease;
            case SPAWN_NEW_AMOUNT -> this.spawnNewAmount;
        };
    }

    public void update(INT_COLUMN type, Integer value) {
        value = type.validate(value);

        switch (type) {
            case POINTS_INCREASE -> this.pointsIncreaseProperty().set(value);
            case LENGTH_INCREASE -> this.lengthIncreaseProperty().set(value);
            case SPAWN_NEW_AMOUNT -> this.spawnNewAmountProperty().set(value);
        }
    }

    public FoodConfig.FoodValueConfig toFoodValueConfig() {
        return new FoodConfig.FoodValueConfig(
            this.pointsIncrease.get(),
            this.lengthIncrease.get(),
            this.spawnNewAmount.get(),
            this.color.get()
        );
    }
}
