package Game;

import Model.Card.Deck;
import Model.Card.NumberCard;
import Model.Player.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class GameState {

    private ArrayList<Player> players;

    private int lives;

    private int ninjas;

    private int level;

    private Deck deck;

    private final ArrayList<NumberCard> playedCards;

    public GameState(ArrayList<Player> players){
        this.players =players;
        lives = players.size();
        deck = new Deck();
        ninjas=2;
        level=0;
        playedCards = new ArrayList<>();
    }

    public ArrayList<Player> getPlayers() {
        return players;
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

    public void addToPlayedCards(int i, NumberCard card) {
        playedCards.add(i, card);
    }

    public void addToPlayedCards(NumberCard card) {
        playedCards.add(card);
    }

    public ArrayList<NumberCard> getPlayedCards() {
        return playedCards;
    }

    private HashMap<Player,Integer> playersNumberOfCards(){
        HashMap<Player,Integer> playersNumberOfCards = new HashMap<>();
        for (Player player:players){
            playersNumberOfCards.put(player,player.getHandSize());
        }
        return playersNumberOfCards;
    }

    public int totalCardsInGame(){
        HashMap<Player,Integer> playersNumberOfCards = playersNumberOfCards();
        int totalCardsInGame = 0;
        for (Player player:playersNumberOfCards.keySet()){
            totalCardsInGame += playersNumberOfCards.get(player);
        }
        return totalCardsInGame;
    }

    public String NumberOfCardsForAllPlayers(){
        StringBuilder numberOfCards = new StringBuilder("Players cards: ");
        HashMap<Player,Integer> playersNumberOfCards = playersNumberOfCards();
        for (Player player:playersNumberOfCards.keySet()){
            numberOfCards.append("Player ").append(player.getPlayerId()).append(": ").append(player.getHandSize()).append("  ");
        }
        return numberOfCards.toString();
    }

    @Override
    public String toString() {
        return "Game state:" +
                " lives: " + lives +
                " ninjas: " + ninjas +
                " level: " + level +
                " left cards: " + deck.getSize() +
                " played cards: " + playedCards
                ; // print player cards
    }
}
