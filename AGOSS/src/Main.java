import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
	static Scanner scanner = new Scanner(System.in);
	static int strength = 5;
	static int agility = 5;
	static int armor = 5;
	static int maxHP = 5;
	static int maxMana = 5;
	
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
		System.out.println("Welcome to AGOSS\n What would you like to name yourself?");
		String name = scanner.next();

		File tmpDir = new File(name + ".txt");
		boolean exists = tmpDir.exists();
		
		if(exists = true) {
			System.out.println("A save with this name already exists, please select another name.");
			newGame();
		}else {
		WriteFile(name, 1);
		}
		System.out.println("Now lets set some stats...\nAll stats are automatically set to 5 and will increase on level ups.\nGo ahead and pick 3 stats that you want to increase.");

		WriteFile(name, 1);
	}

	public static void loadGame() {
		
	}
	
	public static void WriteFile(String name, int newGame) throws IOException {
		
		FileWriter fileWriter = new FileWriter(name + ".txt", true);
	    PrintWriter printWriter = new PrintWriter(fileWriter);
	    BufferedReader reader = new BufferedReader(new FileReader(name + ".txt"));
	    if(newGame == 1) {
		    printWriter.println("Player Name: " + name);
		    printWriter.println("Strength: " + strength);
		    printWriter.println("Agility: " + agility);
		    printWriter.println("Armor: " + armor);
		    printWriter.println("Max HP: " + maxHP);
		    printWriter.println("Max Mana: " + maxMana);
		    
		    String line = null;
		    while ((line = reader.readLine()) != null) {
		        System.out.println(line);
		    }
	    }
	    printWriter.close();
	}
}
