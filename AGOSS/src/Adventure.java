import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Adventure {
	static Scanner scanner = new Scanner(System.in);
	static Boolean winStatus;
	static ArrayList<String> listOfLines = new ArrayList<>();

	
	public static void Resume(Player player, Bag bag, int choice) throws Exception {
		System.out.println("\n--------------\n") ;
		Scanner scanner = new Scanner(System.in);
		String playerMap = (String) player.getPlayerLoc();
		switch(choice) {
			
		case 0:
			//Allow map name input
			System.out.println("Please input a level name");

	    	String mapName = scanner.next();
	    	
	    	//Send the user to the map and read dialogue
	    	Dialogues.readDialogue(mapName);
	    	
	    	//ADD CHECK FOR MAP EXIST
	    	System.out.println("\n--------------\n") ;
			PlayingField.map(player, bag, mapName);
			break;
			
			
		case 1:
			System.out.println("Please input a Map List");
	    	String mapList = scanner.next();
	    	listOfLines = saveMapList(mapList);
	    	boolean check = false;
	    	
	    	//Checks if mapname and list are the same
	    	while(check == false) {
		    	for(int i = 0;i != listOfLines.size(); i++ ) {
			    	
		    		System.out.println(listOfLines.get(i));
		    		
			    	//Send the user to the map and read dialogue
			    	Dialogues.readDialogue(listOfLines.get(i));
			    	
			    	//ADD CHECK FOR MAP EXIST
			    	System.out.println("\n--------------\n") ;
					PlayingField.map(player, bag, listOfLines.get(i));
		    	}
				break;
	    	}
		}
	}
	
	public static ArrayList<String> saveMapList(String mapName) throws Exception {
		//Checks if Map List exists
		if (new File("src\\dialogues\\" + mapName + ".txt").exists()){
			//Continue if it does
			listOfLines = inputToString("src\\maplists\\" + mapName + "_mapList.txt");
			
			
			//Loop through the list
			for(int i = 0; i != listOfLines.size(); i++) {
				return listOfLines;
			}
		}else {
			System.out.println("Missing MapList .txt");
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
