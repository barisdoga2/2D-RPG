package GUI;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import Engine.Variables;
import Structures.Item;
import Utils.GUIUtils;

public class ShopWindow {
	
	private GUIManager guiManager;
	
	private Rectangle moveRect;
	private boolean screenMoving;
	
	private ArrayList<Rectangle> shopSlots = new ArrayList<Rectangle>();
	
	private Item[] shopItems = new Item[28];
	
	private Item interactingItem;
	private int interactingItemCount = 1;
	private int interactingInventorySlot;
			
	private boolean infoWindow = false;
	private Item infoTarget;
	private int mouseX;
	private int mouseY;

	public ShopWindow(GUIManager guiManager, String name) {
		this.guiManager = guiManager;
		this.shopItems = GUIUtils.getShopItems(name);
		initRectangles();
	}
	
	public void render(Graphics g){
		g.drawImage(Variables.shopWindow, Variables.speakWindowX, Variables.speakWindowY);		
		drawShopItems(g);
		renderInfoWindow(g);
	}
	
	private void renderInfoWindow(Graphics g) {
		if(!infoWindow)
			return;
		
		g.drawImage(Variables.infoWindow, mouseX, mouseY);
		
		int x = mouseX + Variables.infoWindow.getWidth() / 2;
		int y = mouseY + 10;
		g.setFont(guiManager.itemNameFont);
		g.drawString(infoTarget.getItemName(), x - g.getFont().getWidth(infoTarget.getItemName()) / 2, y);
		
		y += 30;
		x = mouseX + 15;
		g.setFont(guiManager.infoFont);
		g.drawString("Level : ", x, y);
		
		y += 25;
		g.drawString("Buy   : ", x, y);
		
		y += 25;
		g.drawString("Sell : ", x, y);
		
		y += 25;
		g.drawString("++++++++++++++++++", x, y);
		
		
		for(int i = 0 ; i < infoTarget.getAttribs().length ; i++){
			if(infoTarget.getAttribs()[i][0] == -1)
				continue;
			else{
				y += 25;
				g.drawString(Item.ATTRIB_NAMES[infoTarget.getAttribs()[i][0]] + " :", x, y);
			}
		}
		
		y+= 25;
		g.setFont(guiManager.miniFont);
		g.drawString(infoTarget.getDiscription(), x, y);
		g.setFont(guiManager.infoFont);
		
		y = mouseY + 40;
		x = mouseX + 135;
		g.drawString("" + infoTarget.getRequiredLevel(), x, y);
		
		y += 25;
		g.drawString("" + infoTarget.getBuyPrice(), x, y);
		
		y += 25;
		g.drawString("" + infoTarget.getSellPrice(), x, y);
		
		y += 25;
		
		for(int i = 0 ; i < infoTarget.getAttribs().length ; i++){
			if(infoTarget.getAttribs()[i][0] == -1)
				continue;
			else{
				y += 25;
				g.drawString("" + infoTarget.getAttribs()[i][1], x, y);
			}
		}
		
	}

	private void drawShopItems(Graphics g){
		for(int i = 0 ; i < shopItems.length ; i++){
			if(shopItems[i] != null){
				g.drawImage(shopItems[i].getIco(), shopSlots.get(i).x, shopSlots.get(i).y);
			}
		}
	}

	// General Methods
	
	public void buyItem() {
		
		if(interactingItem.isCountable()){
			
			if(guiManager.getCurrentMap().getPlayer().getGold() < interactingItemCount * infoTarget.getBuyPrice()){
				
				guiManager.getQuestionWindow().setState("Not Enough Gold!", QuestionWindow.WARNING_STATE);
				
			}else if(guiManager.getInventoryWindow().getUseSlotNumber(interactingItem) == 0){
				
				guiManager.getInventoryWindow().insertItem(interactingItem, interactingItemCount, guiManager.getInventoryWindow().getItemSlot(interactingItem));
				guiManager.getCurrentMap().getPlayer().increaseGold(interactingItemCount * -interactingItem.getBuyPrice());
				//guiManager.getQuestionWindow().setState("Buying Completed!", QuestionWindow.WARNING_STATE);
				
			}else if(guiManager.getInventoryWindow().getEmptySlotNumber() >= guiManager.getInventoryWindow().getUseSlotNumber(interactingItem)){
				
				guiManager.getInventoryWindow().insertItem(interactingItem, interactingItemCount, -1);
				guiManager.getCurrentMap().getPlayer().increaseGold(interactingItemCount * -interactingItem.getBuyPrice());
				//guiManager.getQuestionWindow().setState("Buying Completed!", QuestionWindow.WARNING_STATE);
				
			}else{
				
				guiManager.getQuestionWindow().setState("Not Enough Inventory Space!", QuestionWindow.WARNING_STATE);
				
			}
			
		}else{
			
			if(guiManager.getCurrentMap().getPlayer().getGold() < interactingItem.getBuyPrice()){
				
				guiManager.getQuestionWindow().setState("Not Enough Inventory Space!", QuestionWindow.WARNING_STATE);
				
			}else if(guiManager.getInventoryWindow().getEmptySlotNumber() < 1){
				
				guiManager.getQuestionWindow().setState("Not Enough Inventory Space!", QuestionWindow.WARNING_STATE);
				
			}else{
				
				guiManager.getInventoryWindow().insertItem(interactingItem, 0, -1);
				guiManager.getCurrentMap().getPlayer().increaseGold(-interactingItem.getBuyPrice());
				//guiManager.getQuestionWindow().setState("Buying Completed!", QuestionWindow.WARNING_STATE);
				
			}
			
		}
		
		interactingItemCount = 1;
		
	}
	
	public void sellItem(){
		
		if(interactingItem.isCountable()){
			
			guiManager.getInventoryWindow().deleteItem(interactingItem, interactingItemCount, interactingInventorySlot);
			guiManager.getCurrentMap().getPlayer().increaseGold(interactingItemCount * interactingItem.getSellPrice());
			//guiManager.getQuestionWindow().setState("Selling Completed!", QuestionWindow.WARNING_STATE);

			
		}else{
			
			guiManager.getInventoryWindow().deleteItem(interactingItem, 0, interactingInventorySlot);
			guiManager.getCurrentMap().getPlayer().increaseGold(interactingItem.getSellPrice());
			//guiManager.getQuestionWindow().setState("Selling Completed!", QuestionWindow.WARNING_STATE);
			
		}
		
		interactingItemCount = 1;
		
	}

	// Input Handing
	
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		mouseX = newx;
		mouseY = newy;
		for(int i = 0 ; i < shopSlots.size() ; i++){
			if(shopSlots.get(i).contains(newx, newy) && shopItems[i] != null){
				infoWindow = true;
				infoTarget = shopItems[i];
				return;
			}
		}
		
		infoWindow = false;
	}
	
	public void mouseDragged(int oldx, int oldy, int newx, int newy){
		if(screenMoving){
			Variables.speakWindowX += newx - oldx;
			Variables.speakWindowY += newy - oldy;
			initRectangles();
		}
	}
	
	public void mouseClicked(int button, int x, int y){
		if(button == Input.MOUSE_LEFT_BUTTON){
			
			
			
		}else if(button == Input.MOUSE_RIGHT_BUTTON){
			
			for(int i = 0 ; i < shopSlots.size() ; i++){
				if(shopSlots.get(i).contains(x, y)){
					if(shopItems[i] != null){
						if(shopItems[i].isCountable()){
							
							guiManager.getQuestionWindow().setState("How much piece ?" , QuestionWindow.COUNT_BUY_STATE);
							
							
						}else{
							
							guiManager.getQuestionWindow().setState("Buy item for " + shopItems[i].getBuyPrice() + " gold ?", QuestionWindow.BUY_STATE);
							
						}
						
						interactingItem = shopItems[i];
					}
				}
			}
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

	// Utils
	
	private void initRectangles(){
		
		moveRect = new Rectangle(Variables.speakWindowX, Variables.speakWindowY, Variables.speakWindowX, 20);
		
		int xx = Variables.speakWindowX + 50;
		int yy = Variables.speakWindowY + 83;
		shopSlots.clear();
		for(int y = 0 ; y < 7 ; y++){
			for(int x = 0 ; x < 4 ; x++){
				shopSlots.add(new Rectangle(xx, yy, 32, 32));
				xx += 51;
			}
			xx = Variables.speakWindowX + 50;
			yy += 51;
		}

		
	}


	
	public void setBuyInteractingItemCount(int i) {
		interactingItemCount = i;
		guiManager.getQuestionWindow().setState("Buy item(s) for " + interactingItemCount * interactingItem.getBuyPrice(), QuestionWindow.BUY_STATE);
	}
	
	public void setSellInteractingItemCount(int i) {
		interactingItemCount = i;
		if(interactingItemCount > guiManager.getInventoryWindow().getInventoryItemsCounts()[interactingInventorySlot])
			interactingItemCount = guiManager.getInventoryWindow().getInventoryItemsCounts()[interactingInventorySlot];
		guiManager.getQuestionWindow().setState("Sell item(s) for " + interactingItemCount * interactingItem.getSellPrice(), QuestionWindow.SELL_STATE);
	}

	public void setInteractingItem(Item item) {
		interactingItem = item;
	}
	
	public void setInteractingInventorySlot(int i){
		interactingInventorySlot = i;
	}
	
}
