package Structures;

import org.newdawn.slick.Image;

import Utils.GeneralUtils;

public class MapObject {
	
	private int id;
	private String name;
	private String path;
	private Image image;
	private int width;
	private int height;
	private boolean onWalkable;
	
	public MapObject(int id, String name, String path, boolean onWalkable){
		this.id = id;
		this.name = name;
		this.path = path;
		this.onWalkable = onWalkable;
		
		this.image = GeneralUtils.getImage("/Maps/" + path);
		
		this.width = image.getWidth();
		this.height = image.getHeight();
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPath() {
		return path;
	}

	public Image getImage() {
		return image;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isOnWalkable() {
		return onWalkable;
	}
	
}
