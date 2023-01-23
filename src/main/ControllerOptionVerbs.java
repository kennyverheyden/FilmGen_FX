package main;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

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
	private ListView<?> listview;

	@FXML
	private AnchorPane optionVerbsPane;

	@FXML
	void onClickAdd(ActionEvent event) {

	}

	@FXML
	void onClickDelete(ActionEvent event) {

	}

	@FXML
	void onClickToMain(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("guiHome.fxml"));
		optionVerbsPane.getChildren().setAll(pane); // load in same window
	}

}
