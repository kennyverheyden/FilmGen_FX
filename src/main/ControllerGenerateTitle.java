package main;

import classes.FilmTitle;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class ControllerGenerateTitle {

    @FXML
    private Button btnBackToMain;

    @FXML
    private AnchorPane generateTitlePane;

    @FXML
    private Button btnGeneratetitle;

    @FXML
    private GridPane gridPaneGenerateTitle;

    @FXML
    private Button btnSaveToDB;

    @FXML
    private Text titleText;

    private FilmTitle title = new FilmTitle(); // Create object
	
	public void initialize()
	{
		titleText.setText(title.getGeneratedTitle());
	}
    
    @FXML
    void onClickGenerateTitle(ActionEvent event) {
    	titleText.setText(title.generateTitle());
    }

    @FXML
    void onClickSaveToDB(ActionEvent event) {

    }

    @FXML
    public void onClickToMain(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("guiHome.fxml"));
		generateTitlePane.getChildren().setAll(pane); // load in same window
    }

}

