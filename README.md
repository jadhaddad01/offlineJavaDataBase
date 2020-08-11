# Offline Java DataBase 
This project is allows for the local secure storage of people's personal information, protected by a password and encryption to the data. 
 
#### Table of Contents 
- [Description](#desc)
- [Usage Guide](#inst)
  * [Tutorial](#tuto)
  * [Dependencies](#depd)
  * [Installation](#inst1)
  * [Running Program](#runp)
- [Troubleshooting](#trbl)
- [Test Examples](#tstx)
- [Requirements](#rqrm)
  * [User Stories](#usrs)
  * [Use Cases](#uscs)
- [Architecture](#arch)
  * [Patterns and Frameworks](#ptfr)
  * [Design Diagram](#dsdg)
  * [Modifying the Program](#modp)
- [Encryption / Decryption](#encr)
  * [Encryption Method](#encrmethod)
  * [Decryption Method](#decrmethod)
- [Contributors](#cont)
- [License](#lics)

<a name="desc"></a>
## Description

This project is created using only the Java language. Using Ceasar Shift Cypher (see [Encryption / Decryption](#encr)) allows for the secure storage of data and the creation of a password that would eventually decrypt the data. The data is comprised of people's personal information (name, phone number, address, etc.). Prevelent methods include but are not limited to: printing the entire decrypted list of people, adding people to the list, searching for certain people in the list using known information, and changing the password.


<a name="inst"></a>
## Usage Guide

<a name="tuto"></a>
### Tutorial
[![Watch the video](https://img.youtube.com/vi/8hMP5crzj6c/maxresdefault.jpg)](https://youtu.be/8hMP5crzj6c)

<a name="depd"></a>
### Dependencies
- Requires Java JDK 7 (java, javac) or higher to run.
- Can run on Windows, Mac, or Linux.

<a name="inst1"></a>
### Installation Guide
1. Clone repository to your system OR press code -> Download ZIP.
2. If download as ZIP, extract offlineJavaDataBase-master.zip and access the extracted folder.
3. Access the /src folder and copy its path. (../offlineJavaDataBase-master/src)
4. Open terminal and cd into the src path.
5. Compile Main.java

Terminal:
```
cd ../offlineJavaDataBase-master/src
javac Main.java
```
<a name="runp"></a>
### Running the Program
1. Make sure you have followed the [Installation Guide](#inst1).
2. Run the Program.
3. When asked for a password, type: **password** .

Terminal:
```
java Main
Please enter your password: password
```
<a name="trbl"></a>
## Troubleshooting
The program already can process 2 types of errors independently when running.
1. If DATA.txt (the personal data file) is missing or has no data, the program will create a new one, or restore it if it finds DATABACKUP.txt .
2. If PASS.txt (the password file) is missing or has no data, the program will create a new one and assign **password** as the new password.

Issues Solved:
| **Issue**      | **Solution**     |
|------------|--------------|
|I forgot my password | Delete PASS.txt in ../data/ and run the program, as it will give you a new password.|
|Someone changed the data in DATA.txt | Delete DATA.txt in ../data/ and run the program, as it will restore the good data from DATABACKUP.txt .|

<a name="tstx"></a>
## Test Examples
For tests and examples of how to use the program, click [here](https://github.com/jadhaddad01/offlineJavaDataBase/wiki/Tests)

<a name="rqrm"></a>
## Requirements
<a name="usrs"></a>
### User Stories
- As a government agency, we want personal information to be stored locally and encrypted so that only we can see the data.
- As an IT support in that government agency, I want information on what the program is doing and if a certain area of the program fails, to know where to be able troubleshoot the program easily.
- As a government agency supervisor, I want to be able to change the password so that I have control over the program.
<a name="uscs"></a>
### Use Cases
- All external data has to be encrypted and can only be decrypted inside the program.
- A password protects for unintended decryption and can be changed.
- If the external files such as the data and pass files are erased or are deleted, the program should troubleshoot and create new ones and restore them if possible.
- The UI should be easily readable and understood.

<a name="arch"></a>
## Architecture
<a name="ptfr"></a>
### Patterns and Frameworks
**Frameworks:**
- Constructors, getters and setters, toString methods (Person.java, HashDataList.java).
- HashMaps and ArrayLists (HashDataList.java).
- Java libraries used below:
```java
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
```

**Patterns:**

For recurring code in a method, it is simplified to a call to another private in the same class. 
```java
//add Person to Hash with each detail of information instead of writing the code 10 times
private void addPersonHash(String detailToAdd, HashMap<String, ArrayList<Person>> hashToAdd, Person toAdd){
	if(hashToAdd.containsKey(detailToAdd)){ //if the piece of information already exists in a Hash we only add to the array list in the hash
		ArrayList<Person> tmp = new ArrayList<Person>();
		tmp = hashToAdd.get(detailToAdd);
		tmp.add(toAdd);
		hashToAdd.remove(detailToAdd);
		hashToAdd.put(detailToAdd, tmp);
	}
	else{ //if the piece of information doesn't exist in a Hash already we create a new hash
		ArrayList<Person> tmp = new ArrayList<Person>();
		tmp.add(toAdd);
		hashToAdd.put(detailToAdd, tmp);
	}
}
```
Thus, instead of writing the above code 7 times, we only write it once and call it with one line 7 times as shown below.
```java
addPersonHash(firstName, firstNameHash, toAdd); //we add the person with the first name to its hash
addPersonHash(lastName, lastNameHash, toAdd); //we add the person with the last name to its hash
addPersonHash(addressRoad, addressRoadHash, toAdd); //we add the person with the road to its hash
addPersonHash(addressCity, addressCityHash, toAdd); //we add the person with the city to its hash
addPersonHash(addressProvince, addressProvinceHash, toAdd); //we add the person with the province to its hash
addPersonHash(addressCountry, addressCountryHash, toAdd); //we add the person with the country to its hash
addPersonHash(addressPostalCode, addressPostalCodeHash, toAdd); //we add the person with the pc to its hash
```
		
Also, when running a complex system of code such as loading or saving data, encrypting and testing a new password, and much more, it is important to let the user know what exactly is happening and if a subaction of a complex system is successful or fails.

Terminal:
```
Saving New Password.
     -- Encrypting New Password. ✓
     -- Testing Encryption Key. ✓
     -- Writing Encryption Key to File. ✓
     -- Testing Encryption Key from File. ✓
-------------------
```
This lets the user know, if at any point there is a failure, where that failure has happened.

<a name="dsdg"></a>
### Design Diagram
The following design diagram is a class diagram of the system. This diagram has been drawn using [Umple](https://www.umple.org/)
![alt text](https://github.com/jadhaddad01/offlineJavaDataBase/blob/master/img/umple.png)

<a name="modp"></a>
### Modifying the Program
It is recommended that only those who understand the Java language and how to program in Java modify this project and its code. It is also recommended that only additions to the program and its code are made, to prevent failures and errors in the program.
With that said, it is highly encouraged that anyone who would like to modify this program and its code, do exactly that.

**Helpful hints and tips for modification:**
- A template for an additional choice in the menu is commented in the main method and menu method. If you uncomment it, you would be able to add an extra element to the menu.
- Given that a new menu element is created, an example to practice would be to create a RemovePerson method in HashDataList.java . Steps to making the method work are:
  * Ask the user for the password using the existing passCheck method in Main.java .
  * Search for the person you want to remove using searchPeople method in HashDataList.java and out of the given people, create a method that chooses one of them.
  * To finish, you will have to remove the Person from his/her information's HashMaps and then finally from the main ArrayList informationDatabase.
  * Refer to the addPerson method in HashDataList for helpful information on the manipulation of HashMaps and ArrayLists.
- The above example is a possibility, but anyone is free to do anything he/she wants with the code.
- Most methods return a String in the case that "menu" has been inputted, so that we can quickly go back to the menu.


<a name="encr"></a>
## Encryption / Decryption
The encryption used in this project is the [Ceasar Shift Cypher](https://en.wikipedia.org/wiki/Caesar_cipher). This encryption shifts the characters of a String up by a certain number of characters in the [ASCII](https://www.ascii-code.com/) alphabet.

The decryption uses the same principle, but instead of shifting upwards, it shifts the encrypted String downwards by the same amount to regain the original String.

In the source code, the personal data file uses a shift of 20 characters while the password file uses a shift of 10.

<a name="encrmethod"></a>
### Encryption Method
```java
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
```

<a name="decrmethod"></a>
### Decryption Method
```java
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
```

<a name="cont"></a>
## Contributors
- Jad Haddad : jadmail01@gmail.com

<a name="lics"></a>
## License
This project is licensed under the GPL-3.0 License. [License Details](../master/LICENSE)
