package Engine;

import org.newdawn.slick.Image;

import Structures.Item;
import Utils.GUIUtils;
import Utils.GeneralUtils;

public class Variables {
	
	public static int SCREEN_WIDTH;
	public static int SCREEN_HEIGHT;
	
	public static String mapPath = "/Maps/Map";
	public static int currentMap = 1;
	
	public static int objectIdStart = 1000;
	public static int mobIdStart = 2000;
	
	public static Image speakWindow;
	public static int speakWindowX;
	public static int speakWindowY;
	
	public static Image inventoryWindow;
	public static int inventoryWindowX;
	public static int inventoryWindowY;
	public static Image questionWindow;
	public static int questionWindowX;
	public static int questionWindowY;
	
	public static Image shopWindow;
	
	public static Image infoWindow;
	
	public static Image coins;
	
	public static Image leftButton;
	public static Image rightButton;
	public static Image topButton;
	public static Image botButton;
	
	public static Image characterWindow;
	public static int characterWindowX;
	public static int characterWindowY;
	
	public static Image barWindow;
	public static int barWindowX;
	public static int barWindowY;
	
	public static Image wait;
	
	public static Image arrow;
	
	public static Item[] items = new Item[256];
	
	public static void initGUI() {
		speakWindow = GeneralUtils.getImage("/GUI/SpeakWindow.png");
		inventoryWindow = GeneralUtils.getImage("/GUI/InventoryWindow.png");
		shopWindow = GeneralUtils.getImage("/GUI/ShopWindow.png");
		infoWindow = GeneralUtils.getImage("/GUI/InfoWindow.png");
		coins = GeneralUtils.getImage("/GUI/Coins.png");
		leftButton = GeneralUtils.getImage("/GUI/LeftButton.png");
		rightButton = GeneralUtils.getImage("/GUI/RightButton.png");
		topButton = GeneralUtils.getImage("/GUI/TopButton.png");
		botButton = GeneralUtils.getImage("/GUI/BotButton.png");
		questionWindow = GeneralUtils.getImage("/GUI/QuestionWindow.png");
		characterWindow = GeneralUtils.getImage("/GUI/CharacterWindow.png");
		barWindow = GeneralUtils.getImage("/GUI/BarWindow.png");
		wait = GeneralUtils.getImage("/GUI/Wait.png");
		characterWindowX = SCREEN_WIDTH / 2 - characterWindow.getWidth() / 2;
		characterWindowY = SCREEN_HEIGHT / 2 - characterWindow.getHeight() / 2;
		speakWindowX = 100;
		speakWindowY = 100;
		inventoryWindowX = SCREEN_WIDTH - 100 - inventoryWindow.getWidth();
		inventoryWindowY = 100;
		questionWindowX = SCREEN_WIDTH / 2 - questionWindow.getWidth() / 2;
		questionWindowY = SCREEN_HEIGHT / 2 - questionWindow.getHeight() / 2;
		barWindowX = SCREEN_WIDTH / 2 - barWindow.getWidth() / 2;;
		barWindowY = SCREEN_HEIGHT - barWindow.getHeight() - 75;
	}
	
	public static void initItems(){
		GUIUtils.initItems();
	}
	
}
