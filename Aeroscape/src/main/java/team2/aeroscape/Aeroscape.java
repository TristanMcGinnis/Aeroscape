package team2.aeroscape;
/**
 *
 * @author nndcp
 * @author Jacob
 */
public class Aeroscape {
    
    public final Camera camera = new Camera(0, 0);
    public boolean running;
    public GridRenderer gridRenderer;
    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu();
        System.out.println("Menu Initialized");
    }
    public void init() {
        // Initialize game entities and other settings here
        System.out.println("Init");
        gridRenderer = new GridRenderer(); // code kind of works when this is set to GridRenderer gridRenderer = new GridRenderer();
        running = true;
        gameLoop();
    }

    public void gameLoop() {
        System.out.println("Game Loop Initialized");
        final double updateTime = 1_000_000_000.0 / 60.0; // 60 updates per second
        double lastUpdateTime = System.nanoTime();
        double delta = 0;
        while (running) {
            double now = System.nanoTime();
            delta += (now - lastUpdateTime) / updateTime;
            lastUpdateTime = now;

            while (delta >= 1) {
                update(); // Update game entities
                delta--;
            }
            render(); // Render the game entities
            //sync(); // Synchronize the game loop if needed
        }
    }

    public void update() {
                // Update game entities/camera here
        try {
            int screenWidth = gridRenderer.getScreenWidth();
            int screenHeight = gridRenderer.getScreenHeight();
            camera.follow(GridRenderer.getCircleX(), GridRenderer.getCircleY(), screenWidth, screenHeight);
        }
        catch(Exception NullPointerException){
            System.out.println("Null Pointer");
        }
    }

    public void render() {
        // Render game entities here
        try {
           gridRenderer.repaint();
        }
        catch (Exception NullPointerException) {
            System.out.println("Null Pointer");
        }
    }

    public void sync() {
        // Synchronize game loop here, if needed
    }
}
