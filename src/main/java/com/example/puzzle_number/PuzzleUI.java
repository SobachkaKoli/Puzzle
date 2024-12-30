package com.example.puzzle_number;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PuzzleUI {

    private final Stage stage;
    private final TextArea resultArea;

    public PuzzleUI(Stage stage) {
        this.stage = stage;
        this.resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setPromptText("Result will be displayed here...");
    }

    public Scene createScene() {
        Button loadFileButton = createLoadFileButton();

        VBox layout = new VBox(10, loadFileButton, resultArea);
        layout.setStyle("-fx-padding: 10; -fx-alignment: center;");

        return new Scene(layout, 600, 400);
    }

    private Button createLoadFileButton() {
        Button loadFileButton = new Button("Load Puzzle File");

        loadFileButton.setOnAction(event -> {
            File file = chooseFile();
            if (file != null) {
                try {
                    List<String> fragments = PuzzleLogic.readFragmentsFromFile(file);
                    PuzzleLogic.validateFragments(fragments);
                    String result = PuzzleLogic.buildLargestSequence(fragments);
                    resultArea.setText(result);
                } catch (IOException e) {
                    resultArea.setText("Error reading file: " + e.getMessage());
                } catch (IllegalArgumentException e) {
                    resultArea.setText("Invalid file contents: " + e.getMessage());
                }
            } else {
                resultArea.setText("File selection was canceled.");
            }
        });

        return loadFileButton;
    }

    private File chooseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a Puzzle File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        return fileChooser.showOpenDialog(stage);
    }
}