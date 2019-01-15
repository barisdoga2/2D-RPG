package Structures;

import Entity.Player;

public class Timer {
	
	public static final int POTION_TIMER = 0;
	
	// End Of Static Content
	
	private Player player;
	public int slot;
	public int type;
	public boolean actionEnded;
	public boolean updateEnded;
	
	// Timers
	public long lastTime = 0;
	public long now = 0;
	public long timer = 0;
	public long deltaTime = 0;
	public long total = 0;
	
	// Potion Variables
	public Item item;
	public long castTime;
	public int totalValue;
	public long coolDown;
	public float perUpdateGive;
		
	public Timer(int type, int slot, Player player){
		this.player = player;
		this.type = type;
		this.item = player.getInventory().getInventoryItems()[slot];
		
		this.castTime = item.getSpecialAttrib()[Item.CAST_TIME];
		this.totalValue = item.getSpecialAttrib()[Item.TOTAL_VALUE];
		this.coolDown = item.getSpecialAttrib()[Item.COOL_DOWN];
		this.deltaTime = item.getSpecialAttrib()[Item.DELTA_TIME];
		this.perUpdateGive = totalValue / (castTime / deltaTime);
		this.slot = slot;
		this.lastTime = System.currentTimeMillis();
	}
	
	public void update(){
		
		
	
		now = System.currentTimeMillis();
		
		timer += now - lastTime;
		total += now - lastTime;
		
		if(!updateEnded){
			
			if(timer >= deltaTime){
				castTime -= timer;
				coolDown -= timer;
				timer -= deltaTime;
				
				doAction();
				
				if(coolDown <= 0){
					coolDown = 0;
					updateEnded = true;
				}
				
				if(castTime <= 0){
					actionEnded = true;
					castTime = 0;
				}
			}
		}
			
		lastTime = System.currentTimeMillis();
			
	}
	
	private void doAction(){
				
		if(actionEnded)
			return;
		
		if(type == POTION_TIMER){
			
			if(item.getItemType() == Item.HEALTH_POTION_TYPE){
				player.increaseHealth((int)perUpdateGive);
				if(player.getCurrentHealth() >= player.getTotalAtribs()[Item.HEALTH_ATTRIB_TYPE])
					actionEnded = true;
			}else if(item.getItemType() == Item.MANA_POTION_TYPE){
				player.increaseMana((int)perUpdateGive);
				if(player.getCurrentMana() >= player.getTotalAtribs()[Item.MANA_ATTRIB_TYPE])
					actionEnded = true;
			}
			
		}
		
	}
	
	
	
}
