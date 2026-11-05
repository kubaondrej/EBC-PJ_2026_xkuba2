module cz.mendelu.projektjava_xkuba2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens cz.mendelu.projektjava_xkuba2 to javafx.fxml;
    exports cz.mendelu.projektjava_xkuba2;
}