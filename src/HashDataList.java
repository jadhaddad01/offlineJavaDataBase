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
import java.nio.file.Path; 
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.File;
import java.nio.file.StandardCopyOption;

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

	public Person getPerson(int i){
		return informationDatabase.get(i);
	}

	//encrypt method encrypts a String by shifting each char up by charShift
	private static String encrypt(String toBeEncrypted, int charShift){
		char[] ch = toBeEncrypted.toCharArray(); //char array
		int i = 0;
		for(char c : ch){
			c += charShift; //shift up by charShift
			ch[i] = c;
			i++;
		}
		String encrypted = new String(ch); //switch back to String
		return encrypted;
	}

	//decrypt method decrypts a String by shifting each char down by charShift
	private static String decrypt(String toBeDecrypted, int charShift){
		char[] ch = toBeDecrypted.toCharArray(); //char array
		int i = 0;
		for(char c : ch){
			c -= charShift; //shift down by charShift
			ch[i] = c;
			i++;
		}
		String decrypted = new String(ch); //switch back to String
		return decrypted;
	}

	private void addPersonHash(String detailToAdd, HashMap<String, ArrayList<Person>> hashToAdd, Person toAdd){
		if(hashToAdd.containsKey(detailToAdd)){
			ArrayList<Person> tmp = new ArrayList<Person>();
			tmp = hashToAdd.get(detailToAdd);
			tmp.add(toAdd);
			hashToAdd.remove(detailToAdd);
			hashToAdd.put(detailToAdd, tmp);
		}
		else{
			ArrayList<Person> tmp = new ArrayList<Person>();
			tmp.add(toAdd);
			hashToAdd.put(detailToAdd, tmp);
		}
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

		addPersonHash(firstName, firstNameHash, toAdd);
		addPersonHash(lastName, lastNameHash, toAdd);

		//PHONE AND ADDRESS NUMBER CANNOT BE CALLED SINCE THEY HAVE LONG + INT NOT STRING
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

		addPersonHash(addressRoad, addressRoadHash, toAdd);
		addPersonHash(addressCity, addressCityHash, toAdd);
		addPersonHash(addressProvince, addressProvinceHash, toAdd);
		addPersonHash(addressCountry, addressCountryHash, toAdd);
		addPersonHash(addressPostalCode, addressPostalCodeHash, toAdd);

	}

	private String[] firstCheck(HashMap<String, ArrayList<Person>> hashToCheck, String detailToCheck, boolean matchCheck, ArrayList<Person> searching, ArrayList<Person> tmp){
		String tmp1 = "";

		if(matchCheck){
			System.out.print(detailToCheck + ": ");
			tmp1 = scan.nextLine();
			if(tmp1.equals("menu") || tmp1.equals("Menu")){
				tmp1 = "menu";
			}
			if(tmp1.equals("exit") || tmp1.equals("Exit")){
				System.exit(0);
			}
			if(!tmp1.equals("") && (!tmp1.equals("menu"))){
				try{
					tmp = hashToCheck.get(tmp1);
					searching.addAll(tmp);
				}
				catch(Exception e){
					System.out.println(ANSI_RED + "ERROR! "+ ANSI_RESET + detailToCheck + " not found");
					matchCheck = false;
				}
			}
		}

		String[] strArray = new String[2];
		strArray[0] = tmp1;
		strArray[1] = String.valueOf(matchCheck);

		return strArray;
	}

	@SuppressWarnings("unchecked")
	public String searchPeople(){ //FIX ADDRESS NUMBER AND PHONE NUMBER
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

		String[] strArray = firstCheck(firstNameHash, "First Name", match, searching, tmp);
		firstName = strArray[0];
		if(firstName.equals("menu")) return "menu";
		match = Boolean.parseBoolean(strArray[1]);

		strArray = firstCheck(lastNameHash, "Last Name", match, searching, tmp);
		lastName = strArray[0];
		if(lastName.equals("menu")) return "menu";
		match = Boolean.parseBoolean(strArray[1]);

		if(match){
			while(tmptmp1){
				System.out.print("Phone Number: ");
				phoneNumber = scan.nextLine();
				if(phoneNumber.equals("menu") || phoneNumber.equals("Menu")){
					return "menu";
				}
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
				if(addressNumber.equals("menu") || addressNumber.equals("Menu")){
					return "menu";
				}
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

		strArray = firstCheck(addressRoadHash, "Address Road", match, searching, tmp);
		addressRoad = strArray[0];
		if(addressRoad.equals("menu")) return "menu";
		match = Boolean.parseBoolean(strArray[1]);

		strArray = firstCheck(addressCityHash, "City", match, searching, tmp);
		addressCity = strArray[0];
		if(addressCity.equals("menu")) return "menu";
		match = Boolean.parseBoolean(strArray[1]);

		strArray = firstCheck(addressProvinceHash, "Province", match, searching, tmp);
		addressProvince = strArray[0];
		if(addressProvince.equals("menu")) return "menu";
		match = Boolean.parseBoolean(strArray[1]);

		strArray = firstCheck(addressCountryHash, "Country", match, searching, tmp);
		addressCountry = strArray[0];
		if(addressCountry.equals("menu")) return "menu";
		match = Boolean.parseBoolean(strArray[1]);

		strArray = firstCheck(addressPostalCodeHash, "Postal Code", match, searching, tmp);
		addressPostalCode = strArray[0];
		if(addressPostalCode.equals("menu")) return "menu";
		match = Boolean.parseBoolean(strArray[1]);

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
			if(!firstName.equals("")){
				for(int i = 0; i < searching.size(); i++){
					if(searching.get(i).getFirstName().equals(firstName)){
						tmp.add(searching.get(i));
					}
				}

				searching = (ArrayList<Person>)tmp.clone();
			}

			//Last Name
			tmp.clear();
			if(!lastName.equals("")){
				for(int i = 0; i < searching.size(); i++){
					if(searching.get(i).getLastName().equals(lastName)){
						tmp.add(searching.get(i));
					}
				}

				searching = (ArrayList<Person>)tmp.clone();
			}

			//Phone Number
			tmp.clear();
			if(!phoneNumber.equals("")){
				for(int i = 0; i < searching.size(); i++){
					Long phoneNumberTmp1 = Long.parseLong(phoneNumber, 10);
					Long phoneNumberTmp2 = searching.get(i).getPhoneNumber();
					if(phoneNumberTmp2.equals(phoneNumberTmp1)){
						tmp.add(searching.get(i));
					}
				}

				searching = (ArrayList<Person>)tmp.clone();
			}

			//Address Number
			tmp.clear();
			if(!addressNumber.equals("")){
				for(int i = 0; i < searching.size(); i++){
					Integer addressNumberTmp1 = Integer.parseInt(addressNumber);
					Integer addressNumberTmp2 = searching.get(i).getAddressNumber();
					if(addressNumberTmp2.equals(addressNumberTmp1)){
						tmp.add(searching.get(i));
					}
				}

				searching = (ArrayList<Person>)tmp.clone();
			}

			//Address Road
			tmp.clear();
			if(!addressRoad.equals("")){
				for(int i = 0; i < searching.size(); i++){
					if(searching.get(i).getAddressRoad().equals(addressRoad)){
						tmp.add(searching.get(i));
					}
				}

				searching = (ArrayList<Person>)tmp.clone();
			}

			//City
			tmp.clear();
			if(!addressCity.equals("")){
				for(int i = 0; i < searching.size(); i++){
					if(searching.get(i).getAddressCity().equals(addressCity)){
						tmp.add(searching.get(i));
					}
				}

				searching = (ArrayList<Person>)tmp.clone();
			}

			//Province
			tmp.clear();
			if(!addressProvince.equals("")){
				for(int i = 0; i < searching.size(); i++){
					if(searching.get(i).getAddressProvince().equals(addressProvince)){
						tmp.add(searching.get(i));
					}
				}

				searching = (ArrayList<Person>)tmp.clone();
			}

			//Country
			tmp.clear();
			if(!addressCountry.equals("")){
				for(int i = 0; i < searching.size(); i++){
					if(searching.get(i).getAddressCountry().equals(addressCountry)){
						tmp.add(searching.get(i));
					}
				}

				searching = (ArrayList<Person>)tmp.clone();
			}

			//Postal Code
			tmp.clear();
			if(!addressPostalCode.equals("")){
				for(int i = 0; i < searching.size(); i++){
					if(searching.get(i).getAddressPostalCode().equals(addressPostalCode)){
						tmp.add(searching.get(i));
					}
				}

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
				HashDataList hashtmp = new HashDataList();
				for(int i = 0; i < searching.size(); i++){
					hashtmp.addPerson(searching.get(i), false);
				}
				if(Main.printUserChoice(hashtmp).equals("menu")) return "menu";
			}
		}

		//Refering to the top checking function for basic check
		if(!continueCheck && !checkNoInputs){
			System.out.println("     -- " + ANSI_RED + "FAIL! No Matching Users Found!" + ANSI_RESET);
		}

		else if (!continueCheck && checkNoInputs){
			System.out.println("     -- " + ANSI_RED + "FAIL! No Data Given!" + ANSI_RESET);
		}

		

		return "";
	}

	private static void copyFile(File source, File dest) throws IOException {
 	   Files.copy(source.toPath(), dest.toPath());
	}

	public void saveData(){

		Person toSave = new Person();

		try {

			System.out.println("\nSaving Data to DATA + DATA BACKUP Files:");
			System.out.print("     -- Creating DATA File.");
	      	// Creates a FileWriter DATA
	      	FileWriter file = new FileWriter("../data/DATA.txt");
	      	System.out.println(" " + ANSI_GREEN + ANSI_CHECKMARK + ANSI_RESET);

	      	System.out.print("     -- Creating DATA BACKUP File.");
	      	//Create backup file
	      	FileWriter fileBackup = new FileWriter("../data/DATABACKUP.txt");
	      	System.out.println(" " + ANSI_GREEN + ANSI_CHECKMARK + ANSI_RESET);

	      	// Creates a BufferedWriter
	      	BufferedWriter output = new BufferedWriter(file);

	      	System.out.print("     -- Writing Data to DATA File.");
	      	output.write(informationDatabase.size() + "\n");

	      	// Writes the string to the file
	      	for(int i = 0; i < informationDatabase.size(); i++){
		    	toSave = informationDatabase.get(i);
		      	output.write(encrypt(toSave.getFirstName(), 20) + "\n");
		      	output.write(encrypt(toSave.getLastName(), 20) + "\n");
		      	output.write(encrypt(String.valueOf(toSave.getPhoneNumber()), 20) + "\n");
		      	output.write(encrypt(String.valueOf(toSave.getAddressNumber()), 20) + "\n");
		      	output.write(encrypt(toSave.getAddressRoad(), 20) + "\n");
		      	output.write(encrypt(toSave.getAddressCity(), 20) + "\n");
		      	output.write(encrypt(toSave.getAddressProvince(), 20) + "\n");
		      	output.write(encrypt(toSave.getAddressCountry(), 20) + "\n");
		      	output.write(encrypt(toSave.getAddressPostalCode(), 20) + "\n");
		      	output.write(encrypt(toSave.getDescription(), 20) + "\n");
	      	}

	      	// Closes the writer
	      	output.close();
	      	System.out.println(" " + ANSI_GREEN + ANSI_CHECKMARK + ANSI_RESET);
	    }

	    catch (Exception e) {
	    	System.out.println(" " + ANSI_RED + "x" + ANSI_RESET);
	    }

	    //Create the backup
	    try{
	    	System.out.print("     -- Copying DATA File to DATA BACKUP File.");
		    Path original = Paths.get("../data/DATA.txt");
			Path backup = Paths.get("../data/DATABACKUP.txt");
			Files.copy(original, backup, StandardCopyOption.REPLACE_EXISTING);
			System.out.println(" " + ANSI_GREEN + ANSI_CHECKMARK + ANSI_RESET);

			if(informationDatabase.size() <= 1){
				System.out.println(ANSI_GREEN + "SUCCESS! " + ANSI_RESET + informationDatabase.size() + " Person's Information Has Been Saved To The Files.");
			}
			if(informationDatabase.size() > 1){
				System.out.println(ANSI_GREEN + "SUCCESS! " + ANSI_RESET + informationDatabase.size() + " People's Information Have Been Saved To The Files."); //Grammar's important
			}
			System.out.println("-------------------");
		}
		catch(Exception e){
			System.out.println(" " + ANSI_RED + "x" + ANSI_RESET);
		}
	}


	public void loadData(){
		Person toLoad = new Person();
		String s;
		int i = 0;
		int i1 = i;

		try{
			//try everything together and if something doesn't work it's because there is missing DATA in or the File itself
			System.out.println("\nLoading Data from File:");
			System.out.print("     -- Opening DATA File.");
			BufferedReader br = new BufferedReader(new FileReader("../data/DATA.txt"));
			System.out.println(" " + ANSI_GREEN + ANSI_CHECKMARK + ANSI_RESET);

			System.out.print("     -- Reading and Storing Data Locally.");
			i = Integer.parseInt(br.readLine());
			i1 = i;

			while(i != 0){
				toLoad.setFirstName(decrypt(br.readLine(), 20));
				toLoad.setLastName(decrypt(br.readLine(), 20));
				toLoad.setPhoneNumber(Long.parseLong(decrypt(br.readLine(), 20)));
				toLoad.setAddressNumber(Integer.parseInt(decrypt(br.readLine(), 20)));
				toLoad.setAddressRoad(decrypt(br.readLine(), 20));
				toLoad.setAddressCity(decrypt(br.readLine(), 20));
				toLoad.setAddressProvince(decrypt(br.readLine(), 20));
				toLoad.setAddressCountry(decrypt(br.readLine(), 20));
				toLoad.setAddressPostalCode(decrypt(br.readLine(), 20));
				toLoad.setDescription(decrypt(br.readLine(), 20));

				addPerson(toLoad, true);
				toLoad = new Person();

				i--;
			}

			br.close();
			System.out.println(" " + ANSI_GREEN + ANSI_CHECKMARK + ANSI_RESET);
			if(i1 <= 1){
				System.out.println(ANSI_GREEN + "Success! " + ANSI_RESET + i1 + " Person's Information Has Been Loaded Locally.");
			}
			if(i1 > 1){
				System.out.println(ANSI_GREEN + "Success! " + ANSI_RESET + i1 + " People's Information Have Been Loaded Locally."); //Grammar my friend
			}
			System.out.println("-------------------");
		}
		catch(Exception e){
			System.out.println(" " + ANSI_RED + "x" + ANSI_RESET); //x from where it would stop
			Main.troubleshootMissingFile(false,false); //whatever crashes it means there is no data
			loadData(); //after troubleshooting we try again
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