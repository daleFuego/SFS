package com.view.panels;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.dao.DBData;
import com.defines.DefineUtils;
import com.defines.ProductStage;

public class Distribution extends JPanel {

	private static final long serialVersionUID = 7178891230405416813L;
	public JTable tableProcess;
	public JTextField textFieldUndelivered;
	private JTextField textFieldPlane;
	private JTextField textFieldTruck;
	private JTextField textFieldSelected;
	private JCheckBox chckbxTruck;
	private JCheckBox chckbxPlane;
	private int quantity = 0;
	private JButton btnExport;
	private ArrayList<String> selectedProcesses;

	public Distribution() {
		setBorder(null);
		setLayout(null);
		setSize(530, 408);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		JScrollPane scrollPaneProcess = new JScrollPane();
		scrollPaneProcess.setBounds(10, 11, 508, 178);
		add(scrollPaneProcess);

		tableProcess = new JTable();
		tableProcess.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "PROCESS_NAME", "START_DATE", "END_DATE", "QUANTITY", "STATUS" }));
		scrollPaneProcess.setViewportView(tableProcess);
		tableProcess.setBorder(null);
		tableProcess.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		tableProcess.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				try {
					quantity = 0;
					selectedProcesses = new ArrayList<>();
					int[] selectedRows = tableProcess.getSelectedRows();
					for(int i = 0; i < selectedRows.length; i++){
						int row = selectedRows[i];
						
						String processName = tableProcess.getValueAt(row, 0).toString();
						System.out.println(processName);
						quantity += (int) tableProcess.getValueAt(row, 3);
						selectedProcesses.add(processName);
					}
					textFieldSelected.setText(""+quantity);
					
					if(quantity <= DefineUtils.CAPACITY_TRUCK){
						chckbxTruck.setEnabled(true);
						chckbxPlane.setEnabled(true);
						btnExport.setEnabled(true);
					} else if( quantity > DefineUtils.CAPACITY_TRUCK && quantity <= DefineUtils.CAPACITY_PLANE){
						chckbxTruck.setEnabled(false);
						chckbxPlane.setEnabled(true);
						btnExport.setEnabled(true);
					} else {
						chckbxTruck.setEnabled(false);
						chckbxPlane.setEnabled(false);
						btnExport.setEnabled(false);
					}
				} catch (Exception ex) {
				}

			}
		});

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Distribution and delivery control panel", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel.setBounds(10, 192, 510, 205);
		add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 13, 490, 52);
		panel.add(scrollPane);

		JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setFont(new Font("Arial", Font.PLAIN, 11));
		textArea.setText(DefineUtils.DESCRIPTION);
		scrollPane.setViewportView(textArea);

		JLabel lblTotalAmountOf = new JLabel("Total amount of undelivered goods:");
		lblTotalAmountOf.setBounds(10, 75, 191, 14);
		panel.add(lblTotalAmountOf);

		JLabel lblPlaneCapacity = new JLabel("Plane capacity:");
		lblPlaneCapacity.setBounds(10, 99, 191, 14);
		panel.add(lblPlaneCapacity);

		JLabel lblTruckCapacity = new JLabel("Truck capacity:");
		lblTruckCapacity.setBounds(10, 123, 191, 14);
		panel.add(lblTruckCapacity);

		textFieldUndelivered = new JTextField();
		textFieldUndelivered.setEditable(false);
		textFieldUndelivered.setBounds(187, 72, 86, 20);
		panel.add(textFieldUndelivered);
		textFieldUndelivered.setColumns(10);

		textFieldPlane = new JTextField("" + DefineUtils.CAPACITY_PLANE);
		textFieldPlane.setEditable(false);
		textFieldPlane.setBounds(187, 96, 86, 20);
		panel.add(textFieldPlane);
		textFieldPlane.setColumns(10);

		textFieldTruck = new JTextField("" + DefineUtils.CAPACITY_TRUCK);
		textFieldTruck.setEditable(false);
		textFieldTruck.setBounds(187, 120, 86, 20);
		panel.add(textFieldTruck);
		textFieldTruck.setColumns(10);

		JLabel lblSelectedAmoutTo = new JLabel("Selected amout of goods to deliver:");
		lblSelectedAmoutTo.setBounds(10, 147, 191, 14);
		panel.add(lblSelectedAmoutTo);

		textFieldSelected = new JTextField();
		textFieldSelected.setEditable(false);
		textFieldSelected.setBounds(187, 144, 86, 20);
		panel.add(textFieldSelected);
		textFieldSelected.setColumns(10);

		btnExport = new JButton("Export goods");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(selectedProcesses != null && chckbxPlane.isSelected() || chckbxTruck.isSelected()){
					for(String process : selectedProcesses){
						String query = "UPDATE \"PROCESS\" SET \"STATUS\"='" + ProductStage.EXPORT.toString()
								+ "' WHERE \"PROCESS_NAME\"='" + process + "';";
						System.out.println(query);
						DBData.updateDBTable(query);
						DBData.updateTableProcess(tableProcess, textFieldUndelivered);
					}
				} else {
					JOptionPane.showMessageDialog(null, "No transport selected!", "",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnExport.setBounds(10, 171, 490, 23);
		panel.add(btnExport);

		chckbxPlane = new JCheckBox("Transport by plane");
		chckbxPlane.setBounds(285, 95, 117, 23);
		chckbxPlane.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				chckbxTruck.setSelected(false);
			}
		});
		panel.add(chckbxPlane);

		chckbxTruck = new JCheckBox("Transport by truck");
		chckbxTruck.setBounds(285, 119, 117, 23);
		chckbxTruck.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				chckbxPlane.setSelected(false);
			}
		});
		panel.add(chckbxTruck);
		
		JButton btnRefreshTable = new JButton("RELOAD");
		btnRefreshTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DBData.updateTableProcess(tableProcess, textFieldUndelivered);
			}
		});
		btnRefreshTable.setBounds(411, 71, 89, 93);
		panel.add(btnRefreshTable);
		
		DBData.updateTableProcess(tableProcess, textFieldUndelivered);
		btnExport.setEnabled(false);
	}
}
