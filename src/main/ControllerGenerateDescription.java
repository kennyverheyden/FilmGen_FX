package main;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class ControllerGenerateDescription {

    @FXML
    private Button backToMain;

    @FXML
    private Text descriptionText;

    @FXML
    private AnchorPane generateDescriptionPane;

    @FXML
    private Button generatedescription;

    @FXML
    private GridPane gridPaneGenerateDescription;

    @FXML
    private Button saveToDB;

    @FXML
    void onClickGenerateDescription(ActionEvent event) {

    }

    @FXML
    void onClickSaveToDB(ActionEvent event) {

    }

    @FXML
    public void onClickToMain(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("guiHome.fxml"));
		generateDescriptionPane.getChildren().setAll(pane); // load in same window
    }

}

