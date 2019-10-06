import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class PlayingField {
	static int rows = 15;
	static int cols = 15;
	static ArrayList<Mob1> mobList = new ArrayList<Mob1>();
	
	public static void map(Player player, Bag bag) throws Exception {
		Scanner input = new Scanner(System.in);
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

		//Initial scan
		int enemyCount = scanMap(player,map);
		System.out.println("playerLoc:" + player.getMapY() +"," + player.getMapX());
		System.out.println("EnemyCount:" + enemyCount);
		System.out.println("Enemy1:" + mobList.get(0).locationX() + "," + mobList.get(0).locationY());
		System.out.println("Enemy2:" + mobList.get(1).locationX() + "," + mobList.get(1).locationY());
		
		
		//Fight.Move(player,mobList.get(0), null, bag);
		
		while(enemyCount != 0){
			
			//Print out current user stats
			System.out.println("--------------------------------------------------");
			System.out.println("|" + player.getName() + "'s Hp: " + player.getCurrentHp() + "/" + player.getMaxHp());
			System.out.println("|LVL: " + player.getLevel());
			System.out.println("|EXP: "+ player.exp + "/" + player.level*50 +"\n"
					+ "--------------------------------------------------");
			
			
			//Allow the player to move in any direction, use the bag
			//if(option 2 is selected) open bag, else option 1 use move()
			
			System.out.println("Please make a selection: \n"
					+ "1: Player move\n"
					+ "2: Bag Menu");
			
			int temp = input.nextInt();
			switch(temp) {
			case 1:
				//Allows the player to pick which direction they would like to move in
				playerMove(player);
				break;
				
			case 2:
				//Uses the bag menu and update player information
				Fight.useBag(player,bag,null);
				Main.bagUpdater(player,bag);
				Main.playerUpdater(player);
				break;
			}
			
			
			//A class that will decide the movement
			//if the item p is sent, allow the user to move as they want
			//if anything other than p is sent, use AI to move towards the player
			//Possibility of adding different steps per unit by comparing chars
			//move();
			
			
			//All in all, if the player is killed, end the game else 
			//if the enemycount goes to 0, show a "You Win + Mapname" 
			//Make sure to add a Mapname to the vars as the first 3 lines already allow for information
			
			
			//So it will work like this, Mainmenu -> Adventure/do shit -> Select a world to enter
			//-> complete the world and return to Adventure/ allow a save option in "Adventure"
			
			
			for(int i = 0; i == enemyCount; i++) {
				//Move towards player
				System.out.println("Enemy" + i+1 + ":" + mobList.get(i).locationX() + "," + mobList.get(i).locationY());
				enemyCount--;
				
				
				//If the players loc is below the e, move down one
				//set the new coordinate in the object
				//edit the char [] [] by replacing its current position with ' '
				//put the 'e' in the new position in the [] []
				
				
				
				}
		}
		input.close();
	}
	
	public static void playerMove(Player player) {
		
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
	public static int scanMap(Player player, char[][] map) {
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
		//returns total enemies
		return enemyCount;
	}
	
	private static void enemyCreate(int y, int x, char mobType) {

		if(mobType == 'e') {
			mobList.add(new Mob1("FootSoldier", 5, 5, 5, 50, 10, 2, 50, x, y));
		}
		
		if(mobType == 'k') {
			mobList.add(new Mob1("Knight", 5, 5, 5, 50, 10, 2, 75, x, y));
		}
		
		if(mobType == 'g') {
			mobList.add(new Mob1("General", 5, 5, 5, 50, 10, 2, 100, x, y));
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
		
		reader.close();
		return batch;
	} 
}
