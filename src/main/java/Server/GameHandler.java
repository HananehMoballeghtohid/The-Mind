package Server;

import Game.Game;
import Model.Player.Bot;
import Model.Player.Human;
import Model.Player.Player;

import java.util.ArrayList;

public class GameHandler {
    private final int numberOfPlayers;
    private Game game;
    private final ArrayList<ClientHandler> clientHandlers;
    private final ArrayList<Player> players;
    private boolean isFull;

    GameHandler(int numberOfPlayers){
        this.numberOfPlayers=numberOfPlayers;
        clientHandlers=new ArrayList<>();
        players = new ArrayList<>();
        isFull=false;
    }

    public void addPlayer(ClientHandler clientHandler){
        if (!isFull) {
            clientHandlers.add(clientHandler);
            Player player = new Human();
            players.add(player);
            if (clientHandlers.size()==numberOfPlayers){
                isFull=true;
            }
        }
    }

    public void startGame(){
        int numberOfHumans = clientHandlers.size();
        if (numberOfHumans<numberOfPlayers){
            for (int i=0; i<numberOfPlayers-numberOfHumans;i++){
                Player bot = new Bot();
                players.add(bot);
            }
        }
        game = new Game(players);
    }

    public boolean isFull(){
        return isFull;
    }

    public Game getGame() {
        return game;
    }
}
