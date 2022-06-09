package Game;

import Model.Card.Deck;
import Model.Card.NumberCard;
import Model.Player.Player;

import java.util.ArrayList;

public class GameState {

    private ArrayList<Player> players;

    private int lives;

    private int ninjas;

    private int level;

    private Deck deck;

    private ArrayList<NumberCard> playedCards;

    public GameState(ArrayList<Player> players){
        this.players =players;
        lives = players.size();
        deck = new Deck();
        ninjas=2;
        level=1;
        playedCards = new ArrayList<>();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getNinjas() {
        return ninjas;
    }

    public void setNinjas(int ninjas) {
        this.ninjas = ninjas;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Deck getDeck() {
        return deck;
    }

    public ArrayList<NumberCard> getPlayedCards() {
        return playedCards;
    }

    @Override
    public String toString() {
        return "Game state:" +
                "\nlives: " + lives +
                "\nninjas: " + ninjas +
                "\nlevel: " + level +
                "\nleft cards: " + deck.getDeck().size() +
                // players.get(0) represents human
                "\nyour cards: " + getPlayers().get(0).getHand() +
                "\nplayed cards: " + playedCards
                ;
    }
}
