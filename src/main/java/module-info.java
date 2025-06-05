module at.fhhgb.mc.snake {
    requires javafx.controls;
    requires javafx.fxml;
    requires atlantafx.base;
    requires java.logging;
    requires java.desktop;

    exports at.fhhgb.mc.snake;
    exports at.fhhgb.mc.snake.controller;
    exports at.fhhgb.mc.snake.controller.dialog;
    exports at.fhhgb.mc.snake.elements.dialog;
    exports at.fhhgb.mc.snake.game.options;
    exports at.fhhgb.mc.snake.game.struct;

    opens at.fhhgb.mc.snake to javafx.fxml;
    opens at.fhhgb.mc.snake.controller to javafx.fxml;
    opens at.fhhgb.mc.snake.controller.dialog to javafx.fxml;
    opens at.fhhgb.mc.snake.controller.models to javafx.base;
    exports at.fhhgb.mc.snake.elements;
}