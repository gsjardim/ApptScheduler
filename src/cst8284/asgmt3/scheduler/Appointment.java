
package cst8284.asgmt3.scheduler;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Pattern;

import cst8284.asgmt3.exception.BadAppointmentDataException;

import java.io.Serializable;
/**
 *The Appointment Class holds the information from several other classes, that are used to make a new Appointment object.
 *This class implements the Serializable interface.
 *@author Guilherme Jardim
 *@version 1.0
 **/
public class Appointment implements Serializable{
	/**
	 * of type Calendar, holds a reference to an instance of Calendar class;
	 */
	private Calendar aptDate;
	/**
	 * of type String, holds the client's first name.
	 */
	private String firstName;
	/**
	 * of type String, holds the client's last name.
	 */
	private String lastName;
	/**
	 * of type TelephoneNumber, holds a reference to an instance of TelephoneNumber, containing the client's phone number.
	 */
	private TelephoneNumber phone;
	/**
	 * of type Activity, holds a reference to an instance of Activity, containing the information about the activity 
	 * to be performed at the appointment.
	 */
	private Activity activity ;
	public static final long serialVersionUID = 1L;
	/**
	 * This four-args constructor takes four parameters: Calendar cal, String fullName, TelephoneNumber phone and Activity activity.
	 * It is overloaded with a five-args constructor; Using the String class methods substring and indexOf, it splits the parameter fullName into
	 * two separate String values. Then, these five values are passed on to the five-args constructor.
	 * @param cal Calendar - holds the reference value to an instance of Calendar.
	 * @param fullName String - holds a reference value to the String containing the client's full name, and is processed
	 * to generate the first and last name as separated values.
	 * @param phone TelephoneNumber - holds the reference value to an instance of TelephoneNumber.
	 * @param activity Activity - holds the reference value to an instance of Activity.
	 */
	public Appointment(Calendar cal, String fullName, TelephoneNumber phone, Activity activity) {
		
		this(cal, fullName.substring(0, fullName.indexOf(" ")+1), fullName.substring(fullName.indexOf(" ")+1,
				fullName.length()), phone, activity);
	}
	/**
	 * This five-args constructor takes five parameters: Calendar cal, String firstName, String lastName, TelephoNumber phone,
	 * and Activity activity.
	 * It sets the five private fields of the class with these parameters, by calling upon their respective methods: setAptDate, setFirstName, 
	 * setLastName, setPhone, setActivity. 
	 * @param cal Calendar - holds the reference value to an instance of Calendar.
	 * @param firstName String - holds a reference value to the String containing the client's first name
	 * @param lastName String - holds a reference value to the String containing the client's last name
	 * @param phone TelephoneNumber - holds the reference value to an instance of TelephoneNumber.
	 * @param activity Activity - holds the reference value to an instance of Activity.
	 */
	public Appointment(Calendar cal, String firstName, String lastName, TelephoneNumber phone, Activity activity) {
		this.setAptDate(cal);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setPhone(phone);
		this.setActivity(activity);
	}
	
	/**
	 * This method takes one parameter, Calendar cal, and sets the private field aptDate with its value.
	 * @param cal Calendar
	 */
	public void setAptDate(Calendar cal) {
		
		this.aptDate = cal;
	}
	/**
	 * Takes no parameter, returns the value of the private field aptDate.
	 * @return the reference value for the Calendar related to this object.
	 */
	public Calendar getAptDate() {
		return this.aptDate;
	}
	/**
	 * This method takes one parameter, String fName, and sets the private firstName with the value of fName.
	 * However, the field firstName is only set after passing it on to the method isNameCorrect. Only if this method returns true,
	 * firstName is set with fName's value.
	 * 
	 * @param fName String
	 */
	public void setFirstName(String fName) {
		
		if (isNameCorrect(fName)) this.firstName = fName;
	}
	/**
	 * Takes no parameter, returns the value of the private field firstName.
	 * @return the reference value to the String containing the firstName.
	 */
	public String getFirstName() {
		return this.firstName;
	}
	/**
	 * This method takes one parameter, String lName, and sets the private field lastName with the value of lName.
	 * However, the field lastName is only set after passing it on to the method isNameCorrect. Only if this method returns true,
	 * lastName is set with lName's value.
	 * 
	 * @param lName String
	 */
	public void setLastName(String lName) {
		if (isNameCorrect(lName)) this.lastName = lName;
	}
	/**
	 * Takes no parameter, returns the value of the private field lastName.
	 * @return the reference value to the String containing the lastName.
	 */
	public String getLastName() {
		return this.lastName;
	}
	/**
	 * Takes one parameter, String name and returns a boolean value. Throws exceptions of type BadAppointmentDataException according to the
	 * type of error found. The method checks for three possible problems within the value of "name". First, if the value is not empty (""); 
	 * second, if the length of the string is no more than 30 characters, and third, if the string does not contain any invalid characters.
	 * Only if those three conditions are true, the method returns true.
	 * @param name The reference value to the String containing the name (either first or last name).
	 * @return true if all three if statements return true, otherwise a corresponding exception is thrown.
	 * @throws BadAppointmentDataException with the specific message and description if one or more of the conditions described is encountered.
	 */
	public static boolean isNameCorrect(String name) {
		//source for the Pattern regex code and methods: https://stackoverflow.com/questions/48031098/check-for-special-characters-in-a-string-using-regex-in-java
		Pattern regex = Pattern.compile("[{}~`$&+,:;=\\\\?@#|/<>^*()%![0-9]]");
		if(!name.equalsIgnoreCase("")) {
			if(name.length()<31) {
				if(!regex.matcher(name).find()) return true;
				else throw new BadAppointmentDataException("Name cannot include characters other than alphabetic characters, the dash (-), the period (.), and the apostrophe (‘)",
						"Illegal characters in name");
			}
			else throw new BadAppointmentDataException("Name cannot exceed 30 characters","Name exceeds maximum length");
		}
		else throw new BadAppointmentDataException("Must enter a value","Empty or null value entered for Client name");
	}
	/**
	 * Takes one parameter, TelephoneNumber phone, and sets the private field phone with the parameter's value.
	 * @param phone Holds the reference value to the TelephoneNumber object containing the phone number.
	 */
	public void setPhone(TelephoneNumber phone) {
		this.phone = phone;
	}
	/**
	 * Takes no parameter, returns the private field phone.
	 * @return the reference value to the TelephoneNumber object containing the phone number.
	 */
	public TelephoneNumber getPhone() {
		return this.phone;
	}
	/**
	 * Takes one parameter, Activity act, and sets the private field activity with the parameter's value.
	 * @param act Holds the reference value to the Activity object containing the activity's description and category.
	 */
	public void setActivity(Activity act) {
		this.activity = act;
		
	}
	/**
	 * Takes no parameter, returns the private field activity.
	 * @return the reference value to the Activity object containing the activity's description and category.
	 */
	public Activity getActivity() {
		return (activity);
	}
	
	/**
	 * Takes no parameter; Returns a String value "aptString". Uses the String class format method to extract the information from the calendar object
	 * in aptDate, and organize it along with the other String values from the other private fields.
	 * @return aptString a formatted String value containing the information in the class private fields: aptDate (Day of week,
	 * month, year, day of month, hour), firstName, lastName, phone and activity.
	 */
	public String toString() {
		/*Source of help for this piece of code:
		https://docs.oracle.com/javase/tutorial/java/data/numberformat.html
		https://www.javatpoint.com/java-string-format*/
		String aptString = String.format("%s %s %td %tY %02d:00%n%s %s%n%s%n%s", 
				aptDate.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT_FORMAT, Locale.US),
				aptDate.getDisplayName(Calendar.MONTH, Calendar.SHORT_FORMAT, Locale.US), 
				aptDate, aptDate, aptDate.get(Calendar.HOUR_OF_DAY),
				getFirstName(), getLastName(), getPhone().toString(), getActivity().toString());
		
		return (aptString);
	}
}
