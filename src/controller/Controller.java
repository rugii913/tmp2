package controller;

import dto.Member;
import utilTmp.UtilTmp;

public abstract class Controller {
	public String now = UtilTmp.getNow();
	
	public static Member loginedMember = null;
	
	public boolean isLogined() {
		return loginedMember != null;
	}
	
	
	public abstract void doAction(String actionName, String cmd);
	public abstract void makeTestData();

}
