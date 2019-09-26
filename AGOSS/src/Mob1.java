
public class Mob1 {

	    int strength;
		int agility;
		int armor;
		int maxHp;
		int special;
		int level;
		int currentHp;
		int locationY;
		int locationX;
		Object name;

		public Mob1(String name, int strength,int agility,int armor,int maxHp,int special,int level,int currentHp,int locationX, int locationY) {

	    	Object[] Statistics = {name, strength, agility, armor, maxHp, special, level, locationX, locationY};
	    	
	    	this.locationX = locationX;
	    	this.locationY = locationY;
	    	this.name = name;
	    	this.strength = strength;
	    	this.agility = agility;
	    	this.armor = armor;
	    	this.maxHp = maxHp;
	    	this.special = special;
	    	this.level = level;
	    	this.currentHp = maxHp;
	   }
		
		public int locationY() {
	        return locationY;
	    }
		
		public int locationX() {
	        return locationX;
	    }
		
		public Object getName() {
	        return name;
	    }
		
	    public int getStrength() {
	        return strength;
	    }
	    
	    public int getAgility() {
	        return agility;
	    }
	    
	    public int getArmor() {
	        return armor;
	    }
	    
	    public int getMaxHp() {
	        return maxHp;
	    }
	    
	    public int getSpecial() {
	        return special;
	    } 
	    
	    public int getLevel() {
	        return level;
	    } 
	    
	    public int getCurrentHp() {
	    	return currentHp;
	    }
}

