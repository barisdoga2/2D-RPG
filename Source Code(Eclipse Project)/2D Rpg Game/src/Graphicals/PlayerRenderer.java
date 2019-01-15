package Graphicals;

import java.awt.Rectangle;

import org.newdawn.slick.Graphics;

import Entity.Player;
import Structures.Item;

public class PlayerRenderer {
	
	private Player player;
		
	private PlayerAnimationRenderer spriteRenderer;
	
	private PlayerAnimationRenderer headRenderer;
	private PlayerAnimationRenderer gloveRenderer;
	private PlayerAnimationRenderer chestRenderer;
	private PlayerAnimationRenderer weaponRenderer;
	private PlayerAnimationRenderer legRenderer;
	private PlayerAnimationRenderer feetRenderer;
		
	public PlayerRenderer(Player player){
		this.player = player;
		this.spriteRenderer = new PlayerAnimationRenderer("/Player/SpriteSheet.png" ,player);
		this.chestRenderer = new PlayerAnimationRenderer("/Player/BasicChest.png", player);
	}
	
	public void update(){
		spriteRenderer.update();
		
		if(headRenderer != null)
			headRenderer.update();
		
		if(gloveRenderer != null)
			gloveRenderer.update();
		
		if(chestRenderer != null)
			chestRenderer.update();
		
		if(weaponRenderer != null)
			weaponRenderer.update();
		
		if(legRenderer != null)
			legRenderer.update();
		
		if(feetRenderer != null)
			feetRenderer.update();
	}

	public void render(int x, int y, Graphics g) {
		spriteRenderer.render(x, y, g);
		
		if(headRenderer != null)
			headRenderer.render(x, y, g);
		
		if(gloveRenderer != null)
			gloveRenderer.render(x, y, g);
		
		if(chestRenderer != null)
			chestRenderer.render(x, y, g);
		
		if(weaponRenderer != null)
			weaponRenderer.render(x, y, g);
		
		if(legRenderer != null)
			legRenderer.render(x, y, g);
		
		if(feetRenderer != null)
			feetRenderer.render(x, y, g);
	}
	
	public void setAnimation(int animationType, int animationDelay) {
		spriteRenderer.setAnimation(animationType, animationDelay);
		
		if(headRenderer != null)
			headRenderer.setAnimation(animationType, animationDelay);
		
		if(gloveRenderer != null)
			gloveRenderer.setAnimation(animationType, animationDelay);
		
		if(chestRenderer != null)
			chestRenderer.setAnimation(animationType, animationDelay);
		
		if(weaponRenderer != null)
			weaponRenderer.setAnimation(animationType, animationDelay);
		
		if(legRenderer != null)
			legRenderer.setAnimation(animationType, animationDelay);
		
		if(feetRenderer != null)
			feetRenderer.setAnimation(animationType, animationDelay);
	}
	
	public void gearItem(Item item){
		
		if(item.getItemType() == Item.HEAD_TYPE)
			headRenderer = new PlayerAnimationRenderer(item.getSpritePath(), player);
		
		if(item.getItemType() == Item.GLOVE_TYPE)
			gloveRenderer = new PlayerAnimationRenderer(item.getSpritePath(), player);
		
		if(item.getItemType() == Item.CHEST_TYPE)
			chestRenderer = new PlayerAnimationRenderer(item.getSpritePath(), player);
		
		if(item.getItemType() == Item.WEAPON_TYPE){
			weaponRenderer = new PlayerAnimationRenderer(item.getSpritePath(), player, item);
		}
		if(item.getItemType() == Item.LEG_TYPE)
			legRenderer = new PlayerAnimationRenderer(item.getSpritePath(), player);
		
		if(item.getItemType() == Item.FEET_TYPE)
			feetRenderer = new PlayerAnimationRenderer(item.getSpritePath(), player);
		
	}

	public void disGearItem(Item item) {
		
		if(item.getItemType() == Item.HEAD_TYPE)
			headRenderer = null;
		
		if(item.getItemType() == Item.GLOVE_TYPE)
			gloveRenderer = null;
		
		if(item.getItemType() == Item.CHEST_TYPE)
			chestRenderer = new PlayerAnimationRenderer("/Player/BasicChest.png", player);
		
		if(item.getItemType() == Item.WEAPON_TYPE)
			weaponRenderer = null;
		
		if(item.getItemType() == Item.LEG_TYPE)
			legRenderer = null;
		
		if(item.getItemType() == Item.FEET_TYPE)
			feetRenderer = null;
		
	}
	
	public Rectangle getAttackRect(int ITEM_ATTACK_TYPE){
		if(weaponRenderer == null)
			return null;
		
		if(ITEM_ATTACK_TYPE == Item.SPLASH_TYPE){
			return weaponRenderer.getAttackRect(PlayerAnimationRenderer.ATTACK_SPLASH);
		}else if(ITEM_ATTACK_TYPE == Item.THRUST_TYPE){
			return weaponRenderer.getAttackRect(PlayerAnimationRenderer.ATTACK_THRUST);
		}
		
		return new Rectangle(0, 0, 0, 0);
		
	}
	
}
