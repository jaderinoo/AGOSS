import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
		System.out.println("Welcome to AGOSS\n What would you like to name yourself?");
		String name = scanner.next();
		WriteFile(name);
	}

	public static void loadGame() {
		
	}
	
	public static void WriteFile(String name) throws IOException {
		FileWriter fileWriter = new FileWriter("BlockLog.txt", true);
	    PrintWriter printWriter = new PrintWriter(fileWriter);
	    File newFile = new File("/saves/save.txt");
	    if(newFile.length() == 0) {
	    	printWriter.println(name);
	    }
	    printWriter.close();
	}
}
