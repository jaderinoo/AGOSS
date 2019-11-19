//Usually you will require both swing and awt packages
// even if you are working with just swings.
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JList;

class Frame extends JFrame {
	final JFrame frame = new JFrame();
    String text = null;
    int turnNumber = 0;
    int moveCounter = 0;
    int enemies = 0;
    int userIntInput = 0;
    String userStringInput = "";
    public JTable enemyTable;
    public JTextArea mapArea;
    public JTextArea console;
    public JLabel mapName;
    public DefaultTableModel model;
    private JTextField turnNumberLable;
    private JTextField enemyCount;
    private JTextField moveCounterLabel;
    private JTextField textField_4;
    private JTextField textField_5;
    private JTextField textField_6;
    private JTextField textField_7;
    private JTextField textField_8;
    
    public Frame() {
        frame.getContentPane().setForeground(Color.DARK_GRAY);
        frame.getContentPane().setBackground(Color.DARK_GRAY);

        //Creating the Frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(730, 700);

        //Creating the panel at bottom and adding components
        JPanel panel = new JPanel(); // the panel is not visible in output
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setBounds(0, 628, 885, 33);
        JLabel lblCommandLine = new JLabel("Command Line:");
        lblCommandLine.setBounds(10, 9, 83, 14);
        JTextField tf = new JTextField(10); // accepts upto 10 characters
        tf.setBounds(103, 6, 367, 20);
        panel.setLayout(null);
        panel.add(lblCommandLine); // Components Added using Flow Layout
        panel.add(lblCommandLine); // Components Added using Flow Layout
        panel.add(tf);

		mapArea = new JTextArea(24, 80);
		mapArea.setBounds(26, 36, 500, 397);
		mapArea.setBackground(Color.black);
		mapArea.setForeground(Color.white);
		mapArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
		System.setOut(new PrintStream(new OutputStream() {
    	@Override
    	public void write(int b) throws IOException {
    		mapArea.append(String.valueOf((char) b));
    	}
    }));
    	frame.getContentPane().setLayout(null);
    	frame.getContentPane().add(mapArea);

        //Adding Components to the frame.
        frame.getContentPane().add(panel);
        JButton send = new JButton("Enter");
        send.setBounds(480, 5, 67, 23);
        panel.add(send);
        JButton reset = new JButton("Reset");
        reset.setBounds(557, 5, 67, 23);
        panel.add(reset);
        
        mapName = new JLabel("Map view:");
        mapName.setBounds(26, 11, 71, 14);
        frame.getContentPane().add(mapName);
        
        JLabel lblNewLabel_1 = new JLabel("EnemyCount: ");
        lblNewLabel_1.setBounds(452, 11, 71, 14);
        frame.getContentPane().add(lblNewLabel_1);
        
        JLabel lblNewLabel_2 = new JLabel("Hp:");
        lblNewLabel_2.setBounds(536, 363, 30, 14);
        frame.getContentPane().add(lblNewLabel_2);
        
        JLabel lblNewLabel_3 = new JLabel("Turn #");
        lblNewLabel_3.setBounds(382, 11, 46, 14);
        frame.getContentPane().add(lblNewLabel_3);
        
        model = new DefaultTableModel(); 
        enemyTable = new JTable(model);
        enemyTable.setToolTipText("");
        enemyTable.setBounds(536, 43, 172, 283);
        model.addColumn("Type");
        model.addColumn("Coords");
        model.addColumn("HP");
        model.addRow(new Object[]{"Type", "Coords", "HP"});
        frame.getContentPane().add(enemyTable);
        
        turnNumberLable = new JTextField();
        turnNumberLable.setText("0");
        turnNumberLable.setEditable(false);
        turnNumberLable.setColumns(10);
        turnNumberLable.setBounds(421, 8, 21, 20);
        frame.getContentPane().add(turnNumberLable);
        
        enemyCount = new JTextField();
        enemyCount.setText("0");
        enemyCount.setEditable(false);
        enemyCount.setColumns(10);
        enemyCount.setBounds(523, 8, 21, 20);
        frame.getContentPane().add(enemyCount);
        
        JLabel lblExp = new JLabel("EXP:");
        lblExp.setBounds(536, 413, 39, 14);
        frame.getContentPane().add(lblExp);
        
        JLabel lblLvl = new JLabel("LVL:");
        lblLvl.setBounds(536, 388, 39, 14);
        frame.getContentPane().add(lblLvl);
        
        JLabel lblShield = new JLabel("Shield:");
        lblShield.setBounds(617, 413, 71, 14);
        frame.getContentPane().add(lblShield);
        
        JLabel lblWeapon = new JLabel("Weapon:");
        lblWeapon.setBounds(617, 388, 71, 14);
        frame.getContentPane().add(lblWeapon);
        
        JLabel lblBonuses = new JLabel("Bonuses:");
        lblBonuses.setBounds(617, 363, 71, 14);
        frame.getContentPane().add(lblBonuses);
        
        console = new JTextArea(24, 80);
        console.setForeground(Color.WHITE);
        console.setFont(new Font("Monospaced", Font.PLAIN, 15));
        console.setBackground(Color.BLACK);
        console.setBounds(26, 436, 500, 181);
        frame.getContentPane().add(console);
        
        JLabel lblUsersMovecount = new JLabel("User's Movecount:");
        lblUsersMovecount.setBounds(554, 11, 92, 14);
        frame.getContentPane().add(lblUsersMovecount);
        
        moveCounterLabel = new JTextField();
        moveCounterLabel.setText("0");
        moveCounterLabel.setEditable(false);
        moveCounterLabel.setColumns(10);
        moveCounterLabel.setBounds(656, 8, 21, 20);
        frame.getContentPane().add(moveCounterLabel);
        
        textField_4 = new JTextField();
        textField_4.setText("0");
        textField_4.setEditable(false);
        textField_4.setColumns(10);
        textField_4.setBounds(586, 363, 21, 20);
        frame.getContentPane().add(textField_4);
        
        textField_5 = new JTextField();
        textField_5.setText("0");
        textField_5.setEditable(false);
        textField_5.setColumns(10);
        textField_5.setBounds(586, 388, 21, 20);
        frame.getContentPane().add(textField_5);
        
        textField_6 = new JTextField();
        textField_6.setText("0");
        textField_6.setEditable(false);
        textField_6.setColumns(10);
        textField_6.setBounds(586, 413, 21, 20);
        frame.getContentPane().add(textField_6);
        
        textField_7 = new JTextField();
        textField_7.setText("0");
        textField_7.setEditable(false);
        textField_7.setColumns(10);
        textField_7.setBounds(681, 413, 21, 20);
        frame.getContentPane().add(textField_7);
        
        textField_8 = new JTextField();
        textField_8.setText("0");
        textField_8.setEditable(false);
        textField_8.setColumns(10);
        textField_8.setBounds(681, 388, 21, 20);
        frame.getContentPane().add(textField_8);
        frame.setVisible(true);
        
        //Sets send as the default key
        frame.getRootPane().setDefaultButton(send);
        
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
    
    //MAP AREA----------------------------------------------------------
    public void setMapArea() {
        this.mapArea.append("Hello");
    }
    
    public void clearMapArea() throws InterruptedException {
    	Thread.sleep(500);
        this.mapArea.selectAll();
        this.mapArea.replaceSelection("");
    }
    
    //ENEMY TABLE----------------------------------------------------------
    public void setEnemyTable() {
        //Created for later
    }
    
    public void removeEnemyTable() throws InterruptedException {
    	//Created for later
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
    	mapName.setText("Map: " + map);
        return mapName;
    }
    
	public void init() {
		this.pack();
		this.setVisible(true);
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
		    Thread.sleep(750);
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