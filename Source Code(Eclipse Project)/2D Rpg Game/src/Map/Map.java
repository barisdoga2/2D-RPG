package Map;

import org.newdawn.slick.Graphics;

import Entity.Player;
import GUI.GUIManager;
import Utils.MapUtils;

public class Map {
		
	private Player player;
	
	private GUIManager guiManager;
	
	private TileManager tileManager;
	private ObjectManager objectManager;
	private MobManager mobManager;
	
	public int mapWidth;
	public int mapHeight;
	public int spawnX;
	public int spawnY;
	public int xOffset;
	public int yOffset;
	
	public Map(){
		loadMapDetails();
		player = new Player(this, spawnX, spawnY);
		tileManager = new TileManager(this);
		objectManager = new ObjectManager(this);
		mobManager = new MobManager(this);
		guiManager = new GUIManager(this);
	}

	public void update() {
		tileManager.update();
		objectManager.update();
		mobManager.update();
		
		player.update();
		
		guiManager.update();
	}

	public void render(Graphics g) {
		tileManager.render(g);
		objectManager.render(g);
		mobManager.render(g);
		
		player.render(g);
	
		guiManager.render(g);
	}

	public void keyPressed(int keyCode) {
		player.keyPressed(keyCode);
		guiManager.keyPressed(keyCode);
	}

	public void keyReleased(int keyCode) {
		player.keyReleased(keyCode);
		guiManager.keyReleased(keyCode);
	}

	public void mouseClicked(int button, int x, int y) {
		guiManager.mouseClicked(button, x, y);
	}

	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		guiManager.mouseDragged(oldx, oldy, newx, newy);
	}

	public void mousePressed(int button, int x, int y) {
		guiManager.mosuePressed(button, x, y);
	}

	public void mouseReleased(int button, int x, int y) {
		guiManager.mouseReleased(button, x, y);
	}
	
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		guiManager.mouseMoved(oldx, oldy, newx, newy);
	}
	
	// Utils
	private void loadMapDetails(){
		
		int[] mapDetails = MapUtils.loadMapDetails();
		mapWidth = mapDetails[0];
		mapHeight = mapDetails[1];
		spawnX = mapDetails[2];
		spawnY = mapDetails[3];
		
	}

	public boolean isCollideTile(double xCoord, double yCoord) {
		return tileManager.isCollideTile(xCoord, yCoord);
	}
	
	public MobManager getMobManager(){
		return mobManager;
	}
	
	public GUIManager getGUIManager(){
		return guiManager;
	}

	public Player getPlayer() {
		return player;
	}
	
}
