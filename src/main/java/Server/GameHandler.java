package Server;

import Game.Game;
import Model.Player.Bot;
import Model.Player.Human;
import Model.Player.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class GameHandler {
    private final int numberOfPlayers;
    private Game game;
    private final ArrayList<ClientHandler> clientHandlers;
    private final HashMap<Player,ClientHandler> players;
    private boolean isFull;

    GameHandler(int numberOfPlayers){
        this.numberOfPlayers = numberOfPlayers;
        clientHandlers = new ArrayList<>();
        players = new HashMap<>();
        isFull = false;
    }

    public void addPlayer(ClientHandler clientHandler){
        if (!isFull) {
            clientHandlers.add(clientHandler);
            Player player = new Human();
            players.put(player,clientHandler);
            if (clientHandlers.size()==numberOfPlayers){
                isFull = true;
            }
        }
    }

    public void startGame(){
        int numberOfHumans = clientHandlers.size();
        if (numberOfHumans < numberOfPlayers){
            for (int i = 0; i < numberOfPlayers - numberOfHumans; i++){
                Player bot = new Bot();
                players.put(bot,null);
            }
        }
        game = new Game(new ArrayList<>(players.keySet()));
        for (ClientHandler clientHandler:clientHandlers){
            Connection connection = clientHandler.getConnection();
            connection.send(new Message("The game is Started.", clientHandler.getToken()));
        }
    }

    public void MessageToHost(String message){
        ClientHandler host = null;
        for (ClientHandler m:clientHandlers){
            if (m.isHost()){
                host=m;
            }
        }
        Connection connection = host.getConnection();
        connection.send(new Message(message,host.getToken()));
    }

    public void MessageToAnotherClient(String message , String receiverName , String senderName){
        ClientHandler receiver = null;
        for (ClientHandler m:clientHandlers){
            if (m.getName().equals(receiverName)){
                receiver=m;
            }
        }
        if (receiver != null) {
            Connection connection = receiver.getConnection();
            connection.send(new Message(senderName + ": " + message, receiver.getToken()));
        }
    }

    public boolean isFull(){
        return isFull;
    }

    public Game getGame() {
        return game;
    }
}
