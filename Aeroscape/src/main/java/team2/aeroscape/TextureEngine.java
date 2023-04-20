package team2.aeroscape;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class TextureEngine {
    public static BufferedImage ironTexture;
    public static BufferedImage copperTexture;
    public static BufferedImage goldTexture;
    public static BufferedImage coalTexture;
    public static BufferedImage grassTexture;
    public static BufferedImage minerTexture;
    public static BufferedImage smelterTexture;
    public static ImageIcon waterTexture;
    public static BufferedImage playerTexture;
    public static BufferedImage waterStaticTexture;
    static {
        loadTextures();
    }

    private static void loadTextures() {
        try {
            Path ironPath = Paths.get("src/main/resources/textures/iron.png");
            Path copperPath = Paths.get("src/main/resources/textures/copper.png");
            Path goldPath = Paths.get("src/main/resources/textures/gold.png");
            Path coalPath = Paths.get("src/main/resources/textures/coal.png");
            Path grassPath = Paths.get("src/main/resources/textures/grass.png");
            Path minerPath = Paths.get("src/main/resources/textures/miner.png");
            Path smelterPath = Paths.get("src/main/resources/textures/smelter.png");
            Path playerPath = Paths.get("src/main/resources/textures/player.png");
            Path waterPath = Paths.get("src/main/resources/textures/water.gif");
            Path waterStaticPath = Paths.get("src/main/resources/textures/waterStatic.png");
            
            
            ironTexture = ImageIO.read(Files.newInputStream(ironPath));
            copperTexture = ImageIO.read(Files.newInputStream(copperPath));
            goldTexture = ImageIO.read(Files.newInputStream(goldPath));
            coalTexture = ImageIO.read(Files.newInputStream(coalPath));
            grassTexture = ImageIO.read(Files.newInputStream(grassPath));
            minerTexture = ImageIO.read(Files.newInputStream(minerPath));
            smelterTexture = ImageIO.read(Files.newInputStream(smelterPath));
            playerTexture = ImageIO.read(Files.newInputStream(playerPath));
            waterTexture = new ImageIcon(waterPath.toString());
            waterStaticTexture = ImageIO.read(Files.newInputStream(waterStaticPath));
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
