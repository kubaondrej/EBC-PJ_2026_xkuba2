package cz.mendelu.projektjava_xkuba2.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public final class SceneSwitcher {

    //musi tady byt, jinak pada
    private static final String FXML_PATH = "/cz/mendelu/projektjava_xkuba2/";

    private static int WIDTH;
    private static int HEIGHT;

    private static final int menuWIDTH=500;
    private static final int menuHEIGHT=300;

    /**
     * @param event        událost vyvolaná tlačítkem (slouží k nalezení okna)
     * @param fxmlFileName název FXML souboru ve stejném balíku v resources
     */
    public static void switchTo(ActionEvent event, String fxmlFileName) {
        switchTo(event,fxmlFileName,1240,650);
    }

    public static void switchTo(ActionEvent event, String fxmlFileName, int width, int height ) {
        WIDTH=width;
        HEIGHT=height;
        try {
            FXMLLoader loader = new FXMLLoader(SceneSwitcher.class.getResource(FXML_PATH + fxmlFileName));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, WIDTH, HEIGHT));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Chyba: Nepodařilo se načíst soubor " + fxmlFileName);
        }
    }

    public static int getMenuWIDTH() {
        return menuWIDTH;
    }

    public static int getMenuHEIGHT() {
        return menuHEIGHT;
    }

    public static void switchToMenu(ActionEvent event)
    {
        switchTo(event,"menu_scene.fxml",menuWIDTH,menuHEIGHT);
    }


}