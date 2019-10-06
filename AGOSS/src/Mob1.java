
public class Mob1 {

	    int strength;
		int agility;
		int armor;
		int maxHp;
		int special;
		int level;
		int currentHp;
		int getMapY;
		int getMapX;
		Object name;

		public Mob1(String name, int strength,int agility,int armor,int maxHp,int special,int level,int currentHp,int getMapX, int getMapY) {

	    	Object[] Statistics = {name, strength, agility, armor, maxHp, special, level, getMapX, getMapY};
	    	
	    	this.getMapX = getMapX;
	    	this.getMapY = getMapY;
	    	this.name = name;
	    	this.strength = strength;
	    	this.agility = agility;
	    	this.armor = armor;
	    	this.maxHp = maxHp;
	    	this.special = special;
	    	this.level = level;
	    	this.currentHp = maxHp;
	   }
		
		public int getMapY() {
	        return getMapY;
	    }
		
		public int getMapX() {
	        return getMapX;
	    }
		
		public int setMapX(int x) {
			this.getMapX = x;
			return this.getMapX;
		}
		
		public int setMapY(int y) {
			this.getMapY = y;
			return this.getMapY;
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

