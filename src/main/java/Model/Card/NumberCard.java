package Model.Card;

import Model.Card.Card;
import Model.Player.Player;

public class NumberCard extends Card {

    int number;
    Player player;

    public NumberCard(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
