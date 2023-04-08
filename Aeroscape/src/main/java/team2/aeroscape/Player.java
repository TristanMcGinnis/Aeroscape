package team2.aeroscape;

public class Player {
    private int x;
    private int y;
    private int velX;
    private int velY;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.velX = 0;
        this.velY = 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getVelX() {
        return velX;
    }

    public int getVelY() {
        return velY;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }
    
    public void stopX() {
        this.velX = 0;
    }

    public void stopY() {
        this.velY = 0;
    }

    public void update() {
        x += velX;
        y += velY;
    }
}