package Server;

import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket ;
    private final Connection connection;
    private final int id ;
    private boolean host;
    private final Server server;
    private String name;
    private final String token;

    public ClientHandler(Socket socket, int id , Server server) throws IOException {
        this.connection = new Connection(socket);
        this.socket = socket;
        this.id = id;
        host = false;
        this.server = server;
        token = server.getAuthTokenGenerator().nextToken();
    }

    @Override
    public void run() {
        System.out.println("Client Handler is running...");
        connection.send(new Message("enter your name: ", null));
        setName();
        ClientToGame();


    }

    private void setName(){
        name = connection.receive();
        System.out.println("New Client's name is " + name);
        System.out.println("New Client's token is: " + token);
    }

    private void ClientToGame(){
        boolean incorrectAnswer = true;
        while (incorrectAnswer){
            connection.send(new Message("Your token is: " + token + "   1.Create new Game. 2.Join a game.", token));
            String inputFromClient = connection.receive();
            switch (inputFromClient) {
                case "1" :
                    boolean invalidInput = true;
                    while (invalidInput) {
                        connection.send(new Message("Enter number of players: ", token));
                        String inputNumberOfPlayers = connection.receive();
                        try {
                            int numberOfPlayers = Integer.parseInt(inputNumberOfPlayers);
                            setHost();
                            GameHandler gameHandler = new GameHandler(numberOfPlayers);
                            server.addGame(gameHandler);
                            gameHandler.addPlayer(this);
                            connection.send(new Message("Game created successfully. ", token));
                            System.out.println("new Game Created by " + token + " number of players for this game: " + numberOfPlayers);
                            invalidInput = false;
                        } catch (NumberFormatException e) {
                            connection.send(new Message("Invalid input!", token));
                        }
                    }
                    incorrectAnswer = false;
                    break;
                case "2":
                    boolean availableGame = false;
                    GameHandler availableGameHandler = null;
                    if (server.getGameHandlers().size() != 0) {
                        for (GameHandler gameHandler : server.getGameHandlers()) {
                            if (!gameHandler.isFull()) {
                                availableGame = true;
                                availableGameHandler = gameHandler;
                                break;
                            }
                        }
                    }
                    if (availableGame) {
                        availableGameHandler.addPlayer(this);
                        connection.send(new Message("You successfully joined a game.", token));
                        System.out.println(token + " joined game number " + server.getGameHandlers().indexOf(availableGameHandler));
                        incorrectAnswer = false;
                    } else {
                        connection.send(new Message("There is no available game.", token));
                    }
                    break;
                default:
                    connection.send(new Message("Invalid input!", token));
                    break;
            }
        }
    }

    public void setHost(){
        host = true;
    }

}
