package Entity;

import org.newdawn.slick.Graphics;

import Map.Map;

public abstract class Entity {
	
	protected Map currentMap;
	
	protected int id;
	
	protected double x;
	protected double y;
	
	protected int width;
	protected int height;
	
	public abstract void update();
	public abstract void render(Graphics g);
	
	
}
