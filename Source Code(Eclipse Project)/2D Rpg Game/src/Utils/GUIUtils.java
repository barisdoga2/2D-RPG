package Utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import Engine.Variables;
import Structures.Item;

public class GUIUtils {

	public static boolean canSpeakNpc(int id) {
		try{
			InputStream is = MapUtils.class.getResourceAsStream(Variables.mapPath + Variables.currentMap + "/Speaks/" + id + ".txt");

			is.close();
		}catch(Exception e){
			return false;
		}
		
		return true;
	}

	public static Item[] getShopItems(String shopTarget) {
		Item[] shopItems = null;
		
		try{
			
			InputStream is = GUIUtils.class.getResourceAsStream( Variables.mapPath + Variables.currentMap + "/Speaks/" + shopTarget + ".txt");
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			
			String[] tokens = br.readLine().split("\\t");
			
			shopItems = new Item[28];
			
			for(int i = 0 ; i < tokens.length ; i++){
				shopItems[i] = Variables.items[Integer.parseInt(tokens[i])];
			}
			
			br.close();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		return shopItems;
	}
	
	public static void initItems() {
		
		try{
			
			InputStream is = GUIUtils.class.getResourceAsStream("/Items/Items.txt");
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			
			int count = Integer.parseInt(br.readLine());
			
			for(int i = 0 ; i < count ; i++){
				String[] tokens = br.readLine().split("\\t");
				String newText = "";
				for(int k = 0 ; k < tokens.length ; k++){
					if(!tokens[k].equals("")){
						newText += tokens[k];
						
						if(k != tokens.length - 1)
							newText += "\t";
					}
					
					
				}
				
				tokens = newText.split("\\t");
				
				if(Integer.parseInt(tokens[1]) == Item.WEAPON_TYPE){
					Variables.items[i] = new Item( Integer.parseInt(tokens[0]) , Integer.parseInt(tokens[1]) , Integer.parseInt(tokens[2]), Boolean.parseBoolean(tokens[3]), tokens[4] , tokens[5] , Integer.parseInt(tokens[6]) , Integer.parseInt(tokens[7]) , Integer.parseInt(tokens[8]) , Integer.parseInt(tokens[9]) , Integer.parseInt(tokens[10]) , Integer.parseInt(tokens[11]) , Integer.parseInt(tokens[12]) , Integer.parseInt(tokens[13]) , Integer.parseInt(tokens[14]), Boolean.parseBoolean(tokens[15]), tokens[16]);
				}else{
					Variables.items[i] = new Item( Integer.parseInt(tokens[0]) , Integer.parseInt(tokens[1]) , tokens[2] , tokens[3] , Integer.parseInt(tokens[4]) , Integer.parseInt(tokens[5]) , Integer.parseInt(tokens[6]) , Integer.parseInt(tokens[7]) , Integer.parseInt(tokens[8]) , Integer.parseInt(tokens[9]) , Integer.parseInt(tokens[10]) , Integer.parseInt(tokens[11]) , Integer.parseInt(tokens[12]), Boolean.parseBoolean(tokens[13]), tokens[14]);
				}
			}
			
			br.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
