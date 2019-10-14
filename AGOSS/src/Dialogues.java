import java.io.BufferedReader;
import java.io.FileReader;

public class Dialogues {
	//Reads dialogue from a txt
	// "-" dictates a small pause
	public static void readDialogue(String mapName) throws Exception {
		//Checks if dialogue exists
		if (mapName == null){
			return;
		}
		
		//Continue if it does
		String data = inputToString("src\\dialogues\\" + mapName + ".txt");
		
		//Reads in text line by line
		System.out.println(data);
	}
	
	public static String inputToString(String fileName)throws Exception 
	{ 
		//Initializes the buffered reader
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		
		//Initialize the variables needed to read in the file
		String line = null;
		String data = null;
		StringBuffer stbuffer = new StringBuffer();
		//Reads in text line by line
		while((line = reader.readLine())!=null){
			stbuffer.append(line).append("\n");
		}
		
		//Sets the appended string to a string variable
		data = stbuffer.toString();
		//Remove all commas from string
		data = data.replace(",", "");
		
		reader.close();
		return data;
	} 
}
