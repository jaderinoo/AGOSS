
public class Mob1 {

	    int strength;
		int agility;
		int armor;
		int HP;
		int special;
		int level;
		Object name;

		public Mob1(String name, int strength,int agility,int armor,int HP,int special,int level) {

	    	Object[] Statistics = {name, strength, agility, armor, HP, special, level};
	    	
	    	this.name = name;
	    	this.strength = strength;
	    	this.agility = agility;
	    	this.armor = armor;
	    	this.HP = HP;
	    	this.special = special;
	    	this.level = level;
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
	    
	    public int getHP() {
	        return HP;
	    }
	    
	    public int getSpecial() {
	        return special;
	    } 
	    
	    public int getLevel() {
	        return level;
	    } 
}

