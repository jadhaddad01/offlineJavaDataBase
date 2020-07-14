//Author: Jad Haddad

//important imports
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

	//intro outro method helps us save coding space when true is intro and false is outro
	private static void introOutro(boolean intro){ //saving a little real estate by making the outro and intro in one method
		if(!intro) System.out.println("\nHave a good day, Chef."); //the user is always #1 in software

		//Showing the date is useless but impresses the ladies
		DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss z");
		Date date = new Date();
		System.out.println(sdf.format(date));

		if(intro) System.out.println("Welcome to your personal database, Chef.");
	}

	//Testing, Encrypting and then storing a given password
	private static boolean saveEncryptTestPass(String pass){
		boolean continuePassSave = true;

		//Creating Encryption Key
		System.out.print("     -- Encrypting New Password."); //these already tell you what is happening so no need for comments
		String encPass = "";
		try{
			encPass = encrypt(pass,10); //we encrypt with a shift of 10 to change it up from the data encrytion so that people will take 2 minutes instead of 1 to crack the code
			System.out.println(" " + ANSI_GREEN + ANSI_CHECKMARK + ANSI_RESET);
		}
		catch(Exception e){
			System.out.println(" " + ANSI_RED + "x" + ANSI_RESET);
			continuePassSave = false;
		}

		//Testing Encryption Key
		System.out.print("     -- Testing Encryption Key.");
		if(decrypt(encPass,10).equals(pass) && continuePassSave){ //and decrypt with a shift of 10
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

	//if DATA DATABACKUP or PASS are missing we troubleshoot the issue here.
	public static void troubleshootMissingFile(boolean truePassFalseData, boolean missingData){
		if(truePassFalseData){ //Troubleshooting for PASS.TXT
			if(!missingData){
				System.out.println(ANSI_RED + "\nCRITICAL ERROR! PASS.txt File Not Found!" + ANSI_RESET); //if the PASS file is not there someone is messing with your files
				System.out.println("Troubleshooting Missing Pass file:");
			}
			else{
				System.out.println(ANSI_RED + "\nERROR! No Data Found in PASS.txt!" + ANSI_RESET); //can happen if someone erased the data in the file
				System.out.println("Troubleshooting Missing Data in Pass file:");
			}

			boolean continueTroubleShoot = true;
			//Create generic pass
			System.out.print("     -- Creating Generic Password With New File.");
			String genPass = "";
			try{
				genPass = "password"; //if you want to change the generic password to give people change it here
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
		else{ //Troubleshooting for DATA.TXT
			System.out.println(ANSI_RED + "\nCRITICAL ERROR! DATA.txt File Error!" + ANSI_RESET);
			System.out.println("Troubleshooting DATA file Error:");

			//Check if there is DATABACKUP
			boolean checkBackup = true;
			System.out.print("     -- Checking for DATABACKUP.");
			try{
				BufferedReader br = new BufferedReader(new FileReader("../data/DATABACKUP.txt")); //if databackup is available we don't lose any data
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
					Files.copy(original, backup, StandardCopyOption.REPLACE_EXISTING); //copying databackup to new data file
					System.out.println(" " + ANSI_GREEN + ANSI_CHECKMARK + ANSI_RESET);

					//success with how many people restored
					BufferedReader br = new BufferedReader(new FileReader("../data/DATA.txt"));
					int i = Integer.parseInt(br.readLine()); //we just read how many people there is and make a beautiful success message that we restored them
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

	//we check for the password to be able to change the password and to be able to enter the program
	private static void passCheck(){
		//Check if PASS.txt is here and if it isn't we create a new one with password
		BufferedReader br;
		try{
			br = new BufferedReader(new FileReader("../data/PASS.txt"));
		}
		catch(Exception e){
			troubleshootMissingFile(true, false); //if pass doesn't exist we troubleshoot
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
    	//we don't use back to menu here since you would be able to go to the program without entering a passcode
    	if(passWord.equals("exit") || passWord.equals("Exit")){ //force exit program
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
		//if we don't have a successful password entry we try again
    	while(passwordcheck){
    		if(decrypt(passInTxt,10).equals(passWord)){
    			System.out.println(ANSI_GREEN + "Decryption Success." + ANSI_RESET);
    			passwordcheck = false;
    		}
	    	else{
	    		System.out.println(ANSI_RED + "Decryption Failed." + ANSI_RESET);
	    		System.out.print("Retype Password: ");
	    		passWord = scan.nextLine();
	    		if(passWord.equals("exit") || passWord.equals("Exit")){ //force exit program
		    		System.exit(0);
		    	}
	    	}
    	}
    	System.out.println("-------------------");
	}

	//main menu program to choose what to do
	private static String menu(){
		System.out.println("\n\nInformation Database Menu.\n--------------------------");
		System.out.println("1 -> " + ANSI_BLUE + "Print" + ANSI_RESET + " Full Database.");
		System.out.println("2 -> " + ANSI_YELLOW + "Search" + ANSI_RESET + " a Specific Person.");
		System.out.println("3 -> " + ANSI_GREEN + "Add" + ANSI_RESET + " Person to Database.");
		System.out.println("4 -> " + ANSI_PURPLE + "Password" + ANSI_RESET + " Change for Login.");
		System.out.println("5 -> " + ANSI_RED + "Exit" + ANSI_RESET + " Program. (Will Save Progress)");
		/*System.out.println("6 -> " + ANSI_RED + "Remove" + ANSI_RESET + " Person from Database.");*/ //templates as well as under in the menuInt check to add menu items
		System.out.println("--------------------------");

		//Selection
		System.out.println(ANSI_PURPLE_BACKGROUND + "P.S" + ANSI_RESET + " -> At Any Input: [exit -> Force Quit Program.]\n                     [menu -> Return to Menu]"); //P.S for if we need to come back here from any input or exit the program
		System.out.print("Choice: ");
		String menuInt = scan.nextLine();
		if(menuInt.equals("menu") || menuInt.equals("Menu")){
    		return "menu";
    	}
		if(menuInt.equals("exit") || menuInt.equals("Exit")){
    		System.exit(0);
    	}

    	//we need to check if people have placed a good or bad input
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
		    	if(menuInt.equals("menu") || menuInt.equals("Menu")){
		    		return "menu";
		    	}
			}
		}

		return menuInt;
	}

	//where would the user like to print the list in a custom file or in the console? when we have 100000 people in the list it is better in the console
	public static String printUserChoice(HashDataList list){
		System.out.println("----------------------------");
		System.out.println("1 -> " + ANSI_BLUE + "Print to Console." + ANSI_RESET);
		System.out.println("2 -> " + ANSI_YELLOW + "Print to Custom File." + ANSI_RESET);
		System.out.println("----------------------------");

		System.out.print("Choice: ");
		String menuInt = scan.nextLine();
		if(menuInt.equals("menu") || menuInt.equals("Menu")){
			return "menu";
		}
		if(menuInt.equals("exit") || menuInt.equals("Exit")){
			System.exit(0);
		}

		//for bad inputs we retry
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
				if(menuInt.equals("menu") || menuInt.equals("Menu")){
					return "menu";
				}
				if(menuInt.equals("exit") || menuInt.equals("Exit")){
		    		System.exit(0);
		    	}
			}
		}

		if(menuInt.equals("1")){ //basic console printing
			System.out.println("\nPrint to Console Requested.");
			System.out.println("---------------------------");

			for(int i = 0; i < list.sizeInformationDatabase(); i++){
				System.out.println(list.getPerson(i));
			}
			
			return "";
		}

		else{ //if it equals 2 we make a custom file
			System.out.println("\nPrint to Custom File Requested.");
			System.out.println("-------------------------------");
			String fileName = "";

			//the user should choose the name of the file for grouping purposes maybe / the while loop is if a bad 
			boolean flag2 = true;
			while(flag2){
				System.out.println(ANSI_PURPLE_BACKGROUND + "1" + ANSI_RESET + " -- Please Choose a Name for your File. (" + ANSI_BOLD + "NAME" + ANSI_RESET + ".txt)"); 
				System.out.print("Choice for " + ANSI_BOLD + "NAME" + ANSI_RESET + ": ");
				fileName = scan.nextLine();
				if(fileName.equals("menu") || fileName.equals("Menu")){
					return "menu";
				}
				if(fileName.equals("exit") || fileName.equals("Exit")){
					System.exit(0);
				}

				System.out.print("\n");
				System.out.println(ANSI_PURPLE_BACKGROUND + "2" + ANSI_RESET + " -- Confirm File Name. "); //maybe people accidentally inputted a bad file name
				
				fileName = fileName + ".txt";
				System.out.println("File Name = " + ANSI_BOLD + fileName + ANSI_RESET + "\nConfirm? (Y/N)");
				System.out.print("Choice: ");
				String confirmFileName = scan.nextLine();
				if(confirmFileName.equals("menu") || confirmFileName.equals("Menu")){
		    		return "menu";
		   		}
				if(confirmFileName.equals("exit") || confirmFileName.equals("Exit")){
		    		System.exit(0);
		   		}

		   		//bad inputs need to be retried for confirmation
		   		boolean flag1 = true;
				while(flag1){
					if(confirmFileName.equals("Y") || confirmFileName.equals("y")){
						flag1 = false;
						flag2 = false;
					}
					if(confirmFileName.equals("N") || confirmFileName.equals("n")){
						flag1 = false;
					}
					if(!confirmFileName.equals("Y") && !confirmFileName.equals("y") && !confirmFileName.equals("N") && !confirmFileName.equals("n")){
						System.out.println("Y = Correct File Name | N = False File Name");
						System.out.print("Confirm? (" + ANSI_BOLD + fileName + ANSI_RESET + "): ");
						confirmFileName = scan.nextLine();
						if(confirmFileName.equals("menu") || confirmFileName.equals("Menu")){
				    		return "menu";
				    	}
						if(confirmFileName.equals("exit") || confirmFileName.equals("Exit")){
				    		System.exit(0);
				    	}
					}
				}

				//if people inputed nothing we retry
				if(fileName.equals(".txt")){
					System.out.println(ANSI_RED + "FAIL! .txt Not A Valid File Name!\n" + ANSI_RESET);
				}
			}

			//Print to file
			try {
		      	// Creates a FileWriter
		      	FileWriter file = new FileWriter("../data/" + fileName);

		      	// Creates a BufferedWriter
		      	BufferedWriter output = new BufferedWriter(file);

		      	// we want to neatly print out the information to the file to get a 5 star review on yelp
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
					System.out.println(ANSI_PURPLE_BACKGROUND + "P.S" + ANSI_RESET + " -> The file is located at the /data directory of the master directory.\n");
				}
				if(list.sizeInformationDatabase() > 1){
					System.out.println(ANSI_GREEN + "SUCCESS! " + fileName + " has been created with " + list.sizeInformationDatabase() + " Individuals' Information" + ANSI_RESET); //I'm not going to say it again but you know.
					System.out.println(ANSI_PURPLE_BACKGROUND + "P.S" + ANSI_RESET + " -> The file is located at the /data directory of the master directory.\n");
				}

		      	// Closes the writer
		      	output.close();
		    }

		    catch (Exception e) {
		      e.getStackTrace();
		    }

		    return "";
		}
	}

	//when we want to add a Person we want to let the user input their information
	private static String inputedDetails(String detailID){
		if(detailID.equals("Phone Number")){ //phone number is seperate to be able to parse Long
			boolean flagPhone = true;
			String input = "";
			while(flagPhone){
				System.out.print(detailID + ": ");
				input = scan.nextLine();
				if(input.equals("menu") || input.equals("Menu")){
					return "menu";
				}
				if(input.equals("exit") || input.equals("Exit")){
					System.exit(0);
				}
				if(!input.equals("")){
					long tmpPhone = -1l;
					try{
						tmpPhone = Long.parseLong(input, 10); //if the input cannot be parsed we retry
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
		else if(detailID.equals("Address Number")){ //address number is seperate to be able to parse int
			boolean flagAddNum = true;
			String input = "";
			while(flagAddNum){
				System.out.print(detailID + ": ");
				input = scan.nextLine();
				if(input.equals("menu") || input.equals("Menu")){
					return "menu";
				}
				if(input.equals("exit") || input.equals("Exit")){
					System.exit(0);
				}
				if(!input.equals("")){
					long tmpAddNum = -1l;
					try{
						tmpAddNum = Integer.parseInt(input); //if the input cannot be parsed we retry
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


		else{ //everything else is ok since we just need a String
			System.out.print(detailID + ": ");
			String input = scan.nextLine();
			if(input.equals("menu") || input.equals("Menu")){
					return "menu";
				}
			if(input.equals("exit") || input.equals("Exit")){
				System.exit(0);
			}

			return input;
		}
	}

	//Adding people by their relevant information
	private static String addPeople(HashDataList list){
		System.out.println(ANSI_PURPLE_BACKGROUND + "Step 1" + ANSI_RESET +": Enter individual's information.\n(Press Enter to skip unknown information)\n");
		String firstName = inputedDetails("First Name"); //we call the above method inputedDetails to save some coding real estate here
		if(firstName.equals("menu")) return "menu"; //if menu has been selected we return to menu
		String lastName = inputedDetails("Last Name");
		if(lastName.equals("menu")) return "menu";
		String phoneNumber = inputedDetails("Phone Number");
		if(phoneNumber.equals("menu")) return "menu";
		String addressNumber = inputedDetails("Address Number");
		if(addressNumber.equals("menu")) return "menu";
		String addressRoad = inputedDetails("Address Road");
		if(addressRoad.equals("menu")) return "menu";
		String addressCity = inputedDetails("City");
		if(addressCity.equals("menu")) return "menu";
		String addressProvince = inputedDetails("Province");
		if(addressProvince.equals("menu")) return "menu";
		String addressCountry = inputedDetails("Country");
		if(addressCountry.equals("menu")) return "menu";
		String addressPostalCode = inputedDetails("Postal Code");
		if(addressPostalCode.equals("menu")) return "menu";
		String description = inputedDetails("Description");
		if(description.equals("menu")) return "menu";

		Person toAdd = new Person(); //now we start building the new Person

		boolean checkIfInputted = false;
		if(!firstName.equals("")){ toAdd.setFirstName(firstName); checkIfInputted = true;} //if the String is "" we don't set the information and leave it as its intialized variable "Unknown"
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
			list.addPerson(toAdd, false); //false to add the Added on Date description
			System.out.println(ANSI_GREEN + "Success! " + ANSI_RESET + "1 Person's Information Has Been Added.");
			return "";
		}
		else{ //if no input was given we don't add the null Person
			System.out.println(ANSI_RED + "Fail!" + ANSI_RESET + " No Information Given.");
			return "";
		}

	}

	//if we get a generic pass or people are bored of their password we let them change it here
	private static String changePass(){
		//Old Pass check
		System.out.print(ANSI_PURPLE_BACKGROUND + "Step 1" + ANSI_RESET +": Enter your Old Password.");
		passCheck(); //you can't change a password without confirming that you are allowed to do so

		//Choosing new pass
		System.out.print("\n");
		System.out.println(ANSI_PURPLE_BACKGROUND + "Step 2" + ANSI_RESET +": Choose a New Password.");
		boolean continueNewPass = true;
		String newPass = "";
		String newPassConfirm = "";

		//new password has to match the confirming one and they cannot be null so we would retry until a good password is inputted.
		while(true){
			System.out.print("Enter a New Password: ");
			newPass = scan.nextLine();
			if(newPass.equals("menu") || newPass.equals("Menu")){
	    		return "menu";
	    	}
			if(newPass.equals("exit") || newPass.equals("Exit")){
	    		System.exit(0);
	    	}
	    	System.out.print("Confirm New Password: ");
	    	newPassConfirm = scan.nextLine();
	    	if(newPassConfirm.equals("menu") || newPassConfirm.equals("Menu")){
	    		return "menu";
	    	}
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
	    		saveEncryptTestPass(newPass);
	    		break;
	    	}
	    }

	    System.out.println(ANSI_GREEN + "SUCCESS! " + ANSI_RESET + "The New Password Has Been Created.");
		System.out.println("-------------------");
		return "";
	}

	//Finally this is the code that runs when you start the program
	public static void main(String[] args) {
		//Intro to say hello to the Chef
		introOutro(true); //true for intro

		//Checking Password to be able to enter the program menu
		passCheck();

		//Creating and Loading HashData List
		HashDataList list = new HashDataList();
		list.loadData(); //load from Data file

		//keep restarting the menu until 5 or exit is inputted
		while(true){
			String choice = menu();
			if(choice.equals("menu")) continue; //if menu is inputted we go to the top of the while loop

			if(choice.equals("1")){
				System.out.println("\nPrint Database Requested. Where Would You Like To Print?");
				if(printUserChoice(list).equals("menu")) continue; //if menu is inputted we go to the top of the while loop
			}

			if(choice.equals("2")){
				System.out.println("\nSearch People Requested.");
				if((list.searchPeople()).equals("menu")) continue; //if menu is inputted we go to the top of the while loop
			}
			
			if(choice.equals("3")){
				System.out.println("\nAdd Person Requested.");
				if(addPeople(list).equals("menu")) continue; //if menu is inputted we go to the top of the while loop
			}

			if(choice.equals("4")){
				System.out.println("\nChange Password Requested");
				if(changePass().equals("menu")) continue; //if menu is inputted we go to the top of the while loop
			}

			if(choice.equals("5")){
				break; //we get out of the while loop
			}

			/*
			if(choice.equals("6")){
				System.out.println("\nRemove Person Requested");
				if(removePerson().equals("menu")) continue; //if menu is inputted we go to the top of the while loop
			}
			*/

		}

		//Save data and create a backup
		list.saveData(); //save to data file

		//Outro to say goodbye
		introOutro(false); //false for outro
		
	}
}