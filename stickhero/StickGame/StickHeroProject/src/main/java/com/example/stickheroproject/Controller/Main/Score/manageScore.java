package com.example.stickheroproject.Controller.Main.Score;

public class manageScore {
    private static int score = 0;
    private static int mushrooms = 0;
    public static int getMushrooms() {return mushrooms;}
    public static int getScore() {return score;}
    
    public static void setMushrooms(int newMushrooms) {
        mushrooms = newMushrooms;
        score += 2; // 2 points for each mushroom
    }
    public static void clearScore(){score =0;}
    public static void setScore() {
        manageScore.score += 5;}
}
