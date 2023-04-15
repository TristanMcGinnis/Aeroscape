/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package team2.aeroscape;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

/**
 *
 * @author nndcp
 */
public class FireControl {
    //This should be initialized and possibly ran on its on thread
    private static List<SAM> sams;
    
    
    /*
    Initialize FireControl to have an empty list of SAM's
    */
    public FireControl() {
        sams = new ArrayList<>();
    }
    
    
    /*
    Add a SAM to FC handled active sam list.
    */
    public void addSam(SAM sam) {
        sams.add(sam);
    }
    
    
    /*
    Check every SAM in the list and update there positions.
    Delete SAM if no longer active
    */
    public void update() {
        Iterator<SAM> iter = sams.iterator();
        while(iter.hasNext()) {
            SAM sam = iter.next();
            if(sam.update()) iter.remove(); //Sam deleted once no longer active
            //See SAM.update() for more info
        }
    }
}
