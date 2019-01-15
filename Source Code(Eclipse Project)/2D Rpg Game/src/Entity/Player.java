package Entity;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import Engine.Variables;
import GUI.InventoryWindow;
import Graphicals.PlayerAnimationRenderer;
import Graphicals.PlayerRenderer;
import Map.Map;
import Structures.Item;
import Tile.Tile;

public class Player extends Mob{
	
	private boolean[] keys = new boolean[256];
	
	private PlayerRenderer playerRenderer;
	private ArrayList<Arrow> arrows = new ArrayList<Arrow>();
	
	private String name = "Player";
	
	private int level = 0;
	private int gold = 20000;
	
	private int currentHealth = 0;
	private int currentMana = 0;
	
	private int usablePoint = 1000;
	
	private int[] basicatribs = new int[Item.ATTRIB_COUNT];
	private int[] bonusAttribs = new int[Item.ATTRIB_COUNT];

	public Player(Map currentMap, int spawnX, int spawnY){
		this.currentMap = currentMap;
		this.x = spawnX;
		this.y = spawnY;
		init();
		this.width = 64;
		this.height = 64;
		this.cStartX = 17;
		this.cStartY = 47;
		this.cWidth = 30;
		this.cHeight = 15;
		this.gCStartX = 17;
		this.gCStartY = 15;
		this.gCWidth = 30;
		this.gCHeight = 45;
		this.speed = 3;
		this.direction = PlayerAnimationRenderer.DOWN;
		
		this.playerRenderer = new PlayerRenderer(this);
		
	}
	
	private void init(){
		
		basicatribs[Item.ATTACK_ATTRIB_TYPE] = 100;
		basicatribs[Item.MAGIC_ATTACK_ATTRIB_TYPE] = 100;
		basicatribs[Item.CRITICAL_ATTRIB_TYPE] = 10;
		basicatribs[Item.DEF_ATTRIB_TYPE] = 10;
		basicatribs[Item.HEALTH_ATTRIB_TYPE] = 150;
		basicatribs[Item.MANA_ATTRIB_TYPE] = 150;
		currentHealth = 150;
		currentMana = 150;
		basicatribs[Item.SPEED_ATTRIB_TYPE] = 3;
		
	}
	
	@Override
	public void update() {
		dx = 0;
		dy = 0;
		
		if(keys[Input.KEY_W])
			dy = -1 * getTotalAtribs()[Item.SPEED_ATTRIB_TYPE];
		if(keys[Input.KEY_A])
			dx = -1 * getTotalAtribs()[Item.SPEED_ATTRIB_TYPE];
		if(keys[Input.KEY_S])
			dy =  1 * getTotalAtribs()[Item.SPEED_ATTRIB_TYPE];
		if(keys[Input.KEY_D])
			dx =  1 * getTotalAtribs()[Item.SPEED_ATTRIB_TYPE];
		
		move(dx,dy);
		
		x += dx;
		y += dy;
		
		// Direction Staffs
		if(dx > 0){
			direction = PlayerAnimationRenderer.RIGHT;
		}else if(dx < 0){
			direction = PlayerAnimationRenderer.LEFT;
		}else if(dy > 0){
			direction = PlayerAnimationRenderer.DOWN;
		}else if(dy < 0){
			direction = PlayerAnimationRenderer.TOP;
		}

		// Offset Staffs
		currentMap.xOffset = (int) (x - Variables.SCREEN_WIDTH / 2 + width / 2);
		currentMap.yOffset = (int) (y - Variables.SCREEN_HEIGHT / 2 + height /2);
		
		if(currentMap.xOffset < 0)
			currentMap.xOffset = 0;
		if(currentMap.yOffset < 0)
			currentMap.yOffset = 0;
		if(currentMap.xOffset > currentMap.mapWidth * Tile.tileSize - Variables.SCREEN_WIDTH)
			currentMap.xOffset = currentMap.mapWidth * Tile.tileSize - Variables.SCREEN_WIDTH;
		if(currentMap.yOffset > currentMap.mapHeight * Tile.tileSize - Variables.SCREEN_HEIGHT)
			currentMap.yOffset = currentMap.mapHeight * Tile.tileSize - Variables.SCREEN_HEIGHT;
		
		// Animation Stuff
		if(dx == 0 && dy == 0 && !attacking)
			playerRenderer.setAnimation(PlayerAnimationRenderer.IDDLE, PlayerAnimationRenderer.IDDLE_DELAY);
		else if(!attacking){
			playerRenderer.setAnimation(PlayerAnimationRenderer.WALKING, PlayerAnimationRenderer.WALKING_DELAY);
		}
		
		playerRenderer.update();
		
		currentMap.getGUIManager().getInventoryWindow().updateTimers();
		controls();
		
		for(Arrow arrow : arrows){
			arrow.update();
		}
	}
	
	private void controls(){
		
		if(currentHealth >= getTotalAtribs()[Item.HEALTH_ATTRIB_TYPE])
			currentHealth = (int) getTotalAtribs()[Item.HEALTH_ATTRIB_TYPE];
		if(currentMana >= getTotalAtribs()[Item.MANA_ATTRIB_TYPE])
			currentMana = (int) getTotalAtribs()[Item.MANA_ATTRIB_TYPE];
		
	}

	@Override
	public void render(Graphics g) {
		playerRenderer.render((int)x - currentMap.xOffset, (int)y - currentMap.yOffset, g);
		
		g.setColor(Color.red);
		g.drawRect((int)x + cStartX - currentMap.xOffset, (int)y + cStartY - currentMap.yOffset, cWidth, cHeight);
		g.setColor(Color.cyan);
		g.drawRect((int)x + gCStartX - currentMap.xOffset, (int)y +  gCStartY - currentMap.yOffset, gCWidth, gCHeight);
		g.setColor(Color.green);
		
		if(attacking){
			Rectangle rect = playerRenderer.getAttackRect(currentMap.getGUIManager().getInventoryWindow().getGearItems()[Item.WEAPON_TYPE].getAttackType());
			g.drawRect((int)x + rect.x - currentMap.xOffset, (int)y + rect.y - currentMap.yOffset, rect.width, rect.height);
		}
		
		for(Arrow arrow : arrows){
			arrow.render(g);
		}
	}

	public void keyPressed(int keyCode) {
		keys[keyCode] = true;
		
		if(keyCode == Input.KEY_E){
			interactWithNpc();
		}
		
		if(keyCode == Input.KEY_SPACE && currentMap.getGUIManager().getInventoryWindow().getGearItems()[Item.WEAPON_TYPE] != null && !attacking ){
			attacking = true;
			if(currentMap.getGUIManager().getInventoryWindow().getGearItems()[Item.WEAPON_TYPE].getAttackType() == Item.SPLASH_TYPE)
				playerRenderer.setAnimation(PlayerAnimationRenderer.ATTACK_SPLASH, PlayerAnimationRenderer.ATTACK_SPLASH_DELAY);
			else if(currentMap.getGUIManager().getInventoryWindow().getGearItems()[Item.WEAPON_TYPE].getAttackType() == Item.THRUST_TYPE)
				playerRenderer.setAnimation(PlayerAnimationRenderer.ATTACK_THRUST, PlayerAnimationRenderer.ATTACK_THRUST_DELAY);
			else if(currentMap.getGUIManager().getInventoryWindow().getGearItems()[Item.WEAPON_TYPE].getAttackType() == Item.SHOOT_TYPE){
				playerRenderer.setAnimation(PlayerAnimationRenderer.ATTACK_SHOOT, PlayerAnimationRenderer.ATTACK_SHOOT_DELAY);
				arrows.add(new Arrow(this.getFirstArrow(), this));
			}
		}
	}

	public void keyReleased(int keyCode) {
		keys[keyCode] = false;
	}

	public int getLevel() {
		return level;
	}
	
	public void gearItem(Item item){
		playerRenderer.gearItem(item);
		
		for(int i = 0 ; i < item.getAttribs().length ; i++){
			if(item.getAttribs()[i][0] == -1)
				continue;
			else
				increaseBonusAttrib(item.getAttribs()[i][0], item.getAttribs()[i][1]);
		}
		
	}

	public void disGearItem(Item item) {
		playerRenderer.disGearItem(item);
		
		for(int i = 0 ; i < item.getAttribs().length ; i++){
			if(item.getAttribs()[i][0] == -1)
				continue;
			else
				increaseBonusAttrib(item.getAttribs()[i][0], -item.getAttribs()[i][1]);
		}
		
	}
	
	public int getGold(){
		return gold;
	}
	
	public void increaseGold(int gold){
		this.gold += gold;
	}
	
	public String getName(){
		return name;
	}
	
	public int getUsablePoint(){
		return usablePoint;
	}
	
	public int[] getBasicAttribs(){
		return basicatribs;
	}
	
	public int[] getBonusAttribs(){
		return bonusAttribs;
	}

	public void increaseBasicAttrib(int attribType, int count) {
		
		if(usablePoint >= count){
			basicatribs[attribType] += count;
			usablePoint -= count;
		
			if(attribType == Item.STR_ATTRIB_TYPE){
				increaseBasicAttribWithoutControl(Item.ATTACK_ATTRIB_TYPE, count * 12);
			}else if(attribType == Item.HP_ATTRIB_TYPE){
				increaseBasicAttribWithoutControl(Item.HEALTH_ATTRIB_TYPE, count * 15);
			}else if(attribType == Item.DEX_ATTRIB_TYPE){
				increaseBasicAttribWithoutControl(Item.SPEED_ATTRIB_TYPE, count);
				increaseBasicAttribWithoutControl(Item.ATTACK_ATTRIB_TYPE, count * 2);
				increaseBasicAttribWithoutControl(Item.CRITICAL_ATTRIB_TYPE, count * 2);
			}else if(attribType == Item.INT_ATTRIB_TYPE){
				increaseBasicAttribWithoutControl(Item.MAGIC_ATTACK_ATTRIB_TYPE, count * 12);
				increaseBasicAttribWithoutControl(Item.MANA_ATTRIB_TYPE, count * 10);
			}
		}
		
	}
	
	public void increaseBasicAttribWithoutControl(int attribType, int count){
		basicatribs[attribType] += count;
	}

	public void increaseBonusAttrib(int attribType, int count){
		
		bonusAttribs[attribType] += count;
		
		if(attribType == Item.STR_ATTRIB_TYPE){
			increaseBonusAttribWithoutControl(Item.ATTACK_ATTRIB_TYPE, count * 12);
		}else if(attribType == Item.HP_ATTRIB_TYPE){
			increaseBonusAttribWithoutControl(Item.HEALTH_ATTRIB_TYPE, count * 15);
		}else if(attribType == Item.DEX_ATTRIB_TYPE){
			increaseBonusAttribWithoutControl(Item.SPEED_ATTRIB_TYPE, count);
			increaseBonusAttribWithoutControl(Item.ATTACK_ATTRIB_TYPE, count * 2);
			increaseBonusAttribWithoutControl(Item.CRITICAL_ATTRIB_TYPE, count * 2);
		}else if(attribType == Item.INT_ATTRIB_TYPE){
			increaseBonusAttribWithoutControl(Item.MAGIC_ATTACK_ATTRIB_TYPE, count * 12);
			increaseBonusAttribWithoutControl(Item.MANA_ATTRIB_TYPE, count * 10);
		}
				
	}
	
	public void increaseBonusAttribWithoutControl(int attribType, int count){
		bonusAttribs[attribType] += count;
	}

	public double[] getTotalAtribs() {
		double[] total = new double[Item.ATTRIB_COUNT];
		
		
		for(int i = 0 ; i < total.length ; i++){
			total[i] = basicatribs[i] + bonusAttribs[i];
		}
		
		return total;
	}
	
	public int getCurrentHealth(){
		return currentHealth;
	}

	public int getCurrentMana() {
		return currentMana;
	}

	public void setAttacking(boolean b) {
		attacking = false;
	}
	
	public void increaseHealth(int count){
		currentHealth += count;
		
		if(currentHealth < 0)
			currentHealth = 0;
		else if(currentHealth > basicatribs[Item.HEALTH_ATTRIB_TYPE] + bonusAttribs[Item.HEALTH_ATTRIB_TYPE])
			currentHealth = basicatribs[Item.HEALTH_ATTRIB_TYPE] + bonusAttribs[Item.HEALTH_ATTRIB_TYPE];
	}
	
	public void increaseMana(int count){
		currentMana += count;
		
		if(currentMana < 0)
			currentMana = 0;
		else if(currentMana > basicatribs[Item.MANA_ATTRIB_TYPE] + bonusAttribs[Item.MANA_ATTRIB_TYPE])
			currentMana = basicatribs[Item.MANA_ATTRIB_TYPE] + bonusAttribs[Item.MANA_ATTRIB_TYPE];
	}

	public InventoryWindow getInventory() {
		return currentMap.getGUIManager().getInventoryWindow();
	}

	public ArrayList<Arrow> getArrows(){
		return arrows;
	}

	public String getFirstArrow(){
		return "/Items/Sprites/Basic Arrow.png";
	}

	public Map getCurrentMap() {
		return currentMap;
	}
}
