package classes;

import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class FilmTitleDescription extends Film{

	FilmTitle title = new FilmTitle(); 						// Title logic
	FilmDescription description = new FilmDescription(); 	// Description logic
	private String objName; 								// Used for file name, ..
	private String generatedTitle;							// Store generated title
	private String generatedDescription; 					// Store generated description

	// Constructor
	public FilmTitleDescription()
	{
		generatedTitle=title.getGeneratedTitle();
		generatedDescription=description.getGeneratedDescription();
		objName="Film";
	}

	// Delete a record in the database
	@Override
	public boolean executeDelete(int databasePrimaryKey)
	{
		boolean success=myDBConnection.deleteFilm(databasePrimaryKey);
		return success;
	}

	// User generate new title
	public void generateNewTitle()
	{
		this.generatedTitle=title.generateTitle();
	}

	// User generate new description
	public void generateNewDescription()
	{
		this.generatedDescription=description.generateDescription();
	}

	// Save generated film to the database
	public void storeGeneratedFilmDescription() {
		// Ask genre to assign
		int userChoiceGenre=assignGenre();

		// Get foreign key from object instances
		int fkOfWord1=title.getfkOfWord();
		int fkOfWord2=title.getfkOfWord_2();
		int fkOfhyperbolic=description.getfkOfhyperbolic();
		int fkOfStory=description.getfkOfStory();
		int fkOfSubject1=description.getfkOfSubject1();
		int fkOfSubject2=description.getfkOfSubject2();
		int fkOfVerb=description.getfkOfVerb();
		int fkOfSubject3=description.getfkOfSubject3();
		int fkOfLocation=description.getfkOfLocation();

		boolean success=myDBConnection.insertFilmIndex(userChoiceGenre, fkOfWord1, fkOfWord2, fkOfhyperbolic, fkOfStory, fkOfSubject1, fkOfSubject2, fkOfVerb, fkOfSubject3, fkOfLocation);
		if(success)
		{
			Alert msg = new Alert(AlertType.INFORMATION);
			msg.setHeaderText("Film saved");
			msg.setTitle("Saved");
			msg.showAndWait();
		}
		else
		{
			Alert msg = new Alert(AlertType.ERROR);
			msg.setHeaderText("Film not saved due problem with database");
			msg.setTitle("Error");
			msg.showAndWait();
		}
	}

	// Print a list of the stored films to the user
	public static ArrayList<String> readStoredTitleDescription()
	{

		ArrayList<String> keys = myDBConnection.getFilmForeignKeys();  	// Contains Primary Key and foreign keys from database
		ArrayList<String> films = new ArrayList<>(); 					// Here we will store the merged film titles and descriptions
		FilmTitleDescription filmTitleDes = new FilmTitleDescription(); // Create obj for calling delete method in parent class
		ArrayList<Integer> pkListFilm = new ArrayList<Integer>(); 		// Here we store primary keys for the delete option
		// Merge
		for(int i=0;i<keys.size();i++)
		{
			String[] parts = keys.get(i).split(" "); 		// Retrieve a record and split to array by space
			pkListFilm.add(Integer.parseInt(parts[0])); 	// Primary key

			// Here we merge to one complete film in template
			// The index number is the foreign key number stored in the parts array
			String genre=capitalize(myDBConnection.getCategoryByFK(Integer.parseInt(parts[1])));
			String word1=capitalize(myDBConnection.getWordByFK(Integer.parseInt(parts[2])));
			String word2=capitalize(myDBConnection.getWordByFK(Integer.parseInt(parts[3])));
			String hyperbolic=myDBConnection.getHyperbolicByFK(Integer.parseInt(parts[4]));
			String story=myDBConnection.getStoryByFK(Integer.parseInt(parts[5]));
			String subject1=myDBConnection.getSubjectByFK(Integer.parseInt(parts[6]));
			String subject2=myDBConnection.getSubjectByFK(Integer.parseInt(parts[7]));
			String verb=myDBConnection.getVerbByFK(Integer.parseInt(parts[8]));
			String subject3=myDBConnection.getSubjectByFK(Integer.parseInt(parts[9]));
			String location=myDBConnection.getLocationByFK(Integer.parseInt(parts[10]));
			// Merge
			String mergedTitle=String.format("Genre: %-12s | Film: "+ word1 +" "+word2,genre);
			String mergedFilm=String.format("%5d "+ mergedTitle+"\n      Description: "+ capitalize(articleWord(hyperbolic)) +" " + hyperbolic +" "+ story +" of "+ subject1 +" and "+ subject2 + " who must "+ verb + " " + subject3 + " in " + location,(i+1)); 
			films.add(mergedFilm); // Add film to ArrayList
		}
		return films;
	}

	public String getGeneratedTitle() {
		return generatedTitle;
	}

	public String getGeneratedDescription() {
		return generatedDescription;
	}
	
	public String getObjName() {
		return objName;
	}

}