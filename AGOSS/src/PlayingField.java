import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class PlayingField {
	static int rows = 15;
	static int cols = 15;
	static ArrayList<Mob1> mobList = new ArrayList<Mob1>();
	
	public static void map(Player player, Bag bag) throws Exception {
	    
		String[] batch = inputToString("src\\maps\\test.txt");
	    
		//Initialize vars
		String firstLine = batch[0];
		String secondLine = batch[1];
		String thirdLine = batch[2];
		String data = batch[3];
		
		//Test additional map information
		System.out.println(firstLine);
		System.out.println(secondLine);
		System.out.println(thirdLine);
		
		//Prints out the initial map
		char[][] map = saveMap(data);
		printMap(map);
		int i = 0 ;
		int values[] = new int[10];

		//Initial scan
		values = scanMap(player,map,values);
		System.out.println("playerLoc:" + player.getMapY() +"," + player.getMapX());
		System.out.println("EnemyCount:" + values[0]);
		System.out.println("Enemy1:" + mobList.get(0).locationX() + "," + mobList.get(0).locationY());
		System.out.println("Enemy2:" + mobList.get(1).locationX() + "," + mobList.get(1).locationY());
		
		
		//Fight.Move(player,mobList.get(0), null, bag);
		
		//while(i != enemyCount){
			
		//}
		
	}
	
	//Prints the map for the player
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
	
	//Saves map as a local variable
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
	
	//Scans the map for units
	public static int[] scanMap(Player player, char[][] map, int [] values) {
		int enemyCount = 0;
		
		for (int y=0; y < rows; y++) {
		    for (int x=0; x < cols; x++) {
		    	
		    	//If an e is found, create the object and add it to the arraylist: mobList
		        if(map[x][y] == 'e') {
		        	enemyCount++;
		        	enemyCreate(x,y,'e');
		        }
		        
		        //If p is found, save the location 
		        if(map[x][y] == 'p') {
		        	player.setMapY(y);
		        	player.setMapX(x);
		        }
		    }
		}
		values[0] = enemyCount;
		
		return values;
	}
	
	private static void enemyCreate(int y, int x, char mobType) {

		if(mobType == 'e') {
			mobList.add(new Mob1("basic", 5, 5, 5, 50, 10, 2, 50, x, y));
		}

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
