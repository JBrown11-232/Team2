//Written by Josh Brown

package com.example.application;

// Requirement 7 inheritance
public class Option extends PizzaDBEntity{
	private int OID;
	private String name;
	private String type;
	private double price;

	public Option(int OID, String name, String type, double price, int worldNum){
		//Constructor to set all attributes
		super(worldNum);
		this.OID = OID;
		this.name = name;
		this.type = type;
		this.price = price;
	}
	
	//Getters
	public int getOID(){
		return OID;
	}
	public String getName(){
		return name;
	}
	public double getPrice(){
		return price;
	}
	public String getOptionType(){
		return type;
	}
	
	//Convenience methods
	public String getOptionTypeName(){
		return switch(type){
			case "C" -> "Crust";
			case "S" -> "Sauce";
			case "T" -> "Topping";
			default -> type;
		};
	}
	
	public boolean isCrust(){
		return type.equals("C");
	}
	
	public boolean isSauce(){
		return type.equals("S");
	}
	
	public boolean isTopping(){
		return type.equals("T");
	}
	
	@Override
	public boolean isPizza(){
		return false;
	}
	
	@Override
	public boolean isOption(){
		return true;
	}
	
	@Override
	public boolean isCustomer(){
		return false;
	}
	
	@Override
	public String getEntityType(){
		return "Option";
	}
	
	public boolean equals(Option other){
		if(this.getWorldNum() != other.getWorldNum()){
			System.out.println("WARNING: Comparing 2 Options of different worlds!");
			return this.OID==other.OID && this.name.equals(other.name)
					&& this.type.equals(other.type) && this.price==other.price;
		}
		return this.OID == other.OID;
	}
	
	@Override
	public String toString(){
		return "OID: %d; Name: %s; Type: %s; Price: $%.2f".formatted(OID, name, type, price);
	}


	// this section written by Eric Hill
	//setter/update methods for the option attributes

	public String updateOptionOID(int newOID){
		this.OID = newOID;
		return "New option ID updated to " + this.OID;
	}
	public String updateOptionName(String newName){
		this.name = newName;
		return "New option name updated to " + this.name;
	}
	public String updateOptionPrice(double newPrice){
		this.price = newPrice;
		return "New option price updated to $" + this.price;
	}
	public String updateOptionType(String newType){
		this.type = newType;
		return "New option type updated to " + this.type;
	}

	public String updateOption(int newOID, String newName, double newPrice, String newType){
		this.OID = newOID;
		this.name = newName;
		this.price = newPrice;
		this.type = newType;
		return "New option updated to:  " + this.OID + ", " + this.name + ", " + this.price + ", " + this.type;
	}


}
