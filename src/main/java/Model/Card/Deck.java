package Model.Card;
import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    ArrayList<NumberCard> deck;

    public Deck() {
        deck = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            deck.add(new NumberCard(i + 1));
        }
        Collections.shuffle(deck);
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    public ArrayList<NumberCard> getDeck() {
        return deck;
    }

    public void setDeck(ArrayList<NumberCard> deck) {
        this.deck = deck;
    }

}
