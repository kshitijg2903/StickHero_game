package com.example.stickheroproject.UI.Main;

import com.example.stickheroproject.Controller.Main.Score.manageScore;
import com.example.stickheroproject.Controller.Main.Score.updateScore;
import com.example.stickheroproject.Controller.Main.GameDisplay.manageHearts;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class mainGameplay extends Application {
    public static Thread displayScore;
    private bottomGamePlay bottomGamePlay = new bottomGamePlay();
    private Pane bottomGamePane = bottomGamePlay.getGamePane();
    private updateScore updateScore = new updateScore();
    private manageHearts manageHearts = new manageHearts();
    private static ArrayList<ImageView> hearts = new ArrayList<>();
    private HBox topDisplay() {
        manageHearts.setHearts(hearts, 3);
        Label scoreLabel = new Label("Score: " + manageScore.getScore());
        Label mushroomsLabel = new Label("Mushrooms: " + manageScore.getMushrooms());
        Button exit = new Button("Exit");
        exit.setOnAction(actionEvent -> {
            PreGamePlay preGamePlay = new PreGamePlay();
            try {
                Stage currentStage = (Stage) exit.getScene().getWindow();
                currentStage.close();
                preGamePlay.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
        });
//        exit.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> event.consume());

        scoreLabel.setStyle("-fx-font-size: 20px;");
        mushroomsLabel.setStyle("-fx-font-size: 20px;");
        exit.setStyle("-fx-font-size: 15px; -fx-background-radius: 15");

        HBox topBox = new HBox();
        HBox heartBox = new HBox();
        heartBox.getChildren().addAll(hearts);
        heartBox.setAlignment(Pos.CENTER);
        topBox.getChildren().addAll(heartBox, scoreLabel, mushroomsLabel, exit);
        topBox.setPrefSize(1000, 50);
        topBox.setSpacing(20);
        topBox.setAlignment(Pos.CENTER);
        topBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.7);");
        // update data om csv file
        displayScore = new Thread(() -> {
            while (true) {
                try {
                    Platform.runLater(() -> {
                        scoreLabel.setText("Score: " + manageScore.getScore());
                        mushroomsLabel.setText("Mushrooms: " + manageScore.getMushrooms());
                        updateScore.updateScoreCSV("admin");
                        manageHearts.manageHeart(hearts, heartBox);
                    });

                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        displayScore.setDaemon(true); // Make the thread a daemon thread
        displayScore.start();

        return topBox;
    }
    @Override
    public void start(Stage primaryStage) {
        Image backgroundImage = new Image(getClass().getResource("/background.png").toExternalForm());

        if (backgroundImage.isError()) {
            System.err.println("Error loading the background image!");
        }

        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(1280);   // 1280x720
        backgroundImageView.setFitHeight(900);
        StackPane root = new StackPane(backgroundImageView);
        Scene scene = new Scene(root, 1280, 900);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Stick Hero");
        primaryStage.show();

        VBox mainGameBox = new VBox();
        mainGameBox.getChildren().add(topDisplay());
        root.getChildren().add(mainGameBox);
        bottomGamePlay.constructBottomDisplay();
        mainGameBox.getChildren().add(bottomGamePane);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
