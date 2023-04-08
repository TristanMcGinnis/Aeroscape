package team2.aeroscape;

public class Inventory {
    private int iron;
    private int copper;
    private int gold;

    public Inventory() {
        iron = 0;
        copper = 0;
        gold = 0;
    }

    public int getIron() {
        return iron;
    }

    public void addIron(int amount) {
        iron += amount;
    }

    public int getCopper() {
        return copper;
    }

    public void addCopper(int amount) {
        copper += amount;
    }

    public int getGold() {
        return gold;
    }

    public void addGold(int amount) {
        gold += amount;
    }
}
