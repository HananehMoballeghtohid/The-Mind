package Model.Player;

import Model.Card.NumberCard;
import Server.Connection;

import java.util.ArrayList;
import java.util.TreeMap;

public abstract class Player extends Thread {

    private int id;

    private static int nextId;

    ArrayList<NumberCard> hand;

    public ArrayList<NumberCard> getHand() {
        return hand;
    }

    public void setHand(ArrayList<NumberCard> hand) {
        this.hand = hand;
    }

    public Player(){
        id=getNextId();
    }

    private int getNextId(){
        nextId++;
        return nextId;
    }

    public int getPlayerId(){
        return id;
    }

    @Override
    public abstract void run();

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
    public NumberCard getCardFromHand(int i) {
        return hand.get(i);
    }
    public int getHandSize() {
        return hand.size();
    }
}
