package com.dao;

import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.defines.DefineUtils;

public class DBRefresh extends TimerTask {
	private JTable tableDeliveries;
	private JTextField textFieldCoffeeResources;
	private JButton btnStartProcess;
	public DBRefresh(JTable tableDeliveries, JTextField textFieldCoffeeResources, JButton btnStartProcess) {
		this.tableDeliveries = tableDeliveries;
		this.textFieldCoffeeResources = textFieldCoffeeResources;
		this.btnStartProcess = btnStartProcess;
	}

	public void run() {
		int res = DBData.checkCoffeeResources();
		textFieldCoffeeResources.setText("" + res);
		
		if(res >= DefineUtils.LOAD_VALUE){
			btnStartProcess.setEnabled(true);
			btnStartProcess.setText("Start process");
		} else {
			btnStartProcess.setEnabled(false);
			btnStartProcess.setText("Not enough coffee beans to start production process");
		}
		DBData.updateTableDeliveries(tableDeliveries);
	}
}
