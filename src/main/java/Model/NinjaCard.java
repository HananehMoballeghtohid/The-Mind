package Model;

public class NinjaCard {

    boolean isUsed;
    Player player;

    public NinjaCard() {
        isUsed = false;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
