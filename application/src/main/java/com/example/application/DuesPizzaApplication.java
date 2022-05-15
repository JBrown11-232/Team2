//Written by Josh Brown

package com.example.application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;

public class DuesPizzaApplication extends Application{
	//Make attributes for scenes, stage, scene dims
	public static Scene IPS;
	public static Scene ICS;
	public static Scene UOS;
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
	
	public void start(Stage primaryStage) throws SQLException{
		//Set stage attribute and scene attributes
		DuesPizzaApplication.primaryStage = primaryStage;
		
		HomeScene = Home.createHomeScene();
		HelpScene = Help.createHelpScene();
		IPS = InsertPizzaScene.createInsertPizzaScene();
		ICS = InsertCustomerScene.createInsertCustomerScene();
		//TODO link to eric's scene
		createUpdateOptionScene();
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
		MenuItem UO = new MenuItem("Update Option");
		MenuItem TO = new MenuItem("Total Owed");
		Menu startMenu = new Menu("Start");
		Menu insertMenu = new Menu("Create New...");
		Menu updateMenu = new Menu("Update...");
		Menu reviewMenu = new Menu("Review Order...");
		//Add MenuItems to Menus
		startMenu.getItems().setAll(homeItem, helpItem);
		insertMenu.getItems().setAll(IP, IC);
		updateMenu.getItems().setAll(UO);
		reviewMenu.getItems().setAll(TO);
		//Add Menus to a MenuBar
		MenuBar menuBar = new MenuBar(startMenu, insertMenu, updateMenu, reviewMenu);
		//Set event handlers
		homeItem.setOnAction(event -> primaryStage.setScene(HomeScene));
		helpItem.setOnAction(event -> primaryStage.setScene(HelpScene));
		IP.setOnAction(event -> primaryStage.setScene(IPS));
		IC.setOnAction(event -> primaryStage.setScene(ICS));
		UO.setOnAction(event -> primaryStage.setScene(UOS));
		TO.setOnAction(event -> primaryStage.setScene(TOS));
		return menuBar;
	}
	
	//TODO DESTROY AFTER LINKING
	private void createUpdateOptionScene(){
		MenuBar menuBar = createMenuBar();
		VBox root = new VBox(menuBar, new Label("Update Option"));
		UOS = new Scene(root, SCENEWIDTH, SCENEHEIGHT);
	}
}
