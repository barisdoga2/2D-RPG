package GUI;

import java.awt.Rectangle;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import Engine.Variables;

public class QuestionWindow {
	
	private GUIManager guiManager;
	
	private boolean reInit = true;

	public static final int NOTHING_STATE = 0;
	public static final int BUY_STATE = 1;
	public static final int SELL_STATE = 2;
	public static final int WARNING_STATE = 3;
	public static final int COUNT_BUY_STATE = 4;
	public static final int COUNT_SELL_STATE = 5;
	public static final int DIVISION_ITEM_STATE = 6;
	
	protected boolean isVisible = false;

	private int state = NOTHING_STATE;
	private String message;
	
	private String firstText = "";
	private String secondText = "";
	private Rectangle first;
	private Rectangle second;
	
	private String count = "0";
	private boolean typed;
	
	public QuestionWindow(GUIManager guiManager){
		this.guiManager = guiManager;
	}
	
	public void render(Graphics g){
		g.drawImage(Variables.questionWindow, Variables.questionWindowX, Variables.questionWindowY);
		
		g.setFont(guiManager.textFont);
		g.drawString(message, Variables.questionWindowX + Variables.questionWindow.getWidth() / 2 - g.getFont().getWidth(message) / 2, Variables.questionWindowY + Variables.questionWindow.getHeight() / 2 - g.getFont().getHeight(message) / 2 - 20);
		g.setFont(guiManager.buttonFont);
		
		int y = Variables.questionWindowY + Variables.questionWindow.getHeight() - 50;
		
		if(state == WARNING_STATE){
			guiManager.drawButton(Variables.questionWindowX + Variables.questionWindow.getWidth() / 2 - guiManager.getButtonWidth(firstText, g) / 2, y, firstText,g);
			if(reInit)
				first = new Rectangle(Variables.questionWindowX + Variables.questionWindow.getWidth() / 2 - guiManager.getButtonWidth(firstText, g) / 2, y, guiManager.getButtonWidth(firstText, g), 32);
			
		}else if(state == BUY_STATE || state == SELL_STATE || state == COUNT_BUY_STATE || state == COUNT_SELL_STATE || state == DIVISION_ITEM_STATE){
			int x = Variables.questionWindowX + Variables.questionWindow.getWidth() / 2 - guiManager.getButtonWidth(firstText, g) / 2 - 20 / 2 - guiManager.getButtonWidth(secondText, g) / 2;
			guiManager.drawButton(x, y, firstText, g);
			
			if(state == COUNT_BUY_STATE || state == COUNT_SELL_STATE || state == DIVISION_ITEM_STATE){
				g.drawString(count, Variables.questionWindowX + Variables.questionWindow.getWidth() / 2 - g.getFont().getWidth(count) / 2, Variables.questionWindowY + Variables.questionWindow.getHeight() / 2 - g.getFont().getHeight(count) / 2 + 15);
			}
			
			if(reInit)
				first = new Rectangle(x, y, guiManager.getButtonWidth(firstText, g), 32);
			x += guiManager.getButtonWidth(firstText, g) + 20 / 2 + guiManager.getButtonWidth(secondText, g) / 2 - 20;
			guiManager.drawButton(x, y, secondText, g);
			if(reInit)
				second = new Rectangle(x, y, guiManager.getButtonWidth(secondText, g), 32);
		}
		
		reInit = false;
	}
	
	public void setState(String message, int state2) {
		this.state = state2;
		this.message = message;
		this.isVisible = true;
		this.reInit = true;
		
		if(state2 == WARNING_STATE){
			firstText = "OKE";
		}else if(state2 == BUY_STATE || state2 == SELL_STATE || state2 == COUNT_BUY_STATE || state2 == COUNT_SELL_STATE || state2 == DIVISION_ITEM_STATE){
			firstText = "ACCEPT";
			secondText = "CANCEL";
			count = "0";
			typed = false;
		}
	}
	
	public void mouseClicked(int button, int x, int y) {
		if(first != null && first.contains(x ,y)){
			fistButtonAction();
		}
		
		if(second != null && second.contains(x, y)){
			secondButtonAction();
		}
	}
	
	public void fistButtonAction(){
		
		if(state == WARNING_STATE){
			
			exit();
			
		}else if(state == BUY_STATE){
			
			guiManager.getShopWindow().buyItem();
			
		}else if(state == COUNT_BUY_STATE){
			int count2 = 0;
			try{
				count2 = Integer.parseInt(count);
			}catch(Exception e){
				count2 = 0;
			}
			
			if(count2 == 0)
				exit();
			else{
				guiManager.getShopWindow().setBuyInteractingItemCount(count2);
				return;
			}
			
		}else if(state == SELL_STATE){
			
			guiManager.getShopWindow().sellItem();
			
		}else if(state == COUNT_SELL_STATE){
			
			int count2 = 0;
			try{
				count2 = Integer.parseInt(count);
			}catch(Exception e){
				count2 = 0;
			}
			
			if(count2 == 0)
				exit();
			else{
				guiManager.getShopWindow().setSellInteractingItemCount(count2);
				return;
			}
			
		}else if(state == DIVISION_ITEM_STATE){
			
			int count2 = 0;
			try{
				count2 = Integer.parseInt(count);
			}catch(Exception e){
				count2 = 0;
			}
			
			if(count2 == 0)
				exit();
			else{
				guiManager.getInventoryWindow().divideItem(count2);
				exit();
			}
			
		}
		
		exit();
		
	}
	
	public void secondButtonAction(){
		
		if(state == WARNING_STATE){
			
			
			
		}else if(state == BUY_STATE){
			
			
			
		}else if(state == COUNT_BUY_STATE){
			
			
			
		}else if(state == SELL_STATE){
			
			
			
		}else if(state == COUNT_SELL_STATE){
			
			
			
		}
		
		
		exit();
		
	}
	
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		
	}

	public void mousePressed(int button, int x, int y) {
		
	}

	public void mouseReleased(int button, int x, int y) {
		
	}
	
	public void keyPressed(int keyCode){
		

		if(keyCode == Input.KEY_ENTER){
			fistButtonAction();
		}
		
		if(state == COUNT_BUY_STATE || state == COUNT_SELL_STATE || state == DIVISION_ITEM_STATE){
			boolean success = true;
			
			try{
				
				Integer.parseInt(Input.getKeyName(keyCode));
				
			}catch(Exception e){
				success = false;
			}
			
			
			
			if(keyCode == Input.KEY_BACK){

				if(!(count.length() - 1 < 0)){
					count = count.substring(0, count.length() - 1);
				}
				
			}
			
			if(success && count.length() < 2){
				if(!typed){
					count = Input.getKeyName(keyCode);
					typed = true;
				}else
					count += Input.getKeyName(keyCode);
			}
		}
		
	}
	
	public void exit() {
		this.state = NOTHING_STATE;
		this.isVisible = false;
	}

}
