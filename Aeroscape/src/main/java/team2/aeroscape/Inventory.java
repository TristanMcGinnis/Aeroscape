package team2.aeroscape;

public class Inventory {
    private int iron;
    private int copper;
    private int gold;
    private int coal;
    
    public Inventory() {
        iron = 0;
        copper = 0;
        gold = 0;
        coal = 0;
    }

    public int getIron() {
        return iron;
    }

    public void addIron(int amount) {
        System.out.println("Iron Added");
        iron += amount;
    }

    public int getCopper() {
        return copper;
    }

    public void addCopper(int amount) {
        System.out.println("Copper Added");
        copper += amount;
    }

    public int getGold() {
        return gold;
    }

    public void addGold(int amount) {
        System.out.println("Gold Added");
        gold += amount;
    }
    
    public int getCoal() {
        return coal;
    }

    public void addCoal(int amount) {
        System.out.println("Coal");
        coal += amount;
    }
}
