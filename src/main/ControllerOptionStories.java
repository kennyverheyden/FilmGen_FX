package main;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class ControllerOptionStories {

	@FXML
	private Button backToMain;

	@FXML
	private Button btnAdd;

	@FXML
	private Button btnDelete;

	@FXML
	private GridPane gridPaneOptionStories;

	@FXML
	private ListView<?> listview;

	@FXML
	private AnchorPane optionStoriesPane;

	@FXML
	void onClickAdd(ActionEvent event) {

	}

	@FXML
	void onClickDelete(ActionEvent event) {

	}

	@FXML
	void onClickToMain(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("guiHome.fxml"));
		optionStoriesPane.getChildren().setAll(pane); // load in same window
	}

}
