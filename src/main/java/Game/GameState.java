package Game;

import Model.Card.Deck;
import Model.Player.Player;

import java.util.ArrayList;

public class GameState {

    private ArrayList<Player> players;

    private int lives;

    private int ninjas;

    private int level;

    private Deck deck;

    public GameState(ArrayList<Player> players){
        this.players =players;
        lives = players.size();
        deck = new Deck();
        ninjas=2;
        level=1;
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
}
