//Written by Josh Brown

package com.example.application;

// Requirement 7 inheritance
public class Customer extends PizzaDBEntity{
	private int CID;
	private String name;
	private String address;
	private String phoneNumber;
	
	public Customer(int CID, String name, String address, String phoneNumber, int worldNum){
		//Constructor to set all attributes
		super(worldNum);
		this.CID = CID;
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}
	
	//Getters
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
	
	//Convenience methods
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

	// this section written by Eric Hill
	//setter/update methods for the customer attributes

	public String updateCustomerCID(int newCID){
		this.CID = newCID;
		return "New Customer ID updated to " + this.CID;
	}
	public String updateCustomerName(String newName){
		this.name = newName;
		return "New Customer name updated to " + this.name;
	}
	public String updateCustomerAddress(String newAddress){
		this.address = newAddress;
		return "New Customer adddress updated to " + this.address;
	}
	public String updateCustomerPhoneNumber(String newPhoneNumber){
		this.phoneNumber = newPhoneNumber;
		return "New Customer phone number updated to " + this.phoneNumber;
	}

	public String updateCustomer(int newCID, String newName, String newAddress, String newPhoneNumber){
		this.CID = newCID;
		this.name = newName;
		this.address = newAddress;
		this.phoneNumber = newPhoneNumber;
		return "New customer information updated to:  " + this.CID + ", " + this.name + ", " + this.address + ", " + this.phoneNumber;
	}
}
