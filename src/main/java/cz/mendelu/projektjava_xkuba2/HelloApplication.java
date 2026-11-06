package cz.mendelu.projektjava_xkuba2;

import cz.mendelu.projektjava_xkuba2.service.AppData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        AppData.load();   // načte hned po spuštění
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("menu_scene.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 250);
        stage.setTitle("Lékařská dtaabáze");
        stage.setScene(scene);
        stage.show();
    }
}
