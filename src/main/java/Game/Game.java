package Game;
import Model.Card.NumberCard;
import Model.Player.Player;
import java.util.ArrayList;

public class Game {

    GameState gameState;

    public GameState getGameState() {
        return gameState;
    }

    public Game(ArrayList<Player> players){
        gameState = new GameState(players);
        nextLevel();
    }

    public void nextLevel(){
        int level = gameState.getLevel() + 1;
        gameState.setLevel(level);
        setHands(level);
        addLife(level);
        addNinjas(level);
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

    public void playCard(NumberCard card){
        gameState.addToPlayedCards(0, card);
        boolean correctCard = true;
        for (Player player:gameState.getPlayers()){
            if (player.getCardFromHand(0).getNumber()<card.getNumber()){
                correctCard = false;
                for (NumberCard lowerCard:player.getHand()){
                    if (lowerCard.getNumber()<card.getNumber()){
                        gameState.addToPlayedCards(card);
                    }
                }
                player.getHand().removeAll(gameState.getPlayedCards());
            }
        }
        if (!correctCard){
            reduceLive();
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

    private void playNinja() {
        NumberCard max = new NumberCard(0);
        for (Player player : gameState.getPlayers()) {
            NumberCard played = player.play();
            gameState.addToPlayedCards(played);
            if (played.getNumber() > max.getNumber()) {
                max = played;
            }
        }
        for (Player player : gameState.getPlayers()){
            if (player.getCardFromHand(0).getNumber() < max.getNumber()){
                for (NumberCard playerCard : player.getHand()){
                    if (playerCard.getNumber() < max.getNumber()){
                        gameState.addToPlayedCards(playerCard);
                    }
                }
                player.getHand().removeAll(gameState.getPlayedCards());
            }
        }
    }
}
