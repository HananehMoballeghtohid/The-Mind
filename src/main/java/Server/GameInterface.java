package Server;

import Game.Game;
import Model.Player.Human;
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
        for (Player player:players.keySet()) {
            if (player instanceof Human) {
                ClientHandler clientHandler = players.get(player);
                clientHandler.getConnection().send(new Message(game.getGameState().toString(), clientHandler.getToken()));
                clientHandler.getConnection().receive();
                clientHandler.getConnection().send(new Message(player.toString(), clientHandler.getToken()));
                clientHandler.getConnection().receive();

            }
        }
    }

    private void sendState(ClientHandler clientHandler){

    }


}
