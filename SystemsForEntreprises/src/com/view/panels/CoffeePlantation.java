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
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class CoffeePlantation extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textFieldCoffeeResources;
	private JTable tableDeliveries;

	public CoffeePlantation() {
		setBorder(null);
		setLayout(null);
		setSize(625, 475);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		JButton btnOrderCoffee = new JButton("Order coffee");
		btnOrderCoffee.setBounds(12, 158, 133, 25);

		add(btnOrderCoffee);
		
		JButton btnCheckResources = new JButton("Check resources");

		btnCheckResources.setBounds(12, 195, 133, 25);
		add(btnCheckResources);
		
		JLabel lblCoffeResources = new JLabel("Coffe resources in stock:");
		lblCoffeResources.setBounds(12, 69, 186, 20);
		add(lblCoffeResources);
		
		textFieldCoffeeResources = new JTextField();
		textFieldCoffeeResources.setBounds(195, 13, 114, 19);
		textFieldCoffeeResources.setColumns(10);
		add(textFieldCoffeeResources);
		
		JScrollPane scrollPaneDeliveries = new JScrollPane();
		scrollPaneDeliveries.setBounds(12, 252, 601, 152);
		add(scrollPaneDeliveries);
		
		tableDeliveries = new JTable();
		scrollPaneDeliveries.setViewportView(tableDeliveries);
		tableDeliveries.setBorder(new TitledBorder(null, "Deliveries", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		btnCheckResources.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				textFieldCoffeeResources.setText("" + DBData.checkCoffeeResources());
			}
		});
		
		btnOrderCoffee.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new CoffeeOrder(tableDeliveries);				
			}
		});
	}
}
