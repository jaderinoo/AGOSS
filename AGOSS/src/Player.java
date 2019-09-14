
public class Player {

	    private int strength;
		private int agility;
		private int armor;
		private int maxHP;
		private int special;
		private Object playerLoc;
		private Object name;

		public Player(String name, int strength,int agility,int armor,int maxHP,int special, String playerLoc) {

	    	Object[] Statistics = {name, strength, agility, armor, maxHP, special, playerLoc};
	    	
	    	this.name = name;
	    	this.strength = strength;
	    	this.agility = agility;
	    	this.armor = armor;
	    	this.maxHP = maxHP;
	    	this.special = special;
	    	this.playerLoc = playerLoc;
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
	    
	    public Object getPlayerLoc() {
	        return playerLoc;
	    }

		public int addStrength() {
			this.strength++;
			return this.strength;
		}
		
		public int addAgility() {
			this.agility++;
			return this.agility;
		}
		
		public int addArmor() {
			this.armor++;
			return this.armor;
		}
		
		public int addMaxHP() {
			this.maxHP += 10;
			return this.maxHP;
		}
		
		public int addSpecial() {
			this.special += 2;
			return this.special;
		}
	    
	    
}

