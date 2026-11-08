package cz.mendelu.projektjava_xkuba2.controllers;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;

public abstract class TableController<T> {

    @FXML private TableView<T> table;

    //vyhledavani
    @FXML
    public TextField txtSearch;

    @FXML
    public Label entriesCountLabel;

    public void initialize(){}
    public void loadDatabaseData() {}
    public void loadDatabaseData(List<T> data){}
    public T getSelectedEntry()
    {
        return table.getSelectionModel().getSelectedItem();
    }
    public ReadOnlyObjectProperty<T> selectedEntryProperty() {
        return table.getSelectionModel().selectedItemProperty();
    }
    public abstract void updateTableCountLabel();
    @FXML
    public void resetSearchFilter()
    {
        txtSearch.setText("");
        loadDatabaseData();
    }
}