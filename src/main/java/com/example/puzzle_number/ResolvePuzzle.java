package com.example.puzzle_number;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ResolvePuzzle extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Largest Digital Puzzle");

        PuzzleUI puzzleUI = new PuzzleUI(primaryStage);
        Scene scene = puzzleUI.createScene();

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}