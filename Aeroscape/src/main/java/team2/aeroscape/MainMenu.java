package team2.aeroscape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
The MainMenu class represents the main menu of the game.
It extends the JFrame class and implements the ActionListener interface.
This class contains buttons for starting a new game, loading a saved game, displaying options, and quitting the game.
It also contains functionality to request the player's name when starting a new game or loading a saved game.
Finally, it contains functionality for selecting the map size when starting a new game.
*/
public final class MainMenu extends JFrame {
    
    private JButton newGameButton;
    private JButton loadGameButton;
    private JButton optionsButton;
    private JButton quitButton;
    private final int numBtns = 4;
    
    private JTextField nameRequest;
    private JTextField nameEnter;
    private JButton nameButton;
    
    
    /**
    * Constructor for the MainMenu class.
    * Initializes the JFrame and its components, sets their behavior and positions them on the window.
    */
    public MainMenu() {
        // Set the window title
        setTitle("Aeroscape Launcher");
        // Create the buttons
        newGameButton = new JButton("New Game");
        loadGameButton = new JButton("Load Game");
        optionsButton = new JButton("Options");
        quitButton = new JButton("Quit");
        
        nameRequest = new JTextField("Please enter the player's name");
        nameEnter = new JTextField();
        nameButton = new JButton("Enter");
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
            
            RequestPlayerNameNew();
            
            
            //Now executes the next few commented out lines from the button added in RequestPlayerNameNew
            
            //System.out.println("Creating new game...");
            //SwingUtilities.invokeLater(() -> {
                //Aeroscape initialize = new Aeroscape();
                //initialize.init();
            //});
            //dispose();
            //setVisible(false);
        });
        loadGameButton.addActionListener((ActionEvent e) -> {
            // Add code to load the game here
           
            System.out.println("Opening Load Menu");

            RequestPlayerNameLoad();
            
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
    
    
    /**
    * Positions the buttons vertically and horizontally on the window.
    */
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
    
    
    /**
    Request the player name for a new game.
    */
    private void RequestPlayerNameNew(){
        
        this.remove(newGameButton);
        
        Dimension dims = this.size();

        this.setSize(300,300);
        
        int a = 1;
        
        nameRequest.setAlignmentY(a++*dims.height/(this.numBtns+1));
        nameButton.setAlignmentY(a++*dims.height/(this.numBtns+1));
        nameEnter.setAlignmentY(a++*dims.height/(this.numBtns+1));
        
        nameRequest.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameEnter.setAlignmentX(Component.CENTER_ALIGNMENT);  
      
        nameRequest.setSize(10, 2);
        nameEnter.setSize(10,2);
      
        nameRequest.setEditable(false);

      
        add(nameRequest);
      
        add(Box.createRigidArea(new Dimension(0, 10)));

        add(nameEnter);
      
        add(Box.createRigidArea(new Dimension(0, 10)));

        add(nameButton);
      
        nameButton.addActionListener((ActionEvent e) -> {
            String name = nameEnter.getText();
            selectMapSize(name);
        });
      
    }
    
    
    /**
    Request the player name to load a saved game.
    */
    private void RequestPlayerNameLoad(){
        
        this.remove(loadGameButton);
        
        Dimension dims = this.size();

        this.setSize(300,300);
        
        int a = 1;
        
        nameRequest.setAlignmentY(a++*dims.height/(this.numBtns+1));
        nameButton.setAlignmentY(a++*dims.height/(this.numBtns+1));
        nameEnter.setAlignmentY(a++*dims.height/(this.numBtns+1));
      
        nameRequest.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameEnter.setAlignmentX(Component.CENTER_ALIGNMENT);       
        
        
        nameRequest.setEditable(false);
        nameRequest.setSize(10, 2);
        nameEnter.setSize(10,2);
      
        add(nameRequest);
        add(Box.createRigidArea(new Dimension(0, 10)));

        add(nameEnter);
        add(Box.createRigidArea(new Dimension(0, 10)));

        add(nameButton);
      
        nameButton.addActionListener((ActionEvent e) -> {
            // Add code to load the game here
            
            //Grabs the name from the nameEnter text field
            String name = nameEnter.getText();
            
            //Load Game from here using the inputted name
            
            System.out.println("Loading saved game...");
            //SwingUtilities.invokeLater(() -> {
            //    Aeroscape initialize = new Aeroscape(name);
            //    initialize.init();
            //});
            //dispose();
        });
      
    }
    
    /**
    Requests input from the player to select the map size
    */
    private void selectMapSize(String playerName) {
            String[] options = {"Small (50x50)", "Medium (100x100)", "Large (500x500)", "Huge (1000x1000)"};
            int selection = JOptionPane.showOptionDialog(
                    this,
                    "Please select a map size:",
                    "Select Map Size",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            int gridWidth, gridHeight;
            switch (selection) {
                case 0:
                    gridWidth = gridHeight = 50;
                    break;
                case 1:
                    gridWidth = gridHeight = 100;
                    break;
                case 2:
                    gridWidth = gridHeight = 500;
                    break;
                case 3:
                    gridWidth = gridHeight = 1000;
                    break;
                default:
                    return;
            }

            System.out.println("Creating new game...");
            SwingUtilities.invokeLater(() -> {
                Aeroscape initialize = new Aeroscape(playerName, gridWidth, gridHeight);
                initialize.init();
            });
            dispose();
        }
    }

