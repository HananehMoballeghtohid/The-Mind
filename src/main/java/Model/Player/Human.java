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
            while (true) {
                String input = getMessageContent(new Message(clientHandler.getConnection().receive()));
                System.out.println(input);
                switch (input) {
                    case "1" :
                        gameInterface.getSemaphore().acquire();
                        gameInterface.sendPlayerPlayed(this);
                        System.out.println(clientHandler.getName() + " from game" +
                                clientHandler.getGameHandler().getGame().getGameNumber() + " played.");
                        gameInterface.getGame().playCard(play());
                        break;
                    case "2" :
                        //play ninja;
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
