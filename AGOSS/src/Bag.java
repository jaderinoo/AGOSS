
public class Bag {
	int potions;
	int boosters;
	int weapon;
	int shield;
	
	public Bag(Player player, int potions, int boosters,int weapon, int shield) {
		int[] Bag = {potions, boosters, weapon, shield};
		
		this.potions = potions;
		this.boosters = boosters;
		this.weapon = weapon;
		this.shield = shield;

	}
	
    public int getPotions() {
        return potions;
    }
    
    public int getBoosters() {
        return boosters;
    }
    
    public int getWeapon() {
    	return weapon;
    }
    
    public int getShield() {
    	return shield;
    }
    
	public int setWeapon(int wpn) {
		this.weapon = wpn;
		return this.weapon;
	}
    
	public int setShield(int shield) {
		this.shield = shield;
		return this.shield;
	}
	
    public String getWeaponName() {
    		
    	if(weapon == 0) {
    		return "Armingsword";
    	}
    	
    	if(weapon == 1) {

    		return "Longsword";
    	}
    	
    	if(weapon == 2) {

    		return "Battleaxe";	    	
    	}
    	
    	if(weapon == 3) {

    		return "Maul";	    	
    	}
		return "None";
    
    }
    
    public String getShieldName() {
		
    	if(shield == 0) {
    		return "Buckler";
    	}
    	
    	if(shield == 1) {

    		return "Targe";
    	}
    	
    	if(shield == 2) {

    		return "Kite";	    	
    	}
    	
    	if(shield == 3) {

    		return "Heater";	    	
    	}
		return "None";
    
    }
}

