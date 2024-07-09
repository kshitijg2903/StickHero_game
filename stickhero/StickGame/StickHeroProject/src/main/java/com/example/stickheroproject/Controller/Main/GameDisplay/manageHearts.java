package com.example.stickheroproject.Controller.Main.GameDisplay;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.example.stickheroproject.UI.Main.mainGameplay;
import com.example.stickheroproject.UI.Main.bottomGamePlay;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class manageHearts {
    public void setHearts(ArrayList<ImageView> heartArr, int num) {
        heartArr.clear(); // Clear existing hearts
        Image heart = new Image(getClass().getResource("/heart.png").toExternalForm());
        for (int i = 0; i < num; i++) {
            ImageView heartIcon = new ImageView(heart);
            // You may want to set fit height and width here if needed
            heartIcon.setFitHeight(20);
            heartIcon.setFitWidth(20);
            heartArr.add(heartIcon);
        }
    }
    private void showExitConfirmation() {
        if (auth.isHeroDropped()) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Exit Game");
                alert.setHeaderText("You have no more hearts!!");
                ButtonType yesButton = new ButtonType("Ok");
                alert.getButtonTypes().setAll(yesButton);

                alert.showAndWait().ifPresent(response -> {
                    if (response == yesButton) {
                        Platform.exit();
                        System.exit(0);
                    }
                });
            });
        }
    }
    public void manageHeart(ArrayList<ImageView> heartArr, HBox heartBox) {
        if (auth.isDelHeart()) {
            if (!heartArr.isEmpty()) {

                heartArr.remove(heartArr.size() - 1); // Remove the last heart
                auth.setHearts(auth.getHearts()-1);
                auth.setDelHeart(false);
                heartBox.getChildren().clear(); // Clear existing hearts
                heartBox.getChildren().addAll(heartArr); // Add the updated set of hearts
            }

        }
        if(auth.getHearts()==0) {
            showExitConfirmation();
            System.exit(0);
            mainGameplay.displayScore.interrupt();
        }
    }

}
