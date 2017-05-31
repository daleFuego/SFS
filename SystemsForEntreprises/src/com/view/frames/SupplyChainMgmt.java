package com.view.frames;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.UIManager;

import com.defines.DefineUtils;
import com.view.panels.CoffeePlantation;

public class SupplyChainMgmt extends JFrame {

	private static final long serialVersionUID = 1L;

	public SupplyChainMgmt() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 500);
		setVisible(true);
		setTitle(DefineUtils.APP_TILTE);
		getContentPane().setLayout(null);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			setIconImage(ImageIO.read(getClass().getResourceAsStream("/images/icon.png")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		CoffeePlantation coffeePlantation = new CoffeePlantation();
		getContentPane().add(coffeePlantation);
	}
}
