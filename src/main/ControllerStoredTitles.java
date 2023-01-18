package main;

import classes.FilmTitle;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class ControllerStoredTitles {

	@FXML
	private Button backToMain;

	@FXML
	private AnchorPane storedTitlesPane;

	@FXML
	private GridPane gridPaneGenerateTitle;

	@FXML
	private TableColumn<?, ?> storedTitles;
	
	FilmTitle title = new FilmTitle();
	
	public void initialize()
	{
		ArrayList<String> content =  title.readStoredTitle();
		
	}
	


	@FXML
	void onClickToMain(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("guiHome.fxml"));
		storedTitlesPane.getChildren().setAll(pane); // load in same window
	}

}
