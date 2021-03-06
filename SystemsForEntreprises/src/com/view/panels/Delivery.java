package com.view.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.dao.DBData;

public class Delivery extends JPanel {

	private static final long serialVersionUID = 1L;
	public JTextField textFieldCoffeeResources;
	public JTable tableDeliveries;

	public Delivery() {
		setBorder(null);
		setLayout(null);
		setSize(530, 408);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		JScrollPane scrollPaneDeliveries = new JScrollPane();
		scrollPaneDeliveries.setBounds(12, 219, 508, 178);
		add(scrollPaneDeliveries);

		tableDeliveries = new JTable();
		tableDeliveries.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "DELIVERY_NAME", "ORDER_DATE", "ORDER_ARRIVAL", "QUANTITY", "STATUS" }));
		scrollPaneDeliveries.setViewportView(tableDeliveries);
		tableDeliveries.setBorder(null);

		JPanel panelStatus = new JPanel();
		panelStatus
				.setBorder(new TitledBorder(null, "Status Panel", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelStatus.setBounds(12, 11, 339, 208);
		add(panelStatus);
		panelStatus.setLayout(null);

		JLabel lblCoffeResources = new JLabel("Coffee resources in stock:");
		lblCoffeResources.setBounds(10, 23, 183, 14);
		panelStatus.add(lblCoffeResources);

		textFieldCoffeeResources = new JTextField();
		textFieldCoffeeResources.setEditable(false);
		textFieldCoffeeResources.setBounds(258, 20, 71, 19);
		panelStatus.add(textFieldCoffeeResources);
		textFieldCoffeeResources.setColumns(10);

		JPanel panelButtons = new JPanel();
		panelButtons.setBorder(new TitledBorder(null, "Actions", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelButtons.setBounds(361, 11, 159, 208);
		add(panelButtons);
		panelButtons.setLayout(null);

		JButton btnCheckResources = new JButton("Check resources");
		btnCheckResources.setBounds(10, 33, 139, 25);
		panelButtons.add(btnCheckResources);

		JButton btnOrderCoffee = new JButton("Order coffee");
		btnOrderCoffee.setBounds(10, 91, 139, 25);
		panelButtons.add(btnOrderCoffee);

		JButton btnRefreshTable = new JButton("Refresh table");
		btnRefreshTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DBData.updateTableDeliveries(tableDeliveries);
			}
		});
		btnRefreshTable.setBounds(10, 149, 139, 23);
		panelButtons.add(btnRefreshTable);

		btnOrderCoffee.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Order(tableDeliveries);
			}
		});

		btnCheckResources.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				textFieldCoffeeResources.setText("" + DBData.checkCoffeeResources());
			}
		});
	}
}
