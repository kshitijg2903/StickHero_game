
package com.example.stickheroproject.Controller.Main.GameDisplay;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class manageSound {

    private void playSound(String soundFileName) {
        try {
            // Load the sound file using the classpath
            String soundFilePath = getClass().getResource("/soundFiles/" + soundFileName).toString();
            Media sound = new Media(soundFilePath);
            MediaPlayer mediaPlayer = new MediaPlayer(sound);

            // Release resources after the media has finished playing
            mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.dispose());

            mediaPlayer.play();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions, e.g., media format not supported, etc.
        }
    }

    public void playCollisionSound() {
        playSound("mushroomPop.mp3");
    }

    public void playLevelSuccess() {
        playSound("levelSuccess.mp3");
    }

    public void playHighScoreSuccess() {
        playSound("highScore.mp3");
    }
}
