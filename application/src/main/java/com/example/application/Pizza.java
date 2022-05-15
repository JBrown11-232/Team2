//Written by Josh Brown

package com.example.application;

import java.util.ArrayList;

// Requirement 7 inheritance
public class Pizza extends PizzaDBEntity{
	final private int PID;
	final private Customer customer;
	final private String size;
	final private Option crust;
	final private Option sauce;
	final private ArrayList<Option> toppings;
	final private double price;
	final static private double SMALL_PRICE = 3.50;
	final static private double MEDIUM_PRICE = 5.00;
	final static private double LARGE_PRICE = 6.00;
	
	public Pizza(int PID, Customer customer, String size, Option crust, Option sauce,
				 ArrayList<Option> toppings, double price, int worldNum){
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
}
