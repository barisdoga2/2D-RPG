package Graphicals;

import java.awt.Rectangle;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import Entity.Player;
import Structures.Item;
import Utils.GeneralUtils;

public class PlayerAnimationRenderer {
	
	public static final int WIDTH = 64;
	public static final int HEIGHT = 64;
	
	public static final int OVERSIZE_WIDTH = 192;
	public static final int OVERSIZE_HEIGHT = 192;
	public static final int OVERSIZE_STARTY_COORD = 1366;
	
	public static final int ANIMATIONCOUNT = 5;
	
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
	
	public static final int ATTACK_THRUST = 2;
	public static final int ATTACK_THRUST_LENGTH = 8;
	public static final int ATTACK_THRUST_STARTY = 4;
	public static final int ATTACK_THRUST_DELAY = 100;
	
	public static final int ATTACK_SPLASH = 3;
	public static final int ATTACK_SPLASH_LENGTH = 6;
	public static final int ATTACK_SPLASH_STARTY = 12;
	public static final int ATTACK_SPLASH_DELAY = 75;
	
	public static final int ATTACK_SHOOT = 4;
	public static final int ATTACK_SHOOT_LENGTH = 11;
	public static final int ATTACK_SHOOT_STARTY = 16;
	public static final int ATTACK_SHOOT_DELAY = 125;
	
	// End Of Static Content
	
	private Image spriteSheet;
	private Image[][][] animation;
	
	private Player player;
	
	private long timer = 0;
	private long lastTime;
	private long delay;
	private int currentAnimation;
	private int currentIndex;
	
	private Item item;
	
	private Rectangle[][][] collisionRectangles;
	
	public PlayerAnimationRenderer(String path, Player player) {
		createAnimations(path, player);
	}

	public PlayerAnimationRenderer(String path, Player animationPlayer, Item item) {
		this.item = item;
		createAnimations(item.getSpritePath(), animationPlayer);
	}

	public void createAnimations(String path, Player animationPlayer){
		spriteSheet = GeneralUtils.getImage(path);
		animation = new Image[ANIMATIONCOUNT][4][15];
		player = animationPlayer;
		
		for(int i = 0 ; i < IDDLE_LENGTH ; i++){
			for(int x = 0 ; x < 4 ; x++){
				animation[IDDLE][x][i] = spriteSheet.getSubImage(i * WIDTH, IDDLE_STARTY * HEIGHT + x * HEIGHT, WIDTH, HEIGHT);
			}
		}
		
		for(int i = 0 ; i < WALKING_LENGTH ; i++){
			for(int x = 0 ; x < 4 ; x++){
				animation[WALKING][x][i] = spriteSheet.getSubImage(i * WIDTH, WALKING_STARTY * HEIGHT + x * HEIGHT, WIDTH, HEIGHT);
			}
		}
		
		if(item == null || (item != null && item.getItemType() == Item.WEAPON_TYPE && !item.isOverSize())){

			collisionRectangles = new Rectangle[ANIMATIONCOUNT][4][15];
			
			for(int i = 0 ; i < ATTACK_THRUST_LENGTH ; i++){
				for(int x = 0 ; x < 4 ; x++){
					animation[ATTACK_THRUST][x][i] = spriteSheet.getSubImage(i * WIDTH, ATTACK_THRUST_STARTY * HEIGHT + x * HEIGHT, WIDTH, HEIGHT);
					if(item != null)
						collisionRectangles[ATTACK_THRUST][x][i] = getAutoRectangle(spriteSheet.getSubImage(i * WIDTH, ATTACK_THRUST_STARTY * HEIGHT + x * HEIGHT, WIDTH, HEIGHT));
				}
			}
			
			for(int i = 0 ; i < ATTACK_SPLASH_LENGTH ; i++){
				for(int x = 0 ; x < 4 ; x++){
					animation[ATTACK_SPLASH][x][i] = spriteSheet.getSubImage(i * WIDTH, ATTACK_SPLASH_STARTY * HEIGHT + x * HEIGHT, WIDTH, HEIGHT);
					if(item != null)
						collisionRectangles[ATTACK_SPLASH][x][i] = getAutoRectangle(spriteSheet.getSubImage(i * WIDTH, ATTACK_SPLASH_STARTY * HEIGHT + x * HEIGHT, WIDTH, HEIGHT));
				}
			}
			
			for(int i = 0 ; i < ATTACK_SHOOT_LENGTH ; i++){
				for(int x = 0 ; x < 4 ; x++){
					animation[ATTACK_SHOOT][x][i] = spriteSheet.getSubImage(i * WIDTH, ATTACK_SHOOT_STARTY * HEIGHT + x * HEIGHT, WIDTH, HEIGHT);
				}
			}
			
			

		}else if(item != null && item.isOverSize()){
			
			collisionRectangles = new Rectangle[ANIMATIONCOUNT][4][15];
			
			for(int i = 0 ; i < ATTACK_THRUST_LENGTH ; i++){
				for(int x = 0 ; x < 4 ; x++){
					animation[ATTACK_THRUST][x][i] = spriteSheet.getSubImage(i * OVERSIZE_WIDTH, OVERSIZE_STARTY_COORD + x * OVERSIZE_WIDTH, OVERSIZE_WIDTH, OVERSIZE_HEIGHT);
					collisionRectangles[ATTACK_THRUST][x][i] = getAutoRectangle(spriteSheet.getSubImage(i * OVERSIZE_WIDTH, OVERSIZE_STARTY_COORD + x * OVERSIZE_WIDTH, OVERSIZE_WIDTH, OVERSIZE_HEIGHT));
				}
			}
			
			for(int i = 0 ; i < ATTACK_SPLASH_LENGTH ; i++){
				for(int x = 0 ; x < 4 ; x++){
					animation[ATTACK_SPLASH][x][i] = spriteSheet.getSubImage(i * OVERSIZE_WIDTH, OVERSIZE_STARTY_COORD + x * OVERSIZE_HEIGHT, OVERSIZE_WIDTH, OVERSIZE_HEIGHT);
					collisionRectangles[ATTACK_SPLASH][x][i] = getAutoRectangle( spriteSheet.getSubImage(i * OVERSIZE_WIDTH, OVERSIZE_STARTY_COORD + x * OVERSIZE_HEIGHT, OVERSIZE_WIDTH, OVERSIZE_HEIGHT));
				}
			}
			
			for(int i = 0 ; i < ATTACK_SHOOT_LENGTH ; i++){
				for(int x = 0 ; x < 4 ; x++){
					animation[ATTACK_SHOOT][x][i] = spriteSheet.getSubImage(i * OVERSIZE_WIDTH, OVERSIZE_STARTY_COORD + x * OVERSIZE_HEIGHT, OVERSIZE_WIDTH, OVERSIZE_HEIGHT);
				}
			}
			
		}
		
	}
	
	public void update(){
		timer += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		
		if(timer >= delay){
			if(animation[currentAnimation][0][currentIndex + 1] == null){
				if(currentAnimation == ATTACK_SHOOT || currentAnimation == ATTACK_SPLASH || currentAnimation == ATTACK_THRUST)
					player.setAttacking(false);
				else
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
		if(item == null || (item != null && !item.isOverSize()))
			g.drawImage(animation[currentAnimation][player.getDirection()][currentIndex], (int)x, (int)y);
		else if(item != null && item.isOverSize())
			g.drawImage(animation[currentAnimation][player.getDirection()][currentIndex], (int)x - WIDTH, (int)y - HEIGHT);
	}
	
	public int getCurrentAnimation(){
		return currentAnimation;
	}

	public void resetAnimation() {
		setForceAnimation(currentAnimation, (int)delay);
	}
	
	public Rectangle getAttackRect(int ATTACK_TYPE){
		return collisionRectangles[ATTACK_TYPE][player.getDirection()][currentIndex];
	}
	
	private Rectangle getAutoRectangle(Image image){
		
		int startX = 99999;
		int startY = 99999;
		int endX = 0;
		int endY = 0;
		
		for(int y = 0 ; y < image.getHeight() ; y++){
			for(int x = 0 ; x < image.getWidth() ; x++){
				int a = image.getColor(x, y).getAlpha();
				if(a != 0){
					if(x < startX)
						startX = x;
					if(y < startY)
						startY = y;
					if(x > endX)
						endX = x;
					if(y > endY)
						endY = y;
				}
			}
		}
		

		if(startX == 999 || startY == 9999 || endX == 0 || endY == 0)
			return new Rectangle(0, 0, 0, 0);
		
		if(item != null && item.isOverSize()){
			
			return new Rectangle(startX - 64, startY - 64, endX - startX + 1, endY - startY + 1);
			
		}else{
			return new Rectangle(startX, startY, endX - startX + 1, endY - startY + 1);
		}
	}
}
