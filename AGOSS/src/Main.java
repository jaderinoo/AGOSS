import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
	static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) throws IOException {

		
		System.out.println("Please Select an Option:\n  1 - New Game\n  2 - Load Game");
		
		int selection = scanner.nextInt();
		switch(selection) {
			case 1:
				newGame();
				break;
			case 2:
				loadGame();
				break;
			default:
				System.out.println("Invalid option. Please try again.\n"); 
				break;
		}
		main(null);
	}
	
     ////////////////////////////
    //CREATE A NEW GAME PART 1//
   ////////////////////////////
	public static void newGame() throws IOException {
		//Asks user for name
		System.out.println("Welcome to AGOSS\nWhat would you like to name yourself?");
		String name = scanner.next();
		
		//Checks to see if the file already exists
		File exist = new File(name + ".txt");
		if(exist.exists() && !exist.isDirectory()) { 
			//If it does exist, restart
			System.out.println("A save with this name already exists, please select another name.");
			newGame();
		} else {
			//If it doesn't, move on
			System.out.println("\nNew Player Stat Setup:\n");
			WriteFile(name, 1);
		}
		
	}
 
     ///////////////
    //LOAD A GAME//
   ///////////////
	
	@SuppressWarnings("null")
	public static void loadGame() throws IOException {
		System.out.print("\nPlease Enter the characters name: ");
		String tempName = scanner.next();
		BufferedReader reader = new BufferedReader(new FileReader("src\\saves\\" + tempName + "\\" + tempName + ".txt"));
		BufferedReader bagReader = new BufferedReader(new FileReader("src\\saves\\" + tempName + "\\" + tempName + "_Bag.txt"));
		String strCurrentLine = null;
		int tempStats[] = new int[7];
		int x = 0;
		int temp = 0;
		String tempPlayerLoc = "";
		
		//Read and set stats from txt file
		while ((strCurrentLine = reader.readLine()) != null) {
			if(x>=1 && x != 7) {
		    	temp = Integer.parseInt(strCurrentLine);
		    	tempStats[x] = temp;
		    }else{
		    	System.out.println(strCurrentLine);
		    	tempPlayerLoc = strCurrentLine;
		    }
		    x++;
		}
		
		//Set player stats based off save
		Player player = new Player(tempName, 
				tempStats[1], 
				tempStats[2], 
				tempStats[3], 
				tempStats[4], 
				tempStats[5],
				tempStats[6],
				tempPlayerLoc);
		
		//Read from bag txt
		int tempBag[] = new int[2];
		x = 0;
		temp = 0;
		
		
		//Read bag from txt file
				while ((strCurrentLine = reader.readLine()) != null) {
					if(x != 2) {
				    	temp = Integer.parseInt(strCurrentLine);
				    	tempBag[x] = temp;
				    }
				    x++;
				}
		//set bag from txt
		Bag bag = new Bag(player, tempBag[0], tempBag[1]);
		
		
		//Print player stats
		System.out.println("Player Stats: \n");
		System.out.println("Player Name: " + player.getName() +
  				 "\nStrength:    " + player.getStrength() +
  				 "\nAgility:     " + player.getAgility() +
  				 "\nArmor:       " + player.getArmor() +
  				 "\nHP:          " + player.getHP() +
  				 "\nSpecial:     " + player.getSpecial() +
  				 "\nLevel:       " + player.getLevel() +
				 "\nLocation:    " + player.getPlayerLoc());
		
		//Asks user if they'd like to continue their game
		System.out.println("Resume Game?: Y/N");
		String reply = scanner.next();

		switch(reply.toLowerCase()) {
			case "y":
				System.out.println("And so it begins!");
				Adventure.Resume(player,bag);
				break;
				
			case "n":
				System.out.println("Returning to menu\n");
				break;
				
			default:
				System.out.println("Invalid option. Please try again.\n"); 
				break;
		}
		
		reader.close();
		bagReader.close();
	}
	
	public static void WriteFile(String tempName, int newGame) throws IOException {
		
		new File("src\\saves\\" + tempName).mkdir();
		FileWriter fileWriter = new FileWriter("src\\saves\\" + tempName + "\\" + tempName + ".txt", true);
		PrintWriter printWriter = new PrintWriter(fileWriter);
	    BufferedReader reader = new BufferedReader(new FileReader("src\\saves\\" + tempName + "\\" + tempName + ".txt"));
	    
	     ////////////////////////////
	    //CREATE A NEW GAME PART 2//
	   ////////////////////////////
	    
	    if(newGame == 1) {

			///////////////////////////////
			//Initialize Character Start//
			/////////////////////////////
		    Player player = new Player(tempName, 5, 5, 5, 50, 10, 1, "start");
		    
		    //Stat selection screen
			System.out.println("Now lets set some stats...\nAll stats are automatically set to 5 and will increase on level ups.\nGo ahead and pick 3 stats that you want to increase.\n");

		    int x = 0;
		    while(x != 3) {
		    	
		    	//Presents the users current stats
		    	System.out.println("Player Name: " + player.getName() +
	    				 "\nStrength:    " + player.getStrength() +
	    				 "\nAgility:     " + player.getAgility() +
	    				 "\nArmor:       " + player.getArmor() +
	    				 "\nHP:      	 " + player.getHP() +
	    				 "\nSpecial:     " + player.getSpecial() + 
	    				 "\nLevel:       " + player.getLevel() + "\n");
		    	
		    	//Analyzes user input and adjusts the stats
			    String statSelect = scanner.next();
				switch(statSelect.toLowerCase()) {
					case "strength":
						player.addStrength();
						x++;
						break;
						
					case "agility":
						player.addAgility();
						x++;
						break;
						
					case "armor":
						player.addArmor();
						x++;
						break;
						
					case "hp":
						player.addHP();
						x++;
						break;
						
					case "special":
						player.addSpecial();
						x++;
						break;
						
					default:
						System.out.println("Invalid option. Please try again.\n"); 
						break;
				}
				
		    }
		    
		    //Print stats to new file
		    printWriter.println(player.getName() +
		    		"\n" + player.getStrength() +
		    		"\n" + player.getAgility() +
		    		"\n" + player.getArmor() +
		    		"\n" + player.getHP() +
		    		"\n" + player.getSpecial() +
		    		"\n" + player.getLevel() +
		    		"\n" + player.getPlayerLoc());
		    
		    //Print final stat summary
			System.out.println("Final Stat Summary:\n");	
			System.out.println("Player Name: " + player.getName() +
   				 "\nStrength:    " + player.getStrength() +
   				 "\nAgility:     " + player.getAgility() +
   				 "\nArmor:       " + player.getArmor() +
   				 "\nHP:      	 " + player.getHP() +
   				 "\nSpecial:     " + player.getSpecial() +
   				 "\nLevel:       " + player.getLevel());
			
			//Asks user if they'd like to continue or delete their new character.
			System.out.println("\nAre you ready to start your adventure? Y/N");
			String reply = scanner.next();
			
			switch(reply.toLowerCase()) {
			case "y":
				System.out.println("And so it begins!");
				Bag bag = new Bag(player, 0, 0);
				WriteBag(tempName, player, bag);
				Adventure.Resume(player, bag);
				break;
				
			case "n":
				System.out.println("\nDeleting Character and returning to menu.\n");
				try
		        { 
		            Files.deleteIfExists(Paths.get("src\\saves\\" + tempName + "\\" + tempName + ".txt")); 
		        } 
		        catch(NoSuchFileException e) 
		        { 
		            System.out.println("No such file/directory exists"); 
		        } 
		        catch(DirectoryNotEmptyException e) 
		        { 
		            System.out.println("Directory is not empty."); 
		        } 
		        catch(IOException e) 
		        { 
		            System.out.println("Invalid permissions."); 
		        } 
		          
		        System.out.println("Deletion successful.\n"); 
				break;
				
			default:
				System.out.println("Invalid option. Please try again.\n"); 
				break;
		}

	    }
	    reader.close();
	    printWriter.close();
	}
	
	public static Bag WriteBag(String tempName, Player player, Bag bag) throws IOException {
		FileWriter fileWriter = new FileWriter("src\\saves\\" + tempName + "\\" + tempName + "_Bag.txt", true);
		PrintWriter printWriter = new PrintWriter(fileWriter);
	    BufferedReader reader = new BufferedReader(new FileReader("src\\saves\\" + tempName + "\\" + tempName + "_Bag.txt"));
	    printWriter.println(bag.getPotions() + "\n" + bag.getBoosters());
	    System.out.println("Bag stuff: "+bag.getPotions());
		System.out.println(bag.getBoosters());
		printWriter.close();
		reader.close();
	    return bag;
	}
}
