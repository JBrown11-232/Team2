//Written by Josh Brown

package com.example.application;

// Requirement 7 inheritance
public class Customer extends PizzaDBEntity{
	final private int CID;
	final private String name;
	final private String address;
	final private String phoneNumber;
	
	public Customer(int CID, String name, String address, String phoneNumber, int worldNum){
		super(worldNum);
		this.CID = CID;
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}
	
	public int getCID(){
		return CID;
	}
	
	public String getName(){
		return name;
	}
	
	public String getAddress(){
		return address;
	}
	
	public String getPhoneNumber(){
		return phoneNumber;
	}
	
	@Override
	public boolean isPizza(){
		return false;
	}
	
	@Override
	public boolean isOption(){
		return false;
	}
	
	@Override
	public boolean isCustomer(){
		return true;
	}
	
	@Override
	public String getEntityType(){
		return "Customer";
	}
	
	public boolean equals(Customer other){
		if(this.getWorldNum() != other.getWorldNum()){
			System.out.println("WARNING: Comparing 2 Customers of different worlds!");
			return this.CID==other.CID && this.name.equals(other.name)
					&& this.address.equals(other.address) && this.phoneNumber.equals(other.phoneNumber);
		}
		return this.CID == other.CID;
	}
	
	@Override
	public String toString(){
		return "CID: %d; Name: %s; Address: %s; Phone Number: %s".formatted(CID, name, address, phoneNumber);
	}
}
