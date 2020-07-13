public class Person{
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

	public Person(){
		return;
	}

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

	public String getFirstName(){
		return this.firstName;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getLastName(){
		return this.lastName;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public long getPhoneNumber(){
		return this.phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber){
		this.phoneNumber = phoneNumber;
	}

	public int getAddressNumber(){
		return this.addressNumber;
	}

	public void setAddressNumber(int addressNumber){
		this.addressNumber = addressNumber;
	}

	public String getAddressRoad(){
		return this.addressRoad;
	}

	public void setAddressRoad(String addressRoad){
		this.addressRoad = addressRoad;
	}

	public String getAddressCity(){
		return this.addressCity;
	}

	public void setAddressCity(String addressCity){
		this.addressCity = addressCity;
	}

	public String getAddressProvince(){
		return this.addressProvince;
	}

	public void setAddressProvince(String addressProvince){
		this.addressProvince = addressProvince;
	}

	public String getAddressCountry(){
		return this.addressCountry;
	}

	public void setAddressCountry(String addressCountry){
		this.addressCountry = addressCountry;
	}

	public String getAddressPostalCode(){
		return this.addressPostalCode;
	}

	public void setAddressPostalCode(String addressPostalCode){
		this.addressPostalCode = addressPostalCode;
	}

	public String getDescription(){
		return this.description;
	}

	public void setDescription(String description){
		this.description = description;
	}

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
		return (b1 && b2 && b3 && b4 && b5 && b6 && b7 && b8 && b9);
	}

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