package Entity;

import org.newdawn.slick.Graphics;

import Map.Map;
import Structures.MapObject;

public class Object extends Entity{
	
	private MapObject parent;
	
	public Object(Map currentMap, int id, MapObject parent, double x, double y){
		this.currentMap = currentMap;
		this.id = id;
		this.parent = parent;
		this.x = x;
		this.y = y;
	}
	
	
	public void update(){
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(parent.getImage(), (int)x - currentMap.xOffset, (int)y - currentMap.yOffset);
	}
}
