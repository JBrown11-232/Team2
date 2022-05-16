//Written by Josh Brown

package com.example.application;

import java.util.ArrayList;

// Requirement 7 inheritance
public class Pizza extends PizzaDBEntity{
	private int PID;
	private Customer customer;
	private String size;
	private Option crust;
	private Option sauce;
	final private ArrayList<Option> toppings;
	private double price;
	//Define base prices of each size
	static private double SMALL_PRICE = 3.50;
	static private double MEDIUM_PRICE = 5.00;
	static private double LARGE_PRICE = 6.00;

	
	public Pizza(int PID, Customer customer, String size, Option crust, Option sauce,
				 ArrayList<Option> toppings, double price, int worldNum){
		//Constructors to set all attributes
		super(worldNum);
		if(customer == null){
			throw new RuntimeException("Every pizza must have a customer!");
		}
		if(crust==null || sauce==null){
			throw new RuntimeException("Every pizza must have a crust and sauce!");
		}
		if(!toppings.isEmpty() && !toppings.stream().map(Option::isTopping).reduce(true, Boolean::logicalAnd)){
			throw new RuntimeException("Toppings ArrayList may only contain toppings!");
		}
		this.PID = PID;
		this.customer = customer;
		this.size = size;
		this.crust = crust;
		this.sauce = sauce;
		this.toppings = new ArrayList<>(toppings);
		this.price = price;
	}
	
	public Pizza(int PID, Customer customer, String size, ArrayList<Option> usedOptions, double price, int worldNum){
		//Alternate constructor
		super(worldNum);
		this.PID = PID;
		this.customer = customer;
		this.size = size;
		Option crust = null;
		Option sauce = null;
		ArrayList<Option> toppings = new ArrayList<>();
		for(Option option : usedOptions){
			// Requirement 5 switch statement
			switch(option.getOptionType()){
				case "C" -> {
					if(crust != null){
						throw new RuntimeException("A Pizza may only have 1 crust!");
					}
					crust = option;
				}
				case "S" -> {
					if(sauce != null){
						throw new RuntimeException("A Pizza may only have 1 sauce!");
					}
					sauce = option;
				}
				case "T" -> toppings.add(option);
			}
		}
		if(crust==null || sauce==null){
			throw new RuntimeException("Every pizza must have a crust and sauce!");
		}
		this.crust = crust;
		this.sauce = sauce;
		this.toppings = toppings;
		this.price = price;
	}
	
	//Getters
	public int getPID(){
		return PID;
	}
	public Customer getCustomer(){
		return customer;
	}
	public String getSize(){
		return size;
	}
	public Option getCrust(){
		return crust;
	}
	public Option getSauce(){
		return sauce;
	}
	public ArrayList<Option> getToppings(){
		return new ArrayList<>(toppings);
	}
	public double getPrice(){
		return price;
	}
	
	//Convenience methods
	@Override
	public boolean isPizza(){
		return true;
	}
	
	@Override
	public boolean isOption(){
		return false;
	}
	
	@Override
	public boolean isCustomer(){
		return false;
	}
	
	@Override
	public String getEntityType(){
		return "Pizza";
	}
	
	public String getSizeName(){
		return switch(size){
			case "S" -> "Small";
			case "M" -> "Medium";
			case "L" -> "Large";
			default -> throw new RuntimeException("WARNING: Can't understand size " + size + "!");
		};
	}
	
	public int getCID(){
		return customer.getCID();
	}
	
	public static double getSizePrice(String size){
		return switch(size){
			case "S" -> SMALL_PRICE;
			case "M" -> MEDIUM_PRICE;
			case "L" -> LARGE_PRICE;
			default -> throw new RuntimeException("WARNING: Can't find price of size " + size + "!");
		};
	}
	
	public static String toppingsCommaDelimited(ArrayList<Option> toppings){
		StringBuilder stringBuilder = new StringBuilder(toppings.size()*10);
		for(Option option : toppings){
			if(!stringBuilder.isEmpty()){
				stringBuilder.append(", ");
			}
			stringBuilder.append(option.getName());
		}
		return stringBuilder.toString();
	}
	
	public boolean equals(Pizza other){
		if(this.getWorldNum() != other.getWorldNum()){
			System.out.println("WARNING: Comparing 2 Pizzas of different worlds!");
			return this.PID==other.PID && this.customer.equals(other.customer) &&
					this.size.equals(other.size) && this.crust.equals(other.crust) &&
					this.sauce.equals(other.sauce) && this.toppings.equals(other.toppings) &&
					this.price==other.price;
		}
		return this.PID == other.PID;
	}
	
	@Override
	public String toString(){
		return "PID: %d; CID: %d; Size: %s; Crust: %s; Sauce: %s; Toppings: %s; Price: $%.2f".formatted(
				PID, customer.getCID(), size, crust.getName(), sauce.getName(),
				toppingsCommaDelimited(toppings), price);
	}

	// this section written by Eric Hill
	//setter/update methods for the pizza attributes

	public String updatePizzaPID(int newPID){
		this.PID = newPID;
		return "new pizza PID updated to " + this.PID;
	}
	public String updatePizzaCustomer(Customer newCustomer){
		this.customer = newCustomer;
		return "new pizza customer updated to " + this.customer;
	}
	public String updatePizzasize(String newSize){
		this.size = newSize;
		return "new pizza size updated to " + this.size;
	}
	public String updatePizzaCrust(Option newCrust){
		this.crust = newCrust;
		return "new pizza crust updated to " + this.crust;
	}
	public String updatePizzaSauce(Option newSauce){
		this.sauce = newSauce;
		return "new pizza sauce updated to " + this.sauce;
	}
	public String updatePizzaPrice(double newPrice){
		this.price = newPrice;
		return "new pizza price updated to " + this.price;
	}
	public String updatePizzaSmallPrice(double newPrice){
		this.SMALL_PRICE = newPrice;
		return "new pizza small price updated to " + this.SMALL_PRICE ;
	}
	public String updatePizzaMediumPrice(double newPrice){
		this.MEDIUM_PRICE = newPrice;
		return "new pizza medium price updated to " + this.MEDIUM_PRICE ;
	}
	public String updatePizzaLargelPrice(double newPrice){
		this.LARGE_PRICE = newPrice;
		return "new pizza large price updated to " + this.LARGE_PRICE ;
	}

	public String updatePizza(int newPID, Customer newCustomer, String newSize, Option newCrust, Option newSauce, double newPrice){
		this.PID = newPID;
		this.customer = newCustomer;
		this.size = newSize;
		this.crust = newCrust;
		this.sauce = newSauce;
		this.price = newPrice;
		return "Pizza updated to: " + this.PID + ", " + this.customer + ", " + this.size + ", " + this.crust + ", " + this.sauce + ", " + this.price;
	}
}
