package at.fhhgb.mc.snake.game.renderer;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class GameCell extends Pane {
    public enum State {
        EMPTY, FOOD, SNAKE_HEAD, SNAKE, WALL
    }

    private State state;

    public GameCell() {
        this(State.EMPTY);
    }

    public GameCell(State state) {
        this.setState(state);

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

    public GameCell setState(State state) {
        this.state = state;
        switch (state) {
            case EMPTY -> this.setStyle(null);
            case FOOD -> this.setStyle("-fx-background-color: red");
            case SNAKE_HEAD -> this.setStyle("-fx-background-color: lightgreen");
            case SNAKE -> this.setStyle("-fx-background-color: green");
            case WALL -> this.setStyle("-fx-background-color: gray");
        }

        return this;
    }

    // TODO: Fix resizing always remain square problem
//    @Override
//    protected void layoutChildren() {
//        double size = Math.min(getWidth(), getHeight());
//        this.resize(size, size);
//    }
}
