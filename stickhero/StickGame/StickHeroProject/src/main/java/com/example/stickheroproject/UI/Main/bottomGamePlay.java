package com.example.stickheroproject.UI.Main;

import com.example.stickheroproject.Controller.Main.GameDisplay.manageBox;
import com.example.stickheroproject.Controller.Main.GameDisplay.manageHero;
import com.example.stickheroproject.Controller.Main.GameDisplay.auth;
import com.example.stickheroproject.Controller.Main.Score.manageScore;
import com.example.stickheroproject.Controller.Main.GameDisplay.manageSound;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class bottomGamePlay {
    private final double FIRST_BLOCK_X_COORDINATE = 300;
    private final double FIRST_BLOCK_Y_COORDINATE = 540;
    private double HERO_X_COORDINATE = 300;
    private final double HERO_Y_COORDINATE = 430;
    /////////////////////////////////////////////////////////////////////////////////
    private manageBox manageBox = new manageBox();
    private manageHero manageHero = new manageHero();
    private manageSound manageSound = new manageSound();
    private ArrayList<Circle> topMushrooms = new ArrayList<>();
    private  ArrayList<Circle> bottomMushrooms = new ArrayList<>();
    Random random = new Random();
    /////////////////////////////////////////////////////////////////////////////////
    private void setGamePane(Pane gamePane) {bottomGamePlay.gamePane = gamePane;}
    public void setPaneSize(){gamePane.setPrefSize(1280, 700);}
    public Pane getGamePane() {return gamePane;}
    /////////////////////////////////////////////////////////////////////////////////
    static Pane gamePane = new Pane();
    private ImageView heroIcon;
    private Timeline timeline;
    private Line stick = new Line();
    public Line getStick() {return stick;}
    private Rectangle block1;
    private Rectangle block2;
    private Thread moveHero;
    private Thread checkMushroomCollision;
    private Thread reloadGameLoop;
    private static Thread restartGameLoop;
    private Thread manageThreads;
    /////////////////////////////////////////////////////////////////////////////////
    public void constructBottomDisplay() {
        if(moveHero!=null && checkMushroomCollision!=null && restartGameLoop!=null && reloadGameLoop!=null && manageThreads!=null) {
            moveHero.interrupt();
            checkMushroomCollision.interrupt();
            reloadGameLoop.interrupt();
            restartGameLoop.interrupt();
            manageThreads.interrupt();
        }
        if (auth.isMoveHero()) {
            setPaneSize();
            block1 = manageBox.createRectangleBox();
            block2 = manageBox.createRectangleBox();
            heroIcon = manageHero.createHeroIcon();
            manageBox.setLocation(FIRST_BLOCK_X_COORDINATE, FIRST_BLOCK_Y_COORDINATE, block1, gamePane);
            manageBox.setLocation(random.nextInt((int)FIRST_BLOCK_X_COORDINATE+500, 900), FIRST_BLOCK_Y_COORDINATE, block2, gamePane);
            manageHero.setHeroLocation(heroIcon, gamePane, HERO_X_COORDINATE, HERO_Y_COORDINATE);
            moveHero = new Thread(() -> {
                while (!auth.isCreatedStick()) {
                    try {
                        Thread.sleep(1000);  // Adjust the sleep duration as needed
                    } catch (InterruptedException e) {
                        System.out.println("MoveHero-Thread Interrupted");
                        break;
                    }
                }
                Platform.runLater(() -> manageHero.setMovementHero(heroIcon, gamePane, stick, block2));
            });
            checkMushroomCollision = new Thread(() -> {
                while (!auth.isHasHeroReached()) {
                    try {
                        Thread.sleep(100); // Check for collision at regular intervals
                    } catch (InterruptedException e) {
                        System.out.println("CheckMushroom-Thread Interrupted");
                        break;
                    }
                    Platform.runLater(()->{
                        if(auth.isHeroUp()){
                            checkForTopMushroomCollision();
                        }
                        else {
                            checkForBottomMushroomCollision();
                        }
                    });
                }
            });

            reloadGameLoop = new Thread(()->{
                while (!auth.isHasHeroReached() || auth.isRestartGame()){
                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        System.out.println("Reload-Thread Interrupted");
                        break;
                    }
                }
                Platform.runLater(() -> {
                    if(auth.isHasHeroReached() && !auth.isRestartGame()) {
                        for (Circle mushroom : topMushrooms) {
                            gamePane.getChildren().remove(mushroom);
                        }
                        topMushrooms.clear();

                        for (Circle mushroom : bottomMushrooms) {
                            gamePane.getChildren().remove(mushroom);
                        }
                        bottomMushrooms.clear();
                        System.out.println("Reload Game Thread Started");
                        auth.setGameLoop();
                        double deltaX = block2.getX() - HERO_X_COORDINATE; // Calculate the delta for block2
                        TranslateTransition tt1 = new TranslateTransition(Duration.seconds(2), block2);
                        TranslateTransition tt2 = new TranslateTransition(Duration.seconds(2), heroIcon);
                        gamePane.getChildren().remove(block1);
                        gamePane.getChildren().remove(stick);
                        this.block1 = block2;
                        tt1.setByX(-deltaX); // Move block2 by deltaX
                        tt2.setByX(-deltaX); // Move heroIcon by the same deltaX
                        ParallelTransition ptt = new ParallelTransition(tt1, tt2);
                        ptt.setOnFinished(actionEvent -> {
                            gamePane.getChildren().remove(heroIcon);
                            gamePane.getChildren().remove(block2);
                            stick = new Line();
                            auth.setDefault();
                            constructBottomDisplay();
                        });
                        ptt.play();
                    }
                });

            });
            restartGameLoop = new Thread(() -> {
                while (!auth.isRestartGame()) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println("Restart-Thread Interrupted");
                        break;
                    }
                }
                Platform.runLater(() -> {
                    if (auth.isRestartGame() && !auth.isHasHeroReached()) {
                        for (Circle mushroom : topMushrooms) {
                            gamePane.getChildren().remove(mushroom);
                        }
                        topMushrooms.clear();

                        for (Circle mushroom : bottomMushrooms) {
                            gamePane.getChildren().remove(mushroom);
                        }
                        bottomMushrooms.clear();
                        System.out.println("Restart Game Thread Started");
                        gamePane.getChildren().remove(block1);
                        gamePane.getChildren().remove(block2);
                        gamePane.getChildren().remove(heroIcon);
                        gamePane.getChildren().remove(stick);
                        stick = new Line();
                        auth.setDefault();
                        constructBottomDisplay();
                    }
                });
            });
            manageThreads = new Thread(()->{
                while(true){
                    try {
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        System.out.println("Manage-Threads Interrupted");
                        break;
                    }
                    finally {
                        if (auth.isRestartGame()){
                            moveHero.interrupt();
                            reloadGameLoop.interrupt();
                            checkMushroomCollision.interrupt();
                        }
                        else if(auth.isHasHeroReached()){
                            moveHero.interrupt();
                            restartGameLoop.interrupt();
                            checkMushroomCollision.interrupt();
                        }
                    }
                }
            });
            gamePane.setOnMouseClicked(event -> {
                double STICK_X_COORDINATE = heroIcon.getLayoutX() + block1.getWidth();
                double STICK_Y_COORDINATE = heroIcon.getLayoutY() + heroIcon.getFitHeight();

                if (auth.isCreateStick()) {
                    // Create a new line at the click position with zero length
                    stick.setLayoutX(STICK_X_COORDINATE);
                    stick.setLayoutY(STICK_Y_COORDINATE);
                    stick.setStrokeWidth(5);
                    gamePane.getChildren().add(stick);

                    timeline = new Timeline(new KeyFrame(Duration.seconds(0.016), e -> stick.setEndY(stick.getEndY() - 1)));
                    timeline.setCycleCount(Timeline.INDEFINITE);
                    timeline.play();
                    auth.setCreateStick(false);

                    // add mushrooms here
                } else {
                    timeline.stop();
                    stick.getTransforms().add(new Rotate(90));
                    stick.setLayoutX(STICK_X_COORDINATE);
                    stick.setLayoutY(STICK_Y_COORDINATE);
                    auth.setCreatedStick(true);
                    createTopMushroomsOnStick();
                    createBottomMushroomsOnStick();
                    gamePane.setOnMouseClicked(null);
                    moveHero.start();
                    checkMushroomCollision.start();
                    restartGameLoop.start();
                    manageThreads.start();
                }
            });
            reloadGameLoop.start();
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void createBottomMushroomsOnStick() {
        double blockLength = block2.getX() - block2.getX();
        double stickLength = stick.getEndY()-stick.getStartY();
        int mushroomCount;

        if(blockLength/stickLength<3) mushroomCount = random.nextInt(2) + 1; // Random number of mushrooms
        else if(blockLength/stickLength<1.5 && blockLength/stickLength>=3) mushroomCount = random.nextInt(4) + 1;
        else mushroomCount = random.nextInt(5) + 1;

        for (int i = 0; i < mushroomCount; i++) {
            double position = random.nextDouble() * stick.getEndY();

            // Use the initial coordinates of the stick
            Circle mushroom = new Circle(stick.getLayoutX()-position, stick.getLayoutY()+10, 10, Color.BROWN);
            bottomMushrooms.add(mushroom);
            gamePane.getChildren().add(mushroom);
        }
    }
    private void createTopMushroomsOnStick() {
        double blockLength = block2.getX() - block2.getX();
        double stickLength = stick.getEndY()-stick.getStartY();
        int mushroomCount;

        if(blockLength/stickLength<3) mushroomCount = random.nextInt(2) + 1; // Random number of mushrooms
        else if(blockLength/stickLength<1.5 && blockLength/stickLength>=3) mushroomCount = random.nextInt(4) + 1;
        else mushroomCount = random.nextInt(5) + 1;

        for (int i = 0; i < mushroomCount; i++) {
            double position = random.nextDouble() * stick.getEndY();

            // Use the initial coordinates of the stick
            Circle mushroom = new Circle(stick.getLayoutX()-position, stick.getLayoutY()-10, 10, Color.BROWN);
            topMushrooms.add(mushroom);
            gamePane.getChildren().add(mushroom);
        }
    }
    private void checkForTopMushroomCollision() {
        Iterator<Circle> iterator = topMushrooms.iterator();
        while (iterator.hasNext()) {
            Circle mushroom = iterator.next();
            if (heroIcon.getBoundsInParent().intersects(mushroom.getBoundsInParent())) {
                gamePane.getChildren().remove(mushroom);
                iterator.remove();
                manageScore.setMushrooms(manageScore.getMushrooms() + 1); // Update score for each mushroom
                manageSound.playCollisionSound();
            }
        }
    }
    private void checkForBottomMushroomCollision() {
        Iterator<Circle> iterator = bottomMushrooms.iterator();
        while (iterator.hasNext()) {
            Circle mushroom = iterator.next();
            if (heroIcon.getBoundsInParent().intersects(mushroom.getBoundsInParent())) {
                gamePane.getChildren().remove(mushroom);
                iterator.remove();
                manageScore.setMushrooms(manageScore.getMushrooms() + 1); // Update score for each mushroom
                manageSound.playCollisionSound();
            }
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


}
