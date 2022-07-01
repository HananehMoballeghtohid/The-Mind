package Server;

import Game.Game;
import Model.Player.Human;
import Model.Player.Player;

import java.util.HashMap;
import java.util.concurrent.Semaphore;

public class GameInterface {
    private final Semaphore semaphore = new Semaphore(1);
    private final Game game;
    private final GameHandler gameHandler;
    private final HashMap<Player,ClientHandler> humans;

    public GameInterface(Game game, GameHandler gameHandler, HashMap<Player, ClientHandler> humans) {
        this.game = game;
        this.gameHandler = gameHandler;
        this.humans = humans;
    }

    /** sends a message to all human players
     * to get their commands and then run player threads to
     * wait until somebody (or a bot) play.
     */
    public void runGame() {
        if (game.isFinished()) {
            if (game.getGameState().getLives() == 0) {
                for (Player player : humans.keySet()) {
                    if (player instanceof Human) {
                        ClientHandler clientHandler = humans.get(player);
                        clientHandler.getConnection().send(new Message(
                                "you lost!" , clientHandler.getToken(), "0"));
                        clientHandler.getConnection().close();
                    }
                }
            } else {
                for (Player player : humans.keySet()) {
                    if (player instanceof Human) {
                        ClientHandler clientHandler = humans.get(player);
                        clientHandler.getConnection().send(new Message(
                                "you won!" , clientHandler.getToken(), "0"));
                        clientHandler.getConnection().close();
                    }
                }
            }
        }
        for (Player player : getGame().getGameState().getPlayers()) {
            if (player instanceof Human) {
                ClientHandler clientHandler = humans.get(player);
                sendState(player);
                clientHandler.getConnection().send(
                        new Message("what you want to do? "
                                + "1.play smallest card " + " 2.play ninja" + " 3.send emoji",
                                clientHandler.getToken(),
                                "1"));
                ((Human) player).setGameInterface(this);
            }
            semaphore.release(1);
        }
    }

    private void sendState(Player player) {
        humans.get(player).getConnection().send(
                new Message(game.getGameState().toString(),
                        humans.get(player).getToken(),
                        "0"));
        humans.get(player).getConnection().send(
                new Message(game.getGameState().NumberOfCardsForAllPlayers(),
                        humans.get(player).getToken(),
                        "0"));
        humans.get(player).getConnection().send(
                new Message(player.toString(),
                        humans.get(player).getToken(),
                        "0"));
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }

    public Game getGame() {
        return game;
    }

    public void sendPlayerPlayed(Player played) {
        String content;
        if (played instanceof Human) {
            content = "player " + humans.get(played).getName() + " played.";
        } else {
            content = "bot " + game.getGameState().getPlayers().indexOf(played) + " played.";
        }
        for (Player player : humans.keySet()) {
            ClientHandler clientHandler = humans.get(player);
            clientHandler.getConnection().send(new Message(content, clientHandler.getToken(), "0"));
        }
    }

    public HashMap<Player, ClientHandler> getHumans() {
        return humans;
    }
}
