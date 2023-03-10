package classes;

import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class FilmTitle extends Film{

	private String generatedTitle; 	// Store generated title
	private String objName; 		// Used for file name, ..

	// Index of elements for DB operations
	private int fkOfWord;
	private int fkOfWord_2;

	// Constructor
	public FilmTitle()
	{
		generatedTitle=this.generateTitle();
		objName="Title";
	}

	// Delete a record in the database
	@Override
	public boolean executeDelete(int databasePrimaryKey)
	{
		boolean success=myDBConnection.deleteTitle(databasePrimaryKey);
		return success;
	}

	// Generate title
	public String generateTitle()
	{
		String generatedTitle; // Generated title
		ArrayList<String> words = myDBConnection.getWords();
		// Assign random content to fields
		String word=words.get(randomPicker(words));
		fkOfWord=Integer.parseInt(myDBConnection.getWord_fks().get(words.indexOf(word)));

		String word2=words.get(randomPicker(words));
		// Check if word2 and word are duplicates
		boolean notDuplicate=false;
		while(!notDuplicate) {
			if(!word.equals(word2))
			{
				fkOfWord_2=Integer.parseInt(myDBConnection.getWord_fks().get(words.indexOf(word2)));
				notDuplicate=true;
			}
			else
			{
				notDuplicate=false;
				word2=words.get(randomPicker(words));
			}
		}

		//  Build the string with the fields in the template string
		generatedTitle= capitalize(word)+ " " + capitalize(word2); 

		return generatedTitle;
	}

	// Print a list of the stored titles to the user
	public static ArrayList<String> readStoredTitle()
	{
		ArrayList<String> keys = myDBConnection.getTitleForeignKeys(); 	// Contains Primary Key and foreign keys from database
		ArrayList<String> titles = new ArrayList<>(); 					// Here we will store the merged titles
		ArrayList<Integer> pkListTitle = new ArrayList<Integer>(); 		// Here we store primary keys for the delete option
		// Merge the titles
		for(int i=0;i<keys.size();i++)
		{
			String[] parts = keys.get(i).split(" ");		// Retrieve a record and split to array by space
			pkListTitle.add(Integer.parseInt(parts[0])); 	// Primary key

			// Here we merge to one complete title in template
			String genre=capitalize(myDBConnection.getCategoryByFK(Integer.parseInt(parts[1])));
			String word1 = capitalize(myDBConnection.getWordByFK(Integer.parseInt(parts[2])));
			String word2 = capitalize(myDBConnection.getWordByFK(Integer.parseInt(parts[3])));

			String mergedTitle=String.format("%3d Genre: %-12s | Title:  "+word1+ " " +word2,(i+1), genre); 
			titles.add(mergedTitle); // Add title to ArrayList
		}
		return titles;
	}

	// Save generated title to the database
	public void storeGeneratedTitle() {
		int userChoiceGenre=assignGenre(); // Ask genre to assign
		boolean success=myDBConnection.insertTitleIndex(userChoiceGenre, this.getfkOfWord(), this.getfkOfWord_2());
		if(success)
		{
			Alert msg = new Alert(AlertType.INFORMATION);
			msg.setHeaderText("Title saved");
			msg.setTitle("Saved");
			msg.showAndWait();
		}
		else
		{
			Alert msg = new Alert(AlertType.ERROR);
			msg.setHeaderText("Title not saved due problem with database");
			msg.setTitle("Error");
			msg.showAndWait();
		}
	}

	// Needed for FilmTitleDescription class
	public String getGeneratedTitle() {
		return generatedTitle;
	}

	public int getfkOfWord() {
		return fkOfWord;
	}

	public int getfkOfWord_2() {
		return fkOfWord_2;
	}
	
	public String getObjName() {
		return objName;
	}

}