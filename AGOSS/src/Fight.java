import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Fight {
static Scanner scanner = new Scanner(System.in);
static Boolean winStatus;
public static Boolean Move(Player player,Mob1 attacker) {
		
		while(player.getHP() != 0 || attacker.getHP() != 0) {
			System.out.println("\t\t\t|" + player.getName() + "'s current HP: " + player.getHP());
			System.out.println(attacker.getName() + "'s current HP: " + attacker.getHP() + " |");
			System.out.println("LVL: " + attacker.getLevel() + "\t\t\t|\n"
					+ "--------------------------------------------------");
			int attackerDamage = 0;
			int attackDamage = 0;
			int damageTaken = 0;
			int damageDealt = 0;
			System.out.println("What would you like to do?: \n1:Attack   2:Defend "
														 + "\n3:Bag      4:Run");
			
			int selection = scanner.nextInt();
			switch(selection) {
				case 1:

					//If the player is faster
					if(player.getAgility() >= attacker.getAgility()) {
						//System.out.println("Players Move!");
						//Miss
						if(ThreadLocalRandom.current().nextInt(0, 6) == 0){
							System.out.println("\n\t\t\t|MISS");
							break;
						}	
						//Calculate the damage that the attacker will take
						attackDamage = attacker.strength*ThreadLocalRandom.current().nextInt(3, 5);
						damageDealt = attackDamage - player.armor;
						System.out.println("\n\t\t\t|You dealt: " + damageDealt + "DMG");
						attacker.HP = attacker.HP - damageDealt;
					
						//Attacker Move
						//System.out.println("Attackers Move!");
						//Miss
						if(ThreadLocalRandom.current().nextInt(0, 6) == 0){
							System.out.println("\t\t\t|MISS");
							break;
						}	
						//Calculate the damage that the player will take
						attackerDamage = attacker.strength*ThreadLocalRandom.current().nextInt(3, 5);
						damageTaken = attackerDamage - player.armor;
						System.out.println("\t\t\t|You took:  " + damageTaken + "DMG");
						player.HP = player.HP - damageTaken;
						
						//If damageTaken is less than 0, reset it to 0 so the player doesnt gain hp
						if(damageTaken < 0) {
							damageTaken = 0;
						}
						break;
					}
					
					
					//If the Attacker is faster
					if(player.getAgility() <= attacker.getAgility()) {
						//System.out.println("Attackers Move!");
						//Miss
						if(ThreadLocalRandom.current().nextInt(0, 6) == 0){
							System.out.println("\t\t\t|MISS");
							break;
						}	
						//Calculate the damage that the player will take
						attackerDamage = attacker.strength*ThreadLocalRandom.current().nextInt(3, 5);
						damageTaken = attackerDamage - player.armor;
						System.out.println("\n\t\t\t|You took:  " + damageTaken + "DMG");
						player.HP = player.HP - damageTaken;
						
						//Player Move
						//System.out.println("Players Move!");
						//Miss
						if(ThreadLocalRandom.current().nextInt(0, 6) == 0){
							System.out.println("\n\t\t\t|MISS");
							break;
						}	
						//Calculate the damage that the attacker will take
						attackDamage = attacker.strength*ThreadLocalRandom.current().nextInt(3, 5);
						damageDealt = attackDamage - player.armor;
						System.out.println("\t\t\t|You dealt: " + damageDealt + "DMG");
						attacker.HP = attacker.HP - damageDealt;
						
						//If damageTaken is less than 0, reset it to 0 so the player doesnt gain hp
						if(damageTaken < 0) {
							damageTaken = 0;
						}
						break;
					}
					break;
						
					
					
				case 2:
					//Miss
					if(ThreadLocalRandom.current().nextInt(0, 6) == 0){
						System.out.println("\n\t\t\t|MISS");
						break;
					}	
					player.armor += 2;
					
					//Calculate the damage that the player will take
					attackerDamage = attacker.strength*ThreadLocalRandom.current().nextInt(3, 5);
					damageTaken = attackerDamage - player.armor;
					
					//If damageTaken is less than 0, reset it to 0 so the player doesnt gain hp
					if(damageTaken < 0) {
						damageTaken = 0;
					}
					System.out.println("\n\t\t\t|You took: " + damageTaken + "DMG");
					player.HP = player.HP - damageTaken;
					
					player.armor -= 2;
					break;
					
				case 3:
					
					System.out.println("Currently not implemented");
					
					//Miss
					if(ThreadLocalRandom.current().nextInt(0, 6) == 2){
						System.out.println("MISS");
						break;
					}	
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
			
			if(attacker.getHP() <= 0) {
				attacker.HP = 0;
				System.out.print("\t\t\t|You won the fight!\n");
				winStatus =  false;
				return winStatus;
			}else if(player.getHP() <= 0) {
				player.HP = 0;
				System.out.print("You lost the fight \t|\n\n");
				winStatus = true;
				return winStatus;
			}
		}
		return winStatus;
	}
}
