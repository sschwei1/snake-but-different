package at.fhhgb.mc.snake.game.options;

import java.util.List;

public record FoodConfiguration(int initiallySpawnedFood, List<FoodValueConfig> availableFood) {
    public record FoodValueConfig(int pointIncrease, int lengthIncrease, int spawnNewAmount) {
        public FoodValueConfig {
            if (pointIncrease < 0 || lengthIncrease < 0) {
                throw new IllegalArgumentException("Point and length increase must be non-negative.");
            }
        }
    }

    public FoodConfiguration {
        if (availableFood == null || availableFood.isEmpty()) {
            throw new IllegalArgumentException("Configured food cannot be null or empty.");
        }
    }
}
