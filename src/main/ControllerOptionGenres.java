package main;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class ControllerOptionGenres {

	@FXML
	private Button backToMain;

	@FXML
	private Button btnAdd;

	@FXML
	private Button btnDelete;

	@FXML
	private GridPane gridPaneOptionGenres;

	@FXML
	private ListView<?> listview;

	@FXML
	private AnchorPane optionGenresPane;

	@FXML
	void onClickAdd(ActionEvent event) {

	}

	@FXML
	void onClickDelete(ActionEvent event) {

	}

	@FXML
	public void onClickToMain(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("guiHome.fxml"));
		optionGenresPane.getChildren().setAll(pane); // load in same window

	}

}
