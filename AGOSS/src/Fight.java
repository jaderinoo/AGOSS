import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JFrame;

public class Fight extends JFrame {
static Scanner scanner = new Scanner(System.in);
static int winStatus;
static int attackerDamage;
static int attackDamage;
static int damageTaken;
static int damageDealt;
static boolean boosterCheck = false;
static String divider = "----------------------------------------------|";
public static int Move(Player player,Mob1 attacker, Mob1 attacker2, Bag bag, Frame frame) throws IOException, InterruptedException {
	
	//Clear the console 
	frame.clearConsole();
    frame.console.append("Battle Start!");
	
	//Set the weapon and shield bonuses
	player.setWpnBonus(bag.getWeapon());
	player.setShieldBonus(bag.getWeapon());
	
	//Continue fighting until either player has 0 health
	while(player.getCurrentHp() != 0 || attacker.getCurrentHp() != 0) {
		
		//Clear the console 
		frame.clearConsole();
		
		//Update player stats
		updateStatsFrame(player, bag, frame);
		
		//Print enemy statistics
		frame.console.append(attacker.getName() + "'s Hp: " + attacker.getCurrentHp() + "/" + attacker.getMaxHp());
		frame.console.append("\nLVL: " + attacker.getLevel() + "\n" + divider);
		
		//Initialize variables
		attackerDamage = 0;
		attackDamage = 0;
		damageTaken = 0;
		damageDealt = 0;
		
		//Print out player menu
		frame.console.append("\nWhat would you like to do?: \n1:Attack\t2:Defend "
													 + "\n3:Bag\t\t4:Run");
		
		//Grab user input and clear console
		Frame.grabInput(frame,0);
		int selection = frame.getUserIntInput();
		frame.clearConsole();	
		
		//User selection
			switch(selection) {
				case 1:
					//Clear the console 
					frame.clearConsole();
					
					frame.console.append("BattleLog:\n-");
					//If the player is faster
					if(player.getAgility() >= attacker.getAgility()) {
						frame.console.append("\nYou moved first");
						playerMove(player,attacker, frame);
						frame.console.append("\n-");
						attackerMove(player,attacker,frame);
						//
						if(attacker2 != null) {
							attackerMove(player,attacker2,frame);
						}
					}
					
					//If the Attacker is faster
					if(player.getAgility() <= attacker.getAgility()) {
						frame.console.append(attacker.getName() + " moved first");
						attackerMove(player,attacker,frame);
						if(attacker2 != null) {
							attackerMove(player,attacker2,frame);
						}
						System.out.println("-");
						playerMove(player,attacker, frame);
					}
					break;
					
				case 2:
					//Clear the console 
					frame.clearConsole();
					
					frame.console.append("BattleLog:\n-");
					int tempArmor = player.armor;
					player.armor = player.armor/2 + player.armor;;
					attackerMove(player,attacker,frame);
					if(attacker2 != null) {
						attackerMove(player,attacker2,frame);
					}
					player.armor = tempArmor;
					break;
					
				case 3:
					//Clear the console 
					frame.clearConsole();
					
					//Present bag menu
					frame.console.append("Bag Menu:");
					useBag(player,bag,attacker,frame);

					break;
					
				case 4:
					//Clear the console 
					frame.clearConsole();
					
					//If the player is faster than the enemy, run from the fight
					if(player.getAgility() > attacker.getAgility()) {
						//Reset player wpnbonus and shieldbonus
						player.resetWpnBonus(bag.getWeapon());
						player.resetShieldBonus(bag.getWeapon());
						
						//Reset booster if used
						if(boosterCheck == true) {
							player.resetBooster();
						}
						
						//Save stats
						Main.bagUpdater(player,bag);
						Main.playerUpdater(player);
						
						//Return to menu
						frame.console.append("\nBag Menu:");
						return 2;
					}
					break;
					
					
				default:
					frame.console.append("\nInvalid option. Please try again.\n"); 
			}
			
			//Clear the console 
			frame.clearConsole();
			
			//Match win decider
			if(attacker.getCurrentHp() <= 0) {
				//Reset player wpnbonus and shieldbonus
				player.resetShieldBonus(bag.getWeapon());
				player.resetWpnBonus(bag.getWeapon());
				
				//Reset booster if used
				if(boosterCheck == true) {
					player.resetBooster();
				}
				
				//Return player // Player wins
				attacker.currentHp = 0;
				frame.console.append("You won the fight!\n");
				player.levelup(attacker, frame);
				winStatus =  1;
				player.reward(winStatus, attacker, frame);
				frame.console.append("\nReturning to overworld\n");
				Main.bagUpdater(player,bag);
				Main.playerUpdater(player);
				return winStatus;
			}else if(player.getCurrentHp() <= 0) {
				//Reset player wpnbonus and shieldbonus
				player.resetWpnBonus(bag.getWeapon());
				player.resetShieldBonus(bag.getWeapon());
				
				//Reset booster if used
				if(boosterCheck == true) {
					player.resetBooster();
				}
				
				//Reset player // Player loses
				player.currentHp = 0;
				frame.console.append("You lost the fight \n\n");
				winStatus = 0;
				player.reward(winStatus, attacker, frame);
				player.currentHp = player.maxHp;
				frame.console.append("\nReturning to mainmenu\n");
				Main.bagUpdater(player,bag);
				Main.playerUpdater(player);
				return winStatus;
			}
		}
		return winStatus;
	}

	public static void attackerMove(Player player, Mob1 attacker, Frame frame) throws InterruptedException {
		//Kill check
		if(attacker.getCurrentHp() <= 0) {
			return;
		}else if(player.getCurrentHp() <= 0) {
			return;
		}
		
		//Miss
		if(ThreadLocalRandom.current().nextInt(5, 10) == 5){
			frame.console.append("\n" + attacker.getName() + " MISSED");
			return;
		}
		//Calculate the damage that the player will take
		attackerDamage = attacker.strength*ThreadLocalRandom.current().nextInt(3, 5);
		damageTaken = attackerDamage - player.armor;
		//Doesn't allow below 0 attack
		if(damageTaken < 0) {
			damageTaken = 0;
		}
		frame.console.append("\nYou took:  " + damageTaken + "DMG");
		player.currentHp = player.currentHp - damageTaken;
		Thread.sleep(250);
		
		//If damageTaken is less than 0, reset it to 0 so the player doesnt gain currentHp
		if(damageTaken < 0) {
			damageTaken = 0;
		}
		return;
	}
	
	public static void playerMove(Player player, Mob1 attacker, Frame frame) throws InterruptedException {
		//Kill check
		if(attacker.getCurrentHp() <= 0) {
			return;
		}else if(player.getCurrentHp() <= 0) {
			return;
		}
		
		//Miss
		if(ThreadLocalRandom.current().nextInt(0, 6) == 0){
			frame.console.append("\n" + player.getName() + " MISSED");
			return;
		}	
		//Calculate the damage that the attacker will take
		attackDamage = player.strength*ThreadLocalRandom.current().nextInt(3, 5);
		damageDealt = attackDamage - attacker.armor;
		//Doesn't allow below 0 attack
		if(damageDealt < 0) {
			damageDealt = 0;
		}
		frame.console.append("\nYou dealt: " + damageDealt + "DMG");
		attacker.currentHp = attacker.currentHp - damageDealt;
		Thread.sleep(250);
		
		//If damageTaken is less than 0, reset it to 0 so the player doesnt gain currentHp
		if(damageTaken < 0) {
			damageTaken = 0;
		}
	}
	
	public static void updateStatsFrame(Player player, Bag bag, Frame frame) {
		
		//Print out current user statistics
		frame.setHP(player.getCurrentHp(), player.getMaxHp());
		frame.setLVL(player.getLevel());
		frame.setWPN(bag.getWeapon(), bag.getWeaponName());
		frame.setSHD(bag.getShield(), bag.getShieldName());
		frame.setEXP(player.exp, player.level);
	}
	
    public static boolean useBag(Player player, Bag bag, Mob1 attacker, Frame frame) throws InterruptedException {
		//Clear the console 
		frame.clearConsole();
    	
    	//Bag menu
		frame.console.append("What item would you like to use?: \n"
				+ "1: " + bag.getPotions() + "\t- Potions\n"
				+ "2: " + bag.getBoosters() + "\t- Boosters\n"
				+ "3: Exit");
		
    	@SuppressWarnings("unused") boolean alreadyAtMax = false;
    	
    	//Grab user input and clear console
   		Frame.grabInput(frame,0);
    	int bagOptions = frame.getUserIntInput();
    	frame.clearConsole();
    	
		switch(bagOptions) {
			case 1:
			//Output potion menu
				if(bag.potions == 0) {
					frame.console.append("No Potions can be used.");
		    		break;
		    	}else if(bag.potions >= 0) {
		    		//Check if players current hp is already maxed out
		    		//If it is revert back to the player menu
		    		if(player.currentHp == player.maxHp) {
		    			frame.console.append("Player is already at max Hp");
		    			alreadyAtMax = true;
		    			break;
		    		}
		    		//Refill players Hp by 25 hp
		    		frame.console.append(divider + "\n\tPotion used, 25maxHp recovered.");
		    		player.currentHp = player.currentHp + 25;
		    		bag.potions--;
		    		
		    		//If the currenthp after heals is over max, reset it
		    		if(player.currentHp > player.maxHp) {
		    			player.currentHp = player.maxHp;
		    		}
		    		
		    		//Attacker still gets a move
		    		if(attacker!=null) {
		    			frame.console.append("BattleLog:");
		    			attackerMove(player,attacker,frame);
		    		}
		    		break;
		    	}
				break;
			//Output booster menu
			case 2:
				//Make sure user has boosters in bag
				if(bag.boosters == 0) {
					frame.console.append("No Potions can be used.");
		    		break;
		    	}else if(bag.boosters >= 0) {
		    		//If the user hasn't used a booster yet, add the statistics
					if(boosterCheck == false) {
						boosterCheck = true;
						frame.console.append("Booster has been used\n*Player attack +2*");
						bag.boosters--;
						player.useBooster();
						break;
					}
					frame.console.append(player.getName() + " doesn't have any boosters.\n" + divider);
	    		break;
		    	}
	    		
	    	//Exit button
			case 3:
				//Clear the console 
				frame.clearConsole();
				return false;
		}
		if(alreadyAtMax = true) {
			return true;
		}
		return true;
    }
	
}
