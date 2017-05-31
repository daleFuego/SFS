package com.view.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.dao.DBData;
import com.view.frames.CoffeeOrder;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class CoffeePlantation extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textFieldCoffeeResources;

	public CoffeePlantation() {
		setBorder(new TitledBorder(null, "Coffe Plantation Panel", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(null);
		setSize(500, 200);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		JButton btnOrderCoffee = new JButton("Order coffee");
		btnOrderCoffee.setBounds(12, 92, 133, 25);

		add(btnOrderCoffee);
		
		JButton btnCheckResources = new JButton("Check resources");

		btnCheckResources.setBounds(22, 139, 117, 25);
		add(btnCheckResources);
		
		JLabel lblCoffeResources = new JLabel("Coffe resources in stock:");
		lblCoffeResources.setBounds(205, 144, 186, 20);
		add(lblCoffeResources);
		
		textFieldCoffeeResources = new JTextField();
		textFieldCoffeeResources.setBounds(386, 142, 114, 19);
		textFieldCoffeeResources.setColumns(10);
		add(textFieldCoffeeResources);
		
		btnCheckResources.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				textFieldCoffeeResources.setText("" + DBData.checkCoffeeResources());
			}
		});
		
		btnOrderCoffee.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new CoffeeOrder();				
			}
		});
	}
}
