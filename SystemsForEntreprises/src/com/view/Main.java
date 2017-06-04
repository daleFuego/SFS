package com.view;

import com.defines.DefineUtils;

public class Main {

	public static void main(String[] args) {
		
		if (DefineUtils.SHOW_WELCOME_SCREEN) {
			new WelcomeScreen();
		} else {
			new SupplyChainMgmt();
		}
		
	}

}
