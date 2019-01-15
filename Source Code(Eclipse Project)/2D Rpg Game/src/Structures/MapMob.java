package Structures;

public class MapMob {

	private int id;
	private String name;
	private int health;
	private double speed;
	private double damage;
	private int width;
	private int height;
	private int cStartX;
	private int cStartY;
	private int cWidth;
	private int cHeight;
	private int gCStartX;
	private int gCStartY;
	private int gCWidth;
	private int gCHeight;
	private String spriteSheetPath;
	
	public MapMob(int id, String name, int health, double damage, double speed, int height, int width, int cStartY, int cStartX, int cHeight, int cWidth, int gCStartY, int gCStartX, int gCHeight, int gCWidth, String spriteSheetPath){
		this.id = id;
		this.name = name;
		this.health = health;
		this.damage = damage;
		this.speed = speed;
		this.width = width;
		this.height = height;
		this.cStartX = cStartX;
		this.cStartY = cStartY;
		this.cWidth = cWidth;
		this.cHeight = cHeight;
		this.gCStartX = gCStartX;
		this.gCStartY = gCStartY;
		this.gCWidth = gCWidth;
		this.gCHeight = gCHeight;
		this.spriteSheetPath = "/Maps/" + spriteSheetPath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getcStartX() {
		return cStartX;
	}

	public void setcStartX(int cStartX) {
		this.cStartX = cStartX;
	}

	public int getcStartY() {
		return cStartY;
	}

	public void setcStartY(int cStartY) {
		this.cStartY = cStartY;
	}

	public int getcWidth() {
		return cWidth;
	}

	public void setcWidth(int cWidth) {
		this.cWidth = cWidth;
	}

	public int getcHeight() {
		return cHeight;
	}

	public void setcHeight(int cHeight) {
		this.cHeight = cHeight;
	}

	public int getgCStartX() {
		return gCStartX;
	}

	public void setgCStartX(int gCStartX) {
		this.gCStartX = gCStartX;
	}

	public int getgCStartY() {
		return gCStartY;
	}

	public void setgCStartY(int gCStartY) {
		this.gCStartY = gCStartY;
	}

	public int getgCWidth() {
		return gCWidth;
	}

	public void setgCWidth(int gCWidth) {
		this.gCWidth = gCWidth;
	}

	public int getgCHeight() {
		return gCHeight;
	}

	public void setgCHeight(int gCHeight) {
		this.gCHeight = gCHeight;
	}
	
	public String getSpriteSheetPath(){
		return spriteSheetPath;
	}
	
	public void setSpriteSheetPath(String spriteSheetPath){
		this.spriteSheetPath = spriteSheetPath;
	}
	
}

