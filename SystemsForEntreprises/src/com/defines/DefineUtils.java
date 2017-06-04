package com.defines;

import java.text.SimpleDateFormat;

public class DefineUtils {
	public static final String DB_USERNAME = "postgres";
	public static final String DB_PASSWORD = "postgres";

	public static final String DB_NAME = "jdbc:postgresql://127.0.0.1:5432/sfs";
	public static final String DB_TABLE_RESOURCES = "RESOURCES";
	public static final String DB_TABLE_DELIVERIES = "DELIVERIES";
	public static final String DB_TABLE_PROCESS = "PROCESS";

	public static final int LOAD_VALUE = 2000;
	public static int CAPACITY_TRUCK = 2000;
	public static int CAPACITY_PLANE = 6000;
	public static int CAPACITY_STOCK = 40000;
	public static int MAX_COFFEE_DELIVERY = 15000;

	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public static boolean SHOW_WELCOME_SCREEN = false;
	public static boolean REFRESH_FROM_DB = true;

	public static String APP_TILTE = "Starbucks Coffee Supply Chain";
	public static final String DESCRIPTION = "In table above you can check current processes. If process possess status \"READY\" it means that it can be distributed furtger to the stores around the world. In order to do that you need to choose one of two ways to deliver product: by plane or by truck. Each of those transports got its own restrictions and specification. They are presented below.";

}
