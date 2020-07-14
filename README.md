# Offline Java DataBase 
This project is allows for the local secure storage of people's personal information, protected by a password and encryption to the data. 
 
#### Table of Contents 
- [Description](#desc)
- [Usage Guide](#inst)
  * [Dependencies](#depd)
  * [Installation](#inst1)
  * [Running Program](#runp)
- [Encryption / Decryption](#encr)
  * [Encryption Method](#encrmethod)
  * [Decryption Method](#decrmethod)
- [Contributors](#cont)
- [License](#lics)

<a name="desc"></a>
## Description

This project is created using only the Java language. Using Ceasar Shift Cypher (character shifting encryption) allows for the secure storage of data and the creation of a password that would eventually decrypt the data. The data is comprised of people's personal information (name, phone number, address, etc.). Prevelent methods include but are not limited to: printing the entire decrypted list of people, adding people to the list, searching for certain people in the list using known information, and changing the password.


<a name="inst"></a>
## Usage Guide
<a name="depd"></a>
### Dependencies
- Requires Java 7 or higher to run.
- Can run on Windows, Mac, or Linux.

<a name="depd"></a>
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
1. Make sure you have followed the [Installation Guide](#depd).
2. Run the Program.
3. When asked for a password, type: **password** .

Terminal:
```
java Main
Please enter your password: password
```

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
