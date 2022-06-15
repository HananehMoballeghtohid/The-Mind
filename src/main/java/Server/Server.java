package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private final ArrayList<ClientHandler> clientHandlers;
    private final ArrayList<GameHandler> gameHandlers;

    public Server(){
        clientHandlers = new ArrayList<>();
        gameHandlers = new ArrayList<>();
    }

    public void init() throws IOException {
        System.out.println("Server is Running");
        ServerSocket serverSocket = new ServerSocket(8000);
        while (true){
            System.out.println("Waiting for a connection");
            Socket socket = serverSocket.accept();
            ClientHandler clientHandler = new ClientHandler(socket,clientHandlers.size(),this);
            System.out.println("New connection Accepted.");
            clientHandlers.add(clientHandler);
            new Thread(clientHandler).start();

        }
    }

    public void addGame(GameHandler game){
        gameHandlers.add(game);
    }

    public ArrayList<GameHandler> getGameHandlers(){
        return gameHandlers;
    }
}
