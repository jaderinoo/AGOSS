import java.io.IOException;

public class Adventure {
	public static void Resume(Player player) throws IOException {
		System.out.println("\n--------------\n") ;
		String tempLocation = (String) player.getPlayerLoc();
		switch(tempLocation) {
		case "start":
			start();
			break;
			
		case "village":

			break;
			
		default:
			System.out.println("Invalid option. Please try again.\n"); 
			break;
		}
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
