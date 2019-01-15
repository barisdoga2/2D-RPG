package Structures;

import org.newdawn.slick.Image;

import Utils.GeneralUtils;

public class Item {
	
	public static final int HEAD_TYPE = 0;
	public static final int GLOVE_TYPE = 1;
	public static final int CHEST_TYPE = 2;
	public static final int WEAPON_TYPE = 3;
	public static final int PET_TYPE = 4;
	public static final int LEG_TYPE = 5;
	public static final int ORB_TYPE = 6;
	public static final int FEET_TYPE = 7;
	
	public static final int HEALTH_POTION_TYPE = 8;
	public static final int MANA_POTION_TYPE = 9;
	
	public static final int ARROW_TYPE = 10;
	
	public static final int ATTACK_ATTRIB_TYPE = 0;
	public static final int MAGIC_ATTACK_ATTRIB_TYPE = 1;
	
	public static final int STR_ATTRIB_TYPE = 2;
	public static final int DEX_ATTRIB_TYPE = 3;
	public static final int INT_ATTRIB_TYPE = 4;
	public static final int HP_ATTRIB_TYPE = 5;
	
	public static final int HEALTH_ATTRIB_TYPE = 6;
	public static final int MANA_ATTRIB_TYPE = 7;
	
	public static final int DEF_ATTRIB_TYPE = 8;
	
	public static final int CRITICAL_ATTRIB_TYPE = 9;
	public static final int SPEED_ATTRIB_TYPE = 10;
	
	// Connecteds
	public static final int LAST_GEAR_TYPE = 7;
	public static final int ATTRIB_COUNT = 11;
	
	public static final int THRUST_TYPE = 0;
	public static final int SPLASH_TYPE = 1;
	public static final int SHOOT_TYPE = 2;
	
	// For Potions // All Off MiliSecond
	public static final int CAST_TIME = 0;
	public static final int TOTAL_VALUE = 1;
	public static final int COOL_DOWN = 2;
	public static final int DELTA_TIME = 3;
	
	public static final String[] ATTRIB_NAMES = {"Attack"		, "Magic Attack"	, "Str Bonus"		, "Dex Bonus"		, "Int Bonus"		,
												 "Hp Bonus"		, "Max Health"		, "Max Mana"		, "Defance"			, "Ctritical"		, 
												 "Speed"		, ""				, ""				, ""				, ""				};
	// End of Static Content
	
	private int id;
	private int itemType;
	private String itemName;
	private String spritePath;
	private String discription;
	private int buyPrice;
	private int sellPrice;
	private int requiredLevel;
	private int[][] attrib = { {-1,-1}, {-1,-1}, {-1,-1}};
	private boolean countable;
	private int[] specialAttrib = {-1,-1,-1,-1,-1,-1};
	private int attakType;
	private boolean overSize;
	
	private Image ico;
	
	public Item(int id, int itemType, String itemName, String path, int buyPrice, int sellPrice, int requiredLevel, int attrib0, int attrib0Value, int attrib1, int attrib1Value, int attrib2, int attrib2Value, boolean countable, String discription){
		
		this.id = id;
		this.itemType = itemType;
		this.itemName = itemName;
		this.spritePath = "/" + path + "Sprites/" + itemName + ".png";
		this.buyPrice = buyPrice;
		this.sellPrice = sellPrice;
		this.requiredLevel = requiredLevel;
		
		if(itemType == HEALTH_POTION_TYPE || itemType == MANA_POTION_TYPE){
			
			// Cast Time
			this.specialAttrib[CAST_TIME] = attrib0;
			// Total Give Value
			this.specialAttrib[TOTAL_VALUE] = attrib0Value;
			// CoolDown
			this.specialAttrib[COOL_DOWN] = attrib1;
			// MiliSec To Update
			this.specialAttrib[DELTA_TIME] = attrib1Value;
			
			this.specialAttrib[4] = attrib2;
			this.specialAttrib[5] = attrib2Value;
			
		}else{
			
			this.attrib[0][0] = attrib0;
			this.attrib[0][1] = attrib0Value;
			this.attrib[1][0] = attrib1;
			this.attrib[1][1] = attrib1Value;
			this.attrib[2][0] = attrib2;
			this.attrib[2][1] = attrib2Value;
		}
		
		this.discription = discription;
		this.countable = countable;
		
		this.ico = GeneralUtils.getImage("/" + path + "Icos/" + itemName + ".png");
	}
	
	public Item(int id, int itemType, int attackType, boolean overSize, String itemName, String path, int buyPrice, int sellPrice, int requiredLevel, int attrib0, int attrib0Value, int attrib1, int attrib1Value, int attrib2, int attrib2Value, boolean countable, String discription){
		
		this.overSize = overSize;
		this.attakType = attackType;
		this.id = id;
		this.itemType = itemType;
		this.itemName = itemName;
		this.spritePath = "/" + path + "Sprites/" + itemName + ".png";
		this.buyPrice = buyPrice;
		this.sellPrice = sellPrice;
		this.requiredLevel = requiredLevel;
		
		if(itemType == HEALTH_POTION_TYPE || itemType == MANA_POTION_TYPE){
			
			// Cast Time
			this.specialAttrib[0] = attrib0;
			// Total Give Value
			this.specialAttrib[1] = attrib0Value;
			// CoolDown
			this.specialAttrib[2] = attrib1;
			this.specialAttrib[3] = attrib1Value;
			this.specialAttrib[4] = attrib2;
			this.specialAttrib[5] = attrib2Value;
			
		}else{
			
			this.attrib[0][0] = attrib0;
			this.attrib[0][1] = attrib0Value;
			this.attrib[1][0] = attrib1;
			this.attrib[1][1] = attrib1Value;
			this.attrib[2][0] = attrib2;
			this.attrib[2][1] = attrib2Value;
		}
		
		this.discription = discription;
		this.countable = countable;
		
		this.ico = GeneralUtils.getImage("/" + path + "Icos/" + itemName + ".png");
	}
	
	// Getters

	public int getId() {
		return id;
	}

	public int getItemType() {
		return itemType;
	}

	public String getItemName() {
		return itemName;
	}

	public String getSpritePath() {
		return spritePath;
	}

	public int getBuyPrice() {
		return buyPrice;
	}

	public int getSellPrice() {
		return sellPrice;
	}

	public int getRequiredLevel() {
		return requiredLevel;
	}
	
	public int[][] getAttribs(){
		return attrib;
	}
	
	public Image getIco(){
		return ico;
	}
	
	public String getDiscription(){
		return discription;
	}
	
	public boolean isCountable(){
		return countable;
	}
	
	public int[] getSpecialAttrib(){
		return specialAttrib;
	}
	
	public boolean isOverSize(){
		return overSize;
	}
	
	public int getAttackType(){
		return attakType;
	}
}
