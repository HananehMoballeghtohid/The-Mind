package Game;
import Model.Card.NumberCard;
import Model.Player.Player;
import java.util.ArrayList;

public class Game {

    GameState gameState;

    public Game(ArrayList<Player> players){
        gameState = new GameState(players);
    }

    public void nextLevel(){
        gameState.setLevel(gameState.getLevel()+1);
        setHands(gameState.getLevel());
    }

    public void reduceLive(){
        gameState.setLives(gameState.getLives()-1);
    }

    public void setHands(int level) {
        for (Player player : gameState.getPlayers()) {
            ArrayList<NumberCard> hand = new ArrayList<>();
            gameState.getDeck().shuffle();
            for (int i = 0; i < level; i++) {
                hand.add(gameState.getDeck().getDeck().get(0));
                gameState.getDeck().getDeck().remove(0);
            }
            player.setHand(hand);
            player.sortHand();
        }
    }

    public boolean isContinued(){
        if(gameState.getLives()==0){
            return false;
        }
        return gameState.getDeck().getDeck().size() >= gameState.getPlayers().size() * gameState.getLevel();
    }

    public void playCard(NumberCard card){
        gameState.getPlayedCards().add(0,card);
        boolean correctCard = true;
        for (Player player:gameState.getPlayers()){
            if (player.getHand().get(0).getNumber()<card.getNumber()){
                correctCard = false;
                for (NumberCard lowerCard:player.getHand()){
                    if (lowerCard.getNumber()<card.getNumber()){
                        gameState.getPlayedCards().add(lowerCard);
                    }
                }
                player.getHand().removeAll(gameState.getPlayedCards());
            }
        }
        if (!correctCard){
            reduceLive();
        }
    }

    

}
