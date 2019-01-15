package Graphicals;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import Entity.Mob;
import Entity.Npc;
import Utils.GeneralUtils;

public class MobAnimationRenderer {
	
	public static final int ANIMATIONCOUNT = 2;
	
	public static final int TOP = 0;
	public static final int LEFT = 1;
	public static final int DOWN = 2;
	public static final int RIGHT = 3;
	
	public static final int IDDLE = 0;
	public static final int IDDLE_LENGTH = 1;
	public static final int IDDLE_STARTY = 0;
	public static final int IDDLE_DELAY = 350;
	public static final int WALKING = 1;
	public static final int WALKING_LENGTH = 9;
	public static final int WALKING_STARTY = 8;
	public static final int WALKING_DELAY = 150;
	
	// End Of Static Content
	
	private Image spriteSheet;
	private Image[][][] animation;
	
	private Mob mob;
	
	private int width;
	private int height;
	
	private long timer = 0;
	private long lastTime;
	private long delay;
	private int currentAnimation;
	private int currentIndex;
	
	public MobAnimationRenderer(String path, Mob mobAnimation){
		if(mobAnimation instanceof Npc){
			width = ((Npc) mobAnimation).getParent().getWidth();
			height = ((Npc) mobAnimation).getParent().getHeight();
		}
		
		createAnimations(path, mobAnimation);
		
		setForceAnimation(IDDLE, IDDLE_DELAY);
	}
	
	public void createAnimations(String path, Mob mobAnimation){
		spriteSheet = GeneralUtils.getImage(path);
		animation = new Image[ANIMATIONCOUNT][4][15];
		mob = mobAnimation;
		
		for(int i = 0 ; i < IDDLE_LENGTH ; i++){
			for(int x = 0 ; x < 4 ; x++){
				animation[IDDLE][x][i] = spriteSheet.getSubImage(i * width, IDDLE_STARTY * height + x * height, width, height);
			}
		}
		
		for(int i = 0 ; i < WALKING_LENGTH ; i++){
			for(int x = 0 ; x < 4 ; x++){
				animation[WALKING][x][i] = spriteSheet.getSubImage(i * width, WALKING_STARTY * height + x * height, width, height);
			}
		}
		
	}
	
	public void update(){
		timer += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		
		if(timer >= delay){
			if(animation[currentAnimation][0][currentIndex + 1] == null){
				currentIndex = 0;
			}else
				currentIndex++;
			timer = 0;
		}
	}
	
	public void setAnimation(int animationType, int animationDelay){
		if(currentAnimation == animationType)
			return;
		currentAnimation = animationType;
		delay = animationDelay;
		timer = 0;
		currentIndex = 0;
	}
	
	public void setForceAnimation(int animationType, int animationDelay){
		currentAnimation = animationType;
		delay = animationDelay;
		timer = 0;
		currentIndex = 0;
	}
	
	public void render(double x, double y ,Graphics g){
		g.drawImage(animation[currentAnimation][mob.getDirection()][currentIndex], (int)x, (int)y);
		
	}

	
}
