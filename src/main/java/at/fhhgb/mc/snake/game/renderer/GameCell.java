package at.fhhgb.mc.snake.game.renderer;

import at.fhhgb.mc.snake.game.entity.AbstractEntity;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class GameCell extends Pane {
    public enum State {
        EMPTY, FOOD, SNAKE_HEAD, SNAKE, WALL
    }

    private State state;
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

    public State getState() {
        return state;
    }

    public GameCell setState(AbstractEntity entity) {
        if(this.currentPriority >= entity.getRenderingPriority()) {
            return this;
        }
        this.currentPriority = entity.getRenderingPriority();
        return this.setState(entity.getType());
    }

    public GameCell setState(State state) {
        this.state = state;
        switch (state) {
            case EMPTY      -> this.setStyle(null);
            case FOOD       -> this.setStyle("-fx-background-color: red");
            case SNAKE_HEAD -> this.setStyle("-fx-background-color: lightgreen");
            case SNAKE      -> this.setStyle("-fx-background-color: green");
            case WALL       -> this.setStyle("-fx-background-color: gray");
        }

        return this;
    }

    public void clear() {
        this.currentPriority = -1;
        this.setState(State.EMPTY);
    }

    // TODO: Fix resizing always remain square problem
}
