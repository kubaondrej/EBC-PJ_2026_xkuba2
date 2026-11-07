module cz.mendelu.projektjava_xkuba2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;

    opens cz.mendelu.projektjava_xkuba2.model to com.fasterxml.jackson.databind;

    exports cz.mendelu.projektjava_xkuba2.model;
    exports cz.mendelu.projektjava_xkuba2.storage;
    opens cz.mendelu.projektjava_xkuba2 to com.fasterxml.jackson.databind, javafx.fxml;
    exports cz.mendelu.projektjava_xkuba2;
    exports cz.mendelu.projektjava_xkuba2.controllers;
    opens cz.mendelu.projektjava_xkuba2.controllers to com.fasterxml.jackson.databind, javafx.fxml;
}