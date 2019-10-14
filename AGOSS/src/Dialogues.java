import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Dialogues {
	//Reads dialogue from a txt
	// "-" dictates a small pause
	public static void readDialogue(String mapName) throws Exception {
		//Checks if dialogue exists
		if (new File("src\\dialogues\\" + mapName + ".txt").exists()){
			//Continue if it does
			ArrayList<String> listOfLines = inputToString("src\\dialogues\\" + mapName + ".txt");
			
			//Loop through the list
			for(int i = 0; i != listOfLines.size(); i++) {
				//If the item - shows up, skip it and add a delay
				if(listOfLines.get(i).equals("-")) {
					Thread.sleep(200);
				}else {
					System.out.println(listOfLines.get(i));
				}
			}
		}else {
			System.out.println("Missing dialogue .txt");
		}
	}
	
	public static ArrayList<String> inputToString(String fileName)throws Exception 
	{ 
		//Initializes the buffered reader
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
	    ArrayList<String> listOfLines = new ArrayList<>();

	    String line = reader.readLine();
	    while (line != null) {
	      listOfLines.add(line);
	      line = reader.readLine();
	    }
		reader.close();
		return listOfLines;
	} 
}
