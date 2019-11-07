import java.io.BufferedReader;
import java.io.File;
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
	static int enemyNumber = 0;
	static String firstLine = "";
	static int turn = 0;
	static String mapName;
	static char[][] map = null;
	static boolean movementCheck = false;
	//Collision based items
	static char [] collisionSet = {'/','|','\\','_','-','F','P','K','G','M','L'};
	static String divider = "----------------------------------------------|";
	static boolean enemyFound = false;
	static String leaderName = null;
	static String[] splitStats = null;
	
	public static int map(Player player, Bag bag, String mapName) throws Exception {
		
		//Clear mobList for reuse
		mobList.clear();
		turn = 0;
		
		if (new File("src\\maps\\" + mapName + ".txt").exists()){
			//Continue if it does
			String[] batch = inputToString("src\\maps\\" + mapName + ".txt");
		    
	    	//Send the user to the map and read dialogue
	    	Dialogues.readDialogue(mapName);
	    	System.out.println("\n--------------\n");
	    	
	    	//Save mapName to a global String
	    	PlayingField.mapName = mapName;
	    	
			//Initialize vars
			firstLine = batch[0];
			String [] secondLine = batch[1].split(" ");
			String thirdLine = batch[2];
			String leaderStats = batch[3];
			String data = batch[4];

			//Breakdown the Leader information
			splitStats = leaderStats.split(" ");
			leaderName = splitStats[0];

			//parses the size of the grid and places it into rows and cols
			int[] stats = new int[secondLine.length];
			for (int i = 0; i < secondLine.length; i++) {
				String numberAsString = secondLine[i];
				stats[i] = Integer.parseInt(numberAsString);
			}
			rows = stats[0];
			cols = stats[1];
			int debug = stats[2];
			
			//Present dbug information
			if(debug == 1) {
				//Test additional map information
				System.out.println(firstLine);
				System.out.println(batch[1]);
				System.out.println(thirdLine);
			}
			
			//Saves the initial map
			map = saveMap(data);
			
			//Prints initial map
			System.out.println("Map: " + mapName + "\n" + divider);
			printMap(map);
	
			//Initial scan
			enemyCount = scanMap(player,map);
			enemyMoveCount = enemyCount;
			
			playerMenu(player, map, bag, firstLine);
			}else {
			System.out.println("Missing map .txt");
			return 1;
		}
		return 1;
	}
	
	public static boolean playerMenu(Player player, char[][] map, Bag bag, String firstLine) throws Exception {
		if(enemyCount == 0) {
			System.out.println("You beat level: " + firstLine + "\nReturning to main menu\n\n\n\n\n\n");
			return true;
		}
		
		while(enemyCount != 0){
			//Increment turn
			turn++;
			
			//Calculate the amount of moves the player has
			int moveCounter = player.getAgility() / 4;
			int moveCounterTemp = moveCounter;
			
			//Allow the player to move in any direction, use the bag
			//If found == true, give the user the option to use the merchant
			do {
				//Decides what menu will be presented to the user
				boolean merchantFound = findMerchant(map, merchant, player);
				
				//Decides what menu will be presented to the user
				int i = 0;
				for(i = 0; i != enemyMoveCount; i++) {
					if(mobList.get(i).getMapX() != 0 && mobList.get(i).getMapY() != 0) {
						enemyFound = findAttack(map, mobList.get(i), player);
						
						//Saves the array list position for later
						enemyNumber = i;
					}
				}
				
				if(moveCounter != moveCounterTemp) {
					printMap(map);
				}
				
				//Prints out enemyCount, user turn amount, and turn #
				System.out.println("Turn #" + turn);
				System.out.println(player.getName() + "'s movecount: " + moveCounter);
				System.out.println("Enemies remaining: " + enemyCount);
				
				//Print enemy locations
				printEnemyLocations();
				
				//Print out current user statistics
				System.out.println(divider);
				System.out.println("|" + player.getName() + "'s Hp: " + player.getCurrentHp() + "/" + player.getMaxHp() +"\t|Bonuses:");
				System.out.println("|LVL: " + player.getLevel() + "\t\t\t|Weapon: " + bag.getWeaponName() + "(+" + bag.getWeapon() + ")");
				System.out.println("|EXP: "+ player.exp + "/" + player.level*50 +"\t\t|Shield: " + bag.getShieldName() + "(+" + bag.getShield() + ")\n"
						+ divider);

				//Print out initial menu
				System.out.println("Please make a selection: \n"
						+ "1: Player move\n"
						+ "2: Bag Menu");
				
				//Print out merchant menu
				if(merchantFound == true) {
					System.out.println("3: Merchant Menu");
				}
				
				//Print out attack menu
				if(enemyFound == true) {
					System.out.println("4: Attack");
				}
				
				int temp = input.nextInt();
				switch(temp) {
				case 1:		
					//Player move first, then allow all enemies move
					moveCounter--;
					System.out.println(divider);
					playerMove(map, player, bag);
					break;
					
				case 2:
					//Uses the bag menu and update player information
					System.out.println(divider);
					Fight.useBag(player,bag,null);
					Main.bagUpdater(player,bag);
					Main.playerUpdater(player);
					moveCounter--;
					break;
				
				case 3:
					if(merchantFound == true) {
						moveCounter--;
						System.out.println(divider);
						//Use merchant menu
						useMerchant(player,merchant,bag);
					}else {
						System.out.println(divider + "\nPlease choose a valid option.");
					}
					break;	
					
				case 4:
					if(enemyFound == true) {
						moveCounter = 0;
						System.out.println(divider);
						//Use attack menu
						enemyFound(player, map, mobList.get(enemyNumber), bag, enemyNumber);
					}else {
						System.out.println(divider + "\nPlease choose a valid option.");
					}
					break;
					
				default:
					System.out.println(divider + "\nPlease choose a valid option.");
					break;
				}
			}while(moveCounter != 0);
			
			
			//Enemy moves
			for(int z = 0; z != enemyMoveCount; z++) {
				if(mobList.get(z).getMapX() != 0 && mobList.get(z).getMapY() != 0) {
				//Move towards player
				enemyMove(player, bag, map, mobList.get(z),z);
				}
			}

			//Save stats and bags
			Main.bagUpdater(player,bag);
			Main.playerUpdater(player);
		}
		return false;
	}

	/*
	 * I think its finally time to flesh out the enemy / player movement. Next steps to take:
	 * Allow players/enemies to move multiple spaces depending on agility
	 * Have smarter pathing for enemy ai
	 * have something like agility /3 = move amount
	 */
	
	public static void enemyMove(Player player, Bag bag, char[][] map, Mob1 enemy,int i) throws Exception {
		int x = 0;
		//Calculate the total moves that the enemy gets
		int moveCounter = enemy.getAgility() / 4;
		//Checks to see if the player moved
		if(movementCheck == true) {
			//While the enemy still has moves
			while(moveCounter != 0) {
				
				//Initialize easy to use variables
				char left = map[enemy.getMapX()-1][enemy.getMapY()];
				char right = map[enemy.getMapX()+1][enemy.getMapY()];
				char up = map[enemy.getMapX()][enemy.getMapY()-1];
				char down = map[enemy.getMapX()][enemy.getMapY()+1];
				char current = map[enemy.getMapX()][enemy.getMapY()+1];
				boolean check = false;
				
				//Checks if the mob is in 0,0
				if(current != map[0][0]) {
					
					// Left
					if(enemy.getMapX() > player.getMapX() && check == false) {
						for(int z = 0; z != collisionSet.length; z++) {
							//Checks collisionSet
							if(left != (collisionSet[z])) {
								x++;
								//If x has passed through all collisionSet, it'll pass this check
								if(x == collisionSet.length) {
									//Executes movement
									map[enemy.getMapX()][enemy.getMapY()] = ' ';
									map[enemy.getMapX()-1][enemy.getMapY()] = enemy.getType();
									enemy.setMapX(enemy.getMapX()-1);
									
									//Reset x while dropping moveCounter and printing the map for the player
									moveCounter--;
									x = 0;
									check = true;
									System.out.println(enemy.getName() + "'s Move\nMovecount: " + (moveCounter+1) + "\n" + divider);
									printMap(map);
									
									//Adds a small delay between movements to better visualize the moves
									Thread.sleep(1250);
									
									//Check if enemy can attack
									if(moveCounter != 0) {
										playerCheckAttack(player,map,bag);
									}
								}
							}
						}
					}

					//Reset x
					x = 0;
					// Right
					if(enemy.getMapX() < player.getMapX() && check == false) {
						for(int z = 0; z != collisionSet.length; z++) {
							//Checks collisionSet
							if(right != (collisionSet[z])) {
								x++;
								//If x has passed through all collisionSet, it'll pass this check
								if(x == collisionSet.length) {
									//Executes movement
									map[enemy.getMapX()][enemy.getMapY()] = ' ';
									map[enemy.getMapX()+1][enemy.getMapY()] = enemy.getType();
									enemy.setMapX(enemy.getMapX()+1);
									
									//Reset x while dropping moveCounter and printing the map for the player
									moveCounter--;
									x = 0;
									check = true;
									System.out.println(enemy.getName() + "'s Move\nMovecount: " + (moveCounter+1) + "\n" + divider);
									printMap(map);
									
									//Adds a small delay between movements to better visualize the moves
									Thread.sleep(1250);
									
									//Check if enemy can attack
									if(moveCounter != 0) {
										playerCheckAttack(player,map,bag);
									}
								}
							}
				        }
					}
					
					//Reset x
					x = 0;
					// Up
					if(enemy.getMapY() > player.getMapY() && check == false){
						for(int z = 0; z != collisionSet.length; z++) {
							//Checks collisionSet
							if(up != (collisionSet[z])) {
								x++;
								//If x has passed through all collisionSet, it'll pass this check
								if(x == collisionSet.length) {
									//Executes movement
									map[enemy.getMapX()][enemy.getMapY()] = ' ';
									map[enemy.getMapX()][enemy.getMapY()-1] = enemy.getType();
									enemy.setMapY(enemy.getMapY()-1);
									
									//Reset x while dropping moveCounter and printing the map for the player
									moveCounter--;
									x = 0;
									check = true;
									System.out.println(enemy.getName() + "'s Move\nMovecount: " + (moveCounter+1) + "\n" + divider);
									printMap(map);
									
									//Adds a small delay between movements to better visualize the moves
									Thread.sleep(1250);
									
									//Check if enemy can attack
									if(moveCounter != 0) {
										playerCheckAttack(player,map,bag);
									}
								}
							}
						}	
					}
					
					//Reset x
					x = 0;
					// Down
					if(enemy.getMapY() < player.getMapY() && check == false){
						for(int z = 0; z != collisionSet.length; z++) {
							//Checks collisionSet
							if(down != (collisionSet[z])) {
								x++;
								//If x has passed through all collisionSet, it'll pass this check
								if(x == collisionSet.length) {
									//Executes movement
									map[enemy.getMapX()][enemy.getMapY()] = ' ';
									map[enemy.getMapX()][enemy.getMapY()+1] = enemy.getType();
									enemy.setMapY(enemy.getMapY()+1);
									
									//Reset x while dropping moveCounter and printing the map for the player
									moveCounter--;
									x = 0;
									check = true;
									System.out.println(enemy.getName() + "'s Move\nMovecount: " + (moveCounter+1) + "\n" + divider);
									printMap(map);

									//Adds a small delay between movements to better visualize the moves
									Thread.sleep(1250);
									
									//Check if enemy can attack
									if(moveCounter != 0) {
										playerCheckAttack(player,map,bag);
									}
								}
							}
						}
					}
					//Enemies get stuck after bumping into each other
					
					//Code should go here
					if(check == false) {
						//Reset x while dropping moveCounter and printing the map for the player
						moveCounter--;
						x = 0;
						check = true;
						System.out.println(enemy.getName() + "'s Move\nMovecount: " + (moveCounter+1) + "\n" + divider);
						printMap(map);
						//Adds a small delay between movements to better visualize the moves
						Thread.sleep(1250);
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
					break;
				} else {
					//Print map and tell the player to move elsewhere
				   	printMap(map);
				    System.out.println("You cannot move in that direction.\n" + divider);
				    break;
				}
				
			case 5:
				//Stand Ground
				movementCheck = true;
				break;
				
			default:
				System.out.println("invalid selection\n" + divider);
			}
		}
	}
	
	public static void playerCheckAttack(Player player, char[][] map, Bag bag) throws Exception {

		//Compares the players location to the moblist locations
		for(int i = 0; i != enemyMoveCount; i++) {
			if(mobList.get(i).getMapX() != 0 && mobList.get(i).getMapY() != 0) {
				
				//check if above
		        if(player.getMapX() == mobList.get(i).getMapX() && player.getMapY()-1 == mobList.get(i).getMapY()){
		        	System.out.println(mobList.get(i).getName() + " is attacking you!");
		        	enemyFound(player, map, mobList.get(i), bag, i);
		        	i = 0;
		        }
		        //check if below
		        if(player.getMapX() == mobList.get(i).getMapX() && player.getMapY()+1 == mobList.get(i).getMapY()){
		        	System.out.println(mobList.get(i).getName() + " is attacking you!");
		        	enemyFound(player, map, mobList.get(i), bag, i);
		        	i = 0;
		        }
		        //check if left
		        if(player.getMapX()-1 == mobList.get(i).getMapX() && player.getMapY() == mobList.get(i).getMapY()){
		        	System.out.println(mobList.get(i).getName() + " is attacking you!");
		        	enemyFound(player, map, mobList.get(i), bag, i);
		        	i = 0;
		        }
		        //check if right
		        if(player.getMapX()+1 == mobList.get(i).getMapX() && player.getMapY() == mobList.get(i).getMapY()){
		        	System.out.println(mobList.get(i).getName() + " is attacking you!");
		        	enemyFound(player, map, mobList.get(i), bag, i);
		        	i = 0;
		        
				}
			}
		}
	}
	
	public static void enemyFound(Player player,char[][] map, Mob1 enemy, Bag bag, int i) throws Exception {

        //Initiate the fight
        System.out.println("Battle Start!");
        int winStatus = Fight.Move(player, enemy, null, bag);
        
        //Update the stats and bags
		Main.bagUpdater(player,bag);
		Main.playerUpdater(player);

		//If the player wins
		if(winStatus == 1) {
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
		if(winStatus == 0) {
			Main.main(null);
		}
		
		//If player runs from a fight, escape and allow the player to move
		if(winStatus == 2) {
			printMap(map);
			playerMenu(player, map, bag, firstLine);
		}
    }
		
	//Prints the map for the player
	public static void printMap(char[][] map) {
		//Top border
		for(int i = 0; i != cols*2+cols+2; i++) {
			System.out.print("_");
		}
		System.out.println();
		
		//Print map
		for (int y=0; y < rows; y++) {
			System.out.print("|");
		    for (int x=0; x < cols; x++) {
		        if(x % 2 == 0 && y % 2 == 0 && map[x][y] == ' ') {
		        	map[x][y] = ' ';
		        }
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
	
	
	public static void printEnemyLocations() {
		for(int i = 0; i != enemyMoveCount; i++) {
			if(mobList.get(i).getMapX() != 0 && mobList.get(i).getMapY() != 0) {
			//Print enemy locations
			System.out.print(mobList.get(i).getName() + ": \t (" + mobList.get(i).getMapX() + "," + mobList.get(i).getMapY + ") ");
			if((mobList.get(i).getMapX() < 10 && mobList.get(i).getMapY() < 10)) {
				System.out.print("\t");
			}
			System.out.print("\tHP:(" + mobList.get(i).getCurrentHp() + "/" + mobList.get(i).getMaxHp()+")\n");
			}
		}
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
		int F = 0,K = 0,G = 0,L = 0;
		
		for (int y=0; y < rows; y++) {
		    for (int x=0; x < cols; x++) {
		    	
		    	//If an F is found, create the object and add it to the arraylist: mobList
		        if(map[x][y] == 'F') {
		        	F++;
		        	enemyCount++;
		        	enemyCreate(y,x,'F',F);
		        }
		        //If an K is found, create the object and add it to the arraylist: mobList
		        if(map[x][y] == 'K') {
		        	K++;
		        	enemyCount++;
		        	enemyCreate(y,x,'K',K);
		        }
		        //If an G is found, create the object and add it to the arraylist: mobList
		        if(map[x][y] == 'G') {
		        	G++;
		        	enemyCount++;
		        	enemyCreate(y,x,'G',G);
		        }
		        
		        //If an G is found, create the object and add it to the arraylist: mobList
		        if(map[x][y] == 'L') {
		        	L++;
		        	enemyCount++;
		        	enemyCreate(y,x,'L',L);
		        }
		        
		        //If P is found, save the location 
		        if(map[x][y] == 'P') {
		        	player.setMapY(y);
		        	player.setMapX(x);
		        }
		        
		        //If M is found, save merchant location
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
	
	public static boolean findAttack(char[][] map, Mob1 enemy, Player player) {
		boolean found = false;
		
		//check if above
        if(map[player.getMapX()][player.getMapY()-1] == map[enemy.getMapX()][enemy.getMapY()]){
        	found = true;
        }
        //check if below
        if(map[player.getMapX()][player.getMapY()+1] == map[enemy.getMapX()][enemy.getMapY()]){
        	found = true;
        }
        //check if left
        if(map[player.getMapX()-1][player.getMapY()] == map[enemy.getMapX()][enemy.getMapY()]){
        	found = true;
        }
        //check if right
        if(map[player.getMapX()+1][player.getMapY()] == map[enemy.getMapX()][enemy.getMapY()]){
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
					bag.boosters++;
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
	
	private static void enemyCreate(int y, int x, char mobType, int enemyNum) {

		if(mobType == 'F') {
			if(enemyNum > 1) {
				mobList.add(new Mob1("FootSoldier " + enemyNum, 5, 8, 5, 50, 10, 2, 50, x, y, mobType));
			}else {
				mobList.add(new Mob1("FootSoldier", 5, 8, 5, 50, 10, 2, 50, x, y, mobType));
			}
		}
		
		if(mobType == 'K') {
			if(enemyNum > 1) {
				mobList.add(new Mob1("Knight " + enemyNum, 5, 5, 5, 50, 10, 2, 75, x, y, mobType));
			}else {
				mobList.add(new Mob1("Knight", 5, 5, 5, 50, 10, 2, 75, x, y, mobType));
			}
		}
		
		if(mobType == 'G') {
			if(enemyNum > 1) {
				mobList.add(new Mob1("General " + enemyNum, 5, 5, 5, 50, 10, 2, 100, x, y, mobType));
			}else {
				mobList.add(new Mob1("General", 5, 5, 5, 50, 10, 2, 100, x, y, mobType));
			}
		}
		
		if(mobType == 'L') {
			if(enemyNum > 1) {
				mobList.add(new Mob1(leaderName + " " + enemyNum, Integer.parseInt(splitStats[1]), 
						Integer.parseInt(splitStats[2]), Integer.parseInt(splitStats[3]), 
						Integer.parseInt(splitStats[4]), Integer.parseInt(splitStats[5]), 
						Integer.parseInt(splitStats[6]), Integer.parseInt(splitStats[7]), 
						x, y, mobType));
			}else {
				mobList.add(new Mob1(leaderName, Integer.parseInt(splitStats[1]), 
						Integer.parseInt(splitStats[2]), Integer.parseInt(splitStats[3]), 
						Integer.parseInt(splitStats[4]), Integer.parseInt(splitStats[5]), 
						Integer.parseInt(splitStats[6]), Integer.parseInt(splitStats[7]), 
						x, y, mobType));
			}
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
		String leaderStats = reader.readLine();
		
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
		String batch[] = new String[5];
		
		//saves all items to batch[]
		batch[0] = firstLine;
		batch[1] = secondLine;
		batch[2] = thirdLine;
		batch[3] = leaderStats;
		batch[4] = data;
		
		reader.close();
		return batch;
	} 
}
