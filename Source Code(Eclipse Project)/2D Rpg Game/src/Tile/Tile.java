package Tile;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import Engine.Variables;
import Utils.GeneralUtils;
import Utils.MapUtils;

public abstract class Tile {
	
	public static final int tileSize = 32;
	public static Tile tiles[] = new Tile[256];
	
	public static void loadTiles(){
		
		Image tileSet = GeneralUtils.getImage(Variables.mapPath + Variables.currentMap + "/" + Variables.currentMap + "-TileSet.png");
		
		int width = (int)Math.floor(tileSet.getWidth() / tileSize);
		int height = (int)Math.floor(tileSet.getHeight() / tileSize);
		
		for(int y = 0 ; y < height ; y++){
			for(int x = 0 ; x < width ; x++){
				tiles[x + y * width] = new BasicTile( tileSet.getSubImage(x * tileSize, y * tileSize, tileSize, tileSize) );
			}
		}
	
		// Loading Animated Tiles
		
		int[][] animatedTileInfo = MapUtils.getAnimatedTileInfo();
		
		for(int i = 0 ; i < animatedTileInfo.length ; i++){
			BasicTile[] basicTiles = new BasicTile[animatedTileInfo[i][1]];
			for(int x = 0 ; x < basicTiles.length ; x++){
				basicTiles[x] = (BasicTile)tiles[animatedTileInfo[i][0] + x];
			}
			tiles[animatedTileInfo[i][0]] = new AnimatedTile(animatedTileInfo[i][2], basicTiles);
		}
	}
	
	// End Of Static Content
	
	public abstract void update();
	public abstract void render(double x, double y, Graphics g);
	
	
}
