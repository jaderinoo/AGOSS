import java.util.Scanner;

public class Adventure {
	static Scanner scanner = new Scanner(System.in);
	static Boolean winStatus;
	
	public static void Resume(Player player, Bag bag) throws Exception {
		System.out.println("\n--------------\n") ;
		String tempLocation = (String) player.getPlayerLoc();
		switch(tempLocation) {
		case "start":
			start(player, bag);
			break;
			
		case "village":

			break;
			
		case "test":
			//Allow map name input
			System.out.println("Please input a level name");
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
	    	String mapName = scanner.next();
	    	
	    	//Send the user to the map and read dialogue
	    	Dialogues.readDialogue(mapName);
	    	
	    	//ADD CHECK FOR MAP EXIST
	    	System.out.println("\n--------------\n") ;
			PlayingField.map(player, bag, mapName);

			break;
			
		default:
			System.out.println("Invalid option. Please try again.\n"); 
			break;
		}
	}
	
	public static void start(Player player, Bag bag) {
		System.out.println(bag.getPotions());
		System.out.println(bag.getBoosters());
	}
	
	public static void village(Player player, Bag bag) {
		System.out.println("This is a Village!");
	}
	
}
