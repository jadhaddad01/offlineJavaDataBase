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

public class Test{
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

	//basic variables
	private static Scanner scan = new Scanner(System.in);
	private static BufferedReader br;
	private static BufferedReader brNewPass;
	private static FileWriter file;
	private static BufferedWriter output;
	private static String passInTxt;
	private static String passInTxtNewPass;
	private static String exit;
	private static String menuInt;
	private static boolean correctAnswer = true;
	private static boolean flag = true;
	private static boolean menu = true;
	private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss z");
	private static long start;
	private static long end;
	private static String passWord;
	private static boolean failedEncryption = false;
	private static boolean failedDecryption = false;
	private static boolean passwordcheck = true;
	private static boolean newPassChecks = true;
	private static String stringRepeat = "";
	private static final String decryptedPassKey = "tTMcXvpuRw23czfgEsQrMjf5Xjqwp7tLp6pLR5uVPkHS8AS2DPaGcy22F3HhN9AHbRzEynvhEA7bJTqVR66Hswe4rFvtFDEFwhjuvngi";
	private static String encryptedPassKey;
	private static String newPass;
	private static String newPassConfirm;
	private static boolean continueNewPass = true;
	private static HashDataList list;

	//Encrypt String to another String --> DONE
	private static String encrypt(String toBeEncrypted, String passWord){
		try{
            String text = toBeEncrypted;
            String key = passWord; // 128 bit key
            // Create key and cipher
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            // encrypt the text
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(text.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b: encrypted) {
                sb.append((char)b);
            }

            // the encrypted String
            String enc = sb.toString();
            return enc;

        }
        catch(Exception e){
            System.out.println(ANSI_RED + "Encryption failed. WRONG PASSWORD." + ANSI_RESET);
            failedEncryption = true;
        }
        return "Encryption Failed.";
	}

	//Decrypt encrypted String to another String --> DONE
	private static String decrypt(String toBeDecrypted, String passWord){
		try{
            String text = toBeDecrypted;
            String key = passWord; // 128 bit key
            // Create key and cipher
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            // now convert the string to byte array
            // for decryption
            byte[] bb = new byte[toBeDecrypted.length()];
            for (int i=0; i<toBeDecrypted.length(); i++) {
                bb[i] = (byte) toBeDecrypted.charAt(i);
            }

            // decrypt the text
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            String decrypted = new String(cipher.doFinal(bb));
            return decrypted;
        }
        catch(Exception e){
            System.out.println(ANSI_RED + "Decryption failed. WRONG PASSWORD." + ANSI_RESET);
            failedDecryption = true;
        }
        return "Decryption Failed.";
	}


	public static void main(String[] args){
		//Introduction --> DONE
		Date startDate = new Date();
    	System.out.println(sdf.format(startDate));
    	System.out.println("Welcome to your personal database, Master.");

    	try{
			br = new BufferedReader(new FileReader("../data/PASS.txt"));
		}
		catch(Exception e){
			System.out.println("TXT File NOT FOUND.");
			System.exit(0);
		}
		
		//Password check --> DONE
    	System.out.print("Please enter your password: ");
    	passWord = scan.nextLine();
    	if(passWord.equals("exit") || passWord.equals("Exit")){
    		System.exit(0);
    	}
    	try{
	    	passInTxt = br.readLine();
		    br.close();
		}
		catch(Exception e){
			System.out.println("NO DATA IN FILE.");
			System.exit(0);
		}
    	while(passwordcheck){
    		if(decrypt(passInTxt,passWord).equals(decryptedPassKey)){
    			System.out.println(ANSI_GREEN + "Decryption Success." + ANSI_RESET);
    			passwordcheck = false;
    		}
	    	else{
	    		System.out.print("Retype Password: ");
	    		passWord = scan.nextLine();
	    		if(passWord.equals("exit") || passWord.equals("Exit")){
		    		System.exit(0);
		    	}
	    	}
    	}
    	System.out.print("-------------------");

    	/*
    	//For custom -- everytime
    	for(int i = 0; i <= passWord.length(); i++){
    		stringRepeat += "-";
    	}
    	if(passWord.length() == 0){
    		stringRepeat = "";
    	}
    	System.out.println("---------------------------" + stringRepeat);
    	*/
    	/*
    	//Ask for password!!
    	passWord = "Ao.4eZ(Dx.Nnbx[;";
    	String toDecrypt = encrypt("Jad is amazing",passWord);
    	decrypt(toDecrypt, passWord);
    	*/

    	//Loading...
		start = System.currentTimeMillis();
		System.out.println("\n\nLoading Database into system.");

			//Creating Datalist
		System.out.print("     -- Creating DataList.");
		try{
			list = new HashDataList();
			System.out.println(" " + ANSI_GREEN + ANSI_CHECKMARK + ANSI_RESET);
		}
		catch(Exception e){
			System.out.println(" " + ANSI_RED + "x" + ANSI_RESET);
		}

			//Loading Data to DataList
		System.out.print("     -- Loading Data to DataList.");
		try{
			list.loadData();
			System.out.println(" " + ANSI_GREEN + ANSI_CHECKMARK + ANSI_RESET);
		}
		catch(Exception e){
			System.out.println(" " + ANSI_RED + "x" + ANSI_RESET);
		}

		end = System.currentTimeMillis();
		for(int i = 0; i <= (end - start)/10; i++){
    		stringRepeat += "-";
    	}
    	if((end - start) < 10){
    		stringRepeat = "-";
    	}
		System.out.println("Loading finished in " + (end - start) + "ms."); 
		System.out.println("--------------------" + stringRepeat +  "---");

		while(flag){
			//Reset while loop values
			flag = true;
			correctAnswer = true;
			menu = true;
			passwordcheck = true;
			continueNewPass = true;

			//Menu --> DONE
			System.out.println("\n\nInformation Database Menu.\n--------------------------");
			System.out.println("1 -> " + ANSI_BLUE + "Print" + ANSI_RESET + " Full Database.");
			System.out.println("2 -> " + ANSI_YELLOW + "Search" + ANSI_RESET + " a Specific Person.");
			System.out.println("3 -> " + ANSI_GREEN + "Add" + ANSI_RESET + " Person to Database.");
			System.out.println("4 -> " + ANSI_PURPLE + "Password" + ANSI_RESET + " Change for Login.");
			/*System.out.println("5 -> " + ANSI_RED + "Remove" + ANSI_RESET + " Person from Database.");*/
			System.out.println("--------------------------");

				//Selection
			System.out.print("Choice: ");
			menuInt = scan.nextLine();
			if(menuInt.equals("exit") || menuInt.equals("Exit")){
	    		System.exit(0);
	    	}
			while(menu){
				if(menuInt.equals("1") || menuInt.equals("2") || menuInt.equals("3") || menuInt.equals("4")/* || menuInt.equals("5")*/){
					menu = false;
				}
				if(!menuInt.equals("1") && !menuInt.equals("2") && !menuInt.equals("3") && !menuInt.equals("4")/* && !menuInt.equals("5")*/){
					System.out.println("Please choose a number between 1 and 5. [1 -> " + ANSI_BLUE + "Print" + ANSI_RESET + "]");
					System.out.println("                                        [2 -> " + ANSI_YELLOW + "Search" + ANSI_RESET + "]");
					System.out.println("                                        [3 -> " + ANSI_GREEN + "Add" + ANSI_RESET + "]");
					System.out.println("                                        [4 -> " + ANSI_PURPLE + "Password" + ANSI_RESET + "]");
					/*System.out.println("                                        [5 -> " + ANSI_RED + "Remove" + ANSI_RESET + "]");*/
					System.out.print("Choice: ");
					menuInt = scan.nextLine();
					if(menuInt.equals("exit") || menuInt.equals("Exit")){
			    		System.exit(0);
			    	}
				}
			}

			//Choice #1: Print Entire List
			if(menuInt.equals("1")){
				System.out.println("\nPrint Database Requested.");
				System.out.println(list);
			}

			//Choice #2: Search Specific Person
			if(menuInt.equals("2")){
				System.out.println("\nSearch Person Requested.");
				list.searchPeople();
			}

			//Choice #3: Add Person
			if(menuInt.equals("3")){
				System.out.println("\nAdd Person Requested.");
				
			}

			//Choice #5: Password Change --> FINISHED
			if(menuInt.equals("4")){
				//Check Password to be able to switch
				System.out.println("\nPassword Change Requested.");
				System.out.println(ANSI_PURPLE_BACKGROUND + "Step 1" + ANSI_RESET +": Enter your Old Password.");
				System.out.print("Please enter your password: ");
		    	passWord = scan.nextLine();
		    	if(passWord.equals("exit") || passWord.equals("Exit")){
		    		System.exit(0);
		    	}
		    	while(passwordcheck){
		    		if(decrypt(passInTxt,passWord).equals(decryptedPassKey)){
		    			System.out.println(ANSI_GREEN + "Decryption Success." + ANSI_RESET);
		    			passwordcheck = false;
		    		}
			    	else{
			    		System.out.print("Retype Password: ");
			    		passWord = scan.nextLine();
			    		if(passWord.equals("exit") || passWord.equals("Exit")){
				    		System.exit(0);
				    	}
			    	}
		    	}

		    	//Choosing a New Password and Changing Encryption Key to the New One.
		    	System.out.println(ANSI_PURPLE_BACKGROUND + "Step 2" + ANSI_RESET +": Choose a New Password (The New Password Has to be 16 Basic Characters Long.");
		    	while(newPassChecks){
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
			    		System.out.println(ANSI_RED + "Confirmation Does Not Match. Enter New Password Again." + ANSI_RESET);
			    	}
			    	else if(newPass.length() != 16){
			    		System.out.println(ANSI_RED + "New Password is Not 16 Characters Long. Enter New Password Again." + ANSI_RESET);
			    	}
			    	else{
			    		start = System.currentTimeMillis();
			    		System.out.println("\nSaving New Password.");
			    		//Creating Encryption Key
			    		System.out.print("     -- Encrypting New Password.");
						try{
							encryptedPassKey = encrypt(decryptedPassKey,newPass);
							System.out.println(" " + ANSI_GREEN + ANSI_CHECKMARK + ANSI_RESET);
						}
						catch(Exception e){
							System.out.println(" " + ANSI_RED + "x" + ANSI_RESET);
							continueNewPass = false;
						}
						//Testing Encryption Key
						System.out.print("     -- Testing Encryption Key.");
						if(decrypt(encryptedPassKey,newPass).equals(decryptedPassKey) && continueNewPass){
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

							if(decrypt(passInTxtNewPass,newPass).equals(decryptedPassKey)){
								System.out.println(" " + ANSI_GREEN + ANSI_CHECKMARK + ANSI_RESET);
								newPassChecks = false; 
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
		    	end = System.currentTimeMillis();
		    	System.out.println(ANSI_GREEN + "Success. "+ ANSI_RESET + "New Password Changed in " + (end - start) + " ms.");
		    	System.out.println("--------------------------------------");


			}

			/*
			//Choice #5: Remove Specific Person
			if(menuInt.equals("5")){
				System.out.println("\nRemove Person Requested.");
			}
			*/

			//Exit --> DONE
			System.out.println("\nExit? (Y/N)");
			System.out.print("Choice: ");
			exit = scan.nextLine();
			if(exit.equals("exit") || exit.equals("Exit")){
	    		System.exit(0);
	   		}
			while(correctAnswer){			
				if(exit.equals("Y") || exit.equals("y")){
					flag = false;
					correctAnswer = false;
				}
				if(exit.equals("N") || exit.equals("n")){
					correctAnswer = false;
				}
				if(!exit.equals("Y") && !exit.equals("y") && !exit.equals("N") && !exit.equals("n")){
					System.out.println("Y == Yes (Quit Program) || N == No (Return to Menu)");
					System.out.print("Choice: ");
					exit = scan.nextLine();
					if(exit.equals("exit") || exit.equals("Exit")){
			    		System.exit(0);
			    	}
				}
			}
			System.out.println("---------");
		}

		/*
		//Encrypt password to file
		String s = encrypt(decryptedPassKey,"Ao.4eZ(Dx.Nnbx[;");
		try {
	      // Creates a FileWriter
	      FileWriter file = new FileWriter("../data/PASS.txt");

	      // Creates a BufferedWriter
	      BufferedWriter output = new BufferedWriter(file);

	      // Writes the string to the file
	      output.write(s);

	      // Closes the writer
	      output.close();

	      System.out.println(s);
	    }

	    catch (Exception e) {
	      e.getStackTrace();
	    }
	    */


		System.out.println("\n\nHave a good day, Master.");
		Date endDate = new Date();
		System.out.println(sdf.format(endDate));




		/*Person jad = new Person();
		Person josh = new Person();
		josh.setFirstName("Josh");
		DataList people = new DataList();
		System.out.println(people);
		System.out.println(people.getSize());
		System.out.println(people.getCapacity());

		people.addPerson(jad);
		people.addPerson(jad);
		people.addPerson(jad);
		people.addPerson(jad);
		System.out.println(people);
		System.out.println(people.getSize());
		System.out.println(people.getCapacity());

		people.addPerson(josh);
		people.addPerson(jad);
		people.addPerson(jad);
		people.addPerson(jad);
		people.addPerson(jad);
		people.addPerson(jad);
		people.addPerson(jad);
		people.addPerson(jad);
		people.addPerson(jad);
		people.addPerson(josh);
		people.addPerson(jad);
		people.addPerson(jad);
		people.addPerson(jad);
		people.addPerson(jad);
		people.addPerson(jad);
		people.addPerson(jad);
		people.addPerson(jad);
		people.addPerson(jad);
		people.addPerson(jad);
		people.addPerson(jad);
		System.out.println(people);
		System.out.println(people.getSize());
		System.out.println(people.getCapacity());

		int[] j = people.findPeopleIndexFirstName("Josh");
		for(int i = 0; i < j.length; i++){
			System.out.println(j[i]);
		} //fix findpeople
		*/

	}
}