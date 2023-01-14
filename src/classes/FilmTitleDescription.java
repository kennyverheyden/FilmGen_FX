package classes;

import java.util.ArrayList;

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

	// Show the generated film title and description
	public void showFormattedTitleDescription()
	{
		System.out.println("\n    Generated film:");
		printFormattingLine(generatedDescription.length());					// Dynamic line as long as the description
		System.out.println("    Title:\n    "+generatedTitle);				// Print the title
		printFormattingLine(generatedTitle.length());						// Dynamic line as long as the title
		System.out.println("    Description:\n    "+generatedDescription);	// Print the description
		printFormattingLine(generatedDescription.length());					// Dynamic line as long as the description
		titleDescriptionOptions();
	}

	// User generate new title
	private void generateNewTitle()
	{
		this.generatedTitle=title.generateTitle();
	}

	// User generate new description
	private void generateNewDescription()
	{
		this.generatedDescription=description.generateDescription();
	}

	// Generated film options, regenerate and store in DB)
	private void titleDescriptionOptions()
	{
		System.out.println("");
		System.out.println("    [1] Generate another film");
		System.out.println("    [2] Generate another title");
		System.out.println("    [3] Generate another description");
		System.out.println("    [4] Save the generated film");
		System.out.println("\n    Press just enter for main menu");
		System.out.println("");
		System.out.print("    Choice: ");
		String userChoice = userInput.nextLine().toLowerCase();

		switch(userChoice) {
		case "1":
			// Generate another film (title and description)
			generateNewTitle();
			generateNewDescription();
			this.showFormattedTitleDescription();
			break;
		case "2":
			// Generate another title
			generateNewTitle();
			this.showFormattedTitleDescription();
			break;
		case "3":
			// Generate another description
			generateNewDescription();
			this.showFormattedTitleDescription();
			break;
		case "4":
			// Save film to the database
			this.storeGeneratedFilmDescription();
			break;
		default:
			break;
		}
	}

	// Save generated film to the database
	private void storeGeneratedFilmDescription() {
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
			System.out.println("\n    Film saved");
		}
		else
		{
			System.out.println("\n    Film not saved due problem with database");
		}
		pressKeyToContinue();
	}

	// Print a list of the stored films to the user
	public void readStoredTitleDescription()
	{

		ArrayList<String> keys = myDBConnection.getFilmForeignKeys();  	// Contains Primary Key and foreign keys from database
		ArrayList<String> films = new ArrayList<>(); 					// Here we will store the merged film titles and descriptions
		ArrayList<String> titles = new ArrayList<>();					// Here we will store the merged titles only, we use this for formatting (title length for line)
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
			String mergedFilm=String.format("    %5d "+ mergedTitle+"\n    Description: "+ capitalize(articleWord(hyperbolic)) +" " + hyperbolic +" "+ story +" of "+ subject1 +" and "+ subject2 + " who must "+ verb + " " + subject3 + " in " + location,(i+1)); 
			films.add(mergedFilm); // Add film to ArrayList
			titles.add(mergedTitle); // For formatting to know the length of the title for the divider line
		}

		// Print the films from the ArrayList
		System.out.println("");
		if(!keys.isEmpty())
		{
			System.out.println("    Stored generated films (title + description):");
		}
		else
		{
			System.out.println("    Nothing saved");
		}
		System.out.println("");
		for(int i=0;i<films.size();i++)
		{
			if(i==0)
			{
				// Dynamic line - Get size for line by largest word length in ArrayList
				printFormattingLine(getSizeLargestWord(films)-getSizeLargestWord(titles)-11);
			}
			System.out.println(films.get(i));
			// Dynamic line - Get size for line by largest word length in ArrayList
			printFormattingLine(getSizeLargestWord(films)-getSizeLargestWord(titles)-11);

		}
		System.out.println("");

		// Show options to the user
		String userChoice;
		System.out.println("    [1] Delete a film");
		System.out.println("    [2] Save to file");
		System.out.println("\n    Press just enter for main menu");
		System.out.println("");
		System.out.print("    Choice: ");
		userChoice=userInput.nextLine().toLowerCase();
		switch(userChoice) {
		case "1":
			filmTitleDes.deleteItem(pkListFilm);
			break;
		case "2":
			writeToFile(objName,films);
			break;
		default:
			break;
		}
	}

}