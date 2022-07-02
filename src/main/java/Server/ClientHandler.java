package Server;

import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Connection connection;
    private GameHandler gameHandler;
    private boolean host;
    private final Server server;
    private String name;
    private final String token;

    public ClientHandler(Socket socket, Server server) throws IOException {
        this.connection = new Connection(socket);
        host = false;
        this.server = server;
        token = server.getAuthTokenGenerator().nextToken();
    }

    @Override
    public void run() {
        System.out.println("Client Handler is running...");
        connection.send(new Message("enter your name:  ", token,"1"));
        setName();
        ClientToGame();
        waitForStart();
    }

    private void setName(){
        name = getMessageContent(new Message(connection.receive()));
        System.out.println("New Client's name is " + name);
        System.out.println("New Client's token is: " + token);
    }

    private void ClientToGame(){
        boolean incorrectAnswer = true;
        while (incorrectAnswer){
            connection.send(new Message("Your token is: " + token + "  1.Create new Game. 2.Join a game.", token , "1"));
            String inputFromClient = getMessageContent(new Message(connection.receive()));
            switch (inputFromClient) {
                case "1" -> {
                    boolean invalidInput = true;
                    while (invalidInput) {
                        connection.send(new Message("Enter number of players: ", token, "1"));
                        String inputNumberOfPlayers = getMessageContent(new Message(connection.receive()));
                        try {
                            int numberOfPlayers = Integer.parseInt(inputNumberOfPlayers);
                            setHost();
                            this.gameHandler = new GameHandler(numberOfPlayers, server.getGameHandlers().size());
                            server.addGame(gameHandler);
                            gameHandler.addPlayer(this);
                            connection.send(new Message("Game created successfully. ", token, "0"));
                            System.out.println("new Game Created by " + token + " number of players for this game: " + numberOfPlayers);
                            invalidInput = false;
                        } catch (NumberFormatException e) {
                            connection.send(new Message("Invalid input!", token, "0"));
                        }
                    }
                    incorrectAnswer = false;
                }
                case "2" -> {
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
                        this.gameHandler = availableGameHandler;
                        gameHandler.MessageToHost(name + " joined to the game.");
                        connection.send(new Message("You successfully joined a game.", token, "0"));
                        System.out.println(token + " joined game number " + server.getGameHandlers().indexOf(availableGameHandler));
                        incorrectAnswer = false;
                    } else {
                        connection.send(new Message("There is no available game.", token, "0"));
                    }
                }
                default -> connection.send(new Message("Invalid input!", token, "0"));
            }
        }
    }

    private void waitForStart(){
        connection.send(new Message("waiting for others to join...",token,"0"));
        if (isHost()){
            connection.send(new Message("to start the game enter 1",token,"1"));
            boolean invalidInput=true;
            while (invalidInput) {
                String input = getMessageContent(new Message(connection.receive()));
                if ("1".equals(input)) {
                    gameHandler.startGame();
                    System.out.println("the game is started by " + token);
                    invalidInput = false;
                } else {
                    connection.send(new Message("Invalid input!", token, "0"));
                }
            }

        }
    }

    private String getMessageContent(Message message){
        if (message.getAuthToken().equals(token)){
            return message.getContent();
        }
        return "";
    }

    public void setHost(){
        host = true;
    }

    public boolean isHost(){
        return host;
    }

    public Connection getConnection() {
        return connection;
    }

    public GameHandler getGameHandler() {
        return gameHandler;
    }

    public String getName() {
        return name;
    }

    public String getToken() {
        return token;
    }
}
