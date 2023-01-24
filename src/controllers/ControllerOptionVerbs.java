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

public class ControllerOptionVerbs {

	@FXML
	private Button backToMain;

	@FXML
	private Button btnAdd;

	@FXML
	private Button btnDelete;

	@FXML
	private GridPane gridPaneOptionVerbs;

	@FXML
	private ListView<String> listview;

	@FXML
	private AnchorPane optionVerbsPane;


	DBConnect myDBConnection = new DBConnect();

	ArrayList<String> content = new ArrayList<>(myDBConnection.getVerbs());

	public void initialize()
	{
		// Puts the titles contained in an ArrayList in a list
		listview.getItems().addAll(content);
		listview.setStyle("-fx-font-family: monospace; -fx-font-weight: bold;");
	}


	@FXML
	void onClickAdd(ActionEvent event) {
		myDBConnection.askInsertContent("verbs","verb");	// Parameters tableName, columnName
		// Reload listview
		ArrayList<String> content = new ArrayList<>(myDBConnection.getVerbs());
		listview.getItems().clear();
		listview.getItems().addAll(content);
		listview.setStyle("-fx-font-family: monospace; -fx-font-weight: bold;");
	}

	@FXML
	void onClickDelete(ActionEvent event) {
		int id=listview.getSelectionModel().getSelectedIndex();
		myDBConnection.askDeleteContent("verbs","verb",id);		// Parameters tableName columnName
		// Reload listview
		ArrayList<String> content = new ArrayList<>(myDBConnection.getVerbs());
		listview.getItems().clear();
		listview.getItems().addAll(content);
		listview.setStyle("-fx-font-family: monospace; -fx-font-weight: bold;");
	}

	@FXML
	void onClickBack(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/main/guiOptions.fxml"));
		optionVerbsPane.getChildren().setAll(pane); // load in same window
	}

	@FXML
	void onClickToMain(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/main/guiHome.fxml"));
		optionVerbsPane.getChildren().setAll(pane); // load in same window
	}

}
