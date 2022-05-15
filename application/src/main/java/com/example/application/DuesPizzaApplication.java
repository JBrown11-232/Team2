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

public class DuesPizzaApplication extends Application{
	public static Scene IPS;
	public static Scene ICS;
	public static Scene UOS;
	public static Scene HomeScene;
	public static Scene HelpScene;
	public static Scene ROS;
	private static Stage primaryStage;
	final public static int SCENEHEIGHT = 600;
	final public static int SCENEWIDTH = 1000;
	
	public static void main(String[] args){
		PizzaDBManager.createDB();
		launch(args);
	}
	
	public void start(Stage primaryStage){
		DuesPizzaApplication.primaryStage = primaryStage;
		
		HomeScene = Home.createHomeScene();
		HelpScene = Help.createHelpScene();
		IPS = InsertPizzaScene.createInsertPizzaScene();
		ICS = InsertCustomerScene.createInsertCustomerScene();
		//TODO link to eric's scene
		createUpdateOptionScene();
		//TODO line to connor's scene
		createReviewOrderScene();
		
		primaryStage.setScene(HomeScene);
		primaryStage.setTitle("Due's Pizza Application");
		primaryStage.show();
	}
	
	public static MenuBar createMenuBar(){
		MenuItem homeItem = new MenuItem("Home Page");
		MenuItem helpItem = new MenuItem("Help Menu");
		MenuItem IP = new MenuItem("Create Pizza");
		MenuItem IC = new MenuItem("Create Customer");
		MenuItem UO = new MenuItem("Update Option");
		MenuItem RO = new MenuItem("Review Order");
		Menu startMenu = new Menu("Start");
		Menu insertMenu = new Menu("Create New...");
		Menu updateMenu = new Menu("Update...");
		Menu reviewMenu = new Menu("Review...");
		startMenu.getItems().setAll(homeItem, helpItem);
		insertMenu.getItems().setAll(IP, IC);
		updateMenu.getItems().setAll(UO);
		reviewMenu.getItems().setAll(RO);
		MenuBar menuBar = new MenuBar(startMenu, insertMenu, updateMenu, reviewMenu);
		homeItem.setOnAction(event -> primaryStage.setScene(HomeScene));
		helpItem.setOnAction(event -> primaryStage.setScene(HelpScene));
		IP.setOnAction(event -> primaryStage.setScene(IPS));
		IC.setOnAction(event -> primaryStage.setScene(ICS));
		UO.setOnAction(event -> primaryStage.setScene(UOS));
		RO.setOnAction(event -> primaryStage.setScene(ROS));
		return menuBar;
	}
	
	private void createUpdateOptionScene(){
		MenuBar menuBar = createMenuBar();
		VBox root = new VBox(menuBar, new Label("Update Option"));
		UOS = new Scene(root, SCENEWIDTH, SCENEHEIGHT);
	}
	
	private void createReviewOrderScene(){
		MenuBar menuBar = createMenuBar();
		VBox root = new VBox(menuBar, new Label("Review Order"));
		ROS = new Scene(root, SCENEWIDTH, SCENEHEIGHT);
	}
}
