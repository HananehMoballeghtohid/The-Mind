package Game;
import Model.Card.Deck;
import Model.Card.NumberCard;
import Model.Player.Player;

import javax.smartcardio.Card;
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
        }
    }

}
