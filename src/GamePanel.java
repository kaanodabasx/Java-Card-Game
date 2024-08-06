
public class GamePanel {
	
	public static int score = 0 ;
	
    public GamePanel() {
    	
        if (Menu.Selectedlevel == 1) {
        	
        	MemoryGameLevel1 gameLevel1 = new MemoryGameLevel1();
        	MemoryGameLevel1.triesLeft = 18;
            
        } else if (Menu.Selectedlevel == 2) {
        	
            MemoryGameLevel2 gameLevel2 = new MemoryGameLevel2();
            MemoryGameLevel2.triesLeft = 15;
            
        } else if (Menu.Selectedlevel == 3) {
        	
            MemoryGameLevel3 gameLevel3 = new MemoryGameLevel3();
            MemoryGameLevel3.triesLeft = 12;
            
        }
    }
}
