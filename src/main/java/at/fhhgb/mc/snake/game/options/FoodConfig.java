package at.fhhgb.mc.snake.game.options;

import javafx.scene.paint.Color;

import java.util.List;

public record FoodConfig(int initiallySpawnedFood, List<FoodValueConfig> availableFood) {
    public record FoodValueConfig(int pointIncrease, int lengthIncrease, int spawnNewAmount, Color color) {
        public FoodValueConfig {
            if (pointIncrease < 0 || lengthIncrease < 0) {
                throw new IllegalArgumentException("Point and length increase must be non-negative.");
            }
        }
    }

    public FoodConfig {
        if (availableFood == null || availableFood.isEmpty()) {
            throw new IllegalArgumentException("Configured food cannot be null or empty.");
        }
    }
}
