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
                clientHandler.getConnection().send(new Message(game.getGameState().toString(), clientHandler.getToken(),"0"));
                clientHandler.getConnection().send(new Message(player.toString(), clientHandler.getToken(),"0"));
                clientHandler.getConnection().send(new Message("what you want to do ?"
                                                                        + "1.play smallest card"
                                                                           + "  2.play ninja", clientHandler.getToken(),"0"));


            }
        }
    }

    private void sendState(ClientHandler clientHandler){

    }


}
