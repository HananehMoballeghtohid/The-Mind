package Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private final Socket socket ;
    private final PrintWriter out;
    private final Scanner in;
    private final int id ;
    private boolean host;
    private final Server server;
    private String name;

    public ClientHandler(Socket socket, int id , Server server) throws IOException {
        this.socket = socket;
        this.id = id;
        out = new PrintWriter(socket.getOutputStream());
        host=false;
        this.server= server;
        in = new Scanner(socket.getInputStream());
    }

    @Override
    public void run() {
        System.out.println("Client Handler is running...");
        sendMessage("Enter your name: ");
        setName();
        ClientToGame();


    }

    private void setName(){
        name = in.nextLine();
        System.out.println("New Client's name is " + name);
        //TODO TOKEN
    }

    private void ClientToGame(){
        boolean incorrectAnswer = true;
        while (incorrectAnswer){
            sendMessage("What do you want to do?" + "\n 1.Create new Game." + "\n 2.Join a game.");
            String inputFromClient = in.nextLine();
            switch (inputFromClient){
                case "1" :
                    boolean invalidInput = true;
                    while (invalidInput){
                        sendMessage("Enter number of players: ");
                        String inputNumberOfPlayers = in.nextLine();
                        try {
                            int numberOfPlayers = Integer.parseInt(inputNumberOfPlayers);
                            setHost();
                            GameHandler gameHandler = new GameHandler(numberOfPlayers);
                            server.addGame(gameHandler);
                            gameHandler.addPlayer(this);
                            sendMessage("Game Created Successfully.");
                            System.out.println("new Game Created by " + name + " number of players for this game: " + numberOfPlayers);
                            invalidInput = false;
                        }
                        catch (NumberFormatException e){
                            sendMessage("Invalid Input!");
                        }
                    }
                    incorrectAnswer=false;
                    break;
                case "2":
                    boolean availableGame = false;
                    GameHandler availableGameHandler = null;
                    if (server.getGameHandlers().size() != 0 ){
                        for (GameHandler gameHandler:server.getGameHandlers()){
                            if (!gameHandler.isFull()){
                                availableGame = true;
                                availableGameHandler = gameHandler;
                                break;
                            }
                        }
                    }
                    if (availableGame){
                        availableGameHandler.addPlayer(this);
                        sendMessage("You successfully joined a game.");
                        System.out.println(name + " joined game number "+ server.getGameHandlers().indexOf(availableGameHandler));
                        incorrectAnswer=false;
                    }
                    else {
                        sendMessage("There is no available game. ");
                    }
                    break;
                default:
                    sendMessage("Invalid Input!");
            }
        }
    }

    public void setHost(){
        host=true;
    }

    public void sendMessage(String message){
        out.println(message);
        out.flush();
    }
}
