//TODO relink creation methods

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
	public static Scene IPS;
	public static Scene IOS;
	public static Scene ICS;
	public static Scene UPS;
	public static Scene UOS;
	public static Scene UCS;
	public static Scene Home;
	public static Scene Help;
	private static Stage primaryStage;
	
	public static void main(String[] args){
		PizzaDBManager.createDB();
		launch(args);
	}
	
	public void start(Stage primaryStage) throws SQLException{
		DuesPizzaApplication.primaryStage = primaryStage;
		
		Home = HomeScene.createHomeScene();
		createHelp();
		IPS = InsertPizzaScene.createInsertPizzaScene();
		createInsertOptionScene();
		ICS = InsertCustomerScene.createInsertCustomerScene();
		createUpdatePizzaScene();
		createUpdateOptionScene();
		createUpdateCustomerScene();
		
		primaryStage.setScene(Home);
		primaryStage.setTitle("Due's Pizza Application");
		primaryStage.show();
	}
	
	public static MenuBar createMenuBar(){
		MenuItem homeItem = new MenuItem("Home Page");
		MenuItem helpItem = new MenuItem("Help Menu");
		MenuItem IP = new MenuItem("Insert Pizza");
		MenuItem IO = new MenuItem("Insert Option");
		MenuItem IC = new MenuItem("Insert Customer");
		MenuItem UP = new MenuItem("Update Pizza");
		MenuItem UO = new MenuItem("Update Option");
		MenuItem UC = new MenuItem("Update Customer");
		Menu startMenu = new Menu("Start");
		Menu insertMenu = new Menu("Insert");
		Menu updateMenu = new Menu("Update");
		startMenu.getItems().setAll(homeItem, helpItem);
		insertMenu.getItems().setAll(IP, IO, IC);
		updateMenu.getItems().setAll(UP, UO, UC);
		MenuBar menuBar = new MenuBar(startMenu, insertMenu, updateMenu);
		homeItem.setOnAction(event -> primaryStage.setScene(Home));
		helpItem.setOnAction(event -> primaryStage.setScene(Help));
		IP.setOnAction(event -> primaryStage.setScene(IPS));
		IO.setOnAction(event -> primaryStage.setScene(IOS));
		IC.setOnAction(event -> primaryStage.setScene(ICS));
		UP.setOnAction(event -> primaryStage.setScene(UPS));
		UO.setOnAction(event -> primaryStage.setScene(UOS));
		UC.setOnAction(event -> primaryStage.setScene(UCS));
		return menuBar;
	}
	
	private void createHelp(){
		MenuBar menuBar = createMenuBar();
		VBox root = new VBox(menuBar, new Label("Help"));
		Help = new Scene(root);
	}
	
	private void createInsertOptionScene(){
		MenuBar menuBar = createMenuBar();
		VBox root = new VBox(menuBar, new Label("Insert Option"));
		IOS = new Scene(root);
	}
	
	private void createUpdatePizzaScene(){
		MenuBar menuBar = createMenuBar();
		VBox root = new VBox(menuBar, new Label("Update Pizza"));
		UPS = new Scene(root);
	}
	
	private void createUpdateOptionScene(){
		MenuBar menuBar = createMenuBar();
		VBox root = new VBox(menuBar, new Label("Update Option"));
		UOS = new Scene(root);
	}
	
	private void createUpdateCustomerScene(){
		MenuBar menuBar = createMenuBar();
		VBox root = new VBox(menuBar, new Label("Update Customer"));
		UCS = new Scene(root);
	}
}
