import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;

public class Adventure extends JFrame {
	static Scanner scanner = new Scanner(System.in);
	static boolean winStatus = false;
	static ArrayList<String> listOfLines = new ArrayList<>();

	public static void Resume(Player player, Bag bag, int choice, Frame frame) throws Exception {
		System.out.println("\n--------------\n") ;
		switch(choice) {
			
		case 0:
			//Allow map name input
			System.out.print("Please input a level name: ");
			Frame.grabInput(frame,1);
	    	String mapName = frame.getUserStringInput();
	    	
	    	//CHECK IF MAP EXIST
	    	winStatus = PlayingField.map(player, bag, mapName, frame);
	    	
	    	if(winStatus == true) {
	    		frame.console.append("You beat level: " + mapName + "\nReturning to main menu");
	    		Thread.sleep(500);
	    	} else if (winStatus == false) {
	    		frame.console.append("You lost on the level: " + mapName + "\nReturning to main menu with gold cut by 1/2.");
	    		Thread.sleep(500);
	    	}
	    	
			break;
			
		case 2:	
		case 1:
			String mapList = "";
			//Allow user to input mapList
			if(choice == 1) {
				System.out.println("Please input a Map List: ");
				Frame.grabInput(frame,1);
				mapList = frame.getUserStringInput();
			}else if(choice == 2) {
				mapList = "test";
			}
	    	listOfLines = saveMapList(mapList);
	    	boolean check = false;
	    	
	    	//Checks to see if the first stage is set for the player
	    	//If not, set the location to the first level in the mapList
	    	if(player.getPlayerLoc().equals(null)|| player.getPlayerLoc().equals(player.getName())) {
	    		System.out.println("Player missing location, setting to " + listOfLines.get(0));
	    		player.setPlayerLoc(listOfLines.get(0));
	    	}
	    	
	    	/*
	    	 * Winstatus table
	    	 * Lose = 0
	    	 * Win = 1
	    	 * Run = 2
	    	 */
	    	
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
			    		winStatus = PlayingField.map(player, bag, listOfLines.get(i), frame);
			    		
				    	//If player wins a map, save progress
				    	if(winStatus == true && i != listOfLines.size()-1) {
				    		
				    		frame.console.append("You beat level: " + listOfLines.get(i) + "\nMoving to next Level");
				    		Thread.sleep(500);
				    		
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
				    	} else if(winStatus == false) {
				    		System.out.println("Level lost, Would you like to replay it?");
				    		
				    		System.out.print("Resume Game? Y/N: ");
				    		Frame.grabInput(frame,1);
				    		String reply = frame.getUserStringInput();

				    		//Clear map screen
				    		frame.clearMapArea();
				    		
				    		switch(reply.toLowerCase()) {
				    			case "y":
				    				System.out.println("Resuming game. . .");
				    				Adventure.Resume(player,bag,choice,frame);
				    				break;
				    				
				    			case "n":
				    				System.out.println("Returning to menu\n");
				    				return;
				    				
				    			default:
				    				System.out.println("Invalid option. Please try again.\n"); 
				    		}
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
			for(int i = 0; i != listOfLines.size();) {
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
