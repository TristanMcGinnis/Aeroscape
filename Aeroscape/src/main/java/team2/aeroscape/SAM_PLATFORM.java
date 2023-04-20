/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package team2.aeroscape;

import java.awt.Graphics2D;
/**
 *
 * @author nndcp
 */
public class SAM_PLATFORM extends Building {
    int samCount;
    
    public SAM_PLATFORM(Tile tile) {
        super(50,50,tile);
        samCount = 2; //2 free SAM's when built
    }
    
    
    /*
    Launches a SAM at the current target.
    Returns false if launch was not possible
    */
    public boolean launchSAM(FireControl FC, Missile target) {
        if (samCount < 0) 
            return false;
        
        samCount--;
        SAM sam = new SAM(this, target);
        FC.addSam(sam);
        return true;
    }
    
    @Override
    public void update() {
        
    }
}
