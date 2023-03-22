/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package team2.aeroscape;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author nndcp
 */
public class OptionsFrame extends JFrame {
    private final JCheckBox audioCB;
    private final JSlider volumeSlider;
    private final JButton saveBtn;
    private final JButton exitBtn;
    public OptionsFrame() {
        setTitle("Game Options");
        
        //Import current settings from save file...
        //GSettings currSettings = GSettings('savedirectory');
        
        //Define the options
        audioCB = new JCheckBox("Enable Sound");
        volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        saveBtn = new JButton("Save");
        exitBtn = new JButton("Exit");
        
        //Size and Position Window
        setSize(300, 200);
        setLocationRelativeTo(null);

       //Create Options panel
       JPanel mainPanel = new JPanel();
       mainPanel.setLayout(new GridLayout(2,1));
       mainPanel.add(audioCB);
       mainPanel.add(volumeSlider);
        
        //Create Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveBtn);
        buttonPanel.add(exitBtn);
        
        //Add panels to window
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel,BorderLayout.SOUTH);
        
        //Add Event Listeners
        saveBtn.addActionListener((ActionEvent e) ->
        {
            System.out.println("Settings Saved");
            //GSettings newSettings = GSettings();
        });
        
        exitBtn.addActionListener((ActionEvent e) -> {
           System.out.println("Settings Closed");
           this.dispose();
        });
        
        this.setVisible(true);
    }
}
