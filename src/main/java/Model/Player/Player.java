package Model.Player;

import Model.Card.NumberCard;

import java.util.ArrayList;

public abstract class Player {


    ArrayList<NumberCard> hand;

    public ArrayList<NumberCard> getHand() {
        return hand;
    }

    public void setHand(ArrayList<NumberCard> hand) {
        this.hand = hand;
    }
}
