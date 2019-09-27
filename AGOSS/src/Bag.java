import java.util.Scanner;

public class Bag {
	int potions;
	int boosters;
	
	public Bag(Player player, int potions, int boosters) {
		int[] Bag = {potions, boosters};
		
		this.potions = potions;
		this.boosters = boosters;
	}
	
    public int getPotions() {
        return potions;
    }
    
    public int getBoosters() {
        return boosters;
    }
    
}

