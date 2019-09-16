import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Fight {
static Scanner scanner = new Scanner(System.in);
static Boolean winStatus;
static int attackerDamage;
static int attackDamage;
static int damageTaken;
static int damageDealt;
public static Boolean Move(Player player,Mob1 attacker, Bag bag) {
		
		while(player.getCurrentHp() != 0 || attacker.getCurrentHp() != 0) {
			System.out.println("--------------------------------------------------");
			System.out.println("\t\t\t|" + player.getName() + "'s Hp: " + player.getCurrentHp() + "/" + player.getMaxHp());
			System.out.println(attacker.getName() + "'s Hp: " + attacker.getCurrentHp() + "/" + attacker.getMaxHp() + "\t|"
					+ "LVL: " + player.getLevel());
			System.out.println("LVL: " + attacker.getLevel() + "\t\t\t|\n"
					+ "--------------------------------------------------");
			attackerDamage = 0;
			attackDamage = 0;
			damageTaken = 0;
			damageDealt = 0;
			System.out.println("What would you like to do?: \n1:Attack   2:Defend "
														 + "\n3:Bag      4:Run");
			
			System.out.print("Selection: ");
			int selection = scanner.nextInt();
			System.out.println("--------------------------------------------------");
			System.out.println("BattleLog:");
			switch(selection) {
				case 1:
					//If the player is faster
					if(player.getAgility() >= attacker.getAgility()) {
						playerMove(player,attacker);
						attackerMove(player,attacker);
					}
					
					//If the Attacker is faster
					if(player.getAgility() <= attacker.getAgility()) {
						attackerMove(player,attacker);
						playerMove(player,attacker);
					}
					break;
					
				case 2:
					player.armor += 2;
					attackerMove(player,attacker);
					player.armor -= 2;
					break;
					
				case 3:
					//Bag menu
					System.out.println("What item would you like to use?: \n"
							+ "1: " + bag.getPotions() + "- Potions\n"
							+ "2: " + bag.getBoosters() + "- Boosters");
					
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
					    		System.out.println("Potion used, 25maxHp recovered.");
					    		player.currentHp = player.currentHp + 25;
					    		bag.potions--;
					    		
					    		//If the currenthp after heals is over max, reset it
					    		if(player.currentHp > player.maxHp) {
					    			player.currentHp = player.maxHp;
					    		}
					    		
					    		//Attacker still gets a move
					    		attackerMove(player,attacker);
					    		break;
					    	}
							break;
						//Output booster menu
						case 2:
							System.out.println("No Booster can be used.");
				    		break;
					}
					if(alreadyAtMax = true) {
						break;
					}
					attackerMove(player,attacker);

					break;
					
				case 4:
					if(player.getAgility() >= attacker.getAgility()) {
					System.out.println("Currently not implemented");
					break;
					}else {
						System.out.println("You can't run away");
						winStatus = false;
						return winStatus;
					}
					
				default:
					System.out.println("Invalid option. Please try again.\n"); 
					break;
			}
			
			//Match win decider
			if(attacker.getCurrentHp() <= 0) {
				attacker.currentHp = 0;
				System.out.print("You won the fight!\n");
				player.levelup(attacker);
				winStatus =  false;
				player.reward(winStatus, attacker);
				return winStatus;
			}else if(player.getCurrentHp() <= 0) {
				player.currentHp = 0;
				System.out.print("You lost the fight \n\n");
				winStatus = true;
				player.reward(winStatus, attacker);
				//Reset player HP before returning
				player.currentHp = player.maxHp;
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
		System.out.println("You dealt: " + damageDealt + "DMG");
		attacker.currentHp = attacker.currentHp - damageDealt;
		
		//If damageTaken is less than 0, reset it to 0 so the player doesnt gain currentHp
		if(damageTaken < 0) {
			damageTaken = 0;
		}
	}
}
