package GUI;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import Engine.Variables;
import Entity.Player;
import Structures.Item;

public class BarWindow {
	
	private static final int FULL_LENGTH = 154;
	
	private Player player;
	private GUIManager guiManager;

	public BarWindow(GUIManager guiManager, Player player){
		this.player = player;
		this.guiManager = guiManager;
	}

	public void update() {
		
	}

	public void render(Graphics g) {
				
		g.setColor(Color.red);
		g.fillRect(Variables.barWindowX + 24, Variables.barWindowY + 20, calculateLength(FULL_LENGTH,player.getCurrentHealth(), player.getTotalAtribs()[Item.HEALTH_ATTRIB_TYPE]), 14);
		g.setColor(Color.blue);
		g.fillRect(Variables.barWindowX + 24, Variables.barWindowY + 46, calculateLength(FULL_LENGTH,player.getCurrentMana(), player.getTotalAtribs()[Item.MANA_ATTRIB_TYPE]), 14);
		
		g.drawImage(Variables.barWindow, Variables.barWindowX, Variables.barWindowY);
		
		g.setColor(Color.white);
		g.setFont(guiManager.barWindowFont);
		g.drawString(player.getCurrentHealth() + " / " + player.getTotalAtribs()[Item.HEALTH_ATTRIB_TYPE], Variables.barWindowX + 24 + 154 / 2 - g.getFont().getWidth(player.getCurrentHealth() + " / " + player.getTotalAtribs()[Item.HEALTH_ATTRIB_TYPE]) / 2, Variables.barWindowY + 18);
		g.drawString(player.getCurrentMana() + " / " + player.getTotalAtribs()[Item.MANA_ATTRIB_TYPE], Variables.barWindowX + 24 + 154 / 2 - g.getFont().getWidth(player.getCurrentMana() + " / " + player.getTotalAtribs()[Item.MANA_ATTRIB_TYPE]) / 2, Variables.barWindowY + 44);

	}

	private float calculateLength(int fullLength, int currentHealth, double fullHealth) {
		float x = (float) ((currentHealth * 100) / fullHealth);
		float rtrn = x * fullLength / 100;
		
		if(rtrn > FULL_LENGTH)
			rtrn = (float)FULL_LENGTH;
		else if(rtrn < 0)
			rtrn = (float)0;
		
		return rtrn;
	}
	
}
