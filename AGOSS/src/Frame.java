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

class Frame extends JFrame {
	final JFrame frame = new JFrame();
    String text = null;
    int number = 0;
    int enemies = 0;
    int userIntInput = 0;
    String userStringInput = "";
    private JTable table;
    public JTextArea mapArea;
    public JLabel mapName;
    private JTextField turnNumber;
    private JTextField enemyCount;
    private JTextField textField_3;
    private JTextField textField_4;
    private JTextField textField_5;
    private JTextField textField_6;
    private JTextField textField_7;
    private JTextField textField_8;
    
    public Frame() {

        //Creating the Frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(901, 700);

        //Creating the panel at bottom and adding components
        JPanel panel = new JPanel(); // the panel is not visible in output
        panel.setBounds(26, 561, 634, 33);
        JLabel lblCommandLine = new JLabel("Command Line:");
        lblCommandLine.setBounds(10, 9, 83, 14);
        JTextField tf = new JTextField(10); // accepts upto 10 characters
        tf.setBounds(103, 6, 367, 20);
        panel.setLayout(null);
        panel.add(lblCommandLine); // Components Added using Flow Layout
        panel.add(lblCommandLine); // Components Added using Flow Layout
        panel.add(tf);

		mapArea = new JTextArea(24, 80);
		mapArea.setBounds(26, 36, 614, 397);
		mapArea.setBackground(Color.LIGHT_GRAY);
		mapArea.setForeground(Color.black);
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
        lblNewLabel_2.setBounds(168, 485, 30, 14);
        frame.getContentPane().add(lblNewLabel_2);
        
        JLabel lblNewLabel_3 = new JLabel("Turn #");
        lblNewLabel_3.setBounds(382, 11, 46, 14);
        frame.getContentPane().add(lblNewLabel_3);
        
        table = new JTable();
        table.setToolTipText("");
        table.setBounds(671, 36, 172, 283);
        frame.getContentPane().add(table);
        
        turnNumber = new JTextField();
        turnNumber.setText("0");
        turnNumber.setEditable(false);
        turnNumber.setColumns(10);
        turnNumber.setBounds(421, 8, 21, 20);
        frame.getContentPane().add(turnNumber);
        
        enemyCount = new JTextField();
        enemyCount.setText("0");
        enemyCount.setEditable(false);
        enemyCount.setColumns(10);
        enemyCount.setBounds(523, 8, 21, 20);
        frame.getContentPane().add(enemyCount);
        
        JLabel lblExp = new JLabel("EXP:");
        lblExp.setBounds(168, 535, 39, 14);
        frame.getContentPane().add(lblExp);
        
        JLabel lblLvl = new JLabel("LVL:");
        lblLvl.setBounds(168, 510, 39, 14);
        frame.getContentPane().add(lblLvl);
        
        JLabel lblShield = new JLabel("Shield:");
        lblShield.setBounds(280, 535, 71, 14);
        frame.getContentPane().add(lblShield);
        
        JLabel lblWeapon = new JLabel("Weapon:");
        lblWeapon.setBounds(280, 510, 71, 14);
        frame.getContentPane().add(lblWeapon);
        
        JLabel lblBonuses = new JLabel("Bonuses:");
        lblBonuses.setBounds(280, 485, 71, 14);
        frame.getContentPane().add(lblBonuses);
        
        JTextArea textArea_1 = new JTextArea(24, 80);
        textArea_1.setForeground(Color.BLACK);
        textArea_1.setFont(new Font("Monospaced", Font.PLAIN, 15));
        textArea_1.setBackground(Color.LIGHT_GRAY);
        textArea_1.setBounds(26, 436, 614, 46);
        frame.getContentPane().add(textArea_1);
        
        JLabel lblUsersMovecount = new JLabel("User's Movecount:");
        lblUsersMovecount.setBounds(26, 487, 92, 14);
        frame.getContentPane().add(lblUsersMovecount);
        
        textField_3 = new JTextField();
        textField_3.setText("0");
        textField_3.setEditable(false);
        textField_3.setColumns(10);
        textField_3.setBounds(128, 484, 21, 20);
        frame.getContentPane().add(textField_3);
        
        textField_4 = new JTextField();
        textField_4.setText("0");
        textField_4.setEditable(false);
        textField_4.setColumns(10);
        textField_4.setBounds(218, 485, 21, 20);
        frame.getContentPane().add(textField_4);
        
        textField_5 = new JTextField();
        textField_5.setText("0");
        textField_5.setEditable(false);
        textField_5.setColumns(10);
        textField_5.setBounds(218, 510, 21, 20);
        frame.getContentPane().add(textField_5);
        
        textField_6 = new JTextField();
        textField_6.setText("0");
        textField_6.setEditable(false);
        textField_6.setColumns(10);
        textField_6.setBounds(218, 535, 21, 20);
        frame.getContentPane().add(textField_6);
        
        textField_7 = new JTextField();
        textField_7.setText("0");
        textField_7.setEditable(false);
        textField_7.setColumns(10);
        textField_7.setBounds(344, 535, 21, 20);
        frame.getContentPane().add(textField_7);
        
        textField_8 = new JTextField();
        textField_8.setText("0");
        textField_8.setEditable(false);
        textField_8.setColumns(10);
        textField_8.setBounds(344, 510, 21, 20);
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
    
    public void setMapArea() {
        this.mapArea.append("Hello");
    }
    
    public void clearMapArea() throws InterruptedException {
    	Thread.sleep(500);
        this.mapArea.selectAll();
        this.mapArea.replaceSelection("");
    }
    
    public int setTurnNumber(int x) {
	    this.number = x;
	    turnNumber.setText(Integer.toString(number));
        return number;
    }
    
    public int setEnemies(int x) {
    	this.enemies = x; 
    	enemyCount.setText(Integer.toString(enemies));
        return enemies;
    }
    
    public int getEnemies() {
        return enemies;
    }
    
    public int getTurnNumber() {
        return number;
    }
    
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