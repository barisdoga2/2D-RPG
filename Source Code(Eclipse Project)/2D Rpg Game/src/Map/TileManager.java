package Map;

import org.newdawn.slick.Graphics;

import Engine.Variables;
import Tile.Tile;
import Utils.MapUtils;

public class TileManager {
	
	private Map currentMap;
	
	private int[][] tileIDs;
	private int[][] collisionIDs;
	
	public TileManager(Map currentMap){
		this.currentMap = currentMap;
		Tile.loadTiles();
		tileIDs = MapUtils.loadTileIDs(currentMap.mapWidth, currentMap.mapHeight);
		collisionIDs = MapUtils.loadCollisionIDs(currentMap.mapWidth, currentMap.mapHeight);
	}
	
	public void update(){
		int startX = (int)Math.floor(currentMap.xOffset / Tile.tileSize);
		int startY = (int)Math.floor(currentMap.yOffset / Tile.tileSize);
		
		int rowToDraw = (int)Math.ceil(Variables.SCREEN_WIDTH / Tile.tileSize) + 2;
		int colToDraw = (int)Math.ceil(Variables.SCREEN_HEIGHT / Tile.tileSize) + 2;
		
		for(int y = startY ; y < startY + colToDraw ; y++){
			if(y >= currentMap.mapHeight)
				continue;
			for(int x = startX ; x < startX + rowToDraw ; x++){
				if(x >= currentMap.mapWidth)
					continue;
				Tile.tiles[tileIDs[y][x]].update();
			}
		}
	}
	
	public void render(Graphics g){
		
		int startX = (int)Math.floor(currentMap.xOffset / Tile.tileSize);
		int startY = (int)Math.floor(currentMap.yOffset / Tile.tileSize);
		
		int rowToDraw = (int)Math.ceil(Variables.SCREEN_WIDTH / Tile.tileSize) + 2;
		int colToDraw = (int)Math.ceil(Variables.SCREEN_HEIGHT / Tile.tileSize) + 2;
		
		for(int y = startY ; y < startY + colToDraw ; y++){
			if(y >= currentMap.mapHeight)
				continue;
			for(int x = startX ; x < startX + rowToDraw ; x++){
				if(x >= currentMap.mapWidth)
					continue;
				Tile.tiles[tileIDs[y][x]].render(x * Tile.tileSize - currentMap.xOffset, y * Tile.tileSize - currentMap.yOffset, g);
			}
		}
		
	}

	public boolean isCollideTile(double xCoord, double yCoord){
		int x = (int)Math.floor(xCoord / Tile.tileSize);
		int y = (int)Math.floor(yCoord / Tile.tileSize);
		
		try{
			if(collisionIDs[y][x] == 0){
				return false;
			}
		}catch(Exception e){
			return true;
		}
		
		return true;
	}
	
}
