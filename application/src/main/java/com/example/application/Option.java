//Written by Josh Brown

package com.example.application;

// Requirement 7 inheritance
public class Option extends PizzaDBEntity{
	final private int OID;
	final private String name;
	final private String type;
	final private double price;
	
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
}
