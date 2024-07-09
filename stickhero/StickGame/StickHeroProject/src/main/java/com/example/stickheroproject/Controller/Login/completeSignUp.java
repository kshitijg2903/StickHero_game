package com.example.stickheroproject.Controller.Login;

import java.io.FileWriter;
import java.io.IOException;

public class completeSignUp {
    verifyLogin verifyLogin = new verifyLogin();
    public boolean startSignUp(String usid, String pwd){
        if(!verifyLogin.verifyLoginDetails(usid, pwd)){
            String csvFile = "C:\\Users\\Homeu\\IdeaProjects\\StickGame\\StickHeroProject\\src\\main\\resources\\game.csv";
            try (FileWriter writer = new FileWriter(csvFile, true)){
                writer.append(usid);
                writer.append(",");
                writer.append(pwd);
                writer.append(",");
                writer.append("10");
                writer.append("\n");
                writer.flush();
                return true;
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        return false;
    }
}
