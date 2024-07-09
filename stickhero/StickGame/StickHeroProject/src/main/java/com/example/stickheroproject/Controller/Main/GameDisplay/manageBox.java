package com.example.stickheroproject.Controller.Main.GameDisplay;

import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class manageBox {
    private final int START_LENGTH = 120;
    private final int END_LENGTH = 150;
    private int currentWidth;
    private int HEIGHT = 300;
    Random random = new Random();
    public int getCurrentLength() {return currentWidth;}
    public Rectangle createRectangleBox() {
        this.currentWidth = ThreadLocalRandom.current().nextInt(this.START_LENGTH, this.END_LENGTH + 1);
        String hexColor = "#0E3723";
        Color fillColor = Color.web(hexColor);

        // Create the Rectangle with rounded corners and set the fill color
        Rectangle rectangle = new Rectangle(this.currentWidth, HEIGHT);
        rectangle.setArcWidth(10.0);
        rectangle.setArcHeight(10.0);
        rectangle.setFill(fillColor);

        return rectangle;
    }
    public void setLocation(double x, double y, Rectangle block, Pane pane){
        block.setX(x);
        block.setY(y);
        pane.getChildren().add(block);
    }
    public void animateBlockRightLeft(Rectangle block, double startX, double startY) {
        int targetY = 400;
        int targetX = random.nextInt(400, 700);
        block.setTranslateX(0);
        block.setTranslateY(0);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(5), block);
        transition.setToX(40);
        transition.setToY(40);
        transition.play();
    }

}
