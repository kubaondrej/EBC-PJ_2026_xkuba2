package cz.mendelu.projektjava_xkuba2.ui_controllers;

import cz.mendelu.projektjava_xkuba2.service.LoggingService;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;

import java.util.List;

public final class TableHelper {
    public static <T> void loadDataIntoTable(TableView<T> table, List<T> data) {
        table.setItems(FXCollections.observableArrayList(data));
        LoggingService.log("NACITAM" + ' ' + data.size()+ ' ' +"POLOŽEK ->" + ' ' +table.getId());
    }

    public static <T> boolean  checkIfEmpty(TableView<T> table)
    {
        return table.getItems().isEmpty();
    }


}
