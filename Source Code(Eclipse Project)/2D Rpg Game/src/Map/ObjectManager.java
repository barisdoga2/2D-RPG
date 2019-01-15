package Map;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

import Entity.Object;
import Structures.MapObject;
import Utils.MapUtils;

public class ObjectManager {
	
	private ArrayList<MapObject> objectList;
	private ArrayList<Object> placedObjects;
	
	public ObjectManager(Map currentMap){
		objectList = MapUtils.getMapObjects();
		placedObjects = MapUtils.getPlacedObjects(objectList, currentMap);
		
	}

	public void update() {
		
	}

	public void render(Graphics g) {
		
		for(Object obj : placedObjects){
			obj.render(g);
		}
		
	}

}
