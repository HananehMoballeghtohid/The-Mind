package Model.Player;

import Model.Card.NumberCard;

public class Human extends Player {

    @Override
    public NumberCard play() {
        NumberCard card = getHand().get(0);
        getHand().remove(0);
        return card;
    }
}
