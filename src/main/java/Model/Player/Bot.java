package Model.Player;

import Model.Card.NumberCard;

public class Bot extends Player {

    @Override
    public synchronized NumberCard play() {
        //TODO add time
        NumberCard card = getHand().get(0);
        getHand().remove(0);
        return card;
    }

    @Override
    public void run() {
        //TODO
    }
}
