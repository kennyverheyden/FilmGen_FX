package main;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class ControllerOptionWords {

	@FXML
	private Button backToMain;

	@FXML
	private Label btnAdd;

	@FXML
	private Button btnDelete;

	@FXML
	private Button export;

	@FXML
	private GridPane gridPaneOptionWords;

	@FXML
	private ListView<?> listview;

	@FXML
	private AnchorPane optionWordsPane;

	@FXML
	void btnExport(ActionEvent event) {

	}

	@FXML
	void onClickAdd(MouseEvent event) {

	}

	@FXML
	void onClickDelete(ActionEvent event) {

	}

	@FXML
	void onClickToMain(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("guiHome.fxml"));
		optionWordsPane.getChildren().setAll(pane); // load in same window
	}

}
