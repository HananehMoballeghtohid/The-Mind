package Server;

import Game.Game;
import Model.Player.Player;

import java.util.HashMap;

public class GameInterface {
    private final Game game;
    private final GameHandler gameHandler;
    private final HashMap<Player,ClientHandler> players;

    public GameInterface(Game game, GameHandler gameHandler, HashMap<Player, ClientHandler> players) {
        this.game = game;
        this.gameHandler = gameHandler;
        this.players = players;
    }

    public void runGame(){

    }
}
