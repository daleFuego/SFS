package com.view.main;

import com.defines.DefineUtils;
import com.view.frames.SupplyChainMgmt;
import com.view.frames.WelcomeScreen;

public class Main {

	public static void main(String[] args) {
		
		if (DefineUtils.SHOW_WELCOME_SCREEN) {
			new WelcomeScreen();
		} else {
			new SupplyChainMgmt();
		}
		
	}

}
