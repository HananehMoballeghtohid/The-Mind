package Game;
import Model.Card.NumberCard;
import Model.Player.Bot;
import Model.Player.Player;
import java.util.ArrayList;

public class Game {

    private final GameState gameState;
    private int gameNumber;

    /**
     * the variable is going to let
     * bots know if they are playing as
     * the first player in the round
     * or someone played before them.
     */
    private boolean isFirstPlay = true;

    public GameState getGameState() {
        return gameState;
    }

    public Game(ArrayList<Player> players){
        gameState = new GameState(players);
        int level = 1;
        gameState.setLevel(level);
        setHands(level);
        addLife(level);
        addNinjas(level);
    }
    public void nextLevel(){
        int level = gameState.getLevel() + 1;
        gameState.setLevel(level);
        setHands(level);
        addLife(level);
        addNinjas(level);
    }

    public void startPlayers() {
        for (Player player : gameState.getPlayers()) {
            if (player instanceof Bot) {
                ((Bot) player).setTime();
            }
            player.start();
        }
    }

    public void reduceLive(){
        gameState.setLives(gameState.getLives() - 1);
    }

    public void setHands(int level) {
        for (Player player : gameState.getPlayers()) {
            ArrayList<NumberCard> hand = new ArrayList<>();
            gameState.getDeck().shuffle();
            for (int i = 0; i < level; i++) {
                hand.add(gameState.getDeck().getCard(0));
                gameState.getDeck().remove(0);
            }
            player.setHand(hand);
            player.sortHand();
        }
    }

    public boolean isFinished() {
        if (gameState.getLives() == 0) {
            return true;
        }
        return gameState.getLevel() > 12;
    }

    public synchronized void playCard(NumberCard card) {
        gameState.addToPlayedCards(0, card);
        boolean correctCard = true;
        for (Player player : gameState.getPlayers() ){
            if (player instanceof Bot) {
                player.interrupt();
            }
            if (player.getHandSize() > 0) {
                if (player.getCardFromHand(0).getNumber() < card.getNumber()){
                    correctCard = false;
                    int numberOfLowers = 0;
                    for (NumberCard lowerCard : player.getHand()){
                        if (lowerCard.getNumber() < card.getNumber()){
                            numberOfLowers++;
                        }
                    }
                    for (int i = 0; i < numberOfLowers; i++){
                        gameState.addToPlayedCards(0, player.play());
                    }
                }
            }
        }
        if (!correctCard){
            reduceLive();
        }
        isFirstPlay = false;
        if (gameState.totalCardsInGame()==0) {
            isFirstPlay = true;
            nextLevel();
        }
    }

    private void addLife(int level) {
        if (level == 3 ||
        level == 6 || level == 9) {
            gameState.setLives(gameState.getLives() + 1);
        }
    }

    private void addNinjas(int level) {
        if (level == 2 ||
                level == 5 || level == 8) {
            gameState.setNinjas(gameState.getNinjas() + 1);
        }
    }

    public void playNinja() {
        NumberCard max = new NumberCard(0);
        for (Player player : gameState.getPlayers()) {
            NumberCard played = new NumberCard(-1);
            if (player.getHandSize() != 0) {
                played = player.play();
                gameState.addToPlayedCards(played);
            }
            if (played.getNumber() > max.getNumber()) {
                max = played;
            }
        }
        for (Player player : gameState.getPlayers()){
            if (player.getHandSize() != 0) {
                if (player.getCardFromHand(0).getNumber() < max.getNumber()){
                    for (NumberCard playerCard : player.getHand()){
                        if (playerCard.getNumber() < max.getNumber()){
                            gameState.addToPlayedCards(0, playerCard);
                        }
                    }
                    player.getHand().removeAll(gameState.getPlayedCards());
                }
            }
        }
        gameState.setNinjas(gameState.getNinjas() - 1);
        isFirstPlay = false;
        if (gameState.totalCardsInGame()==0) {
            isFirstPlay = true;
            nextLevel();
        }
    }

    public int getGameNumber() {
        return gameNumber;
    }

    public void setGameNumber(int gameNumber) {
        this.gameNumber = gameNumber;
    }

    public boolean isFirstPlay() {
        return isFirstPlay;
    }
}
