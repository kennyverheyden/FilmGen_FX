package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import classes.DBConnect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.StageStyle;

public class ControllerOptionHyperbolics {

	@FXML
	private Button backToMain;

	@FXML
	private Button btnAdd;

	@FXML
	private Button btnDelete;

	@FXML
	private GridPane gridPaneOptionHyperbolics;

	@FXML
	private ListView<String> listview;

	@FXML
	private AnchorPane optionHyperbolicsPane;
	
	DBConnect myDBConnection = new DBConnect();

	ArrayList<String> content = new ArrayList<>(myDBConnection.getHyperbolics());

	public void initialize()
	{
		// Puts the titles contained in an ArrayList in a list
		listview.getItems().addAll(content);
		listview.setStyle("-fx-font-family: monospace; -fx-font-weight: bold;");
	}

	@FXML
	void onClickAdd(ActionEvent event) {
		myDBConnection.askInsertContent("hyperbolic","hyperbolic");	// Parameters tableName, columnName
		// Reload listview
		ArrayList<String> content = new ArrayList<>(myDBConnection.getHyperbolics());
		listview.getItems().clear();
		listview.getItems().addAll(content);
		listview.setStyle("-fx-font-family: monospace; -fx-font-weight: bold;");
	}

	@FXML
	void onClickDelete(ActionEvent event) {
		int id=listview.getSelectionModel().getSelectedIndex();
		myDBConnection.askDeleteContent("hyperbolic","hyperbolic",id);		// Parameters tableName columnName
		// Reload listview
				ArrayList<String> content = new ArrayList<>(myDBConnection.getHyperbolics());
				listview.getItems().clear();
				listview.getItems().addAll(content);
				listview.setStyle("-fx-font-family: monospace; -fx-font-weight: bold;");
	}
	
	@FXML
	void onClickBack(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/guiOptions.fxml"));
		optionHyperbolicsPane.getChildren().setAll(pane); // load in same window
	}

	@FXML
	void onClickToMain(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/guiHome.fxml"));
		optionHyperbolicsPane.getChildren().setAll(pane); // load in same window
	}

}
