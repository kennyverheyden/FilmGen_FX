package main;

import classes.FilmTitle;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
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
	private TableColumn<?, ?> storedTitles;
	
    @FXML
    private TableView<String> tableTitles;
	
	FilmTitle title = new FilmTitle();
	private ObservableList<String> titleList = FXCollections.observableArrayList();
	
	public void initialize()
	{
		//titleList =  (ObservableList<title>) title.readStoredTitle();
		
		tableTitles.setItems(titleList);
		
		//storedTitles.setCellFactory(rowValue -> rowValue.getValue().);
	}
	


	@FXML
	void onClickToMain(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("guiHome.fxml"));
		storedTitlesPane.getChildren().setAll(pane); // load in same window
	}

}
