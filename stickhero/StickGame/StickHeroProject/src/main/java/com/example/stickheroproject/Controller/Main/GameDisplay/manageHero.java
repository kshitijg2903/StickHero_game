package com.example.stickheroproject.Controller.Main.GameDisplay;
import com.example.stickheroproject.Controller.Main.GameDisplay.manageSound;
import com.example.stickheroproject.Controller.Main.Score.manageScore;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class manageHero {
    private manageSound manageSound = new manageSound();
    Image heroIconImage = new Image(getClass().getResource("/sonicicon.png").toExternalForm());
    public ImageView createHeroIcon(){
        int pixelWidth = 80;
        int pixelHeight = 110;
        ImageView heroIcon =new ImageView(heroIconImage);
        heroIcon.setFitWidth(pixelWidth);
        heroIcon.setFitHeight(pixelHeight);
        return heroIcon;
    }
    public void setHeroLocation(ImageView heroIcon, Pane pane, double x, double y){
        heroIcon.setLayoutX(x);
        heroIcon.setLayoutY(y);
        pane.getChildren().add(heroIcon);
    }
    public boolean validateHeroPosition(ImageView heroIcon, Rectangle block) {
        double heroX = heroIcon.getLayoutX() +heroIcon.getTranslateX();
        double blockX = block.getX();
        double blockWidth = block.getWidth();

        double blockLeft = blockX - blockWidth / 2;
        double blockRight = blockX + blockWidth / 2;
        return heroX >= blockLeft && heroX <= blockRight;
    }
    private void showExitConfirmation() {
        if (auth.isHeroDropped()) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Exit Game");
                alert.setHeaderText("Are you sure you want to exit the game?");
                ButtonType yesButton = new ButtonType("Yes");
                ButtonType noButton = new ButtonType("No");
                alert.getButtonTypes().setAll(yesButton, noButton);

                alert.showAndWait().ifPresent(response -> {
                    if (response == yesButton) {
                        Platform.exit();
                        System.exit(0);
                    } else if (response == noButton) {
                        auth.setHeroDropped(false);
                        auth.setRestartGame(true);
                        auth.setDelHeart(true);
                        // Additional logic if needed when the user chooses not to exit
                    }
                });
            });
        }
    }
    public void dropHero(ImageView heroIcon) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(3), heroIcon);
        translateTransition.setNode(heroIcon);
        translateTransition.setByY(300);
        translateTransition.play();
    }

    public void setMovementHero(ImageView heroIcon, Pane gamePane, Line stick, Rectangle block) {
        TranslateTransition moveTransition = null;
        SequentialTransition sequentialTransition = new SequentialTransition();
        if(auth.getGameLoop()>=4 && auth.getGameLoop()<8){
            moveTransition = new TranslateTransition(Duration.seconds(2), heroIcon);
        } else if (auth.getGameLoop()>=8) {
            moveTransition = new TranslateTransition(Duration.seconds(1), heroIcon);
        } else if (auth.getGameLoop()<4) {
            moveTransition = new TranslateTransition(Duration.seconds(3), heroIcon);
        }
        moveTransition.setByX(-stick.getEndY()+50); // Adjusted for correct horizontal movement
        gamePane.setOnMouseClicked(event -> {
            if(auth.isHeroUp()){
                heroIcon.setScaleY(heroIcon.getScaleY() * -1);
                heroIcon.setLayoutY(heroIcon.getLayoutY()+heroIcon.getFitHeight());
                auth.setHeroUp(false);
            } else {
                heroIcon.setScaleY(heroIcon.getScaleY() * -1);
                heroIcon.setLayoutY(heroIcon.getLayoutY()-heroIcon.getFitHeight());
                auth.setHeroUp(true);
            }
        });
        TranslateTransition dummyTransition = new TranslateTransition(Duration.ONE, heroIcon); // Dummy transition for synchronization
        dummyTransition.setOnFinished(e -> {
            boolean check = validateHeroPosition(heroIcon, block);
            if (!check || !auth.isHeroUp()) {
                gamePane.setOnMouseClicked(null);
                dropHero(heroIcon);
                auth.setHeroDropped(true);
                auth.setHasHeroMoved(false);
                showExitConfirmation();
            } else {
                gamePane.setOnMouseClicked(null);
                manageSound.playLevelSuccess();
                manageScore.setScore();
                auth.setHasHeroMoved(true);
                auth.setHasHeroReached(true);

            }
        });

        sequentialTransition.getChildren().addAll(moveTransition, dummyTransition);
        sequentialTransition.play();
    }

}
