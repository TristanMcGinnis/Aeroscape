package team2.aeroscape;


/**
The SAM_PLATFORM class represents a surface-to-air missile platform in the game.
*/
public class SAM_PLATFORM extends Building {
    int samCount;
    
    
    /**
    Creates a new SAM_PLATFORM with the specified Tile position.
    @param tile the Tile position of the SAM_PLATFORM.
    */
    public SAM_PLATFORM(Tile tile) {
        super(50,50,tile);
        samCount = 2; //2 free SAM's when built
    }
    
    
    /**
    Launches a SAM at the current target.
    @param FC the FireControl system of the game.
    @param target the missile target.
    @return false if launch was not possible; otherwise, true.
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
