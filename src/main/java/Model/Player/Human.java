package Model.Player;

import Model.Card.NumberCard;
import Server.ClientHandler;

public class Human extends Player implements Runnable {
    private final ClientHandler clientHandler;

    public Human(ClientHandler clientHandler) {
        this.clientHandler=clientHandler;
    }


    @Override
    public NumberCard play() {
        NumberCard card = getHand().get(0);
        getHand().remove(0);
        return card;
    }
    @Override
    public String toString() {
        return "your cards: " + hand.toString();
    }

    @Override
    public void run() {

    }
}
