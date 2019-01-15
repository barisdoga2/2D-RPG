package GUI;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import Engine.Variables;
import Structures.Item;
import Structures.Timer;

public class InventoryWindow {

	private GUIManager guiManager;
	
	protected boolean isVisible = false;
	
	private ArrayList<Timer> timers = new ArrayList<Timer>();
	
	private int[] inventoryItemsCounts = new int[16];
	private Item[] inventoryItems = new Item[16];
	private Item[] gearItems = new Item[8];
	
	private ArrayList<Rectangle> inventorySlots = new ArrayList<Rectangle>();
	private ArrayList<Rectangle> gearSlots = new ArrayList<Rectangle>();
	
	private Rectangle moveRect;
	private boolean screenMoving;
		
	private boolean infoWindow = false;
	private Item infoTarget;
	private int mouseX;
	private int mouseY;
	
	// Item Move Variables
	private int movingSlot = -1;
	private boolean inventoryItem;
	private int itemX;
	private int itemY;
	
	private int divideSlot;
	
	private Color itemInUseColor = new Color(35,35,35,175);

	public InventoryWindow(GUIManager guiManager){
		this.guiManager = guiManager;
		initRectangles();
		inventoryItems[0] = Variables.items[10];
		gearItemFromSlot(0);
	}
	
	public void render(Graphics g){
		g.drawImage(Variables.inventoryWindow, Variables.inventoryWindowX, Variables.inventoryWindowY);
		
		drawInventoryItems(g);
		drawGearItems(g);
		
		g.drawImage(Variables.coins, Variables.inventoryWindowX + 318, Variables.inventoryWindowY + 260);
		g.setFont(guiManager.textFont);
		g.drawString("" + guiManager.getCurrentMap().getPlayer().getGold(), Variables.coins.getWidth() + Variables.inventoryWindowX + 318, Variables.inventoryWindowY + 242);
		
		renderInfoWindow(g);
		
		drawUsingItems(g);
	}
	
	public void renderMoving(Graphics g){
		if(movingSlot != -1){
			if(inventoryItem)
				g.drawImage(inventoryItems[movingSlot].getIco(), itemX, itemY);
			else
				g.drawImage(gearItems[movingSlot].getIco(), itemX, itemY);
		}
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
		
		y += 25;
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

	private void drawGearItems(Graphics g) {
		for(int i = 0 ; i < gearItems.length ; i++){
			if(gearItems[i] != null){
				if(!inventoryItem && movingSlot == i)
					continue;
				g.drawImage(gearItems[i].getIco(), gearSlots.get(i).x, gearSlots.get(i).y);
			}
		}
	}

	private void drawInventoryItems(Graphics g) {
		g.setFont(guiManager.infoFont);
		for(int i = 0 ; i < inventoryItems.length ; i++){
			if(inventoryItems[i] != null){
				if(inventoryItem && movingSlot == i)
					continue;
				g.drawImage(inventoryItems[i].getIco(), inventorySlots.get(i).x, inventorySlots.get(i).y);
				if(inventoryItems[i].isCountable())
					g.drawString("" + inventoryItemsCounts[i], inventorySlots.get(i).x + 18, inventorySlots.get(i).y + 5);
			}
		}
	}
	
	private void drawUsingItems(Graphics g){
		
		for(int i = 0 ; i < inventoryItems.length ; i++){
			for(int k = 0 ; k < timers.size() ; k++){
				if(inventoryItems[i] != null && inventoryItems[i].getItemType() == timers.get(k).item.getItemType() && !timers.get(k).updateEnded){
					g.setColor(itemInUseColor);
					g.fillArc(inventorySlots.get(i).x, inventorySlots.get(i).y, 32, 32, 270, 270 + ((int)(((timers.get(k).total * 100 ) / timers.get(k).item.getSpecialAttrib()[Item.COOL_DOWN]) * 3.6)), 270 );
				}
			}
		}
		
	}
	
	// General Methods
	
	public void insertItem(Item item, int count, int slot) {
		
		if(slot != -1){
			
			inventoryItems[slot] = item;
			
			if(item.isCountable()){
				inventoryItemsCounts[slot] += count;
			}
			
		}else{
			int firstEmpty = getFirstEmptySlotNumber();
			
			inventoryItems[firstEmpty] = item;
			
			if(item.isCountable()){
				inventoryItemsCounts[firstEmpty] += count;
			}
			
		}
		
	}
	
	public void deleteItem(Item item, int count, int slot){
		
		if(slot != -1){
			
			if(item.isCountable()){
				inventoryItemsCounts[slot] -= count;
				if(inventoryItemsCounts[slot] <= 0){
					inventoryItems[slot] = null;
					inventoryItemsCounts[slot] = 0;
				}
			}else{
				inventoryItems[slot] = null;
			}
			
		}else{
			
			int itemSlot = getItemSlot(item);
			
			if(item.isCountable()){
				inventoryItemsCounts[itemSlot] -= count;
				if(inventoryItemsCounts[itemSlot] <= 0){
					inventoryItems[itemSlot] = null;
					inventoryItemsCounts[itemSlot] = 0;
				}
			}else{
				inventoryItems[itemSlot] = null;
			}
			
		}
		
	}
	
	public void gearItemFromSlot(int slot){
		
		if(inventoryItems[slot].getRequiredLevel() > guiManager.getCurrentMap().getPlayer().getLevel())
			return;
		
		if(gearItems[inventoryItems[slot].getItemType()] != null){
			Item tmp = gearItems[inventoryItems[slot].getItemType()];
			guiManager.getCurrentMap().getPlayer().disGearItem(tmp);
			
			gearItems[inventoryItems[slot].getItemType()] = inventoryItems[slot]; 
			guiManager.getCurrentMap().getPlayer().gearItem(gearItems[inventoryItems[slot].getItemType()]);
			
			inventoryItems[slot] = tmp;
			
		}else{
			gearItems[inventoryItems[slot].getItemType()] = inventoryItems[slot]; 
			guiManager.getCurrentMap().getPlayer().gearItem(gearItems[inventoryItems[slot].getItemType()]);
			inventoryItems[slot] = null;
		}
		
	}
	
	public void disGearFromSlotToSlot(int slot, int toSlot){
		
		if(toSlot == -1)
			toSlot = getFirstEmptySlotNumber();
		
		if(inventoryItems[toSlot] != null){
			
			toSlot = getFirstEmptySlotNumber();
			inventoryItems[toSlot] = gearItems[slot];
			guiManager.getCurrentMap().getPlayer().disGearItem(gearItems[inventoryItems[slot].getItemType()]);
			gearItems[slot] = null;
			
		}else{
			inventoryItems[toSlot] = gearItems[slot];
			guiManager.getCurrentMap().getPlayer().disGearItem(gearItems[slot]);
			gearItems[slot] = null;
		}
		
	}
	
	public void usePotionItem(int slot) {
		boolean found = false;
		int i = 0;
		for(i = 0 ; i < timers.size() ; i++){
			if(timers.get(i).item.getItemType() == inventoryItems[slot].getItemType()){
				found = true;
				break;
			}
		}
		
		if(found){
			if(timers.get(i).updateEnded){
				timers.remove(i);
				timers.add(new Timer(Timer.POTION_TIMER, slot, guiManager.getCurrentMap().getPlayer()));
				System.out.println("Deleted And Created Again!");
				deleteItem(inventoryItems[slot], 1, slot);
			}
		}else{
			System.out.println("Just Created!");
			timers.add(new Timer(Timer.POTION_TIMER, slot, guiManager.getCurrentMap().getPlayer()));
			deleteItem(inventoryItems[slot], 1, slot);
		}
	}
	
	// Input Handing
	
	public void mouseMoved(int oldx, int oldy, int newx, int newy){
		mouseX = newx;
		mouseY = newy;
		
		for(int i = 0 ; i < inventorySlots.size() ; i++){
			if(inventorySlots.get(i).contains(newx, newy) && inventoryItems[i] != null){
				infoWindow = true;
				infoTarget = inventoryItems[i];
				return;
			}
		}
		
		for(int i = 0 ; i < gearSlots.size() ; i++){
			if(gearSlots.get(i).contains(newx, newy) && gearItems[i] != null){
				infoWindow = true;
				infoTarget = gearItems[i];
				return;
			}
		}
		
		infoWindow = false;
	}
	
	public void mouseClicked(int button, int x, int y) {
		
		if(button == Input.MOUSE_RIGHT_BUTTON){
			
			for(int i = 0 ; i < inventorySlots.size() ; i++){
				if(inventorySlots.get(i).contains(x , y)){
					
					if(inventoryItems[i] != null && guiManager.isShopWindow()){
						if(inventoryItems[i].isCountable()){
							guiManager.getQuestionWindow().setState("How many to sell ?", QuestionWindow.COUNT_SELL_STATE);
							guiManager.getShopWindow().setInteractingItem(inventoryItems[i]);
							guiManager.getShopWindow().setInteractingInventorySlot(i);
						}else{
							guiManager.getQuestionWindow().setState("Sell Item for " + inventoryItems[i].getSellPrice() + " gold ?", QuestionWindow.SELL_STATE);
							guiManager.getShopWindow().setInteractingItem(inventoryItems[i]);
							guiManager.getShopWindow().setInteractingInventorySlot(i);
						}
					}else if(inventoryItems[i] != null && !guiManager.isShopWindow() && inventoryItems[i].getItemType() <= Item.LAST_GEAR_TYPE){
						gearItemFromSlot(i);
					}else if(inventoryItems[i] != null && (inventoryItems[i].getItemType() == Item.HEALTH_POTION_TYPE || inventoryItems[i].getItemType() == Item.MANA_POTION_TYPE)){
						usePotionItem(i);
					}
					break;
				}
			}
			
			for(int i = 0 ; i < gearSlots.size() ; i++){
				if(gearSlots.get(i).contains(x, y)){
					
					if(gearItems[i] != null && getEmptySlotNumber() > 0){
						disGearFromSlotToSlot(i, -1);
					}
					
				}
			}
			
		}else if(button == Input.MOUSE_LEFT_BUTTON){
			
			
			
		}else if(button == Input.MOUSE_MIDDLE_BUTTON){
			
			for(int i = 0 ; i < inventorySlots.size() ; i++){
				
				if(inventorySlots.get(i).contains(x, y) && inventoryItems[i] != null && inventoryItems[i].isCountable()){
					divideSlot = i;
					guiManager.getQuestionWindow().setState("How much to divide ?", QuestionWindow.DIVISION_ITEM_STATE);
				}
				
			}
			
		}
	}
	
	public void mouseDragged(int oldx, int oldy, int newx, int newy){
		
		if(screenMoving){
			Variables.inventoryWindowX += newx - oldx;
			Variables.inventoryWindowY += newy - oldy;
			initRectangles();
		}
		
		if(movingSlot != -1){
			itemX += newx - oldx;
			itemY += newy - oldy;
			infoWindow = false;
		}
		
	}
	
	public void mousePressed(int button, int x, int y){
		if(moveRect != null && moveRect.contains(x, y) && button == Input.MOUSE_LEFT_BUTTON)
			screenMoving = true;
		
		if(button == Input.MOUSE_LEFT_BUTTON){
			
			for(int i = 0 ; i < inventorySlots.size() ; i++){
				if(inventorySlots.get(i).contains(x, y) && inventoryItems[i] != null){
					inventoryItem = true;
					movingSlot = i;
					itemX = inventorySlots.get(i).x;
					itemY = inventorySlots.get(i).y;
					infoWindow = false;
					return;
				}
			}
			
			for(int i = 0 ; i < gearSlots.size() ; i++){
				if(gearSlots.get(i).contains(x, y) && gearItems[i] != null){
					inventoryItem = false;
					movingSlot = i;
					itemX = gearSlots.get(i).x;
					itemY = gearSlots.get(i).y;
					infoWindow = false;
					return;
				}
			}
			
		}
	}
	
	public void mouseReleased(int button, int x, int y){
		if(screenMoving)
			screenMoving = false;
		
		if(button == Input.MOUSE_LEFT_BUTTON){
			
			if(movingSlot != -1){
				
				for(int i = 0 ; i < inventorySlots.size() ; i++){
					if(inventorySlots.get(i).intersects(new Rectangle(itemX, itemY, 32, 32))){
						if(inventoryItem){
							
							if(i == movingSlot)
								continue;
							
							if(inventoryItems[i] == null){
								
								inventoryItemsCounts[i] = inventoryItemsCounts[movingSlot];
								inventoryItemsCounts[movingSlot] = 0;
								inventoryItems[i] = inventoryItems[movingSlot];
								inventoryItems[movingSlot] = null;
								movingSlot = -1;
								break;
								
							}else{
								
								if(inventoryItems[i] == inventoryItems[movingSlot] && inventoryItems[i].isCountable()){
									
									inventoryItemsCounts[i] += inventoryItemsCounts[movingSlot];
									inventoryItemsCounts[movingSlot] = 0;
									inventoryItems[movingSlot] = null;
									movingSlot = -1;
									break;
									
								}else{
									Item tmp = inventoryItems[i];
									int tmp2 = inventoryItemsCounts[i];
									inventoryItems[i] = inventoryItems[movingSlot];
									inventoryItemsCounts[i] = inventoryItemsCounts[movingSlot];
									inventoryItems[movingSlot] = tmp;
									inventoryItemsCounts[movingSlot] = tmp2;
									movingSlot = -1;
									break;
								}
							
							}
							
						}else{
							
							if(inventoryItems[i] == null){
								disGearFromSlotToSlot(movingSlot, i);
								movingSlot = -1;
								break;
							}else if(inventoryItems[i] != null && inventoryItems[i].getItemType() == gearItems[movingSlot].getItemType()){
								gearItemFromSlot(i);
								movingSlot = -1;
								break;
							}
							
						}

					}
				}
				
				if(inventoryItem){
					for(int i = 0 ; i < gearSlots.size() ; i++){
						if(gearSlots.get(i).intersects(new Rectangle(itemX, itemY, 32, 32))){
							
							if(inventoryItems[movingSlot].getItemType() == i){
								gearItemFromSlot(movingSlot);
							}
							
							movingSlot = -1;
							return;
						}
					}
				}
				
			}
			
			
			movingSlot = -1;
		}
	}

	// Utils
	
	public int getItemSlot(Item item){
		
		for(int i = 0 ; i < inventoryItems.length ; i++){
			if(inventoryItems[i] != null && inventoryItems[i] == item){
				return i;
			}
		}
		
		return -1;
		
	}
	
	public int getUseSlotNumber(Item item){
		
		if(item.isCountable()){
			
			for(int i = 0 ; i < inventoryItems.length ; i++){
				if(inventoryItems[i] != null && inventoryItems[i] == item){
					return 0;
				}
			}
			
		}
		
		return 1;

		
	}
	
	public int getEmptySlotNumber() {
		int count = 0;
		for(Item item : inventoryItems){
			if(item == null)
				count++;
		}
		return count;
	}
	
	public int getFirstEmptySlotNumber(){
		for(int i = 0 ; i < inventoryItems.length ; i++){
			if(inventoryItems[i] == null)
				return i;
		}
		return -1;
	}
	
	private void initRectangles(){
		int xx = Variables.inventoryWindowX + 41;
		int yy = Variables.inventoryWindowY + 285;
		inventorySlots.clear();
		for(int y = 0 ; y < 2 ; y++){
			for(int x = 0 ; x < 8 ; x++){
				inventorySlots.add(new Rectangle(xx, yy, 32, 32));
				xx += 47;
			}
			xx = Variables.inventoryWindowX + 41;
			yy += 47;
		}
		
		gearSlots.clear();
		gearSlots.add( new Rectangle(Variables.inventoryWindowX + 113, Variables.inventoryWindowY + 74, 32, 32) );
		gearSlots.add( new Rectangle(Variables.inventoryWindowX + 66, Variables.inventoryWindowY + 121, 32, 32) );
		gearSlots.add( new Rectangle(Variables.inventoryWindowX + 113, Variables.inventoryWindowY + 121, 32, 32) );
		gearSlots.add( new Rectangle(Variables.inventoryWindowX + 160, Variables.inventoryWindowY + 121, 32, 32) );
		gearSlots.add( new Rectangle(Variables.inventoryWindowX + 66, Variables.inventoryWindowY + 168, 32, 32) );
		gearSlots.add( new Rectangle(Variables.inventoryWindowX + 113, Variables.inventoryWindowY + 168, 32, 32) );
		gearSlots.add( new Rectangle(Variables.inventoryWindowX + 160, Variables.inventoryWindowY + 168, 32, 32) );
		gearSlots.add( new Rectangle(Variables.inventoryWindowX + 113, Variables.inventoryWindowY + 215, 32, 32) );
		
		moveRect = new Rectangle(Variables.inventoryWindowX, Variables.inventoryWindowY, Variables.inventoryWindow.getWidth(), 20);
		
	}

	public int[] getInventoryItemsCounts() {
		return inventoryItemsCounts;
	}

	public void divideItem(int count) {
		
		if(count >= inventoryItemsCounts[divideSlot])
			return;
		else{
			if(getEmptySlotNumber() > 0){
				
				int firstEmpty = getFirstEmptySlotNumber();
				
				inventoryItems[firstEmpty] = inventoryItems[divideSlot];
				inventoryItemsCounts[firstEmpty] = count;
				inventoryItemsCounts[divideSlot] -= count;
				
			}
			
		}
		
	}

	public Item[] getGearItems(){
		return gearItems;
	}

	public void updateTimers(){
		for(Timer timer : timers){
			timer.update();
		}
	}

	public Item[] getInventoryItems() {
		return inventoryItems;
	}
	
}
