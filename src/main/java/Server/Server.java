package Server;

import AuthenticationToken.AuthTokenGenerator;
import config.config;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private final ArrayList<ClientHandler> clientHandlers;
    private final ArrayList<GameHandler> gameHandlers;
    private AuthTokenGenerator authTokenGenerator = new AuthTokenGenerator();

    public Server(){
        clientHandlers = new ArrayList<>();
        gameHandlers = new ArrayList<>();
    }

    public void init() throws IOException {
        System.out.println("Server is Running");
        config config=new config();
        ServerSocket serverSocket = new ServerSocket(config.getPort());
        System.out.println("Waiting for a connection");
        while (true){
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

    public ArrayList<ClientHandler> getClientHandlers() {
        return clientHandlers;
    }

    public AuthTokenGenerator getAuthTokenGenerator() {
        return authTokenGenerator;
    }

    public void setAuthTokenGenerator(AuthTokenGenerator authTokenGenerator) {
        this.authTokenGenerator = authTokenGenerator;
    }
}
