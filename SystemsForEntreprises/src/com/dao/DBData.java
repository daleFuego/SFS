package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.defines.DefineUtils;

public class DBData {

	public static Integer checkCoffeeResources() {
		Integer retValue = 0;

		try {
			DriverManager.registerDriver(new org.postgresql.Driver());
			Connection connection = DriverManager.getConnection(DefineUtils.DB_NAME, DefineUtils.DB_USERNAME,
					DefineUtils.DB_PASSWORD);
			connection.setAutoCommit(false);
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM \"" + DefineUtils.DB_TABLE_COFFEE_PLANTATION + "\"");
			while (rs.next()) {
				retValue = Integer.parseInt(rs.getString(DefineUtils.DB_COL_COFFEE_RESOURCES));
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
				+ "\" (\"DELIVERY_NAME\", \"ORDER_DATE\", \"ORDER_ARRIVAL\", \"QUANTITY\") VALUES ('" + deliveryName
				+ "', '" + dateOfDelivery + "', '" + dateOfArrival + "', " + deliverySize + ")";

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

	public static boolean updateTable(JTable table, String query) {
		try {
			DriverManager.registerDriver(new org.postgresql.Driver());

			Connection connection = DriverManager.getConnection(DefineUtils.DB_NAME, DefineUtils.DB_USERNAME,
					DefineUtils.DB_PASSWORD);
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();

			while (table.getRowCount() > 0) {
				((DefaultTableModel) table.getModel()).removeRow(0);
			}

			int columns = resultSet.getMetaData().getColumnCount();
			while (resultSet.next()) {
				Object[] row = new Object[columns];
				for (int i = 1; i <= columns; i++) {
					row[i - 1] = resultSet.getObject(i);
				}
				((DefaultTableModel) table.getModel()).insertRow(resultSet.getRow() - 1, row);
			}

			resultSet.close();
			statement.close();
			connection.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
