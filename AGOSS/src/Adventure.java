import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Adventure {
	static Scanner scanner = new Scanner(System.in);
	static Boolean winStatus;
	
	public static void Resume(Player player) throws IOException {
		System.out.println("\n--------------\n") ;
		String tempLocation = (String) player.getPlayerLoc();
		switch(tempLocation) {
		case "start":
			start();
			break;
			
		case "village":

			break;
			
		case "test":
			
			Mob1 attacker = new Mob1("baddie", 5, 5, 5, 50, 10, 1);
			Move(player,attacker);
			
			break;
			
		default:
			System.out.println("Invalid option. Please try again.\n"); 
			break;
		}
	}
	
	public static Boolean Move(Player player,Mob1 attacker) {
		
		while(player.getHP() != 0 || attacker.getHP() != 0) {
			System.out.println("\t\t\t|" + player.getName() + "'s current HP: " + player.getHP());
			System.out.println(attacker.getName() + "'s current HP: " + attacker.getHP() + " |");
			System.out.println("LVL: " + attacker.getLevel() + "\t\t\t|\n"
					+ "--------------------------------------------------");
			int attackerDamage = 0;
			int damageTaken = 0;
			System.out.println("What would you like to do?: \n1:Attack   2:Defend "
														 + "\n3:Bag      4:Run");
			
			int selection = scanner.nextInt();
			switch(selection) {
				case 1:
					//Miss
					if(ThreadLocalRandom.current().nextInt(0, 6) == 0){
						System.out.println("\n\t\t\t|MISS");
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
					
					
					
					//Miss
					if(ThreadLocalRandom.current().nextInt(0, 6) == 2){
						System.out.println("MISS");
						break;
					}	
					break;
					
				case 4:
		
					break;
				default:
					System.out.println("Invalid option. Please try again.\n"); 
					break;
			}
			if(attacker.getHP() < 0) {
				attacker.HP = 0;
				System.out.print("\t\t\t|You won the fight!\n");
				winStatus =  false;
				return winStatus;
			}else if(player.getHP() < 0) {
				player.HP = 0;
				System.out.print("You lost the fight \t|\n\n");
				winStatus = true;
				return winStatus;
			}
		}
		return winStatus;
	}
	
	public static void Attack(Player player,Mob1 attacker) {

		player.strength = 0;
	}
	
	public static void start() {
		System.out.println("Welcome to AGOSS!\n"
				+ "This game plays very similarly to other textbased RPGs but with a slight twist\n"
				+ "Think of it as a pokemon game where you only use one pokemon.");
	}
	
	public static void village() {
		System.out.println("This is a Village!");
	}
	
}
