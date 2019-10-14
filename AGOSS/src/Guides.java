import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Guides {
    static ArrayList<String> listOfLines = new ArrayList<>();

    String guideName;
    
    public Guides(String guideName) throws Exception {
		Guides.listOfLines = saveGuide(guideName);
		this.guideName = guideName;
		
	}

	public static ArrayList<String> saveGuide(String mapName) throws Exception {
		//Checks if dialogue exists
		if (new File("src\\dialogues\\" + mapName + ".txt").exists()){
			//Continue if it does
			listOfLines = inputToString("src\\dialogues\\" + mapName + "_guide.txt");
			
			
			//Loop through the list
			for(int i = 0; i != listOfLines.size(); i++) {
				System.out.println(listOfLines.get(i));
				return listOfLines;
			}
		}else {
			System.out.println("Missing guide .txt");
		}
		return listOfLines;
	}
    
	public static ArrayList<String> inputToString(String fileName)throws Exception 
	{ 
		//Initializes the buffered reader
		BufferedReader reader = new BufferedReader(new FileReader(fileName));

	    String line = reader.readLine();
	    while (line != null) {
	      listOfLines.add(line);
	      line = reader.readLine();
	    }
		reader.close();
		return listOfLines;
	} 
	
	public static ArrayList<String> getGuide() {
		return Guides.listOfLines;
		
	}
}
