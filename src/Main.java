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

public class Main{
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

	//Scanner
	private static Scanner scan = new Scanner(System.in);

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

	private static void introOutro(boolean intro){ //saving a little real estate by making the outro and intro in one method
		if(!intro) System.out.println("Have a good day, Master.");

		//Showing the date
		DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss z");
		Date date = new Date();
		System.out.println(sdf.format(date));

		if(intro) System.out.println("Welcome to your personal database, Master.");
	}

	private static boolean saveEncryptTestPass(String pass){
		boolean continuePassSave = true;

		//Creating Encryption Key
		System.out.print("     -- Encrypting New Password.");
		String encPass = "";
		try{
			encPass = encrypt(pass,10);
			System.out.println(" " + ANSI_GREEN + ANSI_CHECKMARK + ANSI_RESET);
		}
		catch(Exception e){
			System.out.println(" " + ANSI_RED + "x" + ANSI_RESET);
			continuePassSave = false;
		}

		//Testing Encryption Key
		System.out.print("     -- Testing Encryption Key.");
		if(decrypt(encPass,10).equals(pass) && continuePassSave){
			System.out.println(" " + ANSI_GREEN + ANSI_CHECKMARK + ANSI_RESET);
		}
		else{
			System.out.println(" " + ANSI_RED + "x" + ANSI_RESET);
			continuePassSave = false;
		}

		//Writing New Encryption Key to PASS.txt
		System.out.print("     -- Writing Encryption Key to File.");
		if(continuePassSave){
			try {
		      BufferedWriter output = new BufferedWriter(new FileWriter("../data/PASS.txt"));
		      output.write(encPass);
		      output.close();
		      System.out.println(" " + ANSI_GREEN + ANSI_CHECKMARK + ANSI_RESET);
		    }

		    catch (Exception e) {
		      System.out.println(" " + ANSI_RED + "x" + ANSI_RESET);
		      continuePassSave = false;
		    }
		}
		else{
			System.out.println(" " + ANSI_RED + "x" + ANSI_RESET);
			continuePassSave = false;
		}

		//Testing Encryption Key from File
		System.out.print("     -- Testing Encryption Key from File.");
		if(continuePassSave){
			String passInTxtNewPass = "";
			try{
				BufferedReader brNewPass = new BufferedReader(new FileReader("../data/PASS.txt"));
				passInTxtNewPass = brNewPass.readLine();
				brNewPass.close();
			}
			catch(Exception e){
				System.out.println(" " + ANSI_RED + "x" + ANSI_RESET);
				continuePassSave = false;
			}

			if(decrypt(passInTxtNewPass,10).equals(pass) && continuePassSave){
				System.out.println(" " + ANSI_GREEN + ANSI_CHECKMARK + ANSI_RESET);
			}
			else{
				System.out.println(" " + ANSI_RED + "x" + ANSI_RESET);
				continuePassSave = false;
			}
		}
		else{
			System.out.println(" " + ANSI_RED + "x" + ANSI_RESET);
		    continuePassSave = false;
		}

		return continuePassSave;
	}

	public static void troubleshootMissingFile(boolean truePassFalseData, boolean missingData){
		if(truePassFalseData){
			if(!missingData){
				System.out.println(ANSI_RED + "\nCRITICAL ERROR! PASS.txt File Not Found!" + ANSI_RESET);
				System.out.println("Troubleshooting Missing Pass file:");
			}
			else{
				System.out.println(ANSI_RED + "\nERROR! No Data Found in PASS.txt!" + ANSI_RESET);
				System.out.println("Troubleshooting Missing Data in Pass file:");
			}

			boolean continueTroubleShoot = true;
			//Create generic pass
			System.out.print("     -- Creating Generic Password With New File.");
			String genPass = "";
			try{
				genPass = "password";
				FileWriter passFile = new FileWriter("../data/PASS.txt");
				System.out.println(" " + ANSI_GREEN + ANSI_CHECKMARK + ANSI_RESET);
			}
			catch(Exception e){
				System.out.println(" " + ANSI_RED + "x" + ANSI_RESET);
				continueTroubleShoot = false;
			}

			//Encrypt generic Pass
			if(continueTroubleShoot){
				saveEncryptTestPass(genPass);
				System.out.println(ANSI_GREEN + "Success! "+ ANSI_RESET + "Your New Password is: " + ANSI_BOLD + genPass + ANSI_RESET);

				//For custom -----...
				String stringRepeat = "";
		    	for(int i = 0; i <= genPass.length(); i++){
		    		stringRepeat += "-";
		    	}
		    	System.out.println("-------------------");
			}
		}
		else{ //MAKE TROUBLESHOOT FOR DATA.TXT
			System.out.println(ANSI_RED + "\nCRITICAL ERROR! DATA.txt File Error!" + ANSI_RESET);
			System.out.println("Troubleshooting DATA file Error:");

			//Check if there is DATABACKUP
			boolean checkBackup = true;
			System.out.print("     -- Checking for DATABACKUP.");
			try{
				BufferedReader br = new BufferedReader(new FileReader("../data/DATABACKUP.txt"));
				System.out.println(" " + ANSI_GREEN + ANSI_CHECKMARK + ANSI_RESET);
			}
			catch(Exception e){
				System.out.println(" " + ANSI_RED + "x" + ANSI_RESET);
				checkBackup = false;
			}

			if(checkBackup){
				System.out.print("     -- Creating DATA file and Copying DATABACKUP to DATA File.");
				try{
					FileWriter file = new FileWriter("../data/DATA.txt"); //Create a new file DATA.txt
				    Path original = Paths.get("../data/DATABACKUP.txt");
					Path backup = Paths.get("../data/DATA.txt");
					Files.copy(original, backup, StandardCopyOption.REPLACE_EXISTING);
					System.out.println(" " + ANSI_GREEN + ANSI_CHECKMARK + ANSI_RESET);

					//success with how many people restored
					BufferedReader br = new BufferedReader(new FileReader("../data/DATA.txt"));
					int i = Integer.parseInt(br.readLine());
					if(i <= 1){ //1 or 0
						System.out.println(ANSI_GREEN + "Success! " + ANSI_RESET +  i + " Person's Information Has Been Restored.");
					}
					if(i > 1){
						System.out.println(ANSI_GREEN + "Success! " + ANSI_RESET +  i + " People's Information Have Been Restored."); //Grammar is important my friend
					}
					System.out.println("-------------------");
				}
				catch(Exception e){ //If this happens there is a fault in the memory and should work the next time tried
					System.out.println(" " + ANSI_RED + "x" + ANSI_RESET);
					System.out.println(ANSI_RED + "FAIL! System Shutting Down!");
					System.exit(0);
				}
			}
			else{
				System.out.print("     -- Creating and Initializing DATA file.");
				try{
					FileWriter file = new FileWriter("../data/DATA.txt"); //Create a new file DATA.txt
					BufferedWriter output = new BufferedWriter(file);
			      	output.write("0"); //default size of list is 0
			      	output.close();
			      	System.out.println(" " + ANSI_GREEN + ANSI_CHECKMARK + ANSI_RESET);
			      	System.out.println(ANSI_GREEN + "Success! " + ANSI_RESET + "An Empty DATA File Has Been Restored.");
				}
				catch(Exception e){ //If this happens there is a fault in the memory and should work the next time tried
					System.out.println(" " + ANSI_RED + "x" + ANSI_RESET);
					System.out.println(ANSI_RED + "FAIL! System Shutting Down!");
					System.exit(0);
				}
			}
		}
	}

	private static void passCheck(){
		//Check if PASS.txt is here and if it isn't we create a new one with password
		BufferedReader br;
		try{
			br = new BufferedReader(new FileReader("../data/PASS.txt"));
		}
		catch(Exception e){
			troubleshootMissingFile(true, false);
		}

		try{
			br = new BufferedReader(new FileReader("../data/PASS.txt")); //cannot cause error since would be solved by previous code
			if (br.readLine() == null) {
				troubleshootMissingFile(true, true);
			}
		}
		catch(Exception e){
			//we use try here because it won't compile when using the BufferedReader without it 
		}

		//Check input and test if password matches decrypted version of the encrypted key
		System.out.print("\nPlease enter your password: ");
		String passInTxt = "";
    	String passWord = scan.nextLine();
    	if(passWord.equals("exit") || passWord.equals("Exit")){
    		System.exit(0);
    	}
    	try{
    		br = new BufferedReader(new FileReader("../data/PASS.txt")); //we need to initialize and it is impossible for this to cause error since it would be solved in the previous lines
	    	passInTxt = br.readLine();
		    br.close();
		}
		catch(Exception e){
			//cannot give error since if it would it would be solved in the tries before
		}

		boolean passwordcheck = true;
    	while(passwordcheck){
    		if(decrypt(passInTxt,10).equals(passWord)){
    			System.out.println(ANSI_GREEN + "Decryption Success." + ANSI_RESET);
    			passwordcheck = false;
    		}
	    	else{
	    		System.out.println(ANSI_RED + "Decryption Failed." + ANSI_RESET);
	    		System.out.print("Retype Password: ");
	    		passWord = scan.nextLine();
	    		if(passWord.equals("exit") || passWord.equals("Exit")){
		    		System.exit(0);
		    	}
	    	}
    	}
    	System.out.println("-------------------");
	}

	private static String menu(){
		System.out.println("\n\nInformation Database Menu.\n--------------------------");
		System.out.println("1 -> " + ANSI_BLUE + "Print" + ANSI_RESET + " Full Database.");
		System.out.println("2 -> " + ANSI_YELLOW + "Search" + ANSI_RESET + " a Specific Person.");
		System.out.println("3 -> " + ANSI_GREEN + "Add" + ANSI_RESET + " Person to Database.");
		System.out.println("4 -> " + ANSI_PURPLE + "Password" + ANSI_RESET + " Change for Login.");
		System.out.println("5 -> " + ANSI_RED + "Exit" + ANSI_RESET + " Program. (Will Save Progress)");
		/*System.out.println("6 -> " + ANSI_RED + "Remove" + ANSI_RESET + " Person from Database.");*/
		System.out.println("--------------------------");

		//Selection
		System.out.print("Choice: ");
		String menuInt = scan.nextLine();
		if(menuInt.equals("exit") || menuInt.equals("Exit")){
    		System.exit(0);
    	}

    	boolean wrongChoice = true;
		while(wrongChoice){
			if(menuInt.equals("1") || menuInt.equals("2") || menuInt.equals("3") || menuInt.equals("4") || menuInt.equals("5") /* || menuInt.equals("6")*/){
				wrongChoice = false;
			}
			if(!menuInt.equals("1") && !menuInt.equals("2") && !menuInt.equals("3") && !menuInt.equals("4") && !menuInt.equals("5") /* && !menuInt.equals("6")*/){
				System.out.println("Please choose a number between 1 and 5. [1 -> " + ANSI_BLUE + "Print" + ANSI_RESET + "]");
				System.out.println("                                        [2 -> " + ANSI_YELLOW + "Search" + ANSI_RESET + "]");
				System.out.println("                                        [3 -> " + ANSI_GREEN + "Add" + ANSI_RESET + "]");
				System.out.println("                                        [4 -> " + ANSI_PURPLE + "Password" + ANSI_RESET + "]");
				System.out.println("                                        [5 -> " + ANSI_RED + "Exit" + ANSI_RESET + "]");
				/*System.out.println("                                        [6 -> " + ANSI_RED + "Remove" + ANSI_RESET + "]");*/
				System.out.print("Choice: ");
				menuInt = scan.nextLine();
				if(menuInt.equals("exit") || menuInt.equals("Exit")){
		    		System.exit(0);
		    	}
			}
		}

		return menuInt;
	}

	public static void printUserChoice(HashDataList list){
		System.out.println("----------------------------");
		System.out.println("1 -> " + ANSI_BLUE + "Print to Console." + ANSI_RESET);
		System.out.println("2 -> " + ANSI_YELLOW + "Print to Custom File." + ANSI_RESET);
		System.out.println("----------------------------");

		System.out.print("Choice: ");
		String menuInt = scan.nextLine();
		if(menuInt.equals("exit") || menuInt.equals("Exit")){
			System.exit(0);
		}

		boolean menu = true;
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

			for(int i = 0; i < list.sizeInformationDatabase(); i++){
				System.out.println(list.getPerson(i));
			}
			
		}

		if(menuInt.equals("2")){
			System.out.println("\nPrint to Custom File Requested.");
			System.out.println("-------------------------------");
			String fileName = "";

			boolean flag2 = true;
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
				String confirmFileName = scan.nextLine();
				if(confirmFileName.equals("exit") || confirmFileName.equals("Exit")){
		    		System.exit(0);
		   		}

		   		boolean flag1 = true;
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
		      	FileWriter file = new FileWriter("../data/" + fileName);

		      	// Creates a BufferedWriter
		      	BufferedWriter output = new BufferedWriter(file);

		      	// Writes the string to the file
		      	Person person = new Person();
		      	for(int i = 0; i < list.sizeInformationDatabase(); i++){
			    	person = list.getPerson(i);
			      	output.write("Name: " + person.getFirstName() + " " + person.getLastName() + "\n");
			      	output.write("Phone number: " + person.getPhoneNumber() + "\n");
			      	output.write("Address: " + person.getAddressNumber() + " " + person.getAddressRoad() + ", " + person.getAddressCity() + ", " + person.getAddressProvince() + ", " + person.getAddressCountry() + "\n");
			      	output.write("PC: " + person.getAddressPostalCode() + "\n");
			      	output.write("Description: " + person.getDescription() + "\n\n");
		      	}

		    	if(list.sizeInformationDatabase() == 1){
					System.out.println(ANSI_GREEN + "SUCCESS! " + fileName + " has been created with " + list.sizeInformationDatabase() + " Individual's Information" + ANSI_RESET);
					System.out.println("The file is located at the /data directory of the master directory.\n");
				}
				if(list.sizeInformationDatabase() > 1){
					System.out.println(ANSI_GREEN + "SUCCESS! " + fileName + " has been created with " + list.sizeInformationDatabase() + " Individuals' Information" + ANSI_RESET);
					System.out.println("The file is located at the /data directory of the master directory.\n");
				}

		      	// Closes the writer
		      	output.close();
		    }

		    catch (Exception e) {
		      e.getStackTrace();
		    }
		}
	}

	private static String inputedDetails(String detailID){
		if(detailID.equals("Phone Number")){
			boolean flagPhone = true;
			String input = "";
			while(flagPhone){
				System.out.print(detailID + ": ");
				input = scan.nextLine();
				if(input.equals("exit") || input.equals("Exit")){
					System.exit(0);
				}
				if(!input.equals("")){
					long tmpPhone = -1l;
					try{
						tmpPhone = Long.parseLong(input, 10);
						flagPhone = false;
					}
					catch(Exception e){
						System.out.println(ANSI_RED + "ERROR! "+ ANSI_RESET + "Malicious input. Please try again.");
					}
				}
				else{
					flagPhone = false;
				}
			}

			return input;
		}
		else if(detailID.equals("Address Number")){
			boolean flagAddNum = true;
			String input = "";
			while(flagAddNum){
				System.out.print(detailID + ": ");
				input = scan.nextLine();
				if(input.equals("exit") || input.equals("Exit")){
					System.exit(0);
				}
				if(!input.equals("")){
					long tmpAddNum = -1l;
					try{
						tmpAddNum = Integer.parseInt(input);
						flagAddNum = false;
					}
					catch(Exception e){
						System.out.println(ANSI_RED + "ERROR! "+ ANSI_RESET + "Malicious input. Please try again.");
					}
				}
				else{
					flagAddNum = false;
				}
			}

			return input;
		}


		else{
			System.out.print(detailID + ": ");
			String input = scan.nextLine();
			if(input.equals("exit") || input.equals("Exit")){
				System.exit(0);
			}

			return input;
		}
	}

	private static void addPeople(HashDataList list){
		System.out.println(ANSI_PURPLE_BACKGROUND + "Step 1" + ANSI_RESET +": Enter individual's information.\n(Press Enter to skip unknown information)\n");
		String firstName = inputedDetails("First Name");
		String lastName = inputedDetails("Last Name");
		String phoneNumber = inputedDetails("Phone Number");
		String addressNumber = inputedDetails("Address Number");
		String addressRoad = inputedDetails("Address Road");
		String addressCity = inputedDetails("City");
		String addressProvince = inputedDetails("Province");
		String addressCountry = inputedDetails("Country");
		String addressPostalCode = inputedDetails("Postal Code");
		String description = inputedDetails("Description");

		Person toAdd = new Person();

		boolean checkIfInputted = false;
		if(!firstName.equals("")){ toAdd.setFirstName(firstName); checkIfInputted = true;}
		if(!lastName.equals("")) {toAdd.setLastName(lastName); checkIfInputted = true;}
		if(!phoneNumber.equals("")) {toAdd.setPhoneNumber(Long.parseLong(phoneNumber, 10)); checkIfInputted = true;}
		if(!addressNumber.equals("")) {toAdd.setAddressNumber(Integer.parseInt(addressNumber)); checkIfInputted = true;}
		if(!addressRoad.equals("")) {toAdd.setAddressRoad(addressRoad); checkIfInputted = true;}
		if(!addressCity.equals("")) {toAdd.setAddressCity(addressCity); checkIfInputted = true;}
		if(!addressProvince.equals("")) {toAdd.setAddressProvince(addressProvince); checkIfInputted = true;}
		if(!addressCountry.equals("")) {toAdd.setAddressCountry(addressCountry); checkIfInputted = true;}
		if(!addressPostalCode.equals("")) {toAdd.setAddressPostalCode(addressPostalCode); checkIfInputted = true;}
		if(!description.equals("")) {toAdd.setDescription(description); checkIfInputted = true;}

		if(checkIfInputted){
			list.addPerson(toAdd, false);
			System.out.println(ANSI_GREEN + "Success! " + ANSI_RESET + "1 Person's Information Has Been Added.");
		}
		else{
			System.out.println(ANSI_RED + "Fail!" + ANSI_RESET + " No Information Given.");
		}

	}

	private static void changePass(){
		//Old Pass check
		System.out.print(ANSI_PURPLE_BACKGROUND + "Step 1" + ANSI_RESET +": Enter your Old Password.");
		passCheck();

		//Choosing new pass
		System.out.print("\n");
		System.out.println(ANSI_PURPLE_BACKGROUND + "Step 2" + ANSI_RESET +": Choose a New Password.");
		boolean newPassFlag = true;
		boolean continueNewPass = true;
		String newPass = "";
		String newPassConfirm = "";

		String encryptedPassKey = "";
		String passInTxtNewPass = "";

		FileWriter file;
		BufferedWriter output;
		BufferedReader brNewPass = null;

		while(newPassFlag){
			System.out.print("Enter a New Password: ");
    		newPass = scan.nextLine();
    		if(newPass.equals("exit") || newPass.equals("Exit")){
	    		System.exit(0);
	    	}
	    	System.out.print("Confirm New Password: ");
	    	newPassConfirm = scan.nextLine();
	    	if(newPassConfirm.equals("exit") || newPassConfirm.equals("Exit")){
	    		System.exit(0);
	    	}
	    	if(!newPass.equals(newPassConfirm)){
	    		System.out.println(ANSI_RED + "Error!" + ANSI_RESET + "Confirmation Does Not Match. Enter New Password Again.");
	    	}
	    	else if(newPass.equals("")){
	    		System.out.println(ANSI_RED + "Error!" + ANSI_RESET + "Password Cannot Be Null. Enter New Password Again.");
	    	}
	    	else{
	    		System.out.println("\nSaving New Password.");
	    		//Creating Encryption Key
	    		System.out.print("     -- Encrypting New Password.");
				try{
					encryptedPassKey = encrypt(newPass,10);
					System.out.println(" " + ANSI_GREEN + ANSI_CHECKMARK + ANSI_RESET);
				}
				catch(Exception e){
					System.out.println(" " + ANSI_RED + "x" + ANSI_RESET);
					continueNewPass = false;
				}
				//Testing Encryption Key
				System.out.print("     -- Testing Encryption Key.");
				if(decrypt(encryptedPassKey,10).equals(newPass) && continueNewPass){
					System.out.println(" " + ANSI_GREEN + ANSI_CHECKMARK + ANSI_RESET);
				}
				else{
					System.out.println(" " + ANSI_RED + "x" + ANSI_RESET);
					continueNewPass = false;
				}
				//Writing New Encryption Key to PASS.txt
				System.out.print("     -- Writing Encryption Key to File.");
				if(continueNewPass){
					try {
				      file = new FileWriter("../data/PASS.txt");
				      output = new BufferedWriter(file);
				      output.write(encryptedPassKey);
				      output.close();
				      System.out.println(" " + ANSI_GREEN + ANSI_CHECKMARK + ANSI_RESET);
				    }

				    catch (Exception e) {
				      System.out.println(" " + ANSI_RED + "x" + ANSI_RESET);
				      continueNewPass = false;
				    }
				}
				else{
					System.out.println(" " + ANSI_RED + "x" + ANSI_RESET);
					continueNewPass = false;
				}
				//Testing Encryption Key from File
				System.out.print("     -- Testing Encryption Key from File.");
				if(continueNewPass){
					try{
						brNewPass = new BufferedReader(new FileReader("../data/PASS.txt"));
					}
					catch(Exception e){
						System.out.println("TXT File NOT FOUND.");
						System.exit(0);
					}

					try{
				    	passInTxtNewPass = brNewPass.readLine();
					    brNewPass.close();
					}
					catch(Exception e){
						System.out.println("NO DATA IN FILE.");
						System.exit(0);
					}

					if(decrypt(passInTxtNewPass,10).equals(newPass)){
						System.out.println(" " + ANSI_GREEN + ANSI_CHECKMARK + ANSI_RESET);
						newPassFlag = false; 
					}
					else{
						System.out.println(" " + ANSI_RED + "x" + ANSI_RESET);
						continueNewPass = false;
					}
				}
				else{
					System.out.println(" " + ANSI_RED + "x" + ANSI_RESET);
				    continueNewPass = false;
				}
	    	}
		}
		System.out.println("-------------------");
	}

	public static void main(String[] args) {
		//Intro
		introOutro(true); //true for intro

		//Checking Password
		passCheck();

		//Creating and Loading HashData List
		HashDataList list = new HashDataList();
		list.loadData();

		boolean backToMenu = true;
		//CONTINUE TO CHANGE PASS
		while(backToMenu){
			String choice = menu();

			if(choice.equals("1")){
				System.out.println("\nPrint Database Requested. Where Would You Like To Print?");
				printUserChoice(list);
			}

			if(choice.equals("2")){
				System.out.println("\nSearch People Requested.");
				list.searchPeople();
			}
			
			if(choice.equals("3")){
				System.out.println("\nAdd Person Requested.");
				addPeople(list);
			}

			if(choice.equals("4")){
				System.out.println("\nChange Password Requested");
				changePass();
			}

			if(choice.equals("5")){
				break;
			}

		}

		//Save data and create a backup
		list.saveData();

		//Outro
		introOutro(false); //false for outro
		
	}
}