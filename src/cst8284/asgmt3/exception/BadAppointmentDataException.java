

package cst8284.asgmt3.exception;
/**
 *The BadAppointmentDataException Class is a subclass of RuntimeException.
 *It handles a specific type of exception associated with the data used to make an Appointment object.
 *@author Guilherme Jardim
 *@version 1.0
 **/
public class BadAppointmentDataException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	/**
	 * Holds a detailed description of the exception, with instructions to the user.
	 */
	private String description;
	/**
	 * The default constructor passes a pair of default String messages to the two-args constructor.
	 */
	public BadAppointmentDataException () {
		this("Please, try again","Bad data entered");
		
	}
	/**
	 * This two-args constructor passes the first String paramenter "message" to the superclass RuntimeException's constructor.
	 * The second String parameter "description", is passed to the setDescription method.
	 * @param message The default message associated with the specific exception.
	 * @param description A more detailed description of what the problem is, with instructions for the user.
	 */
	public BadAppointmentDataException (String message, String description) {
		super(message);
		setDescription(description);
		
	}
	/**
	 * This method returns the String value of the private field "description"
	 * @return String description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * This method sets the private field "description" with the received parameter of type String.
	 * @param description String
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
