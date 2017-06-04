package com.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import com.dao.DBData;
import com.defines.DefineUtils;
import com.view.panels.Delivery;
import com.view.panels.Distribution;
import com.view.panels.Process;

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
		
		Delivery coffeeDelivery = new Delivery();
		coffeeDelivery.setBorder(new LineBorder(new Color(0, 0, 0)));
		tabbedPaneDelivery.addTab("Coffee Delivery", null, coffeeDelivery, null);
		
		Process process = new Process();
		process.setBorder(new LineBorder(new Color(0, 0, 0)));
		tabbedPaneDelivery.addTab("Process", null, process, null);
		
		Distribution distribution = new Distribution();
		distribution.setBorder(new LineBorder(new Color(0, 0, 0)));
		tabbedPaneDelivery.addTab("Distribution", null, distribution, null);
		
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
		
		if(DefineUtils.REFRESH_FROM_DB){
			DBData.startRefreshTask(coffeeDelivery.tableDeliveries, coffeeDelivery.textFieldCoffeeResources, process.btnStartProcess);
		}
	}
}
