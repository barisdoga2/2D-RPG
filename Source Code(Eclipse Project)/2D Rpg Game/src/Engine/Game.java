package Engine;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;

import State.StateManager;

public class Game extends BasicGame implements KeyListener,MouseListener{
	
	public static void main(String[] args){
		
		try {
			Variables.SCREEN_WIDTH = 1366;
			Variables.SCREEN_HEIGHT = 768;
			AppGameContainer gameApp = new AppGameContainer(new Game("2D RPG GAME by Barisdoga"));
			gameApp.setDisplayMode(Variables.SCREEN_WIDTH, Variables.SCREEN_HEIGHT, true);
			gameApp.setUpdateOnlyWhenVisible(false);
			gameApp.setAlwaysRender(true);
			gameApp.setVSync(true);
			gameApp.setTargetFrameRate(61);
			gameApp.start();
			
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
	}
	
	public Game(String title) {
		super(title);
	}
	
	@Override
	public void init(GameContainer appContainer) throws SlickException {
		Variables.initGUI();
		Variables.initItems();
		StateManager.setState(StateManager.MENU_STATE);
	}
	
	long max = 0;
	long total = 0;
	long ticks = 0;
	
	@Override
	public void update(GameContainer appContainer, int deltaTime) throws SlickException {
		long now = System.nanoTime();
		StateManager.update();		
		long last = System.nanoTime();
		if(max < last - now)
			max = last - now;
		total += last - now;
		ticks++;
		//System.out.println("Max = " + max + " // " + total / ticks);
	}
	
	@Override
	public void render(GameContainer appContainer, Graphics g) throws SlickException {
		StateManager.render(g);
	}
	
	@Override
	public void keyPressed(int keyCode, char c){
		StateManager.keyPressed(keyCode);
	}
	
	@Override
	public void keyReleased(int keyCode, char c){
		StateManager.keyReleased(keyCode);
	}
	
	public void mouseClicked(int button, int x, int y, int clickCount){
		StateManager.mouseClicked(button, x, y, clickCount);
	}
	
	public void mousePressed(int button, int x, int y){
		StateManager.mousePressed(button, x, y);
	}
	
	public void mouseReleased(int button, int x, int y){
		StateManager.mouseReleased(button, x, y);
	}
	
	public void mouseDragged(int oldx, int oldy, int newx, int newy){
		StateManager.mouseDragged(oldx, oldy, newx, newy);
	}
	
	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy){
		StateManager.mouseMoved(oldx, oldy, newx, newy);
	}

}
