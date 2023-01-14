package classes;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

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
	public void deleteItem(ArrayList<Integer> pkList) {
		System.out.print("\n    Delete item: ");
		String input=userInput.nextLine(); // Get userChose number
		if(!pkList.isEmpty())
		{
			// As long as input is integer
			// Input choice is not 0, because PK in DB starts from 1
			// Input choice not bigger than highest PK in DB (Size arrayList)
			while (!isInteger(input) || input.equals("0") || Integer.parseInt(input)>pkList.size()) 
			{
				System.out.print("    Enter a valid number: ");
				input=userInput.nextLine();
			}
			int pk = pkList.get((Integer.parseInt(input)-1));

			if(executeDelete(pk))
			{
				System.out.println("    Chosen item deleted");
				pressKeyToContinue();
			}
			else
			{
				System.out.println("    Item not deleted due database error");
				pressKeyToContinue();
			}
			System.out.println("");
		}
		else
		{
			System.out.println("\n    No stored items in database");
			pressKeyToContinue();
		}
	}

	// Random picker in the ArrayList
	public int randomPicker(ArrayList<String> obj)
	{
		Random rn = new Random();
		int randomNum = rn.nextInt(obj.size());
		return randomNum;
	}

	// Dynamic line
	public static void printFormattingLine(int length) // Take e.g string.length() 
	{
		System.out.print("    "); 		// Formatting: add extra space before line
		for(int i=0;i<length-1;i++)  	// Extend dynamic the line as long as the string
		{
			System.out.print("-");	
		}
		System.out.println(); 			// New line
	}

	// Dynamic line - Get size for line by largest word length in ArrayList
	public static int getSizeLargestWord(ArrayList<String> list)
	{
		int size=0; // Store length of largest word
		for(int i=0;i<list.size();i++)  	
		{
			if(list.get(i).length()>size) // Read ArrayList and get length of each String
			{
				size=list.get(i).length();
			}
		}
		return size;
	}

	// Ask random genre (category) or choose a genre
	public int assignGenre()
	{
		int genre;
		System.out.println("\n    Assign genre:");
		System.out.println("");
		System.out.println("    [1] Let the computer pick a random genre");
		System.out.println("    [2] Choose a genre");
		System.out.println("");
		System.out.print("    Choice: ");

		String input=userInput.nextLine(); // Get userChoice number

		// Avoid error when user type not a valid number
		while (!input.equals("1") && !input.equals("2"))
		{
			System.out.print("\n    Enter a valid number: ");
			input=userInput.nextLine();
		}
		switch(input) {
		case "1":
			genre=this.randomGenre();
			break;
		case "2":
			genre=this.askGenre();
			break;
		default :
			genre=this.randomGenre();
			break;
		}
		return Integer.parseInt(myDBConnection.getCategorie_ids().get(genre-1)); // Get PK from categories for foreign key
	}

	// Ask genre (category)
	public int askGenre()
	{
		ArrayList<String> cat = myDBConnection.getCategories();
		System.out.println("\n    Assign one of the following genres.");
		System.out.println("");  // new line
		System.out.print("   "); // add some spaces before line
		for(int i=0;i<cat.size();i++)
		{
			System.out.print(" "+(i+1)+ "="+capitalize(cat.get(i))+" ");
			if(i>3 && i%5==0) {System.out.print("\n   ");}; // add new line for readability
		}
		System.out.print("\n    Assign genre number: ");
		// Ask user choice

		String input = userInput.nextLine();
		int userChoiceGenre=0;
		if(isInteger(input)) // Avoid error when user only press enter
		{
			userChoiceGenre=Integer.parseInt(input);
		}

		// As long as input is integer
		// Input choice is not 0, because PK in DB starts from 1
		// Input choice not bigger than highest PK in DB (Size arrayList categories)
		while (!isInteger(input) || userChoiceGenre==0 || userChoiceGenre>cat.size()) {
			System.out.print("\n    Enter a valid number: ");
			input = userInput.nextLine();
			if(isInteger(input)) // Avoid error when user only press enter
			{
				userChoiceGenre=Integer.parseInt(input);
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
			System.out.println("\n    Random genre: "+cat.get(randomGenre)); 	// Show random genre
			System.out.print("    Would you like to confirm? [y/n]: "); 		// Ask for confirmation
			input = userInput.nextLine().toLowerCase();
			while(!input.equals("y") && !input.equals("n")) 					// Input validation
			{
				System.out.print("    Invalid input. Enter y or n: ");
				input = userInput.nextLine();
			}
			if(input.equals("y"))
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
			System.out.println("\n    Successfully wrote to the file: FilmGen-"+name+"s.txt");
			pressKeyToContinue();
		} catch (IOException e) {
			System.out.println("\n    An error occurred.");
			e.printStackTrace();
			pressKeyToContinue();
		}
	}

	// Close scanner
	public static void closeScanner()
	{
		userInput.close();
	}

}