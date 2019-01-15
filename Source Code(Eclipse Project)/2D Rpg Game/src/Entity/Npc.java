package Entity;

import org.newdawn.slick.Graphics;

import Graphicals.MobAnimationRenderer;
import Map.Map;
import Structures.MapMob;

public class Npc extends Mob{
	
	private MobAnimationRenderer npcAnimationRenderer;
	private MapMob parent;

	public Npc(Map currentMap, int id, MapMob parent, double x, double y){
		this.currentMap = currentMap;
		this.id = id;
		this.parent = parent;
		this.x = x;
		this.y = y;
		this.npcAnimationRenderer = new MobAnimationRenderer(parent.getSpriteSheetPath(), this);
		this.direction = MobAnimationRenderer.DOWN;
		
		sync();
	}
	
	@Override
	public void update() {
		npcAnimationRenderer.update();
	}

	@Override
	public void render(Graphics g) {
		npcAnimationRenderer.render(x - currentMap.xOffset, y - currentMap.yOffset, g);
		
		/*g.setColor(Color.red);
		g.drawRect((int)x + cStartX - currentMap.xOffset, (int)y + cStartY - currentMap.yOffset, cWidth, cHeight);
		g.setColor(Color.cyan);
		g.drawRect((int)x + gCStartX - currentMap.xOffset, (int)y +  gCStartY - currentMap.yOffset, gCWidth, gCHeight);*/
	}
	
	public MapMob getParent(){
		return parent;
	}
	
	private void sync(){
		this.cStartX = parent.getcStartX();
		this.cStartY = parent.getcStartY();
		this.cWidth = parent.getcWidth();
		this.cHeight = parent.getcHeight();
		this.gCStartX = parent.getgCStartX();
		this.gCStartY = parent.getgCStartY();
		this.gCWidth = parent.getgCWidth();
		this.gCHeight = parent.getgCHeight();
	}

}
