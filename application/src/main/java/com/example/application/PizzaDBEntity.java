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
	
	public abstract String toString();
}
