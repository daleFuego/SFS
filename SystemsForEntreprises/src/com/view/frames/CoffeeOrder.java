package com.view.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.dao.DBData;
import com.defines.DefineUtils;

public class CoffeeOrder extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTextField textFieldCurrentCoffeRes;
	private JTextField textFieldMaximumStorageSpace;
	private JTextField textFieldAvailableStorageSpace;
	private JTextField textFieldAvailableCoffeeRes;
	private JTextField textFieldDeliverySize;

	private JSlider sliderSetDeliverySize;

	public CoffeeOrder() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Order Coffee");
		setSize(500, 357);
		setVisible(true);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			setIconImage(ImageIO.read(getClass().getResourceAsStream("/images/icon.png")));
			getContentPane().setLayout(null);

			JLabel lblCurrentCoffeeResources = new JLabel("Current coffee resources");
			lblCurrentCoffeeResources.setBounds(7, 38, 200, 15);
			getContentPane().add(lblCurrentCoffeeResources);

			textFieldCurrentCoffeRes = new JTextField();
			textFieldCurrentCoffeRes.setEditable(false);
			textFieldCurrentCoffeRes.setBounds(214, 36, 56, 19);
			getContentPane().add(textFieldCurrentCoffeRes);
			textFieldCurrentCoffeRes.setColumns(10);

			JLabel lblMaximumStorageSpace = new JLabel("Maximum storage space:");
			lblMaximumStorageSpace.setBounds(7, 91, 200, 15);
			getContentPane().add(lblMaximumStorageSpace);

			textFieldMaximumStorageSpace = new JTextField();
			textFieldMaximumStorageSpace.setEditable(false);
			textFieldMaximumStorageSpace.setBounds(214, 89, 56, 19);
			getContentPane().add(textFieldMaximumStorageSpace);
			textFieldMaximumStorageSpace.setColumns(10);

			JLabel lblAvailableStorageSpace = new JLabel("Available storage space:");
			lblAvailableStorageSpace.setBounds(7, 144, 200, 15);
			getContentPane().add(lblAvailableStorageSpace);

			textFieldAvailableStorageSpace = new JTextField();
			textFieldAvailableStorageSpace.setEditable(false);
			textFieldAvailableStorageSpace.setBounds(214, 142, 56, 19);
			getContentPane().add(textFieldAvailableStorageSpace);
			textFieldAvailableStorageSpace.setColumns(10);

			JLabel lblAvailableCoffeeResources = new JLabel("Available coffee resources:");
			lblAvailableCoffeeResources.setBounds(7, 197, 200, 15);
			getContentPane().add(lblAvailableCoffeeResources);

			textFieldAvailableCoffeeRes = new JTextField();
			textFieldAvailableCoffeeRes.setEditable(false);
			textFieldAvailableCoffeeRes.setBounds(214, 195, 56, 19);
			getContentPane().add(textFieldAvailableCoffeeRes);
			textFieldAvailableCoffeeRes.setColumns(10);

			JLabel lblSetDeliverySize = new JLabel("Set delivery size");
			lblSetDeliverySize.setBounds(7, 250, 200, 15);
			getContentPane().add(lblSetDeliverySize);

			sliderSetDeliverySize = new JSlider();
			sliderSetDeliverySize.setBounds(7, 277, 200, 16);
			getContentPane().add(sliderSetDeliverySize);

			textFieldDeliverySize = new JTextField();
			textFieldDeliverySize.setEditable(false);
			textFieldDeliverySize.setBounds(214, 276, 56, 19);
			getContentPane().add(textFieldDeliverySize);
			textFieldDeliverySize.setColumns(10);

			JButton btnSendDeliveryRequest = new JButton("Send delivery request");
			btnSendDeliveryRequest.setBounds(277, 273, 207, 25);
			getContentPane().add(btnSendDeliveryRequest);

			sliderSetDeliverySize.addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent e) {
					textFieldDeliverySize.setText("" + sliderSetDeliverySize.getValue());
				}
			});

			btnSendDeliveryRequest.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (DBData.sendDeliveryRequest(sliderSetDeliverySize.getValue())) {
						JOptionPane.showMessageDialog(null, "Delivery request sent successfully", "",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "Delivery request could not be send", "",
								JOptionPane.ERROR_MESSAGE);
					}
					
					dispose();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		fillFields();
	}

	public void fillFields() {

		Integer currentCoffeeResources = DBData.checkCoffeeResources();
		Integer stockCapacity = DefineUtils.CAPACITY_STOCK;
		Integer availableStorageSpace = stockCapacity - currentCoffeeResources;
		Integer availableCoffeeResources = new Random().nextInt(DefineUtils.MAX_COFFEE_DELIVERY);
		Integer maximumDeliverySize = 0;

		if (availableStorageSpace > availableCoffeeResources) {
			maximumDeliverySize = availableCoffeeResources;
		} else {
			maximumDeliverySize = availableStorageSpace;
		}

		textFieldCurrentCoffeRes.setText("" + currentCoffeeResources);
		textFieldMaximumStorageSpace.setText("" + stockCapacity);
		textFieldAvailableStorageSpace.setText("" + availableStorageSpace);
		textFieldAvailableCoffeeRes.setText("" + availableCoffeeResources);

		sliderSetDeliverySize.setMaximum(maximumDeliverySize);
	}
}
