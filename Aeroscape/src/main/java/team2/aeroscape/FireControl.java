package team2.aeroscape;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

/**
The FireControl class provides a means for managing Surface-to-Air Missiles (SAMs).
It handles adding, updating, and removing SAMs from an active list.
*/
public class FireControl {
    
    private static List<SAM> sams;
    
    /**
     * Constructor for the FireControl class.
     * Initializes an empty list to hold active SAMs.
     */
    public FireControl() {
        sams = new ArrayList<>();
    }
    
    
    /**
     * Adds a SAM to the active list.
     *
     * @param sam - The SAM to add.
     */
    public void addSam(SAM sam) {
        sams.add(sam);
    }
    
    
    /**
     * Checks every SAM in the list and updates their positions.
     * Deletes the SAM from the list if it is no longer active.
     * See SAM.update() for more information.
     */
    public void update() {
        Iterator<SAM> iter = sams.iterator();
        while(iter.hasNext()) {
            SAM sam = iter.next();
            if(!sam.update()) iter.remove(); //Sam deleted once no longer active
        }
    }
}
