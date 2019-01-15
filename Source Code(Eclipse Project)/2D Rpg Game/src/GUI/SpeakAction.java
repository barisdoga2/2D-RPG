package GUI;

import java.util.ArrayList;

import Utils.MapUtils;

public class SpeakAction {
	
	private static final String NULL = "null";
	private static SpeakAction currentAction;
	
	public static ArrayList<SpeakAction> speakActions = new ArrayList<SpeakAction>();
	
	public static void loadSpeakActions(int id){
		
		speakActions = MapUtils.getSpeakAcitons(id);
		
		currentAction = speakActions.get(0);
		
	}
	
	public static SpeakAction getCurrentAction(){
		return currentAction;
	}
	
	public static void setCurrentAction(SpeakAction newAction){
		currentAction = newAction;
	}
	
	public static SpeakAction getNextAction(){
		if(currentAction.getNextActionName().equals(NULL))
			return null;
		
		for(SpeakAction act : speakActions){
			if(act.getActionName().equals(currentAction.getNextActionName())){
				return act;
			}
		}
		
		return null;
	}
	
	public static SpeakAction getNext2Action(){
		if(currentAction.getNext2ActionName().equals(NULL))
			return null;
		
		for(SpeakAction act : speakActions){
			if(act.getActionName().equals(currentAction.getNext2ActionName())){
				return act;
			}
		}
		
		return null;
	}
	
	// End Of Static Content
	
	private String actionName;
	private String actionText;
	private String nextAction;
	private String next2Action;
	private boolean special = false;
	private String[] specialData;
	
	public SpeakAction(String actionName, String actionText, String nextAction, String next2Action){
		this.actionName = actionName;
		this.actionText = actionText;
		this.nextAction = nextAction;
		this.next2Action = next2Action;
		
		if(actionText.contains("-")){
			this.special = true;
			this.specialData = actionText.substring(1, actionText.length() - 1).split("\\.");
			this.actionText = "";
		}

	}
	
	public String getNextActionName(){
		return nextAction;
	}
	
	public String getNext2ActionName(){
		return next2Action;
	}
	
	public String getActionName(){
		return actionName;
	}
	
	public String getActionText(){
		return actionText;
	}
	
	public boolean isSpecial(){
		return special;
	}
	
	public String[] specialData(){
		return specialData;
	}
}
