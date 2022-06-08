package Model;

public class HeartCard extends Card {

    boolean isDead;

    public HeartCard() {
        isDead = false;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }
}
