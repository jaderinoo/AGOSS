import java.util.concurrent.ThreadLocalRandom;

public class Player {

	    int strength;
		int agility;
		int armor;
		int maxHp;
		int special;
		int level;
		int exp;
		int gold;
		int currentHp;
		private Object playerLoc;
		Object name;

		public Player(String name, int strength,int agility,int armor,int maxHp,int special, int level, int exp, int gold, int currentHp, String playerLoc) {

	    	Object[] Statistics = {name, strength, agility, armor, maxHp, special, level, currentHp, playerLoc };
	    	
	    	this.name = name;
	    	this.strength = strength;
	    	this.agility = agility;
	    	this.armor = armor;
	    	this.maxHp = maxHp;
	    	this.special = special;
	    	this.playerLoc = playerLoc;
	    	this.level = level;
	    	this.exp = exp;
	    	this.gold = gold;
	    	this.currentHp = currentHp;
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
	    
	    public int getExp() {
	        return exp;
	    }
	    
	    public int getGold() {
	        return gold;
	    }
	    
	    public int getCurrentHp() {
	    	return currentHp;
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
		
		public int addmaxHp() {
			this.maxHp += 10;
			return this.maxHp;
		}
		
		public int addSpecial() {
			this.special += 2;
			return this.special;
		}
		
		//Using items
		public int usePotion() {
			return this.currentHp;
		}
		
		public int useBooster() {
			return this.strength;
		}
		
		public void levelup() {
			//Reward EXP
			this.exp = this.exp + 10;
			//If the players exp is = to its current level*2
			if(this.exp == this.level*50) {
				System.out.println("~LEVEL UP!~");
				this.exp = 0;
				this.level++;
				int x = 0;
				//Randomly selects 3 stats to boost on level up
			    while(x != 3) {
					switch(ThreadLocalRandom.current().nextInt(1, 5)) {
					case 1:
						this.addStrength();
						x++;
						System.out.println("Strength +1");
						break;
						
					case 2:
						this.addAgility();
						x++;
						System.out.println("Agility +1");
						break;
						
					case 3:
						this.addArmor();
						x++;
						System.out.println("Armor +1");
						break;
						
					case 4:
						this.addmaxHp();
						x++;
						System.out.println("MaxHp +1");
						break;
						
					case 5:
						this.addSpecial();
						x++;
						System.out.println("Special +1");
						break;
					}
				}
			}
		}
		
		public void reward(boolean winStatus) {
			//If the player wins
			if(winStatus == false) {
				this.gold = this.gold + 110;
				
			//Else if the player loses
			}else if(winStatus == true) {
				this.gold = this.gold / 2;
			}
			
		}
}

