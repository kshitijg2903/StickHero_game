package com.example.stickheroproject.Controller.Main.Score;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.example.stickheroproject.Controller.Main.GameDisplay.manageSound;
import com.example.stickheroproject.Controller.Main.GameDisplay.auth;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class updateScore {
    private manageSound manageSound = new manageSound();
    public void updateScoreCSV(String userId) {
        String csvFile = getClass().getResource("/game.csv").getPath();

        String newScore = "" + manageScore.getScore();

        try {
            CSVReader reader = new CSVReader(new FileReader(csvFile));
            List<String[]> csvBody = reader.readAll();

            for(int i=0; i<csvBody.size(); i++){
                String[] strArray = csvBody.get(i);
                if(strArray[0].equals(userId)){
                    if(Integer.parseInt(newScore)>Integer.parseInt(strArray[2])) {
                        strArray[2] = newScore;
                        if(auth.getHighScoreReached()==0 && auth.isHasHeroReached()) showScoreUpdateDialog();
                    }
                }
                csvBody.set(i, strArray);
            }
            reader.close();
            CSVWriter writer = new CSVWriter(new FileWriter(csvFile));
            writer.writeAll(csvBody);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
    }
    private void showScoreUpdateDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Score Update");
        alert.setHeaderText(null);
        alert.setContentText("Congratulations! You have beaten your high score");
        manageSound.playHighScoreSuccess();
        alert.showAndWait();
        auth.setHighScoreReached();
    }
}
