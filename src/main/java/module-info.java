module at.fhhgb.mc.snake {
    requires javafx.controls;
    requires javafx.fxml;
    requires atlantafx.base;
    requires java.desktop;


    opens at.fhhgb.mc.snake to javafx.fxml;
    exports at.fhhgb.mc.snake;
    exports at.fhhgb.mc.snake.controller;
    opens at.fhhgb.mc.snake.controller to javafx.fxml;
}