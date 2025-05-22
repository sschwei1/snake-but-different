package at.fhhgb.mc.snake.game.event.state;

public abstract class ChangeEvent extends StateEvent {
    private final int total;
    private final int change;

    public ChangeEvent(int total, int change) {
        this.total = total;
        this.change = change;
    }

    public int getTotal() {
        return total;
    }

    public int getChange() {
        return change;
    }
}
