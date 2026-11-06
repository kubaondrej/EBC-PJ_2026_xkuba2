package cz.mendelu.projektjava_xkuba2;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.EventListener;


public final class SceneSwitcher {

    private static int WIDTH;
    private static int HEIGHT;

    private SceneSwitcher() {}

    /**
     * @param event        událost vyvolaná tlačítkem (slouží k nalezení okna)
     * @param fxmlFileName název FXML souboru ve stejném balíku v resources
     */
    public static void switchTo(ActionEvent event, String fxmlFileName) {
        if(fxmlFileName=="menu_scene.fxml")
        {
            WIDTH=500;
            HEIGHT=250;
        }
        else
        {
            WIDTH = 1240;
            HEIGHT = 500;
        }
        try {
            FXMLLoader loader = new FXMLLoader(SceneSwitcher.class.getResource(fxmlFileName));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, WIDTH, HEIGHT));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Chyba: Nepodařilo se načíst soubor " + fxmlFileName);
        }
    }


}