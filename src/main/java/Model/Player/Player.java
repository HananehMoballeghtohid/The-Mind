package Model.Player;

import Model.Card.NumberCard;
import java.util.ArrayList;
import java.util.TreeMap;

public abstract class Player {


    ArrayList<NumberCard> hand;

    public ArrayList<NumberCard> getHand() {
        return hand;
    }

    public void setHand(ArrayList<NumberCard> hand) {
        this.hand = hand;
    }

    public abstract NumberCard play();

    public void sortHand() {
        TreeMap<Integer, NumberCard> treeMap = new TreeMap<>();
        for (NumberCard card : hand) {
            treeMap.put(card.getNumber(), card);
        }
        hand.clear();
        for (int i : treeMap.keySet()) {
            hand.add(treeMap.get(i));
        }
    }

}
