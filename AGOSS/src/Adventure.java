import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Adventure {
	static Scanner scanner = new Scanner(System.in);
	static Boolean winStatus;
	
	public static void Resume(Player player, Bag bag) throws IOException {
		System.out.println("\n--------------\n") ;
		String tempLocation = (String) player.getPlayerLoc();
		switch(tempLocation) {
		case "start":
			start(player, bag);
			break;
			
		case "village":

			break;
			
		case "test":
			
			Mob1 attacker = new Mob1("baddie", 5, 5, 5, 50, 10, 2, 50);
			Fight.Move(player,attacker, bag);
			Main.bagUpdater(player,bag);
			Main.playerUpdater(player);
			
			break;
			
		default:
			System.out.println("Invalid option. Please try again.\n"); 
			break;
		}
	}
	
	public static void start(Player player, Bag bag) {
		System.out.println("Welcome to AGOSS!\n"
				+ "This game plays very similarly to other textbased RPGs but with a slight twist\n"
				+ "Think of it as a pokemon game where you only use one pokemon.");
		System.out.println(bag.getPotions());
		System.out.println(bag.getBoosters());
	}
	
	public static void village(Player player, Bag bag) {
		System.out.println("This is a Village!");
	}
	
}
