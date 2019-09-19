import java.io.BufferedReader;
import java.io.FileReader;

public class PlayingField {
	static int rows = 15;
	static int cols = 15;

	public static void map(Player player, Bag bag) throws Exception {
	    
		String[] batch = inputToString("src\\maps\\test.txt");
	    
		//Initialize vars
		String firstLine = batch[0];
		String secondLine = batch[1];
		String thirdLine = batch[2];
		String data = batch[3];
		
		//Test prins
		System.out.println(firstLine);
		System.out.println(secondLine);
		System.out.println(thirdLine);
		
		char[][] map = saveMap(data);
		printMap(map);
	}
	
	public static void printMap(char[][] map) {
		System.out.println("Playing Field:\n_______________________________________________");
		for (int i=0; i < rows; i++) {
			System.out.print("|");
		    for (int j=0; j < cols; j++) {
		        System.out.print("[" + map[i][j] + "]");
		    }
		    System.out.println("|");
		}
		System.out.println("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
	}
	
	public static char[][] saveMap(String data){
		char [][] map = new char[rows][cols];
		int next = 0;
		double maxRow = 0;
		maxRow = Math.sqrt(data.length());
		for (int i=0; i < maxRow; i++) {
		    for (int j=0; j < maxRow; j++) {
		    	map[i][j] = data.charAt(next++);
		    }
		}
		return map;

	}
	
	public static String[] inputToString(String fileName)throws Exception 
	{ 
		//Initializes the buffered reader
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		
		//Reads the first lines of the input file so it isnt included in the string
		String firstLine = reader.readLine(); 
		String secondLine = reader.readLine();
		String thirdLine = reader.readLine();
		
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
		//Remove all line breaks from string
		data = data.replace("\n", "").replace("\r", "");
		//Return all grabbed vars
		String batch[] = new String[4];
		
		//saves all items to batch[]
		batch[0] = firstLine;
		batch[1] = secondLine;
		batch[2] = thirdLine;
		batch[3] = data;
		
		return batch;
	} 
}
