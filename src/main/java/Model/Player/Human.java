package Model.Player;

import Model.Card.NumberCard;
import Server.ClientHandler;
import Server.Connection;
import Server.GameInterface;
import Server.Message;

public class Human extends Player {

    private final ClientHandler clientHandler;
    private GameInterface gameInterface;

    public Human(ClientHandler clientHandler) {
        super();
        this.clientHandler=clientHandler;
    }


    @Override
    public synchronized NumberCard play() {
        NumberCard card = getHand().get(0);
        getHand().remove(0);
        return card;
    }

    @Override
    public String toString() {
        return "your cards: " + hand.toString();
    }

    @Override
    public void run() {
        try {
            while (clientHandler.getConnection().isOpen()) {
                String input = getMessageContent(new Message(clientHandler.getConnection().receive()));
                System.out.println(input);
                switch (input) {
                    case "1" :
                        if (!hand.isEmpty()) {
                            gameInterface.getSemaphore().acquire();
                            gameInterface.sendPlayerPlayed(this);
                            System.out.println(clientHandler.getName() + " from game" +
                                    clientHandler.getGameHandler().getGame().getGameNumber() + " played.");
                            gameInterface.getGame().playCard(play());
                            gameInterface.runGame();
                        }
                        else {
                            clientHandler.getConnection().send(new Message("You don't have any cards left."
                                            ,clientHandler.getToken(),"1"));
                        }
                        break;
                    case "2" :
                        if (gameInterface.getGame().getGameState().getNinjas() != 0) {
                            System.out.println(clientHandler.getName() + " from game" +
                                    clientHandler.getGameHandler().getGame().getGameNumber() + " played.");
                            gameInterface.getGame().playNinja();
                            gameInterface.runGame();
                        } else {
                            clientHandler.getConnection().send(new Message("There are no Ninja cards."
                                    ,clientHandler.getToken(),"1"));
                        }
                        break;
                    case "3":
                        clientHandler.getConnection().send(new Message("Enter the emoji you want to send."
                                ,clientHandler.getToken(),"1"));
                        input =  getMessageContent(new Message(clientHandler.getConnection().receive()));
                        switch (input){
                            case ":)":
                            case ":D":
                            case ":|":
                            case "<3":
                            case ":/":
                            case ":(":
                                clientHandler.getConnection().send(new Message("Emoji sent!"
                                        ,clientHandler.getToken(),"1"));
                                clientHandler.getGameHandler().MessageToAllClients(input,clientHandler.getName());
                                System.out.println(clientHandler.getName() + " said " + input);
                                break;
                            default:
                                clientHandler.getConnection().send(new Message("you just can send simple emojis!"
                                        ,clientHandler.getToken(),"1"));
                                break;
                        }
                        break;
                    default:
                        clientHandler.getConnection().send(new Message("Invalid input!"
                                , clientHandler.getToken(),
                                "1"));
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private String getMessageContent(Message message){
        if (message.getAuthToken().equals(clientHandler.getToken())){
            return message.getContent();
        }
        return "";
    }

    public void setGameInterface(GameInterface gameInterface) {
        this.gameInterface = gameInterface;
    }
}
