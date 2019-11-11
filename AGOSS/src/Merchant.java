
public class Merchant {
	int mapX;
	int mapY;
	
	public Merchant(int mapX, int mapY) {
		int[] Bag = {mapX,mapY};
		
		this.mapX = mapX;
		this.mapY = mapY;

	}
    
	public int getMapX() {
		return this.mapX;
	}
	
	public int getMapY() {
		return this.mapY;
	}
	
	public int setMapX(int x) {
		this.mapX = x;
		return this.mapX;
	}
	
	public int setMapY(int y) {
		this.mapY = y;
		return this.mapY;
	}
}

