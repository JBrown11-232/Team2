//Written by Josh Brown

package com.example.application;

public abstract class PizzaDBEntity{
	final private int worldNum;
	
	public PizzaDBEntity(int worldNum){
		this.worldNum = worldNum;
	}
	
	public int getWorldNum(){
		return worldNum;
	}
	
	public abstract boolean isPizza();
	
	public abstract boolean isOption();
	
	public abstract boolean isCustomer();
	
	public abstract String getEntityType();
	
	public abstract String toString();
}
