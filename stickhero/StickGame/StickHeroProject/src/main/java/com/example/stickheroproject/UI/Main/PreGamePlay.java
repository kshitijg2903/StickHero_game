package com.example.stickheroproject.UI.Main;

import com.example.stickheroproject.Controller.Main.GameDisplay.auth;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Locale;

public class PreGamePlay extends Application {

    public static void main(String[] args) {launch(args);}
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Window");

        // Heading
        Label headingLabel = new Label("Welcome To Stick Hero "+auth.getUserid().toLowerCase(Locale.ROOT));
        headingLabel.setFont(Font.font("Arial", 24));

        // Icons
        Image icon1 = new Image(getClass().getResource("/playButton.png").toExternalForm());
        Image icon2 = new Image(getClass().getResource("/exitButton.png").toExternalForm());


        ImageView iconView1 = new ImageView(icon1);
        iconView1.setFitWidth(70);
        iconView1.setFitHeight(70);
        iconView1.setOnMouseClicked(mouseEvent -> {
            mainGameplay gameplay = new mainGameplay();
            gameplay.start(primaryStage);
        });
        ImageView iconView2 = new ImageView(icon2);
        iconView2.setFitWidth(70);
        iconView2.setFitHeight(70);
        iconView2.setOnMouseClicked(mouseEvent -> {
            System.exit(0);
        });

        HBox imageBox = new HBox(20, iconView1, iconView2);
        imageBox.setAlignment(Pos.CENTER);

        Label termsAndConditions = new Label("Created by Aakarsh and Kshitij");
        termsAndConditions.setFont(Font.font("Arial", 14));
        termsAndConditions.setStyle("-fx-text-fill: #333333;");

        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(20));
        vBox.getChildren().addAll(headingLabel, imageBox, termsAndConditions); // Add imageBox directly to vBox

        Scene scene = new Scene(vBox, 800, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

}

