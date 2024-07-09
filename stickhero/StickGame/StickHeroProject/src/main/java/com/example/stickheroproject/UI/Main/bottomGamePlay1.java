package com.example.stickheroproject.UI.Main;

import com.example.stickheroproject.Controller.Main.GameDisplay.manageBox;
import com.example.stickheroproject.Controller.Main.Score.manageScore;
import javafx.scene.shape.Circle; // Import for mushroom representation
import java.util.ArrayList; // Import for list
import java.util.Iterator; // Import for iterator
import com.example.stickheroproject.Controller.Main.GameDisplay.manageHero;
import com.example.stickheroproject.Controller.Main.GameDisplay.auth;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.util.Random;

public class bottomGamePlay1 {

    private ArrayList<Circle> mushrooms = new ArrayList<>(); // List to store mushrooms
    private ArrayList<Circle> topMushrooms = new ArrayList<>();
    private  ArrayList<Circle> bottomMushrooms = new ArrayList<>();

    private final double FIRST_BLOCK_X_COORDINATE = 200;
    private final double FIRST_BLOCK_Y_COORDINATE = 580;
    private double HERO_X_COORDINATE = 200;
    private final double HERO_Y_COORDINATE = 510;
    /////////////////////////////////////////////////////////////////////////////////
    private manageBox manageBox = new manageBox();
    private manageHero manageHero = new manageHero();
    Random random = new Random();
    /////////////////////////////////////////////////////////////////////////////////
    public void setGamePane(Pane gamePane) {
        bottomGamePlay.gamePane = gamePane;}
    public void setPaneSize(){gamePane.setPrefSize(1280, 700);}
    public Pane getGamePane() {return gamePane;}
    /////////////////////////////////////////////////////////////////////////////////
    private static Pane gamePane = new Pane();
    private ImageView heroIcon;
    private Timeline timeline;
    private Line stick = new Line();
    public Line getStick() {return stick;}
    private Rectangle block1;
    /////////////////////////////////////////////////////////////////////////////////
    public void constructBottomDisplay() {
        if (auth.isMoveHero()) {
            setPaneSize();
            if (auth.getGameLoop() == 0) {
                block1 = manageBox.createRectangleBox();
            }
            Rectangle block2 = manageBox.createRectangleBox();
            block2.setFill(Color.RED);
            heroIcon = manageHero.createHeroIcon();
            manageBox.setLocation((int) FIRST_BLOCK_X_COORDINATE, (int) FIRST_BLOCK_Y_COORDINATE, block1, gamePane);
            manageBox.setLocation(random.nextInt((int) FIRST_BLOCK_X_COORDINATE + 400, 1000), (int) FIRST_BLOCK_Y_COORDINATE, block2, gamePane);
            manageHero.setHeroLocation(heroIcon, gamePane, (int) HERO_X_COORDINATE, (int) HERO_Y_COORDINATE);

            Thread moveHero = new Thread(() -> {
                while (!auth.isCreatedStick()) {
                    try {
                        Thread.sleep(1000);  // Adjust the sleep duration as needed
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Platform.runLater(() -> manageHero.setMovementHero(heroIcon, gamePane, stick, block2));
            });

            Thread checkMushroomCollision = new Thread(() -> {
                while (!auth.isHasHeroReached()) {
                    try {
                        Thread.sleep(100); // Check for collision at regular intervals
                    } catch (InterruptedException e) {
                        e.printStackTrace();
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

            Thread reloadGameLoop = new Thread(() -> {
                while (!auth.isHasHeroReached()) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Platform.runLater(() -> {
                    if (auth.isHasHeroReached()) {
                        auth.setGameLoop();
                        Rectangle block_1 = block2;
                        gamePane.getChildren().remove(heroIcon);
                        gamePane.getChildren().remove(block1);
                        gamePane.getChildren().remove(block2);
                        gamePane.getChildren().remove(stick);
                        stick = new Line();
                        auth.setDefault();
                        block1 = block_1;
                        constructBottomDisplay();
                    }
                });
            });

            gamePane.setOnMouseClicked(event -> {
                double STICK_X_COORDINATE = heroIcon.getLayoutX() + block1.getWidth();
                double STICK_Y_COORDINATE = heroIcon.getLayoutY() + heroIcon.getFitHeight();

                if (auth.isCreateStick()) {
                    stick.setLayoutX(STICK_X_COORDINATE);
                    stick.setLayoutY(STICK_Y_COORDINATE);
                    stick.setStrokeWidth(5);
                    gamePane.getChildren().add(stick);

                    timeline = new Timeline(new KeyFrame(Duration.seconds(0.016), e -> stick.setEndY(stick.getEndY() - 1)));
                    timeline.setCycleCount(Timeline.INDEFINITE);
                    timeline.play();
                    auth.setCreateStick(false);
                     // Add mushrooms on the stick
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
                    checkMushroomCollision.start(); // Start checking for mushroom collisions
                }
            });

            System.out.println("Hero-Reached:" + auth.isHasHeroReached());
            reloadGameLoop.start();
        }
    }

    private void createTopMushroomsOnStick() {
        int mushroomCount = random.nextInt(5) + 1; // Random number of mushrooms

        for (int i = 0; i < mushroomCount; i++) {
            double position = random.nextDouble() * stick.getEndY();

            // Use the initial coordinates of the stick
            Circle mushroom = new Circle(stick.getLayoutX()-position, stick.getLayoutY()-10, 10, Color.BROWN);
            mushrooms.add(mushroom);
            topMushrooms.add(mushroom);
            gamePane.getChildren().add(mushroom);
        }
    }

    private void createBottomMushroomsOnStick() {
        int mushroomCount = random.nextInt(5) + 1; // Random number of mushrooms

        for (int i = 0; i < mushroomCount; i++) {
            double position = random.nextDouble() * stick.getEndY();

            // Use the initial coordinates of the stick
            Circle mushroom = new Circle(stick.getLayoutX()-position, stick.getLayoutY()+10, 10, Color.BROWN);
            mushrooms.add(mushroom);
            bottomMushrooms.add(mushroom);
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
            }
        }
    }
}