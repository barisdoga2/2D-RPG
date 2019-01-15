package State;

import org.newdawn.slick.Graphics;

public abstract class State {

	public abstract void update();
	public abstract void render(Graphics g);
	
	public abstract void keyPressed(int keyCode);
	public abstract void keyReleased(int keyCode);
	public abstract void mouseClicked(int button, int x, int y);
	public abstract void mouseDragged(int oldx, int oldy, int newx, int newy);
	public abstract void mousePressed(int button, int x, int y);
	public abstract void mouseReleased(int button, int x, int y);
	public abstract void mouseMoved(int oldx, int oldy, int newx, int newy);
	
}
