package Model.Card;
import Model.Player.Player;

public class NumberCard {

    int number;

    public NumberCard(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return String.valueOf(number);
    }
}
