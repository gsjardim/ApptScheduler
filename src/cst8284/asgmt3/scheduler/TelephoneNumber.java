
package cst8284.asgmt3.scheduler;
import java.io.Serializable;

import cst8284.asgmt3.exception.BadAppointmentDataException;

/**
 * The TelephoneNumber class handles and stores the information for the Telephone number associated with the appointment.
 * An instance of TelephoneNumber is created and stored within the Appointment object, each time a new Appointment is instantiated in the Scheduler class.
 * This class implements the Serializable interface.
 * @author Guilherme Jardim
 * @version 1.0
 **/
public class TelephoneNumber implements Serializable{
	
	//private static final long serialVersionUID = 1L;
	/**
	 * holds the three digits for the phone area code.
	 */
	private int areaCode;
	/**
	 * holds the three digits for the phone prefix.
	 */
	private int prefix;
	/**
	 * holds the four digits for the phone line number.
	 */
	private int lineNumber;
	/**
	 * The one-arg constructor takes one parameter of type String, phoneNumber.
	 * Then it splits the String into three segments, which contain the values for the area code, prefix and line number. These values are parsed to
	 * Integer type and set to the class private fields, by using their respective setter methods: setAreaCode, setPrefix, setLineNumber.
	 * However, first the constructor passes the phoneNumber parameter to the isPhoneCorrect method to verify possible errors. Only if the isPhoneCorrect
	 * returns true, the parameter is processed and the class fields are set.
	 * @param phoneNumber holds a reference value to the String containing the tree segments of phone number, in the following format: "aaa-ppp-llll".
	 */
	public TelephoneNumber(String phoneNumber) {
		if(isPhoneNumberCorrect(phoneNumber)) {
			this.setAreaCode(Integer.parseInt(phoneNumber.substring(0, 3)));
			this.setPrefix(Integer.parseInt(phoneNumber.substring(4, 7)));
			this.setLineNumber(Integer.parseInt(phoneNumber.substring(8, 12)));
		}
		//If the method isPhoneNumberCorrect returns false, it is because an exception was thrown, and it will propagate upwards, until it is caught by
		//another method.
	}
	/**
	 * Takes one parameter of type String, phoneNumber. Returns a boolean value. Throws exceptions of type {@link}BadAppointmentDataException according to the
	 * type of error found. The method checks for possible problems within the value of "phoneNumber". In the first if statement, it checks if the value 
	 * is not empty (""); In the second statement, it checks if the length is exactly 12 characters, if the two dashes are in the right position,and 
	 * if there are exact two dashes only. In the third, if the string does not contain any invalid characters. Last of all, it checks if the area code
	 * does not begin with either '0' nor '1', which are not allowed as first digits for the area code. Only if all those conditions are true, the method
	 * returns true.
	 * @param phoneNumber holds a reference value to the String containing the tree segments of phone number, in the following format: "aaa-ppp-llll".
	 * @return boolean value true if all the conditions in the if statements are true. Otherwise, an exception is thrown and the method returns false.
	 * @throws BadAppointmentDataException related to each of those conditions, passing two strings to the BadAppointmentDataException constructor:
	 * one containing the default message and another containing the description and instructions to the user. There are four possible
	 * exceptions related to a mistyped telephone number.
	 */
	private static boolean isPhoneNumberCorrect (String phoneNumber) {
		int countHyphen=0;
		for(int i=0 ; i<phoneNumber.length();i++) 
			if (phoneNumber.charAt(i)=='-') countHyphen++;
		
		//first, check if the number of digits and hyphens are correct
		if(!phoneNumber.equals("")) {
			if(phoneNumber.length()==12 && phoneNumber.charAt(3)=='-' && phoneNumber.charAt(7)=='-' && countHyphen==2) {
				//second, check for any improper character in the string
				if(phoneNumber.matches("\\d\\d\\d-\\d\\d\\d-\\d\\d\\d\\d")) {
						//Last, check if area code starts with 0 or 1
						if(phoneNumber.charAt(0)!='0' && phoneNumber.charAt(0)!='1') return true;
						else throw new BadAppointmentDataException("Area code can’t start with a ‘0’ or a ‘1’",
								"Invalid number");
					
				}
				else throw new BadAppointmentDataException("Telephone numbers can only contain numbers or the character '-'",
							"Bad character(s) in input string");
				
			}
			else throw new BadAppointmentDataException("Missing digit(s); correct format is AAA-PPP-NNNN, where AAA is the area code and PPP-NNNN is the local number",
					"Incorrect Format");
		}
		else throw new BadAppointmentDataException("Must enter a value","Empty or null value entered");
		
	}
	/**
	 * Takes no parameter, returns the private field areaCode.
	 * @return the int value the phone number area code.
	 */
	public int getAreaCode() {
		return this.areaCode;
	}
	/**
	 * Takes one parameter, int areaCode, and sets the private field areaCode with the parameter's value.
	 * @param areaCode Holds the int value of the phone number area code.
	 */
	public void setAreaCode(int areaCode) {
		
		this.areaCode = areaCode;
	}
	/**
	 * Takes no parameter, returns the private field prefix.
	 * @return the int value the phone number prefix.
	 */
	public int getPrefix() {
		return this.prefix;
	}
	/**
	 * Takes one parameter, int prefix, and sets the private field prefix with the parameter's value.
	 * @param prefix Holds the int value of the phone number prefix.
	 */
	public void setPrefix(int prefix) {
		this.prefix = prefix;
	}
	/**
	 * Takes no parameter, returns the private field lineNumber.
	 * @return the int value the phone number line number.
	 */
	public int getLineNumber() {
		return this.lineNumber;
	}
	/**
	 * Takes one parameter, int lineNumber, and sets the private field lineNumber with the parameter's value.
	 * @param lineNumber Holds the int value of the phone number line number.
	 */
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	/**
	 * Takes no parameter; returns the concateneted string with the entire phone number.
	 * @return a string formatted with the three segments of telephone number: (area code) prefix - line number.
	 */
	public String toString() {
		return ("("+String.valueOf(this.areaCode)+") "+String.valueOf(this.prefix)+"-"+String.valueOf(this.lineNumber));
	}

}
