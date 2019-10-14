import java.util.Scanner;

public class Adventure {
	static Scanner scanner = new Scanner(System.in);
	static Boolean winStatus;
	
	public static void Resume(Player player, Bag bag) throws Exception {
		System.out.println("\n--------------\n") ;
		Scanner scanner = new Scanner(System.in);
		String tempLocation = (String) player.getPlayerLoc();
		switch(tempLocation) {
		case "start":
			start(player, bag);
			break;
			
		case "main":
			System.out.println("Please input a level name");
	    	String guideName = scanner.next();
	    	Guides guide = new Guides(guideName);
	    	
	    	for(int i = 0;i != Guides.listOfLines.size(); i++ ) {
		    	
	    		System.out.println(Guides.listOfLines.get(i));
	    		
		    	//Send the user to the map and read dialogue
		    	Dialogues.readDialogue(guideName);
		    	
		    	//ADD CHECK FOR MAP EXIST
		    	System.out.println("\n--------------\n") ;
				PlayingField.map(player, bag, guideName);
	    	}
			break;
			
		case "test":
			//Allow map name input
			System.out.println("Please input a level name");

	    	String mapName = scanner.next();
	    	
	    	//Get guide
	    	//Guides.readGuide(mapName);
	    	
	    	while(Guides.getGuide() != Guides.getGuide()) {
	    	
		    	//Send the user to the map and read dialogue
		    	Dialogues.readDialogue(mapName);
		    	
		    	//ADD CHECK FOR MAP EXIST
		    	System.out.println("\n--------------\n") ;
				PlayingField.map(player, bag, mapName);
	    	}
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
