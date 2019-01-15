package Tile;

import org.newdawn.slick.Graphics;

public class AnimatedTile extends Tile{

	private BasicTile[] basicTiles;
	private int delay;
	private int currentFrame;
	
	private long lastTime = 0;
	private long timePass = 0;
	
	public AnimatedTile(int delay, BasicTile[] basicTiles){
		this.basicTiles = basicTiles;
		this.delay = delay;
		this.currentFrame = 0;
	}
	
	@Override
	public void update() {
		timePass += System.currentTimeMillis() - lastTime;
		
		if(timePass > delay){
			
			if(currentFrame == basicTiles.length - 1){
				currentFrame = 0;
			}else{
				currentFrame++;
			}
			
			timePass = 0;
		}
		
		lastTime = System.currentTimeMillis();
	}

	@Override
	public void render(double x, double y, Graphics g) {
		basicTiles[currentFrame].render(x, y, g);
	}
	
	

}
