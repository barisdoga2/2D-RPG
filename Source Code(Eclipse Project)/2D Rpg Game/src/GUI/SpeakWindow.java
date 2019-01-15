package GUI;

import java.awt.Rectangle;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import Engine.Variables;

public class SpeakWindow {
	
	private GUIManager guiManager;
	
	private boolean actionChanged = true;
	
	private Rectangle moveRect;
	private boolean screenMoving;
	private Rectangle nextRect;
	private Rectangle next2Rect;
	
	public SpeakWindow(GUIManager guiManager, int speakID){
		this.guiManager = guiManager;
		SpeakAction.loadSpeakActions(speakID);
		initRectangles();
	}
	
	public void render(Graphics g){
		g.drawImage(Variables.speakWindow, Variables.speakWindowX, Variables.speakWindowY);
		
		int x = Variables.speakWindowX + Variables.speakWindow.getWidth() / 2;
		int y = Variables.speakWindowY + 100;
		
		g.setFont(guiManager.textFont);
		
		g.drawString(SpeakAction.getCurrentAction().getActionText(), x - g.getFont().getWidth(SpeakAction.getCurrentAction().getActionText()) / 2, y);
		
		y += 150;
		
		g.setFont(guiManager.buttonFont);
		if(SpeakAction.getNextAction() != null && SpeakAction.getNext2Action() != null){
			x -= guiManager.getButtonWidth(SpeakAction.getNextAction().getActionName(), g) / 2 + 20 / 2 + guiManager.getButtonWidth(SpeakAction.getNext2Action().getActionName(), g) / 2;
			guiManager.drawButton(x, y, SpeakAction.getNextAction().getActionName(), g);
			if(actionChanged)
				nextRect = new Rectangle(x, y, guiManager.getButtonWidth(SpeakAction.getNextAction().getActionName(), g), 32);
			x += guiManager.getButtonWidth(SpeakAction.getCurrentAction().getNextActionName(), g) + 20 / 2 + guiManager.getButtonWidth(SpeakAction.getNext2Action().getActionName(), g) / 2;
			guiManager.drawButton(x, y, SpeakAction.getCurrentAction().getNext2ActionName(), g);
			if(actionChanged){
				next2Rect = new Rectangle(x, y, guiManager.getButtonWidth(SpeakAction.getNext2Action().getActionName(), g), 32);
				actionChanged = false;
			}
		}else if(SpeakAction.getNextAction() != null){
			x -= guiManager.getButtonWidth(SpeakAction.getNextAction().getActionName(), g) / 2;
			guiManager.drawButton(x, y, SpeakAction.getNextAction().getActionName(), g);
			if(actionChanged){
				nextRect = new Rectangle(x, y, guiManager.getButtonWidth(SpeakAction.getNextAction().getActionName(), g), 32);
				next2Rect = null;
				actionChanged = false;
			}
		}else if(SpeakAction.getNext2Action() != null){
			x -= guiManager.getButtonWidth(SpeakAction.getNext2Action().getActionName(), g) / 2;
			guiManager.drawButton(x, y, SpeakAction.getNext2Action().getActionName(), g);
			if(actionChanged){
				next2Rect = new Rectangle(x, y, guiManager.getButtonWidth(SpeakAction.getNext2Action().getActionName(), g), 32);
				nextRect = null;
				actionChanged = false;
			}
		}else{
			if(actionChanged){
				nextRect = null;
				next2Rect = null;
				actionChanged = false;
			}
		}
		
	}
	
	public void mouseClicked(int button, int x, int y){
		if(nextRect != null)
			if(nextRect.contains(x, y)){
				setAction(SpeakAction.getNextAction());
			}
				
		if(next2Rect != null)
			if(next2Rect.contains(x, y)){
				SpeakAction.setCurrentAction(SpeakAction.getNext2Action());
				actionChanged = true;
			}
	}
	
	public void mouseDragged(int oldx, int oldy, int newx, int newy){
		if(screenMoving){
			Variables.speakWindowX += newx - oldx;
			Variables.speakWindowY += newy - oldy;
			initRectangles();
		}
	}
	
	public void mousePressed(int button, int x, int y){
		if(moveRect != null && moveRect.contains(x, y) && button == Input.MOUSE_LEFT_BUTTON)
			screenMoving = true;
	}
	
	public void mouseReleased(int button, int x, int y){
		if(screenMoving)
			screenMoving = false;
	}
	
	private void setAction(SpeakAction nextAction) {
		
		if(nextAction.isSpecial()){
			if(nextAction.specialData()[1].equals("Shop")){
				guiManager.openShopWindowWithName(nextAction.specialData()[2]);
			}
		}
		
		actionChanged = true;
	}

	private void initRectangles(){
		moveRect = new Rectangle(Variables.speakWindowX, Variables.speakWindowY, Variables.speakWindow.getWidth(), 20);
		actionChanged = true;
	}
	
}
