package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.defines.DefineUtils;
import com.defines.ProductStage;

public class DBData {

	public static Integer checkCoffeeResources() {
		Integer retValue = 0;

		try {
			DriverManager.registerDriver(new org.postgresql.Driver());
			Connection connection = DriverManager.getConnection(DefineUtils.DB_NAME, DefineUtils.DB_USERNAME,
					DefineUtils.DB_PASSWORD);
			connection.setAutoCommit(false);
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM \"" + DefineUtils.DB_TABLE_RESOURCES + "\"");
			while (rs.next()) {
				retValue = Integer.parseInt(rs.getString("QUANTITY_COFFEE"));
			}
			rs.close();
			stmt.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return retValue;
	}

	public static boolean sendDeliveryRequest(int deliverySize) {
		Calendar currentDate = Calendar.getInstance();
		long timeInMillis = currentDate.getTimeInMillis();

		String deliveryName = "CoffeDelivery_" + timeInMillis;
		String dateOfDelivery = DefineUtils.dateFormat.format(currentDate.getTime());
		String dateOfArrival = DefineUtils.dateFormat.format(new Date(timeInMillis + 60000));

		String query = "INSERT INTO \"" + DefineUtils.DB_TABLE_DELIVERIES
				+ "\" (\"DELIVERY_NAME\", \"ORDER_DATE\", \"ORDER_ARRIVAL\", \"QUANTITY\", \"DELIVERED\") VALUES ('"
				+ deliveryName + "', '" + dateOfDelivery + "', '" + dateOfArrival + "', " + deliverySize + ", " + "'"
				+ ProductStage.IMPORT.toString() + "')";
		System.out.println(query);

		try {
			DriverManager.registerDriver(new org.postgresql.Driver());
			Connection connection = DriverManager.getConnection(DefineUtils.DB_NAME, DefineUtils.DB_USERNAME,
					DefineUtils.DB_PASSWORD);
			PreparedStatement statement = connection.prepareStatement(query);
			int rowsAffected = connection.prepareStatement(query).executeUpdate();

			statement.close();
			connection.close();

			if (rowsAffected > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean sendProcessConfirmation() {
		Calendar currentDate = Calendar.getInstance();
		long timeInMillis = currentDate.getTimeInMillis();

		String processName = "Process_" + timeInMillis;
		String startDate = DefineUtils.dateFormat.format(currentDate.getTime());
		String endDate = DefineUtils.dateFormat.format(new Date(timeInMillis + 10000));

		String query = "INSERT INTO \"" + DefineUtils.DB_TABLE_PROCESS
				+ "\" (\"PROCESS_NAME\", \"START_DATE\", \"END_DATE\", \"QUANTITY\", \"STATUS\") VALUES ('"
				+ processName + "', '" + startDate + "', '" + endDate + "', " + DefineUtils.LOAD_VALUE + ", " + "'"
				+ ProductStage.READY.toString() + "')";
		System.out.println(query);

		try {
			DriverManager.registerDriver(new org.postgresql.Driver());
			Connection connection = DriverManager.getConnection(DefineUtils.DB_NAME, DefineUtils.DB_USERNAME,
					DefineUtils.DB_PASSWORD);
			PreparedStatement statement = connection.prepareStatement(query);
			int rowsAffected = connection.prepareStatement(query).executeUpdate();

			statement.close();
			connection.close();

			if (rowsAffected > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean updateTableDeliveries(JTable table) {
		try {
			String query = "SELECT * FROM \"" + DefineUtils.DB_TABLE_DELIVERIES + "\";";
			DriverManager.registerDriver(new org.postgresql.Driver());
			Connection connection = DriverManager.getConnection(DefineUtils.DB_NAME, DefineUtils.DB_USERNAME,
					DefineUtils.DB_PASSWORD);
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();

			while (table.getRowCount() > 0) {
				((DefaultTableModel) table.getModel()).removeRow(0);
			}

			int columns = resultSet.getMetaData().getColumnCount();
			int val = 0;
			while (resultSet.next()) {
				Object[] row = new Object[columns + 1];
				for (int i = 0; i < columns; i++) {
					row[i] = resultSet.getObject(i + 1);
				}

				String status = (String) row[4];
				try {
					Date dateOfArrival = DefineUtils.dateFormat.parse((String) row[2]);
					Date currentDate = new Date();

					if (currentDate.compareTo(dateOfArrival) > 0 && status.equals(ProductStage.IMPORT.toString())) {
						val += (int) row[3];
						row[4] = ProductStage.STORAGE;
						query = "UPDATE \"DELIVERIES\" SET \"DELIVERED\"='" + ProductStage.STORAGE
								+ "' WHERE \"DELIVERY_NAME\"='" + (String) row[0] + "';";
						updateDBTable(query);
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}

				((DefaultTableModel) table.getModel()).addRow(row);
			}

			if (val > 0) {
				val += checkCoffeeResources();
				query = "UPDATE \"RESOURCES\" SET \"QUANTITY_COFFEE\"='" + val + "';";
				updateDBTable(query);
			}

			resultSet.close();
			statement.close();
			connection.close();
			table.setRowSelectionInterval(table.getRowCount() - 1, table.getRowCount() - 1);

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean updateTableProcess(JTable table, JTextField textFieldUndelivered) {
		try {
			String query = "SELECT * FROM \"" + DefineUtils.DB_TABLE_PROCESS + "\";";
			DriverManager.registerDriver(new org.postgresql.Driver());
			Connection connection = DriverManager.getConnection(DefineUtils.DB_NAME, DefineUtils.DB_USERNAME,
					DefineUtils.DB_PASSWORD);
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();

			while (table.getRowCount() > 0) {
				((DefaultTableModel) table.getModel()).removeRow(0);
			}

			int val = 0;
			int columns = resultSet.getMetaData().getColumnCount();
			while (resultSet.next()) {
				Object[] row = new Object[columns + 1];

				row[0] = resultSet.getObject(5); // name
				row[1] = resultSet.getObject(1); // start
				row[2] = resultSet.getObject(2); // end
				row[3] = resultSet.getObject(4); // quanity
				row[4] = resultSet.getObject(3); // status

				if (row[4].toString().equals(ProductStage.READY.toString())) {
					val += (int) row[3];
				}
				((DefaultTableModel) table.getModel()).addRow(row);
			}
			textFieldUndelivered.setText("" + val);
			table.scrollRectToVisible(table.getCellRect(table.getRowCount() - 1, 0, true));
			resultSet.close();
			statement.close();
			connection.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean updateDBTable(String query) {
		try {
			DriverManager.registerDriver(new org.postgresql.Driver());
			Connection connection = DriverManager.getConnection(DefineUtils.DB_NAME, DefineUtils.DB_USERNAME,
					DefineUtils.DB_PASSWORD);
			PreparedStatement statement = connection.prepareStatement(query);

			if (statement.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	public static void startRefreshTask(JTable tableDeliveries, JTextField textFieldCoffeeResources,
			JButton btnStartProcess) {
		Timer timer = new Timer();
		timer.schedule(new DBRefresh(tableDeliveries, textFieldCoffeeResources, btnStartProcess), 0, 5000);
	}

}
