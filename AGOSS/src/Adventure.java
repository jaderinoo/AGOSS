import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Adventure {
	static Scanner scanner = new Scanner(System.in);
	static Boolean winStatus;
	static ArrayList<String> listOfLines = new ArrayList<>();

	
	public static void Resume(Player player, Bag bag) throws Exception {
		System.out.println("\n--------------\n") ;
		Scanner scanner = new Scanner(System.in);
		String tempLocation = (String) player.getPlayerLoc();
		switch(tempLocation) {
		case "start":
			//start(player, bag);
			break;
			
		case "main":
			System.out.println("Please input a level name");
	    	String guideName = scanner.next();
	    	listOfLines = saveGuide(guideName);
	    	
	    	for(int i = 0;i != listOfLines.size(); i++ ) {
		    	
	    		System.out.println(listOfLines.get(i));
	    		
		    	//Send the user to the map and read dialogue
		    	Dialogues.readDialogue(listOfLines.get(i));
		    	
		    	//ADD CHECK FOR MAP EXIST
		    	System.out.println("\n--------------\n") ;
				PlayingField.map(player, bag, listOfLines.get(i));
	    	}
			break;
			
		case "test":
			//Allow map name input
			System.out.println("Please input a level name");

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
	
	public static ArrayList<String> saveGuide(String mapName) throws Exception {
		//Checks if Guide exists
		if (new File("src\\dialogues\\" + mapName + ".txt").exists()){
			//Continue if it does
			listOfLines = inputToString("src\\dialogues\\" + mapName + "_guide.txt");
			
			
			//Loop through the list
			for(int i = 0; i != listOfLines.size(); i++) {
				return listOfLines;
			}
		}else {
			System.out.println("Missing guide .txt");
		}
		return listOfLines;
	}
    
	public static ArrayList<String> inputToString(String fileName)throws Exception 
	{ 
		//Initializes the buffered reader
		BufferedReader reader = new BufferedReader(new FileReader(fileName));

	    String line = reader.readLine();
	    while (line != null) {
	      listOfLines.add(line);
	      line = reader.readLine();
	    }
		reader.close();
		return listOfLines;
	} 
	
}
