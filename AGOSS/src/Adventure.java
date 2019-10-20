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
		switch(choice) {
			
		case 0:
			//Allow map name input
			System.out.println("Please input a level name");

	    	String mapName = scanner.next();
	    	
	    	//CHECK IF MAP EXIST
	    	System.out.println("\n--------------\n");
	    	PlayingField.map(player, bag, mapName);
			break;
			
			
		case 1:
			//Allow user to input mapList
			System.out.println("Please input a Map List");
	    	String mapList = scanner.next();
	    	listOfLines = saveMapList(mapList);
	    	boolean check = false;
	    	
	    	//Checks to see if the first stage is set for the player
	    	//If not, set the location to the first level in the mapList
	    	if(player.getPlayerLoc().equals(null)|| player.getPlayerLoc().equals(player.getName())) {
	    		System.out.println("Player missing location, setting to " + listOfLines.get(0));
	    		player.setPlayerLoc(listOfLines.get(0));
	    	}
	    	
	    	//Checks if mapName and list are the same
	    	while(check == false) {
		    	for(int i = 0;i != listOfLines.size(); i++ ) {    
		    		//Reset win status
		    		winStatus = false;
		    		
		    		//Map completion check
		    		if(i == listOfLines.size()) {
		    			System.out.println("Map list completed");
		    			return;
		    		}
		    		
			    	//CHECK IF MAP EXIST	
			    	if(listOfLines.get(i).equals(player.getPlayerLoc())) {
			    		winStatus = PlayingField.map(player, bag, listOfLines.get(i));
			    		
				    	//If player wins a map, save progress
				    	if(winStatus == true && i != listOfLines.size()-1) {
				    		//Flavor
				    		System.out.print("Saveing progress");
				    		Thread.sleep(200);
				    		System.out.print(" .");
				    		Thread.sleep(200);
				    		System.out.print(" .");
				    		Thread.sleep(200);
				    		System.out.print(" .\n");
				    		
				    		//Set location and save
				    		player.setPlayerLoc(listOfLines.get(i+1));
				    		Main.playerUpdater(player);
				    	}
			    	}
		    	}
				break;
	    	}
		}
	}
	
	public static ArrayList<String> saveMapList(String mapList) throws Exception {
		//Checks if Map List exists
		if (new File("src\\maplists\\" + mapList + "_mapList.txt").exists()){
			//Continue if it does
			listOfLines = inputToString("src\\maplists\\" + mapList + "_mapList.txt");
			
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
