package com.example.application;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TODO add input filters for cntrl chars and ' (prepared statments)?
//TODO test create DB methods, adding valid/invalid data, joining to get names
//TODO determine good ID bounds

public class PizzaDBManager{
	final static private String DB_URL = "jdbc:derby:PizzaDB;create=true";
	final static private int ID_LOWER_BOUND = 1;
	final static private int ID_UPPER_BOUND = 100000000;
	
	public static void createDB(){
		try{
			Connection conn = createConn();
			System.out.println("Dropping...");
			dropTables(conn);
			System.out.println("Creating Customer");
			createCustomerTable(conn);
			System.out.println("Creating AvailableOptions");
			createAvailableOptionTable(conn);
			System.out.println("Creating Pizza");
			createPizzaTable(conn);
			System.out.println("Creating UsedOption");
			createUsedOptionTable(conn);
			System.out.println("Done");
			closeConn(conn);
		}
		catch(SQLException ex){
			System.out.println("ERROR: "+ex.getMessage());
		}
	}
	
	private static void dropTables(Connection conn) throws SQLException{
		Statement stmt = conn.createStatement();
		try{
			stmt.executeUpdate("DROP TABLE UsedOption");
		}
		catch(SQLException ex){
			if(!ex.getMessage().contains(" because it does not exist.")){
				System.out.println("ERROR: "+ex.getMessage());
			}
		}
		try{
			stmt.executeUpdate("DROP TABLE AvailableOption");
		}
		catch(SQLException ex){
			if(!ex.getMessage().contains(" because it does not exist.")){
				System.out.println("ERROR: "+ex.getMessage());
			}
		}
		try{
			stmt.executeUpdate("DROP TABLE Pizza");
		}
		catch(SQLException ex){
			if(!ex.getMessage().contains(" because it does not exist.")){
				System.out.println("ERROR: "+ex.getMessage());
			}
		}
		try{
			stmt.executeUpdate("DROP TABLE Customer");
		}
		catch(SQLException ex){
			if(!ex.getMessage().contains(" because it does not exist.")){
				System.out.println("ERROR: "+ex.getMessage());
			}
		}
		stmt.close();
	}
	
	private static void createCustomerTable(Connection conn) throws SQLException{
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("CREATE TABLE Customer (CustomerID INT NOT NULL PRIMARY KEY, "+
				"Name VARCHAR(40), Address VARCHAR(80), PhoneNumber VARCHAR(20))");
		
		stmt.executeUpdate("INSERT INTO Customer VALUES (2, 'Andrew', '101 Street A', '410-000-0000')");
		stmt.executeUpdate("INSERT INTO Customer VALUES (5, 'Steven', '222 Blvd C', '409-290-3110')");
		stmt.executeUpdate("INSERT INTO Customer VALUES (8, 'Grace', '999 Lane F', '551-831-3997')");
		stmt.executeUpdate("INSERT INTO Customer VALUES (11, 'Rachel', '108 Street A', '932-282-3769')");
		stmt.executeUpdate("INSERT INTO Customer VALUES (14, 'Andrew', '1010 Road G', '216-237-2634')");
		
		stmt.close();
	}
	
	private static void createAvailableOptionTable(Connection conn) throws SQLException{
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("CREATE TABLE AvailableOption (OptionID INT NOT NULL PRIMARY KEY, "+
				"Name VARCHAR(50), Type CHAR(1), Price DOUBLE)");
		
		stmt.executeUpdate("INSERT INTO AvailableOption VALUES (1, 'Normal Crust', 'C', 4.99)");
		stmt.executeUpdate("INSERT INTO AvailableOption VALUES (4, 'Thin Crust', 'C', 5.99)");
		stmt.executeUpdate("INSERT INTO AvailableOption VALUES (7, 'Thick Crust', 'C', 6.29)");
		stmt.executeUpdate("INSERT INTO AvailableOption VALUES (10, 'Cheese Stuffed Crust', 'C', 8.99)");
		stmt.executeUpdate("INSERT INTO AvailableOption VALUES (13, 'Pepperoni Stuffed Crust', 'C', 9.59)");
		
		stmt.executeUpdate("INSERT INTO AvailableOption VALUES (16, 'Alfredo Sauce', 'S', 2.99)");
		stmt.executeUpdate("INSERT INTO AvailableOption VALUES (19, 'Tomato Sauce', 'S', 2.99)");
		stmt.executeUpdate("INSERT INTO AvailableOption VALUES (22, 'No Sauce', 'S', 2.99)");
		
		stmt.executeUpdate("INSERT INTO AvailableOption VALUES (25, 'Pepperoni', 'T', 1.59)");
		stmt.executeUpdate("INSERT INTO AvailableOption VALUES (28, 'Bacon', 'T', 2.29)");
		stmt.executeUpdate("INSERT INTO AvailableOption VALUES (31, 'Extra Cheese', 'T', 1.99)");
		stmt.executeUpdate("INSERT INTO AvailableOption VALUES (34, 'Mushrooms', 'T', 1.49)");
		stmt.executeUpdate("INSERT INTO AvailableOption VALUES (37, 'Peppers', 'T', 1.89)");
		stmt.executeUpdate("INSERT INTO AvailableOption VALUES (40, 'Pineapples', 'T', 2.79)");
		stmt.executeUpdate("INSERT INTO AvailableOption VALUES (43, 'Chicken', 'T', 3.09)");
		
		stmt.close();
	}
	
	private static void createPizzaTable(Connection conn) throws SQLException{
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("CREATE TABLE Pizza (PizzaID INT NOT NULL PRIMARY KEY, "+
				"CustomerID INT REFERENCES Customer(CustomerID), Size CHAR(1), Price DOUBLE)");
		
		stmt.executeUpdate("INSERT INTO Pizza VALUES (3, 5, 'S', 10.99)");
		stmt.executeUpdate("INSERT INTO Pizza VALUES (6, 5, 'M', 15.59)");
		stmt.executeUpdate("INSERT INTO Pizza VALUES (9, 5, 'L', 20.99)");
		stmt.executeUpdate("INSERT INTO Pizza VALUES (12, 2, 'L', 24.29)");
		stmt.executeUpdate("INSERT INTO Pizza VALUES (15, 14, 'M', 12.99)");
		
		stmt.close();
	}
	
	private static void createUsedOptionTable(Connection conn) throws SQLException{
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("CREATE TABLE UsedOption (OptionID INT REFERENCES AvailableOption(OptionID), "+
				"PizzaID INT REFERENCES Pizza(PizzaID))");
		
		stmt.executeUpdate("INSERT INTO UsedOption VALUES (1, 3)");
		stmt.executeUpdate("INSERT INTO UsedOption VALUES (19, 3)");
		stmt.executeUpdate("INSERT INTO UsedOption VALUES (28, 3)");
		
		stmt.executeUpdate("INSERT INTO UsedOption VALUES (4, 6)");
		stmt.executeUpdate("INSERT INTO UsedOption VALUES (19, 6)");
		stmt.executeUpdate("INSERT INTO UsedOption VALUES (28, 6)");
		stmt.executeUpdate("INSERT INTO UsedOption VALUES (43, 6)");
		
		stmt.executeUpdate("INSERT INTO UsedOption VALUES (10, 9)");
		stmt.executeUpdate("INSERT INTO UsedOption VALUES (22, 9)");
		stmt.executeUpdate("INSERT INTO UsedOption VALUES (34, 9)");
		stmt.executeUpdate("INSERT INTO UsedOption VALUES (31, 9)");
		
		stmt.executeUpdate("INSERT INTO UsedOption VALUES (7, 12)");
		stmt.executeUpdate("INSERT INTO UsedOption VALUES (16, 12)");
		stmt.executeUpdate("INSERT INTO UsedOption VALUES (31, 12)");
		stmt.executeUpdate("INSERT INTO UsedOption VALUES (34, 12)");
		stmt.executeUpdate("INSERT INTO UsedOption VALUES (40, 12)");
		
		stmt.executeUpdate("INSERT INTO UsedOption VALUES (13, 15)");
		stmt.executeUpdate("INSERT INTO UsedOption VALUES (16, 15)");
		stmt.executeUpdate("INSERT INTO UsedOption VALUES (25, 15)");
		stmt.executeUpdate("INSERT INTO UsedOption VALUES (28, 15)");
		
		stmt.close();
	}
	
	private static Connection createConn() throws SQLException{
		return DriverManager.getConnection(DB_URL);
	}
	
	private static void closeConn(Connection conn){
		if(conn != null){
			try{
				conn.close();
			}
			catch(SQLException ex){
				System.out.println("ERROR: "+ex.getMessage());
			}
		}
	}
	
	public static ArrayList<String> getCustomerNames() throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		ResultSet results = stmt.executeQuery("SELECT Name FROM Customer");
		ArrayList<String> listData = new ArrayList<>();
		while(results.next()){
			listData.add(results.getString(1));
		}
		
		stmt.close();
		closeConn(conn);
		return listData;
	}
	
	public static ArrayList<Integer> getCustomerIDs() throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		ResultSet results = stmt.executeQuery("SELECT CustomerID FROM Customer");
		ArrayList<Integer> listData = new ArrayList<>();
		while(results.next()){
			listData.add(results.getInt(1));
		}
		
		stmt.close();
		closeConn(conn);
		return listData;
	}
	
	public static ArrayList<Integer> getPizzaIDs() throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		ResultSet results = stmt.executeQuery("SELECT PizzaID FROM Pizza");
		ArrayList<Integer> listData = new ArrayList<>();
		while(results.next()){
			listData.add(results.getInt(1));
		}
		
		stmt.close();
		closeConn(conn);
		return listData;
	}
	
	public static ArrayList<Integer> getAvailableOptionIDs() throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		ResultSet results = stmt.executeQuery("SELECT OptionID FROM AvailableOption");
		ArrayList<Integer> listData = new ArrayList<>();
		while(results.next()){
			listData.add(results.getInt(1));
		}
		
		stmt.close();
		closeConn(conn);
		return listData;
	}
	
	public static String getCustomerName(int CID) throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		ResultSet results = stmt.executeQuery("SELECT Name FROM Customer WHERE CustomerID="+CID);
		String val = results.next() ? results.getString(1) : "";
		
		stmt.close();
		closeConn(conn);
		return val;
	}
	
	public static double getAvailableOptionPrice(int OID) throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		ResultSet results = stmt.executeQuery("SELECT Price FROM AvailableOption WHERE OptionID="+OID);
		double val = results.next() ? results.getDouble(1) : -1.0;
		
		stmt.close();
		closeConn(conn);
		return val;
	}
	
	public static String getAvailableOptionName(int OID) throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		ResultSet results = stmt.executeQuery("SELECT Name FROM AvailableOption WHERE OptionID="+OID);
		String val = results.next() ? results.getString(1) : "";
		
		stmt.close();
		closeConn(conn);
		return val;
	}
	
	public static double getPizzaPrice(int PID) throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		ResultSet results = stmt.executeQuery("SELECT Price FROM Pizza WHERE PizzaID="+PID);
		double val = results.next() ? results.getDouble(1) : -1.0;
		
		stmt.close();
		closeConn(conn);
		return val;
	}
	
	public static int getPizzaCustomer(int PID) throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		ResultSet results = stmt.executeQuery("SELECT CustomerID FROM Pizza WHERE PizzaID="+PID);
		int val = results.next() ? results.getInt(1) : -1;
		
		stmt.close();
		closeConn(conn);
		return val;
	}
	
	public static ArrayList<String> getCustomerPizzas(int CID) throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		Statement stmt2 = conn.createStatement();
		
		ResultSet results = stmt.executeQuery("SELECT * FROM Pizza WHERE CustomerID="+CID);
		ArrayList<String> listData = new ArrayList<>();
		while(results.next()){
			int PID = results.getInt("PizzaID");
			ResultSet pizzaOptions = stmt2.executeQuery("SELECT AvailableOption.Name, AvailableOption.Type FROM "+
					"UsedOption LEFT JOIN AvailableOption ON UsedOption.OptionID=AvailableOption.OptionID "+
					"WHERE UsedOption.PizzaID="+PID);
			String crust = "", sauce = "";
			StringBuilder toppings = new StringBuilder(25);
			while(pizzaOptions.next()){
				switch(pizzaOptions.getString(2)){
					case "C" -> crust = pizzaOptions.getString(1);
					case "S" -> sauce = pizzaOptions.getString(1);
					case "T" -> {
						if(!toppings.isEmpty()){
							toppings.append(", ");
						}
						toppings.append(pizzaOptions.getString(1));
					}
				}
			}
			listData.add("PID: %d; Size: %s; Crust: %s; Sauce: %s; Toppings: %s; Price: $%.2f".formatted(
					PID, results.getString("Size"), crust, sauce,
					toppings.toString(), results.getDouble("Price")));
		}
		
		stmt2.close();
		stmt.close();
		closeConn(conn);
		return listData;
	}
	
	public static String getPizzaSummary(int PID) throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		Statement stmt2 = conn.createStatement();
		
		String val;
		ResultSet results = stmt.executeQuery("SELECT * FROM Pizza WHERE PizzaID="+PID);
		if(!results.next()){
			return "";
		}
		ResultSet pizzaOptions = stmt2.executeQuery("SELECT AvailableOption.Name, AvailableOption.Type FROM "+
				"UsedOption LEFT JOIN AvailableOption ON UsedOption.OptionID=AvailableOption.OptionID "+
				"WHERE UsedOption.PizzaID="+PID);
		String crust = "", sauce = "";
		StringBuilder toppings = new StringBuilder(25);
		while(pizzaOptions.next()){
			switch(pizzaOptions.getString(2)){
				case "C" -> crust = pizzaOptions.getString(1);
				case "S" -> sauce = pizzaOptions.getString(1);
				case "T" -> {
					if(!toppings.isEmpty()){
						toppings.append(", ");
					}
					toppings.append(pizzaOptions.getString(1));
				}
			}
		}
		val = "CID: %d; Size: %s; Crust: %s; Sauce: %s; Toppings: %s; Price: $%.2f".formatted(
				results.getInt("CustomerID"), results.getString("Size"),
				crust, sauce, toppings.toString(), results.getDouble("Price"));
		
		stmt2.close();
		stmt.close();
		closeConn(conn);
		return val;
	}
	
	public static ArrayList<String> getCustomerSummaries() throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		ResultSet results = stmt.executeQuery("SELECT Customer.CustomerID, Name, Address, "+
				"PhoneNumber, COALESCE(SUM(Pizza.Price),0.0) FROM Customer LEFT JOIN Pizza "+
				"ON Customer.CustomerID=Pizza.CustomerID GROUP BY Customer.CustomerID, Name, Address, PhoneNumber");
		ArrayList<String> listData = new ArrayList<>();
		while(results.next()){
			listData.add("CID: %d; Name: %s; Address: %s; Phone Number: %s; Total Owed: $%.2f".formatted(
					results.getInt(1), results.getString(2),
					results.getString(3), results.getString(4),
					results.getDouble(5)));
		}
		
		stmt.close();
		closeConn(conn);
		return listData;
	}
	
	public static String getCustomerSummary(int CID) throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		ResultSet results = stmt.executeQuery("SELECT Name, Address, PhoneNumber, COALESCE(SUM(Price),0.0) "+
				"FROM Customer LEFT JOIN Pizza ON Customer.CustomerID=Pizza.CustomerID "+
				"WHERE Customer.CustomerID="+CID+" GROUP BY Customer.CustomerID, Name, Address, PhoneNumber");
		if(!results.next()){
			return "";
		}
		String val = "Name: %s; Address: %s; Phone Number: %s; Total Owed: $%.2f".formatted(
				results.getString(1), results.getString(2),
				results.getString(3), results.getDouble(4));
		
		stmt.close();
		closeConn(conn);
		return val;
	}
	
	public static ArrayList<String> getAvailableOptions() throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		ResultSet results = stmt.executeQuery("SELECT OptionID, Name, Price FROM AvailableOption");
		ArrayList<String> listData = new ArrayList<>();
		while(results.next()){
			listData.add("OID: %d; Name: %s; Price: $%.2f".formatted(results.getInt(1),
					results.getString(2), results.getDouble(3)));
		}
		
		stmt.close();
		closeConn(conn);
		return listData;
	}
	
	public static ArrayList<String> getAvailableCrusts() throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		ResultSet results = stmt.executeQuery("SELECT OptionID, Name, Price FROM AvailableOption WHERE Type='C'");
		ArrayList<String> listData = new ArrayList<>();
		while(results.next()){
			listData.add("OID: %d; Name: %s; Price: $%.2f".formatted(results.getInt(1),
					results.getString(2), results.getDouble(3)));
		}
		
		stmt.close();
		closeConn(conn);
		return listData;
	}
	
	public static ArrayList<String> getAvailableSauces() throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		ResultSet results = stmt.executeQuery("SELECT OptionID, Name, Price FROM AvailableOption WHERE Type='S'");
		ArrayList<String> listData = new ArrayList<>();
		while(results.next()){
			listData.add("OID: %d; Name: %s; Price: $%.2f".formatted(results.getInt(1),
					results.getString(2), results.getDouble(3)));
		}
		
		stmt.close();
		closeConn(conn);
		return listData;
	}
	
	public static ArrayList<String> getAvailableToppings() throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		ResultSet results = stmt.executeQuery("SELECT OptionID, Name, Price FROM AvailableOption WHERE Type='T'");
		ArrayList<String> listData = new ArrayList<>();
		while(results.next()){
			listData.add("OID: %d; Name: %s; Price: $%.2f".formatted(results.getInt(1),
					results.getString(2), results.getDouble(3)));
		}
		
		stmt.close();
		closeConn(conn);
		return listData;
	}
	
	public static ArrayList<String> getUsedOptions(int PID) throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		ResultSet results = stmt.executeQuery("SELECT UsedOption.OptionID, Name, Type, Price "+
				"FROM UsedOption LEFT JOIN AvailableOption ON UsedOption.OptionID=AvailableOption.OptionID "+
				"WHERE PizzaID="+PID);
		ArrayList<String> listData = new ArrayList<>();
		while(results.next()){
			listData.add("OID: %d; Name: %s; Type: %s; Price: $%.2f".formatted(
					results.getInt(1), results.getString(2),
					results.getString(3), results.getDouble(4)));
		}
		
		stmt.close();
		closeConn(conn);
		return listData;
	}
	
	public static String getPizzaCrust(int PID) throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		ResultSet results = stmt.executeQuery("SELECT UsedOption.OptionID, Name, Price "+
				"FROM UsedOption LEFT JOIN AvailableOption ON UsedOption.OptionID=AvailableOption.OptionID "+
				"WHERE PizzaID="+PID+" AND Type='C'");
		if(!results.next()){
			return "";
		}
		String val = "OID: %d; Name: %s; Price: $%.2f".formatted(results.getInt(1),
					results.getString(2), results.getDouble(3));
		
		stmt.close();
		closeConn(conn);
		return val;
	}
	
	public static String getPizzaSauce(int PID) throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		ResultSet results = stmt.executeQuery("SELECT UsedOption.OptionID, Name, Price "+
				"FROM UsedOption LEFT JOIN AvailableOption ON UsedOption.OptionID=AvailableOption.OptionID "+
				"WHERE PizzaID="+PID+" AND Type='S'");
		if(!results.next()){
			return "";
		}
		String val = "OID: %d; Name: %s; Price: $%.2f".formatted(results.getInt(1),
				results.getString(2), results.getDouble(3));
		
		stmt.close();
		closeConn(conn);
		return val;
	}
	
	public static ArrayList<String> getPizzaToppings(int PID) throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		ResultSet results = stmt.executeQuery("SELECT UsedOption.OptionID, Name, Price "+
				"FROM UsedOption LEFT JOIN AvailableOption ON UsedOption.OptionID=AvailableOption.OptionID "+
				"WHERE PizzaID="+PID+" AND Type='T'");
		ArrayList<String> listData = new ArrayList<>();
		while(results.next()){
			listData.add("OID: %d; Name: %s; Price: $%.2f".formatted(results.getInt(1),
					results.getString(2), results.getDouble(3)));
		}
		
		stmt.close();
		closeConn(conn);
		return listData;
	}
	
	public static String getPizzaSize(int PID) throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		ResultSet results = stmt.executeQuery("SELECT Size FROM Pizza "+
				"WHERE PizzaID="+PID);
		if(!results.next()){
			return "";
		}
		String val = results.getString(1);
		
		stmt.close();
		closeConn(conn);
		return val;
	}
	
	private static int generateID(int mod3res){
		Random r = new Random();
		int val = r.nextInt(ID_LOWER_BOUND, ID_UPPER_BOUND);
		return val%3==mod3res ? val : generateID(mod3res);
	}
	
	public static int getUniquePID() throws SQLException{//TODO test
		ArrayList<Integer> usedPIDs;
		usedPIDs = getPizzaIDs();
		int val;
		do{
			val = generateID(0);
		} while(usedPIDs.contains(val));
		return val;
	}
	
	public static int getUniqueOID() throws SQLException{//TODO test
		ArrayList<Integer> usedOIDs;
		usedOIDs = getAvailableOptionIDs();
		int val;
		do{
			val = generateID(1);
		} while(usedOIDs.contains(val));
		return val;
	}
	
	public static int getUniqueCID() throws SQLException{//TODO test
		ArrayList<Integer> usedCIDs;
		usedCIDs = getCustomerIDs();
		int val;
		do{
			val = generateID(2);
		} while(usedCIDs.contains(val));
		return val;
	}
	
	public static int getIDFromSummary(String summary){//TODO test
		Matcher m = Pattern.compile("[POC]?ID: 0*(\\d+)").matcher(summary);
		return m.find() ? Integer.parseInt(m.group(1)) : -1;
	}
	
	public static int getPIDFromSummary(String summary){//TODO test
		Matcher m = Pattern.compile("PID: 0*(\\d+)").matcher(summary);
		return m.find() ? Integer.parseInt(m.group(1)) : -1;
	}
	
	public static int getOIDFromSummary(String summary){//TODO test
		Matcher m = Pattern.compile("OID: 0*(\\d+)").matcher(summary);
		return m.find() ? Integer.parseInt(m.group(1)) : -1;
	}
	
	public static int getCIDFromSummary(String summary){//TODO test
		Matcher m = Pattern.compile("CID: 0*(\\d+)").matcher(summary);
		return m.find() ? Integer.parseInt(m.group(1)) : -1;
	}
	
	public static double getPriceFromSummary(String summary){//TODO test
		Matcher m = Pattern.compile("Price: \\$(\\d+\\.?\\d*)").matcher(summary);
		return m.find() ? Double.parseDouble(m.group(1)) : -1.0;
	}
	
	public static void submitOrder(int CID, ArrayList<Integer> OIDs, String size, double price) throws SQLException{//TODO test
		int PID = insertPizza(CID, size, price);
		for(int OID : OIDs){
			insertUsedOption(OID, PID);
		}
	}
	
	public static int insertCustomer(String name, String address, String phoneNumber) throws SQLException{//TODO test
		int CID = getUniqueCID();
		insertCustomer(CID, name, address, phoneNumber);
		return CID;
	}
	
	public static void insertCustomer(int CID, String name, String address, String phoneNumber) throws SQLException{//TODO test
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		stmt.executeUpdate("INSERT INTO Customer VALUES (%d, '%s', '%s', '%s')".formatted(
				CID, name, address, phoneNumber));
		
		stmt.close();
		closeConn(conn);
	}
	
	public static int insertAvailableOption(String name, String type, double price) throws SQLException{//TODO test
		int OID = getUniqueOID();
		insertAvailableOption(OID, name, type, price);
		return OID;
	}
	
	public static void insertAvailableOption(int OID, String name, String type, double price) throws SQLException{//TODO test
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		stmt.executeUpdate("INSERT INTO AvailableOption VALUES (%d, '%s', '%s', %f)".formatted(
				OID, name, type, price));
		
		stmt.close();
		closeConn(conn);
	}
	
	public static void insertUsedOption(int OID, int PID) throws SQLException{//TODO test
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		stmt.executeUpdate("INSERT INTO UsedOption VALUES (%d, %d)".formatted(OID, PID));
		
		stmt.close();
		closeConn(conn);
	}
	
	public static int insertPizza(int CID, String size, double price) throws SQLException{//TODO test
		int PID = getUniquePID();
		insertPizza(PID, CID, size, price);
		return PID;
	}
	
	public static void insertPizza(int PID, int CID, String size, double price) throws SQLException{//TODO test
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		stmt.executeUpdate("INSERT INTO Pizza VALUES (%d, %d, '%s', %f)".formatted(PID, CID, size, price));
		
		stmt.close();
		closeConn(conn);
	}
	
	public static void updateCustomer(int CID, String name, String address, String phoneNumber) throws SQLException{//TODO test
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		stmt.executeUpdate("UPDATE Customer SET Name='%s', Address='%s', PhoneNumber='%s' WHERE CustomerID=%d"
				.formatted(name, address, phoneNumber, CID));
		
		stmt.close();
		closeConn(conn);
	}
	
	public static void updateAvailableOption(int OID, String name, String type, double price) throws SQLException{//TODO test
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		stmt.executeUpdate("UPDATE AvailableOption SET Name='%s', Type='%s', Price=%f WHERE OptionID=%d"
				.formatted(name, type, price, OID));
		
		stmt.close();
		closeConn(conn);
	}
	
	public static void updatePizza(int PID, int CID, String size, double price) throws SQLException{//TODO test
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		stmt.executeUpdate("UPDATE Pizza SET CustomerID=%d, Size='%s', Price=%f WHERE PizzaID=%d"
				.formatted(CID, size, price, PID));
		
		stmt.close();
		closeConn(conn);
	}
	
	public static void removeCustomer(int CID) throws SQLException{//TODO test
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		stmt.executeUpdate("DELETE FROM Customer WHERE CustomerID=%d".formatted(CID));
		ResultSet results = stmt.executeQuery("SELECT PizzaID FROM Pizza WHERE CustomerID=%d"
				.formatted(CID));
		while(results.next()){
			removePizza(results.getInt(1));
		}
		
		stmt.close();
		closeConn(conn);
	}
	
	public static void removeAvailableOption(int OID) throws SQLException{//TODO test
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		stmt.executeUpdate("DELETE FROM AvailableOption WHERE OptionID=%d".formatted(OID));
		stmt.executeUpdate("DELETE FROM UsedOption WHERE OptionID=%d".formatted(OID));
		
		stmt.close();
		closeConn(conn);
	}
	
	public static void removeUsedOption(int OID, int PID) throws SQLException{//TODO test
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		stmt.executeUpdate("DELETE FROM UsedOption WHERE OptionID=%d AND PizzaID=%d".formatted(OID, PID));
		
		stmt.close();
		closeConn(conn);
	}
	
	public static void removePizza(int PID) throws SQLException{//TODO test
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		stmt.executeUpdate("DELETE FROM Pizza WHERE PizzaID=%d".formatted(PID));
		stmt.executeUpdate("DELETE FROM UsedOption WHERE PizzaID=%d".formatted(PID));
		
		stmt.close();
		closeConn(conn);
	}
}
