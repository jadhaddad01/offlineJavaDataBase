//Author: Jad Haddad

public class Person{
	//all variables start unknows for if we add a Person and don't know some information it will start as that
	private String firstName = "Unknown";
	private String lastName = "Unknown";
	private long phoneNumber = -1l;
	private int addressNumber = -1;
	private String addressRoad = "Unknown";
	private String addressCity = "Unknown";
	private String addressProvince = "Unknown";
	private String addressCountry = "Unknown";
	private String addressPostalCode = "Unknown";
	private String description = "Unknown";
	//private Person - brother, sister, mother, father, friend, boss....;

	//an empty constructor
	public Person(){
		return;
	}

	//constructor with information
	public Person(String firstName, String lastName, long phoneNumber, int addressNumber, String addressRoad, String addressCity, String addressProvince, String addressCountry, String addressPostalCode, String description){
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.addressNumber = addressNumber;
		this.addressRoad = addressRoad;
		this.addressCity = addressCity;
		this.addressProvince = addressProvince;
		this.addressCountry = addressCountry;
		this.addressPostalCode = addressPostalCode;
		this.description = description;
	}

	//first name getter
	public String getFirstName(){
		return this.firstName;
	}

	//first name setter
	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	//last name getter
	public String getLastName(){
		return this.lastName;
	}

	//last name setter
	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	//phone num getter
	public long getPhoneNumber(){
		return this.phoneNumber;
	}

	//phone num setter
	public void setPhoneNumber(long phoneNumber){
		this.phoneNumber = phoneNumber;
	}

	//add num getter
	public int getAddressNumber(){
		return this.addressNumber;
	}

	//add num setter
	public void setAddressNumber(int addressNumber){
		this.addressNumber = addressNumber;
	}

	//add rd getter
	public String getAddressRoad(){
		return this.addressRoad;
	}

	//add rd setter
	public void setAddressRoad(String addressRoad){
		this.addressRoad = addressRoad;
	}

	//add city getter
	public String getAddressCity(){
		return this.addressCity;
	}

	//add city setter
	public void setAddressCity(String addressCity){
		this.addressCity = addressCity;
	}

	//add province getter
	public String getAddressProvince(){
		return this.addressProvince;
	}

	//add province setter
	public void setAddressProvince(String addressProvince){
		this.addressProvince = addressProvince;
	}

	//add country getter
	public String getAddressCountry(){
		return this.addressCountry;
	}

	//add country setter
	public void setAddressCountry(String addressCountry){
		this.addressCountry = addressCountry;
	}

	//add pc getter
	public String getAddressPostalCode(){
		return this.addressPostalCode;
	}

	//add pc setter
	public void setAddressPostalCode(String addressPostalCode){
		this.addressPostalCode = addressPostalCode;
	}

	//desc getter
	public String getDescription(){
		return this.description;
	}

	//desc setter
	public void setDescription(String description){
		this.description = description;
	}

	//checking if 2 Persons are equal
	public boolean isEqual(Person person){
		boolean b1 = (this.firstName == person.getFirstName());
		boolean b2 = (this.lastName == person.getLastName());
		boolean b3 = (this.phoneNumber == person.getPhoneNumber());
		boolean b4 = (this.addressNumber == person.getAddressNumber());
		boolean b5 = (this.addressRoad == person.getAddressRoad());
		boolean b6 = (this.addressCity == person.getAddressCity());
		boolean b7 = (this.addressProvince == person.getAddressProvince());
		boolean b8 = (this.addressCountry == person.getAddressCountry());
		boolean b9 = (this.addressPostalCode == person.getAddressPostalCode());
		return (b1 && b2 && b3 && b4 && b5 && b6 && b7 && b8 && b9); //everything in the 2 people have to be the same except Description since they will likely be added in defferent times
	}

	//printing method Override
	public String toString(){
		String s = "";
		s += "\nName: " + getFirstName() + " " + getLastName() + "\n";
		s += "Phone number: " + getPhoneNumber() + "\n";
		s += "Address: " + getAddressNumber() + " " + getAddressRoad() + ", " + getAddressCity() + ", " + getAddressProvince() + ", " + getAddressCountry() + "\n";
		s += "PC: " + getAddressPostalCode() + "\n";
		s += "Description: " + getDescription() + "\n";
		
		return s;
	}
}