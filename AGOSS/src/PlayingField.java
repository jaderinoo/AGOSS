import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

//NOTES
/*		
			//All in all, if the player is killed, end the game else 
			//if the enemycount goes to 0, show a "You Win + Mapname" 
			//Make sure to add a Mapname to the vars as the first 3 lines already allow for information
			
			//So it will work like this, Mainmenu -> Adventure/do shit -> Select a world to enter
			//-> complete the world and return to Adventure/ allow a save option in "Adventure"
 */

public class PlayingField {
	static int rows = 15;
	static int cols = 15;
	static ArrayList<Mob1> mobList = new ArrayList<Mob1>();
	static Merchant merchant = new Merchant(0,0);
	static Scanner input = new Scanner(System.in);
	static int enemyCount = 0;
	static int enemyMoveCount = 0;
	static int enemyKillCount = 0;
	static String firstLine = "";
	static int turn = 0;
	static boolean movementCheck = false;
	//Collision based items
	static char [] collisionSet = {'/','|','\\','_','-','F','P','K','G','M'};
	static String divider = "----------------------------------------------|";
	
	public static void map(Player player, Bag bag) throws Exception {

		String[] batch = inputToString("src\\maps\\test.txt");
	    
		//Initialize vars
		firstLine = batch[0];
		String [] secondLine = batch[1].split(" ");
		String thirdLine = batch[2];
		String data = batch[3];
		
		//parses the size of the grid and places it into rows and cols
		int[] stats = new int[secondLine.length];
		for (int i = 0; i < secondLine.length; i++) {
			String numberAsString = secondLine[i];
			stats[i] = Integer.parseInt(numberAsString);
		}
		rows = stats[0];
		cols = stats[1];
		
		//Test additional map information
		System.out.println(firstLine);
		System.out.println(batch[1]);
		System.out.println(thirdLine);
		
		//Saves the initial map
		char[][] map = saveMap(data);

		//Initial scan
		enemyCount = scanMap(player,map);
		enemyMoveCount = enemyCount;
		
		//Print out location of players and enemy locations
		/*
		System.out.println("playerLoc:" + player.getMapY() +"," + player.getMapX());
		System.out.println("EnemyCount:" + enemyCount);
		System.out.println("Enemy1:" + mobList.get(0).getMapX() + "," + mobList.get(0).getMapY());
		System.out.println("Enemy2:" + mobList.get(1).getMapX() + "," + mobList.get(1).getMapY());
		*/
		
		playerMenu(player, map, bag, firstLine);

	}
	
	public static void playerMenu(Player player, char[][] map, Bag bag, String firstLine) throws Exception {
		if(enemyCount == 0) {
			System.out.println("You beat level: " + firstLine + "\nReturning to main menu\n\n\n\n\n\n");
			return;
		}
		
		while(enemyCount != 0){
			turn++;
			printMap(map);
			
			//Decides what menu will be presented to the user
			boolean found = findMerchant(map, merchant, player);
			
			//Prints out enemyCount
			System.out.println("Enemies remaining: " + enemyCount);
			System.out.println("Turn #" + turn);
			
			//Print out current user stats
			System.out.println(divider);
			System.out.println("|" + player.getName() + "'s Hp: " + player.getCurrentHp() + "/" + player.getMaxHp() +"\t|Bonuses:");
			System.out.println("|LVL: " + player.getLevel() + "\t\t\t|Weapon: " + bag.getWeaponName() + "(+" + bag.getWeapon() + ")");
			System.out.println("|EXP: "+ player.exp + "/" + player.level*50 +"\t\t|Shield: " + bag.getShieldName() + "(+" + bag.getShield() + ")\n"
					+ divider);
			
			
			//Allow the player to move in any direction, use the bag
			//If found == true, give the user the option to use the merchant
			boolean check = false;
			do {
				if(found == false) {
				System.out.println("Please make a selection: \n"
						+ "1: Player move\n"
						+ "2: Bag Menu");
				}else if(found == true) {
					System.out.println("Please make a selection: \n"
							+ "1: Player move\n"
							+ "2: Bag Menu\n"
							+ "3: Merchant Menu");
				}
				
				int temp = input.nextInt();
				switch(temp) {
				case 1:		
					//Player move first, then allow all enemies move
					check = true;
					System.out.println(divider);
					playerMove(map, player, bag);
					break;
					
				case 2:
					//Uses the bag menu and update player information
					System.out.println(divider);
					check = Fight.useBag(player,bag,null);
					Main.bagUpdater(player,bag);
					Main.playerUpdater(player);
					if (check == false) {
						printMap(map);
					}
					break;
					
				case 3:
					if(found == true) {
						check = true;
						System.out.println(divider);
						//Use merchant menu
						useMerchant(player,merchant,bag);
					}else {
						System.out.println(divider + "\nPlease choose a valid option.");
					}
					break;
					
				default:
					System.out.println(divider + "\nPlease choose a valid option.");
					break;
				}
			}while(check == false);
			
			
			//Enemy moves
			for(int i = 0; i != enemyMoveCount; i++) {
				if(mobList.get(i).getMapX() != 0 && mobList.get(i).getMapY() != 0) {
				//Move towards player
				enemyMove(player, bag, map, mobList.get(i),i);
				}
			}

			//Save stats and bags
			Main.bagUpdater(player,bag);
			Main.playerUpdater(player);
		}
	}
	
	public static void enemyMove(Player player, Bag bag, char[][] map, Mob1 enemy,int i) throws InterruptedException, IOException {
		int x = 0;
		//Checks to see if the player moved
		if(movementCheck == true) {
			//Checks if the mob is in 0,0
			if(map[enemy.getMapX()][enemy.getMapY()] != map[0][0]) {
				// Left
				if(enemy.getMapX() > player.getMapX()) {
					for(int z = 0; z != collisionSet.length; z++) {
						if(map[enemy.getMapX()-1][enemy.getMapY()] != (collisionSet[z])) {
							x++;
							//If X has enough clears, it'll pass this check
							if(x == collisionSet.length) {
								map[enemy.getMapX()][enemy.getMapY()] = ' ';
								map[enemy.getMapX()-1][enemy.getMapY()] = enemy.getType();
								enemy.setMapX(enemy.getMapX()-1);
							}
						}
					}
				// Up
				}else if(enemy.getMapY() > player.getMapY()){
					for(int z = 0; z != collisionSet.length; z++) {
						if(map[enemy.getMapX()][enemy.getMapY()-1] != (collisionSet[z])) {
							x++;
							//If X has enough clears, it'll pass this check
							if(x == collisionSet.length) {
								map[enemy.getMapX()][enemy.getMapY()] = ' ';
								map[enemy.getMapX()][enemy.getMapY()-1] = enemy.getType();
								enemy.setMapY(enemy.getMapY()-1);
							}
						}
					}	
				// Down
				}else if(enemy.getMapY() < player.getMapY()){
					for(int z = 0; z != collisionSet.length; z++) {
						if(map[enemy.getMapX()][enemy.getMapY()+1] != (collisionSet[z])) {
							x++;
							//If X has enough clears, it'll pass this check
							if(x == collisionSet.length) {
								map[enemy.getMapX()][enemy.getMapY()] = ' ';
								map[enemy.getMapX()][enemy.getMapY()+1] = enemy.getType();
								enemy.setMapY(enemy.getMapY()+1);
							}
						}
					}
				// Right
				}else if(enemy.getMapX() < player.getMapX()) {
					for(int z = 0; z != collisionSet.length; z++) {
						if(map[enemy.getMapX()+1][enemy.getMapY()] != (collisionSet[z])) {
							x++;
							//If X has enough clears, it'll pass this check
							if(x == collisionSet.length) {
								map[enemy.getMapX()][enemy.getMapY()] = ' ';
								map[enemy.getMapX()+1][enemy.getMapY()] = enemy.getType();
								enemy.setMapX(enemy.getMapX()+1);
							}
						}
			        }
				}
			}
		}
	}
	
	public static void playerMove(char[][] map, Player player, Bag bag) throws Exception {
		
		//Global var reset
		movementCheck = false;
		
		//While something is in the way, remain false
		while(movementCheck == false) {
			
			//Prompts the user to move in a direction
			System.out.println("Please make a selection: \n"
					+ "1: Up\n"
					+ "2: Down\n"
					+ "3: Left\n"
					+ "4: Right\n"
					+ "5: Stand Ground");
			
			//Ask user for movement input
			int x = 0;
			int temp2 = input.nextInt();
			System.out.println(divider);
			switch(temp2) {
			
			case 1:
				//Up
				//Cycle through array to see if item is in the way of player
				for(int i = 0; i != collisionSet.length; i++) {
					if(map[player.getMapX()][player.getMapY()-1] != (collisionSet[i])) {
						x++;
						//If X has enough clears, it'll pass this check
						if(x == collisionSet.length) {
							movementCheck = true;
						}
					}
		        }
				if(movementCheck == true){
				    //Move right and print map
					map[player.getMapX()][player.getMapY()] = ' ';
					map[player.getMapX()][player.getMapY()-1] = 'P';
					player.setMapY(player.getMapY()-1);
					playerCheckAttack(player,map,bag);
					break;
				} else {
					//Print map and tell the player to move elsewhere
				   	printMap(map);
				    System.out.println("You cannot move in that direction.\n" + divider);
				    break;
				}
				
			case 2:
				//Down
				//Cycle through array to see if item is in the way of player
				for(int i = 0; i != collisionSet.length; i++) {
					if(map[player.getMapX()][player.getMapY()+1] != (collisionSet[i])) {
						x++;
						//If X has enough clears, it'll pass this check
						if(x == collisionSet.length) {
							movementCheck = true;
						}
					}
		        }
				if(movementCheck == true){
				    //Move right and print map
					map[player.getMapX()][player.getMapY()] = ' ';
					map[player.getMapX()][player.getMapY()+1] = 'P';
					player.setMapY(player.getMapY()+1);
					playerCheckAttack(player,map,bag);
					break;
				} else {
					//Print map and tell the player to move elsewhere
				   	printMap(map);
				    System.out.println("You cannot move in that direction.\n" + divider);
				    break;
				}
				
			case 3:
				//Left
				//Cycle through array to see if item is in the way of player
				for(int i = 0; i != collisionSet.length; i++) {
					if(map[player.getMapX()-1][player.getMapY()] != (collisionSet[i])) {
						x++;
						//If X has enough clears, it'll pass this check
						if(x == collisionSet.length) {
							movementCheck = true;
						}
					}
		        }
				if(movementCheck == true){
				    //Move right and print map
					map[player.getMapX()][player.getMapY()] = ' ';
					map[player.getMapX()-1][player.getMapY()] = 'P';
					player.setMapX(player.getMapX()-1);
					playerCheckAttack(player,map,bag);
					break;
				} else {
					//Print map and tell the player to move elsewhere
				   	printMap(map);
				    System.out.println("You cannot move in that direction.\n" + divider);
				    break;
				}
				
			case 4:
				//Right
				//Cycle through array to see if item is in the way of player
				for(int i = 0; i != collisionSet.length; i++) {
					if(map[player.getMapX()+1][player.getMapY()] != (collisionSet[i])) {
						x++;
						//If X has enough clears, it'll pass this check
						if(x == collisionSet.length) {
							movementCheck = true;
						}
					}
		        }
				if(movementCheck == true){
				    //Move right and print map
					map[player.getMapX()][player.getMapY()] = ' ';
					map[player.getMapX()+1][player.getMapY()] = 'P';
					player.setMapX(player.getMapX()+1);
					playerCheckAttack(player,map,bag);
					break;
				} else {
					//Print map and tell the player to move elsewhere
				   	printMap(map);
				    System.out.println("You cannot move in that direction.\n" + divider);
				    break;
				}
				
			case 5:
				//Stand Ground
				playerCheckAttack(player,map,bag);
				movementCheck = true;
				break;
			}
		}
	}
	
	public static void playerCheckAttack(Player player, char[][] map, Bag bag) throws Exception {

		//Compares the players location to the moblist locations
		for(int i = 0; i != enemyMoveCount; i++) {
			if(mobList.get(i).getMapX() != 0 && mobList.get(i).getMapY() != 0) {
				
				//check if above
		        if(player.getMapX() == mobList.get(i).getMapX() && player.getMapY()-1 == mobList.get(i).getMapY()){
		        	enemyFound(player, map, mobList.get(i), bag, i);
		        	i = 0;
		        }
		        //check if below
		        if(player.getMapX() == mobList.get(i).getMapX() && player.getMapY()+1 == mobList.get(i).getMapY()){
		        	enemyFound(player, map, mobList.get(i), bag, i);
		        	i = 0;
		        }
		        //check if left
		        if(player.getMapX()-1 == mobList.get(i).getMapX() && player.getMapY() == mobList.get(i).getMapY()){
		        	enemyFound(player, map, mobList.get(i), bag, i);
		        	i = 0;
		        }
		        //check if right
		        if(player.getMapX()+1 == mobList.get(i).getMapX() && player.getMapY() == mobList.get(i).getMapY()){
		        	enemyFound(player, map, mobList.get(i), bag, i);
		        	i = 0;
		        
				}
			}
		}
	}
	
	public static void enemyFound(Player player,char[][] map, Mob1 enemy, Bag bag, int i) throws Exception {

        //Initiate the fight
        System.out.println("Battle Start!");
        boolean winStatus = Fight.Move(player, enemy, null, bag);
        
        //Update the stats and bags
		Main.bagUpdater(player,bag);
		Main.playerUpdater(player);

		//If the player wins
		if(winStatus == true) {
			//Reduce the enemyCount
			enemyCount--;
			
			//Increase enemyKillCount
			enemyKillCount++;

			//Remove the space from the map
			map[mobList.get(i).getMapX()][mobList.get(i).getMapY()] = ' ';
			
			//Move the enemy to 0,0
			mobList.get(i).setMapX(0);
			mobList.get(i).setMapY(0);

			//Return to player menu
			playerMenu(player, map, bag, firstLine);
        }
		
		// If you lose; return to main
		if(winStatus == false) {
			Main.main(null);
		}
    }
		
	//Prints the map for the player
	public static void printMap(char[][] map) {
		System.out.println("Playing Field:\n");
		
		//Top border
		for(int i = 0; i != cols*2+cols+2; i++) {
			System.out.print("_");
		}
		System.out.println();
		
		//Print map
		for (int y=0; y < rows; y++) {
			System.out.print("|");
		    for (int x=0; x < cols; x++) {
		        System.out.print(" " + map[x][y] + " ");
		    }
		    System.out.println("|");
		}
		
		//Bottom border
		for(int i = 0; i != cols*2+cols+2; i++) {
			System.out.print("-");
		}
		System.out.println();
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
		    	
		    	//If an F is found, create the object and add it to the arraylist: mobList
		        if(map[x][y] == 'F') {
		        	enemyCount++;
		        	enemyCreate(y,x,'F');
		        }
		        //If an K is found, create the object and add it to the arraylist: mobList
		        if(map[x][y] == 'K') {
		        	enemyCount++;
		        	enemyCreate(y,x,'K');
		        }
		        //If an G is found, create the object and add it to the arraylist: mobList
		        if(map[x][y] == 'G') {
		        	enemyCount++;
		        	enemyCreate(y,x,'G');
		        }
		        
		        //If P is found, save the location 
		        if(map[x][y] == 'P') {
		        	player.setMapY(y);
		        	player.setMapX(x);
		        }
		        
		        if(map[x][y] == 'M') {
		        	merchant.setMapX(x);
		        	merchant.setMapY(y);
		        }
		    }
		}
		//returns total enemies
		return enemyCount;
	}
	
	public static boolean findMerchant(char[][] map, Merchant merchant, Player player) {
		boolean found = false;
		
		//check if above
        if(map[player.getMapX()][player.getMapY()-1] == map[merchant.getMapX()][merchant.getMapY()]){
        	found = true;
        }
        //check if below
        if(map[player.getMapX()][player.getMapY()+1] == map[merchant.getMapX()][merchant.getMapY()]){
        	found = true;
        }
        //check if left
        if(map[player.getMapX()-1][player.getMapY()] == map[merchant.getMapX()][merchant.getMapY()]){
        	found = true;
        }
        //check if right
        if(map[player.getMapX()+1][player.getMapY()] == map[merchant.getMapX()][merchant.getMapY()]){
        	found = true;
		}
        
        //return found
		return found;
	}
	
	public static void useMerchant(Player player,Merchant merchant,Bag bag) throws IOException {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.println("Merchant Menu\nCurrent Gold:" + player.getGold() + "\n" + divider + "\nWhat item would you like to buy?: \n"
				+ "1: " + bag.getPotions() + " \t- Potions (250g)\n"
				+ "2: " + bag.getBoosters() + " \t- Boosters (250g)\n"
				+ "3: " + (bag.getWeapon()+1) + "/4 \t- Weapon Upgrade (1000g)\n"
				+ "4: " + (bag.getShield()+1) + "/4 \t- Shield Upgrade (1000g)\n"
				+ "5: Leave");
		
    	@SuppressWarnings("unused") boolean alreadyAtMax = false;
    	int amount = 0;
    	int userOption = scanner.nextInt();
		switch(userOption) {
			//Purchase potions
			case 1:
				while(amount <= 0) {
					System.out.println("How many would you like to purchase?");
					amount = scanner.nextInt();
					if(amount <= 0) {
						System.out.println("Please choose a number larger than 0\n" + divider);
					}
				}
				System.out.println(divider);
				player.purchase(amount*250);
				for(int i = 0; i != amount;i++) {
					bag.potions++;
				}
				System.out.println("You spent " + amount*250 + "g and recieved " + amount + " potion\n" + divider);
				break;
			
			//Purchase boosters
			case 2:
				while(amount <= 0) {
					System.out.println("How many would you like to purchase?");
					amount = scanner.nextInt();
					if(amount <= 0) {
						System.out.println("Please choose a number larger than 0\n" + divider);
					}
				}
				System.out.println(divider);
				player.purchase(amount*250);
				for(int i = 0; i != amount;i++) {
					bag.potions++;
				}
				System.out.println("You spent " + amount*250 + "g and recieved " + amount + " Boosters\n" + divider);
				break;
				
				//Upgrade weapon
			case 3:
				if(bag.weapon != 3) {
					bag.weapon++;
					player.purchase(1000);
					System.out.println("You spent 1000g and recieved a new weapon\n" + divider);

				}else {
					System.out.println("Your weapon is at it's max level.\n" + divider);
				}
				break;
				
				//Upgrade shield
			case 4:
				if(bag.shield != 3) {
					bag.shield++;
					player.purchase(1000);
					System.out.println("You spent 1000g and recieved a new shield\n" + divider);

				}else {
					System.out.println("Your shield is at it's max level.\n" + divider);
				}
				break;
				
			default:
				System.out.println("invalid selection\n" + divider);
		}
		Main.bagUpdater(player,bag);
		Main.playerUpdater(player);
	}
	
	private static void enemyCreate(int y, int x, char mobType) {

		if(mobType == 'F') {
			mobList.add(new Mob1("FootSoldier", 5, 5, 5, 50, 10, 2, 50, x, y, mobType));
		}
		
		if(mobType == 'K') {
			mobList.add(new Mob1("Knight", 5, 5, 5, 50, 10, 2, 75, x, y, mobType));
		}
		
		if(mobType == 'G') {
			mobList.add(new Mob1("General", 5, 5, 5, 50, 10, 2, 100, x, y, mobType));
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
