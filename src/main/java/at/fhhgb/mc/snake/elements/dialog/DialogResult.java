package at.fhhgb.mc.snake.elements.dialog;

public class DialogResult<T> {
    public enum DialogAction {
        OK, CANCEL
    }

    private final DialogAction action;
    private final T result;

    public DialogResult(DialogAction action, T result) {
        this.action = action;
        this.result = result;
    }

    public DialogAction getAction() {
        return action;
    }

    public T getResult() {
        return result;
    }

    public static <T> DialogResult<T> invalid() {
        return new DialogResult<>(DialogAction.CANCEL, null);
    }
}
