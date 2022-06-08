package Model;

public class LevelCard extends Card {

    private static int gameLevel;

    private static LevelCard levelCard;

    private LevelCard() {
        gameLevel = 1;
    }
    public static LevelCard getInstance() {
        return levelCard;
    }

    public static int getGameLevel() {
        return gameLevel;
    }
    public static void nextLevel() {
        gameLevel++;
    }

}
