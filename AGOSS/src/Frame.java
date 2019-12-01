//Usually you will require both swing and awt packages
// even if you are working with just swings.
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.imageio.ImageIO;
import javax.print.DocFlavor.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.SystemColor;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JViewport;

class Frame extends JFrame {
	final JFrame frame = new JFrame();
    String text = null;
    int turnNumber = 0;
    int moveCounter = 0;
    int enemies = 0;
    int userIntInput = 0;
    String userStringInput = "";
    public JTable enemyTable;
    public JTextPane mapArea;
    public JViewport viewport;
    public JScrollPane scrollPane;
    public StyledDocument doc;
    public JTextArea console;
    public JProgressBar expBar;
    public JPanel noWrapPanel;
    public JLabel mapName;
    public JLabel userStats;
    public JLabel mapType;
    public DefaultTableModel model;
    public JTextField turnNumberLable;
    public JTextField enemyCount;
    public JTextField moveCounterLabel;
    public JTextField hpField;
    public JTextField lvlField;
    public JTextField expField;
    private JTextField SHDField;
    private BufferedImage image;
    private JTextField WPNField;
    
    public Frame() {
        frame.getContentPane().setForeground(Color.DARK_GRAY);
        frame.getContentPane().setBackground(SystemColor.activeCaption);
        frame.getContentPane().setFont(new Font("Roboto", Font.PLAIN, 12));

        //Creating the Frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(730, 700);

		mapArea = new JTextPane();
		mapArea.setEditable(false);
		scrollPane = new JScrollPane(mapArea);
		
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(null);
		scrollPane.setBounds(26, 36, 484, 386);
		
		mapArea.setBackground(new Color(112, 128, 144));
		mapArea.setForeground(Color.white);
		mapArea.setFont(new Font("Monospaced", Font.PLAIN, 15));

        viewport = new JViewport() {
            @Override
            protected void paintComponent(Graphics g) {
              g.drawImage(image, 0, 0, this);
            }
          };

        scrollPane.setViewport(viewport);
        scrollPane.setViewportView(mapArea);
    	
		
    	doc = mapArea.getStyledDocument();
    	
    	//Print to mapArea
		System.setOut(new PrintStream(new OutputStream() {
    	public void write(int b) throws IOException {
    		try {
				doc.insertString(doc.getLength(), String.valueOf((char) b), null);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }));
    	frame.getContentPane().setLayout(null);
    	frame.getContentPane().add(scrollPane);

        //Creating the panel at bottom and adding components
        JPanel panel = new JPanel(); // the panel is not visible in output
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setBounds(0, 628, 885, 33);
        JLabel lblCommandLine = new JLabel("Command Line:");
        lblCommandLine.setBounds(10, 9, 96, 14);
        JTextField tf = new JTextField(10); // accepts upto 10 characters
        tf.setBounds(97, 6, 432, 20);
        panel.setLayout(null);
        panel.add(lblCommandLine); // Components Added using Flow Layout
        panel.add(lblCommandLine); // Components Added using Flow Layout
        panel.add(tf);
    	
        //Adding Components to the frame.
        frame.getContentPane().add(panel);
        JButton send = new JButton("Enter");
        send.setBounds(539, 5, 67, 23);
        panel.add(send);
        JButton reset = new JButton("Reset");
        reset.setBounds(628, 5, 67, 23);
        panel.add(reset);
        
        mapName = new JLabel("Main Menu:");
        mapName.setBounds(26, 11, 71, 14);
        frame.getContentPane().add(mapName);
        
        JLabel lblEnemyCount = new JLabel("EnemyCount: ");
        lblEnemyCount.setBounds(437, 11, 86, 14);
        frame.getContentPane().add(lblEnemyCount);
        
        JLabel lblHP = new JLabel("HP:");
        lblHP.setBounds(520, 411, 30, 14);
        frame.getContentPane().add(lblHP);
        
        JLabel lblTurn = new JLabel("Turn #");
        lblTurn.setBounds(520, 368, 46, 14);
        frame.getContentPane().add(lblTurn);
        
        model = new DefaultTableModel(); 
        enemyTable = new JTable(model);
        enemyTable.setRowSelectionAllowed(false);
        enemyTable.setBackground(new Color(112, 128, 144));
        enemyTable.setToolTipText("");
        enemyTable.setBounds(520, 36, 188, 316);
        model.addColumn("Type");
        model.addColumn("Coords");
        model.addColumn("HP");
        model.addRow(new Object[]{"Type", "Coords", "HP"});
        frame.getContentPane().add(enemyTable);
        
        turnNumberLable = new JTextField();
        turnNumberLable.setText("0");
        turnNumberLable.setEditable(false);
        turnNumberLable.setColumns(10);
        turnNumberLable.setBounds(565, 365, 21, 20);
        frame.getContentPane().add(turnNumberLable);
        
        enemyCount = new JTextField();
        enemyCount.setText("0");
        enemyCount.setEditable(false);
        enemyCount.setColumns(10);
        enemyCount.setBounds(523, 8, 21, 20);
        frame.getContentPane().add(enemyCount);
        
        JLabel lblExp = new JLabel("EXP:");
        lblExp.setBounds(520, 461, 39, 14);
        frame.getContentPane().add(lblExp);
        
        JLabel lblLvl = new JLabel("LVL:");
        lblLvl.setBounds(520, 436, 39, 14);
        frame.getContentPane().add(lblLvl);
        
        JLabel lblShield = new JLabel("Shield:");
        lblShield.setBounds(520, 568, 71, 14);
        frame.getContentPane().add(lblShield);
        
        JLabel lblWeapon = new JLabel("Weapon:");
        lblWeapon.setBounds(520, 543, 71, 14);
        frame.getContentPane().add(lblWeapon);
        
        JLabel lblBonuses = new JLabel("Bonuses:");
        lblBonuses.setBounds(520, 518, 71, 14);
        frame.getContentPane().add(lblBonuses);
        
        console = new JTextArea(24, 80);
        console.setEditable(false);
        console.setForeground(Color.WHITE);
        console.setFont(new Font("Monospaced", Font.PLAIN, 15));
        console.setBackground(new Color(112, 128, 144));
        console.setBounds(26, 427, 484, 190);
        frame.getContentPane().add(console);
        
        JLabel lblUsersMovecount = new JLabel("User's Movecount:");
        lblUsersMovecount.setBounds(576, 11, 111, 14);
        frame.getContentPane().add(lblUsersMovecount);
        
        moveCounterLabel = new JTextField();
        moveCounterLabel.setText("0");
        moveCounterLabel.setEditable(false);
        moveCounterLabel.setColumns(10);
        moveCounterLabel.setBounds(687, 8, 21, 20);
        frame.getContentPane().add(moveCounterLabel);
        
        hpField = new JTextField();
        hpField.setHorizontalAlignment(SwingConstants.RIGHT);
        hpField.setText("0");
        hpField.setEditable(false);
        hpField.setColumns(10);
        hpField.setBounds(565, 411, 122, 20);
        frame.getContentPane().add(hpField);
        
        lvlField = new JTextField();
        lvlField.setHorizontalAlignment(SwingConstants.RIGHT);
        lvlField.setText("0");
        lvlField.setEditable(false);
        lvlField.setColumns(10);
        lvlField.setBounds(565, 436, 122, 20);
        frame.getContentPane().add(lvlField);
        
        expField = new JTextField();
        expField.setHorizontalAlignment(SwingConstants.RIGHT);
        expField.setText("0");
        expField.setEditable(false);
        expField.setColumns(10);
        expField.setBounds(565, 461, 122, 20);
        frame.getContentPane().add(expField);
        
        SHDField = new JTextField();
        SHDField.setHorizontalAlignment(SwingConstants.RIGHT);
        SHDField.setText("0");
        SHDField.setEditable(false);
        SHDField.setColumns(10);
        SHDField.setBounds(576, 568, 111, 20);
        frame.getContentPane().add(SHDField);
        
        WPNField = new JTextField();
        WPNField.setHorizontalAlignment(SwingConstants.RIGHT);
        WPNField.setText("0");
        WPNField.setEditable(false);
        WPNField.setColumns(10);
        WPNField.setBounds(576, 543, 111, 20);
        frame.getContentPane().add(WPNField);
        frame.setVisible(true);
        
        //Sets send as the default key
        frame.getRootPane().setDefaultButton(send);
        
        expBar = new JProgressBar();
        expBar.setBounds(520, 486, 167, 14);
        frame.getContentPane().add(expBar);
        
        JLabel divider = new JLabel("_______________________");
        divider.setFont(new Font("Tahoma", Font.PLAIN, 12));
        divider.setBounds(520, 501, 167, 14);
        frame.getContentPane().add(divider);
        
        userStats = new JLabel("Users Stats:");
        userStats.setBounds(520, 386, 162, 14);
        frame.getContentPane().add(userStats);
        
        mapType = new JLabel("Objective:");
        mapType.setBounds(123, 11, 288, 14);
        frame.getContentPane().add(mapType);
        
        //Reset button listener
        reset.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
               tf.setText("");
            }
        });
        
        //Send button listener
        send.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                text  = tf.getText();
                tf.setText("");
            	setUserInput(text);
            }
        });
    }
    
    //USER INPUT----------------------------------------------------------
    public void setUserInput(String input) {
    	if(input.matches("^\\d+(\\.\\d+)?")) {
    		userIntInput = Integer.parseInt(input);
    	} else {
    		userStringInput = input;
    	}
    }

    public int getUserIntInput() {
    	//Returns the userinput as an Int
        return userIntInput;
    }
    
    public String getUserStringInput() {
    	//Returns the userinput as a String
        return userStringInput;
    }
    
    //PRINT IMAGES---------------------------------------------------------
    
    public void printIcon(char type) {
    	mapArea.setSelectionStart(doc.getLength());
    	
    	if(type == 'P') {
    		mapArea.insertIcon(new ImageIcon("src\\tilesets\\Player.png"));
    		return;
    	}
    	
    	if(type == 'b') {
    		mapArea.insertIcon(new ImageIcon("src\\tilesets\\Background.png"));
    		return;
    	}
    	
    	System.out.print(type);
    	
    }
    
    //SET MAP BACKGROUND--------------------------------------------------
    
    public void setBackground(String bg) {
    	
    	mapArea.setOpaque(false);
    	
    	try {
            image = ImageIO.read(new File("src\\tilesets\\" + bg + ".png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void removeBackground() {
    	mapArea.setOpaque(true);
    }
    
    //MAP AREA----------------------------------------------------------
    public void setUserStats(String name) {
    	String formatName = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
        this.userStats.setText(formatName + "'s Stats:");
    }
    
    public void clearMapArea() throws InterruptedException {
    	Thread.sleep(500);
    	this.mapArea.setText("");
    }
    
    //CONSOLE--------------------------------------------------------------
    
    public void clearConsole() throws InterruptedException {
    	Thread.sleep(500);
        this.console.selectAll();
        this.console.replaceSelection("");
    }
    
    //ENEMY TABLE----------------------------------------------------------
    public void setEnemyTable() {
        //Created for later
    }
    
    public void clearEnemyTable() throws InterruptedException {
    	model.setRowCount(0);
    	model.addRow(new Object[]{"Type", "Coords", "HP"});
    }
    
    //TURN NUMBER----------------------------------------------------------
    public int setTurnNumber(int x) {
	    this.turnNumber = x;
	    turnNumberLable.setText(Integer.toString(turnNumber));
        return turnNumber;
    }

    public int getTurnNumber() {
        return turnNumber;
    }
    
    //ENEMY COUNT----------------------------------------------------------
    public int setEnemies(int x) {
    	this.enemies = x; 
    	enemyCount.setText(Integer.toString(enemies));
        return enemies;
    }
    
    public int getEnemies() {
        return enemies;
    }
    
    //HP COUNT----------------------------------------------------------
    public void setHP(int current, int total) {
    	hpField.setText(Integer.toString(current) + "/" + Integer.toString(total));
    }
    
    //LVL COUNT----------------------------------------------------------
    public void setLVL(int lvl) {
    	lvlField.setText(Integer.toString(lvl));
    }
    
    //EXP COUNT----------------------------------------------------------
    public void setEXP(int current, int lvl) {
    	expField.setText(Integer.toString(current) + "/" + Integer.toString(lvl*50));
    	expBar.setMaximum(lvl*50);
    	expBar.setMinimum(0);
    	expBar.setValue(current);
    }
    
    //WPN COUNT----------------------------------------------------------
    public void setWPN(int WPN, String WPNName) {
    	WPNField.setText(WPNName + "(+" + Integer.toString(WPN) + ")\n");
    }
    
    //SHD COUNT----------------------------------------------------------
    public void setSHD(int SHD, String SHDName) {
    	SHDField.setText(SHDName + "(+" + Integer.toString(SHD) + ")\n");
    }
    
    //MOVE COUNT----------------------------------------------------------
    public int setmoveCounter(int x) {
    	this.moveCounter = x; 
    	moveCounterLabel.setText(Integer.toString(moveCounter));
        return moveCounter;
    }
    
    public int getmoveCounter() {
        return moveCounter;
    }
    

    //MAP NAME----------------------------------------------------------
    public JLabel getMapName() {
        return mapName;
    }
    
    public JLabel setMapName(String map) {
    	String formatMap = map.substring(0,1).toUpperCase() + map.substring(1).toLowerCase();
    	mapName.setText("Map: " + formatMap);
        return mapName;
    }
    
    //MAP TYPE----------------------------------------------------------
    
    public JLabel setMapType(int type) {
    	String formatType = "";
    	
    	//Decides what tpe of map is being played
    	if(type == 0) {
    		formatType = "Eleminate all enemies";
    	}
    	
    	if(type == 1) {
    		formatType = "Reach point X";
    	}
    	
    	//Set the objective on GUI
    	mapType.setText("Objective: " + formatType);
        return mapType;
    }
    
	public void init() {
		this.pack();
		this.setVisible(false);
		this.setAlwaysOnTop(true);

	}
	
	public JFrame getFrame() {
		return this;
	}
	
	public static void grabInput(Frame frame, int type) throws InterruptedException {
		
		//If type == 0, Grab int
		//If type == 1, Grab String		
		//Wait for user input
		do{
		    Thread.sleep(400);
		}while(frame.text == null);
		
		//After grabbing the selection, send it to user and reset text to null for later
		if(type == 0){
		    System.out.print(frame.getUserIntInput());

		}else if(type == 1) {
		    System.out.print(frame.getUserStringInput());
		}
		frame.text = null;
	}
}