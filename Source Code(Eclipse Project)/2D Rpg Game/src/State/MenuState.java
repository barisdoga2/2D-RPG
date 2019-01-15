package State;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.TrueTypeFont;

import Engine.Variables;

public class MenuState extends State{

	private String title = "2D RPG GAME";
	private TrueTypeFont titleFont = new TrueTypeFont(new Font("Gabriola", Font.BOLD, 50), true);
	private Color titleColor = new Color(Color.cyan);
	
	private String[] options = {"New Game","Load Game","Help","Exit"};
	private TrueTypeFont optionFont = new TrueTypeFont(new Font("Gabriola", Font.PLAIN, 30), true);
	private Color optionColor = new Color(Color.white);
	private Color currentOptionColor = new Color(Color.red);
	
	private int currentOption = 0;
	
	@Override
	public void update() {}

	@Override
	public void render(Graphics g) {
		
		// Rendering Title
		g.setFont(titleFont);
		g.setColor(titleColor);
		int x = Variables.SCREEN_WIDTH / 2 - g.getFont().getWidth(title) / 2;
		int y = 100;
		g.drawString(title, x, y);
		
		// Rendering Options
		g.setFont(optionFont);
		g.setColor(optionColor);
		y = 300;
		
		for(int i = 0 ; i < options.length ; i++){
			
			if(i == currentOption)
				g.setColor(currentOptionColor);
			else
				g.setColor(optionColor);
			
			x = Variables.SCREEN_WIDTH / 2 - g.getFont().getWidth(options[i]) / 2;
			y += 10 + g.getFont().getHeight(options[i]);
			
			g.drawString(options[i], x, y);
			
		}
		
	}
	
	private void select(){
		
		if(currentOption == 0){
			StateManager.setState(StateManager.GAME_STATE);
		}else if(currentOption == 1){
			
		}else if(currentOption == 2){
			
		}else if(currentOption == 3){
			System.exit(-1);
		}
	
	}

	@Override
	public void keyPressed(int keyCode) {
		if(keyCode == Input.KEY_W){
			
			if(currentOption == 0)
				currentOption = options.length - 1;
			else
				currentOption--;
			
		}else if(keyCode == Input.KEY_S){
			
			if(currentOption == options.length - 1)
				currentOption = 0;
			else
				currentOption++;
			
		}else if(keyCode == Input.KEY_ENTER){
			
			select();
			
		}
	}

	@Override
	public void keyReleased(int keyCode) {}

	@Override
	public void mouseClicked(int button, int x, int y) {}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {}

	@Override
	public void mousePressed(int button, int x, int y) {}

	@Override
	public void mouseReleased(int button, int x, int y) {}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {}

}
