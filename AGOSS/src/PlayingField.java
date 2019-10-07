import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;

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
		int enemyMoveCount = enemyCount;
		System.out.println("playerLoc:" + player.getMapY() +"," + player.getMapX());
		System.out.println("EnemyCount:" + enemyCount);
		System.out.println("Enemy1:" + mobList.get(0).getMapX() + "," + mobList.get(0).getMapY());
		System.out.println("Enemy2:" + mobList.get(1).getMapX() + "," + mobList.get(1).getMapY());
		
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
				//Player move first, then allow all enemys move
				playerMove(map, player);

				break;
				
			case 2:
				//Uses the bag menu and update player information
				Fight.useBag(player,bag,null);
				Main.bagUpdater(player,bag);
				Main.playerUpdater(player);
				break;
			}
			
			//Reset move counter
			enemyMoveCount = enemyCount;

			//Enemy moves
			for(int i = 0; i != enemyMoveCount; i++) {
				//Move towards player
				enemyMove(map, mobList.get(i),i);
			}

			//Save stats and bags
			Main.bagUpdater(player,bag);
			Main.playerUpdater(player);
			
			//All in all, if the player is killed, end the game else 
			//if the enemycount goes to 0, show a "You Win + Mapname" 
			//Make sure to add a Mapname to the vars as the first 3 lines already allow for information
			
			//So it will work like this, Mainmenu -> Adventure/do shit -> Select a world to enter
			//-> complete the world and return to Adventure/ allow a save option in "Adventure"
		}
		
		//This section will be used after the enemycount is set to 0.
		//Return to a menu that doesnt exist yet xd

	}
	
	public static void playerMove(char[][] map, Player player) {
		Scanner input2 = new Scanner(System.in);
		System.out.println("Please make a selection: \n"
				+ "1: Up\n"
				+ "2: Down\n"
				+ "3: Left\n"
				+ "4: Right");
		
		int temp2 = input2.nextInt();
		switch(temp2) {
		case 1:
			//Up
			map[player.getMapX()][player.getMapY()] = ' ';
			map[player.getMapX()][player.getMapY()-1] = 'p';
			player.setMapY(player.getMapY()-1);
			System.out.println("PLAYER LOCATION " + player.getMapX() + "," + player.getMapY());
			printMap(map);
			break;
			
		case 2:
			//Down
			player.setMapY(player.getMapY()+1);
			break;
			
		case 3:
			//Left
			player.setMapX(player.getMapX()-1);
			break;
			
		case 4:
			//Right
			player.setMapX(player.getMapX()+1);
			break;
		}
	}
	
	public static void enemyMove(char[][] map, Mob1 enemy,int i) {
		//Post enemy positions
		System.out.println(enemy.getName() + ": (" + enemy.getMapX() + "," + enemy.getMapY() + ")");

		//If the players loc is below the e, move down one
		//set the new coordinate in the object
		//edit the char [] [] by replacing its current position with ' '
		//put the 'e' in the new position in the [] []
	}
		
	//Prints the map for the player
	public static void printMap(char[][] map) {
		System.out.println("Playing Field:\n_______________________________________________");
		for (int y=0; y < rows; y++) {
			System.out.print("|");
		    for (int x=0; x < cols; x++) {
		        System.out.print("[" + map[x][y] + "]");
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
		for (int y=0; y < maxRow; y++) {
		    for (int x=0; x < maxRow; x++) {
		    	map[x][y] = data.charAt(next++);
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
		        	System.out.println("PLAYER LOCATION " + player.getMapX() + "," + player.getMapY());
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
