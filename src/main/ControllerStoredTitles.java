package main;

import classes.FilmTitle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    private ListView<String> titleList;
	
	FilmTitle title = new FilmTitle();
	ArrayList<String> listedTitles = new ArrayList<>(FilmTitle.readStoredTitle());
	
	public void initialize()
	{
		
		// Puts the titles contained in an ArrayList in a list
		titleList.getItems().addAll(listedTitles);
		
		
	}
	


	@FXML
	void onClickToMain(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("guiHome.fxml"));
		storedTitlesPane.getChildren().setAll(pane); // load in same window
	}

}
