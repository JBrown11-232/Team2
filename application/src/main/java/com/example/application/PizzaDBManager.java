//Written by Josh Brown

package com.example.application;

import java.sql.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class PizzaDBManager{
	//Define constants and instantiate worldNum
	final static private String DB_URL = "jdbc:derby:PizzaDB;create=true";
	final static private int ID_LOWER_BOUND = 1;
	final static private int ID_UPPER_BOUND = 100000000;
	static private int worldNum = 0;
	
	public static int getWorldNum(){
		return worldNum;
	}
	
	public static void createDB(){
		//Drop tables and create it anew
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
				// Needed to calculate the correct price of pizzas since we gave them a price of 0.0 earlier
				recalculatePizzaPrice(PID);
			}
		}
		catch(SQLException ex){
			System.out.println("ERROR: "+ex.getMessage());
		}
	}
	
	private static void dropTables(Connection conn) throws SQLException{
		//Drop tables from database (ignore when tables already DNE)
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
	
	//Methods to create tables in the database
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
		
		// Enter with a price of zero since we will calculate later
		stmt.executeUpdate("INSERT INTO Pizza VALUES (3, 5, 'S', 0.0)");
		stmt.executeUpdate("INSERT INTO Pizza VALUES (6, 5, 'M', 0.0)");
		stmt.executeUpdate("INSERT INTO Pizza VALUES (9, 5, 'L', 0.0)");
		stmt.executeUpdate("INSERT INTO Pizza VALUES (12, 2, 'L', 0.0)");
		stmt.executeUpdate("INSERT INTO Pizza VALUES (15, 14, 'M', 0.0)");
		
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
		// Requirement 1 use database + 9 use external files
		return DriverManager.getConnection(DB_URL);
	}
	
	private static void closeConn(Connection conn){
		//Close the database connection
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
		//Return the names of the customers in the database
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		ResultSet results = stmt.executeQuery("SELECT Name FROM Customer");
		ArrayList<String> listData = new ArrayList<>();
		// Requirement 6 loops
		while(results.next()){
			listData.add(results.getString(1));
		}
		
		stmt.close();
		closeConn(conn);
		return listData;
	}
	
	public static ArrayList<Integer> getCustomerIDs() throws SQLException{
		//Return the IDs of the customers in the database
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
		//Return the IDs of the pizzas in the database
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
		//Return the IDs of the available options in the database
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
		//Return the name of the specified customer in the database
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		ResultSet results = stmt.executeQuery("SELECT Name FROM Customer WHERE CustomerID="+CID);
		String val = results.next() ? results.getString(1) : "";
		
		stmt.close();
		closeConn(conn);
		return val;
	}
	
	public static double getAvailableOptionPrice(int OID) throws SQLException{
		//Return the price of the specified available option in the database
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		ResultSet results = stmt.executeQuery("SELECT Price FROM AvailableOption WHERE OptionID="+OID);
		double val = results.next() ? results.getDouble(1) : -1.0;
		
		stmt.close();
		closeConn(conn);
		return val;
	}
	
	public static String getAvailableOptionName(int OID) throws SQLException{
		//Return the name of the specified available option in the database
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		ResultSet results = stmt.executeQuery("SELECT Name FROM AvailableOption WHERE OptionID="+OID);
		String val = results.next() ? results.getString(1) : "";
		
		stmt.close();
		closeConn(conn);
		return val;
	}
	
	public static double getPizzaPrice(int PID) throws SQLException{
		//Return the price of the specified pizza in the database
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		ResultSet results = stmt.executeQuery("SELECT Price FROM Pizza WHERE PizzaID="+PID);
		double val = results.next() ? results.getDouble(1) : -1.0;
		
		stmt.close();
		closeConn(conn);
		return val;
	}
	
	public static Customer getPizzaCustomer(int PID) throws SQLException{
		//Return the customer of the specified pizza in the database
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
		//Return the pizzas of the specified customer in the database
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
		//Return the specified pizza in the database
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		Statement stmt2 = conn.createStatement();
		
		ResultSet results = stmt.executeQuery("SELECT * FROM Pizza LEFT JOIN Customer ON "+
				"Pizza.CustomerID=Customer.CustomerID WHERE PizzaID="+PID);
		// Requirement 4 if-statement
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
		//Return all customers in the database
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
		//Return the specified customer in the database
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
		//Return the specified available option in the database
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
		//Return the total owed of the specified customer in the database
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
		//Return all available options in the database
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
		//Return all available crusts in the database
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
		//Return all available sauces in the database
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
		//Return all available toppings in the database
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
		//Return all used options of the specified pizza in the database
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
		//Return the crust of the specified pizza in the database
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
		//Return the sauce of the specified pizza in the database
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
		//Return all toppings of the specified pizza in the database
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
		//Return the size of the specified pizza in the database
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
		//Return an unused PID
		ArrayList<Integer> usedPIDs;
		usedPIDs = getPizzaIDs();
		int val;
		do{
			val = generateID(0);
		} while(usedPIDs.contains(val));
		return val;
	}
	
	public static int getUniqueOID() throws SQLException{
		//Return an unused OID
		ArrayList<Integer> usedOIDs;
		usedOIDs = getAvailableOptionIDs();
		int val;
		do{
			val = generateID(1);
		} while(usedOIDs.contains(val));
		return val;
	}
	
	public static int getUniqueCID() throws SQLException{
		//Return an unused CID
		ArrayList<Integer> usedCIDs;
		usedCIDs = getCustomerIDs();
		int val;
		do{
			val = generateID(2);
		} while(usedCIDs.contains(val));
		return val;
	}
	
	public static int submitOrder(Customer customer, ArrayList<Option> options, String size) throws SQLException{
		//Submit a pizza order to the database
		double price = options.stream().mapToDouble(Option::getPrice).sum() + Pizza.getSizePrice(size);
		return submitOrder(customer.getCID(), new ArrayList<>(options.stream().map(Option::getOID).toList()), size, price);
	}
	
	public static int submitOrder(int CID, ArrayList<Integer> OIDs, String size, double price) throws SQLException{
		//Submit a pizza order to the database
		int PID = insertPizza(CID, size, price);
		for(int OID : OIDs){
			insertUsedOption(OID, PID);
		}
		return PID;
	}
	
	public static int insertCustomer(String name, String address, String phoneNumber) throws SQLException{
		//Insert a customer into the database
		int CID = getUniqueCID();
		insertCustomer(CID, name, address, phoneNumber);
		return CID;
	}
	
	public static void insertCustomer(int CID, String name, String address, String phoneNumber) throws SQLException{
		//Insert a customer into the database
		Connection conn = createConn();
		PreparedStatement stmt = conn.prepareStatement("INSERT INTO Customer VALUES (?, ?, ?, ?)");
		
		if(CID < ID_LOWER_BOUND || CID > ID_UPPER_BOUND){
			throw new RuntimeException("CID outside of acceptable range!");
		}
		if(name.length()==0){
			throw new RuntimeException("Customer name cannot be empty!");
		}
		if(name.length() > 50)
		{
			throw new RuntimeException("Customer's name is to long");
		}
		if(address.length() > 80)
		{
			throw new RuntimeException("Customer's address is to long");
		}
		if(phoneNumber.length() > 20)
		{
			throw new RuntimeException("Customer's phone number is to long");
		}
		
		stmt.setInt(1, CID);
		stmt.setString(2, name);
		stmt.setString(3, address);
		stmt.setString(4, phoneNumber);
		stmt.executeUpdate();
		worldNum++;
		
		stmt.close();
		closeConn(conn);
	}
	
	public static int insertAvailableOption(String name, String type, double price) throws SQLException{
		//Insert an available option into the database
		int OID = getUniqueOID();
		insertAvailableOption(OID, name, type, price);
		return OID;
	}
	
	public static void insertAvailableOption(int OID, String name, String type, double price) throws SQLException{
		//Insert an available option into the database
		Connection conn = createConn();
		PreparedStatement stmt = conn.prepareStatement("INSERT INTO AvailableOption VALUES (?, ?, ?, ?)");
		
		if(OID < ID_LOWER_BOUND || OID > ID_UPPER_BOUND){
			throw new RuntimeException("OID outside of acceptable range!");
		}
		if(name.length()==0){
			throw new RuntimeException("Option name cannot be empty!");
		}
		type = type.substring(0,1).toUpperCase(Locale.ROOT);
		if(type.length()!=1 || !"CST".contains(type)){
			throw new RuntimeException("Type must be C, S, or T!");
		}
		if(price < 0.0){
			throw new RuntimeException("Price of option cannot be negative!");
		}
		
		stmt.setInt(1, OID);
		stmt.setString(2, name);
		stmt.setString(3, type);
		stmt.setDouble(4, price);
		stmt.executeUpdate();
		worldNum++;
		
		stmt.close();
		closeConn(conn);
	}
	
	public static void insertUsedOption(int OID, int PID) throws SQLException{
		//Insert a used option into the database
		Connection conn = createConn();
		PreparedStatement stmt = conn.prepareStatement("INSERT INTO UsedOption VALUES (?, ?)");
		
		// No error checking needed since OID and PID have the foreign key constraint
		
		stmt.setInt(1, OID);
		stmt.setInt(2, PID);
		stmt.executeUpdate();
		worldNum++;
		
		stmt.close();
		closeConn(conn);
	}
	
	public static int insertPizza(int CID, String size, double price) throws SQLException{
		//Insert a pizza into the database
		int PID = getUniquePID();
		insertPizza(PID, CID, size, price);
		return PID;
	}
	
	public static void insertPizza(int PID, int CID, String size, double price) throws SQLException{
		//Insert a pizza into the database
		Connection conn = createConn();
		PreparedStatement stmt = conn.prepareStatement("INSERT INTO Pizza VALUES (?, ?, ?, ?)");
		
		if(PID < ID_LOWER_BOUND || PID > ID_UPPER_BOUND){
			throw new RuntimeException("PID outside of acceptable range!");
		}
		// CID doesn't need to be checked since it has the foreign key constraint
		size = size.substring(0,1).toUpperCase(Locale.ROOT);
		if(!"SML".contains(size)){
			throw new RuntimeException("Size must be S, M, or L!");
		}
		if(price < 0.0){
			throw new RuntimeException("Price of pizza cannot be negative!");
		}
		
		stmt.setInt(1, PID);
		stmt.setInt(2, CID);
		stmt.setString(3, size);
		stmt.setDouble(4, price);
		stmt.executeUpdate();
		worldNum++;
		
		stmt.close();
		closeConn(conn);
	}
	
	public static void updateCustomerName(int CID, String name) throws SQLException{
		//Update a customer in the database
		updateCustomerName(getCustomer(CID), name);
	}
	
	public static void updateCustomerName(Customer customer, String name) throws SQLException{
		//Update a customer in the database
		updateCustomer(customer.getCID(), name, customer.getAddress(), customer.getPhoneNumber());
	}
	
	public static void updateCustomerAddress(int CID, String address) throws SQLException{
		//Update a customer in the database
		updateCustomerAddress(getCustomer(CID), address);
	}
	
	public static void updateCustomerAddress(Customer customer, String address) throws SQLException{
		//Update a customer in the database
		updateCustomer(customer.getCID(), customer.getName(), address, customer.getPhoneNumber());
	}
	
	public static void updateCustomerPhoneNumber(int CID, String phoneNumber) throws SQLException{
		//Update a customer in the database
		updateCustomerPhoneNumber(getCustomer(CID), phoneNumber);
	}
	
	public static void updateCustomerPhoneNumber(Customer customer, String phoneNumber) throws SQLException{
		//Update a customer in the database
		updateCustomer(customer.getCID(), customer.getName(), customer.getAddress(), phoneNumber);
	}
	
	public static void updateCustomer(int CID, String name, String address, String phoneNumber) throws SQLException{
		//Update a customer in the database
		Connection conn = createConn();
		PreparedStatement stmt = conn.prepareStatement("UPDATE Customer SET Name=?, Address=?, PhoneNumber=? WHERE CustomerID=?");
		
		if(name.length()==0){
			throw new RuntimeException("Customer name cannot be empty!");
		}
		
		stmt.setString(1, name);
		stmt.setString(2, address);
		stmt.setString(3, phoneNumber);
		stmt.setInt(4, CID);
		stmt.executeUpdate();
		worldNum++;
		
		stmt.close();
		closeConn(conn);
	}
	
	public static void updateAvailableOptionName(int OID, String name) throws SQLException{
		//Update an available option in the database
		updateAvailableOptionName(getAvailableOption(OID), name);
	}
	
	public static void updateAvailableOptionName(Option option, String name) throws SQLException{
		//Update an available option in the database
		updateAvailableOption(option.getOID(), name, option.getOptionType(), option.getPrice());
	}
	
	public static void updateAvailableOptionType(int OID, String type) throws SQLException{
		//Update an available option in the database
		updateAvailableOptionType(getAvailableOption(OID), type);
	}
	
	public static void updateAvailableOptionType(Option option, String type) throws SQLException{
		//Update an available option in the database
		updateAvailableOption(option.getOID(), option.getName(), type, option.getPrice());
	}
	
	public static void updateAvailableOptionPrice(int OID, double price) throws SQLException{
		//Update an available option in the database
		updateAvailableOptionPrice(getAvailableOption(OID), price);
	}
	
	public static void updateAvailableOptionPrice(Option option, double price) throws SQLException{
		//Update an available option in the database
		updateAvailableOption(option.getOID(), option.getName(), option.getOptionType(), price);
	}
	
	public static void updateAvailableOption(int OID, String name, String type, double price) throws SQLException{
		//Update an available option in the database
		Connection conn = createConn();
		PreparedStatement stmt = conn.prepareStatement("UPDATE AvailableOption SET Name=?, Type=?, Price=? WHERE OptionID=?");
		
		if(name.length()==0){
			throw new RuntimeException("Option name cannot be empty!");
		}
		type = type.substring(0,1).toUpperCase(Locale.ROOT);
		if(type.length()!=1 || !"CST".contains(type)){
			throw new RuntimeException("Type must be C, S, or T!");
		}
		if(price < 0.0){
			throw new RuntimeException("Price of option cannot be negative!");
		}
		
		stmt.setString(1, name);
		stmt.setString(2, type);
		stmt.setDouble(3, price);
		stmt.setInt(4, OID);
		stmt.executeUpdate();
		worldNum++;
		
		stmt.close();
		closeConn(conn);
	}
	
	public static void updatePizzaCustomerID(int PID, int CID) throws SQLException{
		//Update a pizza in the database
		updatePizzaCustomerID(getPizza(PID), CID);
	}
	
	public static void updatePizzaCustomerID(Pizza pizza, int CID) throws SQLException{
		//Update a pizza in the database
		updatePizza(pizza.getPID(), CID, pizza.getSize(), pizza.getPrice());
	}
	
	public static void updatePizzaSize(int PID, String size) throws SQLException{
		//Update a pizza in the database
		updatePizzaSize(getPizza(PID), size);
	}
	
	public static void updatePizzaSize(Pizza pizza, String size) throws SQLException{
		//Update a pizza in the database
		updatePizza(pizza.getPID(), pizza.getCID(), size, pizza.getPrice());
	}
	
	public static void updatePizzaPrice(int PID, double price) throws SQLException{
		//Update a pizza in the database
		updatePizzaPrice(getPizza(PID), price);
	}
	
	public static void updatePizzaPrice(Pizza pizza, double price) throws SQLException{
		//Update a pizza in the database
		updatePizza(pizza.getPID(), pizza.getCID(), pizza.getSize(), price);
	}
	
	public static void updatePizza(int PID, int CID, String size, double price) throws SQLException{
		//Update a pizza in the database
		Connection conn = createConn();
		PreparedStatement stmt = conn.prepareStatement("UPDATE Pizza SET CustomerID=?, Size=?, Price=? WHERE PizzaID=?");
		
		size = size.substring(0,1).toUpperCase(Locale.ROOT);
		if(!"SML".contains(size)){
			throw new RuntimeException("Size must be S, M, or L!");
		}
		if(price < 0.0){
			throw new RuntimeException("Price of pizza cannot be negative!");
		}
		
		stmt.setInt(1, CID);
		stmt.setString(2, size);
		stmt.setDouble(3, price);
		stmt.setInt(4, PID);
		stmt.executeUpdate();
		worldNum++;
		
		stmt.close();
		closeConn(conn);
	}
	
	public static void recalculatePizzaPrice(int PID) throws SQLException{
		//Recalculate the pizza's price in the database
		Pizza pizza = getPizza(PID);
		ArrayList<Option> toppings = pizza.getToppings();
		double price = toppings.stream().mapToDouble(Option::getPrice).sum() + pizza.getCrust().getPrice() +
				pizza.getSauce().getPrice() + Pizza.getSizePrice(pizza.getSize());
		
		updatePizzaPrice(pizza, price);
	}
	
	public static void removeCustomer(Customer customer) throws SQLException{
		//Remove a customer from the database
		removeCustomer(customer.getCID());
	}
	
	public static void removeCustomer(int CID) throws SQLException{
		//Remove a customer from the database
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
		//Remove an available option from the database
		removeAvailableOption(option.getOID());
	}
	
	public static void removeAvailableOption(int OID) throws SQLException{
		//Remove an available option from the database
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
		//Remove a used option from the database
		removeUsedOption(option.getOID(), pizza.getPID());
	}
	
	public static void removeUsedOption(int OID, int PID) throws SQLException{
		//Remove a used option from the database
		Connection conn = createConn();
		Statement stmt = conn.createStatement();
		
		stmt.executeUpdate("DELETE FROM UsedOption WHERE OptionID=%d AND PizzaID=%d".formatted(OID, PID));
		worldNum++;
		
		stmt.close();
		closeConn(conn);
	}
	
	public static void removePizza(Pizza pizza) throws SQLException{
		//Remove a pizza from the database
		removePizza(pizza.getPID());
	}
	
	public static void removePizza(int PID) throws SQLException{
		//Remove a pizza from the database
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
