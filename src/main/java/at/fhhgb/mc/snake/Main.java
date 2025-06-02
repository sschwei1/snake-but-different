package at.fhhgb.mc.snake;

import at.fhhgb.mc.snake.log.GLog;
import atlantafx.base.theme.PrimerDark;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        GLog.disableLogging();
        Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Snake but different");
        stage.setScene(scene);

        stage.setWidth(900);
        stage.setHeight(900);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}