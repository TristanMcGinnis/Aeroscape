/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package team2.aeroscape;

/**
 *
 * @author nndcp
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public final class MainMenu extends JFrame {
    
    private JButton newGameButton;
    private JButton loadGameButton;
    private JButton optionsButton;
    private JButton quitButton;
    private final int numBtns = 4;
    
    public MainMenu() {
        // Set the window title
        setTitle("Aeroscape Launcher");
        // Create the buttons
        newGameButton = new JButton("New Game");
        loadGameButton = new JButton("Load Game");
        optionsButton = new JButton("Options");
        quitButton = new JButton("Quit");
        
        // Set the layout manager for the window
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        
        // Set the size of the window
        setSize(300, 200);
        
        // Center the window on the screen
        setLocationRelativeTo(null);
        
        //Position the buttons
        this.positionBtns();
        
        // Set the behavior for the buttons
        newGameButton.addActionListener((ActionEvent e) -> {
            // Add code to start the game here
            System.out.println("Creating new game...");
        });
        
        newGameButton.addActionListener((ActionEvent e) -> {
                // Add code to start the game here
                System.out.println("Loading saved game...");
        });
        
        optionsButton.addActionListener((ActionEvent e) -> {
            // Add code to display options here
            System.out.println("Displaying Aeroscape options...");
            OptionsFrame optsFrame = new OptionsFrame();
        });
        
        quitButton.addActionListener((ActionEvent e) -> {
            // Add code to quit the game here
            System.exit(0);
        });
        
        // Make the window visible
        setVisible(true);
    }
    
    public void positionBtns() {
        // Set the alignment for the buttons
        Dimension dims = this.size();
        System.out.println(dims.height);
        int ctr = 1;
        //Vertical Alignment (Not currently working)
        newGameButton.setAlignmentY(ctr++*dims.height/(this.numBtns+1));
        loadGameButton.setAlignmentY(ctr++*dims.height/(this.numBtns+1));
        optionsButton.setAlignmentY(ctr++*dims.height/(this.numBtns+1));
        quitButton.setAlignmentY(ctr++*dims.height/(this.numBtns+1));
        
        //Horizontal Alignment
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Add some padding between the buttons
        add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Add the buttons to the window
        add(newGameButton);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(loadGameButton);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(optionsButton);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(quitButton);
    }
}
