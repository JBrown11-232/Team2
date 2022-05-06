//Written by Josh Brown

package com.example.application;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

//TODO add input filters for cntrl chars and ' (prepared statments)?
//TODO make sure Option type and Pizza size are cut to one char
//TODO validate pizzas having exactly 1 crust and sauce before updates
//TODO check all void methods (and non voids)

public class PizzaDBManager{
	final static private String DB_URL = "jdbc:derby:PizzaDB;create=true";
	final static private int ID_LOWER_BOUND = 1;
	final static private int ID_UPPER_BOUND = 100000000;
	
	static private int worldNum = 0;
	
	public static int getWorldNum(){
		return worldNum;
	}
	
	public static void createDB(){
		try{
			Connection conn = createConn();
			System.out.println("Dropping...");
			worldNum = -1*Math.abs(worldNum+1);
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
			worldNum = Math.abs(worldNum);
			for(int PID : getPizzaIDs()){
				recalculatePizzaPrice(PID);
			}
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
	
	public static Customer getPizzaCustomer(int PID) throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		ResultSet results = stmt.executeQuery("SELECT Customer.CustomerID, Name, Address, PhoneNumber "+
				"FROM Pizza LEFT JOIN Customer ON Pizza.CustomerID=Customer.CustomerID WHERE PizzaID="+PID);
		Customer val = results.next() ? new Customer(results.getInt(1), results.getString(2),
				results.getString(3), results.getString(4), worldNum) : null;
		
		stmt.close();
		closeConn(conn);
		return val;
	}
	
	public static ArrayList<Pizza> getCustomerPizzas(int CID) throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		Statement stmt2 = conn.createStatement();
		
		ResultSet results = stmt.executeQuery("SELECT * FROM Pizza LEFT JOIN Customer ON "+
				"Pizza.CustomerID=Customer.CustomerID WHERE Customer.CustomerID="+CID);
		ArrayList<Pizza> listData = new ArrayList<>();
		while(results.next()){
			int PID = results.getInt("PizzaID");
			Customer customer = new Customer(results.getInt("CustomerID"),
					results.getString("Name"), results.getString("Address"),
					results.getString("PhoneNumber"), worldNum);
			ResultSet pizzaOptions = stmt2.executeQuery("SELECT UsedOption.OptionID, Name, Type, Price FROM "+
					"UsedOption LEFT JOIN AvailableOption ON UsedOption.OptionID=AvailableOption.OptionID "+
					"WHERE UsedOption.PizzaID="+PID);
			Option crust = null, sauce = null;
			ArrayList<Option> toppings = new ArrayList<>();
			while(pizzaOptions.next()){
				switch(pizzaOptions.getString(3)){
					case "C" -> crust = new Option(pizzaOptions.getInt(1),
							pizzaOptions.getString(2), pizzaOptions.getString(3),
							pizzaOptions.getDouble(4), worldNum);
					case "S" -> sauce = new Option(pizzaOptions.getInt(1),
							pizzaOptions.getString(2), pizzaOptions.getString(3),
							pizzaOptions.getDouble(4), worldNum);
					case "T" ->	toppings.add(new Option(pizzaOptions.getInt(1),
							pizzaOptions.getString(2), pizzaOptions.getString(3),
							pizzaOptions.getDouble(4), worldNum));
				}
			}
			listData.add(new Pizza(PID, customer, results.getString("Size"),
					crust, sauce, toppings, results.getDouble("Price"), worldNum));
		}
		
		stmt2.close();
		stmt.close();
		closeConn(conn);
		return listData;
	}
	
	public static Pizza getPizza(int PID) throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		Statement stmt2 = conn.createStatement();
		
		ResultSet results = stmt.executeQuery("SELECT * FROM Pizza LEFT JOIN Customer ON "+
				"Pizza.CustomerID=Customer.CustomerID WHERE PizzaID="+PID);
		if(!results.next()){
			return null;
		}
		Customer customer = new Customer(results.getInt("CustomerID"),
				results.getString("Name"), results.getString("Address"),
				results.getString("PhoneNumber"), worldNum);
		ResultSet pizzaOptions = stmt2.executeQuery("SELECT UsedOption.OptionID, Name, Type, Price FROM "+
				"UsedOption LEFT JOIN AvailableOption ON UsedOption.OptionID=AvailableOption.OptionID "+
				"WHERE UsedOption.PizzaID="+PID);
		Option crust = null, sauce = null;
		ArrayList<Option> toppings = new ArrayList<>();
		while(pizzaOptions.next()){
			switch(pizzaOptions.getString(3)){
				case "C" -> crust = new Option(pizzaOptions.getInt(1),
						pizzaOptions.getString(2), pizzaOptions.getString(3),
						pizzaOptions.getDouble(4), worldNum);
				case "S" -> sauce = new Option(pizzaOptions.getInt(1),
						pizzaOptions.getString(2), pizzaOptions.getString(3),
						pizzaOptions.getDouble(4), worldNum);
				case "T" ->	toppings.add(new Option(pizzaOptions.getInt(1),
						pizzaOptions.getString(2), pizzaOptions.getString(3),
						pizzaOptions.getDouble(4), worldNum));
			}
		}
		Pizza pizza = new Pizza(PID, customer, results.getString("Size"),
				crust, sauce, toppings, results.getDouble("Price"), worldNum);
		
		stmt2.close();
		stmt.close();
		closeConn(conn);
		return pizza;
	}
	
	public static ArrayList<Customer> getCustomers() throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		ResultSet results = stmt.executeQuery("SELECT CustomerID, Name, Address, PhoneNumber FROM Customer");
		ArrayList<Customer> listData = new ArrayList<>();
		while(results.next()){
			listData.add(new Customer(results.getInt(1), results.getString(2),
					results.getString(3), results.getString(4), worldNum));
		}
		
		stmt.close();
		closeConn(conn);
		return listData;
	}
	
	public static Customer getCustomer(int CID) throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		ResultSet results = stmt.executeQuery("SELECT Name, Address, PhoneNumber "+
				"FROM Customer WHERE CustomerID="+CID);
		if(!results.next()){
			return null;
		}
		Customer customer = new Customer(CID, results.getString(1), results.getString(2),
				results.getString(3), worldNum);
		
		stmt.close();
		closeConn(conn);
		return customer;
	}
	
	public static Option getAvailableOption(int OID) throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		ResultSet results = stmt.executeQuery("SELECT OptionID, Name, Type, Price "+
				"FROM AvailableOption WHERE OptionID="+OID);
		if(!results.next()){
			return null;
		}
		Option option = new Option(results.getInt(1), results.getString(2),
				results.getString(3), results.getDouble(4), worldNum);
		
		stmt.close();
		closeConn(conn);
		return option;
	}
	
	public static double getTotalOwed(int CID) throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		ResultSet results = stmt.executeQuery("SELECT COALESCE(SUM(Pizza.Price),0.0) FROM Customer LEFT JOIN Pizza "+
				"ON Customer.CustomerID=Pizza.CustomerID WHERE Customer.CustomerID="+CID+" GROUP BY Customer.CustomerID");
		if(!results.next()){
			return -1.0;
		}
		double val = results.getDouble(1);
		
		stmt.close();
		closeConn(conn);
		return val;
		
	}
	
	public static ArrayList<Option> getAvailableOptions() throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		ResultSet results = stmt.executeQuery("SELECT OptionID, Name, Type, Price FROM AvailableOption");
		ArrayList<Option> listData = new ArrayList<>();
		while(results.next()){
			listData.add(new Option(results.getInt(1), results.getString(2),
					results.getString(3), results.getDouble(4), worldNum));
		}
		
		stmt.close();
		closeConn(conn);
		return listData;
	}
	
	public static ArrayList<Option> getAvailableCrusts() throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		ResultSet results = stmt.executeQuery("SELECT OptionID, Name, Price FROM AvailableOption WHERE Type='C'");
		ArrayList<Option> listData = new ArrayList<>();
		while(results.next()){
			listData.add(new Option(results.getInt(1), results.getString(2),
					"C", results.getDouble(3), worldNum));
		}
		
		stmt.close();
		closeConn(conn);
		return listData;
	}
	
	public static ArrayList<Option> getAvailableSauces() throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		ResultSet results = stmt.executeQuery("SELECT OptionID, Name, Price FROM AvailableOption WHERE Type='S'");
		ArrayList<Option> listData = new ArrayList<>();
		while(results.next()){
			listData.add(new Option(results.getInt(1), results.getString(2),
					"S", results.getDouble(3), worldNum));
		}
		
		stmt.close();
		closeConn(conn);
		return listData;
	}
	
	public static ArrayList<Option> getAvailableToppings() throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		ResultSet results = stmt.executeQuery("SELECT OptionID, Name, Price FROM AvailableOption WHERE Type='T'");
		ArrayList<Option> listData = new ArrayList<>();
		while(results.next()){
			listData.add(new Option(results.getInt(1), results.getString(2),
					"T", results.getDouble(3), worldNum));
		}
		
		stmt.close();
		closeConn(conn);
		return listData;
	}
	
	public static ArrayList<Option> getUsedOptions(int PID) throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		ResultSet results = stmt.executeQuery("SELECT UsedOption.OptionID, Name, Type, Price "+
				"FROM UsedOption LEFT JOIN AvailableOption ON UsedOption.OptionID=AvailableOption.OptionID "+
				"WHERE PizzaID="+PID);
		ArrayList<Option> listData = new ArrayList<>();
		while(results.next()){
			listData.add(new Option(results.getInt(1), results.getString(2),
					results.getString(3), results.getDouble(4), worldNum));
		}
		
		stmt.close();
		closeConn(conn);
		return listData;
	}
	
	public static Option getPizzaCrust(int PID) throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		ResultSet results = stmt.executeQuery("SELECT UsedOption.OptionID, Name, Price "+
				"FROM UsedOption LEFT JOIN AvailableOption ON UsedOption.OptionID=AvailableOption.OptionID "+
				"WHERE PizzaID="+PID+" AND Type='C'");
		if(!results.next()){
			return null;
		}
		Option crust = new Option(results.getInt(1), results.getString(2),
				"C", results.getDouble(3), worldNum);
		
		stmt.close();
		closeConn(conn);
		return crust;
	}
	
	public static Option getPizzaSauce(int PID) throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		ResultSet results = stmt.executeQuery("SELECT UsedOption.OptionID, Name, Price "+
				"FROM UsedOption LEFT JOIN AvailableOption ON UsedOption.OptionID=AvailableOption.OptionID "+
				"WHERE PizzaID="+PID+" AND Type='S'");
		if(!results.next()){
			return null;
		}
		Option sauce = new Option(results.getInt(1), results.getString(2),
				"S", results.getDouble(3), worldNum);
		
		stmt.close();
		closeConn(conn);
		return sauce;
	}
	
	public static ArrayList<Option> getPizzaToppings(int PID) throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		ResultSet results = stmt.executeQuery("SELECT UsedOption.OptionID, Name, Price "+
				"FROM UsedOption LEFT JOIN AvailableOption ON UsedOption.OptionID=AvailableOption.OptionID "+
				"WHERE PizzaID="+PID+" AND Type='T'");
		ArrayList<Option> listData = new ArrayList<>();
		while(results.next()){
			listData.add(new Option(results.getInt(1), results.getString(2),
					"T", results.getDouble(3), worldNum));
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
	
	public static int getUniquePID() throws SQLException{
		ArrayList<Integer> usedPIDs;
		usedPIDs = getPizzaIDs();
		int val;
		do{
			val = generateID(0);
		} while(usedPIDs.contains(val));
		return val;
	}
	
	public static int getUniqueOID() throws SQLException{
		ArrayList<Integer> usedOIDs;
		usedOIDs = getAvailableOptionIDs();
		int val;
		do{
			val = generateID(1);
		} while(usedOIDs.contains(val));
		return val;
	}
	
	public static int getUniqueCID() throws SQLException{
		ArrayList<Integer> usedCIDs;
		usedCIDs = getCustomerIDs();
		int val;
		do{
			val = generateID(2);
		} while(usedCIDs.contains(val));
		return val;
	}
	
	public static int submitOrder(int CID, ArrayList<Integer> OIDs, String size, double price) throws SQLException{
		int PID = insertPizza(CID, size, price);
		for(int OID : OIDs){
			insertUsedOption(OID, PID);
		}
		return PID;
	}
	
	public static int submitOrder(Customer customer, ArrayList<Option> options, String size) throws SQLException{
		double price = options.stream().mapToDouble(Option::getPrice).sum() + Pizza.getSizePrice(size);
		return submitOrder(customer.getCID(), new ArrayList<>(options.stream().map(Option::getOID).toList()), size, price);
	}
	
	public static int insertCustomer(String name, String address, String phoneNumber) throws SQLException{
		int CID = getUniqueCID();
		insertCustomer(CID, name, address, phoneNumber);
		return CID;
	}
	
	public static void insertCustomer(int CID, String name, String address, String phoneNumber) throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		stmt.executeUpdate("INSERT INTO Customer VALUES (%d, '%s', '%s', '%s')".formatted(
				CID, name, address, phoneNumber));
		worldNum++;
		
		stmt.close();
		closeConn(conn);
	}
	
	public static int insertAvailableOption(String name, String type, double price) throws SQLException{
		int OID = getUniqueOID();
		insertAvailableOption(OID, name, type, price);
		return OID;
	}
	
	public static void insertAvailableOption(int OID, String name, String type, double price) throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		stmt.executeUpdate("INSERT INTO AvailableOption VALUES (%d, '%s', '%s', %f)".formatted(
				OID, name, type, price));
		worldNum++;
		
		stmt.close();
		closeConn(conn);
	}
	
	public static void insertUsedOption(int OID, int PID) throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		stmt.executeUpdate("INSERT INTO UsedOption VALUES (%d, %d)".formatted(OID, PID));
		worldNum++;
		
		stmt.close();
		closeConn(conn);
	}
	
	public static int insertPizza(int CID, String size, double price) throws SQLException{
		int PID = getUniquePID();
		insertPizza(PID, CID, size, price);
		return PID;
	}
	
	public static void insertPizza(int PID, int CID, String size, double price) throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		stmt.executeUpdate("INSERT INTO Pizza VALUES (%d, %d, '%s', %f)".formatted(PID, CID, size, price));
		worldNum++;
		
		stmt.close();
		closeConn(conn);
	}
	
	public static void updateCustomer(int CID, String name, String address, String phoneNumber) throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		stmt.executeUpdate("UPDATE Customer SET Name='%s', Address='%s', PhoneNumber='%s' WHERE CustomerID=%d"
				.formatted(name, address, phoneNumber, CID));
		worldNum++;
		
		stmt.close();
		closeConn(conn);
	}
	
	public static void updateAvailableOption(int OID, String name, String type, double price) throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		stmt.executeUpdate("UPDATE AvailableOption SET Name='%s', Type='%s', Price=%f WHERE OptionID=%d"
				.formatted(name, type, price, OID));
		worldNum++;
		
		stmt.close();
		closeConn(conn);
	}
	
	public static void updatePizza(int PID, int CID, String size, double price) throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		stmt.executeUpdate("UPDATE Pizza SET CustomerID=%d, Size='%s', Price=%f WHERE PizzaID=%d"
				.formatted(CID, size, price, PID));
		worldNum++;
		
		stmt.close();
		closeConn(conn);
	}
	
	public static void recalculatePizzaPrice(int PID) throws SQLException{
		ArrayList<Option> usedOptions = getUsedOptions(PID);
		String size = getPizzaSize(PID);
		double price = usedOptions.stream().mapToDouble(Option::getPrice).sum() + Pizza.getSizePrice(size);
		
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		stmt.executeUpdate("UPDATE Pizza SET Price=%f WHERE PizzaID=%d"
				.formatted(price, PID));
		worldNum++;
		
		stmt.close();
		closeConn(conn);
	}
	
	public static void removeCustomer(Customer customer) throws SQLException{
		removeCustomer(customer.getCID());
	}
	
	public static void removeCustomer(int CID) throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		ResultSet results = stmt.executeQuery("SELECT PizzaID FROM Pizza WHERE CustomerID=%d"
				.formatted(CID));
		while(results.next()){
			removePizza(results.getInt(1));
		}
		
		stmt.executeUpdate("DELETE FROM Customer WHERE CustomerID=%d".formatted(CID));
		worldNum++;
		
		stmt.close();
		closeConn(conn);
	}
	
	public static void removeAvailableOption(Option option) throws SQLException{
		removeAvailableOption(option.getOID());
	}
	
	public static void removeAvailableOption(int OID) throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		stmt.executeUpdate("DELETE FROM UsedOption WHERE OptionID=%d".formatted(OID));
		worldNum++;
		stmt.executeUpdate("DELETE FROM AvailableOption WHERE OptionID=%d".formatted(OID));
		worldNum++;
		
		stmt.close();
		closeConn(conn);
	}
	
	public static void removeUsedOption(Option option, Pizza pizza) throws SQLException{
		removeUsedOption(option.getOID(), pizza.getPID());
	}
	
	public static void removeUsedOption(int OID, int PID) throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		stmt.executeUpdate("DELETE FROM UsedOption WHERE OptionID=%d AND PizzaID=%d".formatted(OID, PID));
		worldNum++;
		
		stmt.close();
		closeConn(conn);
	}
	
	public static void removePizza(Pizza pizza) throws SQLException{
		removePizza(pizza.getPID());
	}
	
	public static void removePizza(int PID) throws SQLException{
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		stmt.executeUpdate("DELETE FROM UsedOption WHERE PizzaID=%d".formatted(PID));
		worldNum++;
		stmt.executeUpdate("DELETE FROM Pizza WHERE PizzaID=%d".formatted(PID));
		worldNum++;
		
		stmt.close();
		closeConn(conn);
	}
}
