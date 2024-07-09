package com.example.stickheroproject.UI.Login;

import com.example.stickheroproject.Controller.Login.verifyLogin;
import com.example.stickheroproject.Controller.Main.GameDisplay.auth;
import com.example.stickheroproject.UI.Main.mainGameplay;
import com.example.stickheroproject.UI.Main.PreGamePlay;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Login extends Application {
    private verifyLogin verifyLogin = new verifyLogin();
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Stick Hero Login");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Welcome To Stick Hero");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField passwordBox = new PasswordField();
        grid.add(passwordBox, 1, 2);
        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);
        Button signIn = new Button("Sign In");
        Button signUp = new Button("Sign Up");
        signIn.setOnAction(event -> {
            if (verifyLogin.verifyLoginDetails(userTextField.getText(), passwordBox.getText())) {
                auth.setUserid(userTextField.getText());
                PreGamePlay preGamePlay = new PreGamePlay();
                preGamePlay.start(primaryStage);
            } else {
                actiontarget.setText("Invalid login credentials");
                actiontarget.setStyle("-fx-text-fill: red;");
            }
        });
        signUp.setOnAction(actionEvent -> {
            SignUp signUp1 = new SignUp();
            signUp1.start(primaryStage);
        });
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(signIn);
        hbBtn.getChildren().add(signUp);
        grid.add(hbBtn, 1, 4);
        Scene scene = new Scene(grid, 400, 475);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
