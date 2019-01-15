package Map;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

import Entity.Mob;
import Structures.MapMob;
import Utils.MapUtils;

public class MobManager {
	
	private ArrayList<MapMob> mobList;
	private ArrayList<Mob> placedMobs;
	
	public MobManager(Map currentMap){
		
		mobList = MapUtils.getMapMobs();
		placedMobs = MapUtils.getPlacedMobs(mobList, currentMap);
		
	}
	
	public void update(){
		for(Mob mob : placedMobs){
			mob.update();
		}
	}
	
	public void render(Graphics g){
		for(Mob mob : placedMobs){
			mob.render(g);
		}
	}

	public ArrayList<MapMob> getMobList() {
		return mobList;
	}

	public ArrayList<Mob> getPlacedMobs() {
		return placedMobs;
	}
	
}
