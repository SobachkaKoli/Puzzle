module com.example.puzzle_number {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.puzzle_number to javafx.fxml;
    exports com.example.puzzle_number;
}