package com.example.stickheroproject.UI.Login;

import com.example.stickheroproject.Controller.Login.completeSignUp;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SignUp extends Application {
    private  completeSignUp completeSignUp = new completeSignUp();
    private String username;
    private String password;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Stick Hero Sign-Up");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Sign-Up In Stick-Hero");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("New User Name:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("New Password:");
        grid.add(pw, 0, 2);

        PasswordField passwordBox = new PasswordField();
        grid.add(passwordBox, 1, 2);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

        Button btn = new Button("Sign Up");
        btn.setOnAction(actionEvent -> {
            if(completeSignUp.startSignUp(userTextField.getText(), passwordBox.getText())) {
                Login login = new Login();
                login.start(primaryStage);
            }
            else {
                actiontarget.setText("UserName/Password already exists");
            }
        });
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
//        Button SIbtn = new Button("Sign in");
//        hbBtn.getChildren().add(SIbtn);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        Scene scene = new Scene(grid, 400, 475);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
