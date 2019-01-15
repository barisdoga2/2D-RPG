package State;

import org.newdawn.slick.Graphics;

import Map.Map;

public class GameState extends State{
	
	private Map currentMap;
	
	public GameState(){
		currentMap = new Map();
	}
	
	@Override
	public void update() {
		currentMap.update();
	}

	@Override
	public void render(Graphics g) {
		currentMap.render(g);
	}

	@Override
	public void keyPressed(int keyCode) {
		currentMap.keyPressed(keyCode);
	}

	@Override
	public void keyReleased(int keyCode) {
		currentMap.keyReleased(keyCode);
	}

	@Override
	public void mouseClicked(int button, int x, int y) {
		currentMap.mouseClicked(button, x, y);
	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		currentMap.mouseDragged(oldx, oldy, newx, newy);
	}

	@Override
	public void mousePressed(int button, int x, int y) {
		currentMap.mousePressed(button, x, y);
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		currentMap.mouseReleased(button, x, y);
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		currentMap.mouseMoved(oldx, oldy, newx, newy);
	}

}
