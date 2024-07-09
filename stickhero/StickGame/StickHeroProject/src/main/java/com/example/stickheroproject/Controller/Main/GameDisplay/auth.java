package com.example.stickheroproject.Controller.Main.GameDisplay;

public class auth {
    private static String userid = "admin";
    private static String pwd = "admin";
    private static int gameLoop = 0;
    private static int hearts =3;
    private static boolean delHeart = false;
    public static void setDelHeart(boolean delHeart) {auth.delHeart = delHeart;}
    public static boolean isDelHeart() {return delHeart;}
    public static void setHearts(int hearts) {auth.hearts = hearts;}
    public static int getHearts() {return hearts;}
    public static boolean isRestartGame() {return restartGame;}
    public static void setRestartGame(boolean restartGame) {auth.restartGame = restartGame;}
    private static boolean restartGame = false;
    private static boolean moveHero = true;
    private static boolean hasHeroMoved = false;
    private static boolean hasHeroReached = false;
    private static boolean heroDropped = false;
    private static boolean heroUp = true;
    private static boolean moveScreen = false;
    private static boolean createStick = true;
    private static boolean createdStick = false;
    public static int getHighScoreReached() {return highScoreReached;}
    public static void setHighScoreReached() {auth.highScoreReached +=1;}
    private static int highScoreReached = 0;
    public static boolean isHeroDropped() {
        return heroDropped;
    }
    public static void setHeroDropped(boolean heroDropped) {
        auth.heroDropped = heroDropped;
    }
    public static void setMoveScreen(boolean moveScreen) {auth.moveScreen = moveScreen;}
    public static void setMoveHero(boolean moveHero) {auth.moveHero = moveHero;}
    public static void setCreateStick(boolean createStick) {auth.createStick = createStick;}
    public static void setHasHeroMoved(boolean hasHeroMoved) {auth.hasHeroMoved = hasHeroMoved;}
    public static void setCreatedStick(boolean createdStick) {auth.createdStick = createdStick;}
    public static void setHasHeroReached(boolean hasHeroReached) {auth.hasHeroReached = hasHeroReached;}
    public static void setHeroUp(boolean heroUp) {auth.heroUp = heroUp;}
    public static void setGameLoop() {auth.gameLoop += 1;}
    public static void setPwd(String pwd) {auth.pwd = pwd;}
    public static void setUserid(String userid) {auth.userid = userid;}
    public static int getGameLoop() {return gameLoop;}
    public static String getPwd() {return pwd;}
    public static String getUserid() {return userid;}
    public static boolean isCreatedStick() {return createdStick;}
    public static boolean isCreateStick() {return createStick;}
    public static boolean isHasHeroReached() {return hasHeroReached;}
    public static boolean isMoveScreen() {return moveScreen;}
    public static boolean isMoveHero() {return moveHero;}
    public static boolean isHasHeroMoved() {return hasHeroMoved;}
    public static boolean isHeroUp() {return heroUp;}
    public static void setDefault(){
        moveHero = true;
        moveScreen = false;
        createStick = true;
        createdStick = false;
        hasHeroReached = false;
        hasHeroMoved = false;
        heroDropped = false;
        restartGame = false;
    }
}
