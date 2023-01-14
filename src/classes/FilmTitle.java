package classes;

import java.util.ArrayList;

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
	public void readStoredTitle()
	{
		ArrayList<String> keys = myDBConnection.getTitleForeignKeys(); 	// Contains Primary Key and foreign keys from database
		ArrayList<String> titles = new ArrayList<>(); 					// Here we will store the merged titles
		FilmTitle filmTit = new FilmTitle(); 							// Create obj for calling delete method in parent class
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

			String mergedTitle=String.format("    %5d Genre: %-12s | Title:  "+word1+ " " +word2,(i+1), genre); 
			titles.add(mergedTitle); // Add title to ArrayList
		}

		// Print the titles from the ArrayList
		System.out.println("");
		if(!keys.isEmpty())
		{
			System.out.println("    Stored separately generated titles:");
		}
		else
		{
			System.out.println("    Nothing saved");
		}
		System.out.println("");
		for(int i=0;i<titles.size();i++)
		{
			if(i==0)
			{
				// Dynamic line - Get size for line by largest word length in ArrayList
				printFormattingLine(getSizeLargestWord(titles)-1);
			}
			System.out.println(titles.get(i));
			// Dynamic line - Get size for line by largest word length in ArrayList
			printFormattingLine(getSizeLargestWord(titles)-1);

		}
		System.out.println("");

		// Show options to the user
		String userChoice;
		System.out.println("    [1] Delete a title");
		System.out.println("    [2] Save to file");
		System.out.println("\n    Press just enter for main menu");
		System.out.println("");
		System.out.print("    Choice: ");
		userChoice=userInput.nextLine().toLowerCase();
		switch(userChoice) {
		case "1":
			filmTit.deleteItem(pkListTitle);
			break;
		case "2":
			writeToFile(objName,titles);
			break;
		default:
			break;
		}
	}

	// Print generated title to the user
	public void showFormattedTitle()
	{
		System.out.println("\n    Generated film title:");
		printFormattingLine(generatedTitle.length());		// Dynamic line as long as the title
		System.out.println("    "+generatedTitle);			// Print the title
		printFormattingLine(generatedTitle.length());
		titleOptions();										// What can the user do with the title
	}

	// Generated title options, regenerate and store in DB
	private void titleOptions()
	{
		System.out.println("");
		System.out.println("    [1] Generate another title");
		System.out.println("    [2] Save the generated title");
		System.out.println("\n    Press just enter for main menu");
		System.out.println("");
		System.out.print("    Choice: ");

		String userChoice = userInput.nextLine().toLowerCase();

		switch(userChoice) {
		case "1":
			this.generatedTitle=this.generateTitle();
			this.showFormattedTitle();
			break;
		case "2":
			this.storeGeneratedTitle();
			break;
		default:
			break;
		}
	}

	// Save generated title to the database
	private void storeGeneratedTitle() {
		int userChoiceGenre=assignGenre(); // Ask genre to assign
		boolean success=myDBConnection.insertTitleIndex(userChoiceGenre, this.getfkOfWord(), this.getfkOfWord_2());
		if(success)
		{
			System.out.println("\n    Title saved");
		}
		else
		{
			System.out.println("\n    Title not saved due problem with database");
		}
		pressKeyToContinue();
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

}