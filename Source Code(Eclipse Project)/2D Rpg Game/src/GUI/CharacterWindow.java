package GUI;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import Engine.Variables;
import Structures.Item;

public class CharacterWindow {
	
	public boolean isVisible;

	private GUIManager guiManager;
	
	private Rectangle moveRect;
	private boolean screenMoving;
	
	private ArrayList<Rectangle> plusRectangles = new ArrayList<Rectangle>();
	
	public CharacterWindow(GUIManager guiManager){
		this.guiManager = guiManager;
		initRectangles();
	}

	public void render(Graphics g) {
		g.drawImage(Variables.characterWindow, Variables.characterWindowX, Variables.characterWindowY);
		
		int x = Variables.characterWindowX;
		int y = Variables.characterWindowY + 28;
		
		g.setFont(guiManager.textFont);
		x = Variables.characterWindowX + Variables.characterWindow.getWidth() / 2 - g.getFont().getWidth(guiManager.getCurrentMap().getPlayer().getName()) / 2;
		g.drawString(guiManager.getCurrentMap().getPlayer().getName(), x, y);
		
		g.setFont(guiManager.infoFont);
		x = Variables.characterWindowX + Variables.characterWindow.getWidth() / 2 - g.getFont().getWidth("Level " + guiManager.getCurrentMap().getPlayer().getLevel()) / 2;
		y+= 40;
		g.drawString("Level " + guiManager.getCurrentMap().getPlayer().getLevel(), x, y);
		
		x = Variables.characterWindowX + 33;
		y+= 45;
		g.drawString("Points : " + guiManager.getCurrentMap().getPlayer().getUsablePoint() , x, y);
		
		x = Variables.characterWindowX + 33;
		y = Variables.characterWindowY + 155;
		g.drawString("Str :" + guiManager.getCurrentMap().getPlayer().getBasicAttribs()[Item.STR_ATTRIB_TYPE] + "+" + guiManager.getCurrentMap().getPlayer().getBonusAttribs()[Item.STR_ATTRIB_TYPE], x, y);
		
		y+= 40;
		g.drawString("Hp :" + guiManager.getCurrentMap().getPlayer().getBasicAttribs()[Item.HP_ATTRIB_TYPE] + "+" + guiManager.getCurrentMap().getPlayer().getBonusAttribs()[Item.HP_ATTRIB_TYPE], x, y);
		
		y+= 40;
		g.drawString("Dex :" + guiManager.getCurrentMap().getPlayer().getBasicAttribs()[Item.DEX_ATTRIB_TYPE] + "+" + guiManager.getCurrentMap().getPlayer().getBonusAttribs()[Item.DEX_ATTRIB_TYPE], x, y);
		
		y+= 40;
		g.drawString("Int :" + guiManager.getCurrentMap().getPlayer().getBasicAttribs()[Item.INT_ATTRIB_TYPE] + "+" + guiManager.getCurrentMap().getPlayer().getBonusAttribs()[Item.INT_ATTRIB_TYPE], x, y);
	
	
		x = Variables.characterWindowX + 177;
		y = Variables.characterWindowY + 135;
		g.drawString("Attack : ", x, y);
		g.drawString("" + guiManager.getCurrentMap().getPlayer().getTotalAtribs()[Item.ATTACK_ATTRIB_TYPE], x + 110, y);
		
		y+= 20;
		g.drawString("Magic Attack :", x, y);
		g.drawString("" + guiManager.getCurrentMap().getPlayer().getTotalAtribs()[Item.MAGIC_ATTACK_ATTRIB_TYPE], x + 110, y);
		
		y+= 20;
		g.drawString("Health :", x, y);
		g.drawString("" + guiManager.getCurrentMap().getPlayer().getTotalAtribs()[Item.HEALTH_ATTRIB_TYPE], x + 110, y);
		
		y+= 20;
		g.drawString("Mana :", x, y);
		g.drawString("" + guiManager.getCurrentMap().getPlayer().getTotalAtribs()[Item.MANA_ATTRIB_TYPE], x + 110, y);
		
		y+= 20;
		g.drawString("Defance :", x, y);
		g.drawString("" + guiManager.getCurrentMap().getPlayer().getTotalAtribs()[Item.DEF_ATTRIB_TYPE], x + 110, y);
		
		y+= 20;
		g.drawString("Critical :", x, y);
		g.drawString("" + guiManager.getCurrentMap().getPlayer().getTotalAtribs()[Item.CRITICAL_ATTRIB_TYPE], x + 110, y);
		
		y+= 20;
		g.drawString("Speed :", x, y);
		g.drawString("" + guiManager.getCurrentMap().getPlayer().getTotalAtribs()[Item.SPEED_ATTRIB_TYPE], x + 110, y);
	}

	public void mouseClicked(int button, int x, int y) {
		if(button == Input.MOUSE_LEFT_BUTTON){
			if(plusRectangles.get(0).contains(x, y)){
				guiManager.getCurrentMap().getPlayer().increaseBasicAttrib(Item.STR_ATTRIB_TYPE, 1);
			}else if(plusRectangles.get(1).contains(x, y)){
				guiManager.getCurrentMap().getPlayer().increaseBasicAttrib(Item.HP_ATTRIB_TYPE, 1);
			}else if(plusRectangles.get(2).contains(x, y)){
				guiManager.getCurrentMap().getPlayer().increaseBasicAttrib(Item.DEX_ATTRIB_TYPE, 1);
			}else if(plusRectangles.get(3).contains(x, y)){
				guiManager.getCurrentMap().getPlayer().increaseBasicAttrib(Item.INT_ATTRIB_TYPE, 1);
			}
		}
	}

	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		if(screenMoving){
			Variables.characterWindowX += newx - oldx;
			Variables.characterWindowY += newy- oldy;
			initRectangles();
		}
	}

	public void mousePressed(int button, int x, int y) {
		if(moveRect.contains(x, y))
			screenMoving = true;
	}

	public void mouseReleased(int button, int x, int y) {
		if(screenMoving)
			screenMoving = false;
	}
	
	private void initRectangles(){
		moveRect = new Rectangle(Variables.characterWindowX, Variables.characterWindowY, Variables.characterWindow.getWidth(), 20);
		
		plusRectangles.clear();
		plusRectangles.add(new Rectangle(Variables.characterWindowX + 129, Variables.characterWindowY + 159, 20, 32));
		plusRectangles.add(new Rectangle(Variables.characterWindowX + 129, Variables.characterWindowY + 199, 20, 32));
		plusRectangles.add(new Rectangle(Variables.characterWindowX + 129, Variables.characterWindowY + 239, 20, 32));
		plusRectangles.add(new Rectangle(Variables.characterWindowX + 129, Variables.characterWindowY + 279, 20, 32));
	}
	
}
