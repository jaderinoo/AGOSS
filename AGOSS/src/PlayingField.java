import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.text.BadLocationException;

//NOTES
/*		
			//All in all, if the player is killed, end the game else 
			//if the enemycount goes to 0, show a "You Win + Mapname" 
			//Make sure to add a Mapname to the vars as the first 3 lines already allow for information
			
			//So it will work like this, Mainmenu -> Adventure/do shit -> Select a world to enter
			//-> complete the world and return to Adventure/ allow a save option in "Adventure"
 */

public class PlayingField extends JFrame {
	static int rows = 15;
	static int cols = 15;
	static ArrayList<Mob1> mobList = new ArrayList<Mob1>();
	static Merchant merchant = new Merchant(0,0);
	static Scanner input = new Scanner(System.in);
	public static Frame frame;
	static int enemyCount = 0;
	static int enemyMoveCount = 0;
	static int enemyKillCount = 0;
	static int enemyNumber = 0;
	static String levelName = "";
	static int turn = 0;
	static String mapName;
	static GridSpace [][] map = null;
	static boolean movementCheck = false;
	static String divider = "----------------------------------------------|";
	static boolean initiateFight = false;
	static String leaderName = null;
	static int mapType = 0;
	static int xCounter = 0;
	static String[] splitStats = null;
	
	public static boolean map(Player player, Bag bag, String mapName, Frame frame2) throws Exception {
		frame = frame2;
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
			levelName = batch[0];
			String [] secondLine = batch[1].split(" ");
			String background = batch[2];
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
		mapType = stats[2];
		
		//Saves the initial map
		map = saveMap(data);
		
		//Prints initial map
		frame.setMapName(mapName);
		frame.setMapType(mapType);
    	frame.setBackground(background);
		printMap(map);

		//Initial scan
		enemyCount = scanMap(player,map);
		enemyMoveCount = enemyCount;
		frame.setUserStats((String) player.getName());
		
		playerMenu(player, map, bag, levelName);
		}else {
		System.out.println("Missing " + mapName + ".txt");
		return false;
		}
		
		//If level is beat return to adventure
		frame.removeBackground();

		return true;
	}
	
	public static void playerMenu(Player player, GridSpace[][] map, Bag bag, String levelName) throws Exception {
		
		while(enemyCount != 0 && mapType == 0 || xCounter != 1){
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
						initiateFight = findAttack(map, mobList.get(i), player);
						
						//Saves the array list position for later
						enemyNumber = i;
					}
				}
				
				if(moveCounter != moveCounterTemp) {
					printMap(map);
				}
				
				//Prints out enemyCount, user turn amount, and turn #
				frame.setTurnNumber(turn);
				frame.setmoveCounter(moveCounter);
				frame.setEnemies(enemyCount);
				
				//Print enemy locations
				frame.clearEnemyTable();
				printEnemyLocations();
				
				//Print out current user statistics
				frame.setHP(player.getCurrentHp(), player.getMaxHp());
				frame.setLVL(player.getLevel());
				frame.setWPN(bag.getWeapon(), bag.getWeaponName());
				frame.setSHD(bag.getShield(), bag.getShieldName());
				frame.setEXP(player.exp, player.level);

				//Clear Console
				frame.clearConsole();
				
				//Print out initial menu
				frame.console.append("Please make a selection: \n"
						+ "1: Player move\n"
						+ "2: Bag Menu");
				
				//Print out merchant menu
				if(merchantFound == true) {
					frame.console.append("\n3: Merchant Menu");
				}
				
				//Print out attack menu
				if(initiateFight == true) {
					frame.console.append("\n4: Attack");
				}
				
				Frame.grabInput(((Frame)frame),0);
				int temp = ((Frame)frame).getUserIntInput();
				switch(temp) {
				case 1:		
					//Player move first, then allow all enemies move
					moveCounter--;
					playerMove(map, player, bag);
					break;
					
				case 2:
					//Uses the bag menu and update player information
					Fight.useBag(player,bag,null, ((Frame)frame));
					Main.bagUpdater(player,bag);
					Main.playerUpdater(player);
					moveCounter--;
					break;
				
				case 3:
					if(merchantFound == true) {
						moveCounter--;
						//Use merchant menu
						useMerchant(player,merchant,bag);
					}else {
						frame.console.append(divider + "\nPlease choose a valid option.");
					}
					break;	
					
				case 4:
					if(initiateFight == true) {
						moveCounter = 0;
						//Use attack menu
						initiateFight(player, map, mobList.get(enemyNumber), bag);
						
						//return to adventure if all enemies are cleared
						if(enemyCount == 0) {
							return;
						}
						
					}else {
						frame.console.append(divider + "\nPlease choose a valid option.");
					}
					break;
					
				default:
					frame.console.append(divider + "\nPlease choose a valid option.");
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
		return;
	}

	/*
	 * I think its finally time to flesh out the enemy / player movement. Next steps to take:
	 * Allow players/enemies to move multiple spaces depending on agility
	 * Have smarter pathing for enemy ai
	 * have something like agility /3 = move amount
	 */
	
	public static void enemyMove(Player player, Bag bag, GridSpace[][] map, Mob1 enemy,int i) throws Exception {
		
		//Print map
		printMap(map);

		//Calculate the total moves that the enemy gets
		int moveCounter = enemy.getAgility() / 4;
		//Checks to see if the player moved
		if(movementCheck == true) {
			
			//Clear Console
			frame.clearConsole();
			
			//Save current location to a var
			char current = map[enemy.getMapX()][enemy.getMapY()].getType();;
			
			//While the enemy still has moves
			while(moveCounter != 0) {
				//Checks if the mob is in 0,0
				if(current != map[0][0].getType()) {
					
					//Initialize easy to use variables
					char left = map[enemy.getMapX()-1][enemy.getMapY()].getType();
					char right = map[enemy.getMapX()+1][enemy.getMapY()].getType();
					char up = map[enemy.getMapX()][enemy.getMapY()-1].getType();
					char down = map[enemy.getMapX()][enemy.getMapY()+1].getType();
					boolean check = false;
					
					// Left
					if(enemy.getMapX() > player.getMapX() && check == false) {
						if(left == ' ') {
							//Executes movement
							map[enemy.getMapX()][enemy.getMapY()].setType(' ');
							map[enemy.getMapX()-1][enemy.getMapY()].setType(enemy.getType());
							enemy.setMapX(enemy.getMapX()-1);
							
							//drop movecounter down
							moveCounter--;

							check = true;
							frame.console.append(enemy.getName() + "'s Move: Left\nMovecount: " + (moveCounter+1) + "\n" + divider + "\n");
							printMap(map);
							
							//Adds a small delay between movements to better visualize the moves
							Thread.sleep(250);
							
							//Check if enemy can attack
							if(moveCounter != 0) {
								playerCheckAttack(player,map,bag,enemy);
							}

						}
					}

					// Right
					if(enemy.getMapX() < player.getMapX() && check == false) {
						if(right == ' ') {
							//Executes movement
							map[enemy.getMapX()][enemy.getMapY()].setType(' ');
							map[enemy.getMapX()+1][enemy.getMapY()].setType(enemy.getType());
							enemy.setMapX(enemy.getMapX()+1);
							
							//drop movecounter down
							moveCounter--;

							check = true;
							frame.console.append(enemy.getName() + "'s Move: Right\nMovecount: " + (moveCounter+1) + "\n" + divider + "\n");
							printMap(map);
							
							//Adds a small delay between movements to better visualize the moves
							Thread.sleep(250);
							
							//Check if enemy can attack
							if(moveCounter != 0) {
								playerCheckAttack(player,map,bag,enemy);
							}
						}
					}

					// Up
					if(enemy.getMapY() > player.getMapY() && check == false){
						if(up == ' ') {
							//Executes movement
							map[enemy.getMapX()][enemy.getMapY()].setType(' ');
							map[enemy.getMapX()][enemy.getMapY()-1].setType(enemy.getType());
							enemy.setMapY(enemy.getMapY()-1);
							
							//drop movecounter down
							moveCounter--;

							check = true;
							frame.console.append(enemy.getName() + "'s Move: Up\nMovecount: " + (moveCounter+1) + "\n" + divider + "\n");
							printMap(map);
							
							//Adds a small delay between movements to better visualize the moves
							Thread.sleep(250);
							
							//Check if enemy can attack
							if(moveCounter != 0) {
								playerCheckAttack(player,map,bag,enemy);
							}
						}
					}
					// Down
					if(enemy.getMapY() < player.getMapY() && check == false){
						if(down == ' ') {
							//Executes movement
							map[enemy.getMapX()][enemy.getMapY()].setType(' ');
							map[enemy.getMapX()][enemy.getMapY()+1].setType(enemy.getType());
							enemy.setMapY(enemy.getMapY()+1);
							
							//drop movecounter down
							moveCounter--;

							check = true;
							frame.console.append(enemy.getName() + "'s Move: Down\nMovecount: " + (moveCounter+1) + "\n" + divider + "\n");
							printMap(map);

							//Adds a small delay between movements to better visualize the moves
							Thread.sleep(250);
							
							//Check if enemy can attack
							if(moveCounter != 0) {
								playerCheckAttack(player,map,bag,enemy);
							}
						}
					}
						
					
					//Enemies get stuck after bumping into each other
					
					//Code should go here
					if(check == false) {
						//If standing still, check for attack
						if(moveCounter != 0) {
							playerCheckAttack(player,map,bag,enemy);
						}
						
						//Reset x while dropping moveCounter and printing the map for the player
						moveCounter--;
						
						check = true;
						frame.console.append("\n" + enemy.getName() + "'s Move: None\nMovecount: " + (moveCounter+1) + "\n" + divider);
						printMap(map);
						//Adds a small delay between movements to better visualize the moves
						Thread.sleep(250);
					}
				}
			}
		}
	}
	
	public static void playerMove(GridSpace[][] map, Player player, Bag bag) throws Exception {
		
		//Clear Console
		frame.clearConsole();
		
		//Global var reset
		movementCheck = false;
		
		//While something is in the way, remain false
		while(movementCheck == false) {
			
			//Prompts the user to move in a direction
			frame.console.append("Please make a selection: \n"
					+ "1: Up\n"
					+ "2: Down\n"
					+ "3: Left\n"
					+ "4: Right\n"
					+ "5: Stand Ground");
			
			char left = map[player.getMapX()-1][player.getMapY()].getType();
			char right = map[player.getMapX()+1][player.getMapY()].getType();
			char up = map[player.getMapX()][player.getMapY()-1].getType();
			char down = map[player.getMapX()][player.getMapY()+1].getType();
			
			//Ask user for movement input
			Frame.grabInput(((Frame)frame),0);
			int temp2 = ((Frame)frame).getUserIntInput();
			switch(temp2) {
			
			case 1:
				//Up
				if(up == ' '){
				    //Move right and print map
					map[player.getMapX()][player.getMapY()].setType(' ');
					map[player.getMapX()][player.getMapY()-1].setType('P');
					player.setMapY(player.getMapY()-1);
					movementCheck = true;
					break;
				} else {
					//Print map and tell the player to move elsewhere
				   	printMap(map);
				   	
					//Clear Console
					frame.clearConsole();
					
					//Present error message
				   	frame.console.append("You cannot move in that direction.\n" + divider + "\n");
				    break;
				}
				
			case 2:
				//Down
				if(down == ' '){
				    //Move right and print map
					map[player.getMapX()][player.getMapY()].setType(' ');
					map[player.getMapX()][player.getMapY()+1].setType('P');
					player.setMapY(player.getMapY()+1);
					movementCheck = true;
					break;
				} else {
					//Print map and tell the player to move elsewhere
				   	printMap(map);
				   	
					//Clear Console
					frame.clearConsole();
					
					//Present error message
				   	frame.console.append("You cannot move in that direction.\n" + divider + "\n");
				    break;
				}
				
			case 3:
				//Left
				if(left == ' '){
				    //Move right and print map
					map[player.getMapX()][player.getMapY()].setType(' ');
					map[player.getMapX()-1][player.getMapY()].setType('P');
					player.setMapX(player.getMapX()-1);
					movementCheck = true;
					break;
				} else {
					//Print map and tell the player to move elsewhere
				   	printMap(map);
				   	
					//Clear Console
					frame.clearConsole();
					
					//Present error message
				   	frame.console.append("You cannot move in that direction.\n" + divider + "\n");
				    break;
				}
				
			case 4:
				//Right
				if(right == ' '){
				    //Move right and print map
					map[player.getMapX()][player.getMapY()].setType(' ');
					map[player.getMapX()+1][player.getMapY()].setType('P');
					player.setMapX(player.getMapX()+1);
					movementCheck = true;
					break;
				} else {
					//Print map and tell the player to move elsewhere
				   	printMap(map);
				   	
					//Clear Console
					frame.clearConsole();
					
					//Present error message
				   	frame.console.append("You cannot move in that direction.\n" + divider + "\n");
				    break;
				}
				
			case 5:
				//Stand Ground
				movementCheck = true;
				break;
				
			default:
				//Clear Console
				frame.clearConsole();
				
				//Present error message
				frame.console.append("invalid selection\n" + divider);
			}
		}
	}
	
	public static void playerCheckAttack(Player player, GridSpace[][] map, Bag bag, Mob1 enemy) throws Exception {

			if(enemy.getMapX() != 0 && enemy.getMapY() != 0) {
				
				//check if above
		        if(player.getMapX() == enemy.getMapX() && player.getMapY()-1 == enemy.getMapY()){
		        	frame.console.append(enemy.getName() + " is attacking you!");
		        	initiateFight(player, map, enemy, bag);
		        	
		        }
		        //check if below
		        if(player.getMapX() == enemy.getMapX() && player.getMapY()+1 == enemy.getMapY()){
		        	frame.console.append(enemy.getName() + " is attacking you!");
		        	initiateFight(player, map, enemy, bag);
		        	
		        }
		        //check if left
		        if(player.getMapX()-1 == enemy.getMapX() && player.getMapY() == enemy.getMapY()){
		        	frame.console.append(enemy.getName() + " is attacking you!");
		        	initiateFight(player, map, enemy, bag);
		        	
		        }
		        //check if right
		        if(player.getMapX()+1 == enemy.getMapX() && player.getMapY() == enemy.getMapY()){
		        	frame.console.append(enemy.getName() + " is attacking you!");
		        	initiateFight(player, map, enemy, bag);
		        	
		        
				}
			}
		
	}
	
	public static void initiateFight(Player player,GridSpace[][] map, Mob1 enemy, Bag bag) throws Exception {

        //Initiate the fight
        int winStatus = Fight.Move(player, enemy, null, bag, ((Frame)frame));
        
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
			map[enemy.getMapX()][enemy.getMapY()].setType(' ');
			
			//Move the enemy to 0,0
			enemy.setMapX(0);
			enemy.setMapY(0);

			//Return to player menu
			printMap(map);
			return;
        }
		
		// If you lose; return to main
		if(winStatus == 0) {
			Main.main(null);
		}
		
		//If player runs from a fight, escape and allow the player to move
		if(winStatus == 2) {
			printMap(map);
			return;
		}
    }
	
		
	//Prints the map for the player
	public static void printMap(GridSpace[][] map) throws InterruptedException, BadLocationException {
    	int x = 0;
    	int spacer = 10;
    	
    	//Clear map screen
    	frame.clearMapArea();
		
		//Top border
		for(int i = 0; i != cols*2+cols+2; i++) {
			int temp = i;
			
			if(i == rows) {
				break;
			}
			
			if(i < 10) {
				System.out.print("  " + i);
			}
			
			if(i >= 10) {
				spacer++;
				do {
					System.out.print(" ");
				}while(temp > spacer);
				
				System.out.print(i);
			}
		}
		
		//Separate top border from map
		System.out.println();
		
		//Print map
		for (int y=0; y < rows; y++) {
			System.out.print(y);
		    for (x=0; x < cols; x++) {
		    	
		    	//Saves current sprite
	        	char sprite = map[x][y].getType();

	        	//If item isnt blank, add delay
	        	if(sprite == ' ') {
	        		Thread.sleep(1);
	        	}

		        //Print first 10 items
		        if(x == 0 && y >= 10) {

		        	//Print item
		        	if(sprite != ' ') {
		   
		        		//Print item
		        		if(sprite == '_') {
		        			frame.printIcon(sprite);
		        			frame.printIcon(sprite);
		        		}else {
		        			frame.printIcon(sprite);
			        		System.out.print(" ");
		        		}
		        	} else {
		        	System.out.print(sprite + " ");	
		        	}	
		        	
		        } else {//Print rest 
		        	
		        	//Print item
		        	if(sprite != ' ') {
		        		
		        		//Print item
		        		if(sprite == '_') {
		        			frame.printIcon(sprite);
		        			frame.printIcon(sprite);
		        			frame.printIcon(sprite);
		        			//If sprite == a character, allow more room
		        		}else if(sprite == 'P' || sprite == 'F' || sprite == 'K' || sprite == 'G' || sprite == 'L'){
		        			frame.printIcon(sprite);
		        			System.out.print(" ");
		        		} else {
			        		System.out.print(" ");
		        			frame.printIcon(sprite);
			        		System.out.print(" ");
		        		}
		        	} else {
		        	System.out.print(" " + sprite + " ");	
		        	}
		        }
		    }
		    System.out.println(" ");
		}
	}
	
	
	public static void printEnemyLocations() {
		for(int i = 0; i != enemyMoveCount; i++) {
			if(mobList.get(i).getMapX() != 0 && mobList.get(i).getMapY() != 0) {
			//Print enemy locations
			frame.model.addRow(new Object[]{mobList.get(i).getName(), "(" + mobList.get(i).getMapX() + "," + mobList.get(i).getMapY + ")", "(" + mobList.get(i).getCurrentHp() + "/" + mobList.get(i).getMaxHp()+")"});
			}
		}
	}
	
	//Saves map as a local variable
	public static GridSpace[][] saveMap(String data){
		GridSpace [][] map = new GridSpace[rows][cols];
		int next = 0;
		double maxRow = 0;
		maxRow = Math.sqrt(data.length());
		for (int y=0; y < maxRow; y++) {
		    for (int x=0; x < maxRow; x++) {
		    	char type = data.charAt(next++);
		    	map[x][y] = new GridSpace(type);
		    }
		}
		return map;
	}
	
	//Scans the map for units
	public static int scanMap(Player player, GridSpace[][] map) {
		int enemyCount = 0;
		int F = 0,K = 0,G = 0,L = 0;
		
		for (int y=0; y < rows; y++) {
		    for (int x=0; x < cols; x++) {
		    	
		    	//If an F is found, create the object and add it to the arraylist: mobList
		        if(map[x][y].getType() == 'F') {
		        	F++;
		        	enemyCount++;
		        	enemyCreate(y,x,'F',F);
		        }
		        //If an K is found, create the object and add it to the arraylist: mobList
		        if(map[x][y].getType() == 'K') {
		        	K++;
		        	enemyCount++;
		        	enemyCreate(y,x,'K',K);
		        }
		        //If an G is found, create the object and add it to the arraylist: mobList
		        if(map[x][y].getType() == 'G') {
		        	G++;
		        	enemyCount++;
		        	enemyCreate(y,x,'G',G);
		        }
		        
		        //If an L is found, create the object and add it to the arraylist: mobList
		        if(map[x][y].getType() == 'L') {
		        	L++;
		        	enemyCount++;
		        	enemyCreate(y,x,'L',L);
		        }
		        
		        //If P is found, save the location 
		        if(map[x][y].getType() == 'P') {
		        	player.setMapY(y);
		        	player.setMapX(x);
		        }
		        
		        //If M is found, save merchant location
		        if(map[x][y].getType() == 'M') {
		        	merchant.setMapX(x);
		        	merchant.setMapY(y);
		        }
		    }
		}
		//returns total enemies
		return enemyCount;
	}
	
	public static boolean findMerchant(GridSpace[][] map, Merchant merchant, Player player) {
		boolean found = false;
		
		//check if above
        if(map[player.getMapX()][player.getMapY()-1].getType() == map[merchant.getMapX()][merchant.getMapY()].getType()){
        	found = true;
        }
        //check if below
        if(map[player.getMapX()][player.getMapY()+1].getType() == map[merchant.getMapX()][merchant.getMapY()].getType()){
        	found = true;
        }
        //check if left
        if(map[player.getMapX()-1][player.getMapY()].getType() == map[merchant.getMapX()][merchant.getMapY()].getType()){
        	found = true;
        }
        //check if right
        if(map[player.getMapX()+1][player.getMapY()].getType() == map[merchant.getMapX()][merchant.getMapY()].getType()){
        	found = true;
		}
        
        //return found
		return found;
	}
	
	public static boolean findAttack(GridSpace[][] map, Mob1 enemy, Player player) {
		boolean found = false;
		
		//check if above
        if(map[player.getMapX()][player.getMapY()-1].getType() == map[enemy.getMapX()][enemy.getMapY()].getType()){
        	found = true;
        }
        //check if below
        if(map[player.getMapX()][player.getMapY()+1].getType() == map[enemy.getMapX()][enemy.getMapY()].getType()){
        	found = true;
        }
        //check if left
        if(map[player.getMapX()-1][player.getMapY()].getType() == map[enemy.getMapX()][enemy.getMapY()].getType()){
        	found = true;
        }
        //check if right
        if(map[player.getMapX()+1][player.getMapY()].getType() == map[enemy.getMapX()][enemy.getMapY()].getType()){
        	found = true;
		}
        
        //return found
		return found;
	}
	
	public static void useMerchant(Player player,Merchant merchant,Bag bag) throws IOException, InterruptedException {
		
		//Clear Console
		frame.clearConsole();
		
		//Presents merchant options
		frame.console.append("Merchant Menu\nCurrent Gold:" + player.getGold() + "\n" + divider + "\nWhat item would you like to buy?: \n"
				+ "1: " + bag.getPotions() + " \t- Potions (250g)\n"
				+ "2: " + bag.getBoosters() + " \t- Boosters (250g)\n"
				+ "3: " + (bag.getWeapon()+1) + "/4 \t- Weapon Upgrade (1000g)\n"
				+ "4: " + (bag.getShield()+1) + "/4 \t- Shield Upgrade (1000g)\n"
				+ "5: Leave");
		
    	@SuppressWarnings("unused") boolean alreadyAtMax = false;
    	int amount = 0;
    	
    	//Take user input
    	Frame.grabInput(((Frame)frame),0);
		int userOption = ((Frame)frame).getUserIntInput();
		
		//Clear Console
		frame.clearConsole();
		switch(userOption) {
			//Purchase potions
			case 1:
				while(amount <= 0) {
					frame.console.append("How many would you like to purchase?");
					Frame.grabInput(((Frame)frame),0);
					amount = ((Frame)frame).getUserIntInput();
					if(amount <= 0) {
						frame.console.append("Please choose a number larger than 0\n" + divider + "\n");
					}
				}
				((Frame)frame).clearConsole();
				player.purchase(amount*250, ((Frame)frame));
				for(int i = 0; i != amount;i++) {
					bag.potions++;
				}
				frame.console.append("You spent " + amount*250 + "g and recieved " + amount + " potion\n" + divider + "\n");
				break;
			
			//Purchase boosters
			case 2:
				while(amount <= 0) {
					frame.console.append("How many would you like to purchase?");
					Frame.grabInput(((Frame)frame),0);
					amount = ((Frame)frame).getUserIntInput();
					if(amount <= 0) {
						frame.console.append("Please choose a number larger than 0\n" + divider + "\n");
					}
				}
				((Frame)frame).clearConsole();
				player.purchase(amount*250, ((Frame)frame));
				for(int i = 0; i != amount;i++) {
					bag.boosters++;
				}
				frame.console.append("You spent " + amount*250 + "g and recieved " + amount + " Boosters\n" + divider + "\n");
				break;
				
				//Upgrade weapon
			case 3:
				if(bag.weapon != 3) {
					bag.weapon++;
					player.purchase(1000, ((Frame)frame));
					frame.console.append("You spent 1000g and recieved a new weapon\n" + divider + "\n");

				}else {
					frame.console.append("Your weapon is at it's max level.\n" + divider + "\n");
				}
				break;
				
				//Upgrade shield
			case 4:
				if(bag.shield != 3) {
					bag.shield++;
					player.purchase(1000, ((Frame)frame));
					frame.console.append("You spent 1000g and recieved a new shield\n" + divider + "\n");

				}else {
					frame.console.append("Your shield is at it's max level.\n" + divider + "\n");
				}
				break;
				
			default:
				frame.console.append("invalid selection\n" + divider + "\n");
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
