package com.defines;

import java.text.SimpleDateFormat;

public class DefineUtils {
	public static final String DB_USERNAME = "postgres";
	public static final String DB_PASSWORD = "postgres";

	public static final String DB_NAME = "jdbc:postgresql://127.0.0.1:5432/sfs";
	public static final String DB_TABLE_RESOURCES = "RESOURCES";
	public static final String DB_TABLE_DELIVERIES = "DELIVERIES";
	
	public static int CAPACITY_TRUCK = 24000;
	public static int CAPACITY_PLANE = 60000;
	public static int CAPACITY_STOCK = 40000;
	public static int MAX_COFFEE_DELIVERY = 15000;
	
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	public static boolean SHOW_WELCOME_SCREEN = false;
	
	public static String APP_TILTE = "Starbucks Coffee Supply Chain";
}
