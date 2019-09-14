import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Arrays;
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
	
	public static void loadGame() throws IOException {
		System.out.print("\nPlease Enter the characters name: ");
		String tempName = scanner.next();
		BufferedReader reader = new BufferedReader(new FileReader(tempName + ".txt"));
		String strCurrentLine = null;
		
		while ((strCurrentLine = reader.readLine()) != null) {
		    System.out.println(strCurrentLine);
		}
		/*
		Player player = new Player(tempname, 
						tempStrength(), 
						tempAgility(), 
						tempArmor(), 
						tempMaxHP(), 
						tempMaxMana());
						*/
		reader.close();
	}
	
	public static void WriteFile(String tempName, int newGame) throws IOException {
		
		FileWriter fileWriter = new FileWriter(tempName + ".txt", true);
	    PrintWriter printWriter = new PrintWriter(fileWriter);
	    BufferedReader reader = new BufferedReader(new FileReader(tempName + ".txt"));
	    
	     ////////////////////////////
	    //CREATE A NEW GAME PART 2//
	   ////////////////////////////
	    
	    if(newGame == 1) {
	    	//Set new stats
		    Player player = new Player(tempName, 5, 5, 5, 50, 10);

		    //Print stats to new file
		    printWriter.println(player.getName() +
		    		"\n" + player.getStrength() +
		    		"\n" + player.getAgility() +
		    		"\n" + player.getArmor() +
		    		"\n" + player.getMaxHP() +
		    		"\n" + player.getMaxMana());
		    
		    //Stat selection screen
			System.out.println("Now lets set some stats...\nAll stats are automatically set to 5 and will increase on level ups.\nGo ahead and pick 3 stats that you want to increase.\n");

		    int x = 0;
		    while(x != 3) {
		    	
		    	//Presents the users current stats
		    	System.out.println("Player Name: " + player.getName() +
	    				 "\nStrength:    " + player.getStrength() +
	    				 "\nAgility:     " + player.getAgility() +
	    				 "\nArmor:       " + player.getArmor() +
	    				 "\nMax HP:      " + player.getMaxHP() +
	    				 "\nMax Mana:    " + player.getMaxMana() + "\n");
		    	
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
						
					case "maxhp":
						player.addMaxHP();
						x++;
						break;
						
					case "maxmana":
						player.addMaxMana();
						x++;
						break;
						
					default:
						System.out.println("Invalid option. Please try again.\n"); 
						break;
				}
				
		    }
		    
			System.out.println("Final Stat Summary:\n");	
			System.out.println("Player Name: " + player.getName() +
   				 "\nStrength:    " + player.getStrength() +
   				 "\nAgility:     " + player.getAgility() +
   				 "\nArmor:       " + player.getArmor() +
   				 "\nMax HP:      " + player.getMaxHP() +
   				 "\nMax Mana:    " + player.getMaxMana());
			
			System.out.println("\nAre you ready to start your adventure? Y/N");
			
			String reply = scanner.next();
			
			if (reply == "y")
			{
			   System.out.println("And so it begins!");
			   //adventure();
			}
			else if(reply == "n")
			{
				System.out.println("\nDeleting Character and returning to menu.\n");
				try
		        { 
		            Files.deleteIfExists(Paths.get(tempName + ".txt")); 
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
		    }
			
	    }
	    reader.close();
	    printWriter.close();
	}
}
