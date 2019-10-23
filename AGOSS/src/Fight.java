import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Fight {
static Scanner scanner = new Scanner(System.in);
static Boolean winStatus;
static int attackerDamage;
static int attackDamage;
static int damageTaken;
static int damageDealt;
static String divider = "----------------------------------------------|";
public static Boolean Move(Player player,Mob1 attacker, Mob1 attacker2, Bag bag) throws IOException {
	
	player.setWpnBonus(bag.getWeapon());
	player.setShieldBonus(bag.getWeapon());
	
	while(player.getCurrentHp() != 0 || attacker.getCurrentHp() != 0) {
		System.out.println(divider);
		System.out.println("\t\t\t|" + player.getName() + "'s Hp: " + player.getCurrentHp() + "/" + player.getMaxHp());
		System.out.println(attacker.getName() + "'s Hp: " + attacker.getCurrentHp() + "/" + attacker.getMaxHp() + "\t|"
				+ "LVL: " + player.getLevel());
		System.out.println("LVL: " + attacker.getLevel() + "\t\t\t|EXP: "+ player.exp + "/" + player.level*50 +"\n"
				+ divider);
		attackerDamage = 0;
		attackDamage = 0;
		damageTaken = 0;
		damageDealt = 0;
		System.out.println("What would you like to do?: \n1:Attack   2:Defend "
													 + "\n3:Bag");
		System.out.print("Selection: ");
		int selection = scanner.nextInt();
		System.out.println(divider);
			switch(selection) {
				case 1:
					System.out.println("BattleLog:\n-");
					//If the player is faster
					if(player.getAgility() >= attacker.getAgility()) {
						System.out.println("You moved first");
						playerMove(player,attacker);
						System.out.println("-");
						attackerMove(player,attacker);
						//
						if(attacker2 != null) {
							attackerMove(player,attacker2);
						}
					}
					
					//If the Attacker is faster
					if(player.getAgility() <= attacker.getAgility()) {
						System.out.println(attacker.getName() + " moved first");
						attackerMove(player,attacker);
						if(attacker2 != null) {
							attackerMove(player,attacker2);
						}
						System.out.println("-");
						playerMove(player,attacker);
					}
					break;
					
				case 2:
					System.out.println("BattleLog:\n-");
					int tempArmor = player.armor;
					player.armor = player.armor/2 + player.armor;;
					attackerMove(player,attacker);
					if(attacker2 != null) {
						attackerMove(player,attacker2);
					}
					player.armor = tempArmor;
					break;
					
				case 3:
					System.out.println("Bag Menu:");
					useBag(player,bag,attacker);

					break;
					
				default:
					System.out.println("Invalid option. Please try again.\n"); 
					break;
			}
			
			//Match win decider
			if(attacker.getCurrentHp() <= 0) {
				//Reset player wpnbonus and shieldbonus
				player.resetShieldBonus(bag.getWeapon());
				player.resetWpnBonus(bag.getWeapon());
				
				//Return player // Player wins
				attacker.currentHp = 0;
				System.out.print("You won the fight!\n");
				player.levelup(attacker);
				winStatus =  true;
				player.reward(winStatus, attacker);
				System.out.println("Returning to overworld\n");
				Main.bagUpdater(player,bag);
				Main.playerUpdater(player);
				return winStatus;
			}else if(player.getCurrentHp() <= 0) {
				//Reset player wpnbonus and shieldbonus
				player.resetWpnBonus(bag.getWeapon());
				player.resetShieldBonus(bag.getWeapon());
				
				//Reset player // Player loses
				player.currentHp = 0;
				System.out.print("You lost the fight \n\n");
				winStatus = false;
				player.reward(winStatus, attacker);
				player.currentHp = player.maxHp;
				System.out.println("Returning to mainmenu\n");
				Main.bagUpdater(player,bag);
				Main.playerUpdater(player);
				return winStatus;
			}
		}
		return winStatus;
	}

	public static void attackerMove(Player player, Mob1 attacker) {
		//Kill check
		if(attacker.getCurrentHp() <= 0) {
			return;
		}else if(player.getCurrentHp() <= 0) {
			return;
		}
		
		//Miss
		if(ThreadLocalRandom.current().nextInt(5, 10) == 5){
			System.out.println(attacker.getName() + " MISSED");
			return;
		}
		//Calculate the damage that the player will take
		attackerDamage = attacker.strength*ThreadLocalRandom.current().nextInt(3, 5);
		damageTaken = attackerDamage - player.armor;
		//Doesn't allow below 0 attack
		if(damageTaken < 0) {
			damageTaken = 0;
		}
		System.out.println("You took:  " + damageTaken + "DMG");
		player.currentHp = player.currentHp - damageTaken;
		
		//If damageTaken is less than 0, reset it to 0 so the player doesnt gain currentHp
		if(damageTaken < 0) {
			damageTaken = 0;
		}
		return;
	}
	
	public static void playerMove(Player player, Mob1 attacker) {
		//Kill check
		if(attacker.getCurrentHp() <= 0) {
			return;
		}else if(player.getCurrentHp() <= 0) {
			return;
		}
		
		//Miss
		if(ThreadLocalRandom.current().nextInt(0, 6) == 0){
			System.out.println(player.getName() + " MISSED");
			return;
		}	
		//Calculate the damage that the attacker will take
		attackDamage = player.strength*ThreadLocalRandom.current().nextInt(3, 5);
		damageDealt = attackDamage - attacker.armor;
		//Doesn't allow below 0 attack
		if(damageDealt < 0) {
			damageDealt = 0;
		}
		System.out.println("You dealt: " + damageDealt + "DMG");
		attacker.currentHp = attacker.currentHp - damageDealt;
		
		//If damageTaken is less than 0, reset it to 0 so the player doesnt gain currentHp
		if(damageTaken < 0) {
			damageTaken = 0;
		}
	}
	
    public static boolean useBag(Player player,Bag bag,Mob1 attacker) {
    	//Bag menu
		System.out.println("What item would you like to use?: \n"
				+ "1: " + bag.getPotions() + "\t- Potions\n"
				+ "2: " + bag.getBoosters() + "\t- Boosters\n"
				+ "3: Exit");
		
    	@SuppressWarnings("unused") boolean alreadyAtMax = false;
    	int bagOptions = scanner.nextInt();
		switch(bagOptions) {
			case 1:
			//Output potion menu
				if(bag.potions == 0) {
		    		System.out.println("No Potions can be used.");
		    		break;
		    	}else if(bag.potions >= 0) {
		    		//Check if players current hp is already maxed out
		    		//If it is revert back to the player menu
		    		if(player.currentHp == player.maxHp) {
		    			System.out.println("Player is already at max Hp");
		    			alreadyAtMax = true;
		    			break;
		    		}
		    		//Refill players Hp by 25 hp
		    		System.out.println(divider + "\n\tPotion used, 25maxHp recovered.");
		    		player.currentHp = player.currentHp + 25;
		    		bag.potions--;
		    		
		    		//If the currenthp after heals is over max, reset it
		    		if(player.currentHp > player.maxHp) {
		    			player.currentHp = player.maxHp;
		    		}
		    		//Attacker still gets a move
		    		if(attacker!=null) {
		    			System.out.println(divider);
		    			System.out.println("BattleLog:");
		    			attackerMove(player,attacker);
		    		}
		    		break;
		    	}
				break;
			//Output booster menu
			case 2:
				System.out.println("No Booster can be used.\n" + divider);
	    		break;
	    		
	    	//Exit button
			case 3:
				System.out.println(divider);
				return false;
		}
		if(alreadyAtMax = true) {
			return true;
		}
		return true;
    }
	
}
