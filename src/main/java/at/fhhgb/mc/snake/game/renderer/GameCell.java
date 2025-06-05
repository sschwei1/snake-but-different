package at.fhhgb.mc.snake.game.renderer;

import at.fhhgb.mc.snake.game.entity.AbstractEntity;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Map;
import java.util.WeakHashMap;

public class GameCell extends Pane {
    public enum State {
        EMPTY, FOOD, SNAKE_HEAD, SNAKE, WALL
    }

    private static final Map<Color, String> colorStyleCache = new WeakHashMap<>();

    private int currentPriority;

    public GameCell() {
        this(State.EMPTY, -1);
    }

    public GameCell(State state, int currentPriority) {
        this.setState(state);
        this.currentPriority = currentPriority;

        this.setBorder(new Border(
            new BorderStroke(
                Color.DARKGRAY,
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                new BorderWidths(1)
            )
        ));
    }

    public GameCell setState(AbstractEntity entity) {
        if(this.currentPriority >= entity.getRenderingPriority()) {
            return this;
        }

        this.currentPriority = entity.getRenderingPriority();

        return (entity.getType() == State.EMPTY || entity.getColor() == null)
            ? this.setState(entity.getType())
            : this.setState(entity.getColor());
    }

    public GameCell setState(State state) {
        switch (state) {
            case EMPTY      -> this.setStyle(null);
            case FOOD       -> this.setStyle("-fx-background-color: red");
            case SNAKE_HEAD -> this.setStyle("-fx-background-color: lightgreen");
            case SNAKE      -> this.setStyle("-fx-background-color: green");
            case WALL       -> this.setStyle("-fx-background-color: gray");
        }

        return this;
    }

    public GameCell setState(Color color) {
        if(color == null) {
            this.setStyle(null);
            return this;
        }

        String fxColor = colorStyleCache.computeIfAbsent(color, c -> String.format(
            "-fx-background-color: rgba(%d, %d, %d, %.2f)",
            (int) (c.getRed() * 255),
            (int) (c.getGreen() * 255),
            (int) (c.getBlue() * 255),
            c.getOpacity()
        ));
        this.setStyle(fxColor);

        return this;
    }

    public void clear() {
        this.currentPriority = -1;
        this.setState(State.EMPTY);
    }
}
