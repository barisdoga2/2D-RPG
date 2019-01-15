package Entity;

import java.awt.Rectangle;

import Engine.Variables;
import Utils.GUIUtils;

public abstract class Mob extends Entity{
	
	public static final int INTERACT_RANGE = 3;

	protected int cStartX;
	protected int cStartY;
	protected int cWidth;
	protected int cHeight;
	protected int gCStartX;
	protected int gCStartY;
	protected int gCWidth;
	protected int gCHeight;
	
	protected double speed;
	
	protected int direction = 0;
	
	protected double dx;
	protected double dy;
	
	protected boolean attacking = false;
	
	// Collision Methods
	protected void move(double dx, double dy){
		
		if(dx != 0 && dy != 0){
			move(dx, 0);
			move(0, dy);
			return;
		}
		
		if(dx != 0){
			if(dx < 0){
				if(hasLeftCollided(dx) || collidesWithMob(dx, dy)){
					this.dx = 0;
				}
			}else if(dx > 0){
				if(hasRightCollided(dx) || collidesWithMob(dx, dy)){
					this.dx = 0;
				}
			}
		}else if(dy != 0){
			if(dy < 0){
				if(hasTopCollided(dy) || collidesWithMob(dx, dy)){ 
					this.dy = 0;
				}
			}else if(dy > 0){
				if(hasBotCollided(dy) || collidesWithMob(dx, dy)){
					this.dy = 0;
				}
			}
		}
		
	}
	
	private boolean collidesWithMob(double dx, double dy) {
		Rectangle playerRect = new Rectangle((int)x + (int)dx + cStartX, (int)y + (int)dy + cStartY, cWidth, cHeight);
		for(Mob mob : currentMap.getMobManager().getPlacedMobs()){
			Rectangle mobRect = new Rectangle((int)mob.x + (int)mob.dx + mob.cStartX, (int)mob.y + (int)mob.dy + mob.cStartY, mob.cWidth, mob.cHeight);
			if(playerRect.intersects(mobRect))
				return true;
		}
		return false;
	}

	protected boolean hasTopCollided(double nextY){
		if(currentMap.isCollideTile(x + cStartX, y + cStartY + nextY) || currentMap.isCollideTile(x + cStartX + cWidth, y + cStartY + nextY))
			return true;
		return false;
	}
	
	protected boolean hasBotCollided(double nextY){
		if(currentMap.isCollideTile(x + cStartX, y + cStartY + cHeight + nextY) || currentMap.isCollideTile(x + cStartX + cWidth, y + cStartY + cHeight + nextY))
			return true;
		return false;
	}

	protected boolean hasLeftCollided(double nextX){
		if(currentMap.isCollideTile(x + cStartX + nextX, y + cStartY) || currentMap.isCollideTile(x + cStartX + nextX, y + cStartY + cHeight))
			return true;
		return false;
	}

	protected boolean hasRightCollided(double nextX){
		if(currentMap.isCollideTile(x + cStartX + cWidth + nextX, y + cStartY) || currentMap.isCollideTile(x + cStartX + cWidth + nextX, y + cStartY + cHeight))
			return true;
		return false;
	}
	
	// Interact Methods
	protected boolean interactWithNpc(){
		Rectangle playerRect = new Rectangle((int)x + (int)dx + gCStartX - INTERACT_RANGE, (int)y + (int)dy + gCStartY - INTERACT_RANGE, gCWidth + INTERACT_RANGE, gCHeight + INTERACT_RANGE);
		for(Mob mob : currentMap.getMobManager().getPlacedMobs()){
			if(mob instanceof Npc){
				Rectangle mobRect = new Rectangle((int)mob.x + (int)mob.dx + mob.gCStartX - INTERACT_RANGE, (int)mob.y + (int)mob.dy + mob.gCStartY - INTERACT_RANGE, mob.gCWidth + INTERACT_RANGE, mob.gCHeight + INTERACT_RANGE);
				if(playerRect.intersects(mobRect)){
					if(GUIUtils.canSpeakNpc(mob.id - Variables.mobIdStart))
						currentMap.getGUIManager().openSpeakWindowWithID(mob.id - Variables.mobIdStart);
					return true;
				}
			}
		}
		return false;
	}

	// Getters And Setters
	public int getDirection() {
		return direction;
	}
	
	public boolean isAttacking(){
		return attacking;
	}
}
