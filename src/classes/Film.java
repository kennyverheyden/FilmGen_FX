package classes;

import java.io.FileWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.StageStyle;
import javafx.scene.control.Alert.AlertType;

public abstract class Film {

	// PARENT CLASS

	// Initialize static Scanner for all child classes
	static Scanner userInput = new Scanner(System.in);

	// Make DB connection
	static DBConnect myDBConnection = new DBConnect();

	// Tables of DB loaded separately in ArrayLists
	static ArrayList<String> hyperbolics = myDBConnection.getHyperbolics();
	static ArrayList<String> locations = myDBConnection.getLocations();
	static ArrayList<String> stories = myDBConnection.getStories();
	static ArrayList<String> subjects = myDBConnection.getSubjects();
	static ArrayList<String> verbs = myDBConnection.getVerbs();
	static ArrayList<String> words = myDBConnection.getWords();

	// Constructor
	public Film(){	
	}

	// Delete item from Title or Description table
	public boolean deleteItem(ArrayList<Integer> pkList, int id) {
		int pk = pkList.get((id));
		if(executeDelete(pk))
		{
			return true;
		}
		else
		{
			return false;
		}		
	}

	// Random picker in the ArrayList
	public int randomPicker(ArrayList<String> obj)
	{
		Random rn = new Random();
		int randomNum = rn.nextInt(obj.size());
		return randomNum;
	}

	// Ask random genre (category) or choose a genre
	public int assignGenre()
	{
		int genre;

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText("Let the computer pick a random genre?");
		alert.setTitle("Genre");
		alert.getButtonTypes().remove(0,2);
		alert.getButtonTypes().add(0, ButtonType.YES);
		alert.getButtonTypes().add(1, ButtonType.NO);

		Optional<ButtonType> confirmationResponse = alert.showAndWait();
		if(confirmationResponse.get() ==ButtonType.YES)
		{
			genre=this.randomGenre();
		}
		else
		{
			genre=this.askGenre();
		}

		return Integer.parseInt(myDBConnection.getCategorie_ids().get(genre-1)); // Get PK from categories for foreign key
	}

	// Ask genre (category)
	public int askGenre()
	{
		// Prepare String of genres
		StringBuilder str = new StringBuilder();
		String msg;
		// Retrieve genres
		ArrayList<String> cat = myDBConnection.getCategories();

		// Make String of genres
		for(int i=0;i<cat.size();i++)
		{
			str.append((i+1)+ "="+capitalize(cat.get(i))+" ");
		}
		msg=str.toString();

		// Get choice
		boolean invalid = true;
		String choice = null;
		int userChoiceGenre=0;
		// As long as input is integer
		// Input choice is not 0, because PK in DB starts from 1
		// Input choice not bigger than highest PK in DB (Size arrayList categories)
		while(invalid || userChoiceGenre>cat.size() || userChoiceGenre<=0)
		{
			// Show genres and ask choice
			TextInputDialog inputdialog = new TextInputDialog("Enter choice number");
			inputdialog.setContentText("Choice: ");
			inputdialog.setHeaderText(msg);
			inputdialog.getDialogPane().lookupButton(ButtonType.CANCEL).setVisible(false);
			inputdialog.initStyle(StageStyle.UNDECORATED);
			inputdialog.getDialogPane().setMaxWidth(350); 
			inputdialog.showAndWait();
			choice=inputdialog.getEditor().getText(); 
			if(isInteger(choice))
			{
				userChoiceGenre = Integer.parseInt(choice); 
				invalid=false;
			}
		}
		return userChoiceGenre;
	}

	// Random genre (category)
	public int randomGenre()
	{
		ArrayList<String> cat = myDBConnection.getCategories();
		boolean confirmed = false; 				// check user confirmation
		int randomGenre = randomPicker(cat);	// Call method randomPicker for random int
		String input;
		while(!confirmed) // As long as the user not confirmed his choice
		{
			randomGenre = randomPicker(cat);

			// Ask for confirmation
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("Computer chose: "+cat.get(randomGenre)+", would you like to confirm?");
			alert.setTitle("Confirm");
			alert.getButtonTypes().remove(0,2);
			alert.getButtonTypes().add(0, ButtonType.YES);
			alert.getButtonTypes().add(1,ButtonType.NO);
			Optional<ButtonType> confirmationResponse = alert.showAndWait();
			if(confirmationResponse.get() == ButtonType.YES)
			{
				confirmed=true;
			}
		}
		return randomGenre+1; // +1 because array-size starts from 0 and Database starts from 1
	}

	// Check if a number is an Integer
	public static boolean isInteger(String s) {
		try { 
			Integer.parseInt(s); 
		} catch(NumberFormatException e) { 
			return false; 
		}
		// only got here if we didn't return false
		return true;
	}

	// Override method to use unique query for DB
	// Called from method deleteItem()
	public abstract boolean executeDelete(int databasePrimaryKey);

	// Define "an" article for a word with start with a vowel. Example: an average
	public static String articleWord(String word)
	{
		String article = null;
		char first = word.charAt(0);

		if(first=='a' || first=='e' || first=='i' || first=='o' || first=='u' || first=='y')
		{
			article="an";
		}
		else
		{
			article="a";
		}
		return article;
	}

	// Pause or stop the program till user input
	public static void pressKeyToContinue() {
		System.out.println("    Press enter to continue");
		userInput.nextLine();
	}

	// Capitalize the first letter of a String
	public static String capitalize(String str) {
		if(str == null || str.isEmpty()) {
			return str;
		}
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	// Save to file
	public void writeToFile(String name, ArrayList<String> list)
	{
		try {
			FileWriter writer = new FileWriter("FilmGen - "+name+"s.txt");
			writer.write("FilmGen - "+name+"s");
			writer.write(System.lineSeparator());
			writer.write(System.lineSeparator());
			for(int i=0;i<list.size();i++)
			{
				writer.write(list.get(i));
				writer.write(System.lineSeparator());
				writer.write(System.lineSeparator());
			}
			writer.close();
			Alert msg = new Alert(AlertType.INFORMATION);
			msg.setHeaderText("Successfully wrote to the file: FilmGen-"+name+"s.txt");
			msg.setTitle("Succes");
			msg.showAndWait();
		} catch (IOException e) {
			Alert msg = new Alert(AlertType.ERROR);
			msg.setHeaderText("An error occurred.");
			msg.setTitle("Error");
			msg.showAndWait();
			e.printStackTrace();
		}
	}

	// Close scanner
	public static void closeScanner()
	{
		userInput.close();
	}

}