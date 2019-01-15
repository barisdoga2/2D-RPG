package Utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import Engine.Variables;
import Entity.Mob;
import Entity.Npc;
import Entity.Object;
import GUI.SpeakAction;
import Map.Map;
import Structures.MapMob;
import Structures.MapObject;

public class MapUtils {

	public static int[] loadMapDetails() {
		int[] mapDetails = new int[4];
		
		try{
			InputStream is = MapUtils.class.getResourceAsStream(Variables.mapPath + Variables.currentMap + "/" + Variables.currentMap + ".txt");
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			
			String[] tokens = br.readLine().split("\\t");
			for(int i = 0 ; i < mapDetails.length ; i++){
				mapDetails[i] = Integer.parseInt(tokens[i]);
			}
			
			br.close();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		return mapDetails;
	}

	public static int[][] loadTileIDs(int mapWidth, int mapHeight) {
		int[][] tileIDs = new int[mapHeight][mapWidth];
		
		try{
			
			InputStream is = MapUtils.class.getResourceAsStream(Variables.mapPath + Variables.currentMap + "/" + Variables.currentMap + "-TileMap.txt");
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			
			String[] tokens;
			
			for(int y = 0 ; y < mapHeight ; y++){
				tokens = br.readLine().split("\\t");
				for(int x = 0 ; x < mapWidth ; x++){
					tileIDs[y][x] = Integer.parseInt(tokens[x]);
				}
			}
			
			br.close();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		return tileIDs;
	}
	

	public static int[][] getAnimatedTileInfo() {
		int[][] animatedTileInfo = null;
		
		try{
			
			InputStream is = MapUtils.class.getResourceAsStream(Variables.mapPath + Variables.currentMap + "/" + Variables.currentMap + "-TileMapAnimated.txt");
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			
			int count = Integer.parseInt(br.readLine());
			String line;
			String tokens[];
			animatedTileInfo = new int[count][3];
			for(int i = 0 ; i < count ; i++){
				line = br.readLine();
				tokens = line.split("\\t");
				
				for(int x = 0 ; x < tokens.length ; x++){
					animatedTileInfo[i][x] = Integer.parseInt(tokens[x]);
				}
			}
			
			br.close();
		}catch(Exception e){
			return null;
		}
		
		return animatedTileInfo;
	}
	
	public static int[][] loadCollisionIDs(int mapWidth, int mapHeight) {
		int[][] collisionIDs = new int[mapHeight][mapWidth];
		
		try{
			
			InputStream is = MapUtils.class.getResourceAsStream(Variables.mapPath + Variables.currentMap + "/" + Variables.currentMap + "-CollisionMap.txt");
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			
			String[] tokens;
			
			for(int y = 0 ; y < mapHeight ; y++){
				tokens = br.readLine().split("\\t");
				for(int x = 0 ; x < mapWidth ; x++){
					collisionIDs[y][x] = Integer.parseInt(tokens[x]);
				}
			}
			
			br.close();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		return collisionIDs;
	}

	public static ArrayList<MapObject> getMapObjects() {
		ArrayList<MapObject> mapObjects = new ArrayList<MapObject>();
		
		try{
			
			InputStream is = MapUtils.class.getResourceAsStream(Variables.mapPath + Variables.currentMap + "/" + Variables.currentMap + "-ObjectList.txt");
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			
			String line;
			String[] tokens;
			
			while((line = br.readLine()) != null){
				
				tokens = line.split("\\t");
				
				boolean onWalkable = false;
				if(tokens[3].equals("1"))
					onWalkable = true;
				
				mapObjects.add( new MapObject( Integer.parseInt(tokens[0]) , tokens[1], tokens[2], onWalkable) );
				
			}
			
			br.close();
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		return mapObjects;
	}

	public static ArrayList<Object> getPlacedObjects(ArrayList<MapObject> mapObjects, Map currentMap) {
		ArrayList<Object> placedObjects = new ArrayList<Object>();
		
		try{
			
			InputStream is = MapUtils.class.getResourceAsStream(Variables.mapPath + Variables.currentMap + "/" + Variables.currentMap + "-ObjectMap.txt");
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			
			String line;
			String[] tokens;
			int idCount = Variables.objectIdStart;
			while((line = br.readLine()) != null){
				tokens = line.split("\\t");
				placedObjects.add( new Object(currentMap, idCount, mapObjects.get(Integer.parseInt(tokens[0])), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2])) );
				idCount++;
			}
			
			br.close();
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		return placedObjects;
	}

	public static ArrayList<MapMob> getMapMobs() {
		ArrayList<MapMob> mapMobs = new ArrayList<MapMob>();
		
		String[] tokens;
		
		try{
			
			InputStream is = MapUtils.class.getResourceAsStream(Variables.mapPath + Variables.currentMap + "/" + Variables.currentMap + "-MobList.txt");
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			
			String line;
			while((line = br.readLine()) != null){
				tokens = line.split("\\t");
				mapMobs.add(new MapMob(Integer.parseInt(tokens[0]),
														tokens[1],
														Integer.parseInt(tokens[2]),
														Double.parseDouble(tokens[3]),
														Double.parseDouble(tokens[4]),
														Integer.parseInt(tokens[5]),
														Integer.parseInt(tokens[6]),
														Integer.parseInt(tokens[7]),
														Integer.parseInt(tokens[8]),
														Integer.parseInt(tokens[9]),
														Integer.parseInt(tokens[10]),
														Integer.parseInt(tokens[11]),
														Integer.parseInt(tokens[12]),
														Integer.parseInt(tokens[13]),
														Integer.parseInt(tokens[14]),
														tokens[15]));
			}
			
			br.close();
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}

		return mapMobs;
	}

	public static ArrayList<Mob> getPlacedMobs(ArrayList<MapMob> mapMobs, Map currentMap) {
		ArrayList<Mob> placedMobs = new ArrayList<Mob>();
		
		try{
			
			InputStream is = MapUtils.class.getResourceAsStream(Variables.mapPath + Variables.currentMap + "/" + Variables.currentMap + "-MobMap.txt");
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			
			String line;
			String[] tokens;
			int count = Variables.mobIdStart;
			while((line = br.readLine()) != null){
				tokens = line.split("\\t");

				if(Integer.parseInt(tokens[3]) == 1)
					placedMobs.add(new Npc( currentMap, count, mapMobs.get(Integer.parseInt(tokens[0])), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2])));
				
				count++;
			}
			
		}catch(Exception e){
			return null;
		}
		
		return placedMobs;
	}

	public static ArrayList<SpeakAction> getSpeakAcitons(int id) {
		ArrayList<SpeakAction> speakActions = new ArrayList<SpeakAction>();
		
		try{
			InputStream is = MapUtils.class.getResourceAsStream(Variables.mapPath + Variables.currentMap + "/Speaks/" + id + ".txt");
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			
			String[] tokens;
			String line;
			
			while((line = br.readLine()) != null){
				tokens = line.split(":");
				speakActions.add( new SpeakAction(tokens[0], tokens[1], tokens[2], tokens[3]));
			}
			
			br.close();
			
		}catch(Exception e){
			return null;
		}
		
		return speakActions;
	}

}
