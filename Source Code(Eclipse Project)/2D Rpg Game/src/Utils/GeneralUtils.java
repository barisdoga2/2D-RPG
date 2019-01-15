package Utils;

import org.newdawn.slick.Image;

public class GeneralUtils {
	
	public static Image getImage(String path){
		
		Image image = null;
		
		try{
			
			path = path.substring(1,path.length());
			image = new Image(path);
			
		}catch(Exception e){
			return null;
		}			

		return image;
		
	}
	
}
