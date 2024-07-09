package com.example.stickheroproject.Controller.Login;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class verifyLogin {
    private ArrayList<String> login = new ArrayList<>();
    private ArrayList<String> pwd = new ArrayList<>();
    public void getLoginDetails() {
        String csvFile = "C:\\Users\\Homeu\\IdeaProjects\\StickGame\\StickHeroProject\\src\\main\\resources\\game.csv";
        try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
            // Read all rows into a list
            List<String[]> rows = reader.readAll();

            for (String[] row : rows) {
                // Assuming the CSV file has three columns
                login.add(row[0]);
                pwd.add(row[1]);
            }

        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }
    public boolean verifyLoginDetails(String usid, String pwd){
        getLoginDetails();
        System.out.println(this.login);
        System.out.println(this.pwd);
        boolean loginCheck = false;
        boolean pwdCheck = false;
        for(String id: this.login){
            if(id.equals(usid)){
                loginCheck = true;
                break;
            }
        }
        for(String pwdid : this.pwd){
            if(pwdid.equals(pwd)){
                pwdCheck = true;
                break;
            }
        }
        return loginCheck&pwdCheck;
    }
}
