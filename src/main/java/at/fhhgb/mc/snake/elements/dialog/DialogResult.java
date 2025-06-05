package at.fhhgb.mc.snake.elements.dialog;

public record DialogResult<T>(DialogAction action, T result) {
    public enum DialogAction {
        OK, CANCEL
    }

    public static <T> DialogResult<T> invalid() {
        return new DialogResult<>(DialogAction.CANCEL, null);
    }
}
