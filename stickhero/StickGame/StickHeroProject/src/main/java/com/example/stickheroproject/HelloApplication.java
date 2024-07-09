package com.example.stickheroproject;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create a Pane to hold the elements
        Pane root = new Pane();

        // Create a Rectangle (block)
        Rectangle block = new Rectangle(100, 100, 50, 50);
        block.setFill(Color.BLUE);

        // Create a character Rectangle
        Rectangle character = new Rectangle(50, 150, 30, 30);
        character.setFill(Color.RED);

        // Add the block and character to the Pane
        root.getChildren().addAll(block, character);

        // Create TranslateTransitions to move the block and character
        TranslateTransition blockTransition = new TranslateTransition(Duration.seconds(2), block);
        blockTransition.setToX(300); // Move the block to the right

        TranslateTransition characterTransition = new TranslateTransition(Duration.seconds(2), character);
        characterTransition.setToX(200); // Move the character to the right

        // Play the animations
        blockTransition.play();
        characterTransition.play();

        // Create a Scene and set it on the Stage
        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Moving Scene Example");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
