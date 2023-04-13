package team2.aeroscape;

public class Inventory {
    private int iron;
    private int copper;
    private int gold;
    private int coal;
    
    private int iron_ingot;
    private int copper_ingot;
    private int gold_ingot;
    
    public Inventory() {
        iron = 0;
        copper = 0;
        gold = 0;
        coal = 0;
        
        iron_ingot = 0;
        copper_ingot = 0;
        gold_ingot = 0;
    }

    public int getIronIngot()
    {
        return iron_ingot;
    }
    
    public void addIronIngot(int amount)
    {
        System.out.println("Iron_Ingot Added");
        iron_ingot += amount;
    }
    
    public int getCopperIngot()
    {
        return copper_ingot;
    }
    
    public void addCopperIngot(int amount)
    {
        System.out.println("Copper_Ingot Added");
        copper_ingot += amount;
    }
    
    public int getGoldIngot()
    {
        return gold_ingot;
    }
    
    public void addGoldIngot(int amount)
    {
        System.out.println("Gold_Ingot Added");
        gold_ingot += amount;
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
