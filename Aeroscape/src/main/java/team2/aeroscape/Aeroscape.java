// Import necessary libraries
package team2.aeroscape;
import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.sound.sampled.Clip;
import javax.swing.SwingUtilities;

/**
 * This class represents the main game engine for the Aeroscape game.
 * @author Jacob Neel
 * @author Tristan McGinnis
 * @author Nick Davis
 * @author Gavin Brady
 */
public class Aeroscape {
    
    // Initialize variables
    private Player player;
    private Camera camera;
    private GridRenderer gridRenderer;
    private Inventory inventory;
    private LevelData levelData;
    private AudioEngine audioEngine;
    private KeyboardInputManager keyboardManager;
    private MouseInputManager mouseManager;
    private boolean running;
    private boolean isZooming;

    /**
     * Constructor for the Aeroscape game engine.
     * @param playerName Name of the player.
     * @param gridWidth Width of the grid.
     * @param gridHeight Height of the grid.
     */
    public Aeroscape(String playerName, int gridWidth, int gridHeight) {
        int playerStartX = (gridWidth * 50) / 2;
        int playerStartY = (gridHeight * 50) / 2;
        player = new Player(playerStartX, playerStartY);
        camera = new Camera(0, 0, 1.5);
        inventory = new Inventory();
        levelData = new LevelData();
        //levelData = new LevelData(playerName,inventory, );
        gridRenderer = new GridRenderer(camera, player, levelData, inventory, gridWidth, gridHeight);
        audioEngine = new AudioEngine();
        keyboardManager = new KeyboardInputManager();
        mouseManager = new MouseInputManager();
        System.out.println("Player name = " + playerName);
        running = true;
    }
    
    /**
     * Main method for the game engine.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        new MainMenu();
        System.out.println("Menu Initialized");
    }
    
    /**
     * Initialization method for the game engine.
     * Initializes game entities and other settings.
     */
    public void init() {
        System.out.println("Init");

        audioEngine.loadAudio("MainMusic", "src/main/resources/sfx/MainMusic.wav");
        audioEngine.loopSound2D("MainMusic", -45.0f, Clip.LOOP_CONTINUOUSLY);
        JFrame frame = createGameWindow();
        addListeners(frame);
        new Thread(this::gameLoop).start();
    }
    
    /**
     * Creates and initializes the game window.
     * @return The JFrame representing the game window.
     */
    private JFrame createGameWindow() {
        JFrame frame = new JFrame("Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().add(gridRenderer, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        return frame;
    }

    
    /**
     * Adds input listeners to the game window.
     * @param frame The JFrame representing the game window.
     */
    private void addListeners(JFrame frame) {
        frame.addKeyListener(keyboardManager);
        frame.addMouseListener(mouseManager);
        gridRenderer.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                initializePositions();
            }
        });
        updateCameraPosition();

        gridRenderer.repaint();
    }
    
    
    /**
     * Updates the camera position based on the player's position.
     */
    private void updateCameraPosition() {
        int screenWidth = gridRenderer.getScreenWidth();
        int screenHeight = gridRenderer.getScreenHeight();
        double newX = player.getX() * camera.getZoom() - (screenWidth / 2);
        double newY = player.getY() * camera.getZoom() - (screenHeight / 2);

        camera.setX((int) newX);
        camera.setY((int) newY);
    }


    /**
     * The game loop is responsible for updating and rendering the game entities. It works by calculating the time difference
     * since the last update and adding it to a delta variable. If the delta time is greater than or equal to 1, the game entities
     * are updated and the delta time is decremented. The game entities are then rendered. This loop runs continuously while the 
     * running flag is set to true, ensuring that the game is constantly updating and rendering.
     */
    public void gameLoop() {
            System.out.println("Game Loop Initialized");
            final double updateTime = 1000/60; //60 Updates per second
            double lastUpdateTime = System.currentTimeMillis(); 
            double delta = 0;


            while (running) {
                double now = System.currentTimeMillis();
                delta = (now - lastUpdateTime);
                lastUpdateTime = now;


                update(updateTime);
                while (delta < updateTime) {
                    delta = System.currentTimeMillis() - lastUpdateTime;
                }
                SwingUtilities.invokeLater(() -> {
                    render();
                });
                //sync(); // Synchronize the game loop if needed
            }
        }

    
    /**
    * Updates game entities and camera position.
    * @param delta The time elapsed since the last update.
    */
    public void update(double delta) {
        // Update game entities/camera here
        try {
            int screenWidth = gridRenderer.getScreenWidth();
            int screenHeight = gridRenderer.getScreenHeight();
          
            int step = (int) (5 + camera.getZoom());

            if (keyboardManager.isUp()) {
                player.setVelY(-step);
            } else if (keyboardManager.isDown()) {
                player.setVelY(step);
            } else {
                player.stopY();
            }

            if (keyboardManager.isLeft()) {
                player.setVelX(-step);
            } else if (keyboardManager.isRight()) {
                player.setVelX(step);
            } else {
                player.stopX();
            }
            
            
            

            player.update();

            double lerpFactor = 0.1; // Adjust this value for smoother or more responsive movement (0.0 to 1.0)
            double newX, newY;
            if (isZooming) {
                newX = player.getX() * camera.getZoom() - (screenWidth / 2);
                newY = player.getY() * camera.getZoom() - (screenHeight / 2);
            } else {
                newX = Camera.lerp(camera.getX(), player.getX() * camera.getZoom() - (screenWidth / 2), lerpFactor);
                newY = Camera.lerp(camera.getY(), player.getY() * camera.getZoom() - (screenHeight / 2), lerpFactor);
            }

            camera.setX((int) newX);
            camera.setY((int) newY);

            MouseEvent clickEvent = mouseManager.getMouseClickEvent();
            if (clickEvent != null) {
                if (clickEvent.getButton() == MouseEvent.BUTTON1) { // Left mouse button
                    gridRenderer.handleMinerPlacement(clickEvent);
                } else if (clickEvent.getButton() == MouseEvent.BUTTON3) { // Right mouse button
                    gridRenderer.handleSmelterPlacement(clickEvent);
                } else if (clickEvent.getButton() == MouseEvent.BUTTON2) { // Middle Mouse Button
                    gridRenderer.handleSamPltPlacement(clickEvent);
                } 
            }

            
        } catch (Exception NullPointerException) {
            System.out.println("Null Pointer");
        }
        
        for (Miner miner : levelData.getMiners()) {
            miner.update();
        }      
        
        for (Smelter smelter : levelData.getSmelters()) {
            smelter.update();
        }
        
        //levelData.FC.update();
        //levelData.EH.update();
        
    }


    /**
     * Renders the game entities.
     */
    public void render() {
        try {
           gridRenderer.repaint();
        }
        catch (Exception NullPointerException) {
            System.out.println("Null Pointer");
        }
    }

    
    /**
     * Synchronize the game loop if needed.
     */
    public void sync() {
        // Synchronize game loop here, if needed
    }
    
    
    /**
     * Initializes and updates camera position
     */
    public void initializePositions() {
        int screenWidth = gridRenderer.getWidth();
        int screenHeight = gridRenderer.getHeight();
        camera.follow(player.getX(), player.getY(), screenWidth, screenHeight);
    }
}
