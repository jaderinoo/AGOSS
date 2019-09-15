
public class Mob1 {

	    int strength;
		int agility;
		int armor;
		int maxHp;
		int special;
		int level;
		int currentHp;
		Object name;

		public Mob1(String name, int strength,int agility,int armor,int maxHp,int special,int level,int currentHp) {

	    	Object[] Statistics = {name, strength, agility, armor, maxHp, special, level};
	    	
	    	this.name = name;
	    	this.strength = strength;
	    	this.agility = agility;
	    	this.armor = armor;
	    	this.maxHp = maxHp;
	    	this.special = special;
	    	this.level = level;
	    	this.currentHp = maxHp;
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
	    
	    public int getmaxHp() {
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

