package com.example.stickheroproject.Controller.Main.Score;

public class displayScore implements Runnable{
    @Override
    public void run() {
        while (true){
            manageScore.getScore();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

}
