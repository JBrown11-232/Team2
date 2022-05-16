//Written by Josh Brown

package com.example.application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class DuesPizzaApplication extends Application{
	//Make attributes for scenes, stage, scene dims
	public static Scene IPS;
	public static Scene ICS;
	public static Scene HomeScene;
	public static Scene HelpScene;
	public static Scene TOS;
	private static Stage primaryStage;
	final public static int SCENEHEIGHT = 600;
	final public static int SCENEWIDTH = 900;
	
	public static void main(String[] args){
		//Create DB and launch rest of program
		PizzaDBManager.createDB();
		launch(args);
	}
	
	public void start(Stage primaryStage){
		//Set stage attribute and scene attributes
		DuesPizzaApplication.primaryStage = primaryStage;
		
		HomeScene = Home.createHomeScene();
		HelpScene = Help.createHelpScene();
		IPS = InsertPizzaScene.createInsertPizzaScene();
		ICS = InsertCustomerScene.createInsertCustomerScene();
		TOS = FinalCost.createTotalOwed();
		
		//Set scene to home to start and show the application
		primaryStage.setScene(HomeScene);
		primaryStage.setTitle("Due's Pizza Application");
		primaryStage.show();
	}
	
	public static MenuBar createMenuBar(){
		//Method for creating the MenuBar to navigate through scenes
		//Make MenuItems and Menus
		MenuItem homeItem = new MenuItem("Home Page");
		MenuItem helpItem = new MenuItem("Help Menu");
		MenuItem IP = new MenuItem("Create Pizza");
		MenuItem IC = new MenuItem("Create Customer");
		MenuItem TO = new MenuItem("Total Owed");
		Menu startMenu = new Menu("Start");
		Menu insertMenu = new Menu("Create New...");
		Menu reviewMenu = new Menu("Review Order...");
		//Add MenuItems to Menus
		startMenu.getItems().setAll(homeItem, helpItem);
		insertMenu.getItems().setAll(IP, IC);
		reviewMenu.getItems().setAll(TO);
		//Add Menus to a MenuBar
		MenuBar menuBar = new MenuBar(startMenu, insertMenu, reviewMenu);
		//Set event handlers
		homeItem.setOnAction(event -> primaryStage.setScene(HomeScene));
		helpItem.setOnAction(event -> primaryStage.setScene(HelpScene));
		IP.setOnAction(event -> {
			primaryStage.setScene(IPS);
			InsertPizzaScene.reloadData();
		});
		IC.setOnAction(event -> primaryStage.setScene(ICS));
		TO.setOnAction(event -> {
			primaryStage.setScene(TOS);
			FinalCost.reloadData();
		});
		return menuBar;
	}
}
