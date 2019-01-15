package Tile;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class BasicTile extends Tile{
	
	private Image tileImage;

	public BasicTile(Image tileImage) {
		this.tileImage = tileImage;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(double x, double y, Graphics g) {
		g.drawImage(tileImage, (int)x, (int)y);
	}

}
