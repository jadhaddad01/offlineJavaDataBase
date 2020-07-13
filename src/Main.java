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
				System.out.println(ANSI_GREEN + "Success. "+ ANSI_RESET + "Your New Password is: " + ANSI_BOLD + genPass + ANSI_RESET);
				System.out.println("---------------------------------------");
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
				}
				catch(Exception e){
					System.out.println(" " + ANSI_RED + "x" + ANSI_RESET);
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
				}
				catch(Exception e){
					System.out.println(" " + ANSI_RED + "x" + ANSI_RESET);
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

	public static void main(String[] args) {
		//Intro
		introOutro(true); //true for intro

		//Checking Password
		passCheck();

		//Creating and Loading HashData List
		HashDataList list = new HashDataList();
		list.loadData();



		//Save data and create a backup
		list.saveData();

		//Outro
		introOutro(false); //false for outro
		
	}
}