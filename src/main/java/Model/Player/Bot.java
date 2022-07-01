package Model.Player;

import Model.Card.NumberCard;
import Server.GameInterface;

public class Bot extends Player {

    private GameInterface gameInterface;
    private int id;
    private int waitingTime;

    public Bot(int id){
        super();
        this.id = id;
    }

    @Override
    public synchronized NumberCard play() {
        NumberCard card = getHand().get(0);
        getHand().remove(0);
        return card;
    }

    @Override
    public void run() {
        while (true) {
            try {
                sleep(waitingTime);
                break;
            } catch (InterruptedException e) {
                setTime();
            }
        }
        if (!hand.isEmpty()) {
            try {
                gameInterface.getSemaphore().acquire();
                System.out.println( "bot" + id + " from game" +
                        gameInterface.getGame().getGameNumber() + " played.");
                gameInterface.sendPlayerPlayed(this);
                gameInterface.getGame().playCard(play());
                gameInterface.runGame();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setGameInterface(GameInterface gameInterface) {
        this.gameInterface = gameInterface;
    }

    public void setTime() {
        int dif;
        if (!gameInterface.getGame().isFirstPlay()) {
            dif = hand.get(0).getNumber() - gameInterface.getGame().getGameState().getLastPlayerCard();
        } else {
            dif = hand.get(0).getNumber();
        }
        waitingTime = dif*(500);
        System.out.println(waitingTime);
    }
}
