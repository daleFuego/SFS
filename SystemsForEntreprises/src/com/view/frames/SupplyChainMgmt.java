package com.view.frames;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.UIManager;

import com.defines.DefineUtils;
import com.view.panels.CoffeePlantation;
import javax.swing.JTabbedPane;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class SupplyChainMgmt extends JFrame {

	private static final long serialVersionUID = 1L;

	public SupplyChainMgmt() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(650, 500);
		setVisible(true);
		setTitle(DefineUtils.APP_TILTE);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			setIconImage(ImageIO.read(getClass().getResourceAsStream("/images/icon.png")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPaneDelivery = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPaneDelivery);
		
		CoffeePlantation coffeePlantation = new CoffeePlantation();
		coffeePlantation.setBorder(new LineBorder(new Color(0, 0, 0)));
		tabbedPaneDelivery.addTab("Coffe Delivery", null, coffeePlantation, null);
	}
}
