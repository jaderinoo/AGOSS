
public class Mob1 {

	    private int strength;
		private int agility;
		private int armor;
		private int maxHP;
		private int special;
		private Object name;

		public Mob1(String name, int strength,int agility,int armor,int maxHP,int special) {

	    	Object[] Statistics = {name, strength, agility, armor, maxHP, special};
	    	
	    	this.name = name;
	    	this.strength = strength;
	    	this.agility = agility;
	    	this.armor = armor;
	    	this.maxHP = maxHP;
	    	this.special = special;
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
	    
	    public int getMaxHP() {
	        return maxHP;
	    }
	    
	    public int getSpecial() {
	        return special;
	    }     
}

