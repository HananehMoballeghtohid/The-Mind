package Game;

import Model.Player.Player;

import java.util.ArrayList;

public class Game {

    GameState gameState;

    public Game(ArrayList<Player> players){
        gameState = new GameState(players);
    }

    public void nextLevel(){
        gameState.setLevel(gameState.getLevel()+1);
        //todo get cards
    }

    public void reduceLive(){
        gameState.setLives(gameState.getLives()-1);
    }


}
