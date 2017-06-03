package com.view.frames;

import java.awt.Color;
import java.awt.Component;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import com.defines.DefineUtils;
import com.view.panels.CoffeeDelivery;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
		getContentPane().setLayout(null);
		
		JTabbedPane tabbedPaneDelivery = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPaneDelivery.setBorder(new LineBorder(new Color(0, 0, 0)));
		tabbedPaneDelivery.setBounds(5, 5, 624, 408);
		getContentPane().add(tabbedPaneDelivery);
		
		CoffeeDelivery coffeePlantation = new CoffeeDelivery();
		coffeePlantation.setBorder(new LineBorder(new Color(0, 0, 0)));
		tabbedPaneDelivery.addTab("Coffe Delivery", null, coffeePlantation, null);
		
		JPanel panelCtrls = new JPanel();
		panelCtrls.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelCtrls.setBounds(5, 418, 624, 36);
		getContentPane().add(panelCtrls);
		panelCtrls.setVisible(true);
		panelCtrls.setLayout(null);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setBounds(272, 6, 89, 23);
		panelCtrls.add(btnExit);
		panelCtrls.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{btnExit}));
	}
}
