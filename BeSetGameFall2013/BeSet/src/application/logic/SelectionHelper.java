package application.logic;

import application.Main;
import application.core.Tile;

public class SelectionHelper 
{
    private final int MAX_WAIT_TIME = 10000;
    private int newTime             = MAX_WAIT_TIME;
    private int lastTime            = 0;
    private int glowedTile          = 0;
    private Tile[] match            = null;
    private double timer					=0;
    
    public void Update(int deltaTime)
    {
        lastTime += (deltaTime - lastTime);
    }
    
    
    private void getTime(){
    	
    	timer = Main.GBC.progress; // a double value starting at 1.0 down to 0 for ai move
    }
    
    
    public int GetGlowId()
    {
        return glowedTile;
    }
    
    public void Reset()
    {
        glowedTile = 0;
        lastTime   = 0;
        match      = null;
    }
    
    public boolean ShouldGlow()
    {
        return (lastTime >= newTime & (glowedTile < 2));
    }
    
    public Tile Glow()
    {
        Tile tile = match[glowedTile++];
        newTime   += MAX_WAIT_TIME;
        return tile;
    }
    
    public void SetMatch(Tile[] tile)
    {
        Reset();
        match = tile;
    }
}
