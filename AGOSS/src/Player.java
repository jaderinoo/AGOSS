
public class Player {

	    int strength;
		int agility;
		int armor;
		int HP;
		int special;
		int level;
		private Object playerLoc;
		Object name;

		public Player(String name, int strength,int agility,int armor,int HP,int special, int level, String playerLoc) {

	    	Object[] Statistics = {name, strength, agility, armor, HP, special, level, playerLoc };
	    	
	    	this.name = name;
	    	this.strength = strength;
	    	this.agility = agility;
	    	this.armor = armor;
	    	this.HP = HP;
	    	this.special = special;
	    	this.playerLoc = playerLoc;
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
		
		public int addHP() {
			this.HP += 10;
			return this.HP;
		}
		
		public int addSpecial() {
			this.special += 2;
			return this.special;
		}
		
		//Using items
		public int usePotion() {
			return this.HP;
		}
		
		public int useBooster() {
			return this.strength;
		}
}

