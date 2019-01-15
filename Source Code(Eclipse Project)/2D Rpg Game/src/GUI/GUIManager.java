package GUI;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.TrueTypeFont;

import Engine.Variables;

import java.awt.Font;

import Map.Map;

public class GUIManager {
	
	private Map currentMap;
	
	private SpeakWindow speakWindow;
	private InventoryWindow inventoryWindow;
	private QuestionWindow questionWindow;
	private ShopWindow shopWindow;
	private CharacterWindow characterWindow;
	private BarWindow barWindow;
	
	protected TrueTypeFont textFont = new TrueTypeFont(new Font("Gabriola", Font.PLAIN, 30), true);
	protected TrueTypeFont buttonFont = new TrueTypeFont(new Font("Gabriola", Font.PLAIN, 20), true);
	protected TrueTypeFont itemNameFont = new TrueTypeFont(new Font("Arial", Font.BOLD, 20), true);
	protected TrueTypeFont infoFont = new TrueTypeFont(new Font("Gabriola", Font.PLAIN, 25), true);
	protected TrueTypeFont miniFont = new TrueTypeFont(new Font("Gabriola", Font.PLAIN, 15), true);
	protected TrueTypeFont barWindowFont = new TrueTypeFont(new Font("Arial", Font.BOLD, 16), true);
	
	public GUIManager(Map currentMap){
		this.currentMap = currentMap;
		this.inventoryWindow = new InventoryWindow(this);
		this.questionWindow = new QuestionWindow(this);
		this.characterWindow = new CharacterWindow(this);
		this.barWindow = new BarWindow(this, currentMap.getPlayer());
	}
	
	public void update(){
		barWindow.update();
	}
	
	public void render(Graphics g){
		g.setColor(Color.white);
		if(isSpeakWindow())
			speakWindow.render(g);
		
		if(isShopWindow())
			shopWindow.render(g);
		
		if(isInventoryWindow())
			inventoryWindow.render(g);
		
		if(isCharacterWindow())
			characterWindow.render(g);
		
		if(isQuestionWindow())
			questionWindow.render(g);
		
		
		if(isInventoryWindow())
			inventoryWindow.renderMoving(g);
		
		barWindow.render(g);
		
	}

	public void keyPressed(int keyCode) {
		
		if(isQuestionWindow()){
			questionWindow.keyPressed(keyCode);
			return;
		}
		
		if(keyCode == Input.KEY_ESCAPE){
			if(isQuestionWindow()){
				questionWindow.exit();
				return;
			}if(isShopWindow()){
				shopWindow = null;
				return;
			}if(isSpeakWindow()){
				speakWindow = null;
				return;
			}if(isInventoryWindow()){
				inventoryWindow.isVisible = false;
				return;
			}if(isCharacterWindow()){
				characterWindow.isVisible = false;
				return;
			}
		}else if(keyCode == Input.KEY_I){
			inventoryWindow.isVisible = !inventoryWindow.isVisible;
		}else if(keyCode == Input.KEY_C){
			characterWindow.isVisible = !characterWindow.isVisible;
		}
	}

	public void keyReleased(int keyCode) {
		
	}

	public void mouseClicked(int button, int x, int y) {
		
		if(isQuestionWindow()){
			questionWindow.mouseClicked(button, x, y);
			return;
		}
		
		if(isSpeakWindow()){
			speakWindow.mouseClicked(button, x, y);
		}
		
		if(isShopWindow()){
			shopWindow.mouseClicked(button, x, y);
		}
		
		if(isInventoryWindow()){
			inventoryWindow.mouseClicked(button, x, y);
		}
		
		if(isCharacterWindow()){
			characterWindow.mouseClicked(button, x, y);
		}
		
	}

	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		
		if(isQuestionWindow()){
			questionWindow.mouseDragged(oldx, oldy, newx, newy);
			return;
		}
		
		if(isSpeakWindow()){
			speakWindow.mouseDragged(oldx, oldy, newx, newy);
		}if(isShopWindow()){
			shopWindow.mouseDragged(oldx, oldy, newx, newy);
		}if(isInventoryWindow()){
			inventoryWindow.mouseDragged(oldx, oldy, newx, newy);
		}if(isCharacterWindow()){
			characterWindow.mouseDragged(oldx, oldy, newx, newy);
		}
	}

	public void mosuePressed(int button, int x, int y) {
		
		if(isQuestionWindow()){
			questionWindow.mousePressed(button, x, y);
			return;
		}
		
		if(isSpeakWindow()){
			speakWindow.mousePressed(button, x, y);
		}if(isShopWindow()){
			shopWindow.mousePressed(button, x, y);
		}if(isInventoryWindow()){
			inventoryWindow.mousePressed(button, x, y);
		}if(isCharacterWindow()){
			characterWindow.mousePressed(button, x, y);
		}
	}

	public void mouseReleased(int button, int x, int y) {
		
		if(isQuestionWindow()){
			questionWindow.mouseReleased(button, x, y);
			return;
		}
		
		if(isSpeakWindow()){
			speakWindow.mouseReleased(button, x, y);
		}if(isShopWindow()){
			shopWindow.mouseReleased(button, x, y);
		}if(isInventoryWindow()){
			inventoryWindow.mouseReleased(button, x, y);
		}if(isCharacterWindow()){
			characterWindow.mouseReleased(button, x, y);
		}
	}

	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		if(isShopWindow()){
			shopWindow.mouseMoved(oldx, oldy, newx, newy);
		}if(isInventoryWindow()){
			inventoryWindow.mouseMoved(oldx, oldy, newx, newy);
		}
	}
	
	// General Methods

	public void openSpeakWindowWithID(int speakID) {
		if(!isShopWindow() && !isQuestionWindow()){
			speakWindow = new SpeakWindow(this, speakID);
		}
	}
	
	public void openShopWindowWithName(String name) {
		shopWindow = new ShopWindow(this, name);
		speakWindow = null;
	}
	
	// Utils
	public void drawButton(int x, int y, String buttonName, Graphics g){
		g.drawImage(Variables.leftButton, x, y);
		for(int i = 0 ; i < g.getFont().getWidth(buttonName) ; i++){
			g.drawImage(Variables.topButton, x + 17 + i, y);
			g.drawImage(Variables.botButton, x + 17 + i, y + 24);
		}
		g.drawImage(Variables.rightButton, x + 17 + g.getFont().getWidth(buttonName), y);
		
		
		g.drawString(buttonName, x + 17, y);
		
	}

	public int getButtonWidth(String text, Graphics g) {
		return g.getFont().getWidth(text) + 17 + 17;
	}
	
	// Getters And Setters
	public SpeakWindow getSpeakWindow(){
		return speakWindow;
	}
	
	public InventoryWindow getInventoryWindow(){
		return inventoryWindow;
	}
	
	public QuestionWindow getQuestionWindow(){
		return questionWindow;
	}
	
	public ShopWindow getShopWindow(){
		return shopWindow;
	}
	
	public CharacterWindow getCharacterWindow(){
		return characterWindow;
	}
	
	public boolean isSpeakWindow(){
		return speakWindow != null;
	}
	
	public boolean isInventoryWindow(){
		return inventoryWindow.isVisible;
	}
	
	public boolean isQuestionWindow(){
		if(questionWindow != null)
			return questionWindow.isVisible;
		return false;
	}
	
	public boolean isShopWindow(){
		return shopWindow != null;
	}
	
	public boolean isCharacterWindow(){
		return characterWindow.isVisible;
	}

	public Map getCurrentMap() {
		return currentMap;
	}
	
}
