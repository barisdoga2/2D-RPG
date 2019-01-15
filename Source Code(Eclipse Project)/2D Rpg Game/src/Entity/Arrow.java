package Entity;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import Graphicals.PlayerAnimationRenderer;
import Map.Map;
import Utils.GeneralUtils;

public class Arrow extends Entity{

	private Image spriteSheet;
	private Image[][] animation;
	private Image arrow;
	private Map currentMap;
	private Player player;
	
	private boolean ended = false;
	
	private double speed = 7;
	private double friction = 0.05;
	private double totalDistance = 0;
	
	private long timer;
	private long lastTime;
	private long delay;
	private int currentIndex = 0;
	
	private boolean onPlayersHand = true;
	private int lastPlayerDirection = 0;
	
	public Arrow(String path, Player player){
		this.player = player;
		this.currentMap = player.getCurrentMap();
		this.x = player.x;
		this.y = player.y;
		this.delay = PlayerAnimationRenderer.ATTACK_SHOOT_DELAY;
		spriteSheet = GeneralUtils.getImage(path);
		animation = new Image[4][PlayerAnimationRenderer.ATTACK_SHOOT_LENGTH + 1];
		for(int y = 0 ; y < PlayerAnimationRenderer.ATTACK_SHOOT_LENGTH ; y++){
			for(int x = 0 ; x < 4 ; x++){
				animation[x][y] = spriteSheet.getSubImage(y * 64, PlayerAnimationRenderer.ATTACK_SHOOT_STARTY * 64 + x * 64, 64, 64);
			}
		}
	}
	
	@Override
	public void update() {
		if(ended)
			return;
		
		timer += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		
		if(timer >= delay && onPlayersHand){
			if(currentIndex == 8){
				onPlayersHand = false;
			}else
				currentIndex++;
			timer = 0;
		}
		
		if(onPlayersHand){
			lastPlayerDirection = player.getDirection();
			this.x += player.dx;
			this.y += player.dy;
		}else{
			if(lastPlayerDirection == 0){
				this.y -= speed;
			}else if(lastPlayerDirection == 1){
				this.x -= speed;
			}else if(lastPlayerDirection == 2){
				this.y += speed;
			}else if(lastPlayerDirection == 3){
				this.x += speed;
			}
			totalDistance += speed;
			speed = speed - friction;
			if(speed <= 0)
				ended = true;
		}
				
	}

	@Override
	public void render(Graphics g) {
		if(!ended)
			g.drawImage(animation[lastPlayerDirection][currentIndex], (int)x - currentMap.xOffset, (int)y - currentMap.yOffset);
	}

}
