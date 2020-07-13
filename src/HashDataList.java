import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.HashMap;
import java.util.ArrayList;


public class HashDataList{
	//text colors
	private static final String ANSI_RESET = "\u001B[0m";
	private static final String ANSI_BLACK = "\u001B[30m";
	private static final String ANSI_RED = "\u001B[31m";
	private static final String ANSI_GREEN = "\u001B[32m";
	private static final String ANSI_YELLOW = "\u001B[33m";
	private static final String ANSI_BLUE = "\u001B[34m";
	private static final String ANSI_PURPLE = "\u001B[35m";
	private static final String ANSI_CYAN = "\u001B[36m";
	private static final String ANSI_WHITE = "\u001B[37m";

	//text background colors
	private static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	private static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	private static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	private static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	private static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	private static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	private static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	private static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	//other characters
	private static final String ANSI_CHECKMARK = "\u2713";
	private static final String ANSI_BOLD = "\033[0;1m";

	//Date and input scanner
	private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss z");
	private static Scanner scan = new Scanner(System.in);

	//Hashes of every dataset
	private HashMap<String, ArrayList<Person>> firstNameHash = new HashMap<String, ArrayList<Person>>();
	private HashMap<String, ArrayList<Person>> lastNameHash = new HashMap<String, ArrayList<Person>>();
	private HashMap<Long, ArrayList<Person>> phoneNumberHash = new HashMap<Long, ArrayList<Person>>();
	private HashMap<Integer, ArrayList<Person>> addressNumberHash = new HashMap<Integer, ArrayList<Person>>();
	private HashMap<String, ArrayList<Person>> addressRoadHash = new HashMap<String, ArrayList<Person>>();
	private HashMap<String, ArrayList<Person>> addressCityHash = new HashMap<String, ArrayList<Person>>();
	private HashMap<String, ArrayList<Person>> addressProvinceHash = new HashMap<String, ArrayList<Person>>();
	private HashMap<String, ArrayList<Person>> addressCountryHash = new HashMap<String, ArrayList<Person>>();
	private HashMap<String, ArrayList<Person>> addressPostalCodeHash = new HashMap<String, ArrayList<Person>>();
	
	//Final Database
	private ArrayList<Person> informationDatabase = new ArrayList<Person>();

	public HashDataList(){
		return;
	}

	public boolean isEmptyFirstName(){
		return firstNameHash.isEmpty();
	}

	public boolean isEmptyLastName(){
		return lastNameHash.isEmpty();
	}

	public boolean isEmptyPhoneNumber(){
		return phoneNumberHash.isEmpty();
	}

	public boolean isEmptyAddressNumber(){
		return addressNumberHash.isEmpty();
	}

	public boolean isEmptyAddressRoad(){
		return addressRoadHash.isEmpty();
	}

	public boolean isEmptyAddressCity(){
		return addressCityHash.isEmpty();
	}

	public boolean isEmptyAddressProvince(){
		return addressProvinceHash.isEmpty();
	}

	public boolean isEmptyAddressCountry(){
		return addressCountryHash.isEmpty();
	}

	public boolean isEmptyAddressPostalCode(){
		return addressPostalCodeHash.isEmpty();
	}

	public boolean isEmptyInformationDatabase(){
		return informationDatabase.isEmpty();
	}

	public int sizeFirstName(){
		return firstNameHash.size();
	}

	public int sizeLastName(){
		return lastNameHash.size();
	}

	public int sizePhoneNumber(){
		return phoneNumberHash.size();
	}

	public int sizeAddressNumber(){
		return addressNumberHash.size();
	}

	public int sizeAddressRoad(){
		return addressRoadHash.size();
	}

	public int sizeAddressCity(){
		return addressCityHash.size();
	}

	public int sizeAddressProvince(){
		return addressProvinceHash.size();
	}

	public int sizeAddressCountry(){
		return addressCountryHash.size();
	}

	public int sizeAddressPostalCode(){
		return addressPostalCodeHash.size();
	}

	public int sizeInformationDatabase(){
		return informationDatabase.size();
	}

	public void addPerson(Person person, boolean descriptionDate){
		if(person == null){
			return;
		}

		Date startDate = new Date();
    	String date = sdf.format(startDate);
    	Person toAdd = person;
    	if(!descriptionDate){
	    	toAdd.setDescription("Added on " + date + " | " + toAdd.getDescription());
    	}

		informationDatabase.add(toAdd);

		String firstName = toAdd.getFirstName();
		String lastName = toAdd.getLastName();
		long phoneNumber = toAdd.getPhoneNumber();
		int addressNumber = toAdd.getAddressNumber();
		String addressRoad = toAdd.getAddressRoad();
		String addressCity = toAdd.getAddressCity();
		String addressProvince = toAdd.getAddressProvince();
		String addressCountry = toAdd.getAddressCountry();
		String addressPostalCode = toAdd.getAddressPostalCode();

		if(firstNameHash.containsKey(firstName)){
			ArrayList<Person> firstNameArray = new ArrayList<Person>();
			firstNameArray = firstNameHash.get(firstName);
			firstNameArray.add(toAdd);
			firstNameHash.remove(firstName);
			firstNameHash.put(firstName, firstNameArray);
		}
		else{
			ArrayList<Person> firstNameArray = new ArrayList<Person>();
			firstNameArray.add(toAdd);
			firstNameHash.put(firstName, firstNameArray);
		}

		if(lastNameHash.containsKey(lastName)){
			ArrayList<Person> lastNameArray = new ArrayList<Person>();
			lastNameArray = lastNameHash.get(lastName);
			lastNameArray.add(toAdd);
			lastNameHash.remove(lastName);
			lastNameHash.put(lastName, lastNameArray);
		}
		else{
			ArrayList<Person> lastNameArray = new ArrayList<Person>();
			lastNameArray.add(toAdd);
			lastNameHash.put(lastName, lastNameArray);
		}

		if(phoneNumberHash.containsKey(phoneNumber)){
			ArrayList<Person> phoneNumberArray = new ArrayList<Person>();
			phoneNumberArray = phoneNumberHash.get(phoneNumber);
			phoneNumberArray.add(toAdd);
			phoneNumberHash.remove(phoneNumber);
			phoneNumberHash.put(phoneNumber, phoneNumberArray);
		}
		else{
			ArrayList<Person> phoneNumberArray = new ArrayList<Person>();
			phoneNumberArray.add(toAdd);
			phoneNumberHash.put(phoneNumber, phoneNumberArray);
		}

		if(addressNumberHash.containsKey(addressNumber)){
			ArrayList<Person> addressNumberArray = new ArrayList<Person>();
			addressNumberArray = addressNumberHash.get(addressNumber);
			addressNumberArray.add(toAdd);
			addressNumberHash.remove(addressNumber);
			addressNumberHash.put(addressNumber, addressNumberArray);
		}
		else{
			ArrayList<Person> addressNumberArray = new ArrayList<Person>();
			addressNumberArray.add(toAdd);
			addressNumberHash.put(addressNumber, addressNumberArray);
		}

		if(addressRoadHash.containsKey(addressRoad)){
			ArrayList<Person> addressRoadArray = new ArrayList<Person>();
			addressRoadArray = addressRoadHash.get(addressRoad);
			addressRoadArray.add(toAdd);
			addressRoadHash.remove(addressRoad);
			addressRoadHash.put(addressRoad, addressRoadArray);
		}
		else{
			ArrayList<Person> addressRoadArray = new ArrayList<Person>();
			addressRoadArray.add(toAdd);
			addressRoadHash.put(addressRoad, addressRoadArray);
		}

		if(addressCityHash.containsKey(addressCity)){
			ArrayList<Person> addressCityArray = new ArrayList<Person>();
			addressCityArray = addressCityHash.get(addressCity);
			addressCityArray.add(toAdd);
			addressCityHash.remove(addressCity);
			addressCityHash.put(addressCity, addressCityArray);
		}
		else{
			ArrayList<Person> addressCityArray = new ArrayList<Person>();
			addressCityArray.add(toAdd);
			addressCityHash.put(addressCity, addressCityArray);
		}

		if(addressProvinceHash.containsKey(addressProvince)){
			ArrayList<Person> addressProvinceArray = new ArrayList<Person>();
			addressProvinceArray = addressProvinceHash.get(addressProvince);
			addressProvinceArray.add(toAdd);
			addressProvinceHash.remove(addressProvince);
			addressProvinceHash.put(addressProvince, addressProvinceArray);
		}
		else{
			ArrayList<Person> addressProvinceArray = new ArrayList<Person>();
			addressProvinceArray.add(toAdd);
			addressProvinceHash.put(addressProvince, addressProvinceArray);
		}

		if(addressCountryHash.containsKey(addressCountry)){
			ArrayList<Person> addressCountryArray = new ArrayList<Person>();
			addressCountryArray = addressCountryHash.get(addressCountry);
			addressCountryArray.add(toAdd);
			addressCountryHash.remove(addressCountry);
			addressCountryHash.put(addressCountry, addressCountryArray);
		}
		else{
			ArrayList<Person> addressCountryArray = new ArrayList<Person>();
			addressCountryArray.add(toAdd);
			addressCountryHash.put(addressCountry, addressCountryArray);
		}

		if(addressPostalCodeHash.containsKey(addressPostalCode)){
			ArrayList<Person> addressPostalCodeArray = new ArrayList<Person>();
			addressPostalCodeArray = addressPostalCodeHash.get(addressPostalCode);
			addressPostalCodeArray.add(toAdd);
			addressPostalCodeHash.remove(addressPostalCode);
			addressPostalCodeHash.put(addressPostalCode, addressPostalCodeArray);
		}
		else{
			ArrayList<Person> addressPostalCodeArray = new ArrayList<Person>();
			addressPostalCodeArray.add(toAdd);
			addressPostalCodeHash.put(addressPostalCode, addressPostalCodeArray);
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Person> searchPeople(){ //FIX ADDRESS NUMBER AND PHONE NUMBER
		String firstName = "";
		String lastName = "";
		String phoneNumber = "";
		String addressNumber = "";
		String addressRoad = "";
		String addressCity = "";
		String addressProvince = "";
		String addressCountry = "";
		String addressPostalCode = "";

		String menuInt = "";
		String fileName = "";
		String confirmFileName = "";

		ArrayList<Person> searching = new ArrayList<Person>();
		ArrayList<Person> tmp = new ArrayList<Person>();

		boolean match = true;
		boolean tmptmp1 = true;
		boolean tmptmp2 = true;
		boolean continueCheck = true;
		boolean checkIndividual = true;
		boolean checkNoInputs = false;
		boolean menu = true;
		boolean flag1 = true;
		boolean flag2 = true;

		int finalNumber = 0;
		int choosePrint = 0;

		Person searchedPerson = new Person();

		System.out.println(ANSI_PURPLE_BACKGROUND + "Step 1" + ANSI_RESET +": Enter individual's information.\n(Press Enter to skip unknown information)\n");


		System.out.print("First Name: ");
		firstName = scan.nextLine();
		if(firstName.equals("exit") || firstName.equals("Exit")){
			System.exit(0);
		}
		if(!firstName.equals("")){
			try{
				tmp = firstNameHash.get(firstName);
				searching.addAll(tmp);
			}
			catch(Exception e){
				System.out.println(ANSI_RED + "ERROR! "+ ANSI_RESET + "First name not found");
				match = false;
			}
		}

		if(match){
			System.out.print("Last Name: ");
			lastName = scan.nextLine();
			if(lastName.equals("exit") || lastName.equals("Exit")){
				System.exit(0);
			}
			if(!lastName.equals("")){
				try{
					tmp = lastNameHash.get(lastName);
					searching.addAll(tmp);
				}
				catch(Exception e){
					System.out.println(ANSI_RED + "ERROR! "+ ANSI_RESET + "Last name not found");
					match = false;
				}
			}
		}

		if(match){
			while(tmptmp1){
				System.out.print("Phone Number: ");
				phoneNumber = scan.nextLine();
				if(phoneNumber.equals("exit") || phoneNumber.equals("Exit")){
					System.exit(0);
				}
				if(!phoneNumber.equals("")){
					long phoneNumberTmp = -1l;
					try{
						phoneNumberTmp = Long.parseLong(phoneNumber, 10);
					}
					catch(Exception e){
						System.out.println(ANSI_RED + "ERROR! "+ ANSI_RESET + "Malicious input. Please try again.");
						tmptmp2 = false;
					}

					if(tmptmp2){
						try{
							tmp = phoneNumberHash.get(phoneNumberTmp);
							searching.addAll(tmp);
							tmptmp1 = false;
						}
						catch(Exception e){
							System.out.println(ANSI_RED + "ERROR! "+ ANSI_RESET + "Phone number not found");
							match = false;
							tmptmp1 = false;
						}
					}
					tmptmp2 = true;
				}
				else{
					tmptmp1 = false;
				}
			}
			tmptmp1 = true;
			tmptmp2 = true;
		}

		if(match){
			while(tmptmp1){
				System.out.print("Address Number: ");
				addressNumber = scan.nextLine();
				if(addressNumber.equals("exit") || addressNumber.equals("Exit")){
					System.exit(0);
				}
				if(!addressNumber.equals("")){
					int addressNumberTmp = -1;
					try{
						addressNumberTmp = Integer.parseInt(addressNumber);
					}
					catch(Exception e){
						System.out.println(ANSI_RED + "ERROR! "+ ANSI_RESET + "Malicious input. Please try again.");
						tmptmp2 = false;
					}

					if(tmptmp2){
						try{
							tmp = addressNumberHash.get(addressNumberTmp);
							searching.addAll(tmp);
							tmptmp1 = false;
						}
						catch(Exception e){
							System.out.println(ANSI_RED + "ERROR! "+ ANSI_RESET + "Address number not found");
							match = false;
							tmptmp1 = false;
						}
					}
					tmptmp2 = true;
				}
				else {
					tmptmp1 = false;
				}
			}
			tmptmp1 = true;
			tmptmp2 = true;
		}

		if(match){
			System.out.print("Address Road: ");
			addressRoad = scan.nextLine();
			if(addressRoad.equals("exit") || addressRoad.equals("Exit")){
				System.exit(0);
			}
			if(!addressRoad.equals("")){
				try{
					tmp = addressRoadHash.get(addressRoad);
					searching.addAll(tmp);
				}
				catch(Exception e){
					System.out.println(ANSI_RED + "ERROR! "+ ANSI_RESET + "Address road not found");
					match = false;
				}
			}
		}

		if(match){
			System.out.print("City: ");
			addressCity = scan.nextLine();
			if(addressCity.equals("exit") || addressCity.equals("Exit")){
				System.exit(0);
			}
			if(!addressCity.equals("")){
				try{
					tmp = addressCityHash.get(addressCity);
					searching.addAll(tmp);
				}
				catch(Exception e){
					System.out.println(ANSI_RED + "ERROR! "+ ANSI_RESET + "City not found");
					match = false;
				}
			}
		}

		if(match){
			System.out.print("Province: ");
			addressProvince = scan.nextLine();
			if(addressProvince.equals("exit") || addressProvince.equals("Exit")){
				System.exit(0);
			}
			if(!addressProvince.equals("")){
				try{
					tmp = addressProvinceHash.get(addressProvince);
					searching.addAll(tmp);
				}
				catch(Exception e){
					System.out.println(ANSI_RED + "ERROR! "+ ANSI_RESET + "Province not found");
					match = false;
				}
			}
		}

		if(match){
			System.out.print("Country: ");
			addressCountry = scan.nextLine();
			if(addressCountry.equals("exit") || addressCountry.equals("Exit")){
				System.exit(0);
			}
			if(!addressCountry.equals("")){
				try{
					tmp = addressCountryHash.get(addressCountry);
					searching.addAll(tmp);
				}
				catch(Exception e){
					System.out.println(ANSI_RED + "ERROR! "+ ANSI_RESET + "Country not found");
					match = false;
				}
			}
		}

		if(match){
			System.out.print("Postal Code: ");
			addressPostalCode = scan.nextLine();
			if(addressPostalCode.equals("exit") || addressPostalCode.equals("Exit")){
				System.exit(0);
			}
			if(!addressPostalCode.equals("")){
				try{
					tmp = addressPostalCodeHash.get(addressPostalCode);
					searching.addAll(tmp);
				}
				catch(Exception e){
					System.out.println(ANSI_RED + "ERROR! "+ ANSI_RESET + "Postal Code not found");
					match = false;
				}
			}
		}

		//Gives True if No Inputs are Given
		checkNoInputs = !(!firstName.equals("") || 
						   !lastName.equals("") || 
						   !phoneNumber.equals("") || 
						   !addressNumber.equals("") || 
						   !addressRoad.equals("") || 
						   !addressCity.equals("") ||
						   !addressProvince.equals("") ||
						   !addressCountry.equals("") ||
						   !addressPostalCode.equals(""));

		System.out.print("\n");
		System.out.println(ANSI_PURPLE_BACKGROUND + "Step 2" + ANSI_RESET +": Creating list matching criteria.");
		if(continueCheck){
			System.out.print("     -- Checking basic input criteria.");
			if(match && !checkNoInputs){
				System.out.println(" " + ANSI_GREEN + ANSI_CHECKMARK + ANSI_RESET);
			}
			else{
				System.out.println(" " + ANSI_RED + "x" + ANSI_RESET);
				continueCheck = false;
			}
		}
		
		if(continueCheck){
			System.out.print("     -- Checking individuals who match all inputs.");
			//First Name
			tmp.clear();
			for(int i = 0; i < searching.size(); i++){ //IMPORTANT SWITCH BETWEEN IF BELOW AND THIS FOR FOR BETTER EFFICIENCY
				// checkIndividual = true;
				if(!firstName.equals("")){
					if(searching.get(i).getFirstName().equals(firstName)){
						tmp.add(searching.get(i));
					}
				}
			}
			if(!firstName.equals("")){
				searching = (ArrayList<Person>)tmp.clone();
			}

			//Last Name
			tmp.clear();
			for(int i = 0; i < searching.size(); i++){
				// checkIndividual = true;
				if(!lastName.equals("")){
					if(searching.get(i).getLastName().equals(lastName)){
						tmp.add(searching.get(i));
					}
				}
			}
			if(!lastName.equals("")){
				searching = (ArrayList<Person>)tmp.clone();
			}

			//Phone Number
			tmp.clear();
			for(int i = 0; i < searching.size(); i++){
				// checkIndividual = true;
				if(!phoneNumber.equals("")){
					Long phoneNumberTmp1 = Long.parseLong(phoneNumber, 10);
					Long phoneNumberTmp2 = searching.get(i).getPhoneNumber();
					if(phoneNumberTmp2.equals(phoneNumberTmp1)){
						tmp.add(searching.get(i));
					}
				}
			}
			if(!phoneNumber.equals("")){
				searching = (ArrayList<Person>)tmp.clone();
			}

			//Address Number
			tmp.clear();
			for(int i = 0; i < searching.size(); i++){
				// checkIndividual = true;
				if(!addressNumber.equals("")){
					Integer addressNumberTmp1 = Integer.parseInt(addressNumber);
					Integer addressNumberTmp2 = searching.get(i).getAddressNumber();
					if(addressNumberTmp2.equals(addressNumberTmp1)){
						tmp.add(searching.get(i));
					}
				}
			}
			if(!addressNumber.equals("")){
				searching = (ArrayList<Person>)tmp.clone();
			}

			//Address Road
			tmp.clear();
			for(int i = 0; i < searching.size(); i++){
				// checkIndividual = true;
				if(!addressRoad.equals("")){
					if(searching.get(i).getAddressRoad().equals(addressRoad)){
						tmp.add(searching.get(i));
					}
				}
			}
			if(!addressRoad.equals("")){
				searching = (ArrayList<Person>)tmp.clone();
			}

			//City
			tmp.clear();
			for(int i = 0; i < searching.size(); i++){
				// checkIndividual = true;
				if(!addressCity.equals("")){
					if(searching.get(i).getAddressCity().equals(addressCity)){
						tmp.add(searching.get(i));
					}
				}
			}
			if(!addressCity.equals("")){
				searching = (ArrayList<Person>)tmp.clone();
			}

			//Province
			tmp.clear();
			for(int i = 0; i < searching.size(); i++){
				// checkIndividual = true;
				if(!addressProvince.equals("")){
					if(searching.get(i).getAddressProvince().equals(addressProvince)){
						tmp.add(searching.get(i));
					}
				}
			}
			if(!addressProvince.equals("")){
				searching = (ArrayList<Person>)tmp.clone();
			}

			//Country
			tmp.clear();
			for(int i = 0; i < searching.size(); i++){
				// checkIndividual = true;
				if(!addressCountry.equals("")){
					if(searching.get(i).getAddressCountry().equals(addressCountry)){
						tmp.add(searching.get(i));
					}
				}
			}
			if(!addressCountry.equals("")){
				searching = (ArrayList<Person>)tmp.clone();
			}

			//Postal Code
			tmp.clear();
			for(int i = 0; i < searching.size(); i++){
				// checkIndividual = true;
				if(!addressPostalCode.equals("")){
					if(searching.get(i).getAddressPostalCode().equals(addressPostalCode)){
						tmp.add(searching.get(i));
					}
				}
			}
			if(!addressPostalCode.equals("")){
				searching = (ArrayList<Person>)tmp.clone();
			}

			//Remove duplicates and give final check or x if there is no people
			tmp.clear();
			for(Person thisPerson : searching){
				if(!tmp.contains(thisPerson)){
					tmp.add(thisPerson);
				}
			}
			searching = (ArrayList<Person>)tmp.clone();

			if(searching.size() == 0){
				System.out.println(" " + ANSI_RED + "x" + ANSI_RESET);
				System.out.println("     -- " + ANSI_RED + "FAIL! No Matching Users Found!" + ANSI_RESET);
			}

			else{
				System.out.println(" " + ANSI_GREEN + ANSI_CHECKMARK + ANSI_RESET);
				if(searching.size() == 1){
					System.out.println("     -- " + ANSI_GREEN + "SUCCESS! 1 Matching Individual Has Been Found!" + ANSI_RESET);
				}
				if(searching.size() > 1){
					System.out.println("     -- " + ANSI_GREEN + "SUCCESS! " + searching.size() + " Matching Individuals Have Been Found!" + ANSI_RESET);
				}

				//Because there are people to print we will ask if the user wants them in the console or in a TXT file.
				System.out.print("\n");
				System.out.println(ANSI_PURPLE_BACKGROUND + "Step 3" + ANSI_RESET +": Where to Print Data.");
				System.out.println("----------------------------");
				System.out.println("1 -> " + ANSI_BLUE + "Print to Console." + ANSI_RESET);
				System.out.println("2 -> " + ANSI_YELLOW + "Print to Custom File." + ANSI_RESET);
				System.out.println("----------------------------");

				System.out.print("Choice: ");
				menuInt = scan.nextLine();
				if(menuInt.equals("exit") || menuInt.equals("Exit")){
	    			System.exit(0);
	    		}

	    		while(menu){
					if(menuInt.equals("1") || menuInt.equals("2")){
						menu = false;
					}
					if(!menuInt.equals("1") && !menuInt.equals("2")){
						System.out.println("Please choose a number between 1 and 2. [1 -> " + ANSI_BLUE + "Console" + ANSI_RESET + "]");
						System.out.println("                                        [2 -> " + ANSI_YELLOW + "Custom File" + ANSI_RESET + "]");
						System.out.print("Choice: ");
						menuInt = scan.nextLine();
						if(menuInt.equals("exit") || menuInt.equals("Exit")){
				    		System.exit(0);
				    	}
					}
				}

				if(menuInt.equals("1")){
					System.out.println("\nPrint to Console Requested.");
					System.out.println("---------------------------");

					for(int i = 0; i < searching.size(); i++){
						System.out.println(searching.get(i));
					}
					
				}

				if(menuInt.equals("2")){
					System.out.println("\nPrint to Custom File Requested.");
					System.out.println("-------------------------------");

					while(flag2){
						System.out.println(ANSI_PURPLE_BACKGROUND + "1" + ANSI_RESET + " -- Please Choose a Name for your File. (" + ANSI_BOLD + "NAME" + ANSI_RESET + ".txt)");
						System.out.print("Choice for " + ANSI_BOLD + "NAME" + ANSI_RESET + ": ");
						fileName = scan.nextLine();
						if(fileName.equals("exit") || fileName.equals("Exit")){
							System.exit(0);
						}

						System.out.print("\n");
						System.out.println(ANSI_PURPLE_BACKGROUND + "2" + ANSI_RESET + " -- Confirm File Name. ");
						
						fileName = fileName + ".txt";
						System.out.println("File Name = " + ANSI_BOLD + fileName + ANSI_RESET + "\nConfirm? (Y/N)");
						System.out.print("Choice: ");
						confirmFileName = scan.nextLine();
						if(confirmFileName.equals("exit") || confirmFileName.equals("Exit")){
				    		System.exit(0);
				   		}
						while(flag1){
							if(confirmFileName.equals("Y") || confirmFileName.equals("y")){
								flag1 = false;
							}
							if(confirmFileName.equals("N") || confirmFileName.equals("n")){
								flag1 = false;
							}
							if(!confirmFileName.equals("Y") && !confirmFileName.equals("y") && !confirmFileName.equals("N") && !confirmFileName.equals("n")){
								System.out.println("Y = Correct File Name | N = False File Name");
								System.out.print("Confirm? (" + ANSI_BOLD + fileName + ANSI_RESET + "): ");
								confirmFileName = scan.nextLine();
								if(confirmFileName.equals("exit") || confirmFileName.equals("Exit")){
						    		System.exit(0);
						    	}
							}
						}

						if(fileName.equals(".txt")){
							System.out.println(ANSI_RED + "FAIL! .txt Not A Valid File Name!\n" + ANSI_RESET);
						}
						else{
							flag2 = false;
						}
					}

					//Print to file
					try {
				      	// Creates a FileWriter
				      	FileWriter file = new FileWriter(fileName);

				      	// Creates a BufferedWriter
				      	BufferedWriter output = new BufferedWriter(file);

				      	// Writes the string to the file
				      	for(int i = 0; i < searching.size(); i++){
					    	searchedPerson = searching.get(i);
					      	output.write("Name: " + searchedPerson.getFirstName() + " " + searchedPerson.getLastName() + "\n");
					      	output.write("Phone number: " + searchedPerson.getPhoneNumber() + "\n");
					      	output.write("Address: " + searchedPerson.getAddressNumber() + " " + searchedPerson.getAddressRoad() + ", " + searchedPerson.getAddressCity() + ", " + searchedPerson.getAddressProvince() + ", " + searchedPerson.getAddressCountry() + "\n");
					      	output.write("PC: " + searchedPerson.getAddressPostalCode() + "\n");
					      	output.write("Description: " + searchedPerson.getDescription() + "\n\n");
				      	}

				    	if(searching.size() == 1){
							System.out.println(ANSI_GREEN + "SUCCESS! " + fileName + " has been created with " + searching.size() + " Individual's Information\n");
						}
						if(searching.size() > 1){
							System.out.println(ANSI_GREEN + "SUCCESS! " + fileName + " has been created with " + searching.size() + " Individuals' Information\n");
						}

				      	// Closes the writer
				      	output.close();
				    }

				    catch (Exception e) {
				      e.getStackTrace();
				    }

				}
			}
		}

		//Refering to the top checking function for basic check
		if(!continueCheck && !checkNoInputs){
			System.out.println("     -- " + ANSI_RED + "FAIL! No Matching Users Found!" + ANSI_RESET);
		}

		else if (!continueCheck && checkNoInputs){
			System.out.println("     -- " + ANSI_RED + "FAIL! No Data Given!" + ANSI_RESET);
		}

		

		return searching;
	}

	public void saveData(){

		Person toSave = new Person();

		try {
	      	// Creates a FileWriter
	      	FileWriter file = new FileWriter("../data/DATA.txt");

	      	// Creates a BufferedWriter
	      	BufferedWriter output = new BufferedWriter(file);

	      	output.write(informationDatabase.size() + "\n");

	      	// Writes the string to the file
	      	for(int i = 0; i < informationDatabase.size(); i++){
		    	toSave = informationDatabase.get(i);
		      	output.write(toSave.getFirstName() + "\n");
		      	output.write(toSave.getLastName() + "\n");
		      	output.write(toSave.getPhoneNumber() + "\n");
		      	output.write(toSave.getAddressNumber() + "\n");
		      	output.write(toSave.getAddressRoad() + "\n");
		      	output.write(toSave.getAddressCity() + "\n");
		      	output.write(toSave.getAddressProvince() + "\n");
		      	output.write(toSave.getAddressCountry() + "\n");
		      	output.write(toSave.getAddressPostalCode() + "\n");
		      	output.write(toSave.getDescription() + "\n");
	      	}

	      	// Closes the writer
	      	output.close();
	    }

	    catch (Exception e) {
	      
	    }
	}


	public void loadData(){
		Person toLoad = new Person();
		String s;
		int i = 0;

		try{
			BufferedReader br = new BufferedReader(new FileReader("../data/DATA.txt"));

			i = Integer.parseInt(br.readLine());

			while(i != 0){
				toLoad.setFirstName(br.readLine());
				toLoad.setLastName(br.readLine());
				toLoad.setPhoneNumber(Long.parseLong(br.readLine()));
				toLoad.setAddressNumber(Integer.parseInt(br.readLine()));
				toLoad.setAddressRoad(br.readLine());
				toLoad.setAddressCity(br.readLine());
				toLoad.setAddressProvince(br.readLine());
				toLoad.setAddressCountry(br.readLine());
				toLoad.setAddressPostalCode(br.readLine());
				toLoad.setDescription(br.readLine());

				addPerson(toLoad, true);
				toLoad = new Person();

				i--;
			}

			br.close();
		}
		catch(Exception e){

		}

	}

	public String toString(){
		String s = "";
		for (int i = 0; i < informationDatabase.size(); i++) {
      		s += informationDatabase.get(i);
    	}
		return s;
	}

	
}