package State;

import org.newdawn.slick.Graphics;

public class StateManager {
	
	public static final int MENU_STATE = 0;
	public static final int GAME_STATE = 1;
	
	public static State currentState;
	
	public static void setState(int state){
		
		if(state == MENU_STATE){
			setState(new MenuState());
		}else if(state == GAME_STATE){
			setState(new GameState());
		}
		
	}
	
	public static void setState(State state){
		
		currentState = state;
		
	}
	
	public static void update(){
		currentState.update();
	}
	
	public static void render(Graphics g){
		currentState.render(g);
	}

	public static void keyPressed(int keyCode) {
		currentState.keyPressed(keyCode);
	}

	public static void keyReleased(int keyCode) {
		currentState.keyReleased(keyCode);
	}

	public static void mouseClicked(int button, int x, int y, int clickCount) {
		currentState.mouseClicked(button, x, y);
	}

	public static void mousePressed(int button, int x, int y) {
		currentState.mousePressed(button, x, y);
	}

	public static void mouseReleased(int button, int x, int y) {
		currentState.mouseReleased(button, x, y);
	}

	public static void mouseDragged(int oldx, int oldy, int newx, int newy) {
		currentState.mouseDragged(oldx, oldy, newx, newy);
	}

	public static void mouseMoved(int oldx, int oldy, int newx, int newy) {
		currentState.mouseMoved(oldx, oldy, newx, newy);
	}
	
}
