package classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

public class DBConnect {

	static Connection c;
	PreparedStatement preparedStatement;
	ResultSet resultSet;
	String sqlQuery;

	// Initialize Scanner for this class
	Scanner dbInput = new Scanner(System.in);

	// Relative path to database file
	static String url="jdbc:sqlite:src/resources/FilmGen.sqlite?foreign_keys=on";

	// Constructor
	public DBConnect(){
	}

	// Only for connectivity testing	
	//	public static void openDB() {
	//
	//		{
	//			try {
	//				Class.forName("org.sqlite.JDBC");
	//				c = DriverManager.getConnection(url);
	//			} catch ( Exception e ) {
	//				System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	//				System.exit(0);
	//			}
	//			System.out.println("Opened database successfully");
	//
	//		}
	//	}

	// View or edit stored data in database
	public void databaseView()
	{
		String userChoice="0";
		String name="null"; // Used for table title
		do
		{
			System.out.println("");
			System.out.println("    Choose a table:");
			System.out.println("    [1] Words");
			System.out.println("    [2] Verbs");
			System.out.println("    [3] Subjects");
			System.out.println("    [4] Stories");
			System.out.println("    [5] Locations");
			System.out.println("    [6] Hyperbolics");
			System.out.println("    [7] Genres");
			System.out.println(" ");
			System.out.println("    Press enter for main menu");
			System.out.println("");
			System.out.print("    Choice: ");
			userChoice=dbInput.nextLine().toLowerCase();
			switch(userChoice) {
			case "1":
				name = "Stored words";
				readTable(getWords(),name);
				showEditOptions("words","word");  				// Parameters tableName, columnName
				break;
			case "2":
				name = "Stored verbs";
				readTable(getVerbs(),name);
				showEditOptions("verbs","verbs"); 				// Parameters tableName, columnName
				break;
			case "3":
				name = "Stored subjects";
				readTable(getSubjects(),name);
				showEditOptions("subjects","subjects"); 		// Parameters tableName, columnName
				break;
			case "4":
				name = "Stored stories";
				readTable(getStories(),name);
				showEditOptions("stories","stories"); 			// Parameters tableName, columnName
				break;
			case "5":
				name = "Stored locations";
				readTable(getLocations(),name);
				showEditOptions("locations","location"); 		// Parameters tableName, columnName
				break;
			case "6":
				name = "Stored hyperbolics";
				readTable(getHyperbolics(),name);
				showEditOptions("hyperbolic","hyperbolic"); 	// Parameters tableName, columnName
				break;
			case "7":
				name = "Stored genres";
				readTable(getCategories(),name);
				showEditOptions("categories","category"); 		// Parameters tableName, columnName
				break;
			default :
				break;
			}
		}
		while(!userChoice.equals(""));
		System.out.println("");
	}

	// Show edit options to the user
	private void showEditOptions(String tableName, String columnName)
	{
		String userChoice;
		System.out.println("    [1] Insert data");
		System.out.println("    [2] Delete data");
		System.out.println("\n    Press enter for back");
		System.out.println("");
		System.out.print("    Choice: ");
		userChoice=dbInput.nextLine().toString();
		switch(userChoice)
		{
		case "1":
			// Ask value to insert in db
			askInsertContent(tableName,columnName);		// Parameters tableName columnName
			break;
		case "2":
			// Ask value to delete in db
			askDeleteContent(tableName,columnName);		// Parameters tableName columnName
			break;
		default :
			break;
		}
	}

	// Check if userInput is integer
	private boolean isNumeric(String str) {
		if (str == null) {
			return false;
		}
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	// If the user select a record by number id - Search value by id
	private String getIDbyString(String tableName, int id)
	{
		String str=null; // Store found value to return
		switch(tableName)
		{
		case "words":
			str=searchStringById(getWords(),id);
			break;
		case "verbs":
			str=searchStringById(getVerbs(),id);
			break;
		case "subjects":
			str=searchStringById(getSubjects(),id);
			break;
		case "stories":
			str=searchStringById(getStories(),id);
			break;
		case "locations":
			str=searchStringById(getLocations(),id);
			break;
		case "hyperbolic":
			str=searchStringById(getHyperbolics(),id);
			break;
		case "categories":
			str=searchStringById(getCategories(),id);
			break;
		default :
			break;
		}
		return str;
	}

	//	Called by getIDbyString() method - Search value by id number in ArrayList
	private String searchStringById(ArrayList<String> records, int id)
	{
		String str=null;
		if(id<(records.size()+1) && id!=0)
		{
			str=records.get(id-1); 	// id -1 because array starts from 0
		}
		else
		{
			System.out.println("\n    Invalid id number or number out of range");
		}
		return str;
	}

	// Scan an entry from user to delete a record
	private void askDeleteContent(String tableName, String columnName)
	{
		System.out.print("    Type value or number to delete: ");
		String str = dbInput.nextLine().toString();
		String value=null; // Store value to delete
		if(!str.equals(""))
		{
			// Check if user input is String or number (Id)
			if(isNumeric(str))
			{
				int id = Integer.parseInt(str);
				value=getIDbyString(tableName,id);
			}
			else
			{
				value=str;
			}
			// Delete record
			boolean succes=deleteQuery(tableName,columnName,value);
			if(succes)
			{
				System.out.println("\n    Successfully deleted");
				Film.pressKeyToContinue();
			}
			else
			{
				System.out.println("    Value not deleted\n");
				Film.pressKeyToContinue();
			}
		}
	}

	// Delete a record determined by user
	private boolean deleteQuery(String tableName, String columnName, String str)
	{
		sqlQuery="DELETE FROM main."+tableName+" WHERE "+columnName+" = ?";
		try{
			c= DriverManager.getConnection(url);//Establishing Connection
			PreparedStatement preparedStatement=c.prepareStatement(sqlQuery);
			preparedStatement.setString(1, str);
			int row=preparedStatement.executeUpdate();
			preparedStatement.close();
			if(row>=1)
			{
				return true; 	// Delete success
			}
			else
			{
				return false; 	// Delete unsuccessful
			}
		}catch(Exception e){
			if(e.getLocalizedMessage().equals("[SQLITE_CONSTRAINT_TRIGGER] A RAISE function within a trigger fired, causing the SQL statement to abort (FOREIGN KEY constraint failed)"))
			{
				System.out.println("\n    This value is still used in generated films, titles or descriptions");
			}
			else
			{
				System.out.println(e);
			}
		}
		return false;
	}

	// Scan an entry from user to insert a record to a database table
	private void askInsertContent(String tableName, String columnName)
	{
		System.out.print("    Insert new value: ");
		String str = dbInput.nextLine().toString();


		if(!str.equals(""))
		{
			if(tableName.equals("words") && str.length()>15) // Max allowed length for category due formatting
			{
				System.out.println("\n    Error adding: max value length for "+columnName+" is 15");
			}
			else if(tableName.equals("verbs") && str.length()>30) // Max allowed length for category due formatting
			{
				System.out.println("\n    Error adding: max value length for "+columnName+" is 30");
			}
			else if(tableName.equals("subjects") && str.length()>35) // Max allowed length for category due formatting
			{
				System.out.println("\n    Error adding: max value length for "+columnName+" is 35");
			}
			else if(tableName.equals("stories") && str.length()>15) // Max allowed length for category due formatting
			{
				System.out.println("\n    Error adding: max value length for "+columnName+" is 15");
			}
			else if(tableName.equals("locations") && str.length()>25) // Max allowed length for category due formatting
			{
				System.out.println("\n    Error adding: max value length for "+columnName+" is 25");
			}
			else if(tableName.equals("hyperbolic") && str.length()>15) // Max allowed length for category due formatting
			{
				System.out.println("\n    Error adding: max value length for "+columnName+" is 15");
			}
			else if(tableName.equals("categories") && str.length()>12) // Max allowed length for category due formatting
			{
				System.out.println("\n    Error adding: max value length for "+columnName+" is 12");
			}
			else
			{
				String sqlQuery = "INSERT INTO main."+tableName+" ("+columnName+") VALUES ('"+str+"')";
				boolean succes=insertQuery(sqlQuery);
				if(succes)
				{
					System.out.println("\n    Successfully added");
				}
				else
				{
					System.out.println("\n    Error adding");
				}
			}
		}
		Film.pressKeyToContinue();
	}

	// Insert content to tables
	private boolean insertQuery(String str)
	{
		sqlQuery=str;
		try{
			c= DriverManager.getConnection(url);//Establishing Connection
			PreparedStatement preparedStatement=c.prepareStatement(sqlQuery);
			int row=preparedStatement.executeUpdate();
			preparedStatement.close();
			if(row==1)
			{
				return true;
			}
			else
			{
				return false;
			}
		}catch(Exception e){
			System.out.println(e);
		}
		return false;
	}

	// Read records of a table (ArrayList getters)
	private void readTable(ArrayList<String> table, String name)
	{
		Film.printFormattingLine(name.length()+1);
		System.out.println("    "+name);
		Film.printFormattingLine(name.length()+1);
		System.out.print("\n    | "); // Formatting
		for(int i=0;i<table.size();i++)
		{
			if(i%10==0 && i !=0) 			// Formatting: new line after 10 values
			{
				System.out.println("");		// Break line
				System.out.print("    | "); // Add spaces
			}
			System.out.print((i+1)+" "+table.get(i)+" | "); // Print values
		}
		System.out.println("\n");
	}

	// Database statistics
	public void databaseStats()
	{
		System.out.println("\n    Database statistics");
		System.out.println("    --------------------\n");
		System.out.println("    Amount of data stored in database:");
		System.out.printf("\n    %8d words", getWords().size());
		System.out.printf("\n    %8d verbs", getVerbs().size());
		System.out.printf("\n    %8d subjects", getSubjects().size());
		System.out.printf("\n    %8d stories", getStories().size());
		System.out.printf("\n    %8d locations", getLocations().size());
		System.out.printf("\n    %8d hyperbolics", getHyperbolics().size());
		System.out.printf("\n    %8d genres", getCategories().size());
		System.out.printf("\n    %8d generated films (title + descripton)", getFilmForeignKeys().size());
		System.out.printf("\n    %8d generated titles", getTitleForeignKeys().size());
		System.out.printf("\n    %8d generated subjecs", getDescriptionForeignKeys().size());
		System.out.println("\n");
		Film.pressKeyToContinue();
	}

	// Delete a generated film
	public boolean deleteFilm(int PK)
	{
		sqlQuery="DELETE FROM main.films WHERE film_id = ?";
		try{
			c= DriverManager.getConnection(url);//Establishing Connection
			PreparedStatement preparedStatement=c.prepareStatement(sqlQuery);
			preparedStatement.setInt(1, PK);
			int row=preparedStatement.executeUpdate();
			preparedStatement.close();
			if(row==1)
			{
				return true;
			}
			else
			{
				return false;
			}

		}catch(Exception e){
			System.out.println(e);
		}
		return false;
	}

	// Insert Film title, foreign ID, indexes
	public boolean insertFilmIndex(int indexOfCategory, int indexOfWord1, int indexOfWord2, int indexOfhyperbolic, int indexOfStory,  int indexOfSubject1, int indexOfSubject2, int indexOfVerb, int indexOfSubject3, int indexOfLocation)
	{
		sqlQuery="INSERT INTO main.films (fk_category_id, fk_word_title_id, fk_word_title_id_2, fk_hyperbolic_descrip_id, fk_story_descrip_id, fk_subject_descrip_id, fk_subject_descrip_id_2, fk_verb_descrip_id, fk_subject_descrip_id_3, fk_location_descrip_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try{
			c= DriverManager.getConnection(url);//Establishing Connection
			PreparedStatement preparedStatement=c.prepareStatement(sqlQuery);
			preparedStatement.setInt(1, indexOfCategory);
			preparedStatement.setInt(2, indexOfWord1);
			preparedStatement.setInt(3, indexOfWord2);
			preparedStatement.setInt(4, indexOfhyperbolic);
			preparedStatement.setInt(5, indexOfStory);
			preparedStatement.setInt(6, indexOfSubject1);
			preparedStatement.setInt(7, indexOfSubject2);
			preparedStatement.setInt(8, indexOfVerb);
			preparedStatement.setInt(9, indexOfSubject3);
			preparedStatement.setInt(10, indexOfLocation);
			int row=preparedStatement.executeUpdate();
			preparedStatement.close();
			if(row==1)
			{
				return true;
			}
			else
			{
				return false;
			}

		}catch(Exception e){
			System.out.println(e);
		}
		return false;
	}

	// Get Film FK, foreign ID, index
	public ArrayList<String> getFilmForeignKeys()
	{
		ArrayList<String> TitleForeignKeys = new ArrayList<>();
		sqlQuery="select film_id, fk_category_id, fk_word_title_id, fk_word_title_id_2, fk_hyperbolic_descrip_id, fk_story_descrip_id, fk_subject_descrip_id, fk_subject_descrip_id_2, fk_verb_descrip_id, fk_subject_descrip_id_3, fk_location_descrip_id from main.films";
		try{
			c= DriverManager.getConnection(url);//Establishing Connection
			PreparedStatement preparedStatement=c.prepareStatement(sqlQuery);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()){
				TitleForeignKeys.add(resultSet.getInt("film_id")+" "+resultSet.getInt("fk_category_id") +" "+resultSet.getInt("fk_word_title_id") +" "+ resultSet.getInt("fk_word_title_id_2") +" " + resultSet.getInt("fk_hyperbolic_descrip_id") +" "+ resultSet.getInt("fk_story_descrip_id") +" "+ resultSet.getInt("fk_subject_descrip_id") +" "+ resultSet.getInt("fk_subject_descrip_id_2") +" "+ resultSet.getInt("fk_verb_descrip_id") + " " + resultSet.getInt("fk_subject_descrip_id_3") +" "+ resultSet.getInt("fk_location_descrip_id") +" ");
			}
			resultSet.close();
		}catch(Exception e){
			System.out.println("Error in connection");
		}
		return(TitleForeignKeys);
	}

	// Delete a generated film title
	public boolean deleteTitle(int PK)
	{
		sqlQuery="DELETE FROM main.titles WHERE title_id = ?";
		try{
			c= DriverManager.getConnection(url); //Establishing Connection
			PreparedStatement preparedStatement=c.prepareStatement(sqlQuery);
			preparedStatement.setInt(1, PK);
			int row=preparedStatement.executeUpdate();
			preparedStatement.close();
			if(row==1)
			{
				return true;
			}
			else
			{
				return false;
			}

		}catch(Exception e){
			System.out.println(e);
		}
		return false;
	}

	// Insert Film title, foreign ID, indexes
	public boolean insertTitleIndex(int indexOfCategory, int indexOfWord1, int indexOfWord2)
	{
		sqlQuery="INSERT INTO main.titles (fk_category_id, fk_word_id, fk_word_id_2) VALUES (?, ?, ?)";
		try{
			c= DriverManager.getConnection(url);//Establishing Connection
			PreparedStatement preparedStatement=c.prepareStatement(sqlQuery);
			preparedStatement.setInt(1, indexOfCategory);
			preparedStatement.setInt(2, indexOfWord1);
			preparedStatement.setInt(3, indexOfWord2);
			int row=preparedStatement.executeUpdate();
			preparedStatement.close();
			if(row==1)
			{
				return true;
			}
			else
			{
				return false;
			}

		}catch(Exception e){
			System.out.println(e);
		}
		return false;
	}

	// Get Titles FK, foreign ID, index
	public ArrayList<String> getTitleForeignKeys()
	{
		ArrayList<String> TitleForeignKeys = new ArrayList<>();
		sqlQuery="select title_id, fk_category_id, fk_word_id, fk_word_id_2 from main.titles";
		try{
			c= DriverManager.getConnection(url);//Establishing Connection
			PreparedStatement preparedStatement=c.prepareStatement(sqlQuery);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()){
				TitleForeignKeys.add(resultSet.getInt("title_id")+" "+resultSet.getInt("fk_category_id") +" "+resultSet.getInt("fk_word_id") +" "+ resultSet.getInt("fk_word_id_2") +" ");
			}
			resultSet.close();
		}catch(Exception e){
			System.out.println("Error in connection");
		}
		return(TitleForeignKeys);
	}

	// Get Descriptions FK, foreign ID, index
	public ArrayList<String> getDescriptionForeignKeys()
	{
		ArrayList<String> DescriptionForeignKeys = new ArrayList<>();
		sqlQuery="select description_id, fk_category_id, fk_hyperbolic_id, fk_story_id, fk_subject_id, fk_subject_id_2, fk_verb_id, fk_subject_id_3, fk_location_id from main.descriptions";
		try{
			c= DriverManager.getConnection(url);//Establishing Connection
			PreparedStatement preparedStatement=c.prepareStatement(sqlQuery);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()){
				DescriptionForeignKeys.add(resultSet.getInt("description_id")+" "+resultSet.getInt("fk_category_id") +" "+ resultSet.getInt("fk_hyperbolic_id") +" "+ resultSet.getInt("fk_story_id") +" "+ resultSet.getInt("fk_subject_id") +" "+ resultSet.getInt("fk_subject_id_2") +" "+ resultSet.getInt("fk_verb_id")
				+" "+ resultSet.getInt("fk_subject_id_3") +" "+ resultSet.getInt("fk_location_id"));
			}
			resultSet.close();
		}catch(Exception e){
			System.out.println("Error in connection");
		}
		return(DescriptionForeignKeys);
	}

	// Delete a generated film description
	public boolean deleteDescription(int PK)
	{
		sqlQuery="DELETE FROM main.descriptions WHERE description_id = ?";
		try{
			c= DriverManager.getConnection(url);//Establishing Connection
			PreparedStatement preparedStatement=c.prepareStatement(sqlQuery);
			preparedStatement.setInt(1, PK);
			int row=preparedStatement.executeUpdate();
			preparedStatement.close();
			if(row==1)
			{
				return true;
			}
			else
			{
				return false;
			}

		}catch(Exception e){
			System.out.println(e);
		}
		return false;
	}

	// Insert Film description, foreign ID, indexes
	public boolean insertDescriptionIndex(int indexOfcategory, int indexOfhyperbolic, int indexOfStory,  int indexOfSubject1, int indexOfSubject2, int indexOfVerb, int indexOfSubject3, int indexOfLocation)
	{
		sqlQuery="INSERT INTO main.descriptions (fk_category_id, fk_hyperbolic_id, fk_story_id, fk_subject_id, fk_subject_id_2, fk_verb_id, fk_subject_id_3, fk_location_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		try{
			c= DriverManager.getConnection(url);//Establishing Connection
			PreparedStatement preparedStatement=c.prepareStatement(sqlQuery);
			preparedStatement.setInt(1, indexOfcategory);
			preparedStatement.setInt(2, indexOfhyperbolic);
			preparedStatement.setInt(3, indexOfStory);
			preparedStatement.setInt(4, indexOfSubject1);
			preparedStatement.setInt(5, indexOfSubject2);
			preparedStatement.setInt(6, indexOfVerb);
			preparedStatement.setInt(7, indexOfSubject3);
			preparedStatement.setInt(8, indexOfLocation);
			int row=preparedStatement.executeUpdate();
			preparedStatement.close();
			if(row==1)
			{
				return true;
			}
			else
			{
				return false;
			}

		}catch(Exception e){
			System.out.println(e);
		}
		return false;
	}

	// Getters for DB

	// Get Category (genre) all foreign keys
	public ArrayList<String> getCategorie_ids()
	{
		ArrayList<String> categorie_ids = new ArrayList<>();
		sqlQuery="select category_id from categories";
		try{
			c= DriverManager.getConnection(url);//Establishing Connection
			PreparedStatement preparedStatement=c.prepareStatement(sqlQuery);

			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()){
				categorie_ids.add(resultSet.getString("category_id")); 
			}
			resultSet.close();
		}catch(Exception e){
			System.out.println("Error in connection");
		}
		return(categorie_ids);
	}

	// Get Category (genre) specific value by foreign key
	public String getCategoryByFK(int fk)
	{
		String category = null;
		sqlQuery="select category from categories where category_id=?";
		try {
			c=DriverManager.getConnection(url);
			PreparedStatement preparedStatement=c.prepareStatement(sqlQuery);
			preparedStatement.setInt(1, fk);
			ResultSet resultSet=preparedStatement.executeQuery();
			category=resultSet.getString("category");
			resultSet.close();
		}catch(Exception e) {
			System.out.println("Error in connection");
		}
		return category;
	}

	// Get Category (genre) values
	public ArrayList<String> getCategories()
	{
		ArrayList<String> categories = new ArrayList<>();
		sqlQuery="select category from categories";
		try{
			c= DriverManager.getConnection(url);//Establishing Connection
			PreparedStatement preparedStatement=c.prepareStatement(sqlQuery);

			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()){
				categories.add(resultSet.getString("category")); 
			}
			resultSet.close();
		}catch(Exception e){
			System.out.println("Error in connection");
		}
		return(categories);
	}

	// Get Hyperbolic all foreign keys
	public ArrayList<String> getHyperbolic_fks()
	{
		ArrayList<String> hyperbolic_fks = new ArrayList<String>();
		sqlQuery="select hyperbolic_id from hyperbolic";
		try {
			c=DriverManager.getConnection(url);
			PreparedStatement preparedStatement=c.prepareStatement(sqlQuery);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()){
				hyperbolic_fks.add(resultSet.getString("hyperbolic_id")); 
			}
			resultSet.close();
		}catch(Exception e) {
			System.out.println("Error in connection");
		}
		return hyperbolic_fks;
	}

	// Get Hyperbolic specific value by foreign key
	public String getHyperbolicByFK(int fk)
	{
		String hyperbolic = null;
		sqlQuery="select hyperbolic from hyperbolic where hyperbolic_id=?";
		try {
			c=DriverManager.getConnection(url);
			PreparedStatement preparedStatement=c.prepareStatement(sqlQuery);
			preparedStatement.setInt(1, fk);
			ResultSet resultSet=preparedStatement.executeQuery();
			hyperbolic=resultSet.getString("hyperbolic");
			resultSet.close();
		}catch(Exception e) {
			System.out.println("Error in connection");
		}
		return hyperbolic;
	}

	// Get Hyperbolic values
	public ArrayList<String> getHyperbolics()
	{
		ArrayList<String> hyperbolic = new ArrayList<>();
		sqlQuery="select hyperbolic from hyperbolic";
		try{
			c= DriverManager.getConnection(url);//Establishing Connection
			PreparedStatement preparedStatement=c.prepareStatement(sqlQuery);

			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()){
				hyperbolic.add(resultSet.getString("hyperbolic")); 
			}
			resultSet.close();
		}catch(Exception e){
			System.out.println("Error in connection");
		}
		return(hyperbolic);
	}

	// Get Location all foreign keys
	public ArrayList<String> getLocations_fks()
	{
		ArrayList<String> location_fks = new ArrayList<String>();
		sqlQuery="select loc_id from locations";
		try {
			c=DriverManager.getConnection(url);
			PreparedStatement preparedStatement=c.prepareStatement(sqlQuery);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()){
				location_fks.add(resultSet.getString("loc_id")); 
			}
			resultSet.close();
		}catch(Exception e) {
			System.out.println("Error in connection");
		}
		return location_fks;
	}

	// Get Location specific value by foreign key
	public String getLocationByFK(int fk)
	{
		String location = null;
		sqlQuery="select location from locations where loc_id=?";
		try {
			c=DriverManager.getConnection(url);
			PreparedStatement preparedStatement=c.prepareStatement(sqlQuery);
			preparedStatement.setInt(1, fk);
			ResultSet resultSet=preparedStatement.executeQuery();
			location=resultSet.getString("location");
			resultSet.close();
		}catch(Exception e) {
			System.out.println("Error in connection");
		}
		return location;
	}

	// Get Location values
	public ArrayList<String> getLocations()
	{
		ArrayList<String> locations = new ArrayList<>();
		sqlQuery="select location from locations";
		try{
			c= DriverManager.getConnection(url);//Establishing Connection
			PreparedStatement preparedStatement=c.prepareStatement(sqlQuery);

			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()){
				locations.add(resultSet.getString("location")); 
			}
			resultSet.close();
		}catch(Exception e){
			System.out.println("Error in connection");
		}
		return(locations);
	}

	// Get Story all foreign keys
	public ArrayList<String> getStory_fks()
	{
		ArrayList<String> story_fks = new ArrayList<String>();
		sqlQuery="select story_id from stories";
		try {
			c=DriverManager.getConnection(url);
			PreparedStatement preparedStatement=c.prepareStatement(sqlQuery);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()){
				story_fks.add(resultSet.getString("story_id")); 
			}
			resultSet.close();
		}catch(Exception e) {
			System.out.println("Error in connection");
		}
		return story_fks;
	}

	// Get Story specific value by foreign key
	public String getStoryByFK(int fk)
	{
		String story = null;
		sqlQuery="select story from stories where story_id=?";
		try {
			c=DriverManager.getConnection(url);
			PreparedStatement preparedStatement=c.prepareStatement(sqlQuery);
			preparedStatement.setInt(1, fk);
			ResultSet resultSet=preparedStatement.executeQuery();
			story=resultSet.getString("story");
			resultSet.close();
		}catch(Exception e) {
			System.out.println("Error in connection");
		}
		return story;
	}

	// Get Story values
	public ArrayList<String> getStories()
	{
		ArrayList<String> stories = new ArrayList<>();
		sqlQuery="select story from stories";
		try{
			c= DriverManager.getConnection(url);//Establishing Connection
			PreparedStatement preparedStatement=c.prepareStatement(sqlQuery);

			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()){
				stories.add(resultSet.getString("story")); 
			}
			resultSet.close();
		}catch(Exception e){
			System.out.println("Error in connection");
		}
		return(stories);
	}

	// Get Subject all foreign keys
	public ArrayList<String> getSubject_fks()
	{
		ArrayList<String> subject_fks = new ArrayList<String>();
		sqlQuery="select subject_id from subjects";
		try {
			c=DriverManager.getConnection(url);
			PreparedStatement preparedStatement=c.prepareStatement(sqlQuery);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()){
				subject_fks.add(resultSet.getString("subject_id")); 
			}
			resultSet.close();
		}catch(Exception e) {
			System.out.println("Error in connection");
		}
		return subject_fks;
	}

	// Get Subject specific value by foreign key
	public String getSubjectByFK(int fk)
	{
		String subject = null;
		sqlQuery="select subject from subjects where subject_id=?";
		try {
			c=DriverManager.getConnection(url);
			PreparedStatement preparedStatement=c.prepareStatement(sqlQuery);
			preparedStatement.setInt(1, fk);
			ResultSet resultSet=preparedStatement.executeQuery();
			subject=resultSet.getString("subject");
			resultSet.close();
		}catch(Exception e) {
			System.out.println("Error in connection");
		}
		return subject;
	}

	// Get Subject values
	public ArrayList<String> getSubjects()
	{
		ArrayList<String> subjects = new ArrayList<>();
		sqlQuery="select subject from subjects";
		try{
			c= DriverManager.getConnection(url);//Establishing Connection
			PreparedStatement preparedStatement=c.prepareStatement(sqlQuery);

			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()){
				subjects.add(resultSet.getString("subject")); 
			}
			resultSet.close();
		}catch(Exception e){
			System.out.println("Error in connection");
		}
		return(subjects);
	}

	// Get Verb all foreign keys
	public ArrayList<String> getVerb_fks()
	{
		ArrayList<String> verb_fks = new ArrayList<String>();
		sqlQuery="select verb_id from verbs";
		try {
			c=DriverManager.getConnection(url);
			PreparedStatement preparedStatement=c.prepareStatement(sqlQuery);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()){
				verb_fks.add(resultSet.getString("verb_id")); 
			}
			resultSet.close();
		}catch(Exception e) {
			System.out.println("Error in connection");
		}
		return verb_fks;
	}

	// Get Verb specific value by foreign key
	public String getVerbByFK(int fk)
	{
		String verb = null;
		sqlQuery="select verb from verbs where verb_id=?";
		try {
			c=DriverManager.getConnection(url);
			PreparedStatement preparedStatement=c.prepareStatement(sqlQuery);
			preparedStatement.setInt(1, fk);
			ResultSet resultSet=preparedStatement.executeQuery();
			verb=resultSet.getString("verb");
			resultSet.close();
		}catch(Exception e) {
			System.out.println("Error in connection");
		}
		return verb;
	}

	// Get Verb values
	public ArrayList<String> getVerbs()
	{
		ArrayList<String> verbs = new ArrayList<>();
		sqlQuery="select verb from verbs";
		try{
			c= DriverManager.getConnection(url);//Establishing Connection
			PreparedStatement preparedStatement=c.prepareStatement(sqlQuery);

			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()){
				verbs.add(resultSet.getString("verb")); 
			}
			resultSet.close();
		}catch(Exception e){
			System.out.println("Error in connection");
		}
		return(verbs);
	}

	// Get Word all foreign keys
	public ArrayList<String> getWord_fks()
	{
		ArrayList<String> word_fks = new ArrayList<String>();
		sqlQuery="select word_id from words";
		try {
			c=DriverManager.getConnection(url);
			PreparedStatement preparedStatement=c.prepareStatement(sqlQuery);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()){
				word_fks.add(resultSet.getString("word_id")); 
			}
			resultSet.close();
		}catch(Exception e) {
			System.out.println("Error in connection");
		}
		return word_fks;
	}

	// Get Word specific value by foreign key
	public String getWordByFK(int fk)
	{
		String word = null;
		sqlQuery="select word from words where word_id=?";
		try {
			c=DriverManager.getConnection(url);
			PreparedStatement preparedStatement=c.prepareStatement(sqlQuery);
			preparedStatement.setInt(1, fk);
			ResultSet resultSet=preparedStatement.executeQuery();
			word=resultSet.getString("word");
			resultSet.close();
		}catch(Exception e) {
			System.out.println("Error in connection");
		}
		return word;
	}

	// Get Word values
	public ArrayList<String> getWords()
	{
		ArrayList<String> words = new ArrayList<>();
		sqlQuery="select word from words";
		try{
			c= DriverManager.getConnection(url);//Establishing Connection
			PreparedStatement preparedStatement=c.prepareStatement(sqlQuery);

			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()){
				words.add(resultSet.getString("word")); 
			}
			resultSet.close();
		}catch(Exception e){
			System.out.println("Error in connection");
		}
		return(words);
	}

	// Close the scanner
	public void closeScanner()
	{
		dbInput.close();
	}

}