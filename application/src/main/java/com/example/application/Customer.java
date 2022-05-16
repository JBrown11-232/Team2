//Written by Josh Brown

package com.example.application;

import java.sql.SQLException;

import static com.example.application.PizzaDBManager.*;

// Requirement 7 inheritance
public class Customer extends PizzaDBEntity{
	final private int CID;
	final private String name;
	final private String address;
	final private String phoneNumber;
	
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
	//setter/update methods for the customer attributes in the database

	public String updateCustomername(String newName) throws SQLException {
		updateCustomerName(this.getCID(), newName);
		return "New Customer name updated to " + this.name;
	}
	public String updateCustomeraddress(String newAddress) throws SQLException {
		updateCustomerAddress(this.getCID(), newAddress);
		return "New Customer adddress updated to " + this.address;
	}
	public String updateCustomerPhonenumber(String newPhoneNumber) throws SQLException {
		updateCustomerPhoneNumber(this.getCID(), newPhoneNumber);
		return "New Customer phone number updated to " + this.phoneNumber;
	}

	public String updateCustomer(String newName, String newAddress, String newPhoneNumber) throws SQLException {
		updateCustomerName(this.getCID(), newName);
		updateCustomerAddress(this.getCID(), newAddress);
		updateCustomerPhoneNumber(this.getCID(), newPhoneNumber);
		return "New customer information updated to:  " + this.name + ", " + this.address + ", " + this.phoneNumber;
	}
}
