import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
	
	public static void newGame() throws IOException {
		//Asks user for name
		System.out.println("Welcome to AGOSS\n What would you like to name yourself?");
		String name = scanner.next();
		
		//Checks to see if the file already exists
		File exist = new File(name + ".txt");
		if(exist.exists() && !exist.isDirectory()) { 
			//If it does exist, restart
			System.out.println("A save with this name already exists, please select another name.");
			newGame();
		} else {
			//If it doesn't, move on
			System.out.println("A save with this name doesnt exist.");
			WriteFile(name, 1);
		}
		
		//Stat selection screen
		//System.out.println("Now lets set some stats...\nAll stats are automatically set to 5 and will increase on level ups.\nGo ahead and pick 3 stats that you want to increase.");
	}

	public static void loadGame() {
		
	}
	
	public static void WriteFile(String tempName, int newGame) throws IOException {
		
		FileWriter fileWriter = new FileWriter(tempName + ".txt", true);
	    PrintWriter printWriter = new PrintWriter(fileWriter);
	    BufferedReader reader = new BufferedReader(new FileReader(tempName + ".txt"));
	    if(newGame == 1) {
		    Player player = new Player(tempName, 5, 5, 5, 5, 5);

		    
		    printWriter.println(player.getName());
		    printWriter.println(player.getStrength());
		    printWriter.println(player.getAgility());
		    printWriter.println(player.getArmor());
		    printWriter.println(player.getMaxHP());
		    printWriter.println(player.getMaxMana());


	    }
	    reader.close();
	    printWriter.close();
	}
}
